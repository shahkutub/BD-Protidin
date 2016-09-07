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
import com.kalerkantho.Model.OnItemClickListenerNews;

import java.util.ArrayList;
import java.util.List;

public class MyFvRecyAdapterList extends RecyclerView.Adapter<MyFvRecyAdapterList.MyViewHolder> {
    Context context;
    private final OnItemClickListenerNews listener;
    private List<CommonNewsItem> listData = new ArrayList<CommonNewsItem>();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView commonImage;
        TextView commonTitle;
        TextView commonDateTime;
        TextView commonCategory;
        View divderView;
        LinearLayout commonView;


        public MyViewHolder(View view) {
            super(view);
            commonView = (LinearLayout) view.findViewById(R.id.commonView);
            divderView = (View) view.findViewById(R.id.divderView);
            commonImage = (ImageView) view.findViewById(R.id.imag_comm);
            commonTitle = (TextView) view.findViewById(R.id.commonTitle);
            commonDateTime = (TextView) view.findViewById(R.id.comDateTime);
            commonCategory = (TextView) view.findViewById(R.id.common_cat);

        }

        public void bind(final CommonNewsItem item, final OnItemClickListenerNews listener) {

            final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");

            if (!(TextUtils.isEmpty(item.getImage()))) {
                Glide.with(context).load(item.getImage()).placeholder(R.drawable.defaulticon).into(commonImage);
            } else {
                Glide.with(context).load(R.drawable.defaulticon).placeholder(R.drawable.defaulticon).into(commonImage);
            }

            if (!TextUtils.isEmpty(item.getTitle())) {
                commonTitle.setText(item.getTitle());

            } else {
              commonTitle.setText("");
            }

            if (!TextUtils.isEmpty(item.getBanglaDateString())){
                commonDateTime.setText(item.getBanglaDateString());
            }else{
                commonDateTime.setText("");
            }

            if (!TextUtils.isEmpty(item.getCategory_name())){
               commonCategory.setText(item.getCategory_name());
            }else{
                commonCategory.setText("");
                divderView.setVisibility(View.GONE);
            }



            commonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String id = item.getId();
                    Intent i = new Intent(context, DetailsActivity.class);
                    i.putExtra("content_id",id);
                    i.putExtra("is_favrt","0");
                    context.startActivity(i);



                }
            });

            commonTitle.setTypeface(face_reg);
            commonDateTime.setTypeface(face_reg);
            commonCategory.setTypeface(face_reg);




        }
    }
    public MyFvRecyAdapterList(Context context, List<CommonNewsItem> listData,OnItemClickListenerNews listener) {
        this.context = context;
        this.listener = listener;
        this.listData = listData;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_common_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

         holder.bind(listData.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
