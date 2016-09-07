package com.kalerkantho.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangladesh_pratidin.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kalerkantho.DetailsActivity;
import com.kalerkantho.Model.DetailsModel;
import com.kalerkantho.Model.FvrtModel;
import com.kalerkantho.Model.OnItemClickListenerNews;
import com.kalerkantho.MyDb.MyDBHandler;
import com.kalerkantho.holder.AllPhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AppBd on 8/30/2016.
 */
public class FavrtRecycleAdapter  extends RecyclerView.Adapter<FavrtRecycleAdapter.MyViewHolder> {
    Activity context;
    private final OnItemClickListenerNews listener;
    private List<DetailsModel> photoList = new ArrayList<DetailsModel>();
    private AllPhoto allPhoto;
    private MyDBHandler db;
    private FvrtModel fm = new FvrtModel();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photo,fvrtBtn;
        TextView heading,category,dateTime;

        public MyViewHolder(View view) {
            super(view);

            photo = (ImageView) view.findViewById(R.id.imgFvrt);
            fvrtBtn = (ImageView) view.findViewById(R.id.fvrtBtn);
            heading = (TextView) view.findViewById(R.id.fvrtTxtTitle);
            category = (TextView) view.findViewById(R.id.fvrtCategory);
            dateTime = (TextView) view.findViewById(R.id.dateFvrt);

        }

        public void bind(final DetailsModel item, final OnItemClickListenerNews listener) {

            try {
                final Typeface face_reg = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_reg.ttf");
                final Typeface face_bold = Typeface.createFromAsset(context.getAssets(), "fonts/SolaimanLipi_Bold.ttf");

                if (!(TextUtils.isEmpty(item.getNews().getImage()))) {
                    Glide.with(context).load(item.getNews().getImage()).placeholder(R.drawable.defaulticon).into(photo);
                } else {
                    Glide.with(context).load(R.drawable.defaulticon).placeholder(R.drawable.defaulticon).into(photo);
                }
                //item.getNews()
                heading.setText(item.getNews().getTitle());
                category.setText(item.getNews().getCategory_name());
                dateTime.setText(item.getNews().getBanglaDateString());
                fvrtBtn.setBackgroundResource(R.drawable.fav_off);

                heading.setTypeface(face_bold);
                category.setTypeface(face_reg);
                dateTime.setTypeface(face_reg);
                fvrtBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        boolean isFvrt = false;
                        for (FvrtModel fm : db.getAllFvrtModels()) {
                            if (item.getNews().getId().equalsIgnoreCase(fm.getFvrtId())) {
                                Log.e("get compared:", "true");
                                isFvrt = true;
                                break;
                            }
                        }

                        if (!isFvrt) { // not added before

                            Gson gson = new Gson();
                            String favObject = gson.toJson(item);
                            //Log.e("to json:",favObject);
                            fm.setFvrtId(item.getNews().getId());
                            fm.setFvrtObject(favObject);
                            db.addFavrtEntry(fm);
                            fvrtBtn.setBackgroundResource(R.drawable.fav_off);
                           /* fm.setFvrtId(content_id);
                            Gson gson = new Gson();
                            String favObject = gson.toJson(allDetail);
                            //Log.e("to json:",favObject);
                            fm.setFvrtObject(favObject);
                            db.addFavrtEntry(fm);
                            Toast.makeText(con,"Data Added Successfully",Toast.LENGTH_SHORT);*/
//
                        } else {
                            // isFvrt = false;
                            fvrtBtn.setBackgroundResource(R.drawable.fav_on);
                           db.removeSingleFavENtry(item.getNews().getId());
                        }

                        Log.e("Size:",">>"+db.getAllFvrtModels().size());
                       // notifyDataSetChanged();
                    }
                });



            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
    public FavrtRecycleAdapter(Activity context, List<DetailsModel> photoList, OnItemClickListenerNews listener) {
        this.context = context;
        this.listener = listener;
        this.photoList = photoList;
        db = new MyDBHandler(this.context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favrt, parent, false);
        return new MyViewHolder(itemView);
    }
//
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.bind(photoList.get(position), listener);
       // holder.bind();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = photoList.get(position).getNews().getId();
                Intent i = new Intent(context, DetailsActivity.class);
                i.putExtra("content_id",id);
                i.putExtra("is_favrt","1");
                context.startActivity(i);

                    context.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }




}
