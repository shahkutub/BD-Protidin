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

import com.bumptech.glide.Glide;
import com.kalerkantho.DetailsActivity;
import com.kalerkantho.Model.CommentInfo;
import com.kalerkantho.Model.CommonNewsItem;
import com.kalerkantho.Model.OnItemClickListenerNews;
import com.kalerkantho.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 9/1/2016.
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {
    Context context;
    private List<CommentInfo> my_newsList = new ArrayList<CommentInfo>();
    private final OnItemClickListenerNews listener;

    public CommentListAdapter(Context context, List<CommentInfo> my_newsList, OnItemClickListenerNews listener) {
        this.context = context;
        this.my_newsList = my_newsList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_row, parent, false);
        return new MyViewHolder(itemView);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameCom,tvCommentDetails,tvDateCom;

        View divderView;

        public MyViewHolder(View view) {
            super(view);

            divderView = (View) view.findViewById(R.id.divderView);

            tvCommentDetails = (TextView) view.findViewById(R.id.tvCommentDetails);
            tvNameCom = (TextView) view.findViewById(R.id.tvNameCom);
            tvDateCom = (TextView) view.findViewById(R.id.tvDateCom);
        }

        public void bind(final CommentInfo item, final OnItemClickListenerNews listener) {

            final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");
            final Typeface face_bold = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_Bold.ttf");

            tvCommentDetails.setTypeface(face_reg);
            tvNameCom.setTypeface(face_reg);
            tvDateCom.setTypeface(face_reg);

            if (!TextUtils.isEmpty(item.getFull_name())) {
                tvNameCom.setText(item.getFull_name());

            } else {
                tvNameCom.setText("");
            }

            if (!TextUtils.isEmpty(item.getComment_text())){
                tvCommentDetails.setText(item.getComment_text());
            }else{
                tvCommentDetails.setText("");
            }

            if (!TextUtils.isEmpty(item.getBanglaDateString())){
                tvDateCom.setText(item.getBanglaDateString());
            }else{
                tvDateCom.setText("");
            }
        }
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {

        holder.bind(my_newsList.get(position), listener);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                String id = my_newsList.get(position).getId();
//                Intent i = new Intent(context, DetailsActivity.class);
////                i.putExtra("content_id",id);
//                i.putExtra("is_favrt","0");
//                context.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return my_newsList.size();
    }
}
