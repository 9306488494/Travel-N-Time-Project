package com.travelandtime.Bot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.travelandtime.Chat.FriendChat;
import com.travelandtime.R;
import com.travelandtime.Social.Community;
import com.travelandtime.Utils.GetPresistenceData;

public class Chatbot extends AppCompatActivity {
    private WebView webView;
    private LinearLayout titleLay;
    private TextView title;
    ProgressDialog pd;
    String myUrl,imei,name,mobb;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flights2);

        webView = (WebView) findViewById(R.id.webView);
        titleLay = (LinearLayout) findViewById(R.id.title_lay);
        title = (TextView) findViewById(R.id.title);
        titleLay.setVisibility(View.GONE);

        // progress dialogue box
        pd=new ProgressDialog(this);
        pd.setTitle("Fetching Data...");
        pd.setMessage("Please wait...");

        // Basic webview setting
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        // External Settings
        webView.getSettings().getTextZoom();
        webView.getSettings().getBuiltInZoomControls();
        webView.getSettings().getAllowUniversalAccessFromFileURLs();
        webView.getSettings().getAllowFileAccess();
        webView.getSettings().getCacheMode();
        webView.getSettings().getAllowContentAccess();
        webView.getSettings().getBlockNetworkImage();

        // collect parameters
        imei= GetPresistenceData.getMyIMEI(getApplicationContext());
        name=GetPresistenceData.getMetaName(getApplicationContext());
        mobb=GetPresistenceData.getMetaMobb(getApplicationContext());

        myUrl = "http://travelntime.in/api/chat3/index.php?imei=" + imei +"&name=" + name.toUpperCase() + "&mobb=" + mobb;

            //myUrl="file:///android_asset/index.php";


        // webCromeClient
        webView.setWebChromeClient(new WebChromeClient());
        /*webView.setWebViewClient(new WebViewClient());*/
        MyBrowser myBrowser = new MyBrowser();
        webView.setWebViewClient(myBrowser);

        loadWebview();
        //set webclient
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

    }// onCreate closer
    private void loadWebview() {
        webView.loadUrl(myUrl);
    }

    // Next forword Actions
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        }
        else
        {
            Intent intent=new Intent(Chatbot.this, Community.class);
            startActivity(intent);
            finish();
        }
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            webView.loadUrl(myUrl);
            return true;
        }
    }

}
