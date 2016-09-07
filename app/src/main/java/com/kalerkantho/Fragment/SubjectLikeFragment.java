package com.kalerkantho.Fragment;

import android.content.Context;
import android.graphics.Typeface;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aapbd.utils.storage.PersistData;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kalerkantho.Adapter.AllOnlineRecyAdapter;
import com.kalerkantho.Adapter.MyFvRecyAdapter;
import com.kalerkantho.Model.Category;
import com.kalerkantho.MyDb.MyDBHandler;
import com.kalerkantho.R;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.DividerItemDecoration;
import com.kalerkantho.holder.AllCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ratan on 7/29/2015.
 */
public class SubjectLikeFragment extends Fragment {
    private Context con;
    private RecyclerView onlineAllMenuList,myFvList;
    private AllOnlineRecyAdapter mAdapter;
    private LinearLayout listChooseBtn,selectSubjectBtn;
    private View listChooseView,selectSubjectView;
    private TextView listChooseText,selectSubjectText;
    private MyDBHandler db;


    private LinearLayoutManager onlineLayoutManager;
    List<Category> onlineList= new ArrayList<Category>();
    AllCategory allCategory;
    Drawable dividerDrawable;

    private LinearLayoutManager myFvLayoutManager;
    private MyFvRecyAdapter myAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.subjectlikefragment,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();

        db = new MyDBHandler(con);
        intiU();
    }

    private void intiU() {


        final Typeface face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");


        try {
            Gson g = new Gson();
            if(!(TextUtils.isEmpty(PersistData.getStringData(con, AppConstant.CATEGORY_RESPONSE)))){

                allCategory = g.fromJson(PersistData.getStringData(con, AppConstant.CATEGORY_RESPONSE),AllCategory.class);

                listChooseBtn = (LinearLayout) getView().findViewById(R.id.listChooseBtn);
                selectSubjectBtn = (LinearLayout) getView().findViewById(R.id.selectSubjectBtn);
                listChooseView = (View) getView().findViewById(R.id.listChooseView);
                selectSubjectView = (View) getView().findViewById(R.id.selectSubjectView);
                listChooseText = (TextView) getView().findViewById(R.id.listChooseText);
                selectSubjectText = (TextView) getView().findViewById(R.id.selectSubjectText);

                onlineAllMenuList = (RecyclerView) getView().findViewById(R.id.onlineAllMenuList);
                myFvList = (RecyclerView) getView().findViewById(R.id.myFvList);

                onlineLayoutManager = new LinearLayoutManager(con);
                onlineAllMenuList.setLayoutManager(onlineLayoutManager);

                myFvLayoutManager = new LinearLayoutManager(con);
                myFvList.setLayoutManager(myFvLayoutManager);

                if (onlineList!=null)
                    onlineList.clear();

                dividerDrawable = ContextCompat.getDrawable(con, R.drawable.lineee);
                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                onlineAllMenuList.addItemDecoration(dividerItemDecoration);
                myFvList.addItemDecoration(dividerItemDecoration);


                for (int i=0;i<allCategory.getCategory_list().size();i++){
                    if (allCategory.getCategory_list().get(i).getM_type().equalsIgnoreCase("online")){
                        onlineList.add(allCategory.getCategory_list().get(i));
                    }
                }

                mAdapter = new AllOnlineRecyAdapter(con, onlineList,null);
                onlineAllMenuList.setAdapter(mAdapter);

                listChooseText.setTypeface(face_bold);
                selectSubjectText.setTypeface(face_bold);


                listChooseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        myFvList.setVisibility(View.VISIBLE);
                        onlineAllMenuList.setVisibility(View.GONE);
                        listChooseText.setTextColor(ContextCompat.getColor(con,R.color.kalerkontho_actionbar));
                        selectSubjectText.setTextColor(ContextCompat.getColor(con,R.color.black));
                        listChooseView.setVisibility(View.VISIBLE);
                        selectSubjectView.setVisibility(View.GONE);

                        myAdapter =new MyFvRecyAdapter(con,null);
                        myFvList.setAdapter(myAdapter);
                        myAdapter.notifyDataSetChanged();

                    }
                });

                selectSubjectBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onlineAllMenuList.setVisibility(View.VISIBLE);
                        myFvList.setVisibility(View.GONE);
                        selectSubjectText.setTextColor(ContextCompat.getColor(con,R.color.kalerkontho_actionbar));
                        listChooseText.setTextColor(ContextCompat.getColor(con,R.color.black));
                        selectSubjectView.setVisibility(View.VISIBLE);
                        listChooseView.setVisibility(View.GONE);

                        mAdapter = new AllOnlineRecyAdapter(con, onlineList,null);
                        onlineAllMenuList.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                });

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

                    PersistData.setIntData(getContext(), AppConstant.FRAGMENTPOSITON,7);
                }
            },100);
        }
    }
}
