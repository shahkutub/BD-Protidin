package com.kalerkantho;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.kalerkantho.Adapter.RecyclerAdapter;
import com.kalerkantho.Model.All_Cat_News_Obj;
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


public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;
    ProgressBar progressShow;
    private boolean isViewShown = false;
    public  List<AllCommonNewsItem> allCommonNewsItem = new ArrayList<AllCommonNewsItem>();
    Context con;
    AllNewsObj allObj;
    private boolean bgflag = false;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_screen,null);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        initUI();
    }


    public void initUI() {
        swipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefreshLayout);
        progressShow = (ProgressBar) getView().findViewById(R.id.progressShow);
        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerview);

        Drawable dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(con);
        mRecyclerView.setLayoutManager(mLayoutManager);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bgflag = true;
                requestGetNeslist(AllURL.getHomeNews());
            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PersistData.setIntData(getContext(), AppConstant.FRAGMENTPOSITON,0);
                    requestGetNeslist(AllURL.getHomeNews());
                }
            },100);
        }
    }

    private void requestGetNeslist(final String url) {
        if (!NetInfo.isOnline(con)) {
           // AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
            //return;
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    withoutInterNet();
                }
            },100);


        }

        Log.e("URL : ", url);

        if (!bgflag){
            progressShow.setVisibility(View.VISIBLE);
        }

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


                        progressShow.setVisibility(View.GONE);

                        swipeRefreshLayout.setRefreshing(false);

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();
                               // AllnewsResponse newslistresponsee = g.fromJson(new String(response), AllnewsResponse.class);

                              //  Log.e("getData()", ">>" + getActivity().getResources().getString(R.string.a));

                                //String jsonString=getActivity().getResources().getString(R.string.a).toString();
                               // String jsonString=loadAssetTextAsString(getContext(),"abc.txt");

                                PersistData.setStringData(con, AppConstant.HOMERESPONSE,response);

                                allObj=g.fromJson(new String(response),AllNewsObj.class);



                                allCommonNewsItem.clear();

                                int i=0;
                                // for top news and below list
                                for(CommonNewsItem topNews:allObj.getTop_news())
                                {
                                    if(i==0)
                                    {
                                        AllCommonNewsItem singleObj=new AllCommonNewsItem();
                                        singleObj.setNewsCategory("topnews");
                                        singleObj.setType("fullscreen");
                                        singleObj.setNews_obj(topNews);
                                        allCommonNewsItem.add(singleObj);


                                    }else
                                    {
                                        AllCommonNewsItem singleObj=new AllCommonNewsItem();
                                        singleObj.setNewsCategory("topnews");
                                        singleObj.setType("defaultscreen");
                                        singleObj.setNews_obj(topNews);
                                       allCommonNewsItem.add(singleObj);
                                    }
                                    i++;
                                }

                        /*        // for bibid row
                                AllCommonNewsItem titleObj=new AllCommonNewsItem();
                                titleObj.setType("titleshow");
                                titleObj.setCategory_title(getActivity().getResources().getString(R.string.bibid));
                                allCommonNewsItem.add(titleObj);*/

                                // for bibid row
                                AllCommonNewsItem hListObj=new AllCommonNewsItem();
                                hListObj.setNewsCategory("blueslide");
                                hListObj.setType("horizontal");
                                hListObj.setList_news_obj(allObj.getBlueslide());
                                allCommonNewsItem.add(hListObj);

                                // All category object need to add

                                for(All_Cat_News_Obj allCat:allObj.getAll_cat_news())
                                {
                                    // for bibid row
                                    AllCommonNewsItem a4=new AllCommonNewsItem();
                                    a4.setNewsCategory("allnews");
                                    a4.setType("titleshow");
                                    a4.setCategory_title(allCat.getCategory_name());
                                    a4.setCategory_id(allCat.getCategory_id());
                                    allCommonNewsItem.add(a4);

                                    for(CommonNewsItem cn:allCat.getNews())
                                    {
                                        AllCommonNewsItem a2=new AllCommonNewsItem();
                                        a4.setNewsCategory("allnews");
                                        a2.setType("defaultscreen");
                                        a2.setNews_obj(cn);
                                       allCommonNewsItem.add(a2);
                                    }
                                }



                             /*   // for bhinow nes
                                AllCommonNewsItem b3Obj=new AllCommonNewsItem();
                                b3Obj.setType("titleshow");
                                b3Obj.setCategory_title(getActivity().getResources().getString(R.string.others_news));
                                allCommonNewsItem.add(b3Obj);*/

                                // for bibid row


                                AllCommonNewsItem redObj=new AllCommonNewsItem();
                                redObj.setNewsCategory("bibid");
                                redObj.setType("horizontal");
                                redObj.setList_news_obj(allObj.getRedslider());
                                allCommonNewsItem.add(redObj);


                                if (allCommonNewsItem.size() > 0) {
                                    //recyclerview adapter
                                    mAdapter = new RecyclerAdapter(getActivity(), allCommonNewsItem);
                                    //set adpater for recyclerview
                                    mRecyclerView.setAdapter(mAdapter);

                                }

                            }


                        } catch (final Exception e) {
                            e.printStackTrace();
                            progressShow.setVisibility(View.GONE);
                        }


                    }
                });
            }
        });


    }

   /* public String getData()
    {
        try {
            InputStream is = getActivity().getAssets().open("abc.txt");

            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer);

            // Finally stick the string into the text view.
            // Replace with whatever you need to have the text into.

          return  text;

        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);

        }
    }

    private String loadAssetTextAsString(Context context, String name) {
        BufferedReader in = null;
        try {
            StringBuilder buf = new StringBuilder();
            InputStream is = context.getAssets().open(name);
            in = new BufferedReader(new InputStreamReader(is));

            String str;
            boolean isFirst = true;
            while ( (str = in.readLine()) != null ) {
                if (isFirst)
                    isFirst = false;
                else
                    buf.append('\n');
                buf.append(str);
            }
            return buf.toString();
        } catch (IOException e) {
            Log.e("fff", "Error opening asset " + name);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e("fff", "Error closing asset " + name);
                }
            }
        }

        return null;
    }*/


    public void withoutInterNet(){


try {
    Gson g = new Gson();

    if (!(TextUtils.isEmpty(PersistData.getStringData(con, AppConstant.HOMERESPONSE)))){
        allObj = g.fromJson(PersistData.getStringData(con, AppConstant.HOMERESPONSE),AllNewsObj.class);

        allCommonNewsItem.clear();

        int i=0;
        // for top news and below list
        for(CommonNewsItem topNews:allObj.getTop_news())
        {
            if(i==0)
            {
                AllCommonNewsItem singleObj=new AllCommonNewsItem();
                singleObj.setNewsCategory("topnews");
                singleObj.setType("fullscreen");
                singleObj.setNews_obj(topNews);
                allCommonNewsItem.add(singleObj);


            }else
            {
                AllCommonNewsItem singleObj=new AllCommonNewsItem();
                singleObj.setNewsCategory("topnews");
                singleObj.setType("defaultscreen");
                singleObj.setNews_obj(topNews);
                allCommonNewsItem.add(singleObj);
            }
            i++;
        }

        // for bibid row
        AllCommonNewsItem hListObj=new AllCommonNewsItem();
        hListObj.setNewsCategory("blueslide");
        hListObj.setType("horizontal");
        hListObj.setList_news_obj(allObj.getBlueslide());
        allCommonNewsItem.add(hListObj);


        for(All_Cat_News_Obj allCat:allObj.getAll_cat_news())
        {
            // for bibid row
            AllCommonNewsItem a4=new AllCommonNewsItem();
            a4.setNewsCategory("allnews");
            a4.setType("titleshow");
            a4.setCategory_title(allCat.getCategory_name());
            a4.setCategory_id(allCat.getCategory_id());
            allCommonNewsItem.add(a4);

            for(CommonNewsItem cn:allCat.getNews())
            {
                AllCommonNewsItem a2=new AllCommonNewsItem();
                a4.setNewsCategory("allnews");
                a2.setType("defaultscreen");
                a2.setNews_obj(cn);
                allCommonNewsItem.add(a2);
            }
        }

        AllCommonNewsItem redObj=new AllCommonNewsItem();
        redObj.setNewsCategory("bibid");
        redObj.setType("horizontal");
        redObj.setList_news_obj(allObj.getRedslider());
        allCommonNewsItem.add(redObj);

        if (allCommonNewsItem.size() > 0) {
            //recyclerview adapter
            mAdapter = new RecyclerAdapter(getActivity(),allCommonNewsItem);
            //set adpater for recyclerview
            mRecyclerView.setAdapter(mAdapter);

        }
    }

}catch (JsonSyntaxException e){
    e.printStackTrace();
    }

    }

}
