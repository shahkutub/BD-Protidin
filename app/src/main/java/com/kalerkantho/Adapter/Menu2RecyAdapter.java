package com.kalerkantho.Adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bangladesh_pratidin.R;
import com.kalerkantho.Dialog.CatListDialogFragment;
import com.kalerkantho.Model.Category;
import com.kalerkantho.Utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class Menu2RecyAdapter extends RecyclerView.Adapter<Menu2RecyAdapter.MyViewHolder> {
    Activity context;
   List<Category> onlineList= new ArrayList<Category>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout allViewBtn;
        TextView menu;

        public MyViewHolder(View view) {
            super(view);
            allViewBtn = (LinearLayout) view.findViewById(R.id.allViewBtn);
            menu = (TextView) view.findViewById(R.id.headlist);
        }
    }
    public Menu2RecyAdapter(Activity context, List<Category> onlineList) {
        this.context = context;
        this.onlineList = onlineList;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");

        if (!TextUtils.isEmpty(onlineList.get(position).getName())){
            holder.menu.setText(onlineList.get(position).getName());
        }else{
            holder.menu.setText("");
        }

        holder.allViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstant.CATEGORYTYPE = onlineList.get(position).getId();
                AppConstant.CATEGORYTITLE= onlineList.get(position).getName();

                Log.e("Category Type",""+ AppConstant.CATEGORYTYPE );
                CatListDialogFragment dialogCatList= new CatListDialogFragment();
                dialogCatList.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogCatList.show(context.getFragmentManager(), "");


            }
        });

        holder.menu.setTypeface(face_reg);
    }

    @Override
    public int getItemCount() {
        return onlineList.size();
    }
}
