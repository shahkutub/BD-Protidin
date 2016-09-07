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
import com.kalerkantho.Adapter.TopNewsRecyAdapter;
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

public class TopNewsFragment extends Fragment {
   private Context con;
   private AllNewsObj allObj;
   private List<AllCommonNewsItem> topnews = new ArrayList<AllCommonNewsItem>();
   private RecyclerView topnewRecList;
   private LinearLayoutManager mLayoutManager;
   private Drawable dividerDrawable;
   private TopNewsRecyAdapter tAdapter;
   private ProgressBar topNewsBg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.topnews,null);
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
                topnews.clear();
                int i=0;
                if(allObj !=null && allObj.getTop_news().size()>0)
                    for(CommonNewsItem topNews:allObj.getTop_news())
                    {
                        if(i==0)
                        {
                            AllCommonNewsItem singleObj=new AllCommonNewsItem();
                            singleObj.setType("fullscreen");
                            singleObj.setNews_obj(topNews);
                            topnews.add(singleObj);

                        }else
                        {
                            AllCommonNewsItem singleObj=new AllCommonNewsItem();
                            singleObj.setType("defaultscreen");
                            singleObj.setNews_obj(topNews);
                            topnews.add(singleObj);
                        }
                        i++;
                    }

                topnewRecList= (RecyclerView) getView().findViewById(R.id.topnewRecList);
                topNewsBg = (ProgressBar) getView().findViewById(R.id.topNewsBg);
                mLayoutManager = new LinearLayoutManager(con);
                topnewRecList.setLayoutManager(mLayoutManager);
                dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                topnewRecList.addItemDecoration(dividerItemDecoration);


                tAdapter = new TopNewsRecyAdapter(con,topnews);
                topnewRecList.setAdapter(tAdapter);

            }

        } catch (JsonSyntaxException e) {
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
                    PersistData.setIntData(getContext(), AppConstant.FRAGMENTPOSITON,1);

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
                    tAdapter = new TopNewsRecyAdapter(con,topnews);
                    topnewRecList.setAdapter(tAdapter);
                }
            },100);


        }

        Log.e("URL : ", url);


        topNewsBg.setVisibility(View.VISIBLE);


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


                        topNewsBg.setVisibility(View.GONE);

                       // swipeRefreshLayout.setRefreshing(false);

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();

                                allObj=g.fromJson(new String(response),AllNewsObj.class);
                                AppConstant.REFRESHFLAG = false;
                                topnews.clear();

                                int i=0;
                                // for top news and below list
                                for(CommonNewsItem topNews:allObj.getTop_news())
                                {
                                    if(i==0)
                                    {
                                        AllCommonNewsItem singleObj=new AllCommonNewsItem();
                                        singleObj.setType("fullscreen");
                                        singleObj.setNews_obj(topNews);
                                        topnews.add(singleObj);

                                    }else
                                    {
                                        AllCommonNewsItem singleObj=new AllCommonNewsItem();
                                        singleObj.setType("defaultscreen");
                                        singleObj.setNews_obj(topNews);
                                        topnews.add(singleObj);
                                    }
                                    i++;
                                }

                                if (topnews.size() > 0) {
                                    tAdapter = new TopNewsRecyAdapter(getActivity(), topnews);
                                    topnewRecList.setAdapter(tAdapter);
                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            topNewsBg.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }



}