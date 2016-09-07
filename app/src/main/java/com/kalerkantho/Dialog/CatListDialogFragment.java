package com.kalerkantho.Dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.aapbd.utils.storage.PersistData;
import com.google.gson.Gson;
import com.kalerkantho.Adapter.CatListRecyAdapter;
import com.kalerkantho.MainActivity;
import com.kalerkantho.Model.CommonNewsItem;
import com.kalerkantho.R;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.DividerItemDecoration;
import com.kalerkantho.Utils.NetInfo;
import com.kalerkantho.holder.AllNirbahito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class CatListDialogFragment extends DialogFragment {

    private Context con;
    private View view;
    private ImageView dissmissCatListBtn;
    private TextView catHeadText;
    //private ProgressBar progressCat;
    private RecyclerView catRcyList;
    private CatListRecyAdapter catAdapter;
    private String response="";
    private AllNirbahito allCatList;
    private LinearLayoutManager mLayoutManager;
    private int pagNumber =1,totalItemCount,pastVisiblesItems,totalpage,visibleItemCount;
    private List<CommonNewsItem> my_newsListTemp = new ArrayList<CommonNewsItem>();
   // Drawable dividerDrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.catlist_dialog, container, true);
        con = getActivity();
        initUi();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initUi() {

        final Typeface face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");
        dissmissCatListBtn = (ImageView) view.findViewById(R.id.dissmissCatListBtn);

        catRcyList = (RecyclerView) view.findViewById(R.id.catRcyList);

        //progressCat = (ProgressBar) view.findViewById(R.id.progressCat);
        catHeadText = (TextView) view.findViewById(R.id.catHeadText);

        mLayoutManager = new LinearLayoutManager(con);
        catRcyList.setLayoutManager(mLayoutManager);



        catRcyList.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //slide_up for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findLastVisibleItemPosition();
                    pagNumber = pagNumber + 1;
                    if (hasMorePage())
                    {
                        if ( ( pastVisiblesItems) >= totalItemCount-AppConstant.scroolBeforeLatItem)
                        {
                            getCatList(AllURL.getCatList(AppConstant.CATEGORYTYPE,pagNumber));

                        }
                    }
                }
            }
        });

        Drawable dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        catRcyList.addItemDecoration(dividerItemDecoration);

       // catHeadText.setText(getString(R.string.catlist));
        catHeadText.setText(AppConstant.CATEGORYTITLE);
        catHeadText.setTypeface(face_bold);

        dissmissCatListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        getCatList(AllURL.getCatList(AppConstant.CATEGORYTYPE,pagNumber));

    }


 private void getCatList(final String url) {

     if (!NetInfo.isOnline(con)) {
         AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet Access!");
         return;

     }

     Log.e("URL : ", url);
     //progressCat.setVisibility(View.VISIBLE);

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

                      //  progressCat.setVisibility(View.GONE);

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {

                                Gson g = new Gson();
                                allCatList=g.fromJson(new String(response),AllNirbahito.class);

                                my_newsListTemp.addAll(allCatList.getMy_news());

                                if(allCatList.getStatus().equalsIgnoreCase("1")){

                                      catAdapter = new CatListRecyAdapter(con,my_newsListTemp,null);
                                      catRcyList.setAdapter(catAdapter);

                                       //Drawable dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
                                       //RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                                       //catRcyList.addItemDecoration(dividerItemDecoration);

                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            //progressCat.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private boolean hasMorePage() {

        if(allCatList.getPaginator()!=null){
            if (TextUtils.isDigitsOnly(""+allCatList.getPaginator().getPageCount())){
                totalpage = allCatList.getPaginator().getPageCount();
            }else{
                totalpage=1;
            }
            int  currentPageCount = Integer.parseInt(allCatList.getPaginator().getPage());

            if (currentPageCount < totalpage) {
                return true;
            }
        }else{
            return false;
        }

        return false;
    }

}
