package com.travelandtime;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
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
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.travelandtime.Configration.Config;
import com.travelandtime.Confirmations.Confirms;
import com.travelandtime.Databases.FlightsDatabase;
import com.travelandtime.Databases.HotelsDatabase;
import com.travelandtime.Databases.ItiDatabase;
import com.travelandtime.Databases.TestDatabase;
import com.travelandtime.Databases.TruncateFlights;
import com.travelandtime.Social.BigPic;
import com.travelandtime.Social.Community;
import com.travelandtime.Utils.GetPresistenceData;
import com.travelandtime.Utils.PresistenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfile extends AppCompatActivity {
    public static final String REQUEST_TAG = "VolleyBlockingRequestActivity";
    private ImageView GoBack,editPic;
    private CircleImageView imageChange;
    private TextView editProfile,changePass,wallet,UpcomingTrip,PastTrip,RequestNewTrip,AboutUs,contactUs,info,userEmail,userMob,userName,privacy,kycupload,logout;

    RequestQueue queue;
    ProgressDialog pd;
    AlertDialog.Builder builder;
    private int CAMERA = 1;
    String image="";
    private static final int STORAGE_PERMISSION_CODE=123;
    private Uri uri;
    int GALLERY = 2;
    private LinearLayout tripLay,tripLay1,supportLay,supportLay1,updateLay,updateLay1;
    private EditText updateMobTxt,newPassTxt,newEmailTxt;
    private Button mobUpdateBtn,newEmailBtn,newMobBtn;
    private ImageView closeMe;
    FlightsDatabase flightsDatabase;
    HotelsDatabase hotelsDatabase;
    ItiDatabase itiDatabase;
    private ImageView backImg;
    private TextView downloadKYC;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
        GoBack = (ImageView) findViewById(R.id.GoBack);
        imageChange = (CircleImageView) findViewById(R.id.imageChange);
        editPic = (ImageView) findViewById(R.id.edit_pic);
        userName = (TextView) findViewById(R.id.user_name);
        userMob = (TextView) findViewById(R.id.user_mob);
        userEmail = (TextView) findViewById(R.id.user_email);
        editProfile = (TextView) findViewById(R.id.edit_profile);
        changePass = (TextView) findViewById(R.id.change_pass);
        wallet = (TextView) findViewById(R.id.wallet);
        UpcomingTrip = (TextView) findViewById(R.id.Upcoming_trip);
        PastTrip = (TextView) findViewById(R.id.Past_trip);
        RequestNewTrip = (TextView) findViewById(R.id.Request_new_trip);
        AboutUs = (TextView) findViewById(R.id.About_us);
        contactUs = (TextView) findViewById(R.id.contact_us);
        info = (TextView) findViewById(R.id.info);
        backImg = (ImageView) findViewById(R.id.backImg);

        tripLay = (LinearLayout) findViewById(R.id.tripLay);
        tripLay1 = (LinearLayout) findViewById(R.id.tripLay1);
        supportLay = (LinearLayout) findViewById(R.id.supportLay);
        supportLay1 = (LinearLayout) findViewById(R.id.supportLay1);
        updateLay = (LinearLayout) findViewById(R.id.updateLay);
        updateLay1 = (LinearLayout) findViewById(R.id.updateLay1);
        updateMobTxt = (EditText) findViewById(R.id.updateMob_txt);
        mobUpdateBtn = (Button) findViewById(R.id.mob_update_btn);
        newEmailTxt = (EditText) findViewById(R.id.newEmail_txt);
        newEmailBtn = (Button) findViewById(R.id.newEmail_btn);
        newPassTxt = (EditText) findViewById(R.id.newPass_txt);
        newMobBtn = (Button) findViewById(R.id.newMob_btn);
        closeMe = (ImageView) findViewById(R.id.close_me);
        privacy = (TextView) findViewById(R.id.privacy);
        logout = (TextView) findViewById(R.id.logout);
        flightsDatabase =new FlightsDatabase(MyProfile.this);
        hotelsDatabase =new HotelsDatabase(MyProfile.this);
        itiDatabase =new ItiDatabase(MyProfile.this);
        kycupload = (TextView) findViewById(R.id.kycupload);

        downloadKYC = (TextView) findViewById(R.id.downloadKYC);

        // setup Alert Box
        // setup builder
        builder=new AlertDialog.Builder(getApplicationContext());
        
        // set up progress dialogue box
        pd=new ProgressDialog(this);
        pd.setMessage("Processing...");
        pd.setTitle("wait please...");
        pd.setCancelable(false);

        // animation image
        Glide.with(getApplicationContext()).asGif().load(R.drawable.circles).into(backImg);


        // ftech complete profile data
        new fetchProfileDate().execute("","","");
        
        // edit profile image
        editPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImageOptions();
            }
        });

        // click on download KYC button
        downloadKYC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setTitle("Searching Documents...");
                pd.setMessage("Please wait....");
                pd.show();
                downCheckKyc(GetPresistenceData.getMyIMEI(getApplicationContext()));
            }
        });
        // update profile
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripLay.setVisibility(View.GONE);
                tripLay1.setVisibility(View.GONE);
                supportLay.setVisibility(View.GONE);
                supportLay1.setVisibility(View.GONE);
                updateLay.setVisibility(View.VISIBLE);
                updateLay1.setVisibility(View.VISIBLE);
            }
        });
        // update password
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripLay.setVisibility(View.GONE);
                tripLay1.setVisibility(View.GONE);
                supportLay.setVisibility(View.GONE);
                supportLay1.setVisibility(View.GONE);
                updateLay.setVisibility(View.VISIBLE);
                updateLay1.setVisibility(View.VISIBLE);
            }
        });
        // upload kyc docs
    kycupload.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(MyProfile.this,RegisterMe.class);
            intent.putExtra("doIt","5");
            startActivity(intent);
            finish();
        }
    });
        // close update lay
        closeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLay.setVisibility(View.GONE);
                updateLay1.setVisibility(View.GONE);
                tripLay.setVisibility(View.VISIBLE);
                tripLay1.setVisibility(View.VISIBLE);
                supportLay.setVisibility(View.VISIBLE);
                supportLay1.setVisibility(View.VISIBLE);
            }
        });
        // click on wallet
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(MyProfile.this, BigPic.class);
               intent.putExtra("image","");
               intent.putExtra("type","11");
               intent.putExtra("text","");
               startActivity(intent);
            }
        });
        // click on UpcomingTrip
        UpcomingTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyProfile.this,RegisterMe.class);
                intent.putExtra("doIt","2");
                startActivity(intent);
                finish();
            }
        });
        // click on Past trip
        PastTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyProfile.this,RegisterMe.class);
                intent.putExtra("doIt","2");
                startActivity(intent);
                finish();
            }
        });
        // click on Past trip
        RequestNewTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyProfile.this, Confirms.class);
                intent.putExtra("page","MyProfile");
                intent.putExtra("lay","Call");
                startActivity(intent);

            }
        });
        // About us
        AboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyProfile.this, Confirms.class);
                intent.putExtra("page","MyProfile");
                intent.putExtra("lay","Aboutus");
                startActivity(intent);
            }
        });
        // contact us
        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyProfile.this, Confirms.class);
                intent.putExtra("page","MyProfile");
                intent.putExtra("lay","Contactus");
                startActivity(intent);
            }
        });
        // privacy policy
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyProfile.this, Confirms.class);
                intent.putExtra("page","MyProfile");
                intent.putExtra("lay","Privacy");
                startActivity(intent);
            }
        });
        // click on logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PresistenceData.checkLogon(getApplicationContext(),"0");
                PresistenceData.checkRegistration(getApplicationContext(),"0");
                PresistenceData.saveIMEI(getApplicationContext(),"0");
                PresistenceData.MyName(getApplicationContext(),"0");
                // update meta data
                PresistenceData.metaData(getApplicationContext(),"0","0","0","0","0");

                // Truncate flight and hotel Tables
                new TruncateFlights(getApplicationContext(),flightsDatabase, hotelsDatabase,itiDatabase).execute();

                Snackbar.make(getWindow().getDecorView().getRootView(),"Successfully logout",Snackbar.LENGTH_SHORT).show();
                Intent intent=new Intent(MyProfile.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // click on GoBack
        GoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyProfile.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // update password
        newMobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_password=newPassTxt.getText().toString();
                if(new_password.length()<=6)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Minimum password limit 6",Snackbar.LENGTH_SHORT).show();
                    newPassTxt.requestFocus();
                }
                else if(new_password.length()>=25)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Maximum password limit 25",Snackbar.LENGTH_SHORT).show();
                    newPassTxt.requestFocus();
                }
                else
                {
                    uploadtoserver(new_password,"2");
                }
            }
        });
        // update mobile no
        mobUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String update_mobileno=updateMobTxt.getText().toString();
                if(update_mobileno.length()<10)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid mobile no",Snackbar.LENGTH_SHORT).show();
                    updateMobTxt.requestFocus();
                }
                else if(update_mobileno.length()>10)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid mobile no",Snackbar.LENGTH_SHORT).show();
                    updateMobTxt.requestFocus();
                }
                else
                {
                    uploadtoserver(update_mobileno,"3");
                }
            }
        });

        // update email id
        newEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String update_email=newEmailTxt.getText().toString();
                if(update_email.length()<4)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Minimum text 5",Snackbar.LENGTH_SHORT).show();
                    newEmailTxt.requestFocus();
                }
                else if(update_email.length()>20)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Maximum text 20",Snackbar.LENGTH_SHORT).show();
                    newEmailTxt.requestFocus();
                }
                else
                {
                    uploadtoserver(update_email,"4");
                }
            }
        });


    } // onCreate closer

    private void downCheckKyc(final String myIMEI) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.download_kyc, new Response.Listener<String>() {
            @Override
            public void onResponse(String response14) {
                pd.dismiss();
                parseData14(response14);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei",myIMEI);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData14(String response14) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response14);
            String Status2=jsonObject.getString("status");

            if(Status2.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String adhaar = jsonObject1.getString("adhaar");
                    String lic = jsonObject1.getString("lic");
                    String passport = jsonObject1.getString("passport");

                    PresistenceData.MyDocs(getApplicationContext(),adhaar,lic,passport);

                    Intent intent=new Intent(MyProfile.this,BigPic.class);
                    intent.putExtra("image","");
                    intent.putExtra("type","10");
                    intent.putExtra("text","");
                    startActivity(intent);
                }
            }
            else
            {
                Toast.makeText(this, "You have not uploaded documents", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    private void ChooseImageOptions() {
            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
            pictureDialog.setTitle("Select Action");
            String[] pictureDialogItems = {
                    "Capture to Camera",
                    "From Gallery"};
            pictureDialog.setItems(pictureDialogItems,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            switch (i) {
                                case 0:
                                    captureimagefromcamera();
                                    pd.setMessage("Uploading...");
                                    break;
                                case 1:
                                    dataFromLib();

                                    pd.setMessage("Uploading...");
                                    break;
                            }
                        }
                    });
            pictureDialog.show();
        
    }

    private void dataFromLib() {
        //method to show file chooser
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);

    }

    private void captureimagefromcamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }


    private void imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        image = Base64.encodeToString(imgbytes, Base64.DEFAULT);

        uploadtoserver(image,"1");
    }

    private void uploadtoserver(final String data, final String type) {

        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.update_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                parseData1(response1, data);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei", GetPresistenceData.getMyIMEI(getApplicationContext()));
                mydata.put("email",GetPresistenceData.getEmail(getApplicationContext()));
                mydata.put("type",type);
                mydata.put("data",data);

                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData1(String response1,String Data) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response1);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {
                pd.dismiss();
                new fetchProfileDate().execute("","","");
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                uploadtoserverr(Data,GetPresistenceData.getMyName(getApplicationContext())+" has update profile image",GetPresistenceData.getMyIMEI(getApplicationContext()),GetPresistenceData.getEmail(getApplicationContext()),GetPresistenceData.getMyName(getApplicationContext()));
                PresistenceData.metaStatus(getApplicationContext(),"0");
                updateLay1.setVisibility(View.GONE);
            }
            else{
                pd.dismiss();
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void uploadtoserverr(final String image, final String ideas_text, final String imei, final String email, final String name) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.posts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response7) {
                parseData7(response7);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MyProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei",imei);
                mydata.put("email",email);
                mydata.put("name",name);
                mydata.put("post_txt",ideas_text);
                mydata.put("post_img",image);
                mydata.put("shared","0");
                mydata.put("shared_name","0");
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData7(String response7) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response7);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {

            }
            else{
                pd.dismiss();
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA && data != null) {
//imgage captured
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageToString(bitmap);
            imageChange.setImageBitmap(bitmap);
            //capture_image.setImageBitmap(bitmap);
        }
        else if (requestCode == GALLERY && data != null && data.getData() != null ) {
            if (resultCode == RESULT_OK) {

                Uri contentURI = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageToString(bitmap);
                    imageChange.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                assert bitmap != null;
                //sendData();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Failed to select the image.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to select image",
                        Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @SuppressLint("StaticFieldLeak")
    private class fetchProfileDate extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue=new RequestQueue(cache1,network1);
            queue.start();
            StringRequest request = new StringRequest(Request.Method.POST, Config.my_profile, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    parseData(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MyProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
            queue.add(request);
            return null;
        }
    }

    private void parseData(String response) {
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
                    Picasso.get().load(my_pic).into(imageChange);
                    userName.setText(name);
                    userMob.setText(mobb);
                    userEmail.setText(email);
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            backImg.setVisibility(View.VISIBLE);
                        }
                    },2000);


                    PresistenceData.MyName(getApplicationContext(),name);
                }
            }
            else
            {
                Toast.makeText(this, "Unable to load data", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




    @Override
    protected void onStop() {
        super.onStop();
        if(queue !=null)
        {
            queue.cancelAll(REQUEST_TAG);
        }

    }

    // back key pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {
            Intent back1Intent=new Intent(MyProfile.this,MainActivity.class);
            startActivity(back1Intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

}
