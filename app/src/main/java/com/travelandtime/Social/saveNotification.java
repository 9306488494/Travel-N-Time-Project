package com.travelandtime.Social;

import android.content.Context;
import android.os.AsyncTask;
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
import com.travelandtime.Configration.Config;

import java.util.HashMap;
import java.util.Map;

class saveNotification extends AsyncTask<String,String,String> {
    String text,metaName,msg,type;
    Context mcontext;
    RequestQueue queue;

    public saveNotification(String text, String metaName, String msg, String type, Context mcontext, RequestQueue queue) {
        this.text=text;
        this.metaName=metaName;
        this.msg=msg;
        this.type=type;
        this.mcontext=mcontext;
        this.queue=queue;
    }

    @Override
    protected String doInBackground(String... strings) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.notification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response8) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mcontext, "Server not responding", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei",text);
                mydata.put("name",metaName);
                mydata.put("msgg",msg);
                mydata.put("type",type);

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
