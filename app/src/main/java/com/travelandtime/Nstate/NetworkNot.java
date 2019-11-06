package com.travelandtime.Nstate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.travelandtime.MainActivity;
import com.travelandtime.R;
import com.travelandtime.Trip;
import com.travelandtime.Utils.AppStatus;

public class NetworkNot extends AppCompatActivity {

    private Button itinary;
    private TextView hindiTxt;
    private TextView engTxt;
    private LinearLayout mainLay;
    private ImageView Image;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonetwork);

        itinary = (Button) findViewById(R.id.itinary);
        hindiTxt = (TextView) findViewById(R.id.hindi_txt);
        engTxt = (TextView) findViewById(R.id.eng_txt);
        mainLay = (LinearLayout) findViewById(R.id.mainLay);
        Image = (ImageView) findViewById(R.id.Image);

        repeat();

        // set Parameters
        Glide.with(this).load(R.drawable.nonetwork).into(Image);
        hindiTxt.setText("प्रिय उपभोक्ता, कृपया हमे आपका नेटवर्क बंद प्राप्त हो रहा है | बिना नेटवर्क के हम आपको आपके कीमती विकल्प खोलने में आपके साथ है | इस समय आप केवल दो विकल्प का चुनाव कर सकते है , बहुविकल्प का चुनाव करने के लिए कृपया अपने नेटवर्क को चालू करे |");
        engTxt.setText("Dear user, we are receiving your network off, without the network we are with you to open your precious options, at this time you can choose only two options, to choose multiple options: Let your network to be fixed.");

        // click on main menu
     /*   Mainmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NetworkNot.this, MainActivity.class);
                startActivity(intent);

            }
        });*/
        // click for Itninary
        itinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NetworkNot.this, Trip.class);
                startActivity(intent);

            }
        });




    } //onCreate closer

    private void repeat() {
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (AppStatus.getInstance(NetworkNot.this).isOnline()) {
                    Intent intent=new Intent(NetworkNot.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    repeat();
                }
            }
        },4000);
    }
}
