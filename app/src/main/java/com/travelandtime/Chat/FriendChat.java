package com.travelandtime.Chat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.TextKeyListener;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.travelandtime.MainActivity;
import com.travelandtime.R;
import com.travelandtime.Social.Community;
import com.travelandtime.Utils.GetPresistenceData;
import com.travelandtime.Webviews.Flights2;

public class FriendChat extends AppCompatActivity {
    private WebView webView;
    private TextView title;
    ProgressDialog pd;
    String frnd_imei,group,myUrl,imei,pic,frnd_name,frnd_pic,type;
    private LinearLayout titleLay;






    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flights2);
        webView = (WebView) findViewById(R.id.webView);
        title = (TextView) findViewById(R.id.title);
        titleLay = (LinearLayout) findViewById(R.id.title_lay);
        titleLay.setVisibility(View.GONE);
        // progress dialogue box
        pd=new ProgressDialog(this);
        pd.setTitle("Fetching Data...");
        pd.setMessage("Please wait...");

        // Receiving intent
        Intent intent=getIntent();
        frnd_imei=intent.getStringExtra("frnd_imei");
        group=intent.getStringExtra("group");
        frnd_name=intent.getStringExtra("frnd_name");
        frnd_pic=intent.getStringExtra("frnd_pic");
        type=intent.getStringExtra("type");

        // imei parameters
        imei= GetPresistenceData.getMyIMEI(getApplicationContext());
        pic=GetPresistenceData.getMetaPic(getApplicationContext());

        // set Url
        if (type.equals("1")) {
            myUrl = "http://travelntime.in/api/chat/index.php?imei=" + imei + "&frnd_imei=" + frnd_imei + "&name=" + frnd_name.toUpperCase() + "&pic=" + frnd_pic;
            //myUrl="file:///android_asset/index.php";
        }
        else
        {
            myUrl = "http://travelntime.in/api/chat2/index.php?imei="+imei+"&checksum="+group+"&groupname="+frnd_imei+"&frnd_name="+frnd_name;
        }

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
            Intent intent=new Intent(FriendChat.this, Community.class);
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
