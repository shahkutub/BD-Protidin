package com.kalerkantho.Adapter;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kalerkantho.Dialog.CatListDialogFragment;
import com.kalerkantho.Model.Category;
import com.kalerkantho.Model.OnItemClickListener;
import com.kalerkantho.R;
import com.kalerkantho.Utils.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class MenuRecyAdapter extends RecyclerView.Adapter<MenuRecyAdapter.MyViewHolder> {
    Activity context;
    List<Category> printList= new ArrayList<Category>();
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout allViewBtn;
        TextView menu;

        public MyViewHolder(View view) {
            super(view);
            allViewBtn = (LinearLayout) view.findViewById(R.id.allViewBtn);
            menu = (TextView) view.findViewById(R.id.headlist);
        }

        public void bind(final Category item, final OnItemClickListener listener) {

            final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");

            if (!TextUtils.isEmpty(item.getName())){
              menu.setText(item.getName());
            }else{
                menu.setText("");
            }

           menu.setTypeface(face_reg);

            allViewBtn.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {

                    AppConstant.CATEGORYTYPE = item.getId();
                    AppConstant.CATEGORYTITLE= item.getName();

                    Log.e("Category Type",""+ AppConstant.CATEGORYTYPE );
                    CatListDialogFragment dialogCatList= new CatListDialogFragment();
                    dialogCatList.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                    dialogCatList.show(context.getFragmentManager(), "");

                }
            });
        }

    }

    public MenuRecyAdapter(Activity context, List<Category> printList,OnItemClickListener listener) {
        this.context = context;
        this.printList = printList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.bind(printList.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return printList.size();
    }

}
