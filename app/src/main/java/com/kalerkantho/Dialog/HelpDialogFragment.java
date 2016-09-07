package com.kalerkantho.Dialog;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bangladesh_pratidin.R;
import com.kalerkantho.Utils.AllURL;

public class HelpDialogFragment extends DialogFragment {

    private Context con;
    private View view;
    private WebView helpWeb;
    private ImageView dissmissHelpBtn;
    private ProgressBar progressHelp;
    private TextView helpHeadText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.help_dialog, container, true);
        con = getActivity();
        initUi();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initUi() {

        //final Typeface face_normal = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_reg.ttf");
        final Typeface face_bold = Typeface.createFromAsset(con.getAssets(), "fonts/SolaimanLipi_Bold.ttf");
        dissmissHelpBtn = (ImageView) view.findViewById(R.id.dissmissHelpBtn);
        helpWeb = (WebView) view.findViewById(R.id.helpWeb);
        progressHelp = (ProgressBar) view.findViewById(R.id.progressHelp);
        helpHeadText = (TextView) view.findViewById(R.id.tvLogin);

        helpHeadText.setText(getString(R.string.help));
        helpHeadText.setTypeface(face_bold);

        dissmissHelpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });


        getWebView(AllURL.getHelp());
    }

    public void getWebView(String url) {
        final CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        CookieSyncManager.createInstance(con);

        helpWeb.clearCache(true);
        helpWeb.clearHistory();

        helpWeb.getSettings().setJavaScriptEnabled(true);
        helpWeb.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        helpWeb.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        helpWeb.getSettings().setUserAgentString("silly_that_i_have_to_do_this");
        helpWeb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        helpWeb.getSettings().setDomStorageEnabled(true);
        helpWeb.getSettings().setLoadWithOverviewMode(true);
        helpWeb.getSettings().setUseWideViewPort(true);
        helpWeb.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        helpWeb.setScrollbarFadingEnabled(true);
        helpWeb.loadUrl(url);

        helpWeb.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressHelp.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressHelp.setVisibility(View.GONE);

            }
        });
    }
}
