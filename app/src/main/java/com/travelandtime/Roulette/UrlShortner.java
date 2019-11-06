package com.travelandtime.Roulette;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.travelandtime.Adapters.RecyclerAdapterInt;
import com.travelandtime.Configration.Config;
import com.travelandtime.GetterSetter.Links;
import com.travelandtime.R;
import com.travelandtime.Social.Community;
import com.travelandtime.Utils.GetPresistenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UrlShortner extends AppCompatActivity {
    private LinearLayout urlShortnerLay;
    private TextView title;
    private EditText urlText;
    private Button shortUrlBtn;
    private TextView url_Text;
    private ImageView copyBtn;
    RequestQueue queue;
    private LinearLayout showUrlText;
    private LinearLayout currenStatesLay;
    private RecyclerView recState;
    ArrayList<Links>LinksNew;








    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.urlshortner);

        urlShortnerLay = (LinearLayout) findViewById(R.id.url_shortner_lay);
        title = (TextView) findViewById(R.id.title);

        urlText = (EditText) findViewById(R.id.url_text);
        shortUrlBtn = (Button) findViewById(R.id.short_url_btn);
        url_Text = (TextView) findViewById(R.id.urlText);
        copyBtn = (ImageView) findViewById(R.id.copy_btn);
        showUrlText = (LinearLayout) findViewById(R.id.show_url_text);
        currenStatesLay = (LinearLayout) findViewById(R.id.curren_states_lay);
        recState = (RecyclerView) findViewById(R.id.recState);

        // Initilize arraylist
        LinksNew=new ArrayList<>();

        // fetch my link states
        new linkState(GetPresistenceData.getMyIMEI(getApplicationContext())).execute();

        // copy the text
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copies", url_Text.getText().toString());
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(UrlShortner.this, "Copied", Toast.LENGTH_SHORT).show();

            }
        });

        // short url code
        shortUrlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=urlText.getText().toString();
                if(url.equals("") || url.length()<7)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid url you have entered",Snackbar.LENGTH_SHORT).show();
                    urlText.requestFocus();
                    urlText.setText("");
                }
                else
                {
                    if(url.contains("www"))
                    {
                        shortMyUrl(url);
                    }
                    else if(url.contains("http"))
                    {
                        shortMyUrl(url);
                    }
                    else
                    {
                        Snackbar.make(getWindow().getDecorView().getRootView(),"Sorry, We are unable to detect right URL",Snackbar.LENGTH_SHORT).show();
                    }

                }
            }
        });




    } //onCreate closer

    private void shortMyUrl(final String url) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.url_shortner, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(String response1) {
                parseData1(response1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Server not responding", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("url",url);
                mydata.put("imei", GetPresistenceData.getMyIMEI(getApplicationContext()));
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData1(String response1) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response1);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {
                String generated_url = jsonObject.getString("new_url");
                url_Text.setText(generated_url);
                urlText.setText("");
                showUrlText.setVisibility(View.VISIBLE);
            }
            else
            {
                Toast.makeText(this, "Unable to short your url", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class linkState extends AsyncTask<String,String,String> {
        String imei;
        public linkState(String imei) {
            this.imei=imei;
        }

        @Override
        protected String doInBackground(String... strings) {
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue=new RequestQueue(cache1,network1);
            queue.start();
            StringRequest request = new StringRequest(Request.Method.POST, Config.my_short_link_states, new Response.Listener<String>() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onResponse(String response2) {
                    parseData2(response2);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Server not responding", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> mydata = new HashMap<>();
                    mydata.put("imei", GetPresistenceData.getMyIMEI(getApplicationContext()));
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

    private void parseData2(String response2) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response2);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {

                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String new_link = jsonObject1.getString("new_link");
                    String link = jsonObject1.getString("link");
                    String total_click = jsonObject1.getString("total_click");
                    String sr = jsonObject1.getString("sr");
                    currenStatesLay.setVisibility(View.VISIBLE);

                    Links link2=new Links(sr,link,new_link,total_click);
                    LinksNew.add(link2);

                    final RecLinks recLinks = new RecLinks(getApplicationContext(), LinksNew);

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    recState.setLayoutManager(layoutManager);
                    recState.setHasFixedSize(true);
                    recState.setAdapter(recLinks);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
