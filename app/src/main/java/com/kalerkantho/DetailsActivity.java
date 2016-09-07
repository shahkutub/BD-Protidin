package com.kalerkantho;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bangladesh_pratidin.R;
import com.kalerkantho.Fragment.DetailsFragment;
import com.kalerkantho.Utils.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity implements CommunicatorFragmentInterface {
    private Context con;
    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;
    String news_titl = "", news_details = "", news_id = "", type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);
        con = this;

        mFragmentManager = getSupportFragmentManager();
       DetailsFragment dtf = new DetailsFragment();
        /*  mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.contain, dtf).commit();*/
        setContentFragment(dtf,false);
    }

    @Override
    public void setContentFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment == null) {
            return;
        }
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.contain);

        if (currentFragment != null && fragment.getClass().isAssignableFrom(currentFragment.getClass())) {
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contain, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void addContentFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment == null) {
            return;
        }
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.contain);

        if (currentFragment != null && fragment.getClass().isAssignableFrom(currentFragment.getClass())) {
            return;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.contain, fragment, fragment.getClass().getName());
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    // This is the handler that will manager to process the broadcast intent
    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String smg = intent.getStringExtra("message");

            showDiaLogView(smg);


        }
    };

    private void showDiaLogView(final String msg) {
        // AlertDialog.Builder adb = new AlertDialog.Builder(this);


        try {
            JSONObject newsObj = new JSONObject(msg);
            news_titl = newsObj.optString("news_title");
            news_details = newsObj.optString("news_details");
            news_id = newsObj.optString("news_id");
            type = newsObj.optString("type");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final Dialog d = new Dialog(con, R.style.full_screen_dialog);

        d.setContentView(R.layout.popup_news);

        RelativeLayout mainLayout = (RelativeLayout) d.findViewById(R.id.mainLayout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (type.equalsIgnoreCase("news")) {
                    Intent intent = new Intent(con, DetailsActivity.class);
                    intent.putExtra("content_id", news_id);
                    intent.putExtra("is_favrt", "0");
                    startActivity(intent);
                    d.dismiss();
                } else {
                    d.dismiss();
                }


            }

        });

        TextView title = (TextView) d.findViewById(R.id.breakingNewsTitle);
        title.setText(news_titl);

        TextView description = (TextView) d.findViewById(R.id.breakingDetails);
        description.setText(news_details);

        final Typeface face_reg = Typeface.createFromAsset(getAssets(), "fonts/SolaimanLipi_reg.ttf");
        description.setTypeface(face_reg);
        title.setTypeface(face_reg);


        // (That new View is just there to have something inside the dialog that can grow big enough to cover the whole screen.)


        WindowManager.LayoutParams wmlp = d.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 0;
        wmlp.y = 0;

        d.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

        AppConstant.openPush=false;

        con.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppConstant.openPush=true;
        unregisterReceiver(mMessageReceiver);
    }
}
