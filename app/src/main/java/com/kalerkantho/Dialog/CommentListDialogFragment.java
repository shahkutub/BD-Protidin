package com.kalerkantho.Dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aapbd.utils.network.AAPBDHttpClient;
import com.aapbd.utils.storage.PersistentUser;
import com.bangladesh_pratidin.R;
import com.google.gson.Gson;
import com.kalerkantho.Adapter.CommentListAdapter;
import com.kalerkantho.Model.CommentInfo;
import com.kalerkantho.Model.CommentListResponse;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.DividerItemDecoration;
import com.kalerkantho.Utils.NetInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by hp on 9/1/2016.
 */
public class CommentListDialogFragment extends DialogFragment {

    private Context con;
    private View view;
    private ImageView imgCrossComment;
    private TextView tvCommentc, tvCommentPlz;
    private ProgressBar progressCat;
    private RecyclerView rvCommentList;
    private CommentListAdapter catAdapter;
    private String response = "";
    private CommentListResponse allCatList;
    private LinearLayoutManager mLayoutManager;
    private int pagNumber = 1, totalItemCount, pastVisiblesItems, totalpage, visibleItemCount;
    private List<CommentInfo> my_newsListTemp = new ArrayList<CommentInfo>();
    // Drawable dividerDrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.comment_list_dialog, container, true);
        con = getActivity();
        initUi();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initUi() {

        final Typeface face_reg = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
        final Typeface face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");
        imgCrossComment = (ImageView) view.findViewById(R.id.imgCrossComment);
        progressCat = (ProgressBar) view.findViewById(R.id.progressCat);
        rvCommentList = (RecyclerView) view.findViewById(R.id.rvCommentList);

        tvCommentPlz = (TextView) view.findViewById(R.id.tvCommentPlz);
        tvCommentc = (TextView) view.findViewById(R.id.tvCommentc);


        tvCommentPlz.setTypeface(face_reg);
        tvCommentc.setTypeface(face_reg);

        tvCommentPlz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommentDialog dialogMenu = new CommentDialog();
                dialogMenu.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
                dialogMenu.show(getActivity().getFragmentManager(), "");
            }
        });

        mLayoutManager = new LinearLayoutManager(con);
        rvCommentList.setLayoutManager(mLayoutManager);

        rvCommentList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //slide_up for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findLastVisibleItemPosition();
                    pagNumber = pagNumber + 1;
                    if (hasMorePage()) {
                        if ((pastVisiblesItems) >= totalItemCount - AppConstant.scroolBeforeLatItem) {
                            getCommentList(AllURL.commentListUrl(AppConstant.newsID, PersistentUser.getUserID(con), pagNumber));

                        }
                    }
                }
            }
        });

        Drawable dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        rvCommentList.addItemDecoration(dividerItemDecoration);

        // catHeadText.setText(getString(R.string.catlist));

        tvCommentc.setTypeface(face_bold);

        imgCrossComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        getCommentList(AllURL.commentListUrl(AppConstant.newsID, PersistentUser.getUserID(con), pagNumber));

    }


    private void getCommentList(final String url) {

        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet Access!");
            return;

        }

        Log.e("URL : ", url);
        progressCat.setVisibility(View.VISIBLE);

        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {


            @Override
            public void run() {

                try {
                    response = AAPBDHttpClient.get(url).body();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        progressCat.setVisibility(View.GONE);

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {

                                Gson g = new Gson();
                                allCatList = g.fromJson(new String(response), CommentListResponse.class);


                                my_newsListTemp.addAll(allCatList.getComments());
                                Log.e("Commentsize", ">>" + my_newsListTemp.size());
                                if (allCatList.getStatus().equalsIgnoreCase("1")) {

                                    catAdapter = new CommentListAdapter(con, my_newsListTemp, null);
                                    rvCommentList.setAdapter(catAdapter);

                                    //Drawable dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
                                    //RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                                    //catRcyList.addItemDecoration(dividerItemDecoration);

                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            progressCat.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private boolean hasMorePage() {

//        if(allCatList.getComments()!=null){
//            if (TextUtils.isDigitsOnly(""+allCatList.getComments().getPageCount())){
//                totalpage = allCatList.getComments().getPageCount();
//            }else{
//                totalpage=1;
//            }
//            int  currentPageCount = Integer.parseInt(allCatList.getComments().getPage());
//
//            if (currentPageCount < totalpage) {
//                return true;
//            }
//        }else{
//            return false;
//        }

        return false;
    }

}
