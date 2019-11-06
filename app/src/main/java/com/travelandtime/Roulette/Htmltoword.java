package com.travelandtime.Roulette;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travelandtime.Chat.FriendChat;
import com.travelandtime.R;
import com.travelandtime.Social.Community;
import com.travelandtime.Utils.GetPresistenceData;

public class Htmltoword extends AppCompatActivity {
    private WebView webView;
    private LinearLayout titleLay;
    private TextView title;
    ProgressDialog pd;
    String frnd_imei,group,myUrl,imei,pic,frnd_name,frnd_pic,type;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flights2);

        webView = (WebView) findViewById(R.id.webView);
        titleLay = (LinearLayout) findViewById(R.id.title_lay);
        title = (TextView) findViewById(R.id.title);

        // hide the title
        titleLay.setVisibility(View.GONE);
        // progress dialogue box
        pd=new ProgressDialog(this);
        pd.setTitle("Fetching Data...");
        pd.setMessage("Please wait...");


        // imei parameters
        imei= GetPresistenceData.getMyIMEI(getApplicationContext());
        pic=GetPresistenceData.getMetaPic(getApplicationContext());

        // set Url

            myUrl = "http://travelntime.in/api/htmltoword/index.php";


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

// webView listner
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
                //Checking runtime permission for devices above Marshmallow.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        downloadDialog(s,s1,s2,s3);

                    } else {


                        //requesting permissions.
                        ActivityCompat.requestPermissions(Htmltoword.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                    }
                }
                else {
                    //Code for devices below API 23 or Marshmallow

                    downloadDialog(s,s1,s2,s3);

                }
            }
        });

    }// onCreate closer

    private void downloadDialog(String s, final String s1, String s2, String s3) {
        //getting filename from url.
        final String filename = URLUtil.guessFileName(s,s2,s3);
        //alertdialog
        AlertDialog.Builder builder=new AlertDialog.Builder(Htmltoword.this);
        //title of alertdialog
        builder.setTitle("Download");
        //message of alertdialog
        builder.setMessage("Do you want to save " +filename);
        //if Yes button clicks.
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //DownloadManager.Request created with url.
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(myUrl));
                //cookie
                String cookie= CookieManager.getInstance().getCookie(myUrl);
                //Add cookie and User-Agent to request
                request.addRequestHeader("Cookie",cookie);
                request.addRequestHeader("User-Agent",s1);
                //file scanned by MediaScannar
                request.allowScanningByMediaScanner();
                //Download is visible and its progress, after completion too.
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                //DownloadManager created
                DownloadManager downloadManager=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
                //Saving files in Download folder
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                //download enqued
                assert downloadManager != null;
                downloadManager.enqueue(request);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //cancel the dialog if Cancel clicks
                dialog.cancel();
            }

        });
        //alertdialog shows.
        builder.create().show();

    }



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
            Intent intent=new Intent(Htmltoword.this, Community.class);
            intent.putExtra("page","Community");
            intent.putExtra("lay","Minigames");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
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
