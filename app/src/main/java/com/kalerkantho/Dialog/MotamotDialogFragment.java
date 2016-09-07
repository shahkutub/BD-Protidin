package com.kalerkantho.Dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aapbd.utils.network.AAPBDHttpClient;
import com.aapbd.utils.storage.PersistData;
import com.aapbd.utils.storage.PersistentUser;
import com.google.gson.Gson;
import com.kalerkantho.Model.LoginResponse;
import com.kalerkantho.R;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.BusyDialog;
import com.kalerkantho.Utils.KeyValue;
import com.kalerkantho.Utils.NetInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.concurrent.Executors;

import cz.msebera.android.httpclient.Header;


public class MotamotDialogFragment extends DialogFragment {
    private Context con;
    private TextView motamotTitle, motatmotBtn, motamotThanks;
    private EditText subjectEditText, detailsEditText;
    private Typeface face_reg, face_bold;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.motamot, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        intiU();
    }

    private void intiU() {
        ImageView imgBackComment = (ImageView) getView().findViewById(R.id.imgBackComment);
        face_reg = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
        face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");

        motamotTitle = (TextView) getView().findViewById(R.id.motamotTitle);
        subjectEditText = (EditText) getView().findViewById(R.id.subjectEditText);
        detailsEditText = (EditText) getView().findViewById(R.id.detailsEditText);
        motatmotBtn = (TextView) getView().findViewById(R.id.motatmotBtn);
        motamotThanks = (TextView) getView().findViewById(R.id.motamotThanks);

        motamotTitle.setTypeface(face_bold);
        subjectEditText.setTypeface(face_reg);
        detailsEditText.setTypeface(face_reg);
        motatmotBtn.setTypeface(face_bold);
        motamotThanks.setTypeface(face_reg);

        imgBackComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        motatmotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (TextUtils.isEmpty(PersistentUser.getUserEmail(con))) {
                    loginDialoag(con);
                } else {
                    if (TextUtils.isEmpty(subjectEditText.getText().toString())) {
                        AlertMessage.showMessage(con, getString(R.string.app_name), getResources().getString(R.string.provide_subject));
                    } else if (TextUtils.isEmpty(detailsEditText.getText().toString())) {
                        AlertMessage.showMessage(con, getString(R.string.app_name), getResources().getString(R.string.provide_details));
                    } else {
                        submitFeedbackAPI(AllURL.submitFeedbackURL());
                    }

                }
            }
        });

    }

    private void loginDialoag(final Context con) {
        final Dialog dialogLogin = new Dialog(con);
        dialogLogin.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogLogin.setContentView(R.layout.dialog_login);
        dialogLogin.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialogLogin.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView tvTitel2 = (TextView) dialogLogin.findViewById(R.id.tvTitel2);
        TextView tvDescription2 = (TextView) dialogLogin.findViewById(R.id.tvDescription2);
        TextView tvLeftCommund = (TextView) dialogLogin.findViewById(R.id.tvLeftCommund);
        TextView tvRightCommund = (TextView) dialogLogin.findViewById(R.id.tvRightCommund);

        //==================Font set==========================

        tvTitel2.setTypeface(face_reg);
        tvLeftCommund.setTypeface(face_bold);
        tvRightCommund.setTypeface(face_bold);
        tvDescription2.setTypeface(face_reg);


        tvLeftCommund.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LoginDialogFragment dialogMenu = new LoginDialogFragment();
                dialogMenu.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogMenu.show(getActivity().getFragmentManager(), "");
                dialogLogin.dismiss();
            }
        });

        tvRightCommund.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialogLogin.dismiss();
            }
        });

        dialogLogin.show();
    }

//    /**
//     * -------------------12. Product Comments List  API------------------
//     */
//    public void submitFeedbackAPI(final Context con) {
//        /**
//         * ---------------slide_up internet first------------
//         */
//        if (!NetInfo.isOnline(con)) {
//            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
//            return;
//        }
//        /**
//         * ----------------Show Busy Dialog -----------------------------------------
//         */
//        final BusyDialog busy = new BusyDialog(con, false, "Please wait.....", false);
//        busy.show();
//        /**
//         * =========================Start Thread====================================================
//         */
//        Executors.newSingleThreadExecutor().submit(new Runnable() {
//
//            String msg = "";
//            String response = "";
//
//            @Override
//            public void run() {
//                // You can performed your task here.
//
//                try {
//                    Log.e("SubmitFeedback URL", AllURL.submitFeedbackURL(PersistentUser.getUserID(con),subjectEditText.getText().toString(),detailsEditText.getText().toString()));
//                    //-------------Hit Server---------------------
//                    response = AAPBDHttpClient.get(AllURL.submitFeedbackURL(PersistentUser.getUserID(con),subjectEditText.getText().toString(),detailsEditText.getText().toString())).body();
////                            header("Authorization", "Bearer " + PersistData.getStringData(con, AppConstant.token)).body();
//                    Log.e("SubmitFeedBack ", ">>" + response);
//
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                    msg = e1.getMessage();
//                }
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        /**
//                         * -------------Here we do UI related tasks inside run() method of runOnUiThread()-------------------
//                         */
//
//                        //-------Stop Busy Dialog-----
//                        if (busy != null) {
//                            busy.dismis();
//                        }
//                        Gson gson = new Gson();
//                        LoginResponse feedbackResponse = gson.fromJson(response, LoginResponse.class);
//                        /**
//                         * ---------main Ui related work--------------
//                         */
//                        if (feedbackResponse.getStatus().equalsIgnoreCase("1")) {
//                            motamotThanks.setVisibility(View.VISIBLE);
////                            Toast.makeText(con,feedbackResponse.getMsg(),Toast.LENGTH_LONG).show();
//                            subjectEditText.setText("");
//                            detailsEditText.setText("");
//                        } else {
//                            msg = feedbackResponse.getMsg();
//                            AlertMessage.showMessage(con, "Feedback", msg);
//                        }
//                    }
//                });
//            }
//        });
//    }

    protected void submitFeedbackAPI(final String url) {
        /**
         * --------------- Check Internet------------
         */
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
            return;
        }

        /**
         * ------Show Busy Dialog------------------
         */
        final BusyDialog busyNow = new BusyDialog(con, true, false);
        busyNow.show();
        /**
         * ---------Create object of  RequestParams to send value with URL---------------
         */
        final RequestParams param = new RequestParams();

        try {
            param.put("user_id", PersistentUser.getUserID(con));
            param.put("feedback_subject", subjectEditText.getText().toString());
            param.put("feedback_text", detailsEditText.getText().toString());
        } catch (final Exception e1) {
            e1.printStackTrace();
        }
        /**
         * ---------Create object of  AsyncHttpClient class to heat server ---------------
         */
        final AsyncHttpClient client = new AsyncHttpClient();
        Log.e("Submit FeedBack URL ", ">>" + url);
        client.post(url, param, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] response) {
                //-----------Off Busy Dialog ----------------
                if (busyNow != null) {
                    busyNow.dismis();
                }
                //-----------------Print Response--------------------
                Log.e("SubmitFeedback ", ">>" + new String(response));

                //------------Data persist using Gson------------------
                Gson g = new Gson();
                LoginResponse feedbackResponse = g.fromJson(new String(response), LoginResponse.class);


                if (feedbackResponse.getStatus().equalsIgnoreCase("1")) {
                    motamotThanks.setVisibility(View.VISIBLE);
//                            Toast.makeText(con,feedbackResponse.getMsg(),Toast.LENGTH_LONG).show();
                    subjectEditText.setText("");
                    detailsEditText.setText("");
                } else {
                    AlertMessage.showMessage(con, "Feedback", feedbackResponse.getMsg());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)

//				Log.e("LoginerrorResponse", new String(errorResponse));

                if (busyNow != null) {
                    busyNow.dismis();
                }
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried

            }
        });

    }
}
