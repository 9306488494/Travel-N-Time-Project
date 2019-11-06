package com.travelandtime.Databases;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.travelandtime.Configration.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SaveIntpack extends AsyncTask<String,String,String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private InternationDatabase intDatabase;

    public SaveIntpack(Context context, InternationDatabase intDatabase) {
        this.context = context;
        this.intDatabase = intDatabase;
    }


    @Override
    protected String doInBackground(String... strings) {


        RequestQueue queue = Volley.newRequestQueue((Objects.requireNonNull(context)));
        StringRequest request = new StringRequest(Request.Method.POST, Config.packs_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                parseData11(response1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        queue.add(request);


        return null;
    }

    private void parseData11(String response1) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response1);
            String status = jsonObject.getString("status");
            if (status.equals("1")) {
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
                    ArrayList<HashMap<String,String>> list=intDatabase.getDatabaseName(sr);
                    for(HashMap<String,String> s:list)
                    {
                        if(Integer.parseInt(String.valueOf(s.get("res")))==0)
                        {
                          /*  // start download file in local host
                            DownloadManager.Request dmr = new DownloadManager.Request(Uri.parse(img));
                            //Alternative if you don't know filename
                            String fileName = URLUtil.guessFileName(img, null, MimeTypeMap.getFileExtensionFromUrl(img));
                            dmr.setTitle(fileName);
                            dmr.setDescription("International offline package initilizing"); //optional
                            dmr.allowScanningByMediaScanner();
                            dmr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                            dmr.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            dmr.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(dmr);*/

                            intDatabase.insert(sr,title,desc1,img,location,price,nop,cat,day,night);
                        }
                    }
                }
            } else {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}