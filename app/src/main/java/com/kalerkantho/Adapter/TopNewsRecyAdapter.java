package com.kalerkantho.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kalerkantho.DetailsActivity;
import com.kalerkantho.R;
import com.kalerkantho.holder.AllCommonNewsItem;

import java.util.ArrayList;
import java.util.List;

public class TopNewsRecyAdapter extends RecyclerView.Adapter<TopNewsRecyAdapter.MyViewHolder> {
    Context context;
    List<AllCommonNewsItem> topnews = new ArrayList<AllCommonNewsItem>();
    public static final int dataOne = 1;
    public static final int dataTwo = 2;

    public TopNewsRecyAdapter(Context context, List<AllCommonNewsItem> topnews) {
        this.context = context;
        this.topnews = topnews;

    }

    @Override
    public int getItemViewType(int position) {

        if(topnews.get(position).getType().equalsIgnoreCase("fullscreen"))
        {
            position=1;

        }else if(topnews.get(position).getType().equalsIgnoreCase("defaultscreen"))
        {
            position=2;

        }

        return position;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 1:
                View viewONE = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_first_item, parent, false);
                DataOne rowONE = new DataOne(viewONE);
                return rowONE;

            case 2:
                View viewTWO = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_common_item, parent, false);
                DataTwo rowTWO = new DataTwo(viewTWO);
                return rowTWO;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       final AllCommonNewsItem newsitem = topnews.get(position);

        final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");
        final Typeface face_bold = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_Bold.ttf");

        if (holder.getItemViewType() == dataOne){

            DataOne firstHolder = (DataOne)holder;

            if (!(TextUtils.isEmpty(newsitem.getNews_obj().getImage()))) {
                Glide.with(context).load(newsitem.getNews_obj().getImage()).placeholder(R.drawable.fullscreen).into(firstHolder.fullScreen);
            } else {
                Glide.with(context).load(R.drawable.fullscreen).placeholder(R.drawable.fullscreen).into(firstHolder.fullScreen);
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

            firstHolder.fullview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id = newsitem.getNews_obj().getId();
                    Intent i = new Intent(context, DetailsActivity.class);
                    i.putExtra("content_id",id);
                    i.putExtra("is_favrt","0");
                    context.startActivity(i);

                }
            });

            firstHolder.titleFullScreen.setTypeface(face_bold);
            firstHolder.datetiemFullScreen.setTypeface(face_reg);
            firstHolder.categoryTitle.setTypeface(face_reg);

        }else if(holder.getItemViewType() == dataTwo){

            DataTwo commonHolder = (DataTwo)holder;

            if (!(TextUtils.isEmpty(newsitem.getNews_obj().getImage()))) {
                Glide.with(context).load(newsitem.getNews_obj().getImage()).placeholder(R.drawable.defaulticon).into(commonHolder.commonImage);
            } else {
                Glide.with(context).load(R.drawable.defaulticon).placeholder(R.drawable.defaulticon).into(commonHolder.commonImage);
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

            commonHolder.commonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String id = newsitem.getNews_obj().getId();
                    Intent i = new Intent(context, DetailsActivity.class);
                    i.putExtra("content_id",id);
                    i.putExtra("is_favrt","0");
                    context.startActivity(i);

                }
            });

            commonHolder.commonTitle.setTypeface(face_bold);
            commonHolder.commonDateTime.setTypeface(face_reg);
            commonHolder.commonCategory.setTypeface(face_reg);

        }

     /*   holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = newsitem.getNews_obj().getId();
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("content_id",id);
                i.putExtra("is_favrt","0");
                context.startActivity(i);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return (null != topnews ? topnews.size() : 0 / 0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View view) {
            super(view);
        }
    }

    public class DataOne extends MyViewHolder {
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
            fullview = (LinearLayout) v.findViewById(R.id.fullview);
        }
    }

    public class DataTwo extends MyViewHolder {
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
}
