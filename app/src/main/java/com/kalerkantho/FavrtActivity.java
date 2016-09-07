package com.kalerkantho;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kalerkantho.Adapter.FavrtRecycleAdapter;
import com.kalerkantho.Model.DetailsModel;
import com.kalerkantho.Model.FvrtModel;
import com.kalerkantho.MyDb.MyDBHandler;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.holder.AllNewsObj;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AppBd on 8/30/2016.
 */
public class FavrtActivity extends AppCompatActivity {

    private Context con;
    Drawable dividerDrawable;
    private RecyclerView favList;
    private FavrtRecycleAdapter fAdapter;
    private AllNewsObj allObj;
    private List<DetailsModel> allDetailsList = new ArrayList<DetailsModel>();
    private MyDBHandler db;
    private LinearLayout backFavBtn;
    private TextView favHeadText;
    private Typeface face_bold;
    String news_titl = "", news_details = "", news_id = "", type = "";
//
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorite_design);
        con = this;

        db = new MyDBHandler(con);
        //intiU();

        face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");
        favList = (RecyclerView)findViewById(R.id.favrtList);
        backFavBtn = (LinearLayout) findViewById(R.id.backFavBtn);
        favHeadText = (TextView) findViewById(R.id.favHeadText);
        favHeadText.setTypeface(face_bold);

        backFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        AppConstant.openPush=false;

        con.registerReceiver(mMessageReceiver, new IntentFilter("unique_name"));

        for(FvrtModel fm: db.getAllFvrtModels()){
            Log.e("Details",fm.getFvrtObject());
            Gson g = new Gson();
            DetailsModel dm = g.fromJson(new String(fm.getFvrtObject()),DetailsModel.class);
            allDetailsList.add(dm);
        }

        favList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(con,2);
        favList.setLayoutManager(layoutManager);

        fAdapter  = new FavrtRecycleAdapter(FavrtActivity.this,allDetailsList,null);
        favList.setAdapter(fAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
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
    protected void onPause() {
        super.onPause();
        AppConstant.openPush=true;
        unregisterReceiver(mMessageReceiver);
    }
}
