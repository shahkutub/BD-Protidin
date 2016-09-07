package com.kalerkantho.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kalerkantho.Adapter.LatestRecyAdapter;
import com.kalerkantho.Model.CommonNewsItem;
import com.kalerkantho.R;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.GridSpacingItemDecoration;
import com.kalerkantho.Utils.NetInfo;
import com.kalerkantho.holder.AllCommonNewsItem;
import com.kalerkantho.holder.AllNewsObj;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SelectedNewsFragment extends Fragment {
   private Context con;
   private AllNewsObj allObj;
   private List<AllCommonNewsItem> selectedNews= new ArrayList<AllCommonNewsItem>();
   private RecyclerView selectedNewRecList;
   private LinearLayoutManager mLayoutManager;
   private Drawable dividerDrawable;
   private LatestRecyAdapter sAdapter;
   private ProgressBar selectedBg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.selectednews,null);
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

                selectedNews.clear();

                for(CommonNewsItem topNews:allObj.getSelected_news())
                {
                    AllCommonNewsItem singleObj=new AllCommonNewsItem();
                    singleObj.setType("fullscreen");
                    singleObj.setNews_obj(topNews);
                    selectedNews.add(singleObj);

                }

                selectedNewRecList= (RecyclerView) getView().findViewById(R.id.selectedNewRecList);
                selectedBg = (ProgressBar) getView().findViewById(R.id.selectedBg);
                selectedNewRecList.setLayoutManager(new GridLayoutManager(con, 2));
                GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(con, R.dimen.space);
                selectedNewRecList.addItemDecoration(itemDecoration);

                sAdapter = new LatestRecyAdapter(con,selectedNews,null);
                selectedNewRecList.setAdapter(sAdapter);
            }

        }catch (JsonSyntaxException e){
            e.printStackTrace();
        }


        if (AppConstant.REFRESHFLAG){

            requestGetNeslist(AllURL.getHomeNews());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PersistData.setIntData(getContext(), AppConstant.FRAGMENTPOSITON,4);

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
                    //intiU();
                    sAdapter = new LatestRecyAdapter(con,selectedNews,null);
                    selectedNewRecList.setAdapter(sAdapter);
                }
            },100);
        }

        Log.e("URL : ", url);

        selectedBg.setVisibility(View.VISIBLE);


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

                        selectedBg.setVisibility(View.GONE);

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();

                                allObj=g.fromJson(new String(response),AllNewsObj.class);
                                AppConstant.REFRESHFLAG = false;
                                selectedNews.clear();

                                for(CommonNewsItem topNews:allObj.getSelected_news())
                                {
                                    AllCommonNewsItem singleObj=new AllCommonNewsItem();
                                    singleObj.setType("fullscreen");
                                    singleObj.setNews_obj(topNews);
                                    selectedNews.add(singleObj);
                                }

                                if (selectedNews.size() > 0) {
                                    sAdapter = new LatestRecyAdapter(con, selectedNews,null);
                                    selectedNewRecList.setAdapter(sAdapter);
                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            selectedBg.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}
