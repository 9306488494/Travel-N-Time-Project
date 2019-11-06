package com.travelandtime;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.travelandtime.Configration.Config;
import com.travelandtime.Databases.TestDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


public class Testing extends AppCompatActivity {
    private LinearLayout resetLay;
    private EditText resetEmail;
    private Button resetBtn;
    private ImageView img;
    TestDatabase testDatabase;
    long queueid;
  String sr="2",link= "http://www.airindia.in/images/pdf/timetable.pdf";





    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        testDatabase=new TestDatabase(Testing.this);
        img = (ImageView) findViewById(R.id.img);


        fetchData();

        // search the record from database
        ArrayList<HashMap<String,String>> list=testDatabase.getDatabaseName(sr);
        for(HashMap<String,String> s:list)
        {
            if(Integer.parseInt(String.valueOf(s.get("res")))==0)
            {
               DownloadManager.Request dmr = new DownloadManager.Request(Uri.parse(link));
//Alternative if you don't know filename
                String fileName = URLUtil.guessFileName(link, null, MimeTypeMap.getFileExtensionFromUrl(link));
                //String fileName=GetFileExtension(link);
                //String fileName="file1."+link.substring(link.lastIndexOf(".") + 1);

                dmr.setTitle(fileName);
                dmr.setDescription("Itinary Offline Initilizing"); //optional
                dmr.allowScanningByMediaScanner();
                dmr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

                //String path=Environment.getExternalStorageDirectory()+File.separator+"Download/"+fileName;
                //String path="content://storage/emulated/0/"+"Download/"+fileName;
                //String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + fileName;
               //Log.e("PATH", path);
                dmr.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                dmr.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                DownloadManager manager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(dmr);
                //File file = new File("/sdcard/Download/E-Ticket.pdf");
                //File file = new File("/storage/emulated/0/Download/E-Ticket.pdf");
                //File file = new File(Environment.getExternalStorageDirectory() + "/Download/" + "E-Ticket.pdf");
                //File file = new File(path);

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                if(file.exists()){
                 /*   Uri path1 = Uri.fromFile(file);
                    Picasso.get().load(path1).into(img);*/
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                   Uri data = FileProvider.getUriForFile(Testing.this, BuildConfig.APPLICATION_ID +".provider",file);
                    intent.setDataAndType(data, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

            }

        }

       /* RequestQueue queue = Volley.newRequestQueue((Objects.requireNonNull(getApplicationContext())));
        StringRequest request = new StringRequest(Request.Method.POST, Config.packs_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                parseData11(response1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("token", "9306488494");
                mydata.put("cat", "International");
                return mydata;
            }
        };
        queue.add(request);*/
    }


    // fetch the record from database regarding Flights
    private void fetchData() {
        // search the record from database
        ArrayList<HashMap<String,String>> list=testDatabase.getDatabaseName("1","1");
        for(HashMap<String,String> s:list)
        {
            if(Integer.parseInt(String.valueOf(s.get("res")))>0)
            {
                String res=s.get("res");
                String sr=s.get("sr");
                String link=s.get("link");
                Toast.makeText(this,link, Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void parseData11(String response1) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response1);
            String status=jsonObject.getString("status");
            if(status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String sr = jsonObject1.getString("sr");
                    String title = jsonObject1.getString("title");
                    String desc1 = jsonObject1.getString("desc1");
                    String img = jsonObject1.getString("img");
                    String location = jsonObject1.getString("location");
                    String price = jsonObject1.getString("price");
                    String nop = jsonObject1.getString("nop");
                    String cat = jsonObject1.getString("cat");
                    String day = jsonObject1.getString("day");
                    String night = jsonObject1.getString("night");



                    // search the record from database
                    ArrayList<HashMap<String,String>> list=testDatabase.getDatabaseName(sr);
                    for(HashMap<String,String> s:list)
                    {
                        if(Integer.parseInt(String.valueOf(s.get("res")))==0)
                        {
                            // start download file in local host
                            DownloadManager.Request dmr = new DownloadManager.Request(Uri.parse(link));
                            //Alternative if you don't know filename
                            String fileName = URLUtil.guessFileName(link, null, MimeTypeMap.getFileExtensionFromUrl(link));
                            dmr.setTitle(fileName);
                            dmr.setDescription("Itinary Offline Initilizing"); //optional
                            dmr.allowScanningByMediaScanner();
                            dmr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                            dmr.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            dmr.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            DownloadManager manager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(dmr);


                            //testDatabase.insert(sr,link);
                        }
                    }
                }
            }
            else
            {
                Toast.makeText(this, "Server response", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
