package com.kalerkantho.Adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangladesh_pratidin.R;
import com.bumptech.glide.Glide;
import com.kalerkantho.DetailsActivity;
import com.kalerkantho.Dialog.CatListDialogFragment;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.holder.AllCommonNewsItem;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {
    List<AllCommonNewsItem> newslist = new ArrayList<AllCommonNewsItem>();
    HorizontalRecyAdapter hAdapter;
    private  String id = "";
//
    private Activity mContext;
    public static final int dataOne = 1;
    public static final int dataTwo = 2;
    public static final int dataThree = 3;
    public static final int dataFour = 4;

    public RecyclerAdapter(Activity context, List<AllCommonNewsItem> newslist) {
        this.newslist = newslist;
        this.mContext = context;
        id = "";

    }

    @Override
    public int getItemViewType(int position) {

       if(newslist.get(position).getType().equalsIgnoreCase("fullscreen"))
        {
            position=1;

        }else if(newslist.get(position).getType().equalsIgnoreCase("defaultscreen"))
        {
            position=2;

        }else if(newslist.get(position).getType().equalsIgnoreCase("titleshow"))
        {
            position=3;


        }else if(newslist.get(position).getType().equalsIgnoreCase("horizontal"))
        {
            position=4;

        }

        return position;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View viewONE = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_first_item, parent, false);
                DataOne rowONE = new DataOne(viewONE);
                return rowONE;

            case 2:
                View viewTWO = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_common_item, parent, false);
                DataTwo rowTWO = new DataTwo(viewTWO);
                return rowTWO;

            case 3:
                View viewTHREE = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_headertilte, parent, false);
                DataThree rowTHREE = new DataThree(viewTHREE);
                return rowTHREE;
            case 4:
                View viewFour = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_horizontallist_item, parent, false);
                DataFour rowFour = new DataFour(viewFour);
                return rowFour;

        }
        return null;
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        final AllCommonNewsItem newsitem = newslist.get(position);
        id = "";
        final Typeface face_reg = Typeface.createFromAsset(mContext.getAssets(), "fonts/SolaimanLipi_reg.ttf");
        final Typeface face_bold = Typeface.createFromAsset(mContext.getAssets(), "fonts/SolaimanLipi_Bold.ttf");

        if (holder.getItemViewType() == dataOne){
            DataOne firstHolder = (DataOne)holder;


            if (!(TextUtils.isEmpty(newsitem.getNews_obj().getImage()))) {
                Glide.with(mContext).load(newsitem.getNews_obj().getImage()).placeholder(R.drawable.fullscreen).into(firstHolder.fullScreen);
            } else {
                Glide.with(mContext).load(R.drawable.fullscreen).placeholder(R.drawable.fullscreen).into(firstHolder.fullScreen);
            }
            if (!TextUtils.isEmpty(newsitem.getNews_obj().getTitle())) {
                firstHolder.titleFullScreen.setText(newsitem.getNews_obj().getTitle());

            } else {
                firstHolder.titleFullScreen.setText("");
            }

            if (!TextUtils.isEmpty(newsitem.getNews_obj().getBanglaDateString())){
                firstHolder.datetiemFullScreen.setText(newsitem.getNews_obj().getBanglaDateString());
            }else{
                firstHolder.datetiemFullScreen.setText("");
            }

            if (!TextUtils.isEmpty(newsitem.getNews_obj().getCategory_name())){
                firstHolder.categoryTitle.setText(newsitem.getNews_obj().getCategory_name());

            }else{
                firstHolder.categoryTitle.setText("");
            }


            firstHolder.categoryTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppConstant.CATEGORYTYPE = newsitem.getNews_obj().getCategory();
                    AppConstant.CATEGORYTITLE= newsitem.getNews_obj().getCategory_name();
                    Log.e("Category Type",""+ AppConstant.CATEGORYTYPE );
                    CatListDialogFragment dialogCatList= new CatListDialogFragment();
                    dialogCatList.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                    dialogCatList.show(mContext.getFragmentManager(), "");

                }
            });




            firstHolder.titleFullScreen.setTypeface(face_bold);
            firstHolder.datetiemFullScreen.setTypeface(face_reg);
            firstHolder.categoryTitle.setTypeface(face_reg);

            firstHolder.fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("DDD"," here  "+id);
                    id = newsitem.getNews_obj().getId();
                    Intent i = new Intent(mContext, DetailsActivity.class);
                    i.putExtra("content_id",id);
                    i.putExtra("is_favrt","0");
                    mContext.startActivity(i);

                }
            });

        }else if(holder.getItemViewType() == dataTwo){
            DataTwo commonHolder = (DataTwo)holder;

            if (!(TextUtils.isEmpty(newsitem.getNews_obj().getImage()))) {
                Glide.with(mContext).load(newsitem.getNews_obj().getImage()).placeholder(R.drawable.defaulticon).into(commonHolder.commonImage);
            } else {
                Glide.with(mContext).load(R.drawable.defaulticon).placeholder(R.drawable.defaulticon).into(commonHolder.commonImage);
            }

            if (!TextUtils.isEmpty(newsitem.getNews_obj().getTitle())) {
                commonHolder.commonTitle.setText(newsitem.getNews_obj().getTitle());

            } else {
                commonHolder.commonTitle.setText("");
            }

            if (!TextUtils.isEmpty(newsitem.getNews_obj().getBanglaDateString())){
                commonHolder.commonDateTime.setText(newsitem.getNews_obj().getBanglaDateString());
            }else{
                commonHolder.commonDateTime.setText("");
            }

            if (!TextUtils.isEmpty(newsitem.getNews_obj().getCategory_name())){
                commonHolder.commonCategory.setText(newsitem.getNews_obj().getCategory_name());
            }else{
                commonHolder.commonCategory.setText("");
                commonHolder.divderView.setVisibility(View.GONE);
            }


            commonHolder.commonCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.CATEGORYTYPE = newsitem.getNews_obj().getCategory();
                    AppConstant.CATEGORYTITLE= newsitem.getNews_obj().getCategory_name();
                    Log.e("Category Type",""+ AppConstant.CATEGORYTYPE );
                    CatListDialogFragment dialogCatList= new CatListDialogFragment();
                    dialogCatList.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                    dialogCatList.show(mContext.getFragmentManager(), "");
                }
            });


            commonHolder.commonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("DDD"," here  "+id);
                    id = newsitem.getNews_obj().getId();
                    Intent i = new Intent(mContext, DetailsActivity.class);
                    i.putExtra("content_id",id);
                    i.putExtra("is_favrt","0");
                    mContext.startActivity(i);


                }
            });

            commonHolder.commonTitle.setTypeface(face_bold);
            commonHolder.commonDateTime.setTypeface(face_reg);
            commonHolder.commonCategory.setTypeface(face_reg);

        }else if(holder.getItemViewType() == dataThree){

            DataThree headerHolder = (DataThree)holder;

            if(!TextUtils.isEmpty(newsitem.getCategory_title())){

                headerHolder.headerTitle.setText(newsitem.getCategory_title());
            }else{
                headerHolder.headerTitle.setText("");
            }


            headerHolder.headerDetailsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppConstant.CATEGORYTYPE = newsitem.getCategory_id();
                    AppConstant.CATEGORYTITLE= newsitem.getCategory_title();

                    Log.e("Category Type",""+ AppConstant.CATEGORYTYPE );
                    CatListDialogFragment dialogCatList= new CatListDialogFragment();
                    dialogCatList.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                    dialogCatList.show(mContext.getFragmentManager(), "");


                }
            });


            headerHolder.headerDetailsBtn.setTypeface(face_reg);
            headerHolder.headerTitle.setTypeface(face_bold);


        }else if(holder.getItemViewType() == dataFour){
            DataFour hListHolder = (DataFour)holder;
            AllCommonNewsItem newsitem1=newslist.get(position);

            LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            hListHolder.horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);
            hAdapter = new HorizontalRecyAdapter(mContext,newsitem1.getList_news_obj());
            hListHolder.horizontal_recycler_view.setAdapter(hAdapter);
            hAdapter.notifyDataSetChanged();
        }



//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return (null != newslist ? newslist.size() : 0 / 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        public CustomViewHolder(View view) {
            super(view);
        }
    }

    public class DataOne extends CustomViewHolder {
        ImageView fullScreen;
        TextView titleFullScreen;
        TextView datetiemFullScreen;
        TextView categoryTitle;
        LinearLayout fullview;


        public DataOne(View v) {
            super(v);
            fullScreen = (ImageView) v.findViewById(R.id.img_commonnews);
            titleFullScreen = (TextView)v.findViewById(R.id.tvTitleComonnews);
            datetiemFullScreen = (TextView) v.findViewById(R.id.tvDatetime);
            categoryTitle = (TextView) v.findViewById(R.id.cat_type);
            fullview=(LinearLayout)v.findViewById(R.id.fullview);
        }
    }

    public class DataTwo extends CustomViewHolder {
        ImageView commonImage;
        TextView commonTitle;
        TextView commonDateTime;
        TextView commonCategory;
        LinearLayout commonView;
        View divderView;

        public DataTwo(View v) {
            super(v);
            divderView = (View) v.findViewById(R.id.divderView);
            commonImage = (ImageView) v.findViewById(R.id.imag_comm);
            commonTitle = (TextView) v.findViewById(R.id.commonTitle);
            commonDateTime = (TextView) v.findViewById(R.id.comDateTime);
            commonCategory = (TextView) v.findViewById(R.id.common_cat);
            commonView = (LinearLayout) v.findViewById(R.id.commonView);
        }
    }

    public class DataThree extends CustomViewHolder {

        TextView headerTitle;
        TextView headerDetailsBtn;

        public DataThree(View v) {
            super(v);
            headerTitle = (TextView) v.findViewById(R.id.headerTitle);
            headerDetailsBtn = (TextView) v.findViewById(R.id.detailsBtn);
        }
    }

    public class DataFour extends CustomViewHolder {
       private RecyclerView horizontal_recycler_view;
        public DataFour(View v) {
            super(v);
            horizontal_recycler_view = (RecyclerView) v.findViewById(R.id.horizontal_recycler_view);
        }
    }
}