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

import com.aapbd.utils.storage.PersistentUser;
import com.google.gson.Gson;
import com.kalerkantho.Model.LoginResponse;
import com.kalerkantho.R;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.BusyDialog;
import com.kalerkantho.Utils.NetInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hp on 9/1/2016.
 */
public class CommentDialog extends DialogFragment {
    private Context con;
    private View view;
    private TextView tvCommentSubmit, commentThanks;
    private EditText etComment;
    private ImageView imgBackComm;
    private Typeface face_reg, face_bold;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.comment_dialog, container, true);
        con = getActivity();
        initUi();
        return view;
    }

    private void initUi() {
        TextView commentTitle = (TextView) view.findViewById(R.id.commentTitle);
        face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");
        face_reg = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
        imgBackComm = (ImageView) view.findViewById(R.id.imgBackComm);
        tvCommentSubmit = (TextView) view.findViewById(R.id.tvCommentSubmit);
        commentThanks = (TextView) view.findViewById(R.id.commentThanks);
        etComment = (EditText) view.findViewById(R.id.etComment);

        commentTitle.setTypeface(face_reg);
        tvCommentSubmit.setTypeface(face_reg);
        etComment.setTypeface(face_reg);
        imgBackComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        tvCommentSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(PersistentUser.getUserEmail(con))) {
                    loginDialoag(con);
                } else {
                    if (TextUtils.isEmpty(etComment.getText().toString())) {
                        AlertMessage.showMessage(con, getString(R.string.app_name), getResources().getString(R.string.write_comment_pleas));
                    } else {
                        submitCommentAPI(AllURL.commentURL());
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
        tvDescription2.setText(getResources().getString(R.string.login_first_comment));
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

    protected void submitCommentAPI(final String url) {
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
            param.put("news_id", "394470");
            param.put("user_id", PersistentUser.getUserID(con));
            param.put("comment_text", etComment.getText().toString());
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
                Log.e("commentResponse ", ">>" + new String(response));

                //------------Data persist using Gson------------------
                Gson g = new Gson();
                LoginResponse feedbackResponse = g.fromJson(new String(response), LoginResponse.class);


                if (feedbackResponse.getStatus().equalsIgnoreCase("1")) {
                    commentThanks.setVisibility(View.VISIBLE);
//                            Toast.makeText(con,getResources().getString(R.string.comment_succes), Toast.LENGTH_LONG).show();
                    etComment.setText("");
//                    getDialog().dismiss();

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
