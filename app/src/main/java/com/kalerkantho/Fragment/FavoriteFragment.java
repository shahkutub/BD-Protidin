package com.kalerkantho.Fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangladesh_pratidin.R;
import com.google.gson.Gson;
import com.kalerkantho.Adapter.FavrtRecycleAdapter;
import com.kalerkantho.Model.DetailsModel;
import com.kalerkantho.Model.FvrtModel;
import com.kalerkantho.MyDb.MyDBHandler;
import com.kalerkantho.holder.AllNewsObj;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AppBd on 8/30/2016.
 */
public class FavoriteFragment extends Fragment {

    private Context con;
    Drawable dividerDrawable;
    private RecyclerView favList;
    private FavrtRecycleAdapter fAdapter;
    private AllNewsObj allObj;
    private List<DetailsModel> allDetailsList = new ArrayList<DetailsModel>();
    private MyDBHandler db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.favorite_design,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        con = getActivity();


        //((AppCompatActivity) con).getSupportActionBar().hide();
        db = new MyDBHandler(con);
        //intiU();
        Log.e("Size::",""+db.getAllFvrtModels().size());
        favList = (RecyclerView) getView().findViewById(R.id.favrtList);
    }

    @Override
    public void onResume() {
        super.onResume();

        for(FvrtModel fm: db.getAllFvrtModels()){
            Log.e("Details",fm.getFvrtObject());
            Gson g = new Gson();
            DetailsModel dm = g.fromJson(new String(fm.getFvrtObject()),DetailsModel.class);
            allDetailsList.add(dm);
        }

        favList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(con,2);
        favList.setLayoutManager(layoutManager);

        fAdapter  = new FavrtRecycleAdapter(getActivity(),allDetailsList,null);
        favList.setAdapter(fAdapter);
    }
}
