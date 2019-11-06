package com.travelandtime.Webviews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.travelandtime.MainActivity;
import com.travelandtime.R;
import com.travelandtime.Trip;

import java.util.Objects;

public class PropertyView extends AppCompatActivity {
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
        if(Objects.requireNonNull(intent.getStringExtra("url")).contains("http") || intent.getStringExtra("url").contains("www"))
        {
            myUrl=intent.getStringExtra("url");
        }
        else
        {
            myUrl="https://www.google.com/search?q="+intent.getStringExtra("url");
        }

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
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setDomStorageEnabled(true);

        // webCromeClient
        webView.setWebChromeClient(new WebChromeClient());
        /*webView.setWebViewClient(new WebViewClient());*/
        PropertyView.MyBrowser myBrowser = new PropertyView.MyBrowser();
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
            Intent intent=new Intent(PropertyView.this, Trip.class);
            startActivity(intent);
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
