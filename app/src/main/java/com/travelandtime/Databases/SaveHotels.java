package com.travelandtime.Databases;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
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
import com.travelandtime.Utils.GetPresistenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SaveHotels extends AsyncTask<String,String,String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private HotelsDatabase hotelsDatabase;
    public SaveHotels(Context context, HotelsDatabase hotelsDatabase) {
        this.context=context;
        this.hotelsDatabase=hotelsDatabase;
    }


    @Override
    protected String doInBackground(String... strings) {
        if(GetPresistenceData.getMyRegStatus(context).equals("1"))
        {
            if(GetPresistenceData.getMyLogon(context).equals("1"))
            {
                RequestQueue queue = Volley.newRequestQueue((Objects.requireNonNull(context)));
                StringRequest request = new StringRequest(Request.Method.POST, Config.trip_hotel, new Response.Listener<String>() {
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
                        mydata.put("imei", GetPresistenceData.getMyIMEI(context));
                        mydata.put("email",GetPresistenceData.getEmail(context));
                        mydata.put("id","1");
                        return mydata;
                    }
                };
                queue.add(request);
            }
        }
        return null;
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
                    String reg_date = jsonObject1.getString("today_date");
                    String hotel_name = jsonObject1.getString("hotel_name");
                    String room_name = jsonObject1.getString("room_name");
                    String check_in = jsonObject1.getString("check_in");
                    String current_status = jsonObject1.getString("current_status");
                    String image_url = jsonObject1.getString("image_url");
                    String idd = jsonObject1.getString("idd");
                    String file_url = jsonObject1.getString("file_url");

                    // search the record from database
                    ArrayList<HashMap<String,String>> list=hotelsDatabase.getDatabaseName(sr);
                    for(HashMap<String,String> s:list)
                    {
                        if(Integer.parseInt(String.valueOf(s.get("res")))==0)
                        {
                            // start download file in local host
                            DownloadManager.Request dmr = new DownloadManager.Request(Uri.parse(file_url));
                            //Alternative if you don't know filename
                            String fileName = URLUtil.guessFileName(file_url, null, MimeTypeMap.getFileExtensionFromUrl(file_url));
                            dmr.setTitle(fileName);
                            dmr.setDescription("Hotels offline initilizing"); //optional
                            dmr.allowScanningByMediaScanner();
                            dmr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                            dmr.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            dmr.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                            DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(dmr);

                            hotelsDatabase.insert(sr,reg_date,hotel_name,room_name,check_in,current_status,image_url,idd,fileName);
                        }
                    }
                }
            }
            else
            {
               // Toast.makeText(context,Config.error_msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
