package com.kalerkantho.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kalerkantho.R;
import com.kalerkantho.Utils.AppConstant;

public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;


    public CustomPagerAdapter(Context context) {
        mContext = context;
        //this.allImagesList =allImagesList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return AppConstant.PHOTOLIST.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.dialog_row, container, false);

        final Typeface face_reg = Typeface.createFromAsset(mContext.getAssets(), "fonts/SolaimanLipi_reg.ttf");

        ImageView imageView = (ImageView) itemView.findViewById(R.id.photo);
        TextView titlePhoto = (TextView) itemView.findViewById(R.id.titlePhoto);
        TextView photoNameText = (TextView) itemView.findViewById(R.id.photoNameText);

        if (!(TextUtils.isEmpty(AppConstant.PHOTOLIST.get(position).getImage()))) {
            Glide.with(mContext).load(AppConstant.PHOTOLIST.get(position).getImage()).placeholder(R.drawable.defaulticon).into(imageView);
        } else {
            Glide.with(mContext).load(R.drawable.defaulticon).placeholder(R.drawable.defaulticon).into(imageView);
        }

        if (!(TextUtils.isEmpty(AppConstant.PHOTOLIST.get(position).getCaption()))){
            titlePhoto.setText(AppConstant.PHOTOLIST.get(position).getCaption());
        }else{
            titlePhoto.setText("");
        }

        if (!(TextUtils.isEmpty(AppConstant.PHOTOLIST.get(position).getTitle()))){
            photoNameText.setText(AppConstant.PHOTOLIST.get(position).getTitle());
        }else{
            photoNameText.setText("");
        }

        titlePhoto.setTypeface(face_reg);
        photoNameText.setTypeface(face_reg);


        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}