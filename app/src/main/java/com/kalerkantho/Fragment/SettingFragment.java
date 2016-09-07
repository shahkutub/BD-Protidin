package com.kalerkantho.Fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aapbd.utils.network.AAPBDHttpClient;
import com.aapbd.utils.storage.PersistData;
import com.google.gson.Gson;
import com.kalerkantho.Dialog.ConditionDialogFragment;
import com.kalerkantho.Dialog.HelpDialogFragment;
import com.kalerkantho.Dialog.PrivacyPolicyDialogFragment;
import com.kalerkantho.Model.LoginResponse;
import com.kalerkantho.R;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.BusyDialog;
import com.kalerkantho.Utils.NetInfo;
import com.kalerkantho.Utils.PersistentUser;
import com.kalerkantho.holder.AllCommonResponse;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

;import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;


public class SettingFragment extends Fragment {
    private Context con;
    private TextView notificationHeadTx, notificationTitle, notificatonAnnounce, soundHeadText, soundTitle;
    private TextView vivrationHead, vivrationTitle, privacyHead, privacyTitle, tramHead, tramTitle, helpHead, helpTitle;
    private LinearLayout hepBtn, privacyPolicyBtn, conditionBtn;
    private RelativeLayout ViewShowTop;
    private ImageView imgBackSetting;
    private ToggleButton notificationTg, soundTg, vivrateTg;
    private AllCommonResponse allCommonResponse;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        intiU();
    }

    private void intiU() {
        final Typeface face_reg = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
        final Typeface face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");

        notificationHeadTx = (TextView) getView().findViewById(R.id.notificationHeadTx);
        notificationTitle = (TextView) getView().findViewById(R.id.notificationTitle);
        notificatonAnnounce = (TextView) getView().findViewById(R.id.notificatonAnnounce);
        soundHeadText = (TextView) getView().findViewById(R.id.soundHeadText);
        soundTitle = (TextView) getView().findViewById(R.id.soundTitle);
        vivrationHead = (TextView) getView().findViewById(R.id.vivrationHead);
        vivrationTitle = (TextView) getView().findViewById(R.id.vivrationTitle);
        privacyHead = (TextView) getView().findViewById(R.id.privacyHead);
        privacyTitle = (TextView) getView().findViewById(R.id.privacyTitle);
        tramHead = (TextView) getView().findViewById(R.id.tramHead);
        tramTitle = (TextView) getView().findViewById(R.id.tramTitle);
        helpHead = (TextView) getView().findViewById(R.id.helpHead);
        helpTitle = (TextView) getView().findViewById(R.id.helpTitle);
        ViewShowTop = (RelativeLayout) getView().findViewById(R.id.ViewShowTop);
        imgBackSetting = (ImageView) getView().findViewById(R.id.imgBackSetting);

        notificationTg = (ToggleButton) getView().findViewById(R.id.notificationTg);
        soundTg = (ToggleButton) getView().findViewById(R.id.soundTg);
        vivrateTg = (ToggleButton) getView().findViewById(R.id.vivrateTg);


        hepBtn = (LinearLayout) getView().findViewById(R.id.hepBtn);
        privacyPolicyBtn = (LinearLayout) getView().findViewById(R.id.privacyPolicyBtn);
        conditionBtn = (LinearLayout) getView().findViewById(R.id.conditionBtn);


        notificationHeadTx.setTypeface(face_bold);
        notificationTitle.setTypeface(face_reg);
        notificatonAnnounce.setTypeface(face_reg);
        soundHeadText.setTypeface(face_bold);
        soundTitle.setTypeface(face_reg);
        vivrationHead.setTypeface(face_bold);
        vivrationTitle.setTypeface(face_reg);
        privacyHead.setTypeface(face_bold);
        privacyTitle.setTypeface(face_reg);
        tramHead.setTypeface(face_bold);
        tramTitle.setTypeface(face_reg);
        helpHead.setTypeface(face_bold);
        helpTitle.setTypeface(face_reg);


        if (PersistData.getBooleanData(con, AppConstant.notificationSettingsOn)) {
            notificationTg.setChecked(true);
        } else {
            notificationTg.setChecked(false);
        }

        if (PersistData.getBooleanData(con, AppConstant.notificationSoundOn)) {
            soundTg.setChecked(true);
        } else {
            soundTg.setChecked(false);
        }

        if (PersistData.getBooleanData(con, AppConstant.notificationVibrateOn)) {
            vivrateTg.setChecked(true);
        } else {
            vivrateTg.setChecked(false);
        }

        notificationTg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                String updateStatus = "0";

                if (isChecked) {
                    updateStatus = "1";
                }

                updateSettings(AllURL.getUpdateSetting(PersistentUser.getUserID(con), updateStatus));
                PersistData.setBooleanData(con, AppConstant.notificationSettingsOn, isChecked);


            }
        });

        soundTg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                PersistData.setBooleanData(con, AppConstant.notificationSoundOn, isChecked);

            }
        });

        vivrateTg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                PersistData.setBooleanData(con, AppConstant.notificationVibrateOn, isChecked);


            }
        });


        if (AppConstant.DETAILSSETTING) {
            ViewShowTop.setVisibility(View.VISIBLE);

        } else {
            ViewShowTop.setVisibility(View.GONE);
        }

        imgBackSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.DETAILSSETTING = false;
                getActivity().onBackPressed();


            }
        });


        privacyPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrivacyPolicyDialogFragment dialogPrivacy = new PrivacyPolicyDialogFragment();
                dialogPrivacy.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogPrivacy.show(getActivity().getFragmentManager(), "");
            }
        });

        conditionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConditionDialogFragment dialogCondition = new ConditionDialogFragment();
                dialogCondition.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogCondition.show(getActivity().getFragmentManager(), "");
            }
        });

        hepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                HelpDialogFragment dialogHelp = new HelpDialogFragment();
                dialogHelp.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogHelp.show(getActivity().getFragmentManager(), "");

            }
        });

    }


    private void updateSettings(final String url) {
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
            return;
        }
        Log.e("URL L_D: ", url);


        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {
            String response = "";

            @Override
            public void run() {

                try {
                    response = AAPBDHttpClient.get(url).body();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {


                        try {

                            Log.e("update response:", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();
                                allCommonResponse = g.fromJson(new String(response), AllCommonResponse.class);


                                if ((allCommonResponse.getMsg().equalsIgnoreCase("Successful"))) {

                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();

                        }


                    }
                });
            }
        });
    }

}
