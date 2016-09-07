package com.kalerkantho.Dialog;


import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aapbd.utils.storage.PersistData;
import com.bangladesh_pratidin.R;
import com.google.gson.Gson;
import com.kalerkantho.Model.LoginResponse;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.BusyDialog;
import com.kalerkantho.Utils.NetInfo;
import com.kalerkantho.Utils.PersistentUser;
import com.kalerkantho.Utils.ValidateEmail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by hp on 8/30/2016.
 */
public class Registration extends DialogFragment {

    private Context con;
    private View view;
private String full_name,email,password,device_type,push_id,registrationtype;
    private TextView tvDataNotFound;
    private EditText etName, etEmail,etPasword;
    private LoginResponse loginResponse;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.registration_fragment_dialog, container, true);
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
        final Typeface face_normal = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");

        TextView tvRegistration = (TextView) view.findViewById(R.id.tvRegistration);
        TextView tvEnter = (TextView) view.findViewById(R.id.tvEnter);
        ImageView imgCross=(ImageView) view.findViewById(R.id.imgCross);
        etName = (EditText) view.findViewById(R.id.etName);
        etEmail = (EditText) view.findViewById(R.id.etEmail);
        etPasword = (EditText) view.findViewById(R.id.etPasword);
        tvRegistration.setTypeface(face_normal);
        tvEnter.setTypeface(face_normal);
        etName.setTypeface(face_normal);
        etEmail.setTypeface(face_normal);
        etPasword.setTypeface(face_normal);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etName.getText().toString())) {
                    AlertMessage.showMessage(con, getString(R.string.app_name), getResources().getString(R.string.provide_name));
                }  else if (TextUtils.isEmpty(etEmail.getText().toString())) {
                    AlertMessage.showMessage(con, getString(R.string.app_name), getResources().getString(R.string.provide_email));
                } else if (!ValidateEmail.validateEmail(etEmail.getText().toString())) {
                    AlertMessage.showMessage(con, getString(R.string.app_name), getResources().getString(R.string.provide_email));
                }  else if (etName.getText().toString().length() < 3) {
//                    AlertMessage.showMessage(con, getString(R.string.status), getString(R.string.checkInternet));
                } else if (TextUtils.isEmpty(etPasword.getText().toString())) {
                    AlertMessage.showMessage(con, getString(R.string.app_name), getResources().getString(R.string.provide_password));
                } else if (etPasword.getText().toString().length() < 3) {
//                    AlertMessage.showMessage(con, getString(R.string.status), getString(R.string.checkInternet));
                } else{
                    full_name = etName.getText().toString();
                    email = etEmail.getText().toString();
                    password = etPasword.getText().toString();

                    normalUserSignUp(AllURL.registrationURL());
                }
            }
        });

    }

    protected void normalUserSignUp(final String url) {
        //--- for net slide_up-----
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getResources().getString(R.string.app_name), "No internet!");
            return;
        }
        final BusyDialog busyNow = new BusyDialog(con, true, false);
        busyNow.show();
        final AsyncHttpClient client = new AsyncHttpClient();
        final RequestParams param = new RequestParams();
        Log.e("Regis URL ", ">>" + url);

        try {
            param.put("full_name", full_name);
            param.put("email", email);
            param.put("password", password);
            param.put("device_type", "android");
            param.put("push_id", PersistData.getStringData(con, AppConstant.GCMID));
            param.put("registrationtype", "normal");
            Log.e("full_name",full_name);
            Log.e("email",email);
            Log.e("password",password);
            Log.e("push_id",PersistData.getStringData(con, AppConstant.GCMID));

        } catch (final Exception e1) {
            e1.printStackTrace();
        }

        client.post(url, param, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] response) {

                if (busyNow != null) {
                    busyNow.dismis();
                }

                Log.e("Registration resposne ", ">>" + new String(response));

                Gson g = new Gson();
                loginResponse = g.fromJson(new String(response), LoginResponse.class);
                Toast.makeText(con,loginResponse.getMsg(),Toast.LENGTH_LONG).show();
                if (loginResponse.getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(con,loginResponse.getMsg(),Toast.LENGTH_LONG).show();
                    PersistentUser.setLogin(con);
                    PersistentUser.setUserID(con,loginResponse.getUserdetails().getId());
                    PersistentUser.setUserEmail(con,loginResponse.getUserdetails().getEmail());
                    PersistentUser.setAccessToken(con,loginResponse.getToken());
//                    PersistData.setStringData(con, AppConstant.id, loginResponse.getUserdetails().getId());
//                    PersistData.setStringData(con, AppConstant.email, loginResponse.getUserdetails().getDevice_type());
//                    PersistData.setStringData(con, AppConstant.last_name, logInResponse.getResults().getLast_name());

//                    PersistData.setStringData(con, AppConstant.token,
//                            loginResponse.getToken());
                    Log.e("token", "=" + PersistData.getStringData(con, AppConstant.token));
//                    if (AppConstant.loginDialogFragment!=null) {
//                        AppConstant.loginDialogFragment.dismiss();
//                    }
                    LoginDialogFragment.instancedialogFragment().dismiss();
                    getDialog().dismiss();

                    //---------Go Tab Activity-----------------------
//                    MainActivity.instance.finish();
//                    if (MyTabActivity.getMyTabActivity()!=null){
//                        MyTabActivity.getMyTabActivity().finish();
//                    }
                   ;

                } else {

//					AlertMessage.showMessage(con, "Status", logInResponse.getMsg() + "");
                    return;
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] errorResponse, Throwable e) {
                Log.e("errorResponse", new String(errorResponse));
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
