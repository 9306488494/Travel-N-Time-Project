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
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import java.util.Objects;



public class RegisterMe extends AppCompatActivity {
    private TextView titleName;
    private LinearLayout regLay;
    private EditText regPhone,regEmail,regPass,regConfirmPass,loginEmail,loginPass,resetEmail;
    private Button regBtn,loginBtn;
    private LinearLayout loginLay;
    private TextView resetText;
    private LinearLayout resetLay;
    private Button resetBtn;
    private EditText regName;
    private LinearLayout messageLay;
    private TextView messageTxt,messageSubTxt,alreadyRegisteredUser,passwordResettxt;
    private LinearLayout resetNewpassLay;
    private EditText oldPassTxt,newPass;
    private Button resetNewPass;
    private EditText myEmail;
    private LinearLayout KYCdoc;
    private Spinner spinner;
    private ImageView closeMe;
    private int CAMERA = 1;
    String image="";
    private static final int STORAGE_PERMISSION_CODE=123;
    AlertDialog.Builder builder;
    String adhaar;
    String lic;
    String passport;
    private Uri uri;
    int GALLERY = 2;
    int reqcode=1;
    private CardView kycPopupMessage;
    RequestQueue queue4;
    FlightsDatabase flightsDatabase;
    HotelsDatabase hotelsDatabase;
    ItiDatabase itiDatabase;
    DompackDatabase domPacks;
    InternationDatabase intDatabase;
    ProgressDialog pd;

    String status,reg_name2,reg_phone2,reg_email2,reg_pass2,reg_confirmpass2;
    String old_pass,new_pass,new_email;
    String login_email2,login_pass2;
    String reset_email2,mail_msg="Dear User, \n Your password is : ",compilence_msg=" \n \n \n Warm regards, \n Travel n Time \n http://travelntime.com";
    String subject="Password Reset Successfully";
    public static final String REQUEST_TAG = "VolleyBlockingRequestActivity";
    RequestQueue queue;
    private LinearLayout mainTitle;




    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_me);

        titleName = (TextView) findViewById(R.id.title_name);
        regLay = (LinearLayout) findViewById(R.id.reg_lay);
        regName = (EditText) findViewById(R.id.reg_name);
        regPhone = (EditText) findViewById(R.id.reg_phone);
        regEmail = (EditText) findViewById(R.id.reg_email);
        regPass = (EditText) findViewById(R.id.reg_pass);
        regConfirmPass = (EditText) findViewById(R.id.reg_confirmPass);
        regBtn = (Button) findViewById(R.id.reg_btn);
        loginLay = (LinearLayout) findViewById(R.id.login_lay);
        loginEmail = (EditText) findViewById(R.id.login_email);
        loginPass = (EditText) findViewById(R.id.login_pass);
        loginBtn = (Button) findViewById(R.id.login_btn);
        resetText = (TextView) findViewById(R.id.resetText);
        resetLay = (LinearLayout) findViewById(R.id.reset_lay);
        resetEmail = (EditText) findViewById(R.id.reset_email);
        resetBtn = (Button) findViewById(R.id.reset_btn);
        messageLay = (LinearLayout) findViewById(R.id.message_lay);
        messageTxt = (TextView) findViewById(R.id.message_txt);
        messageSubTxt = (TextView) findViewById(R.id.message_subTxt);
        alreadyRegisteredUser = (TextView) findViewById(R.id.already_registered_user);
        passwordResettxt = (TextView) findViewById(R.id.passwordResettxt);
        resetNewpassLay = (LinearLayout) findViewById(R.id.resetNewpass_lay);
        oldPassTxt = (EditText) findViewById(R.id.old_pass_txt);
        newPass = (EditText) findViewById(R.id.new_pass);
        resetNewPass = (Button) findViewById(R.id.reset_new_pass);
        myEmail = (EditText) findViewById(R.id.my_email);
        KYCdoc = (LinearLayout) findViewById(R.id.KYCdoc);
        spinner = (Spinner) findViewById(R.id.spinner);
        closeMe = (ImageView) findViewById(R.id.closeMe);
        kycPopupMessage = (CardView) findViewById(R.id.kyc_popup_message);
        flightsDatabase=new FlightsDatabase(RegisterMe.this);
        hotelsDatabase=new HotelsDatabase(RegisterMe.this);
        itiDatabase=new ItiDatabase(RegisterMe.this);
        domPacks=new DompackDatabase(RegisterMe.this);
        intDatabase=new InternationDatabase(RegisterMe.this);
        mainTitle = (LinearLayout) findViewById(R.id.main_title);

        // setup builder
        builder=new AlertDialog.Builder(getApplicationContext());
        // setup storage permissions


        // progress dialogue box initilization
        pd=new ProgressDialog(this);
        pd.setTitle("Processing");
        pd.setMessage("Wait...");
        pd.setCancelable(false);
        Intent intent=getIntent();
        status=intent.getStringExtra("doIt");
        // get Intent
        // if intent is 0 then user will login and if 1 then user will register
        if(GetPresistenceData.getMyRegStatus(getApplicationContext()).equals("1"))
        {
            if(GetPresistenceData.getMyLogon(getApplicationContext()).equals("1"))
            {
                // 5 for kyc upload
                switch (status) {
                    case "0":
                        loginLay.setVisibility(View.VISIBLE);
                        titleName.setText("Login Here");
                        break;
                    case "1":
                        regLay.setVisibility(View.VISIBLE);
                        titleName.setText("Registration");
                        break;
                    case "2":
                        new checkKYC().execute("", "", "");
                        break;
                    case "3":
                        new checkKYC().execute("", "", "");
                        break;
                    case "4":
                        new checkKYC().execute("", "", "");
                        break;
                    case "5":
                        new checkKYC().execute("", "", "");
                        break;
                }
            }
            else
            {
                loginLay.setVisibility(View.VISIBLE);
                titleName.setText("Login Here");
            }
        }
        else
        {
            regLay.setVisibility(View.VISIBLE);
            titleName.setText("Registration");
        }



        // New Password Reset button
        resetNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store value of data
                old_pass=oldPassTxt.getText().toString();
                new_pass=newPass.getText().toString();
                new_email=myEmail.getText().toString();
                // validate all data
                if(old_pass.length()<=6 || old_pass.length()>=30)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Password limit 6-30 digits",Snackbar.LENGTH_SHORT).show();
                    oldPassTxt.requestFocus();
                }
                else if(new_pass.length()<=6 || new_pass.length()>=30)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Password limit 6-30 digits",Snackbar.LENGTH_SHORT).show();
                    newPass.requestFocus();
                }
                else if(new_email.length()<6 || new_email.length()>60)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid email id",Snackbar.LENGTH_SHORT).show();
                    myEmail.requestFocus();
                }
                else if(!new_email.contains("@"))
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid email id",Snackbar.LENGTH_SHORT).show();
                    myEmail.requestFocus();
                }
                else
                {
                    pd.show();
                    pd.setMessage("Checking profile data...");
                    updateNewPass(old_pass,new_pass,new_email);
                }
                
            }
        });
        // send to password reset layout
        passwordResettxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLay.setVisibility(View.GONE);
                resetNewpassLay.setVisibility(View.VISIBLE);
                titleName.setText("Password Reset");
            }
        });
        // registration
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validation on data
                reg_name2=regName.getText().toString();
                reg_phone2=regPhone.getText().toString();
                reg_email2=regEmail.getText().toString();
                reg_pass2=regPass.getText().toString();
                reg_confirmpass2=regConfirmPass.getText().toString();

                if(reg_name2.length()<2)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid Name",Snackbar.LENGTH_SHORT).show();
                    regName.requestFocus();
                }
                else if(reg_name2.contains("1") || reg_name2.contains("2") || reg_name2.contains("3") || reg_name2.contains("4") || reg_name2.contains("5") || reg_name2.contains("6") || reg_name2.contains("7") || reg_name2.contains("8") || reg_name2.contains("9") ||reg_name2.contains("0") || reg_name2.contains("#") || reg_name2.contains("@") || reg_name2.contains("!"))
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Dont use digit in name",Snackbar.LENGTH_SHORT).show();
                    regName.requestFocus();
                }
                else if(reg_phone2.length()<10 || reg_phone2.length()>13)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid phone number",Snackbar.LENGTH_SHORT).show();
                    regPhone.requestFocus();
                }
                else if(reg_email2.length()<6 || reg_email2.length()>60)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid email id",Snackbar.LENGTH_SHORT).show();
                    regEmail.requestFocus();
                }
                else if(!reg_email2.contains("@"))
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid email id",Snackbar.LENGTH_SHORT).show();
                    regEmail.requestFocus();
                }
                else if(reg_pass2.length()<6)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Require password more than 6 digit",Snackbar.LENGTH_SHORT).show();
                    regPass.requestFocus();
                }
                else if(reg_pass2.length()>25)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Password max limit exceed",Snackbar.LENGTH_SHORT).show();
                    regPass.requestFocus();
                }
                else if(reg_confirmpass2.length()<6)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Confirm password more than 6 digit",Snackbar.LENGTH_SHORT).show();
                    regConfirmPass.requestFocus();
                }
                else if(reg_confirmpass2.length()>25)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Password max limit exceed",Snackbar.LENGTH_SHORT).show();
                    regConfirmPass.requestFocus();
                }
                else if (!reg_pass2.equals(reg_confirmpass2))
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Password not matching",Snackbar.LENGTH_SHORT).show();
                    regConfirmPass.requestFocus();
                }
                else
                {
                    pd.show();
                    SaveRegistrationForm(reg_name2,reg_phone2,reg_email2,reg_pass2);
                }

            }
        });

        // login
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // collect data and validate
                login_email2=loginEmail.getText().toString();
                login_pass2=loginPass.getText().toString();

                if(login_email2.length()<6 || login_email2.length()>50)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid email id",Snackbar.LENGTH_SHORT).show();
                    loginEmail.requestFocus();
                }
               /* else if(!login_email2.contains("@"))
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Enter proper email id",Snackbar.LENGTH_SHORT).show();
                    loginEmail.requestFocus();
                }*/
                else if(login_pass2.length()<6 || login_pass2.length()>25)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Password exceeding excepted limit",Snackbar.LENGTH_SHORT).show();
                    loginPass.requestFocus();
                }
                else
                {
                    pd.show();
                    checkLogin(login_email2,login_pass2);
                }
            }
        });

        // password reset form
        resetText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // make invisible all layouts
                loginLay.setVisibility(View.GONE);
                resetLay.setVisibility(View.VISIBLE);
                titleName.setText("Forget password reset");

            }
        });
        // click on reset password button
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get data and validate email id
                reset_email2=resetEmail.getText().toString();
                if(reset_email2.length()<6 || reset_email2.length()>40)
                {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid email id",Snackbar.LENGTH_SHORT).show();
                    resetEmail.requestFocus();
                }
                else if(!reset_email2.contains("@")) {
                    Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid email id",Snackbar.LENGTH_SHORT).show();
                    resetEmail.requestFocus();
                }
                else
                {
                    pd.show();
                    resetPassword(reset_email2);
                }
            }
        });

        // click on already registered user
        alreadyRegisteredUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regLay.setVisibility(View.GONE);
                loginLay.setVisibility(View.VISIBLE);
            }
        });

        // close on close me button
        closeMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.equals("1"))
                {
                    Intent intent=new Intent(getApplicationContext(),Trip.class);
                    startActivity(intent);
                    finish();
                }
                else if(status.equals("2"))
                {
                    Intent intent=new Intent(getApplicationContext(),Trip.class);
                    startActivity(intent);
                    finish();
                }
                else if(status.equals("3"))
                {
                    Intent intent=new Intent(getApplicationContext(),MyProfile.class);
                    startActivity(intent);
                    finish();
                }
                else if(status.equals("4"))
                {
                    // collect profile data
                    CollectMetaData();

                }
                else if(status.equals("5"))
                {
                    Intent intent=new Intent(getApplicationContext(),MyProfile.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                    finish();

                }
            }
        });


    } // onCreate closer

    private void updateNewPass(final String old_pass, final String new_pass, final String new_email) {
        RequestQueue queue3 = Volley.newRequestQueue((Objects.requireNonNull(getApplicationContext())));
        StringRequest request3 = new StringRequest(Request.Method.POST, Config.update_pass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response5) {
                parseData5(response5);

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
                mydata.put("email",new_email);
                mydata.put("new_pass",new_pass);
                mydata.put("old_pass",old_pass);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request3.setRetryPolicy(policy);
        queue3.add(request3);
    }

    private void parseData5(String response5) {
        // send mail with password  if response successfully
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(response5);
            String Status=jsonObject.getString("status");
            if(Status.equals("1"))
            {
                // Password successfully updated
                 pd.dismiss();
                resetNewpassLay.setVisibility(View.GONE);
                messageLay.setVisibility(View.VISIBLE);
                messageTxt.setText("Password Updated");
                messageSubTxt.setText("Wait a movement");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messageLay.setVisibility(View.GONE);
                        loginLay.setVisibility(View.VISIBLE);

                    }
                },2000);
            }
            else  if(Status.equals("2"))
            {
                // Password updation failed
                pd.dismiss();
                resetNewpassLay.setVisibility(View.GONE);
                messageLay.setVisibility(View.VISIBLE);
                messageTxt.setText("Error !!");
                messageSubTxt.setText("Wait a movement");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messageLay.setVisibility(View.GONE);
                        resetNewpassLay.setVisibility(View.VISIBLE);
                    }
                },2000);
            }
            else if(Status.equals("0"))
            {
                // no record found on this mail id
                pd.dismiss();
                resetNewpassLay.setVisibility(View.GONE);
                messageLay.setVisibility(View.VISIBLE);
                messageTxt.setText("Invalid Old Passwor/email");
                messageSubTxt.setText("Wait a movement");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messageLay.setVisibility(View.GONE);
                        resetNewpassLay.setVisibility(View.VISIBLE);
                    }
                },2000);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void resetPassword(final String reset_email2) {
        RequestQueue queue = Volley.newRequestQueue((Objects.requireNonNull(getApplicationContext())));
        StringRequest request = new StringRequest(Request.Method.POST, Config.reset_pass_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                parseData4(response2);

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
                mydata.put("imei",GetPresistenceData.getMyIMEI(getApplicationContext()));
                mydata.put("email",reset_email2);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData4(String response2) {
        // send mail with password  if response successfully
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(response2);
            String Status=jsonObject.getString("status");
            if(Status.equals("1"))
            {
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String pass = jsonObject1.getString("pass");
                    String email1 = jsonObject1.getString("email");

                    // now send the mail to user
                    pd.dismiss();
                    String to=email1;
                    String message=mail_msg+pass+compilence_msg;
                    Intent intentMail = new Intent(Intent.ACTION_SEND);
                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                    intentMail.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intentMail.putExtra(Intent.EXTRA_TEXT, message);

                    //need this to prompts email client only
                    intentMail.setType("message/rfc822");
                    startActivity(Intent.createChooser(intentMail, "Choose an Email client :"));
                }

            }
            else if(Status.equals("0"))
            {
                // no record found on this mail id
                pd.dismiss();
                resetLay.setVisibility(View.GONE);
                messageLay.setVisibility(View.VISIBLE);
                messageTxt.setText("No Record Found");
                messageSubTxt.setText("Wait a movement");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messageLay.setVisibility(View.GONE);
                        resetLay.setVisibility(View.VISIBLE);
                    }
                },2000);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void checkLogin(final String login_email2, final String login_pass2) {
        RequestQueue queue = Volley.newRequestQueue((Objects.requireNonNull(getApplicationContext())));
        StringRequest request = new StringRequest(Request.Method.POST, Config.check_login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                parseData2(response2,login_email2,login_pass2);
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
                mydata.put("email",login_email2);
                mydata.put("pass",login_pass2);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);

    }

    private void parseData2(String response2, String login_email2, String login_pass2) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response2);
            String Status = jsonObject.getString("status");
            String imei = jsonObject.getString("imei");
            String points=jsonObject.getString("points");
            String posts=jsonObject.getString("posts");

            if(Status.equals("0"))
            {
                // failed
                pd.dismiss();
                PresistenceData.checkLogon(getApplicationContext(),"0");
                Snackbar.make(getWindow().getDecorView().getRootView(),"Invalid Credential",Snackbar.LENGTH_SHORT).show();
                loginLay.setVisibility(View.GONE);
                messageLay.setVisibility(View.VISIBLE);
                messageTxt.setText("Invalid Credential");
                messageSubTxt.setText("Wait a movement");

                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messageLay.setVisibility(View.GONE);
                        loginLay.setVisibility(View.VISIBLE);

                    }
                },2000);
            }
            else if(Status.equals("1"))
            {// success
                // save into presistence storage
                pd.dismiss();
                PresistenceData.Wallets(getApplicationContext(),points,posts);
                PresistenceData.checkLogon(getApplicationContext(),"1");
                PresistenceData.checkRegistration(getApplicationContext(),"1");
                PresistenceData.profileData(getApplicationContext(),login_email2,login_pass2);
                PresistenceData.saveIMEI(getApplicationContext(),imei);
                new checkKYC().execute("","","");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void parseData6(String response6) {
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(response6);
            String Status=jsonObject.getString("status");
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                adhaar = jsonObject1.getString("adhaar");
                lic = jsonObject1.getString("lic");
                passport = jsonObject1.getString("passport");

                if (Status.equals("1")) {

                    if (adhaar.equals("1") && lic.equals("1") && passport.equals("1")) {

                        switch (status) {
                            case "2": {
                                // save offline data
                                new SaveFlights(getApplicationContext(),flightsDatabase).execute();
                                new SaveHotels(getApplicationContext(),hotelsDatabase).execute();
                                new SaveIti(getApplicationContext(),itiDatabase).execute();
                                new SaveIntpack(getApplicationContext(),intDatabase).execute();

                                Intent intent = new Intent(getApplicationContext(), Trip.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.enter,R.anim.exit);
                                finish();
                                break;
                            }
                            case "3": {
                                // save offline data
                                new SaveFlights(getApplicationContext(),flightsDatabase).execute();
                                new SaveHotels(getApplicationContext(),hotelsDatabase).execute();
                                new SaveIti(getApplicationContext(),itiDatabase).execute();
                                new SaveIntpack(getApplicationContext(),intDatabase).execute();

                                Intent intent = new Intent(getApplicationContext(), MyProfile.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.enter,R.anim.exit);
                                finish();
                                break;
                            }
                            case "4": {
                                // collect profile data
                                CollectMetaData();
                                break;
                            }
                            case "5": {
                                KYCdoc.setVisibility(View.VISIBLE);
                                kycPopupMessage.setVisibility(View.VISIBLE);
                                PresistenceData.kycDocs(getApplicationContext(), adhaar, lic, passport);
                                pd.dismiss();
                                // close on close button
                                setupSpinner();
                            }
                            case "0": {
                                // save offline data
                                new SaveFlights(getApplicationContext(),flightsDatabase).execute();
                                new SaveHotels(getApplicationContext(),hotelsDatabase).execute();
                                new SaveIti(getApplicationContext(),itiDatabase).execute();
                                new SaveIntpack(getApplicationContext(),intDatabase).execute();

                                Intent intent = new Intent(getApplicationContext(), Trip.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.enter,R.anim.exit);
                                finish();
                                break;
                            }
                        }

                    } else {

                        KYCdoc.setVisibility(View.VISIBLE);
                        kycPopupMessage.setVisibility(View.VISIBLE);
                        PresistenceData.kycDocs(getApplicationContext(), adhaar, lic, passport);
                        pd.dismiss();
                        // close on close button
                        setupSpinner();
                    }
                } else {

                    PresistenceData.kycDocs(getApplicationContext(), adhaar, lic, passport);
                    KYCdoc.setVisibility(View.VISIBLE);
                    kycPopupMessage.setVisibility(View.VISIBLE);
                    setupSpinner();
                    KYCdoc.setVisibility(View.VISIBLE);


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
                Toast.makeText(RegisterMe.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

                    // save offline data
                    new SaveFlights(getApplicationContext(),flightsDatabase).execute();
                    new SaveHotels(getApplicationContext(),hotelsDatabase).execute();
                    new SaveIti(getApplicationContext(),itiDatabase).execute();
                    new SaveIntpack(getApplicationContext(),intDatabase).execute();

                    Intent intent=new Intent(RegisterMe.this, Community.class);
                        startActivity(intent);
                    overridePendingTransition(R.anim.enter,R.anim.exit);
                        finish();
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

    // setup spinner property
    private void setupSpinner() {
        // set adapter in spinner
        final String[] list = { "Select here","Adhaar Card", "Licence", "Passport"};
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,R.layout.spinner_list,list);
        mainTitle.setVisibility(View.GONE);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        loginLay.setVisibility(View.GONE);
        KYCdoc.setVisibility(View.VISIBLE);
        kycPopupMessage.setVisibility(View.VISIBLE);
        titleName.setText("Upload KYC Docs");

        // click on spinner
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              if(String.valueOf(position).equals("0"))
              {

              }
              else if(String.valueOf(position).equals("1"))
              {

                  if(GetPresistenceData.getAdhar(getApplicationContext()).equals("1"))
                  {
                      Toast.makeText(RegisterMe.this,"You have already uploaded adhar card", Toast.LENGTH_SHORT).show();
                  }
                  else
                  {
                      // do for adhar card
                      PresistenceData.saveDocType(getApplicationContext(),"1");
                      showPictureDialog();
                  }
              }
              else if(String.valueOf(position).equals("2"))
              {
                  if(GetPresistenceData.getLic(getApplicationContext()).equals("1"))
                  {
                      Toast.makeText(RegisterMe.this,"You have already uploaded Licence", Toast.LENGTH_SHORT).show();
                  }
                  else
                  {
                      PresistenceData.saveDocType(getApplicationContext(), "2");
                      // do for Licence
                      showPictureDialog();
                  }
              }
              else if(String.valueOf(position).equals("3"))
              {
                  if(GetPresistenceData.getPassport(getApplicationContext()).equals("1"))
                  {
                      Toast.makeText(RegisterMe.this,"You have already uploaded Passport docs", Toast.LENGTH_SHORT).show();
                  }
                  else
                  {
                      // do for passport
                          PresistenceData.saveDocType(getApplicationContext(), "3");
                          showPictureDialog();
                  }
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Open Camera",
                "Gallery"};
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
                                dataFromLib();
                                pd.show();
                                pd.setMessage("Uploading...");
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void captureimagefromcamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }
    private void dataFromLib() {
        //method to show file chooser
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA && data != null) {
//imgage captured
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageToString(bitmap);
            //capture_image.setImageBitmap(bitmap);
        }
        else if (requestCode == GALLERY && data != null && data.getData() != null ) {
            if (resultCode == RESULT_OK) {

                Uri contentURI = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageToString(bitmap);
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
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        image = Base64.encodeToString(imgbytes, Base64.DEFAULT);

        uploadtoserver(image);
    }
    private void uploadtoserver(final String image) {
        queue = Volley.newRequestQueue((Objects.requireNonNull(getApplicationContext())));
        StringRequest request = new StringRequest(Request.Method.POST, Config.save_kyc, new Response.Listener<String>() {
            @Override
            public void onResponse(String response7) {
                parseData7(response7);
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
                mydata.put("imei",GetPresistenceData.getMyIMEI(getApplicationContext()));
                mydata.put("email",GetPresistenceData.getEmail(getApplicationContext()));
                mydata.put("type",GetPresistenceData.getkycTy(getApplicationContext()));
                mydata.put("doc",image);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData7(String response7) {
        JSONObject jsonObject= null;
        try {
            pd.dismiss();
            jsonObject = new JSONObject(response7);
            String Status=jsonObject.getString("status");
            if(Status.equals("1"))
            {

                PresistenceData.kycDocs(getApplicationContext(),adhaar,lic,passport);
                // get kycdoctype 7 all status of data
                String ty=GetPresistenceData.getkycTy(getApplicationContext());
                if(ty.equals("1")) {
                    PresistenceData.kycDocs(getApplicationContext(),"1",lic,passport);
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Upload Successfully", Snackbar.LENGTH_SHORT).show();
                    KYCdoc.setVisibility(View.GONE);
                    kycPopupMessage.setVisibility(View.GONE);
                    messageLay.setVisibility(View.VISIBLE);
                    messageTxt.setText("Upload Successfully");
                    messageSubTxt.setText("Wait a movement");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            messageLay.setVisibility(View.GONE);
                            KYCdoc.setVisibility(View.VISIBLE);
                            kycPopupMessage.setVisibility(View.VISIBLE);

                        }
                    }, 2000);
                }
                else if(ty.equals("2")) {
                    PresistenceData.kycDocs(getApplicationContext(),adhaar,"1",passport);
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Upload Successfully", Snackbar.LENGTH_SHORT).show();
                    KYCdoc.setVisibility(View.GONE);
                    kycPopupMessage.setVisibility(View.GONE);
                    messageLay.setVisibility(View.VISIBLE);
                    messageTxt.setText("Upload Successfully");
                    messageSubTxt.setText("Wait a movement");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            messageLay.setVisibility(View.GONE);
                            KYCdoc.setVisibility(View.VISIBLE);
                            kycPopupMessage.setVisibility(View.VISIBLE);

                        }
                    }, 2000);
                }
                else if(ty.equals("3")) {
                    PresistenceData.kycDocs(getApplicationContext(),adhaar,lic,"1");
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Upload Successfully", Snackbar.LENGTH_SHORT).show();
                    KYCdoc.setVisibility(View.GONE);
                    kycPopupMessage.setVisibility(View.GONE);
                    messageLay.setVisibility(View.VISIBLE);
                    messageTxt.setText("Upload Successfully");
                    messageSubTxt.setText("Wait a movement");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            messageLay.setVisibility(View.GONE);
                            KYCdoc.setVisibility(View.VISIBLE);
                            kycPopupMessage.setVisibility(View.VISIBLE);

                        }
                    }, 2000);
                }

            }
            else
            {
                Snackbar.make(getWindow().getDecorView().getRootView(),"Upload Failed",Snackbar.LENGTH_SHORT).show();
                KYCdoc.setVisibility(View.GONE);
                messageLay.setVisibility(View.VISIBLE);
                messageTxt.setText("Upload Failed !!");
                messageSubTxt.setText("Wait a movement");
                Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messageLay.setVisibility(View.GONE);
                        KYCdoc.setVisibility(View.VISIBLE);
                        kycPopupMessage.setVisibility(View.VISIBLE);

                    }
                },2000);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private void SaveRegistrationForm(final String reg_name2, final String reg_phone2, final String reg_email2, final String reg_pass2) {
        queue = Volley.newRequestQueue((Objects.requireNonNull(getApplicationContext())));
        StringRequest request = new StringRequest(Request.Method.POST, Config.Save_reg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                parseData1(response1);
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
                mydata.put("name",reg_name2);
                mydata.put("mobb",reg_phone2);
                mydata.put("email",reg_email2);
                mydata.put("pass",reg_pass2);
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
            String imei = jsonObject.getString("imei");
            switch (Status) {
                case "0":
                    // failed
                    pd.dismiss();
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Network Issue", Snackbar.LENGTH_SHORT).show();
                    regLay.setVisibility(View.GONE);
                    messageLay.setVisibility(View.VISIBLE);
                    messageTxt.setText("Server Error !!!");
                    messageSubTxt.setText("Try to Register again");
                    break;
                case "1": // success
                    pd.dismiss();
                    // save into presistence storage
                    new SaveDomPack(getApplicationContext(),domPacks).execute();
                    new SaveIntpack(getApplicationContext(),intDatabase).execute();

                    PresistenceData.saveIMEI(getApplicationContext(), imei);
                    PresistenceData.checkLogon(getApplicationContext(), "1");
                    PresistenceData.checkRegistration(getApplicationContext(),"1");
                    PresistenceData.profileData(getApplicationContext(), reg_email2, reg_pass2);
                    regLay.setVisibility(View.GONE);
                    KYCdoc.setVisibility(View.VISIBLE);
                    kycPopupMessage.setVisibility(View.VISIBLE);
                    break;
                case "2":
                    // already exist
                    pd.dismiss();
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Password not matching", Snackbar.LENGTH_SHORT).show();
                    regLay.setVisibility(View.GONE);
                    messageLay.setVisibility(View.VISIBLE);
                    messageTxt.setText("Already Exist");
                    messageSubTxt.setText("Go back and login with registered email id");
                    break;
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
            if(status.equals("5"))
            {
                Intent back1Intent=new Intent(RegisterMe.this,MyProfile.class);
                startActivity(back1Intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
                finish();
            }
            else
            {
                Intent back1Intent=new Intent(RegisterMe.this,MainActivity.class);
                startActivity(back1Intent);
                overridePendingTransition(R.anim.enter,R.anim.exit);
                finish();
            }

        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(queue !=null)
        {
            queue.cancelAll(REQUEST_TAG);
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class checkKYC extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            RequestQueue queue = Volley.newRequestQueue((Objects.requireNonNull(getApplicationContext())));
            StringRequest request = new StringRequest(Request.Method.POST, Config.check_docs_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response6) {
                    parseData6(response6);
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
                    mydata.put("imei",GetPresistenceData.getMyIMEI(getApplicationContext()));
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
}
