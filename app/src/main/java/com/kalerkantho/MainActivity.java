package com.kalerkantho;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aapbd.utils.storage.PersistData;
import com.bangladesh_pratidin.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.kalerkantho.Adapter.Menu2RecyAdapter;
import com.kalerkantho.Adapter.Menu3RecyAdapter;
import com.kalerkantho.Adapter.MenuRecyAdapter;
import com.kalerkantho.Dialog.HelpDialogFragment;
import com.kalerkantho.Dialog.MotamotDialogFragment;
import com.kalerkantho.Fragment.PhotoFragment;
import com.kalerkantho.Fragment.SettingFragment;
import com.kalerkantho.Gcm.FirebaseIDService;
import com.kalerkantho.Model.Category;
import com.kalerkantho.Model.LoginResponse;
import com.kalerkantho.MyDb.MyDBHandler;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.DividerItemDecoration;
import com.kalerkantho.Utils.NetInfo;
import com.kalerkantho.Utils.PersistentUser;
import com.kalerkantho.holder.AllCategory;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.util.TextUtils;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    RecyclerView listViewMenu, shokolshogbadList, nirbachitoList;
    private MenuRecyAdapter mAdapter;
    private Menu2RecyAdapter mAdapter2;
    private Menu3RecyAdapter mAdapter3;
    Drawable dividerDrawable;
    List<Category> printList = new ArrayList<Category>();
    List<Category> onlineList = new ArrayList<Category>();
    private List<String> optionMenuList = new ArrayList<String>();
    private MyDBHandler db;
    private LinearLayoutManager mLayoutManager, mmLayoutManager, mmmLayoutManager;

    private ImageView showPrintListView, shokolShonbadListView, nirbacitoMenuView;
    AllCategory allCategory;

    private RelativeLayout printBtn, shokolBtn, nirbachitoBtn;
    private LinearLayout menuListView;
    private TextView homeMenu, shirshoMenu, shorboMenu, shorbaMenu, printVersion, tvDate;
    private TextView nirbachitoSongbad, shokolShogbad, nirbachitoCategory;
    private TextView favorite, photogalery, setting, motamot;

    private Context con;
    String news_titl = "", news_details = "", news_id = "", type = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        con = this;

        Intent intent = new Intent(con, FirebaseIDService.class);
        startService(intent);


        db = new MyDBHandler(getApplicationContext());
        /**
         *Setup the DrawerLayout and NavigationView
         */

        if (TextUtils.isEmpty(PersistentUser.getUserID(con))) {
            pushIdAPI(AllURL.pushIDURL());
        } else {
            if (!(PersistData.getStringData(con, AppConstant.GCMID).equalsIgnoreCase("1234567890"))) {
                if (!(PersistData.getBooleanData(con, AppConstant.oneTimeCall))) {
                    pushIdAPI(AllURL.pushIDURL());
                    PersistData.setBooleanData(con, AppConstant.oneTimeCall, true);


                }

            }
        }

        final Typeface face_bold = Typeface.createFromAsset(getApplication().getAssets(), "fonts/SolaimanLipi_Bold.ttf");
        final Typeface face_reg = Typeface.createFromAsset(getApplication().getAssets(), "fonts/SolaimanLipi_reg.ttf");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        mDrawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        listViewMenu = (RecyclerView) findViewById(R.id.listViewMenu);
        shokolshogbadList = (RecyclerView) findViewById(R.id.shokolshogbadList);
        nirbachitoList = (RecyclerView) findViewById(R.id.nirbachitoList);

        showPrintListView = (ImageView) findViewById(R.id.showPrintListView);
        shokolShonbadListView = (ImageView) findViewById(R.id.shokolShonbadListView);
        nirbacitoMenuView = (ImageView) findViewById(R.id.nirbacitoMenuView);
        tvDate = (TextView) findViewById(R.id.tvDate);
        homeMenu = (TextView) findViewById(R.id.homeMenu);
        shirshoMenu = (TextView) findViewById(R.id.shirshoMenu);
        shorboMenu = (TextView) findViewById(R.id.shorboMenu);
        shorbaMenu = (TextView) findViewById(R.id.shorbaMenu);
        printVersion = (TextView) findViewById(R.id.printVersion);
        printBtn = (RelativeLayout) findViewById(R.id.printBtn);
        shokolBtn = (RelativeLayout) findViewById(R.id.shokolBtn);
        nirbachitoBtn = (RelativeLayout) findViewById(R.id.nirbachitoBtn);
        menuListView = (LinearLayout) findViewById(R.id.menuListView);

        nirbachitoSongbad = (TextView) findViewById(R.id.nirbachitoSongbad);
        shokolShogbad = (TextView) findViewById(R.id.shokolShogbad);
        nirbachitoCategory = (TextView) findViewById(R.id.nirbachitoCategory);
        favorite = (TextView) findViewById(R.id.favorite);
        photogalery = (TextView) findViewById(R.id.photogalery);
        setting = (TextView) findViewById(R.id.setting);
        motamot = (TextView) findViewById(R.id.motamot);

        homeMenu.setTypeface(face_bold);
        shirshoMenu.setTypeface(face_bold);
        shorboMenu.setTypeface(face_bold);
        shorbaMenu.setTypeface(face_bold);
        printVersion.setTypeface(face_bold);
        nirbachitoSongbad.setTypeface(face_bold);
        shokolShogbad.setTypeface(face_bold);
        nirbachitoCategory.setTypeface(face_bold);
        favorite.setTypeface(face_bold);
        photogalery.setTypeface(face_bold);
        setting.setTypeface(face_bold);
        motamot.setTypeface(face_bold);
        tvDate.setTypeface(face_reg);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        SimpleDateFormat mydate = new SimpleDateFormat("dd");
        String currentDateandTime = mydate.format(new Date());
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        Calendar c = Calendar.getInstance();

        String sDate = c.get(Calendar.YEAR) + "-"
                + c.get(Calendar.MONTH)
                + "-" + c.get(Calendar.DAY_OF_MONTH)
                + " at " + c.get(Calendar.HOUR_OF_DAY)
                + ":" + c.get(Calendar.MINUTE);
        tvDate.setText(sDate);

        tvDate.setText(getBanglaDay(dayOfTheWeek) + " " + AppConstant.getDigitBanglaFromEnglish(String.valueOf(c.get(Calendar.DAY_OF_MONTH))) + " " + getBanglaMonth(String.valueOf(c.get(Calendar.MONTH))) + "," +  AppConstant.getDigitBanglaFromEnglish(String.valueOf(c.get(Calendar.YEAR))));

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        TabFragment fragment = new TabFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("pos", 0);
        PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 0);
        fragment.setArguments(bundle);

        mFragmentTransaction.replace(R.id.containerView, fragment).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mmLayoutManager = new LinearLayoutManager(getApplicationContext());
        mmmLayoutManager = new LinearLayoutManager(getApplicationContext());

        listViewMenu.setLayoutManager(mLayoutManager);
        shokolshogbadList.setLayoutManager(mmLayoutManager);
        nirbachitoList.setLayoutManager(mmmLayoutManager);

        dividerDrawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.lineee);


        Gson g = new Gson();

        try{
            if (!(android.text.TextUtils.isEmpty(AppConstant.CATEGORY_RESPONSE))){

                allCategory = g.fromJson(PersistData.getStringData(getApplicationContext(), AppConstant.CATEGORY_RESPONSE), AllCategory.class);
            }

        }catch (JsonSyntaxException e){

        }


        Log.e("cat size",""+allCategory.getCategory_list().size());

        homeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", 0);
                PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 0);
                fragment.setArguments(bundle);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();

            }
        });

        shirshoMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", 1);
                PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 1);
                fragment.setArguments(bundle);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();


            }
        });

        shorboMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", 2);
                PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 2);
                fragment.setArguments(bundle);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();

            }
        });

        shorbaMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", 3);
                PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 3);
                fragment.setArguments(bundle);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();
            }
        });

        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    if (allCategory != null) {
                        if (allCategory.getCategory_list().size() > 0) {
                            if (listViewMenu.getVisibility() == View.GONE) {
                                listViewMenu.setVisibility(View.VISIBLE);


                                printList.clear();

                                for (int i = 0; i < allCategory.getCategory_list().size(); i++) {
                                    if (allCategory.getCategory_list().get(i).getM_type().equalsIgnoreCase("print")) {

                                        printList.add(allCategory.getCategory_list().get(i));
                                    }
                                }

                                if (printList.size() > 0) {

                                    showPrintListView.setImageResource(R.drawable.back_show);
                                }

                                Log.e("size",""+printList.size());

                                mAdapter = new MenuRecyAdapter(MainActivity.this, printList, null);
                                listViewMenu.setAdapter(mAdapter);

                                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                                listViewMenu.addItemDecoration(dividerItemDecoration);

                            } else {
                                showPrintListView.setImageResource(R.drawable.back_gone);
                                listViewMenu.setVisibility(View.GONE);
                            }
                        }

                    }


                } catch (JsonIOException e) {

                }

            }
        });

        nirbachitoSongbad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", 4);
                PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 4);
                fragment.setArguments(bundle);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();

            }
        });


        shokolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (allCategory != null) {
                        if (allCategory.getCategory_list().size() > 0) {
                            if (shokolshogbadList.getVisibility() == View.GONE) {

                                shokolshogbadList.setVisibility(View.VISIBLE);
                                onlineList.clear();

                                for (int i = 0; i < allCategory.getCategory_list().size(); i++) {

                                    if (allCategory.getCategory_list().get(i).getM_type().equalsIgnoreCase("online")) {

                                        onlineList.add(allCategory.getCategory_list().get(i));
                                    }
                                }

                                if (onlineList.size() > 0) {

                                    shokolShonbadListView.setImageResource(R.drawable.back_show);
                                }


                                mAdapter2 = new Menu2RecyAdapter(MainActivity.this, onlineList);
                                shokolshogbadList.setAdapter(mAdapter2);
                                RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                                shokolshogbadList.addItemDecoration(dividerItemDecoration);

                            } else {
                                shokolShonbadListView.setImageResource(R.drawable.back_gone);
                                shokolshogbadList.setVisibility(View.GONE);
                            }
                        }

                    }

                } catch (JsonIOException e) {
                    e.printStackTrace();
                }

            }
        });


        nirbachitoCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabFragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", 6);
                PersistData.setIntData(con, AppConstant.FRAGMENTPOSITON, 6);
                fragment.setArguments(bundle);
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();


            }
        });


        nirbacitoMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nirbachitoList.getVisibility() == View.GONE) {


                    if (db.getCatList().size() > 0) {
                        nirbacitoMenuView.setImageResource(R.drawable.back_show);
                    }

                    nirbachitoList.setVisibility(View.VISIBLE);
                    mAdapter3 = new Menu3RecyAdapter(MainActivity.this, null);
                    nirbachitoList.setAdapter(mAdapter3);
                    mAdapter3.notifyDataSetChanged();
                    RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
                    nirbachitoList.addItemDecoration(dividerItemDecoration);


                } else {
                    nirbacitoMenuView.setImageResource(R.drawable.back_gone);
                    nirbachitoList.setVisibility(View.GONE);
                }


            }
        });


        photogalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoFragment fragment = new PhotoFragment();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();


            }
        });


        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {/*
                FavoriteFragment fragment= new FavoriteFragment();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();*/

                Intent i = new Intent(con, FavrtActivity.class);
                startActivity(i);
                mDrawerLayout.closeDrawers();
            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingFragment fragment = new SettingFragment();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();
            }
        });


        motamot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MotamotDialogFragment motamotDialogFragment = new MotamotDialogFragment();
                motamotDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                motamotDialogFragment.show(getFragmentManager(), "");
                mDrawerLayout.closeDrawers();

            }
        });


        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, new TabFragment()).commit();

               /* if (menuItem.getItemId() == R.id.nav_item_home) {
                    AppConstant.POSITION=0;


                    mDrawerLayout.closeDrawers();
                    return true;
                }else if(menuItem.getItemId() == R.id.nav_item_latest){

                    AppConstant.POSITION=1;

                    mDrawerLayout.closeDrawers();
                    return true;

                }else if(menuItem.getItemId() == R.id.nav_item_last){

                    AppConstant.POSITION=2;

                    mDrawerLayout.closeDrawers();
                    return true;

                }else if(menuItem.getItemId() == R.id.nav_item_most){

                    AppConstant.POSITION=3;

                    mDrawerLayout.closeDrawers();
                    return true;

                }else if(menuItem.getItemId() == R.id.nav_item_self){

                    AppConstant.POSITION=4;

                    mDrawerLayout.closeDrawers();
                    return true;

                }else if(menuItem.getItemId() == R.id.nav_item_print){

                    AppConstant.POSITION=5;

                    mDrawerLayout.closeDrawers();
                    return true;

                }else if(menuItem.getItemId() == R.id.nav_item_final){

                    AppConstant.POSITION=6;

                    mDrawerLayout.closeDrawers();
                    return true;
                } else if(menuItem.getItemId() == R.id.nav_item_select){
                    AppConstant.POSITION=7;
                    mDrawerLayout.closeDrawers();
                    return true;
                }*/

                return true;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */


        optionMenuList.add(0, getResources().getString(R.string.refresh));
        optionMenuList.add(1, getResources().getString(R.string.setting));
        optionMenuList.add(2, getResources().getString(R.string.helps));
        optionMenuList.add(3, getResources().getString(R.string.feedback));
        setupToolbar();
        /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
         mDrawerLayout.setDrawerListener(mDrawerToggle);
         mDrawerToggle.syncState();*/


        // set here nav icon if want to change
        //  toolbar.setNavigationIcon(R.id.nav_icon);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2 = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    static public String getBanglaDay(String day) {

        String symbol = null;
        switch (day.toLowerCase()) {
            case "saturday":
                symbol = "শনিবার";
                break;
            case "sunday":
                symbol = "রবিবার";
//				symbol="₽";
                break;
            case "monday":
                symbol = "সোমবার";
                break;
            case "tuesday":
                symbol = "মঙ্গলবার";
                break;
            case "wednesday":
                symbol = "বুধবার";
                break;
            case "thursday":
                symbol = "বৃহস্পতিবার";
                break;
            case "friday":
                symbol = "শুক্রবার";
                break;
        }
        return symbol;
    }

    public String getBanglaMonth(String englishMonth) {

        String symbol = null;
        switch (englishMonth.toLowerCase()) {
            case "0":
                symbol = "জানুয়ারি ";
                break;
            case "1":
                symbol = "ফেব্রূয়ারি ";
//				symbol="₽";
                break;
            case "2":
                symbol = "মার্চ ";
                break;
            case "3":
                symbol = "এপ্রিল ";
                break;
            case "4":
                symbol = "মে ";
                break;
            case "5":
                symbol = "জুন ";
                break;
            case "6":
                symbol = "জুলাই ";
                break;
            case "7":
                symbol = "আগস্ট ";
                break;
            case "8":
                symbol = "সেপ্টেম্বর";
                break;
            case "9":
                symbol = "অক্টোবর ";
                break;
            case "10":
                symbol = "নভেম্বর";
                break;
            case "11":
                symbol = "ডিসেম্বর";
                break;
        }
        return symbol;
    }

    protected void pushIdAPI(final String url) {
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
//        final BusyDialog busyNow = new BusyDialog(con, true, false);
//        busyNow.show();
        /**
         * ---------Create object of  RequestParams to send value with URL---------------
         */

        Log.e("Push id ", ">>" + PersistData.getStringData(con, AppConstant.GCMID));
        final RequestParams param = new RequestParams();

        try {
            param.put("device_type", "android");
            param.put("push_id", PersistData.getStringData(con, AppConstant.GCMID));
        } catch (final Exception e1) {
            e1.printStackTrace();
        }
        /**
         * ---------Create object of  AsyncHttpClient class to heat server ---------------
         */
        final AsyncHttpClient client = new AsyncHttpClient();
        Log.e("Push URL ", ">>" + url);
        client.post(url, param, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] response) {
                //-----------Off Busy Dialog ----------------
//                if (busyNow != null) {
//                    busyNow.dismis();
//                }
                //-----------------Print Response--------------------
                Log.e("PushIDResposne ", ">>nnn" + new String(response));

                //------------Data persist using Gson------------------
                Gson g = new Gson();
                LoginResponse loginResponse = g.fromJson(new String(response), LoginResponse.class);

                Log.e("Loginstatus", "=" + loginResponse.getStatus());

                if (loginResponse.getStatus().equalsIgnoreCase("1")) {
                    PersistentUser.setLogin(con);
                    PersistentUser.setUserID(con, loginResponse.getUserdetails().getId());
                    PersistData.setStringData(con, AppConstant.GCMID, loginResponse.getUserdetails().getPush_id());
                    PersistentUser.setAccessToken(con, loginResponse.getToken());
                    Log.e("User id", "=" + PersistentUser.getUserID(con));

                } else {
                    AlertMessage.showMessage(con, getResources().getString(R.string.app_name), loginResponse.getMsg() + "");
                    return;
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {

            }

            @Override
            public void onRetry(int retryNo) {


            }
        });


    }


    private void setupToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.drawer, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshItem:

                AppConstant.REFRESHFLAG = true;

                if (PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON) == 0) {

                    Log.e("Pos", "" + PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON));
                    TabFragment fragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", 0);
                    fragment.setArguments(bundle);
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                    mDrawerLayout.closeDrawers();


                } else if (PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON) == 1) {

                    Log.e("Pos", "" + PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON));
                    TabFragment fragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", 1);
                    fragment.setArguments(bundle);
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                    mDrawerLayout.closeDrawers();


                } else if (PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON) == 2) {

                    Log.e("Pos", "" + PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON));
                    TabFragment fragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", 2);
                    fragment.setArguments(bundle);
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                    mDrawerLayout.closeDrawers();

                } else if (PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON) == 3) {
                    Log.e("Pos", "" + PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON));
                    TabFragment fragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", 3);
                    fragment.setArguments(bundle);
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                    mDrawerLayout.closeDrawers();


                } else if (PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON) == 4) {

                    Log.e("Pos", "" + PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON));
                    TabFragment fragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", 4);
                    fragment.setArguments(bundle);
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                    mDrawerLayout.closeDrawers();


                } else if (PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON) == 5) {

                    Log.e("Pos", "" + PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON));
                    TabFragment fragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", 5);
                    fragment.setArguments(bundle);
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                    mDrawerLayout.closeDrawers();


                } else if (PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON) == 6) {

                    Log.e("Pos", "" + PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON));
                    TabFragment fragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", 6);
                    fragment.setArguments(bundle);
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                    mDrawerLayout.closeDrawers();


                } else if (PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON) == 7) {

                    Log.e("Pos", "" + PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON));
                    TabFragment fragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", 7);
                    fragment.setArguments(bundle);
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                    mDrawerLayout.closeDrawers();


                } else if (PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON) == 8) {

                    Log.e("Pos", "" + PersistData.getIntData(con, AppConstant.FRAGMENTPOSITON));
                    TabFragment fragment = new TabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pos", 8);
                    fragment.setArguments(bundle);
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                    mDrawerLayout.closeDrawers();


                }

           /* final Dialog dialog = new Dialog(con);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.alartdialog);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                LinearLayout mainDialog = (LinearLayout) dialog.findViewById(R.id.mainDialog);
                ImageView dismissDialog = (ImageView) dialog.findViewById(R.id.dismissDialog);

                dialog.show();
                dismissDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                YoYo.with(Techniques.SlideInDown).duration(700).interpolate(new AccelerateInterpolator()).withListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {


                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {
                               //Toast.makeText(SplashActivity.this, "canceled", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        }).playOn(mainDialog);*/


                return true;

            case R.id.settinItem:
                SettingFragment fragment = new SettingFragment();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.containerView, fragment).commit();
                mDrawerLayout.closeDrawers();

                return true;

            case R.id.helpItem:

                HelpDialogFragment dialogHelp = new HelpDialogFragment();
                dialogHelp.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogHelp.show(MainActivity.this.getFragmentManager(), "");

                return true;

            case R.id.feedBackItem:
                MotamotDialogFragment dialoMotamot = new MotamotDialogFragment();
                dialoMotamot.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialoMotamot.show(MainActivity.this.getFragmentManager(), "");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client2.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.bangladesh_pratidin/http/host/path")
        );
        AppIndex.AppIndexApi.start(client2, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.bangladesh_pratidin/http/host/path")
        );
        AppIndex.AppIndexApi.end(client2, viewAction);
        client2.disconnect();
    }

  /*  @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        mDrawerToggle.syncState();
    }*/


   /* class CustomAdapter extends ArrayAdapter<String> {
        Context context;
        private List<String> optionMenuList = new ArrayList<String>();

        CustomAdapter(Context context, List<String> optionMenuList) {
            super(context, R.layout.single_row, optionMenuList);
            this.context = context;
            this.optionMenuList = optionMenuList;

        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v = convertView;

            if (v == null) {
                final LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.single_row, null);
            }

            TextView menName = (TextView) v.findViewById(R.id.headlist);
            menName.setText(optionMenuList.get(position));

            return v;
        }

    }*/
}