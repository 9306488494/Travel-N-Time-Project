package com.travelandtime.PackageClasses;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
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


import com.travelandtime.Adapters.PackAdapter;
import com.travelandtime.Configration.Config;
import com.travelandtime.Inter;
import com.travelandtime.MainActivity;
import com.travelandtime.R;

public class International extends AppCompatActivity {
    private RecyclerView allpackRec;
    private ArrayList<Inter> international;
    RequestQueue queue;
    public static final String REQUEST_TAG = "VolleyBlockingRequestActivity";
    String pack_type;
    ProgressDialog pd;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.international_lay);

        allpackRec = (RecyclerView) findViewById(R.id.allpackRec);

        // initilise array
        international=new ArrayList<>();

        // Receiveing intent
        Intent intent=getIntent();
        pack_type=intent.getStringExtra("pack_type");

        // setup Progress dialogue box
        pd=new ProgressDialog(this);
        pd.setTitle("Fetching data..");
        pd.setMessage("Processing...");
        pd.setCancelable(false);
        pd.show();

        // now parse the array for packages
        new PacksVolley(pack_type).execute("","","");



    } // onCreate closer

    // close all connections on onStop
    @Override
    protected void onStop() {
        super.onStop();
        if(queue !=null)
        {
            queue.cancelAll(REQUEST_TAG);
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class PacksVolley extends AsyncTask<String,String,String> {
        PacksVolley(String pack_type) {
        }

        @Override
        protected String doInBackground(String... strings) {

            // Instantiate the cache
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue=new RequestQueue(cache1,network1);
            queue.start();
            StringRequest request = new StringRequest(Request.Method.POST, Config.packs_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    parseData(response);                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(International.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> mydata = new HashMap<>();
                    mydata.put("token", "9306488494");
                    mydata.put("cat",pack_type);

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
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            pd.dismiss();
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


                Inter inter = new Inter(sr, title, desc1, img, location, price, nop, cat, day, night);
                international.add(inter);

                final PackAdapter packAdapter = new PackAdapter(getApplicationContext(), international);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                allpackRec.setLayoutManager(layoutManager);
                allpackRec.setHasFixedSize(true);
                allpackRec.setAdapter(packAdapter);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // back key pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {
            Intent back1Intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(back1Intent);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
