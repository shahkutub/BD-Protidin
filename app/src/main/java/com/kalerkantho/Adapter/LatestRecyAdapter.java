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
import android.widget.TextView;

import com.bangladesh_pratidin.R;
import com.bumptech.glide.Glide;
import com.kalerkantho.DetailsActivity;
import com.kalerkantho.Model.OnItemClickListenerNews;
import com.kalerkantho.holder.AllCommonNewsItem;

import java.util.ArrayList;
import java.util.List;

public class LatestRecyAdapter extends RecyclerView.Adapter<LatestRecyAdapter.MyViewHolder> {
    Context context;
    private final OnItemClickListenerNews listener;
    private List<AllCommonNewsItem> latestList = new ArrayList<AllCommonNewsItem>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView commonImage;
        TextView commonTitle;
        TextView commonDateTime;
        TextView commonCategory;
        TextView numberingText;



        public MyViewHolder(View view) {
            super(view);

            commonImage = (ImageView) view.findViewById(R.id.imag_comm);
            commonTitle = (TextView) view.findViewById(R.id.commonTitle);
            commonDateTime = (TextView) view.findViewById(R.id.comDateTime);
            commonCategory = (TextView) view.findViewById(R.id.common_cat);
            numberingText = (TextView) view.findViewById(R.id.numberingText);
        }

        public void bind(final AllCommonNewsItem item, final OnItemClickListenerNews listener) {

            final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");
            final Typeface face_bold = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_Bold.ttf");

            if (!(TextUtils.isEmpty(item.getNews_obj().getImage()))) {
                Glide.with(context).load(item.getNews_obj().getImage()).placeholder(R.drawable.defaulticon).into(commonImage);
            } else {
                Glide.with(context).load(R.drawable.defaulticon).placeholder(R.drawable.defaulticon).into(commonImage);
            }

            if (!TextUtils.isEmpty(item.getNews_obj().getTitle())) {
                commonTitle.setText(item.getNews_obj().getTitle());

            } else {
                commonTitle.setText("");
            }

            if (!TextUtils.isEmpty(item.getNews_obj().getBanglaDateString())){
                commonDateTime.setText(item.getNews_obj().getBanglaDateString());
            }else{
                commonDateTime.setText("");
            }

            if (!TextUtils.isEmpty(item.getNews_obj().getCategory_name())){
                commonCategory.setText(item.getNews_obj().getCategory_name());
            }else{
                commonCategory.setText("");

            }

            numberingText.setVisibility(View.GONE);

            commonTitle.setTypeface(face_bold);
            commonDateTime.setTypeface(face_reg);
            commonCategory.setTypeface(face_reg);


        }
    }
    public LatestRecyAdapter(Context context,List<AllCommonNewsItem> latestList,OnItemClickListenerNews listener) {
        this.context = context;
        this.listener = listener;
        this.latestList = latestList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_latest_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

         holder.bind(latestList.get(position), listener);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = latestList.get(position).getNews_obj().getId();
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("content_id",id);
                i.putExtra("is_favrt","0");
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return latestList.size();
    }
}
