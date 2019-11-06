package com.travelandtime;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import com.travelandtime.Adapters.RPhoto;
import com.travelandtime.Configration.Config;
import com.travelandtime.GetterSetter.Photo1;

public class Photos extends AppCompatActivity {
    String ID;
    public static final String REQUEST_TAG = "VolleyBlockingRequestActivity";
    private RecyclerView photosRec;
    ArrayList<Photo1>photo1;
    RequestQueue queue;
    // progress dialog box
    ProgressDialog pd;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos);

        photosRec = (RecyclerView) findViewById(R.id.photosRec);

        // Receive intents
        Intent intent=getIntent();
        ID=intent.getStringExtra("id");

        // Initilize array
        photo1=new ArrayList<>();
        new Volleypics(ID).execute("","","");

        // setup Progress dialogue box
        pd=new ProgressDialog(this);
        pd.setTitle("Fetching data..");
        pd.setMessage("Processing...");
        pd.setCancelable(false);
        pd.show();





    } // onCreate closer

    @SuppressLint("StaticFieldLeak")
    public class Volleypics extends AsyncTask<String,String,String> {
        public Volleypics(String id) {
        }

        @Override
        protected String doInBackground(String... strings) {
            // Instantiate the cache
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue=new RequestQueue(cache1,network1);
            queue.start();
            StringRequest request = new StringRequest(Request.Method.POST, Config.pack_pics_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    parseData(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Photos.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> mydata = new HashMap<>();
                    mydata.put("id",ID);

                    return mydata;
                }
            };
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            queue.add(request);

            return null;
        }
    }

    private void parseData(String response) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response);

        String status = jsonObject.getString("status");
        if(status.equals("1")) {
            pd.dismiss();
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String sr = jsonObject1.getString("sr");
                String pack_id = jsonObject1.getString("pack_id");
                String url = jsonObject1.getString("url");


                Photo1 photo2 = new Photo1(sr, pack_id, url);
                photo1.add(photo2);

                RPhoto rPhoto = new RPhoto(getApplicationContext(), photo1);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                photosRec.setLayoutManager(layoutManager);
                photosRec.setHasFixedSize(true);
                photosRec.setAdapter(rPhoto);

            }
        }
        else
        {

            this.finish();
            Toast.makeText(this, "No More pics found", Toast.LENGTH_SHORT).show();
            pd.dismiss();
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // close all connections on onStop
    @Override
    protected void onStop() {
        super.onStop();
        if(queue !=null)
        {
            queue.cancelAll(REQUEST_TAG);
        }

    }

}
