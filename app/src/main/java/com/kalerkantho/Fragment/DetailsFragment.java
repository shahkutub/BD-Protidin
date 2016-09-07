package com.kalerkantho.Fragment;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aapbd.utils.network.AAPBDHttpClient;
import com.aapbd.utils.storage.PersistentUser;
import com.bangladesh_pratidin.R;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kalerkantho.CommunicatorFragmentInterface;
import com.kalerkantho.Dialog.CatListDialogFragment;
import com.kalerkantho.Dialog.CommentListDialogFragment;
import com.kalerkantho.Dialog.FollowDialogFragment;
import com.kalerkantho.Dialog.HelpDialogFragment;
import com.kalerkantho.Dialog.MotamotDialogFragment;
import com.kalerkantho.Model.DetailsModel;
import com.kalerkantho.Model.FvrtModel;
import com.kalerkantho.MyDb.MyDBHandler;
import com.kalerkantho.Utils.AlertMessage;
import com.kalerkantho.Utils.AllURL;
import com.kalerkantho.Utils.AppConstant;
import com.kalerkantho.Utils.BusyDialog;
import com.kalerkantho.Utils.NetInfo;
import com.kalerkantho.holder.AllCommonResponse;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Executors;


public class DetailsFragment extends Fragment {
    private Context con;
    private TextView headingTxt, txt_positive_like, txt_negative_like, txt_comment, txtDate, txtCategory, detailsTxt;
    private ImageView backImgMain,favImage;
    private String content_id = "", isFvrtString = "";
    private ProgressBar progressShow;
    private MyDBHandler db;
    private FvrtModel fm = new FvrtModel();
    private DetailsModel allDetail;
    private ImageView positive_like, dislikeBtn, sharePlusBtn, defaultShareBtn, commentBtn;
    private AllCommonResponse allCommonResponse;
    Typeface face_reg, face_bold;
    BusyDialog busyDialog;
    LinearLayout backView,menuListView,settingBtn,shareBtn,fvImg;
    TextView titleDetails,settin,helpBtn,feedBtn;
    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;

    public CommunicatorFragmentInterface myCommunicator;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details_view,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        con = getActivity();
        intiU();
    }

    private void intiU() {

        db = new MyDBHandler(con);
        mFragmentManager = getActivity().getSupportFragmentManager();
        headingTxt = (TextView) getView().findViewById(R.id.headingTxt);
        txt_positive_like = (TextView) getView().findViewById(R.id.txt_positive_like);
        txt_negative_like = (TextView) getView().findViewById(R.id.txt_negative_like);
        txt_comment = (TextView) getView().findViewById(R.id.txt_comment);
        txtDate = (TextView) getView().findViewById(R.id.dateTv);
        txtCategory = (TextView) getView().findViewById(R.id.catagoryTv);
        detailsTxt = (TextView) getView().findViewById(R.id.detailsText);
        backImgMain = (ImageView) getView().findViewById(R.id.mainBackground);
        fvImg = (LinearLayout) getView().findViewById(R.id.favrtBtn);
        shareBtn = (LinearLayout) getView().findViewById(R.id.shareBtn);
        settingBtn = (LinearLayout) getView().findViewById(R.id.settingBtn);
        settin = (TextView) getView().findViewById(R.id.settin);
        titleDetails = (TextView) getView().findViewById(R.id.titleDetails);
        helpBtn = (TextView) getView().findViewById(R.id.helpBtn);
        feedBtn = (TextView) getView().findViewById(R.id.feedBtn);
        favImage = (ImageView) getView().findViewById(R.id.favImage);

        backView = (LinearLayout) getView().findViewById(R.id.backView);
        menuListView = (LinearLayout) getView().findViewById(R.id.menuListView);
        progressShow = (ProgressBar) getView().findViewById(R.id.progressShow);
        myCommunicator = (CommunicatorFragmentInterface) con;

        positive_like = (ImageView) getView().findViewById(R.id.positive_like);
        dislikeBtn = (ImageView) getView().findViewById(R.id.dislikeBtn);
        sharePlusBtn = (ImageView) getView().findViewById(R.id.sharePlusBtn);
        defaultShareBtn = (ImageView) getView().findViewById(R.id.defaultShareBtn);
        commentBtn = (ImageView) getView().findViewById(R.id.commentBtn);

        content_id =   getActivity().getIntent().getExtras().getString("content_id");
        isFvrtString = getActivity().getIntent().getExtras().getString("is_favrt");

        face_reg = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
        face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");

        titleDetails.setTypeface(face_bold);
        settin.setTypeface(face_reg);
        helpBtn.setTypeface(face_reg);
        feedBtn.setTypeface(face_reg);

        if (db.isFavorite(content_id)) {
            favImage.setImageResource(R.drawable.fav_white_fill);
        } else {
            favImage.setImageResource(R.drawable.fav_white);
        }


        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();
            }
        });


        // call url for detals
        try {
            if (!content_id.isEmpty()) {
                if (isFvrtString.equalsIgnoreCase("1")) {
                    Log.e("fff", "favrt");

                    String response = "";

                    for (FvrtModel fm : db.getAllFvrtModels()) {
                        if (content_id.equalsIgnoreCase(fm.getFvrtId())) {
                            response = fm.getFvrtObject();
                            break;
                        }
                    }


                    Gson g = new Gson();
                    allDetail = g.fromJson(new String(response), DetailsModel.class);

                    if (allDetail != null) {
                        setAllData(true);
                    }

                } else {
                    Log.e("id details:", content_id);
                    requestGetNeslist(AllURL.getDetails(content_id, PersistentUser.getUserID(con)));
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        fvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isFvrt = false;
                for (FvrtModel fm : db.getAllFvrtModels()) {
                    if (content_id.equalsIgnoreCase(fm.getFvrtId())) {
                        Log.e("get compared:", "true");
                        isFvrt = true;
                        break;
                    }
                }


                if (!isFvrt) { // not added before

                    //isFvrt = true;
                    //   AppConstant.mAdapter.notifyDataSetChanged();
                    fm.setFvrtId(content_id);
                    Gson gson = new Gson();
                    String favObject = gson.toJson(allDetail);
                    fm.setFvrtId(content_id);
                    fm.setFvrtObject(favObject);
                    db.addFavrtEntry(fm);
                    favImage.setImageResource(R.drawable.fav_white_fill);
                    //Toast.makeText(con, "Data Added Successfully", Toast.LENGTH_SHORT);

                } else {
                    // isFvrt = false;
                    favImage.setImageResource(R.drawable.fav_white);

                    db.removeSingleFavENtry(content_id);
                }
                //isFvrt = false;
                Log.e("All Frvt Size:", ">>" + db.getAllFvrtModels().size());
            }
        });


        positive_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dislike = "0", like = "0";

                if (allDetail.getIs_disliked().equalsIgnoreCase("TRUE")) {
                    dislike = "1";
                } else {
                    dislike = "0";
                }

                if (allDetail.getIs_liked().equalsIgnoreCase("TRUE")) {
                    like = "0";
                } else {
                    like = "1";
                }


                setLikeDislike(AllURL.getLikeDislike(PersistentUser.getUserID(con), allDetail.getNews().getId(), like, dislike));


            }
        });


        dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dislike = "0", like = "0";

                if (allDetail.getIs_disliked().equalsIgnoreCase("TRUE")) {
                    dislike = "0";
                } else {
                    dislike = "1";
                }

                if (allDetail.getIs_liked().equalsIgnoreCase("TRUE")) {
                    like = "1";
                } else {
                    like = "0";
                }


                setDislikeLike(AllURL.getLikeDislike(PersistentUser.getUserID(con), allDetail.getNews().getId(), like, dislike));


            }
        });


        sharePlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FollowDialogFragment dialogFollow = new FollowDialogFragment();
                dialogFollow.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar);
                dialogFollow.show(getActivity().getFragmentManager(), "");


            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(con).load(allDetail.getNews().getImage()).memoryPolicy(MemoryPolicy.NO_CACHE).into(target);


            }
        });

        defaultShareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Picasso.with(con).load(allDetail.getNews().getImage()).memoryPolicy(MemoryPolicy.NO_CACHE).into(target);


            }
        });

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstant.newsID = content_id;

                CommentListDialogFragment dialogFragment = new CommentListDialogFragment();
                dialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogFragment.show(getActivity().getFragmentManager(), "");

            }
        });


        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuListView.getVisibility() == View.GONE){
                    menuListView.setVisibility(View.VISIBLE);
                }else {
                    menuListView.setVisibility(View.GONE);
                }

            }
        });

        settin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menuListView.setVisibility(View.GONE);
                AppConstant.DETAILSSETTING = true;
               // SettingFragment fragment = new SettingFragment();
                myCommunicator.setContentFragment(new SettingFragment(), true);
                /*mFragmentTransaction = mFragmentManager.beginTransaction();
                 mFragmentTransaction.replace(R.id.containerViewDetails, fragment).commit();*/


            }
        });

        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuListView.setVisibility(View.GONE);
                HelpDialogFragment dialogHelp= new HelpDialogFragment();
                dialogHelp.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogHelp.show(getActivity().getFragmentManager(), "");

            }
        });
        feedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menuListView.setVisibility(View.GONE);
                MotamotDialogFragment motamotDialogFragment = new MotamotDialogFragment();
                motamotDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                motamotDialogFragment.show(getActivity().getFragmentManager(), "");

            }
        });

    }


    private void setAllData(boolean imageload) {

        headingTxt.setText(allDetail.getNews().getTitle());
        detailsTxt.setText(Html.fromHtml(allDetail.getNews().getDetails()));


        if(imageload)
        {
            Glide.with(con).load(allDetail.getNews().getImage()).placeholder(R.drawable.fullscreen).into(backImgMain);
        }



        if (!(TextUtils.isEmpty(allDetail.getLike_count()))) {
            txt_positive_like.setText("(" + allDetail.getLike_count() + ")");
        } else {
            txt_positive_like.setText("(" + 0 + ")");
        }
        if (!(TextUtils.isEmpty(allDetail.getDislike_count()))) {
            txt_negative_like.setText("(" + allDetail.getDislike_count() + ")");
        } else {
            txt_negative_like.setText("(" + 0 + ")");
        }

        if (!(TextUtils.isEmpty(allDetail.getComments_count())))
            txt_comment.setText("(" + allDetail.getComments_count() + ")");
        else {
            txt_comment.setText("(" + 0 + ")");
        }


        if (allDetail.getIs_liked().equalsIgnoreCase("FALSE")) {

            positive_like.setImageResource(R.drawable.positive_like_white);

        } else {

            positive_like.setImageResource(R.drawable.positive_like);
        }


        if (allDetail.getIs_disliked().equalsIgnoreCase("FALSE")) {
            dislikeBtn.setImageResource(R.drawable.negative_like);
        } else {

            dislikeBtn.setImageResource(R.drawable.negative_like_blue);
        }


        txtDate.setText(allDetail.getNews().getBanglaDateString() + "  | ");
        txtCategory.setText(allDetail.getNews().getCategory_name());
        txtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppConstant.CATEGORYTYPE = allDetail.getNews().getCategory();
                AppConstant.CATEGORYTITLE= allDetail.getNews().getCategory_name();

                Log.e("Category Type",""+ AppConstant.CATEGORYTYPE );

                CatListDialogFragment dialogCatList= new CatListDialogFragment();
                dialogCatList.setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
                dialogCatList.show(getActivity().getFragmentManager(), "");

            }
        });


        headingTxt.setTypeface(face_bold);
        detailsTxt.setTypeface(face_reg);
        txt_positive_like.setTypeface(face_reg);
        txt_negative_like.setTypeface(face_reg);
        txt_comment.setTypeface(face_reg);
        txtDate.setTypeface(face_reg);
        txtCategory.setTypeface(face_reg);
    }


    private void requestGetNeslist(final String url) {
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
            return;
        }
        Log.e("URL : ", url);

        progressShow.setVisibility(View.VISIBLE);
        Executors.newSingleThreadScheduledExecutor().submit(new Runnable() {
            String response = "";

            @Override
            public void run() {

                try {
                    response = AAPBDHttpClient.get(url).body();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e("id response:", response);

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        progressShow.setVisibility(View.GONE);

                        try {
                            final Typeface face_reg = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
                            final Typeface face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");
                            Log.e("details response:", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();
                                allDetail = g.fromJson(new String(response), DetailsModel.class);

                                setAllData(true);
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            progressShow.setVisibility(View.GONE);
                        }


                    }
                });
            }
        });
    }


    private void setLikeDislike(final String url) {
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
            return;
        }
        Log.e("URL L_D: ", url);

        progressShow.setVisibility(View.VISIBLE);
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

                        progressShow.setVisibility(View.GONE);

                        try {

                            final Typeface face_reg = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
                            Log.e("details response:", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();
                                allCommonResponse = g.fromJson(new String(response), AllCommonResponse.class);


                                if ((allCommonResponse.getMsg().equalsIgnoreCase("Successful")) && (allDetail.getIs_liked().equalsIgnoreCase("FALSE"))) {

                                    int current_likecount = 0;
                                    if (!(TextUtils.isEmpty(allDetail.getLike_count()))) {
                                        current_likecount = Integer.parseInt(allDetail.getLike_count());
                                    }

                                    current_likecount = current_likecount + 1;
                                    allDetail.setLike_count("" + current_likecount);
                                    allDetail.setIs_liked("TRUE");
                                    setAllData(false);
                                } else {

                                    int current_likecount = 0;

                                    if (!(TextUtils.isEmpty(allDetail.getLike_count()))) {
                                        current_likecount = Integer.parseInt(allDetail.getLike_count());
                                    }


                                    current_likecount = current_likecount - 1;
                                    allDetail.setLike_count("" + current_likecount);
                                    allDetail.setIs_liked("FALSE");
                                    setAllData(false);
                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            progressShow.setVisibility(View.GONE);
                        }


                    }
                });
            }
        });
    }


    private void setDislikeLike(final String url) {
        if (!NetInfo.isOnline(con)) {
            AlertMessage.showMessage(con, getString(R.string.app_name), "No Internet!");
            return;
        }
        Log.e("URL L_D: ", url);

        progressShow.setVisibility(View.VISIBLE);
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

                        progressShow.setVisibility(View.GONE);

                        try {

                            final Typeface face_reg = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
                            Log.e("details response:", ">>" + new String(response));
                            if (!TextUtils.isEmpty(new String(response))) {
                                Gson g = new Gson();
                                allCommonResponse = g.fromJson(new String(response), AllCommonResponse.class);


                                if ((allCommonResponse.getMsg().equalsIgnoreCase("Successful")) && (allDetail.getIs_disliked().equalsIgnoreCase("FALSE"))) {

                                    int current_dislikCount = 0;
                                    if (!(TextUtils.isEmpty(allDetail.getDislike_count()))) {
                                        current_dislikCount = Integer.parseInt(allDetail.getDislike_count());
                                    }


                                    current_dislikCount = current_dislikCount + 1;
                                    allDetail.setDislike_count("" + current_dislikCount);
                                    allDetail.setIs_disliked("TRUE");
                                    setAllData(false);
                                } else {

                                    int current_dislikCount = 0;
                                    if (!(TextUtils.isEmpty(allDetail.getDislike_count()))) {
                                        current_dislikCount = Integer.parseInt(allDetail.getDislike_count());
                                    }
                                    current_dislikCount = current_dislikCount - 1;
                                    allDetail.setDislike_count("" + current_dislikCount);
                                    allDetail.setIs_disliked("FALSE");
                                    setAllData(false);
                                }
                            }

                        } catch (final Exception e) {
                            e.printStackTrace();
                            progressShow.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    public void defaultShare(Context context, Uri uri) {
        String firstText = getResources().getString(R.string.pretex_details);
        String bodyText = allDetail.getNews().getDetails();
        bodyText = bodyText.replaceAll("<p>", "\n");
        bodyText = bodyText.replaceAll("</p>", "\n");
        String finalStr = firstText + "" + bodyText;

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name) + ": " + allDetail.getNews().getTitle());
        intent.putExtra(Intent.EXTRA_TEXT, finalStr);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        context.startActivity(Intent.createChooser(intent, "Share Image"));
    }

    private Target target = new Target() {


        @Override
        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
            new Thread(new Runnable() {

                @Override
                public void run() {

                    File file = new File(Environment.getExternalStorageDirectory().getPath() + "/picture.jpg");
                    try {
                        file.createNewFile();
                        FileOutputStream ostream = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                        busyDialog.dismis();
                        String path = MediaStore.Images.Media.insertImage(con.getContentResolver(), bitmap, "Title", null);
                        defaultShare(con, Uri.parse(path));

                        ostream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            busyDialog.dismis();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            if (placeHolderDrawable != null) {
            }
            busyDialog = new BusyDialog(con, false, "");
            busyDialog.show();
        }
    };



}
