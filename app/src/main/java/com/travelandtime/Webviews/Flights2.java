package com.travelandtime.Webviews;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.travelandtime.Configration.Config;
import com.travelandtime.Flights;
import com.travelandtime.MainActivity;
import com.travelandtime.R;

public class Flights2 extends AppCompatActivity {
    private WebView webView;
    ProgressDialog pd;
    String myUrl,myTitle;
    private TextView title;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flights2);
        webView = (WebView) findViewById(R.id.webView);
        pd=new ProgressDialog(this);
        pd.setTitle("Fetching Data...");
        pd.setMessage("Please wait...");
        title = (TextView) findViewById(R.id.title);

        // Receive Intent for url and setup url
        Intent intent=getIntent();
        myUrl=intent.getStringExtra("url");
        myTitle=intent.getStringExtra("myTitle");
        title.setText(myTitle);



        // Basic webview setting
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        // External Settings
        webView.getSettings().getTextZoom();
        webView.getSettings().getBuiltInZoomControls();
        webView.getSettings().getAllowUniversalAccessFromFileURLs();
        webView.getSettings().getAllowFileAccess();
        webView.getSettings().getCacheMode();
        webView.getSettings().getAllowContentAccess();
        webView.getSettings().getBlockNetworkImage();

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



    } // onCreate closer
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
            Intent intent=new Intent(Flights2.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        }
    }
    public class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            webView.loadUrl(myUrl);
            return true;
        }
    }




}
