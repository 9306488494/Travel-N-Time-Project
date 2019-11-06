package com.travelandtime;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.travelandtime.Configration.Config;
import com.travelandtime.Databases.DompackDatabase;
import com.travelandtime.Databases.FlightsDatabase;
import com.travelandtime.Databases.HotelsDatabase;
import com.travelandtime.Databases.InternationDatabase;
import com.travelandtime.Databases.ItiDatabase;
import com.travelandtime.Databases.SaveDomPack;
import com.travelandtime.Databases.SaveFlights;
import com.travelandtime.Databases.SaveHotels;
import com.travelandtime.Databases.SaveIntpack;
import com.travelandtime.Databases.SaveIti;
import com.travelandtime.Nstate.NetworkNot;
import com.travelandtime.Utils.AppStatus;
import com.travelandtime.Utils.GetPresistenceData;
import com.travelandtime.Utils.PresistenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



/**
 * Created by Yeshveer on 7/1/2019.
 */

public class Splash extends AppCompatActivity {
    int reqcode = 1;
    String Check_ver, ver, IMEI,fest_img,date1;
    public static final String REQUEST_TAG = "VolleyBlockingRequestActivity";
    RequestQueue queue,queue1;
    FlightsDatabase flightsDatabase;
    HotelsDatabase hotelsDatabase;
    ItiDatabase itiDatabase;
    DompackDatabase domPacks;
    InternationDatabase intDatabase;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        flightsDatabase=new FlightsDatabase(Splash.this);
        hotelsDatabase=new HotelsDatabase(Splash.this);
        itiDatabase=new ItiDatabase(Splash.this);
        domPacks=new DompackDatabase(Splash.this);
        intDatabase=new InternationDatabase(Splash.this);

        permissions();

        // set presistence and create
        PresistenceData.metaStatus(getApplicationContext(),"0");
        // save offline data




    } // onCreate closer

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void permissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] per = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE};
            requestPermissions(per, reqcode);
        }
    }
    
    // permission result
    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int i = 0; i <= permissions.length; i++) {

            if (i == 4) {

                if (requestCode == reqcode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Permission Granted",Snackbar.LENGTH_SHORT).show();
                    checkCurrentVersion();
                }
                else
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Need Permission for better results",Snackbar.LENGTH_SHORT).show();
                    permissions();
                }
            }
        }

    }

    // check current version
    private void checkCurrentVersion() {
        if (AppStatus.getInstance(this).isOnline()) {


            // Check Versions of the app
            PackageManager manager = this.getPackageManager();
            PackageInfo info = null;
            try {
                info = manager.getPackageInfo(this.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            Check_ver = Objects.requireNonNull(info).versionName;
            // volley util
            queue = Volley.newRequestQueue((Objects.requireNonNull(getApplicationContext())));
            StringRequest request = new StringRequest(Request.Method.POST, Config.version, new Response.Listener<String>() {
                @Override
                public void onResponse(String response1) {
                    parseData(response1);
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
                    mydata.put("token","9306488494");
                    return mydata;
                }
            };
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            queue.add(request);
            //close parse
        } else {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent1 = new Intent(Splash.this, NetworkNot.class);
                    startActivity(intent1);
                    finish();
                }
            }, 1000);

        }
    }

    @SuppressLint("HardwareIds")
    private void parseData(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                ver = jsonObject1.getString("ver");
                fest_img = jsonObject1.getString("fest_img");
                date1 = jsonObject1.getString("date1");
                // save festivals presistence
                PresistenceData.Festivals(getApplicationContext(),fest_img);
                if(GetPresistenceData.getCloseFestDate(getApplicationContext())==null)
                {

                    PresistenceData.CloseFest(getApplicationContext(),"0",date1);

                }
                else if(GetPresistenceData.getCloseFestDate(getApplicationContext()).equals(date1))
                {
                    // already set
                }
                else
                {
                    if(fest_img.equals("0")) {
                        PresistenceData.CloseFest(getApplicationContext(), fest_img,date1);
                    }
                    else
                    {
                        PresistenceData.CloseFest(getApplicationContext(), "0",date1);
                    }
                }

                if(ver.equals(""))
                {
                    Log.e("Checkpost","Checking version");
                }
                else {
                    if (ver.equals(Check_ver)) {
                        // create directory
                        final String Paths = Environment.getExternalStorageDirectory() + File.separator + "TnT" + File.separator + "MetaData" + File.separator;
                        File dir1;
                        dir1 = new File(Paths);
                        if (!dir1.isDirectory()) {
                            if (!dir1.exists()) {
                                dir1.mkdirs();
                                Toast.makeText(Splash.this, "Directory Created", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // check user is register or not
                        checkRegister();

                    } else {
                        Toast.makeText(this, "Update your application", Toast.LENGTH_SHORT).show();
                        Uri uri = Uri.parse("market://details?id=" + getPackageName());
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        // To count with Play market backstack, After pressing back button,
                        // to taken back to our application, we need to add following flags to intent.
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        startActivity(goToMarket);
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkRegister() {
        new SaveDomPack(getApplicationContext(),domPacks).execute();
        new SaveIntpack(getApplicationContext(),intDatabase).execute();


        if(GetPresistenceData.getMyIMEI(getApplicationContext()) == null)
        {
            PresistenceData.checkRegistration(getApplicationContext(),"0");
            PresistenceData.checkLogon(getApplicationContext(),"0");
            PresistenceData.FlightsLogin(getApplicationContext(),"0");
            PresistenceData.HotelLogin(getApplicationContext(),"0");
            PresistenceData.ItiLogin(getApplicationContext(),"0");


            Intent intent = new Intent(Splash.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
            finish();
        }
        else
        {
            //save offline data
            new SaveFlights(getApplicationContext(),flightsDatabase).execute();
            new SaveHotels(getApplicationContext(),hotelsDatabase).execute();
            new SaveIti(getApplicationContext(),itiDatabase).execute();


            PresistenceData.checkRegistration(getApplicationContext(),"1");
            PresistenceData.FlightsLogin(getApplicationContext(),"1");
            PresistenceData.HotelLogin(getApplicationContext(),"1");
            PresistenceData.ItiLogin(getApplicationContext(),"1");


            Intent intent = new Intent(Splash.this,MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
            finish();
        }
    }

    // kill process on stop method
    @Override
    protected void onStop() {
        super.onStop();
        if(queue !=null)
        {
            queue.cancelAll(REQUEST_TAG);
        }


    }

}
