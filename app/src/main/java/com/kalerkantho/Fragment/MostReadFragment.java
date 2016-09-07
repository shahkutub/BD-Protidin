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
import com.kalerkantho.Adapter.MostReadRecyAdapter;
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

public class MostReadFragment extends Fragment {
   private Context con;
   private AllNewsObj allObj;
   private List<AllCommonNewsItem> mostRead= new ArrayList<AllCommonNewsItem>();
   private RecyclerView mostReadNewRecList;
   private LinearLayoutManager mLayoutManager;
   private Drawable dividerDrawable;
   private MostReadRecyAdapter mAdapter;
   private ProgressBar mostReadBg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mostread,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        intiU();
    }

    private void intiU() {
        Gson g = new Gson();
        try {
            if (!(TextUtils.isEmpty(PersistData.getStringData(con, AppConstant.HOMERESPONSE)))){
                allObj = g.fromJson(PersistData.getStringData(con, AppConstant.HOMERESPONSE),AllNewsObj.class);

                mostRead.clear();

                for(CommonNewsItem topNews:allObj.getMost_read())
                {
                    AllCommonNewsItem singleObj=new AllCommonNewsItem();
                    singleObj.setType("fullscreen");
                    singleObj.setNews_obj(topNews);
                    mostRead.add(singleObj);

                }




                mostReadNewRecList= (RecyclerView) getView().findViewById(R.id.mostReadNewRecList);
                mostReadBg = (ProgressBar) getView().findViewById(R.id.mostReadBg);
                mLayoutManager = new LinearLayoutManager(con);
                mostReadNewRecList.setLayoutManager(mLayoutManager);
                dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                mostReadNewRecList.addItemDecoration(dividerItemDecoration);
                mostReadNewRecList.setHasFixedSize(true);
                mAdapter = new MostReadRecyAdapter(con,mostRead,null);
                mostReadNewRecList.setAdapter(mAdapter);
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
                    PersistData.setIntData(getContext(), AppConstant.FRAGMENTPOSITON,3);

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
                    mAdapter = new MostReadRecyAdapter(con,mostRead,null);
                    mostReadNewRecList.setAdapter(mAdapter);
                }
            },100);
        }

        Log.e("URL : ", url);

        mostReadBg.setVisibility(View.VISIBLE);


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

                        mostReadBg.setVisibility(View.GONE);

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();

                                allObj=g.fromJson(new String(response),AllNewsObj.class);
                                AppConstant.REFRESHFLAG = false;
                                mostRead.clear();

                                for(CommonNewsItem topNews:allObj.getMost_read())
                                {
                                    AllCommonNewsItem singleObj=new AllCommonNewsItem();
                                    singleObj.setType("fullscreen");
                                    singleObj.setNews_obj(topNews);
                                    mostRead.add(singleObj);

                                }

                                if (mostRead.size() > 0) {
                                    mAdapter = new MostReadRecyAdapter(con, mostRead,null);
                                    mostReadNewRecList.setAdapter(mAdapter);
                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            mostReadBg.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

}
