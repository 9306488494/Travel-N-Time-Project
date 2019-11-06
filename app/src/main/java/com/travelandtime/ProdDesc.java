package com.travelandtime;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.travelandtime.Adapters.RAR;
import com.travelandtime.Configration.Config;
import com.travelandtime.GetterSetter.Reviews;
import com.travelandtime.Social.BigPic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProdDesc extends AppCompatActivity {
    CardView facilityLay;
    public static final String REQUEST_TAG = "VolleyBlockingRequestActivity";
    LinearLayout fLay1, fLay2, fLay3, fLay4, fLay5;
    private LinearLayout circle2Lay;
    TextView facilityTxtTitle, facilityCircle1, facilityCircle2, facilityCircle3, facilityCircle4, facilityCircle5, faciltySubTxt;
    ProgressDialog pd;
    private TextView txtDesc;

    String Image, Title, Price, Desc, Location, ID, Night, Day, Nop;
    RequestQueue queue, queue1;
    private CardView reviewLay1;
    private RecyclerView reviewRec;
    private ArrayList<Reviews> reviews;
    private ScrollView scrollView1;
    private LinearLayout c2, c1, c3;
    private ImageView shareImg;
    private Button callUs;
    private TextView overview, startingBudget, readMore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prod_desc);

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        ImageView prodImg = (ImageView) findViewById(R.id.prodImg);
        TextView titletxt = (TextView) findViewById(R.id.titletxt);
        TextView txtLocation = (TextView) findViewById(R.id.txtLocation);
        TextView circleTxt1 = (TextView) findViewById(R.id.circleTxt1);
        TextView circleTxt2 = (TextView) findViewById(R.id.circleTxt2);
        TextView circleTxt3 = (TextView) findViewById(R.id.circleTxt3);
        facilityLay = (CardView) findViewById(R.id.facility_lay);
        facilityTxtTitle = (TextView) findViewById(R.id.facilityTxtTitle);
        facilityCircle1 = (TextView) findViewById(R.id.facility_circle_1);
        facilityCircle2 = (TextView) findViewById(R.id.facility_circle_2);
        facilityCircle3 = (TextView) findViewById(R.id.facility_circle_3);
        facilityCircle4 = (TextView) findViewById(R.id.facility_circle_4);
        facilityCircle5 = (TextView) findViewById(R.id.facility_circle_5);
        faciltySubTxt = (TextView) findViewById(R.id.faciltySubTxt);
        CardView reviewLay = (CardView) findViewById(R.id.review_lay);
        ImageView userPic = (ImageView) findViewById(R.id.user_pic);
        TextView reviewUser = (TextView) findViewById(R.id.review_user);
        TextView reviewTime = (TextView) findViewById(R.id.review_time);
        TextView userComment = (TextView) findViewById(R.id.user_comment);
        fLay1 = (LinearLayout) findViewById(R.id.f_lay1);
        fLay2 = (LinearLayout) findViewById(R.id.f_lay2);
        fLay3 = (LinearLayout) findViewById(R.id.f_lay3);
        fLay4 = (LinearLayout) findViewById(R.id.f_lay4);
        fLay5 = (LinearLayout) findViewById(R.id.f_lay5);
        circle2Lay = (LinearLayout) findViewById(R.id.circle2_lay);
        CardView cardOverview = (CardView) findViewById(R.id.card_overview);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        LinearLayout circle1Lay = (LinearLayout) findViewById(R.id.circle1_lay);
        LinearLayout circle3Lay = (LinearLayout) findViewById(R.id.circle3_lay);
        reviewLay1 = (CardView) findViewById(R.id.review_lay1);
        reviewRec = (RecyclerView) findViewById(R.id.review_rec);
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        c2 = (LinearLayout) findViewById(R.id.c2);
        c1 = (LinearLayout) findViewById(R.id.c1);
        c3 = (LinearLayout) findViewById(R.id.c3);
        shareImg = (ImageView) findViewById(R.id.shareImg);
        callUs = (Button) findViewById(R.id.callUs);
        overview = (TextView) findViewById(R.id.overview);
        startingBudget = (TextView) findViewById(R.id.startingBudget);
        readMore = (TextView) findViewById(R.id.readMore);


        // Progress dialogue box
        pd = new ProgressDialog(this);
        pd.setTitle("Checking..");
        pd.setMessage("Fetching data...");


        // Initilize review arrays
        reviews = new ArrayList<>();


        // Recieve intent
        Intent intent1 = getIntent();
        Image = intent1.getStringExtra("Image");
        Title = intent1.getStringExtra("Title");
        Price = intent1.getStringExtra("Price");
        Desc = intent1.getStringExtra("Desc");
        Location = intent1.getStringExtra("Location");
        ID = intent1.getStringExtra("ID");
        Night = intent1.getStringExtra("Night");
        Day = intent1.getStringExtra("Day");
        Nop = intent1.getStringExtra("Nop");

        // call api
        new parseReviews().execute("", "", "");


        // set Parameters
        Picasso.get().load(Image).into(prodImg);
        titletxt.setText(Title);
        txtLocation.setText(Location);
        txtDesc.setText(Desc);
        startingBudget.setText("Rs." + Price + "/person");

        // set Circle text
        circleTxt1.setText("Photos");
        circleTxt2.setText("Inclusion");
        circleTxt3.setText("Reviews");
        // Call facility
        circle2Lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //c2.setBackgroundColor(getResources().getColor(R.color.drawable_red));
                c2.setBackground(getDrawable(R.drawable.red_circle));
                c1.setBackground(getDrawable(R.drawable.circle_button));
                c3.setBackground(getDrawable(R.drawable.circle_button));
                pd.show();
                facilityTxtTitle.setText("Inclusions");
                faciltySubTxt.setText("All inclusions are depends on your package");
                new callFacility(ID).execute("", "", "");
            }
        });
        // click on Photos
        circle1Lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c1.setBackground(getDrawable(R.drawable.red_circle));
                c2.setBackground(getDrawable(R.drawable.circle_button));
                c3.setBackground(getDrawable(R.drawable.circle_button));
                Intent intent = new Intent(ProdDesc.this, Photos.class);
                intent.putExtra("id", ID);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        // click on review
        circle3Lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c3.setBackground(getDrawable(R.drawable.red_circle));
                c1.setBackground(getDrawable(R.drawable.circle_button));
                c2.setBackground(getDrawable(R.drawable.circle_button));
                pd.show();
                if (reviewLay1.getVisibility() == View.VISIBLE) {
                    pd.dismiss();
                    reviewLay1.setVisibility(View.GONE);
                } else {
                    pd.dismiss();

                    reviewLay1.setVisibility(View.VISIBLE);
                }
            }
        });

        // click on image and show it on a bigPic
        prodImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), BigPic.class);
                intent.putExtra("image",Image);
                intent.putExtra("type","12");
                intent.putExtra("text","NA");
                startActivity(intent);
            }
        });
        // share the package on whatsapp
        shareImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.get().load(Image).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        /* uri1=Uri.parse(Paths+File.separator+"10.jpg");*/


                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        //intent.putExtra(intent.EXTRA_SUBJECT,"Insert Something new");
                        String data = "\n " + "Attractive Packages on" + "\n" + Title + " \n \n " + "@ Rs." + Price + " /- \n" + Night + "Night/" + Day + "\n \n " + "Click here to install app: \n" + Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()) + "\n\n visi us on : http://travelntime.com" + "\n\n Call us on : + 91 124 4222401";
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_TEXT, data);
                        intent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(bitmap, ProdDesc.this));
                        intent.setPackage(Config.whatsapp_package);
                        // for particular choose we will set getPackage()
                        /*startActivity(intent.createChooser(intent,"Share Via"));*/// this code use for universal sharing
                        startActivity(intent);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(ProdDesc.this, "Load Image Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

                // end Share code
            }
        });
// onClick call to Customer care services
        callUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String mobData = Config.mobileNo;
                callIntent.setData(Uri.parse("tel:" + mobData));//change the number
                if (ActivityCompat.checkSelfPermission(ProdDesc.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });
        // click on readmore button
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtDesc.setText(Desc.substring(0,500));
                txtDesc.setText(Desc);
                readMore.setVisibility(View.GONE);
            }
        });



    }// onCreate closer



    public Uri getLocalBitmapUri(Bitmap bmp, Context context) {
        Uri uriimg = null;
        try {
            File file =  new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "TnT"+ System.currentTimeMillis() +".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 50, out);
            out.close();
            /*uriimg = Uri.fromFile(file);*/
            uriimg = FileProvider.getUriForFile(ProdDesc.this,
                    BuildConfig.APPLICATION_ID + ".provider",file);
            refreshGallery(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return uriimg;
    }

    private void refreshGallery(File new_file) {
        Intent intent1=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent1.setData(Uri.fromFile(new_file));
        sendBroadcast(intent1);
    }


    @SuppressLint("StaticFieldLeak")
    private class callFacility extends AsyncTask<String,String,String> {
        callFacility(String id) {
        }

        @Override
        protected String doInBackground(String... strings) {
            // Instantiate the cache
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue=new RequestQueue(cache1,network1);
            queue.start();
            StringRequest request = new StringRequest(Request.Method.POST, Config.pack_facility_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    parseData(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ProdDesc.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        pd.dismiss();
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String flight = jsonObject1.getString("flight");
                String bed = jsonObject1.getString("bed");
                String breakfast = jsonObject1.getString("breakfast");
                String sightseen = jsonObject1.getString("sightseen");
                String cab = jsonObject1.getString("cab");

                // make visible facility lay
                if(facilityLay.getVisibility() == View.VISIBLE)
                {

                    facilityLay.setVisibility(View.GONE);
                }
                else {

                    facilityLay.setVisibility(View.VISIBLE);
                    if (flight.equals("1")) {
                        fLay1.setVisibility(View.VISIBLE);
                        facilityCircle1.setText("Flight");
                    }
                    if (cab.equals("1")) {
                        fLay2.setVisibility(View.VISIBLE);
                        facilityCircle2.setText("Cab");
                    }
                    if (bed.equals("1")) {
                        fLay3.setVisibility(View.VISIBLE);
                        facilityCircle3.setText("Stay");
                    }

                    if (breakfast.equals("1")) {
                        fLay4.setVisibility(View.VISIBLE);
                        facilityCircle4.setText("Meal");
                    }
                    if (sightseen.equals("1")) {
                        fLay5.setVisibility(View.VISIBLE);
                        facilityCircle5.setText("Sightseen");
                    }
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @SuppressLint("StaticFieldLeak")
    private class parseReviews extends AsyncTask<String,String,String>  {
        @Override
        protected String doInBackground(String... strings) {
            // Instantiate the cache
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue1=new RequestQueue(cache1,network1);
            queue1.start();
            StringRequest request = new StringRequest(Request.Method.POST, Config.pack_review_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    parseData1(response);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ProdDesc.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
            queue1.add(request);
            return null;
        }
    }

    private void parseData1(String response) {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response);
            String status=jsonObject.getString("status");
            if(status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String sr = jsonObject1.getString("sr");
                    String pack_id = jsonObject1.getString("pack_id");
                    String user_name = jsonObject1.getString("user_name");
                    String msgg = jsonObject1.getString("msgg");
                    String time = jsonObject1.getString("time");
                    String user_pic = jsonObject1.getString("user_pic");
                    String user_rating = jsonObject1.getString("user_rating");


                    Reviews reviews1 = new Reviews(sr, pack_id, user_name, msgg, time, user_pic, user_rating);
                    reviews.add(reviews1);

                    RAR rar = new RAR(getApplicationContext(), reviews);

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    reviewRec.setLayoutManager(layoutManager);
                    reviewRec.setHasFixedSize(true);
                    reviewRec.setAdapter(rar);
                }
            }
            else
            {
                overview.setText("No Comments Found (New Products)");
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
    else if(queue1 !=null)
    {
        queue1.cancelAll(REQUEST_TAG);
    }
}

    // back key pressed
/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {
            Intent back1Intent=new Intent(ProdDesc.this,MainActivity.class);
            startActivity(back1Intent);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }*/

}
