package com.kalerkantho.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bangladesh_pratidin.R;
import com.kalerkantho.Model.Category;
import com.kalerkantho.Model.OnItemClickListener;
import com.kalerkantho.MyDb.MyDBHandler;

import java.util.ArrayList;
import java.util.List;

public class AllOnlineRecyAdapter extends RecyclerView.Adapter<AllOnlineRecyAdapter.MyViewHolder> {
    Context context;
    List<Category> onlineList= new ArrayList<Category>();
    private final OnItemClickListener listener;
    MyDBHandler db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menu;
        RelativeLayout addFavorite;

        public MyViewHolder(View view) {
            super(view);
            menu = (TextView) view.findViewById(R.id.headlist);
            addFavorite = (RelativeLayout) view.findViewById(R.id.addFavorite);

        }

        public void bind(final Category item, final OnItemClickListener listener) {

            final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");

            if (!TextUtils.isEmpty(item.getName())){
                menu.setText(item.getName());
            }else{
                menu.setText("");
            }
            menu.setTypeface(face_reg);


            addFavorite.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    if(db.isAdd(item)){

                        Toast.makeText(context,"Already added",Toast.LENGTH_SHORT).show();

                    }else{

                        if (db.addCat(item)){
                            Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }

    }
    public AllOnlineRecyAdapter(Context context, List<Category> onlineList,OnItemClickListener listener) {
        this.context = context;
        this.onlineList = onlineList;
        this.listener = listener;
        db = new MyDBHandler(context);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {

         holder.bind(onlineList.get(position), listener);
       /* holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = onlineList.get(position).getId();
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("content_id",id);
                i.putExtra("is_favrt","0");
                context.startActivity(i);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return onlineList.size();
    }
}
