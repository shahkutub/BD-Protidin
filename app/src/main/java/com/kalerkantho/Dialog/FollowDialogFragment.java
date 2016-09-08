package com.kalerkantho.Dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bangladesh_pratidin.R;
import com.kalerkantho.Utils.AppConstant;

public class FollowDialogFragment extends DialogFragment {
    private Context con;
    private View view;
    private TextView followHeadText;
    private ImageView facShareBtn,twitShareBtn,googlePlusBtn,dialogCloseBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.follow_dialog, container, true);
        con = getActivity();
        initUi();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initUi() {

        final Typeface face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");
        followHeadText = (TextView) view.findViewById(R.id.followHeadText);
        facShareBtn = (ImageView) view.findViewById(R.id.facShareBtn);
        twitShareBtn = (ImageView) view.findViewById(R.id.twitShareBtn);
        googlePlusBtn = (ImageView) view.findViewById(R.id.googlePlusBtn);
        dialogCloseBtn = (ImageView) view.findViewById(R.id.dialogCloseBtn);

        followHeadText.setTypeface(face_bold);

        dialogCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        facShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AppConstant.TITLE = "Facebook";
                AppConstant.URL = "https://www.facebook.com/dailybangladeshpratidin";
                ShareDialogFragment dialogHelp= new ShareDialogFragment();
                dialogHelp.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogHelp.show(getActivity().getFragmentManager(), "");


            }
        });

        twitShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstant.TITLE = "Twitter";
                AppConstant.URL ="https://twitter.com/bdpratidin?lang=en";
                ShareDialogFragment dialogHelp= new ShareDialogFragment();
                dialogHelp.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogHelp.show(getActivity().getFragmentManager(), "");

            }
        });

        googlePlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstant.TITLE = "Google+";
                AppConstant.URL ="https://plus.google.com/110480320193736975760";
                ShareDialogFragment dialogHelp= new ShareDialogFragment();
                dialogHelp.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogHelp.show(getActivity().getFragmentManager(), "");

            }
        });

    }


}
