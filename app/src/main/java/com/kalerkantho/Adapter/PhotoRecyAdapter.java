package com.kalerkantho.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aapbd.utils.network.AAPBDHttpClient;
import com.bangladesh_pratidin.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.kalerkantho.Model.CommonNewsItem;
import com.kalerkantho.Model.OnItemClickListenerNews;
import com.kalerkantho.PhotoGallery;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.NetInfo;
import com.kalerkantho.holder.AllCommonNewsItem;
import com.kalerkantho.holder.AllPhoto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class PhotoRecyAdapter extends RecyclerView.Adapter<PhotoRecyAdapter.MyViewHolder> {
    Activity context;
    private final OnItemClickListenerNews listener;
    private List<AllCommonNewsItem> photoList = new ArrayList<AllCommonNewsItem>();
    private List<CommonNewsItem> allPhotoList = new ArrayList<CommonNewsItem>();
    private AllPhoto allPhoto;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;

        public MyViewHolder(View view) {
            super(view);

            photo = (ImageView) view.findViewById(R.id.photo);

        }

        public void bind(final AllCommonNewsItem item, final OnItemClickListenerNews listener) {



            if (!(TextUtils.isEmpty(item.getNews_obj().getImage()))) {
                Glide.with(context).load(item.getNews_obj().getImage()).placeholder(R.drawable.defaulticon).into(photo);
            } else {
                Glide.with(context).load(R.drawable.defaulticon).placeholder(R.drawable.defaulticon).into(photo);
            }



            photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  //  Log.e(" object ",">>"+item.toString());

                    String cat = item.getNews_obj().getCategory();
                    //Log.e("cat id",""+cat);
                    getPhotoList(AllURL.getPhotoList(cat));


                }
            });
        }

    }
    public PhotoRecyAdapter(Activity context, List<AllCommonNewsItem> photoList, OnItemClickListenerNews listener) {
        this.context = context;
        this.listener = listener;
        this.photoList = photoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

         holder.bind(photoList.get(position), listener);

    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }



    private void getPhotoList(final String url) {
        if (!NetInfo.isOnline(context)) {
            AlertMessage.showMessage(context, context.getString(R.string.app_name), "No Internet!");
            return;
        }
        Log.e("URL : ", url);
        //progressNirbachito.setVisibility(View.VISIBLE);
        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {
            String response = "";

            @Override
            public void run() {

                try {
                    response = AAPBDHttpClient.get(url).body();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                context.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                       // progressNirbachito.setVisibility(View.GONE);

                        try {
                            Log.e("Response", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {



                               /* Gson g = new Gson();
                                Type fooType = new TypeToken<ArrayList<Photo>>() {

                                }.getType();
                                allPhotoList = g.fromJson(new String(response), fooType);*/

                                Gson g = new Gson();
                                allPhoto=g.fromJson(new String(response),AllPhoto.class);

                                AppConstant.PHOTOLIST.clear();


                                 if (allPhoto.getStatus().equalsIgnoreCase("1")){
                                     AppConstant.PHOTOLIST.addAll(allPhoto.getImages());
                                     Log.e("Size",""+AppConstant.PHOTOLIST.size());


                                     context.startActivity(new Intent(context, PhotoGallery.class));

//                                     FragmentManager manager = ((Activity) context).getFragmentManager();
//                                     PhotoViewDialog dialogMenu = new PhotoViewDialog();
//                                     dialogMenu.setStyle(DialogFragment.STYLE_NO_TITLE ,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
//                                     dialogMenu.show(manager,"");
                                 }

                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            //progressNirbachito.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}
