package com.travelandtime;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.travelandtime.Adapters.RecyclerAdapterDom;
import com.travelandtime.Adapters.RecyclerAdapterInt;
import com.travelandtime.Configration.Config;
import com.travelandtime.Confirmations.Confirms;
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
import com.travelandtime.PackageClasses.Choice;
import com.travelandtime.PackageClasses.International;
import com.travelandtime.Social.Community;
import com.travelandtime.Utils.GetPresistenceData;
import com.travelandtime.Utils.PresistenceData;
import com.travelandtime.Webviews.Flights2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    AlertDialog.Builder builder;
    String data = "For best Tour and Travel packages  \n Travel n Time \n We make you travel in Time \n\n +91-124-4222401/402/403 \n\n Visit here : http://www.travelntime.com";
    public ArrayList<Domestic> domestic;
    private ArrayList<Inter> international;
    public static final String REQUEST_TAG = "VolleyBlockingRequestActivity";
    RequestQueue queue, queue1,queue2;
    RecyclerView domesticRec,interRec;
    String imei,myLogin_status,myLogon;
    ProgressDialog pd;
    private Button domesticViewAll,interViewall;
    RequestQueue queue4;
    FlightsDatabase flightsDatabase;
    HotelsDatabase hotelsDatabase;
    ItiDatabase itiDatabase;
    DompackDatabase domPacks;
    InternationDatabase intDatabase;
    private ImageView logo,closeFestImg;
    private GifImageView festImg;






    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        domesticRec = (RecyclerView) findViewById(R.id.domesticRec);
        interRec = (RecyclerView) findViewById(R.id.interRecc);
        CardView flight = findViewById(R.id.flight);
        CardView hotelClick = findViewById(R.id.hotelClick);
        LinearLayout packageImg = findViewById(R.id.packageImg);
        BottomNavigationView navigationView = findViewById(R.id.navigationView);
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        domesticViewAll = (Button) findViewById(R.id.domesticViewAll);
        interViewall = (Button) findViewById(R.id.interViewall);
        flightsDatabase=new FlightsDatabase(MainActivity.this);
        hotelsDatabase=new HotelsDatabase(MainActivity.this);
        itiDatabase=new ItiDatabase(MainActivity.this);
        domPacks=new DompackDatabase(MainActivity.this);
        intDatabase=new InternationDatabase(MainActivity.this);
        logo = (ImageView) findViewById(R.id.logo);
        festImg = (GifImageView) findViewById(R.id.fest_img);
        closeFestImg = (ImageView) findViewById(R.id.close_fest_img);



        // initilize array
        domestic = new ArrayList<>();
        international = new ArrayList<>();

        /*
        pass intent before landing on trip , first check user is logon , if not then logon otherwise register or login from presestence
        * Notes : status 0 intent- for Login
        * Notes : status 1 intent- for Registration
        * */

        // initilize package data
        if(GetPresistenceData.getHotelLogin(MainActivity.this).equals("1"))
        {
            fetchDomesticPacks();
            fetchInternationalPacks();
            if(GetPresistenceData.getMyIMEI(getApplicationContext()) != null)
            {
                notification(GetPresistenceData.getMyIMEI(getApplicationContext()),"","","2");
            }
        }
        else
        {
            new domesticData().execute("","","");
            new InternationalPack().execute("","","");
            if(GetPresistenceData.getMyIMEI(getApplicationContext()) != null)
            {
                notification(GetPresistenceData.getMyIMEI(getApplicationContext()),"","","2");
            }
        }

        // initilize progress dialogue box
        pd=new ProgressDialog(this);
        pd.setTitle("Analyzing...");
        pd.setMessage("Checking..");



        // get values
        imei= GetPresistenceData.getMyIMEI(getApplicationContext());


        // Initilize the Alert box
        builder = new AlertDialog.Builder(MainActivity.this);

        // show festivals image
        String fest_img=GetPresistenceData.getFestivals(getApplicationContext());
        if(fest_img.equals("0"))
        {

        }
        else
        {
            if(GetPresistenceData.getCloseFest(getApplicationContext()).equals("0")) {
                Picasso.get().load(fest_img).into(festImg);
                festImg.setVisibility(View.VISIBLE);
                closeFestImg.setVisibility(View.VISIBLE);
            }
            else
            {

            }
        }

        // click to close festival image and save presistence and close offers
        closeFestImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String date=GetPresistenceData.getCloseFestDate(getApplicationContext());

            PresistenceData.CloseFest(getApplicationContext(),"1",date);
                festImg.setVisibility(View.GONE);
                closeFestImg.setVisibility(View.GONE);
            }
        });
        // click on flight card view
        flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Coming Soon", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this, Flights2.class);
                intent.putExtra("url",Config.flight_url);
                intent.putExtra("myTitle","Flights");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
                finish();
            }
        });

        // click on Hotel Card view
        hotelClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"Coming Soon", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainActivity.this, Flights2.class);
                intent.putExtra("url",Config.hotel_url);
                intent.putExtra("myTitle","Hotels");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
                finish();
            }
        });

        // set on Click listner
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, Confirms.class);
                intent.putExtra("page","MyProfile");
                intent.putExtra("lay","Aboutus");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });

        // Navigation view links
        // Navigation Views
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               /* if(R.id.mhome==item.getItemId())
                {
                    DisplayAct.viewPager2.setCurrentItem(0);
                }
                else*/
                if (R.id.travel == item.getItemId()) {
                    //Toast.makeText(MainActivity.this,"Coming Soon", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this, Flights2.class);
                    intent.putExtra("url",Config.flight_url);
                    intent.putExtra("myTitle","Flights");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                    finish();

                } else if (R.id.trips == item.getItemId()) {
                    pd.show();
                    checKUserisRegister();

                }
                else if (R.id.profile == item.getItemId()) {
                    checkProfile1();
                }
                else if (R.id.social == item.getItemId()) {
                  if(GetPresistenceData.getMyIMEI(getApplicationContext())==null)
                  {
                      Intent intent=new Intent(MainActivity.this,RegisterMe.class);
                      intent.putExtra("doIt","4");
                      startActivity(intent);
                      overridePendingTransition(R.anim.enter,R.anim.exit);
                  }
                  else
                  {
                     if(GetPresistenceData.getMyRegStatus(getApplicationContext()).equals("1"))
                     {
                         if(GetPresistenceData.getMyLogon(getApplicationContext()).equals("1"))
                         {
                             CollectMetaData();
                         }
                         else
                         {
                             Intent intent=new Intent(MainActivity.this,RegisterMe.class);
                             intent.putExtra("doIt","4");
                             startActivity(intent);
                             overridePendingTransition(R.anim.enter,R.anim.exit);
                         }
                     }
                     else
                     {
                         Intent intent=new Intent(MainActivity.this,RegisterMe.class);
                         intent.putExtra("doIt","4");
                         startActivity(intent);
                         overridePendingTransition(R.anim.enter,R.anim.exit);
                     }
                  }
                }
                return true;
            }
        });
        // Navigation view closed



        // click on domestic view all button
        domesticViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this, International.class);
            intent.putExtra("pack_type","Domestic");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
        // click on international view all button
        interViewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, International.class);
                intent.putExtra("pack_type","International");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);


            }
        });
        // click on package and send to an activity
        packageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Choice.class);
                intent.putExtra("Option","Choice");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);

            }
        });

 }// onCreate closer

    private void notification(final String myIMEI, final String name, final String msg, final String type) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.notification, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onResponse(String response8) {
                parseData8(response8);
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
                mydata.put("imei",myIMEI);
                mydata.put("name",name);
                mydata.put("msgg",msg);
                mydata.put("type",type);

                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }


    private void parseData8(String response8) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response8);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String msgg = jsonObject1.getString("msgg");
                    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent intent = null;
                    intent = new Intent(this, Community.class);
                    String channelId = getString(R.string.default_web_client_id);
                    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder mBuilder = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                        mBuilder = new NotificationCompat.Builder(this,channelId).setSmallIcon(R.drawable.ic_launcher_background).setAutoCancel(true)
                                .setContentIntent(contentIntent).setSound(alarmSound).setContentTitle("Travel n Time").setContentText(msgg).setColor(getColor(R.color.drawable_red)).setSmallIcon(R.drawable.smallicon);
                    }
                    // mBuilder.setContentIntent(contentIntent);
                    assert mNotificationManager != null;
                    mNotificationManager.notify((int) System.currentTimeMillis() % Integer.MAX_VALUE, mBuilder.build());


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fetchInternationalPacks() {
        // search the record from database
        ArrayList<HashMap<String,String>> list=intDatabase.getDatabaseName("1","1");
        for(HashMap<String,String> s:list)
        {
            if(Integer.parseInt(String.valueOf(s.get("res")))>0)
            {
                String res=s.get("res");
                String sr=s.get("sr");
                String title=s.get("title");
                String desc1=s.get("desc1");
                String img=s.get("img");
                String location=s.get("location");
                String price=s.get("price");
                String nop=s.get("nop");
                String cat=s.get("cat");
                String day=s.get("day");
                String night=s.get("night");

                Inter inter = new Inter(sr, title, desc1, img, location, price, nop, cat, day, night);
                international.add(inter);

                final RecyclerAdapterInt recyclerAdapterInt = new RecyclerAdapterInt(getApplicationContext(), international);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                interRec.setLayoutManager(layoutManager);
                interRec.setHasFixedSize(true);
                interRec.setAdapter(recyclerAdapterInt);
            }
        }
    }

    private void fetchDomesticPacks() {
        // search the record from database
        ArrayList<HashMap<String,String>> list=domPacks.getDatabaseName("1","1");
        for(HashMap<String,String> s:list)
        {
            if(Integer.parseInt(String.valueOf(s.get("res")))>0)
            {
                String res=s.get("res");
                String sr=s.get("sr");
                String title=s.get("title");
                String desc1=s.get("desc1");
                String img=s.get("img");
                String location=s.get("location");
                String price=s.get("price");
                String nop=s.get("nop");
                String cat=s.get("cat");
                String day=s.get("day");
                String night=s.get("night");


                Domestic domm = new Domestic(sr, title, desc1, img, location, price, nop, cat, day, night);
                domestic.add(domm);

                RecyclerAdapterDom recyclerAdapterDom = new RecyclerAdapterDom(getApplicationContext(), domestic);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                domesticRec.setLayoutManager(layoutManager);
                domesticRec.setHasFixedSize(true);
                domesticRec.setAdapter(recyclerAdapterDom);

            }

        }
    }

    private void checkProfile1() {
        if(GetPresistenceData.getMyIMEI(getApplicationContext())==null)
        {
            Intent intent=new Intent(MainActivity.this,RegisterMe.class);
            intent.putExtra("doIt","3");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            if(GetPresistenceData.getMyRegStatus(getApplicationContext()).equals("1"))
            {
                if(GetPresistenceData.getMyLogon(getApplicationContext()).equals("1"))
                {
                    new checKUserfillForm("3").execute("","","");
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this,RegisterMe.class);
                    intent.putExtra("doIt","3");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
            else
            {
                Intent intent=new Intent(MainActivity.this,RegisterMe.class);
                intent.putExtra("doIt","3");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        }
    }

    private void CollectMetaData() {
        // Instantiate the cache
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue4=new RequestQueue(cache1,network1);
        queue4.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.my_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                profileMetaData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei", GetPresistenceData.getMyIMEI(getApplicationContext()));
                mydata.put("email",GetPresistenceData.getEmail(getApplicationContext()));
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue4.add(request);
    }

    private void profileMetaData(String response) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String name = jsonObject1.getString("name");
                    String mobb = jsonObject1.getString("mobb");
                    String state = jsonObject1.getString("state");
                    String email = jsonObject1.getString("email");
                    String my_pic = jsonObject1.getString("my_pic");
                    String points = jsonObject1.getString("points");
                    String posts = jsonObject1.getString("posts");
                    // set wallet presistence
                    PresistenceData.Wallets(getApplicationContext(),points,posts);
                    /// set Parameters
                    PresistenceData.metaData(getApplicationContext(), name,mobb,state,email,my_pic);
                    PresistenceData.metaStatus(getApplicationContext(),"1");

                    Intent intent=new Intent(MainActivity.this, Community.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter,R.anim.exit);

                }
            }
            else
            {
                Toast.makeText(this, "Unable to load profile data", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    private void checKUserisRegister() {
        if(GetPresistenceData.getMyIMEI(getApplicationContext())==null)
        {
            Intent intent=new Intent(MainActivity.this,RegisterMe.class);
            intent.putExtra("doIt","2");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
        }
        else
        {
            if(GetPresistenceData.getMyRegStatus(getApplicationContext()).equals("1"))
            {
                if(GetPresistenceData.getMyLogon(getApplicationContext()).equals("1"))
                {
                    new checKUserfillForm("2").execute("","","");
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this,RegisterMe.class);
                    intent.putExtra("doIt","2");
                    startActivity(intent);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                }
            }
            else
            {
                Intent intent=new Intent(MainActivity.this,RegisterMe.class);
                intent.putExtra("doIt","2");
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void parseData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
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

                    Domestic domm = new Domestic(sr, title, desc1, img, location, price, nop, cat, day, night);
                    domestic.add(domm);

                    RecyclerAdapterDom recyclerAdapterDom = new RecyclerAdapterDom(getApplicationContext(), domestic);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    domesticRec.setLayoutManager(layoutManager);
                    domesticRec.setHasFixedSize(true);
                    domesticRec.setAdapter(recyclerAdapterDom);
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }





    // create instance for close the app
    @SuppressLint("StaticFieldLeak")
    public static MainActivity fs;

    // on Application closer send to share
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        builder.setMessage("Do you want to close this app ?")
                .setCancelable(true)
                .setNeutralButton("Share", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PackageManager manager = getApplicationContext().getPackageManager();
                        Picasso.get().load(R.drawable.pack_back1).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                /* uri1=Uri.parse(Paths+File.separator+"10.jpg");*/
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("image/*");
                                //intent.putExtra(intent.EXTRA_SUBJECT,"Insert Something new");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra(Intent.EXTRA_TEXT, data + "market://details?id=" + getApplicationContext().getPackageName());
                                intent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, getApplicationContext()));

                                intent.setPackage("com.whatsapp");
                                // for particular choose we will set getPackage()
                                //startActivity(Intent.createChooser(intent,"Share Via"));// this code use for universal sharing
                                startActivity(intent);
                            }

                            @Override
                            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });

                        // end Share code

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // MainActivity.fs.finish();
                            dialog.cancel();
                        /*  System.exit(0);*/
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // MainActivity.fs.finish();
                        finishAffinity();
                        /*  System.exit(0);*/
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Exit");
        alert.show();

        return super.onKeyDown(keyCode, event);
    }

    private Uri getLocalBitmapUri(Bitmap bmp, Context context) {

        Uri uriimg = null;

        try {

            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Tnt" + System.currentTimeMillis() + ".png");

            FileOutputStream out = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.PNG, 50, out);

            out.close();

            /*uriimg = Uri.fromFile(file);*/
            uriimg = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                    BuildConfig.APPLICATION_ID + ".provider", file);
            refreshGallery(file);
        } catch (IOException e) {

            e.printStackTrace();

        }

        return uriimg;

    }

    private void refreshGallery(File file) {
        Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent1.setData(Uri.fromFile(file));
        Objects.requireNonNull(getApplicationContext()).sendBroadcast(intent1);
    }



    @Override
    protected void onStop() {
        super.onStop();

        if(queue !=null)
        {
            queue.cancelAll(REQUEST_TAG);
        }
        else if(queue1 !=null)
        {
            queue1.cancelAll(REQUEST_TAG);
        }

        // initilize Domestic Package data
        if(GetPresistenceData.getHotelLogin(MainActivity.this).equals("1"))
        {
            fetchDomesticPacks();
        }
        else
        {
            new domesticData().execute("","","");
        }
        // initilize International Package data

    }


    @SuppressLint("StaticFieldLeak")
    private class domesticData extends AsyncTask<String,String,String> {
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
                    parseData(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> mydata = new HashMap<>();
                    mydata.put("token", "9306488494");
                    mydata.put("cat", "Domestic");
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

    @SuppressLint("StaticFieldLeak")
    private class InternationalPack extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            // Instantiate the cache
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());

            queue1=new RequestQueue(cache,network);
            queue1.start();
            StringRequest request1 = new StringRequest(Request.Method.POST, Config.packs_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    parseData1(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request1.setRetryPolicy(policy);
            queue1.add(request1);
            return null;

        }
    }

    private void parseData1(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
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


                Inter inter = new Inter(sr, title, desc1, img, location, price, nop, cat, day, night);
                international.add(inter);

                final RecyclerAdapterInt recyclerAdapterInt = new RecyclerAdapterInt(getApplicationContext(), international);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                interRec.setLayoutManager(layoutManager);
                interRec.setHasFixedSize(true);
                interRec.setAdapter(recyclerAdapterInt);


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    private void parseData3(String response,String type) {

        try {
            JSONObject jsonObject=new JSONObject(response);
            String status=jsonObject.getString("status");
            if(status.equals("1"))
            {
                String email=jsonObject.getString("email");
                String pass=jsonObject.getString("pass");

                // check user id already registered or not
                // set presistence
                PresistenceData.checkRegistration(getApplicationContext(),"1");
                PresistenceData.profileData(getApplicationContext(),email,pass);
                // check logon now if registered
                if(type.equals("2")) {
                    // save offline data
                    new SaveFlights(getApplicationContext(),flightsDatabase).execute();
                    new SaveHotels(getApplicationContext(),hotelsDatabase).execute();
                    new SaveIti(getApplicationContext(),itiDatabase).execute();


                    Intent intent = new Intent(MainActivity.this, Trip.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                    finish();
                }
                else
                {
                    // save offline data
                    new SaveFlights(getApplicationContext(),flightsDatabase).execute();
                    new SaveHotels(getApplicationContext(),hotelsDatabase).execute();
                    new SaveIti(getApplicationContext(),itiDatabase).execute();
                    new SaveDomPack(getApplicationContext(),domPacks).execute();

                    Intent intent = new Intent(MainActivity.this, MyProfile.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                    finish();
                }
            }
            else
            {
                // user is not registered, send to registration page
                pd.dismiss();
                pd.setMessage("Require Registration");
                if(type.equals("2")) {
                    Intent intent = new Intent(MainActivity.this, RegisterMe.class);
                    intent.putExtra("doIt", "2");
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, RegisterMe.class);
                    intent.putExtra("doIt", "3");
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                    startActivity(intent);
                }
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @SuppressLint("StaticFieldLeak")
    private class checKUserfillForm extends AsyncTask<String,String,String> {
        String type;
        checKUserfillForm(String type) {
            this.type=type;
        }

        @Override
        protected String doInBackground(String... strings) {
            // Instantiate the cache
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue2=new RequestQueue(cache1,network1);
            queue2.start();
            StringRequest request = new StringRequest(Request.Method.POST, Config.DATA_URL2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    parseData3(response,type);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> mydata = new HashMap<>();
                    mydata.put("imei",GetPresistenceData.getMyIMEI(getApplicationContext()));
                    return mydata;
                }
            };
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request.setRetryPolicy(policy);
            queue2.add(request);
            return null;
        }
    }
}
