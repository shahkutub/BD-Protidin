package com.kalerkantho.Fragment;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aapbd.utils.storage.PersistData;
import com.bangladesh_pratidin.R;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kalerkantho.Adapter.AllNewsRecyAdapter;
import com.kalerkantho.Model.Category;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.DividerItemDecoration;
import com.kalerkantho.holder.AllCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ratan on 7/29/2015.
 */
public class AllnewsFragment extends Fragment {
    private Activity con;
    private RecyclerView allNewsList;
    AllNewsRecyAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    List<Category> onlineList= new ArrayList<Category>();
    AllCategory allCategory;
    Drawable dividerDrawable;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.allnewsfragment,null);
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
            if (!(TextUtils.isEmpty(PersistData.getStringData(con, AppConstant.CATEGORY_RESPONSE)))){

                allCategory = g.fromJson(PersistData.getStringData(con, AppConstant.CATEGORY_RESPONSE),AllCategory.class);
                if (onlineList!=null)
                    onlineList.clear();

                allNewsList = (RecyclerView) getView().findViewById(R.id.allNewsList);
                mLayoutManager = new LinearLayoutManager(con);
                allNewsList.setLayoutManager(mLayoutManager);

                dividerDrawable = ContextCompat.getDrawable(con, R.drawable.lineee);
                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                allNewsList.addItemDecoration(dividerItemDecoration);

                for (int i=0;i<allCategory.getCategory_list().size();i++){
                    if (allCategory.getCategory_list().get(i).getM_type().equalsIgnoreCase("online")){
                        onlineList.add(allCategory.getCategory_list().get(i));
                    }
                }
                mAdapter = new AllNewsRecyAdapter(con, onlineList);
                allNewsList.setAdapter(mAdapter);
            }



        }catch (JsonSyntaxException e){
            e.printStackTrace();
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

                    PersistData.setIntData(getContext(), AppConstant.FRAGMENTPOSITON,8);
                }
            },100);
        }
    }
}
