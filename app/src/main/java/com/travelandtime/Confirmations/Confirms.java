package com.travelandtime.Confirmations;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.travelandtime.Configration.Config;
import com.travelandtime.MyProfile;
import com.travelandtime.R;
import com.travelandtime.Roulette.Htmltoword;
import com.travelandtime.Roulette.RouletteGame;
import com.travelandtime.Roulette.RoulleteSplash;
import com.travelandtime.Roulette.UrlShortner;

public class Confirms extends AppCompatActivity {
    String page, lay;
    private LinearLayout callLay,contactUsLay,privacyLay,minigamesLay,aboutusLay,rouletteInfo,linkShortnerLay;
    private Button callUs,cancel,roulttePlay,urlshortnerBtn;
    private TextView help,myLinkAnalytics,urlShortnerHelp,title;
    private Button htmltoword;
    private TextView htmltowordHelp;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirms);
        callLay = (LinearLayout) findViewById(R.id.callLay);
        callUs = (Button) findViewById(R.id.callUs);
        cancel = (Button) findViewById(R.id.cancel);
        aboutusLay = (LinearLayout) findViewById(R.id.aboutusLay);
        title = (TextView) findViewById(R.id.title);
        contactUsLay = (LinearLayout) findViewById(R.id.contact_usLay);
        privacyLay = (LinearLayout) findViewById(R.id.privacy_lay);
        minigamesLay = (LinearLayout) findViewById(R.id.minigames_lay);
        roulttePlay = (Button) findViewById(R.id.roultte_play);
        urlshortnerBtn = (Button) findViewById(R.id.urlshortner_btn);
        rouletteInfo = (LinearLayout) findViewById(R.id.roulette_info);
        help = (TextView) findViewById(R.id.help);
        myLinkAnalytics = (TextView) findViewById(R.id.my_link_analytics);
        linkShortnerLay = (LinearLayout) findViewById(R.id.link_shortner_lay);
        urlShortnerHelp = (TextView) findViewById(R.id.url_shortner_help);
        htmltoword = (Button) findViewById(R.id.htmltoword);
        htmltowordHelp = (TextView) findViewById(R.id.htmltoword_help);

        // get Intent from other pages
        final Intent intent = getIntent();
        page = intent.getStringExtra("page");
        lay = intent.getStringExtra("lay");


        // set visible layout parameters
        if (lay.equals("Call")) {
            title.setText("CONFIRMATION");
            callLay.setVisibility(View.VISIBLE);

        } else if (lay.equals("Aboutus")) {
            title.setText("ABOUT US");
            aboutusLay.setVisibility(View.VISIBLE);
        } else if (lay.equals("Contactus")) {
            title.setText("Contact us");
            contactUsLay.setVisibility(View.VISIBLE);
        } else if (lay.equals("Privacy")) {
            title.setText("Privacy Policy");
            privacyLay.setVisibility(View.VISIBLE);
        }
        else if (lay.equals("Minigames")) {
            title.setText("SELECT");
            minigamesLay.setVisibility(View.VISIBLE);
        }
        else if (lay.equals("RouletteGame")) {
            title.setText("Learn about Roulette Game");
            rouletteInfo.setVisibility(View.VISIBLE);
        }

        // click on htmltoword converter button
        htmltoword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Confirms.this, Htmltoword.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        });

// click on url shortner link help
        urlShortnerHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minigamesLay.setVisibility(View.GONE);
                title.setVisibility(View.GONE);
                linkShortnerLay.setVisibility(View.VISIBLE);
            }
        });
        // click on help
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minigamesLay.setVisibility(View.GONE);
                title.setText("Learn about Roulette Game");
                rouletteInfo.setVisibility(View.VISIBLE);
            }
        });
        // click on roulette play btn
        roulttePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Confirms.this, RoulleteSplash.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        });
        // click on Link shortner links state
        myLinkAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Confirms.this, UrlShortner.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        });
        // click on Link shortner
        urlshortnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(Confirms.this, UrlShortner.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        });
        // connect call
        callUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String mobData = Config.mobileNo;
                callIntent.setData(Uri.parse("tel:" + mobData));//change the number
                if (ActivityCompat.checkSelfPermission(Confirms.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(page.equals("MyProfile")) {
                    Intent back1Intent = new Intent(Confirms.this, MyProfile.class);
                    startActivity(back1Intent);
                    back1Intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    finish();
                }
            }
        });



    }//onCreate closer

    // back key pressed
  /*  @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {
            if(page.equals("MyProfile")) {
                Intent back1Intent = new Intent(Confirms.this, MyProfile.class);
                startActivity(back1Intent);
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }*/
}
