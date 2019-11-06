package com.travelandtime.Social;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

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
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.travelandtime.Configration.Config;
import com.travelandtime.ProdDesc;
import com.travelandtime.R;
import com.travelandtime.Utils.GetPresistenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifImageView;

import static java.lang.Thread.sleep;

public class BigPic extends AppCompatActivity {
    private FrameLayout bigPic,pushPost,profileLay,documentLay;
    private PhotoView showBigPic;
    String image, type, text;
    private EditText StatusTxt;
    private ImageView imgView,closeMe2,postCam2;
    private Button postBtn;
    RequestQueue queue;
    AlertDialog.Builder builder;
    private int CAMERA = 1;
    String image1 = "";
    private static final int STORAGE_PERMISSION_CODE = 123;
    private Uri uri;
    int GALLERY = 2;
    private CircleImageView profileCircleImage;
    private TextView noOfPosts,noOfFrnds,noOfLikes,profileName,userEmailId,msgForGroupText,groupTitle,walletPoints,walletUser,walletPosts,gdMember;
    private LinearLayout addFriend,likeme,removeFriends,unlike,top2Lay;
    private FrameLayout frndListLay,searchNewFrnd,groupsLay,walletLay;
    private RecyclerView frndlistRec,searchRec,groupRec;
    ArrayList<FriendList> friendlist1;
    private AutoCompleteTextView autoCompleteText;
    private ImageView searchBtn,walletRedstart,closeMe3,closeMe;
    ArrayAdapter<String> adapter;
    ArrayList<String> arr = new ArrayList<>();
    ArrayList<Suser> susers;
    ProgressDialog pd;
    private EditText editGroupText,editGroupDesc;
    private Button groupCreateBtn,groupMsgBtn,activeRedeem,disableRedeem;
    ArrayList<MyGroup> myGroup;
    private Animator currentAnimator;
    // lay10
    private TextView documentTitle;
    private Button adharCardBtn,passportCardBtn,licCardBtn;
    Integer w=0;
    private GifImageView flowers;
    Animation rotate;





    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_pic_lay);
        bigPic = (FrameLayout) findViewById(R.id.big_pic);
        showBigPic = (PhotoView) findViewById(R.id.show_big_pic);
        closeMe = (ImageView) findViewById(R.id.close_me);
        pushPost = (FrameLayout) findViewById(R.id.push_post);
        StatusTxt = (EditText) findViewById(R.id.Status_Txt);
        imgView = (ImageView) findViewById(R.id.imgView);
        postCam2 = (ImageView) findViewById(R.id.post_cam2);
        postBtn = (Button) findViewById(R.id.post_btn);
        closeMe2 = (ImageView) findViewById(R.id.close_me2);
        profileLay = (FrameLayout) findViewById(R.id.profile_lay);
        profileCircleImage = (CircleImageView) findViewById(R.id.profile_circle_image);
        profileName = (TextView) findViewById(R.id.profile_name);
        closeMe3 = (ImageView) findViewById(R.id.close_me3);
        noOfPosts = (TextView) findViewById(R.id.no_of_posts);
        noOfFrnds = (TextView) findViewById(R.id.no_of_frnds);
        noOfLikes = (TextView) findViewById(R.id.no_of_likes);
        addFriend = (LinearLayout) findViewById(R.id.add_friend);
        likeme = (LinearLayout) findViewById(R.id.likeme);
        userEmailId = (TextView) findViewById(R.id.user_email_id);
        removeFriends = (LinearLayout) findViewById(R.id.remove_friends);
        unlike = (LinearLayout) findViewById(R.id.unlike);
        frndListLay = (FrameLayout) findViewById(R.id.frnd_list_lay);
        frndlistRec = (RecyclerView) findViewById(R.id.frndlistRec);
        searchNewFrnd = (FrameLayout) findViewById(R.id.search_new_frnd);
        autoCompleteText = (AutoCompleteTextView) findViewById(R.id.autoCompleteText);
        searchBtn = (ImageView) findViewById(R.id.searchBtn);
        searchRec = (RecyclerView) findViewById(R.id.searchRec);
        pd = new ProgressDialog(this);
        groupsLay = (FrameLayout) findViewById(R.id.groups_lay);
        groupRec = (RecyclerView) findViewById(R.id.groupRec);
        groupTitle = (TextView) findViewById(R.id.group_title);
        editGroupText = (EditText) findViewById(R.id.edit_group_text);
        groupCreateBtn = (Button) findViewById(R.id.group_create_btn);
        msgForGroupText = (TextView) findViewById(R.id.msg_for_group_text);
        groupMsgBtn = (Button) findViewById(R.id.group_msg_btn);
        editGroupDesc = (EditText) findViewById(R.id.edit_group_desc);
        top2Lay = (LinearLayout) findViewById(R.id.top2_lay);
        gdMember = (TextView) findViewById(R.id.gd_member);
        // lay 10
        documentLay = (FrameLayout) findViewById(R.id.document_lay);
        documentTitle = (TextView) findViewById(R.id.document_title);
        adharCardBtn = (Button) findViewById(R.id.adhar_card_btn);
        licCardBtn = (Button) findViewById(R.id.lic_card_btn);
        passportCardBtn = (Button) findViewById(R.id.passport_card_btn);
        walletLay = (FrameLayout) findViewById(R.id.wallet_lay);
        walletPoints = (TextView) findViewById(R.id.wallet_points);
        walletUser = (TextView) findViewById(R.id.wallet_user);
        walletPosts = (TextView) findViewById(R.id.wallet_posts);
        activeRedeem = (Button) findViewById(R.id.activeRedeem);
        disableRedeem = (Button) findViewById(R.id.disableRedeem);
        flowers = (GifImageView) findViewById(R.id.flowers);
        walletRedstart = (ImageView) findViewById(R.id.wallet_redstart);


        // initilize array list for shown my friend list
        friendlist1 = new ArrayList<>();
        susers = new ArrayList<>();
        myGroup = new ArrayList<>();

        // Receive the intent and set the image
        // if type is equal to
        // 1 for Large image
        // 2 for edit the post
        // 3 for profile details
        // 4 for show my Friend List
        // 5 for show search friends
        // 6 for show Joined Groups
        // 7 for show all upcoming group excluded mine groups
        // 8 for group members in existing groups and copy the option 5 layout just change api only
        // 9 for chat same copy of option 4
        // 10 to download kyc docs
        // 11 for wallet
        // 12 large image from Domestic package details viwers

        Intent intent = getIntent();
        image = intent.getStringExtra("image");
        type = intent.getStringExtra("type");
        text = intent.getStringExtra("text");


        // open layouts as per type
        if (type.equals("1")) {
            Picasso.get().load(image).into(showBigPic);
            bigPic.setVisibility(View.VISIBLE);
        } else if (type.equals("2")) {
            pushPost.setVisibility(View.VISIBLE);
            StatusTxt.setText(text);
            if (image.equals("NA")) {

            } else {
                Picasso.get().load(image).into(imgView);
                imgView.setVisibility(View.VISIBLE);
            }

        } else if (type.equals("3")) {
            // show and hide add friends button
            if (GetPresistenceData.getMyIMEI(getApplicationContext()).equals(text)) {
                loadFriendProfile(text, Config.frnd_profile_details);
                addFriend.setVisibility(View.GONE);
                removeFriends.setVisibility(View.GONE);
                likeme.setVisibility(View.GONE);
                unlike.setVisibility(View.GONE);

                //write code here to to check user is your friend or not or write in loadprofile

            }
            checkFriendLike(text, Config.check_frnd_likes);
            loadFriendProfile(text, Config.frnd_profile_details);
            profileLay.setVisibility(View.VISIBLE);
            loadProfileData(text);

        } else if (type.equals("4")) {
            frndListLay.setVisibility(View.VISIBLE);
            new ShowMyFriends(text).execute(10);
        } else if (type.equals("5")) {
            searchNewFrnd.setVisibility(View.VISIBLE);
            new allUsers().execute();
        } else if (type.equals("6")) {
            top2Lay.setVisibility(View.GONE);
            groupTitle.setText("Joined Group");
            groupsLay.setVisibility(View.VISIBLE);
            new myGroup(Config.my_groups, "6").execute();
        } else if (type.equals("7")) {
            groupTitle.setText("Public Groups");
            groupsLay.setVisibility(View.VISIBLE);
            new myGroup(Config.groups, "7").execute();

        }else if(type.equals("8"))
        {
            searchNewFrnd.setVisibility(View.VISIBLE);
            gdMember.setVisibility(View.VISIBLE);
            autoCompleteText.setVisibility(View.GONE);
            searchBtn.setVisibility(View.GONE);
            fetchUser(text,Config.group_members,GetPresistenceData.getMyIMEI(getApplicationContext()));
        }
        else if (type.equals("9")) {
            frndListLay.setVisibility(View.VISIBLE);
            new ShowMyFriends(text).execute(10);
        }
        else if (type.equals("10")) {
            documentLay.setVisibility(View.VISIBLE);
        }
        else if (type.equals("11")) {
            //initilize animation
            rotate= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate);
            walletRedstart.setAnimation(rotate);
            // set wallet preferencese
            walletLay.setVisibility(View.VISIBLE);
            //On slow motion points shown on wallet
            final Integer points=Integer.parseInt(GetPresistenceData.getwalletPoints(getApplicationContext()));

            // UI thread over a BG thread
                (new Thread(new Runnable()
                {

                    @Override
                    public void run()
                    {
                        while (w<=points)
                            try
                            {
                                Thread.sleep(45);
                                runOnUiThread(new Runnable() // start actions in UI thread
                                {

                                    @Override
                                    public void run()
                                    {
                                        walletPoints.setText(String.valueOf(w));
                                        w++;
                                        if(w==points)
                                        {
                                            flowers.setVisibility(View.VISIBLE);

                                        }
                                    }
                                });
                            }
                            catch (InterruptedException e)
                            {
                                // ooops
                            }
                    }
                })).start(); // the while thread will start in BG thread

            walletUser.setText(GetPresistenceData.getMetaName(getApplicationContext()));
            walletPosts.setText(GetPresistenceData.getWalletsPosts(getApplicationContext()));
            // visible activeRedeem and disableRedeem buttons
            if(points>1000)
            {
                activeRedeem.setVisibility(View.VISIBLE);
                disableRedeem.setVisibility(View.GONE);
            }
            else
            {
                disableRedeem.setVisibility(View.VISIBLE);
            }
        }
        else if (type.equals("12")) {
            Picasso.get().load(image).into(showBigPic);
            bigPic.setVisibility(View.VISIBLE);
        }

        // click on activeRedeem button
        activeRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BigPic.this, "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        // click on disableRedeem button
        disableRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BigPic.this, "You need 1000 points", Toast.LENGTH_SHORT).show();
            }
        });
        // download Adharcard documentation
        adharCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adharCardBtn.setBackgroundResource(R.drawable.editbox_border2);
                if(GetPresistenceData.getMyAdhaar(getApplicationContext()).equals(""))
                {
                    Toast.makeText(BigPic.this, "You have not upload ADHAAR CARD on our portal", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    String file_url=GetPresistenceData.getMyAdhaar(getApplicationContext());
                    // start download file in local host
                    DownloadManager.Request dmr = new DownloadManager.Request(Uri.parse(file_url));
                    //Alternative if you don't know filename
                    String fileName = URLUtil.guessFileName(file_url, null, MimeTypeMap.getFileExtensionFromUrl(file_url));
                    dmr.setTitle(fileName);
                    dmr.setDescription("Downloading Adhaar Card"); //optional
                    dmr.allowScanningByMediaScanner();
                    dmr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    dmr.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    dmr.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    assert manager != null;
                    manager.enqueue(dmr);
                    Toast.makeText(BigPic.this, "File downloaded check into("+"Data/downloads/"+fileName+")", Toast.LENGTH_SHORT).show();
                    adharCardBtn.setBackgroundResource(R.drawable.editbox_border);
                    adharCardBtn.setText("File Downloaded");
                   Handler handler=new Handler();
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                           adharCardBtn.setText("Download Adhaar card Again");
                           adharCardBtn.setBackgroundResource(R.drawable.edittext3);
                       }
                   },2000);
                }
            }
        });

        // download licence
        licCardBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                licCardBtn.setBackgroundResource(R.drawable.editbox_border2);
                if(GetPresistenceData.getMyLic(getApplicationContext()).equals(""))
                {
                    Toast.makeText(BigPic.this, "You have not upload Licence on our portal", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    String file_url=GetPresistenceData.getMyAdhaar(getApplicationContext());
                    // start download file in local host
                    DownloadManager.Request dmr = new DownloadManager.Request(Uri.parse(file_url));
                    //Alternative if you don't know filename
                    String fileName = URLUtil.guessFileName(file_url, null, MimeTypeMap.getFileExtensionFromUrl(file_url));
                    dmr.setTitle(fileName);
                    dmr.setDescription("Downloading Licence"); //optional
                    dmr.allowScanningByMediaScanner();
                    dmr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    dmr.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    dmr.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    assert manager != null;
                    manager.enqueue(dmr);
                    Toast.makeText(BigPic.this, "File downloaded check into("+"Data/downloads/"+fileName+")", Toast.LENGTH_SHORT).show();
                    licCardBtn.setBackgroundResource(R.drawable.editbox_border);
                    licCardBtn.setText("File Downloaded");
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            licCardBtn.setText("Download Licence Again");
                            licCardBtn.setBackgroundResource(R.drawable.edittext3);
                        }
                    },2000);
                }
            }
        });

        // download passport copy
        passportCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passportCardBtn.setBackgroundResource(R.drawable.editbox_border2);
                if(GetPresistenceData.getMyPassport(getApplicationContext()).equals(""))
                {
                    Toast.makeText(BigPic.this, "You have not upload Passport on our portal", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    String file_url=GetPresistenceData.getMyAdhaar(getApplicationContext());
                    // start download file in local host
                    DownloadManager.Request dmr = new DownloadManager.Request(Uri.parse(file_url));
                    //Alternative if you don't know filename
                    String fileName = URLUtil.guessFileName(file_url, null, MimeTypeMap.getFileExtensionFromUrl(file_url));
                    dmr.setTitle(fileName);
                    dmr.setDescription("Downloading Passport"); //optional
                    dmr.allowScanningByMediaScanner();
                    dmr.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    dmr.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    dmr.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    assert manager != null;
                    manager.enqueue(dmr);
                    Toast.makeText(BigPic.this, "File downloaded check into("+"Data/downloads/"+fileName+")", Toast.LENGTH_SHORT).show();
                    passportCardBtn.setBackgroundResource(R.drawable.editbox_border);
                    passportCardBtn.setText("File Downloaded");
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            passportCardBtn.setText("Download Passport Again");
                            passportCardBtn.setBackgroundResource(R.drawable.edittext3);
                        }
                    },2000);
                }
            }
        });

        // Create group layout click codes start from here
        groupMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupMsgBtn.setVisibility(View.GONE);
                msgForGroupText.setVisibility(View.GONE);
                groupCreateBtn.setVisibility(View.VISIBLE);
                editGroupText.setVisibility(View.VISIBLE);

            }
        });

        groupCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String group_name = editGroupText.getText().toString();
                if (group_name.length() < 3) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Invalid group name", Snackbar.LENGTH_SHORT).show();
                    editGroupText.requestFocus();
                } else if (group_name.length() > 70) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Maximum limit exceed", Snackbar.LENGTH_SHORT).show();
                    editGroupText.requestFocus();
                } else {
                    if (editGroupDesc.getVisibility() == View.GONE) {
                        editGroupText.setVisibility(View.GONE);
                        editGroupDesc.setVisibility(View.VISIBLE);
                        String grp_desc = editGroupDesc.getText().toString();
                        if (grp_desc.length() < 3) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Invalid group name", Snackbar.LENGTH_SHORT).show();
                            editGroupDesc.requestFocus();
                        } else if (grp_desc.length() > 120) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Maximum description limit exceed", Snackbar.LENGTH_SHORT).show();
                            editGroupDesc.requestFocus();
                        } else {
                            createGroup(group_name, grp_desc, GetPresistenceData.getMetaName(getApplicationContext()), GetPresistenceData.getMyIMEI(getApplicationContext()));
                        }
                    } else {
                        editGroupText.setVisibility(View.GONE);
                        editGroupDesc.setVisibility(View.VISIBLE);
                        String grp_desc = editGroupDesc.getText().toString();
                        if (grp_desc.length() < 3) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Invalid group name", Snackbar.LENGTH_SHORT).show();
                            editGroupDesc.requestFocus();
                        } else if (grp_desc.length() > 120) {
                            Snackbar.make(getWindow().getDecorView().getRootView(), "Maximum description limit exceed", Snackbar.LENGTH_SHORT).show();
                            editGroupDesc.requestFocus();
                        } else {
                            createGroup(group_name, grp_desc, GetPresistenceData.getMetaName(getApplicationContext()), GetPresistenceData.getMyIMEI(getApplicationContext()));
                        }
                    }
                }
            }
        });


        // Create group click listner codes ends here

        // click on click listner
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                susers.clear();
                pd.show();
                String user_name = autoCompleteText.getText().toString();
                fetchUser(user_name,Config.frnd_list,GetPresistenceData.getMyIMEI(getApplicationContext()));
            }
        });
        // click on like button
        likeme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new saveNotification(text,GetPresistenceData.getMetaName(BigPic.this),", like your profile image","1",getApplicationContext(),queue).execute();
                addFriend(text, Config.friend_like, "3");
            }
        });
        // click on unlike button
        unlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new saveNotification(text,GetPresistenceData.getMetaName(BigPic.this),", dislike your profile image","1",getApplicationContext(),queue).execute();
                addFriend(text, Config.friend_unlike, "4");
            }
        });
        // click on Remove friends
        // type show 1 for add 2 for remove
        removeFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new saveNotification(text,GetPresistenceData.getMetaName(BigPic.this),", removed you from friend","1",getApplicationContext(),queue).execute();
                addFriend(text, Config.remove_friend, "2");
            }
        });


        // update post
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate status data
                String ideas_text = StatusTxt.getText().toString();
                if (ideas_text.length() < 3) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Your ideas are too small", Snackbar.LENGTH_SHORT).show();
                    StatusTxt.requestFocus();
                } else if (ideas_text.length() > 250) {
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Text limit maximum exceed than 250 words", Snackbar.LENGTH_SHORT).show();
                    StatusTxt.requestFocus();
                } else {
                    if (image.equals("")) {
                        image = "";
                        String imei = GetPresistenceData.getMyIMEI(getApplicationContext());
                        String email = GetPresistenceData.getEmail(getApplicationContext());
                        String name = GetPresistenceData.getMetaName(getApplicationContext());
                        uploadtoserverr(image1, ideas_text, imei, email, name);
                    } else {
                        String imei = GetPresistenceData.getMyIMEI(getApplicationContext());
                        String email = GetPresistenceData.getEmail(getApplicationContext());
                        String name = GetPresistenceData.getMetaName(getApplicationContext());
                        uploadtoserverr(image1, ideas_text, imei, email, name);
                    }

                }
            }
        });
        // add friends
        addFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.equals(GetPresistenceData.getMyIMEI(getApplicationContext()))) {
                    Toast.makeText(BigPic.this, "You cant add youself as your friend", Toast.LENGTH_SHORT).show();
                } else {
                    addFriend(text, Config.add_friend, "1");
                }

            }
        });


        // close btn close the popup for enter status
        closeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("1")) {
                    Intent intent1 = new Intent(BigPic.this, Community.class);
                    intent1.addFlags(intent1.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent1);
                    finish();
                }
                else if(type.equals("12"))
                {
                    finish();

                }
            }
        });
        // click on cam
        postCam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseImageOptions();
            }
        });
        // click on close3 profile page
        closeMe3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }// onCreate closer






    // creating new group
    private void createGroup(final String group_name, final String grp_desc, final String metaName, final String myIMEI) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.create_group, new Response.Listener<String>() {
            @Override
            public void onResponse(String response12) {
                pd.dismiss();
                parseData12(response12);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                // no need to pass imei but as security i passed it
                mydata.put("user_imei", myIMEI);
                mydata.put("user_name", metaName);
                mydata.put("group_name", group_name);
                mydata.put("group_desc", grp_desc);
                mydata.put("user_pic", GetPresistenceData.getMetaPic(getApplicationContext()));
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData12(String response12) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response12);
            String Status = jsonObject.getString("status");
            if (Status.equals("1")) {
                Toast.makeText(this, "You have created a new group", Toast.LENGTH_SHORT).show();
                groupCreateBtn.setVisibility(View.GONE);
                editGroupText.setVisibility(View.GONE);
                editGroupDesc.setVisibility(View.GONE);
                groupMsgBtn.setVisibility(View.GONE);
                msgForGroupText.setVisibility(View.VISIBLE);
                msgForGroupText.setText("You just created a group !");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        top2Lay.setVisibility(View.GONE);
                    }
                }, 3000);
            } else {
                Toast.makeText(this, "Group creating failed", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }

    private void fetchUser(final String user_name, String url, final String imei) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response11) {
                pd.dismiss();
                parseData11(response11);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                // no need to pass imei but as security i passed it
                mydata.put("user_name", user_name);
                mydata.put("imei", imei);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData11(String response11) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response11);
            String Status = jsonObject.getString("status");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String frnd = jsonObject1.getString("frnd");
                String imei = jsonObject1.getString("imei");
                String name = jsonObject1.getString("name");
                String state = jsonObject1.getString("state");
                String my_pic = jsonObject1.getString("my_pic");

                Suser suser2 = new Suser(imei, name, state, my_pic,frnd);
                susers.add(suser2);


                RSearchF rSearchF = new RSearchF(getApplicationContext(), susers);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                searchRec.setLayoutManager(layoutManager);
                searchRec.setHasFixedSize(true);
                searchRec.setAdapter(rSearchF);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        };
    }


    private void loadFriendProfile(final String text, String frnd_profile_details) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, frnd_profile_details, new Response.Listener<String>() {
            @Override
            public void onResponse(String response5) {
                parseData5(response5);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei", GetPresistenceData.getMyIMEI(getApplicationContext()));
                mydata.put("frnd_imei", text);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData5(String response5) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response5);
            String Status = jsonObject.getString("status");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String frnd = jsonObject1.getString("frnd");
                String like = jsonObject1.getString("like");
                String posts = jsonObject1.getString("posts");

                // set post friend and like widget value
                noOfPosts.setText(posts);
                noOfFrnds.setText(frnd);
                noOfLikes.setText(like);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }

    private void checkFriendLike(final String text, String s) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response4) {
                parseData4(response4);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei", GetPresistenceData.getMyIMEI(getApplicationContext()));
                mydata.put("frnd_imei", text);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData4(String response4) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response4);
            String Status = jsonObject.getString("status");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String like = jsonObject1.getString("like");
                String frnd = jsonObject1.getString("frnd");
                if (frnd.equals("YES")) {
                    addFriend.setVisibility(View.GONE);
                    removeFriends.setVisibility(View.VISIBLE);
                } else {
                    addFriend.setVisibility(View.VISIBLE);
                    removeFriends.setVisibility(View.GONE);
                }
                if (like.equals("YES")) {
                    likeme.setVisibility(View.GONE);
                    unlike.setVisibility(View.VISIBLE);
                } else {
                    likeme.setVisibility(View.VISIBLE);
                    unlike.setVisibility(View.GONE);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }

    private void addFriend(final String text, String s, final String type) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, s, new Response.Listener<String>() {
            @Override
            public void onResponse(String response3) {
                parseData3(response3, type, text);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei", GetPresistenceData.getMyIMEI(getApplicationContext()));
                mydata.put("frnd_imei", text);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData3(String response3, String types, String imei) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response3);
            String Status = jsonObject.getString("status");
            if (Status.equals("1")) {
                // 1 for add friend, 2 for remove 3 for like 4 for unlike
                if (types.equals("1")) {
                    addFriend.setVisibility(View.GONE);
                    removeFriends.setVisibility(View.VISIBLE);
                    loadFriendProfile(text, Config.frnd_profile_details);
                    saveNotification(imei, GetPresistenceData.getMetaName(getApplicationContext()), ", added you as friend ", "1");
                } else if (types.equals("2")) {
                    addFriend.setVisibility(View.VISIBLE);
                    removeFriends.setVisibility(View.GONE);
                    loadFriendProfile(text, Config.frnd_profile_details);
                    saveNotification(imei, GetPresistenceData.getMetaName(getApplicationContext()), ", removed you as friend ", "1");
                } else if (types.equals("3")) {
                    likeme.setVisibility(View.GONE);
                    unlike.setVisibility(View.VISIBLE);
                    loadFriendProfile(text, Config.frnd_profile_details);
                    saveNotification(imei, GetPresistenceData.getMetaName(getApplicationContext()), ", like your profile photo", "1");
                } else if (types.equals("4")) {
                    likeme.setVisibility(View.VISIBLE);
                    unlike.setVisibility(View.GONE);
                    loadFriendProfile(text, Config.frnd_profile_details);
                    saveNotification(imei, GetPresistenceData.getMetaName(getApplicationContext()), ", unlike your profile photo ", "1");
                }
            } else {
                Toast.makeText(this, "Network Issue", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }

    private void saveNotification(final String imei, final String metaName, final String msg, final String type) {
        Cache cache1 = new DiskBasedCache(getApplicationContext().getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.notification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response8) {

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
                mydata.put("imei", imei);
                mydata.put("name", metaName);
                mydata.put("msgg", msg);
                mydata.put("type", type);

                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void loadProfileData(final String text) {
        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.profile_details, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                parseData2(response2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei", text);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData2(String response2) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response2);
            String Status = jsonObject.getString("status");
            if (Status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String name = jsonObject1.getString("name");
                    String state = jsonObject1.getString("state");
                    String my_pic = jsonObject1.getString("my_pic");
                    String email = jsonObject1.getString("email");
                    // set the data on widgets
                    Picasso.get().load(my_pic).into(profileCircleImage);
                    profileName.setText(name);
                    userEmailId.setText(email);
                }
            } else {
                finish();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
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
                                break;
                            case 1:
                                gallery();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void gallery() {
        //method to show file chooser
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void captureimagefromcamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA && data != null) {
//imgage captured
            Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            imageToString(Objects.requireNonNull(bitmap));
            imgView.setImageBitmap(bitmap);
            imgView.setVisibility(View.VISIBLE);
            //capture_image.setImageBitmap(bitmap);
        } else if (requestCode == GALLERY && data != null && data.getData() != null) {
            if (resultCode == RESULT_OK) {

                Uri contentURI = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageToString(bitmap);
                    imgView.setImageBitmap(bitmap);
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
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        image1 = Base64.encodeToString(imgbytes, Base64.DEFAULT);


    }

    private void uploadtoserverr(final String image1, final String ideas_text, final String imei, final String email, final String name) {

        Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.posts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                parseData1(response1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei", imei);
                mydata.put("email", email);
                mydata.put("name", name);
                mydata.put("post_txt", ideas_text);
                mydata.put("post_img", image1);
                mydata.put("shared", "0");
                mydata.put("shared_name", "0");
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
            String Status = jsonObject.getString("status");
            if (Status.equals("1")) {
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(BigPic.this, Community.class);
                startActivity(intent1);
                finish();
            } else {
                Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }


    @SuppressLint("StaticFieldLeak")
    class ShowMyFriends extends AsyncTask<Integer, Integer, String> {
        String mtext;

        ShowMyFriends(String text) {
            this.mtext = text;
        }

        @Override
        protected String doInBackground(final Integer... params) {
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue = new RequestQueue(cache1, network1);
            queue.start();
            StringRequest request = new StringRequest(Request.Method.POST, Config.my_friend_list, new Response.Listener<String>() {
                @Override
                public void onResponse(String response7) {
                    parseData7(response7);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> mydata = new HashMap<>();
                    // no need to pass imei but as security i passed it
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

        @Override
        protected void onPostExecute(String result) {


        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Toast.makeText(BigPic.this, String.valueOf(values[0]), Toast.LENGTH_SHORT).show();


        }
    }

    private void parseData7(String response7) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response7);
            String Status = jsonObject.getString("status");
            if (Status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String name = jsonObject1.getString("name");
                    String imei = jsonObject1.getString("imei");
                    String pic = jsonObject1.getString("pic");


                    FriendList friendList2 = new FriendList(name, imei, pic,image);
                    friendlist1.add(friendList2);

                    FriendRec friendRec = new FriendRec(getApplicationContext(), friendlist1);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    frndlistRec.setLayoutManager(layoutManager);
                    frndlistRec.setHasFixedSize(true);
                    frndlistRec.setAdapter(friendRec);
                }
            } else {
                Toast.makeText(this, "No friend found", Toast.LENGTH_SHORT).show();
                finish();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }


    private void parseData10(String response10) {

        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response10);
            String Status = jsonObject.getString("status");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String imei = jsonObject1.getString("imei");
                String name = jsonObject1.getString("name");
                String my_pic = jsonObject1.getString("my_pic");
                String email = jsonObject1.getString("email");

                arr.add(name);
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, arr);
            autoCompleteText.setThreshold(1);//will start working from first character
            autoCompleteText.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
            autoCompleteText.setTextColor(Color.RED);


        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    @SuppressLint("StaticFieldLeak")
    private class allUsers extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue = new RequestQueue(cache1, network1);
            queue.start();
            StringRequest request = new StringRequest(Request.Method.POST, Config.all_users, new Response.Listener<String>() {
                @Override
                public void onResponse(String response10) {
                    parseData10(response10);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> mydata = new HashMap<>();
                    // no need to pass imei but as security i passed it
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

    @SuppressLint("StaticFieldLeak")
    private class myGroup extends AsyncTask<String, String, String> {
        String my_groups, types;

        myGroup(String my_groups, String types) {
            this.my_groups = my_groups;
            this.types = types;
        }

        @Override
        protected String doInBackground(String... strings) {
            Cache cache1 = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network1 = new BasicNetwork(new HurlStack());

            queue = new RequestQueue(cache1, network1);
            queue.start();
            StringRequest request = new StringRequest(Request.Method.POST, my_groups, new Response.Listener<String>() {
                @Override
                public void onResponse(String response15) {
                    parseData15(response15, types);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(BigPic.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getParams() {
                    Map<String, String> mydata = new HashMap<>();
                    // no need to pass imei but as security i passed it
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

    private void parseData15(String response15, String types) {
        JSONObject jsonObject = null;

        if (types.equals("6")) {
            try {
                jsonObject = new JSONObject(response15);

                String Status = jsonObject.getString("status");

                if (Status.equals("1")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String group_id = jsonObject1.getString("sr");
                        String users_count = jsonObject1.getString("users_count");
                        String group_name = jsonObject1.getString("group_name");
                        String group_desc = jsonObject1.getString("group_desc");
                        String user_imei = jsonObject1.getString("user_imei");
                        String user_name = jsonObject1.getString("user_name");
                        String owner = jsonObject1.getString("owner");
                        String date = jsonObject1.getString("date");
                        String grp_checksum = jsonObject1.getString("grp_checksum");


                        MyGroup myGroup1 = new MyGroup(group_id, group_name, group_desc, user_imei, user_name, owner,types,date,users_count,grp_checksum);
                        myGroup.add(myGroup1);

                        GroupR groupR = new GroupR(getApplicationContext(), myGroup);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        groupRec.setLayoutManager(layoutManager);
                        groupRec.setHasFixedSize(true);
                        groupRec.setAdapter(groupR);

                    }


                } else {
                    Toast.makeText(this, "Recently You have not joined any groups", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            try {
                jsonObject = new JSONObject(response15);

                String Status = jsonObject.getString("status");

                if (Status.equals("1")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                        String group_id = jsonObject1.getString("sr");
                        String users_count = jsonObject1.getString("users_count");
                        String group_name = jsonObject1.getString("group_name");
                        String group_desc = jsonObject1.getString("group_desc");
                        String user_imei = jsonObject1.getString("user_imei");
                        String user_name = jsonObject1.getString("user_name");
                        String owner = "NA";
                        String date = jsonObject1.getString("date");
                        String grp_checksum = jsonObject1.getString("grp_checksum");

                        MyGroup myGroup1 = new MyGroup(group_id, group_name, group_desc, user_imei, user_name, owner,types,date,users_count,grp_checksum);
                        myGroup.add(myGroup1);

                        GroupR groupR = new GroupR(getApplicationContext(), myGroup);
                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        groupRec.setLayoutManager(layoutManager);
                        groupRec.setHasFixedSize(true);
                        groupRec.setAdapter(groupR);

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}