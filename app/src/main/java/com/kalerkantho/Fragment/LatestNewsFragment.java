package com.kalerkantho.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.aapbd.utils.network.AAPBDHttpClient;
import com.aapbd.utils.storage.PersistData;
import com.bangladesh_pratidin.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kalerkantho.Adapter.LatestRecyAdapter;
import com.kalerkantho.Model.CommonNewsItem;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.DividerItemDecoration;
import com.kalerkantho.Utils.NetInfo;
import com.kalerkantho.holder.AllCommonNewsItem;
import com.kalerkantho.holder.AllNewsObj;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class LatestNewsFragment extends Fragment {
   private Context con;
   private AllNewsObj allObj;
   private List<AllCommonNewsItem> latestNews = new ArrayList<AllCommonNewsItem>();
   private RecyclerView latestNewRecList;
   private Drawable dividerDrawable;
   private LatestRecyAdapter lAdapter;
   private ProgressBar latestNewBg;
  private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.latestnews,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        intiU();

    }

    private void intiU() {

        try {

            Gson g = new Gson();
            if (!(TextUtils.isEmpty(PersistData.getStringData(con, AppConstant.HOMERESPONSE)))){
                allObj = g.fromJson(PersistData.getStringData(con, AppConstant.HOMERESPONSE),AllNewsObj.class);

                latestNews.clear();

                for(CommonNewsItem topNews:allObj.getLatest_news())
                {
                    AllCommonNewsItem singleObj=new AllCommonNewsItem();
                    singleObj.setType("fullscreen");
                    singleObj.setNews_obj(topNews);
                    latestNews.add(singleObj);

                }


                latestNewRecList= (RecyclerView) getView().findViewById(R.id.latestNewRecList);

                latestNewBg = (ProgressBar) getView().findViewById(R.id.latestNewBg);


              /*  latestNewRecList.setLayoutManager(new GridLayoutManager(con, 2));
                GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(con, R.dimen.space);
                latestNewRecList.addItemDecoration(itemDecoration);*/

                mLayoutManager = new LinearLayoutManager(con);
                latestNewRecList.setLayoutManager(mLayoutManager);
                dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                latestNewRecList.addItemDecoration(dividerItemDecoration);
                latestNewRecList.setHasFixedSize(true);



                lAdapter = new LatestRecyAdapter(con,latestNews,null);
                latestNewRecList.setAdapter(lAdapter);
            }
        }catch (JsonSyntaxException e){
            e.printStackTrace();
        }


        if (AppConstant.REFRESHFLAG){

            requestGetNeslist(AllURL.getHomeNews());
        }

      /* GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(con, R.dimen.space);
        latestNewRecList.addItemDecoration(itemDecoration);
        itemDecoration.notifyAll();*/

    }

   @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PersistData.setIntData(getContext(), AppConstant.FRAGMENTPOSITON,2);

                }
            },100);
        }
    }


    private void requestGetNeslist(final String url) {
        if (!NetInfo.isOnline(con)) {
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  //  intiU();
                    lAdapter = new LatestRecyAdapter(con,latestNews,null);
                    latestNewRecList.setAdapter(lAdapter);
                }
            },100);


        }

        Log.e("URL : ", url);

        latestNewBg.setVisibility(View.VISIBLE);


        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {
            String response = "";

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

                        latestNewBg.setVisibility(View.GONE);

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();

                                allObj=g.fromJson(new String(response),AllNewsObj.class);
                                AppConstant.REFRESHFLAG = false;
                                latestNews.clear();

                                for(CommonNewsItem topNews:allObj.getLatest_news())
                                {
                                    AllCommonNewsItem singleObj=new AllCommonNewsItem();
                                    singleObj.setType("fullscreen");
                                    singleObj.setNews_obj(topNews);
                                    latestNews.add(singleObj);
                                }
                                if (latestNews.size() > 0) {
                                    lAdapter = new LatestRecyAdapter(con, latestNews,null);
                                    latestNewRecList.setAdapter(lAdapter);
                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            latestNewBg.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}
