package com.kalerkantho.Adapter;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kalerkantho.Model.Category;
import com.kalerkantho.Model.OnItemClickListener;
import com.kalerkantho.MyDb.MyDBHandler;
import com.kalerkantho.R;

import java.util.ArrayList;
import java.util.List;

public class MyFvRecyAdapter extends RecyclerView.Adapter<MyFvRecyAdapter.MyViewHolder> {
    Context context;
    private final OnItemClickListener listener;
    MyDBHandler db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menu;
        ImageView removeFvBtn;

        public MyViewHolder(View view) {
            super(view);
            menu = (TextView) view.findViewById(R.id.headlist);
            removeFvBtn = (ImageView) view.findViewById(R.id.revBtn);
        }

        public void bind(final Category item, final OnItemClickListener listener) {

            final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");

            if (!TextUtils.isEmpty(item.getName())){
                menu.setText(item.getName());
            }else{
                menu.setText("");
            }
            menu.setTypeface(face_reg);

            removeFvBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(db.getCatList().size()>0){
                        db.removeCat(item);
                        notifyDataSetChanged();
                    }else{
                        notifyDataSetChanged();
                    }
                }
            });

        }
    }
    public MyFvRecyAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        db = new MyDBHandler(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

         holder.bind(db.getCatList().get(position), listener);

    }

    @Override
    public int getItemCount() {
        return db.getCatList().size();
    }
}
