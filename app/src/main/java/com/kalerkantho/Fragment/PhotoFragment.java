package com.kalerkantho.Fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aapbd.utils.storage.PersistData;
import com.google.gson.Gson;
import com.kalerkantho.Adapter.LatestRecyAdapter;
import com.kalerkantho.Adapter.PhotoRecyAdapter;
import com.kalerkantho.Model.CommonNewsItem;
import com.kalerkantho.R;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.GridSpacingItemDecoration;
import com.kalerkantho.holder.AllCommonNewsItem;
import com.kalerkantho.holder.AllNewsObj;

import java.util.ArrayList;
import java.util.List;


public class PhotoFragment extends Fragment {
    private Context con;
    Drawable dividerDrawable;
    private RecyclerView photoGalleryList;
    private PhotoRecyAdapter pAdapter;
    private AllNewsObj allObj;
    private List<AllCommonNewsItem> allPhotolist = new ArrayList<AllCommonNewsItem>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photogellary,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        intiU();
    }

    private void intiU() {

        Gson g = new Gson();
        allObj = g.fromJson(PersistData.getStringData(con, AppConstant.HOMERESPONSE),AllNewsObj.class);

        if (allPhotolist!=null)
         allPhotolist.clear();

        for(CommonNewsItem topNews:allObj.getPhoto_gallery())
        {
            AllCommonNewsItem singleObj=new AllCommonNewsItem();
            singleObj.setNews_obj(topNews);
            allPhotolist.add(singleObj);

        }


        final Typeface face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");
        photoGalleryList = (RecyclerView) getView().findViewById(R.id.photoGalleryList);


        photoGalleryList.setLayoutManager(new GridLayoutManager(con, 3));
        GridSpacingItemDecoration itemDecoration = new GridSpacingItemDecoration(con, R.dimen.spacepad);
        photoGalleryList.addItemDecoration(itemDecoration);


        pAdapter = new PhotoRecyAdapter(getActivity(),allPhotolist,null);
        photoGalleryList.setAdapter(pAdapter);



    }

}
