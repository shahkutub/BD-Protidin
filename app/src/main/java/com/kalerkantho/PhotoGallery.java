package com.kalerkantho;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalerkantho.Adapter.CustomPagerAdapter;
import com.kalerkantho.Utils.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

public class PhotoGallery extends Activity {

    Context con;

    private ProgressBar progressShow;

    int imagePos=0;
    String news_titl = "", news_details = "", news_id = "", type = "";

    private ViewPager mPager;
    CustomPagerAdapter mCustomPagerAdapter ;
    private ImageView backwaredBtn,forwardBtn;
    int gallerySize=0;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.photodialog);
        con=this;

        backwaredBtn=(ImageView)findViewById(R.id.backwardButton);
        forwardBtn=(ImageView)findViewById(R.id.forwardButton);
        gallerySize= AppConstant.PHOTOLIST.size();


        mPager = (ViewPager) findViewById(R.id.pager);
        mCustomPagerAdapter = new CustomPagerAdapter(con);
        mPager.setAdapter(mCustomPagerAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                imagePos=position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        backwaredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imagePos==0)
                {

                }else {
                    mPager.setCurrentItem(imagePos-1);
                    imagePos=imagePos-1;
                }

            }
        });

        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((gallerySize-1)==imagePos)
                {

                }else {
                    mPager.setCurrentItem(imagePos+1);
                    imagePos=imagePos+1;
                }

            }
        });


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

    public void closeWindow(View v)
    {
        PhotoGallery.this.finish();
    }
}