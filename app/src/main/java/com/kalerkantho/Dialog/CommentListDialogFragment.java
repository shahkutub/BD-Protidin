package com.kalerkantho.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Window;
import android.widget.EditText;
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
import com.kalerkantho.Model.LoginResponse;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.BusyDialog;
import com.kalerkantho.Utils.DividerItemDecoration;
import com.kalerkantho.Utils.NetInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;

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
    private Typeface face_reg, face_bold;
    EditText etWriteComment;
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
        etWriteComment = (EditText)view.findViewById(R.id.etWriteComment);

        tvCommentPlz = (TextView) view.findViewById(R.id.tvCommentPlz);
        tvCommentc = (TextView) view.findViewById(R.id.tvCommentc);


        tvCommentPlz.setTypeface(face_reg);
        tvCommentc.setTypeface(face_reg);

        tvCommentPlz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!PersistentUser.isLogged(con)) {
                    loginDialoag(con);
                } else if (TextUtils.isEmpty(etWriteComment.getText().toString())) {
                    AlertMessage.showMessage(con, getString(R.string.app_name), getResources().getString(R.string.write_comment_pleas));
                } else {
                    submitCommentAPI(AllURL.commentURL());
                }

//                CommentDialog dialogMenu = new CommentDialog();
//                dialogMenu.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
//                dialogMenu.show(getActivity().getFragmentManager(), "");
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



        CommentInfo comInfo1 = new CommentInfo();
        comInfo1.setComment_text("আমরা শোকাহত");
        comInfo1.setFull_name("মারুফ খান");
        comInfo1.setCreated_at("১২ জুন, ১২ঃ১০");

        CommentInfo comInfo2 = new CommentInfo();
        comInfo2.setComment_text("আমরা শোকাহত");
        comInfo2.setFull_name("মারুফ খান");
        comInfo2.setCreated_at("১২ জুন, ১২ঃ১০");


        CommentInfo comInfo3 = new CommentInfo();
        comInfo3.setComment_text("আমরা শোকাহত");
        comInfo3.setFull_name("মারুফ খান");
        comInfo3.setCreated_at("১২ জুন, ১২ঃ১০");

        CommentInfo comInfo4 = new CommentInfo();
        comInfo4.setComment_text("আমরা শোকাহত");
        comInfo4.setFull_name("মারুফ খান");
        comInfo4.setCreated_at("১২ জুন, ১২ঃ১০");

        CommentInfo comInfo5 = new CommentInfo();
        comInfo5.setComment_text("আমরা শোকাহত");
        comInfo5.setFull_name("মারুফ খান");
        comInfo5.setCreated_at("১২ জুন, ১২ঃ১০");


        CommentInfo comInfo6 = new CommentInfo();
        comInfo6.setComment_text("আমরা শোকাহত");
        comInfo6.setFull_name("মারুফ খান");
        comInfo6.setCreated_at("১২ জুন, ১২ঃ১০");


        my_newsListTemp.add(comInfo1);
        my_newsListTemp.add(comInfo2);
        my_newsListTemp.add(comInfo3);
        my_newsListTemp.add(comInfo4);
        my_newsListTemp.add(comInfo5);
        my_newsListTemp.add(comInfo6);
        catAdapter = new CommentListAdapter(con, my_newsListTemp, null);
        rvCommentList.setAdapter(catAdapter);
        getCommentList(AllURL.commentListUrl(AppConstant.newsID, PersistentUser.getUserID(con), pagNumber));

    }


    private void loginDialoag(final Context con) {
        final Dialog dialogLogin = new Dialog(con);
        dialogLogin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogin.setContentView(R.layout.dialog_login);
        dialogLogin.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitel2 = (TextView) dialogLogin.findViewById(R.id.tvTitel2);
        TextView tvDescription2 = (TextView) dialogLogin.findViewById(R.id.tvDescription2);
        TextView tvLeftCommund = (TextView) dialogLogin.findViewById(R.id.tvLeftCommund);
        TextView tvRightCommund = (TextView) dialogLogin.findViewById(R.id.tvRightCommund);
        tvDescription2.setText(getResources().getString(R.string.login_first_comment));
        //==================Font set==========================

        tvTitel2.setTypeface(face_reg);
        tvLeftCommund.setTypeface(face_bold);
        tvRightCommund.setTypeface(face_bold);
        tvDescription2.setTypeface(face_reg);


        tvLeftCommund.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoginDialogFragment dialogMenu = new LoginDialogFragment();
                dialogMenu.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogMenu.show(getActivity().getFragmentManager(), "");
                dialogLogin.dismiss();
            }
        });

        tvRightCommund.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogLogin.dismiss();
            }
        });

        dialogLogin.show();
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



    protected void submitCommentAPI(final String url) {
        /**
         * --------------- Check Internet------------
         */
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
            return;
        }

        /**
         * ------Show Busy Dialog------------------
         */
        final BusyDialog busyNow = new BusyDialog(con, true, false);
        busyNow.show();
        /**
         * ---------Create object of  RequestParams to send value with URL---------------
         */
        final RequestParams param = new RequestParams();

        try {
            param.put("news_id", AppConstant.newsID);
            param.put("user_id", PersistentUser.getUserID(con));
            param.put("comment_text", etWriteComment.getText().toString());
        } catch (final Exception e1) {
            e1.printStackTrace();
        }
        /**
         * ---------Create object of  AsyncHttpClient class to heat server ---------------
         */
        final AsyncHttpClient client = new AsyncHttpClient();
        Log.e("Submit FeedBack URL ", ">>" + url);
        client.post(url, param, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] response) {
                //-----------Off Busy Dialog ----------------
                if (busyNow != null) {
                    busyNow.dismis();
                }
                //-----------------Print Response--------------------
                Log.e("commentResponse ", ">>" + new String(response));

                //------------Data persist using Gson------------------
                Gson g = new Gson();
                LoginResponse feedbackResponse = g.fromJson(new String(response), LoginResponse.class);


                if (feedbackResponse.getStatus().equalsIgnoreCase("1")) {
//                    commentThanks.setVisibility(View.VISIBLE);
////                            Toast.makeText(con,getResources().getString(R.string.comment_succes), Toast.LENGTH_LONG).show();
//                    etComment.setText("");
////                    getDialog().dismiss();

                } else {
                    AlertMessage.showMessage(con, "Feedback", feedbackResponse.getMsg());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

//				Log.e("LoginerrorResponse", new String(errorResponse));

                if (busyNow != null) {
                    busyNow.dismis();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried

            }
        });

    }

}
