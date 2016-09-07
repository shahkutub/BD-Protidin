package com.kalerkantho.Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bangladesh_pratidin.R;
import com.kalerkantho.Adapter.CustomPagerAdapter;


/**
 * Created by wlaptop on 8/29/2016.
 */
public class PhotoViewDialog extends DialogFragment {
    private ViewPager mPager;
     CustomPagerAdapter mCustomPagerAdapter ;
    // Activity context;
    //private List<Photo> allPhotoList = new ArrayList<Photo>();
    public PhotoViewDialog( ) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.photodialog, container, false);
       // final PhotoViewDialog dialog = new PhotoViewDialog();
       // dialog.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mCustomPagerAdapter = new CustomPagerAdapter(getActivity());
        mPager.setAdapter(mCustomPagerAdapter);
        return rootView;
    }


}
