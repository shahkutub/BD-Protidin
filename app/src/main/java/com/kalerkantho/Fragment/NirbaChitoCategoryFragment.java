package com.kalerkantho.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aapbd.utils.network.AAPBDHttpClient;
import com.aapbd.utils.storage.PersistData;
import com.google.gson.Gson;
import com.kalerkantho.Adapter.MyFvRecyAdapterList;
import com.kalerkantho.Model.Category;
import com.kalerkantho.Model.CommonNewsItem;
import com.kalerkantho.MyDb.MyDBHandler;
import com.kalerkantho.R;
import com.kalerkantho.TabFragment;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.DividerItemDecoration;
import com.kalerkantho.Utils.NetInfo;
import com.kalerkantho.holder.AllNirbahito;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class NirbaChitoCategoryFragment extends Fragment {
    private Context con;
    private ImageView selectBtniv;
    private TextView tvLike, tvTitle1, tvTitle2, startBtn;
    private LinearLayout emptyFv;
    private MyDBHandler db;
    private RecyclerView recFvoList;

    private MyFvRecyAdapterList myAdapter;
    Drawable dividerDrawable;
    private LinearLayoutManager myFvLayoutManager;

    private AllNirbahito allnirbahito;
    private String allCategoryID = "";
    private int pageNumber = 1, visibleItemCount = 0, totalItemCount, pastVisiblesItems, totalpage;

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private List<CommonNewsItem> my_newsListTemp = new ArrayList<CommonNewsItem>();
    private ProgressBar progressNirbachito;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.nirbachitofragment, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        db = new MyDBHandler(con);

        Log.e("allCategvvoryID",">>");
        initU();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){
            Handler handler= new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PersistData.setIntData(getContext(), AppConstant.FRAGMENTPOSITON,6);


                    for (Category category : db.getCatList()) {
                        allCategoryID += category.getId() + "+";
                    }

                    if (allCategoryID.length() > 0) {
                        allCategoryID = allCategoryID.substring(0, allCategoryID.length() - 1);
                    }


                    if (allCategoryID.length() > 0) {
                        getNirbachitolist(AllURL.getNirbachitoList(allCategoryID, pageNumber));
                    } else {

                        recFvoList.setVisibility(View.GONE);
                        emptyFv.setVisibility(View.VISIBLE);
                    }


                }
            },100);
        }
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    private void initU() {

        mFragmentManager = getActivity().getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        progressNirbachito = (ProgressBar) getView().findViewById(R.id.progressNirbachito);

        final Typeface face_reg = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
        final Typeface face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");

        selectBtniv = (ImageView) getView().findViewById(R.id.selectBtniv);
        // progressNirbachito = (ProgressBar) getView().findViewById(R.id.progressNirbachito);
        emptyFv = (LinearLayout) getView().findViewById(R.id.emptyFv);
        tvLike = (TextView) getView().findViewById(R.id.tvLike);
        tvTitle1 = (TextView) getView().findViewById(R.id.tvTitle1);
        tvTitle2 = (TextView) getView().findViewById(R.id.tvTitle2);
        startBtn = (TextView) getView().findViewById(R.id.startBtn);
        recFvoList = (RecyclerView) getView().findViewById(R.id.recFvoList);


        myFvLayoutManager = new LinearLayoutManager(con);
        recFvoList.setLayoutManager(myFvLayoutManager);


        recFvoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //slide_up for scroll down
                {
                    visibleItemCount = myFvLayoutManager.getChildCount();
                    totalItemCount = myFvLayoutManager.getItemCount();
                    pastVisiblesItems = myFvLayoutManager.findLastVisibleItemPosition();
                    pageNumber = pageNumber + 1;
                    if (hasMorePage()) {
                        if ((pastVisiblesItems) >= totalItemCount - AppConstant.scroolBeforeLatItem) {
                            getNirbachitolist(AllURL.getNirbachitoList(allCategoryID, pageNumber));

                        }
                    }
                }
            }
        });


        dividerDrawable = ContextCompat.getDrawable(con, R.drawable.divider);
        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
        recFvoList.addItemDecoration(dividerItemDecoration);

        tvLike.setTypeface(face_bold);
        tvTitle1.setTypeface(face_reg);
        tvTitle2.setTypeface(face_reg);
        startBtn.setTypeface(face_reg);


        for (Category category : db.getCatList()) {
            allCategoryID += category.getId() + "+";
        }


        if (allCategoryID.length() > 0) {
            allCategoryID = allCategoryID.substring(0, allCategoryID.length() - 1);
        }

        Log.e("allCategoryID",">>"+allCategoryID);

        if (allCategoryID.length() > 0) {
            getNirbachitolist(AllURL.getNirbachitoList(allCategoryID, pageNumber));
        } else {

            recFvoList.setVisibility(View.GONE);
            emptyFv.setVisibility(View.VISIBLE);
        }

        selectBtniv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", 7);
                fragment.setArguments(bundle);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();

            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", 7);
                fragment.setArguments(bundle);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
            }
        });

    }

    private void getNirbachitolist(final String url) {
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
            return;
        }
        Log.e("URL : ", url);

        progressNirbachito.setVisibility(View.VISIBLE);
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

                        progressNirbachito.setVisibility(View.GONE);

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();
                                allnirbahito = g.fromJson(new String(response), AllNirbahito.class);

                                if (allnirbahito.getStatus().equalsIgnoreCase("1")) {

                                    my_newsListTemp.addAll(allnirbahito.getMy_news());

                                    if (my_newsListTemp.size() > 0) {

                                        recFvoList.setVisibility(View.VISIBLE);
                                        emptyFv.setVisibility(View.GONE);
                                        myAdapter = new MyFvRecyAdapterList(con, my_newsListTemp, null);
                                        recFvoList.setAdapter(myAdapter);
                                        myAdapter.notifyDataSetChanged();

                                    } else {

                                        recFvoList.setVisibility(View.GONE);
                                        emptyFv.setVisibility(View.VISIBLE);

                                    }
                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            progressNirbachito.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private boolean hasMorePage() {
        if (allnirbahito.getPaginator() != null) {
            if (TextUtils.isDigitsOnly("" + allnirbahito.getPaginator().getPageCount())) {
                totalpage = allnirbahito.getPaginator().getPageCount();
            } else {
                totalpage = 1;
            }
            int currentPageCount = Integer.parseInt(allnirbahito.getPaginator().getPage());

            if (currentPageCount < totalpage) {
                return true;
            }
        } else {
            return false;
        }

        return false;
    }
}
