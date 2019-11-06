package com.travelandtime.Social;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.travelandtime.Bot.Chatbot;
import com.travelandtime.Configration.Config;
import com.travelandtime.Confirmations.Confirms;
import com.travelandtime.MainActivity;
import com.travelandtime.MyProfile;
import com.travelandtime.PackageClasses.Choice;
import com.travelandtime.R;
import com.travelandtime.Utils.AppStatus;
import com.travelandtime.Utils.GetPresistenceData;
import com.travelandtime.Utils.PresistenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Community extends AppCompatActivity {
    private CircleImageView imageChange;
    private ImageView profileImage;
    private ImageView userFrnd,userGroup,userChat,camera,postCam2,closeMe,imgView,backImg,searchFrnd1;
    private EditText enterMsg,StatusTxt;
    private static RecyclerView recPost;
    private TextView frndsTxt,chatTxt,groupTxt,miniGamesText,chatBot,userName,userMob,searchFrnd;
    private FrameLayout pushPost;
    private Button postBtn;
    ProgressDialog pd;
    AlertDialog.Builder builder;
    private int CAMERA = 1;
    String image="";
    private static final int STORAGE_PERMISSION_CODE=123;
    private Uri uri;
    int GALLERY = 2;
    RequestQueue queue;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    static ArrayList<Posts> posts;
    private LinearLayout friends,groupLay,searchFrndLay,chatLay,greenTagDocs,miniGames,helpAsistentLay;
    @SuppressLint("StaticFieldLeak")
    private static RecyclerView suggestFRec;
    static ArrayList<Suggested>suggested1;
    @SuppressLint("StaticFieldLeak")
    private static LinearLayout suggestedLay;
    private static final int REQUEST_STORAGE_PERMISSION = 100;
    private static final int REQUEST_PICK_PHOTO = 101;
    private static int overallXScroll = 0;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.social_me);
        imageChange = (CircleImageView) findViewById(R.id.imageChange);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        userName = (TextView) findViewById(R.id.user_name);
        userMob = (TextView) findViewById(R.id.user_mob);
        userFrnd = (ImageView) findViewById(R.id.user_frnd);
        userGroup = (ImageView) findViewById(R.id.user_group);
        userChat = (ImageView) findViewById(R.id.user_chat);
        enterMsg = (EditText) findViewById(R.id.enter_msg);
        camera = (ImageView) findViewById(R.id.camera);
        recPost = (RecyclerView) findViewById(R.id.rec_post);
        frndsTxt = (TextView) findViewById(R.id.frnds_txt);
        groupTxt = (TextView) findViewById(R.id.group_txt);
        chatTxt = (TextView) findViewById(R.id.chat_txt);
        pushPost = (FrameLayout) findViewById(R.id.push_post);
        StatusTxt = (EditText) findViewById(R.id.Status_Txt);
        postCam2 = (ImageView) findViewById(R.id.post_cam2);
        postBtn = (Button) findViewById(R.id.post_btn);
        closeMe = (ImageView) findViewById(R.id.close_me);
        imgView = (ImageView) findViewById(R.id.imgView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        friends = (LinearLayout) findViewById(R.id.friends);
        backImg = (ImageView) findViewById(R.id.backImg);
        groupLay = (LinearLayout) findViewById(R.id.group_lay);
        suggestFRec = (RecyclerView) findViewById(R.id.suggestFRec);
        suggestedLay = (LinearLayout) findViewById(R.id.suggested_lay);
        searchFrnd1 = (ImageView) findViewById(R.id.search_frnd1);
        searchFrnd = (TextView) findViewById(R.id.search_frnd);
        searchFrndLay = (LinearLayout) findViewById(R.id.search_frnd_lay);
        chatLay = (LinearLayout) findViewById(R.id.chat_lay);
        greenTagDocs = (LinearLayout) findViewById(R.id.green_tag_docs);
        miniGames = (LinearLayout) findViewById(R.id.mini_games);
        miniGamesText = (TextView) findViewById(R.id.mini_games_text);
        helpAsistentLay = (LinearLayout) findViewById(R.id.help_asistent_lay);
        chatBot = (TextView) findViewById(R.id.chat_bot);

        posts=new ArrayList<>();
        suggested1=new ArrayList<>();
        image="";

        // animation image
        Glide.with(getApplicationContext()).asGif().load(R.drawable.circles).into(backImg);

        pd=new ProgressDialog(this);
        pd.setTitle("Asset Processing...");
        pd.setMessage("Processing...");

        // load posts
        loadPost(getApplicationContext(),"T","T");
        // show suggested friends
        if (AppStatus.getInstance(this).isOnline()) {
            loadSuggest(getApplicationContext(),"T","T");
        }

        // get meta data and set to profile
        String img_url= GetPresistenceData.getMetaPic(getApplicationContext());
        Picasso.get().load(img_url).into(imageChange);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                backImg.setVisibility(View.VISIBLE);
            }
        },2200);


        userName.setText(GetPresistenceData.getMetaName(getApplicationContext()));
        userMob.setText(GetPresistenceData.getMetaMobb(getApplicationContext()));

        // click on friends and show the friend list
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Community.this,BigPic.class);
                // change background property of buttons
                friends.setBackground(getDrawable(R.drawable.red_circle));
                groupLay.setBackground(getDrawable(R.drawable.circle_button));
                searchFrndLay.setBackground(getDrawable(R.drawable.circle_button));
                chatLay.setBackground(getDrawable(R.drawable.circle_button));

                intent.putExtra("image","frnd");
                intent.putExtra("text",GetPresistenceData.getMyIMEI(getApplicationContext()));
                intent.putExtra("type","4");
                startActivity(intent);
            }
        });
        // click on chatbot layout
        helpAsistentLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Community.this, Chatbot.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        // click on green tag to download kyc docs
        greenTagDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.setTitle("Searching Documents...");
                pd.setMessage("Please wait....");
                pd.show();
            checkKycdoc(GetPresistenceData.getMyIMEI(getApplicationContext()));
            }
        });
        // click on enter msg and open popup layout
        enterMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushPost.setVisibility(View.VISIBLE);
                StatusTxt.requestFocus();
                StatusTxt.setText("");

            }
        });
        // click on minigames
        miniGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Community.this, Confirms.class);
                intent.putExtra("page","Community");
                intent.putExtra("lay","Minigames");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
        // close btn close the popup for enter status
        closeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushPost.setVisibility(View.GONE);
            }
        });
        // click on cam
        postCam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImageOptions();
            }
        });

        // click on search friends
        searchFrndLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Community.this,BigPic.class);
                // change background property of buttons
                searchFrndLay.setBackground(getDrawable(R.drawable.red_circle));
                friends.setBackground(getDrawable(R.drawable.circle_button));
                groupLay.setBackground(getDrawable(R.drawable.circle_button));
                chatLay.setBackground(getDrawable(R.drawable.circle_button));

                intent.putExtra("image","");
                intent.putExtra("type","5");
                intent.putExtra("text",GetPresistenceData.getMyIMEI(getApplicationContext()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        // click on post button to save the post

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate status data
                String ideas_text=StatusTxt.getText().toString();
                if(ideas_text.length()<3)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Your ideas are too small worded",Snackbar.LENGTH_SHORT).show();
                    StatusTxt.requestFocus();
                }
                else if(ideas_text.length()>250)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Text limit maximum exceed than 250 words",Snackbar.LENGTH_SHORT).show();
                    StatusTxt.requestFocus();
                }
                else
                {
                    if(image.equals(""))
                    {
                        image="NA";
                        String imei=GetPresistenceData.getMyIMEI(getApplicationContext());
                        String email=GetPresistenceData.getEmail(getApplicationContext());
                        String name=GetPresistenceData.getMetaName(getApplicationContext());
                        uploadtoserverr(image,ideas_text,imei,email,name);
                    }
                    else
                    {
                        pd.setTitle("Saving...");
                        pd.setMessage("Saving post...");
                        pd.setCancelable(true);
                        pd.show();
                        String imei=GetPresistenceData.getMyIMEI(getApplicationContext());
                        String email=GetPresistenceData.getEmail(getApplicationContext());
                        String name=GetPresistenceData.getMetaName(getApplicationContext());
                        Log.e("Image_click_value",image);
                        uploadtoserverr(image,ideas_text,imei,email,name);
                    }

                }
            }
        });
// click on camera on main page and visible pushpost layout
camera.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ChooseImageOptions();
        pushPost.setVisibility(View.VISIBLE);
    }
});

// click on user group and user chat
        groupLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Community.this, Choice.class);
                // change background property of buttons
                groupLay.setBackground(getDrawable(R.drawable.red_circle));
                friends.setBackground(getDrawable(R.drawable.circle_button));
                searchFrndLay.setBackground(getDrawable(R.drawable.circle_button));
                chatLay.setBackground(getDrawable(R.drawable.circle_button));

                intent.putExtra("Option","2");
                startActivity(intent);
            }
        });

        // change profile Image
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Community.this, MyProfile.class);
                startActivity(intent);
                finish();
            }
        });

        // click on user chat
        userChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Community.this,BigPic.class);
                intent.putExtra("image","chat");
                intent.putExtra("text",GetPresistenceData.getMyIMEI(getApplicationContext()));
                intent.putExtra("type","9");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        // click on chat layout
        chatLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Community.this,BigPic.class);
                // change background property of buttons
                chatLay.setBackground(getDrawable(R.drawable.red_circle));
                friends.setBackground(getDrawable(R.drawable.circle_button));
                groupLay.setBackground(getDrawable(R.drawable.circle_button));
                searchFrndLay.setBackground(getDrawable(R.drawable.circle_button));


                intent.putExtra("image","chat");
                intent.putExtra("text",GetPresistenceData.getMyIMEI(getApplicationContext()));
                intent.putExtra("type","9");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    } //oncreate closer

    static void loadSuggest(final Context mcontext, final String page,final String lastid) {

        RequestQueue queue;
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.suggested_friends, new Response.Listener<String>() {
            @Override
            public void onResponse(String response3) {
                parseData3(response3,mcontext,page);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mcontext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                // no need to pass imei but as security i passed it
                mydata.put("imei",GetPresistenceData.getMyIMEI(mcontext));
                mydata.put("pageFrom",page);
                mydata.put("lastid",lastid);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);

    }

    private void checkKycdoc(final String myIMEI) {
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
                Toast.makeText(Community.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

                    Intent intent=new Intent(Community.this,BigPic.class);
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


    private void captureimagefromcamera2() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void uploadtoserverr(final String image, final String ideas_text, final String imei, final String email, final String name) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.posts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                pd.dismiss();
                parseData1(response1);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Community.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                                pd.show();
                                pd.setMessage("Uploading...");
                                break;
                            case 1:
                                gallery();
                                pd.show();
                                pd.setMessage("Uploading...");
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void gallery() {

            if(PackageManager.PERMISSION_GRANTED !=
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                }else {
//Yeah! I want both block to do the same thing, you can write your own logic, but this works for me.
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_STORAGE_PERMISSION);
                }
            }else {
//Permission Granted, lets go pick photo
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                // this flag is important in Mi mobiles
               // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
startActivityForResult(intent, GALLERY);

}
}



    private void captureimagefromcamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA && data != null) {
//imgage captured
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            assert bitmap != null;
            imageToString(bitmap);
            imgView.setImageBitmap(bitmap);
            imgView.setVisibility(View.VISIBLE);
            //capture_image.setImageBitmap(bitmap);
        }
        else if (requestCode == GALLERY && data != null && data.getData() != null ) {
            if (resultCode == RESULT_OK) {

                Uri contentURI = data.getData();
                Log.e("Internal Uri", String.valueOf(contentURI));
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    Log.e("bitmap....", String.valueOf(bitmap));
                    imageToString2(bitmap);
                    imgView.setImageBitmap(bitmap);
                    imgView.setVisibility(View.VISIBLE);
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



    private void imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        image = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        pd.dismiss();
    }

    private void imageToString2(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        image = Base64.encodeToString(imgbytes, Base64.DEFAULT);
        Log.e("base64 Conversion",image);
        pd.dismiss();
    }


    private void parseData1(String response1) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response1);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {
                pd.dismiss();
                pushPost.setVisibility(View.GONE);
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                imgView.setImageDrawable(null);
                // load posts
                posts.clear();
                //posts.remove(posts.size()-1);
                loadPost(getApplicationContext(),"T","T");
            }
            else{
                pd.dismiss();
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    static void loadPost(final Context mcontext, final String page, final String lastid) {
        RequestQueue queue;
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.show_posts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                parseData2(response1,mcontext,page);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mcontext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                // no need to pass imei but as security i passed it
                mydata.put("imei",GetPresistenceData.getMyIMEI(mcontext));
                mydata.put("pageFrom",page);
                mydata.put("lastid",lastid);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }


    private static void parseData2(String response1, final Context mcontext, String page) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response1);

            String Status=jsonObject.getString("status");
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String total = jsonObject1.getString("total");
                String sr = jsonObject1.getString("sr");
                String imei = jsonObject1.getString("imei");
                String post_like = jsonObject1.getString("post_like");
                String post_comments = jsonObject1.getString("post_comments");
                String email = jsonObject1.getString("email");
                String date1 = jsonObject1.getString("date1");
                String name = jsonObject1.getString("name");
                String post_txt = jsonObject1.getString("post_txt");
                String post_img = jsonObject1.getString("post_img");
                String shared = jsonObject1.getString("shared");
                String shared_name = jsonObject1.getString("shared_name");
                String like = jsonObject1.getString("like");
                String dp = jsonObject1.getString("dp");

                Posts posts2 = new Posts(sr,post_like,post_comments,imei,email,date1,name,post_txt,post_img,shared,shared_name,like,dp,total);
                posts.add(posts2);

                RPOST rpost = new RPOST(mcontext, posts);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recPost.setLayoutManager(layoutManager);
                recPost.setHasFixedSize(true);
                recPost.setAdapter(rpost);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        };
    }


    private static void parseData3(String response3,Context mcontext,String page) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response3);
            String Status=jsonObject.getString("status");
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String imei = jsonObject1.getString("imei");
                String sr = jsonObject1.getString("sr");
                String name = jsonObject1.getString("name");
                String my_pic = jsonObject1.getString("my_pic");
                String email = jsonObject1.getString("email");

                suggestedLay.setVisibility(View.VISIBLE);
                Suggested suggested2 = new Suggested(imei,name,my_pic,email,GetPresistenceData.getMyIMEI(mcontext),sr);
                suggested1.add(suggested2);

                RSuugested rSuugested = new RSuugested(mcontext, suggested1);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                suggestFRec.setLayoutManager(layoutManager);
                suggestFRec.setHasFixedSize(true);
                suggestFRec.setAdapter(rSuugested);
                //suggestFRec.scrollToPosition(5); //use to focus the item with index
                //rSuugested.notifyDataSetChanged();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        };
    }


    // back key pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {
            Intent back1Intent=new Intent(Community.this, MainActivity.class);
            startActivity(back1Intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
