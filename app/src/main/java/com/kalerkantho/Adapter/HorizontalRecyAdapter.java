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

import com.bangladesh_pratidin.R;
import com.bumptech.glide.Glide;
import com.kalerkantho.DetailsActivity;
import com.kalerkantho.Model.CommonNewsItem;

import java.util.ArrayList;
import java.util.List;

public class HorizontalRecyAdapter extends RecyclerView.Adapter<HorizontalRecyAdapter.MyViewHolder> {
    Context context;
    List<CommonNewsItem> hlist = new ArrayList<CommonNewsItem>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageHorizontal;
        TextView titleHorizontal;
        LinearLayout horixontalView;

        public MyViewHolder(View view) {
            super(view);
            titleHorizontal = (TextView) view.findViewById(R.id.otersnews_title);
            imageHorizontal = (ImageView) view.findViewById(R.id.others_news_icon);
            horixontalView = (LinearLayout) view.findViewById(R.id.horixontalView);
        }
    }
    public HorizontalRecyAdapter(Context context,List<CommonNewsItem> hlist) {
        this.context = context;
        this.hlist = hlist;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_others_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");

        if (!TextUtils.isEmpty(hlist.get(position).getImage())){
            Glide.with(context).load(hlist.get(position).getImage()).placeholder(R.drawable.defaulticon).into(holder.imageHorizontal);
        }else{
            Glide.with(context).load(R.drawable.defaulticon).placeholder(R.drawable.defaulticon).into(holder.imageHorizontal);
        }

        if (!TextUtils.isEmpty(hlist.get(position).getTitle())){
            holder.titleHorizontal.setText(hlist.get(position).getTitle());
        }else{
            holder.titleHorizontal.setText("");
        }

        holder.titleHorizontal.setTypeface(face_reg);

        holder.horixontalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String id = hlist.get(position).getId();
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("content_id",id);
                i.putExtra("is_favrt","0");
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return hlist.size();
    }
}
