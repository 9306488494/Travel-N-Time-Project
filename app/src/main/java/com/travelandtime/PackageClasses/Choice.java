package com.travelandtime.PackageClasses;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.travelandtime.MainActivity;
import com.travelandtime.R;
import com.travelandtime.Social.BigPic;
import com.travelandtime.Utils.GetPresistenceData;


@SuppressLint("Registered")
public class Choice extends AppCompatActivity {
    private LinearLayout packagesChoices;
    private Button domesticPack;
    private Button interPacks;
    String option;
    private LinearLayout groupChoice;
    private Button myGroup;
    private Button newGroups;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_lay);

        packagesChoices = (LinearLayout) findViewById(R.id.packages_choices);
        domesticPack = (Button) findViewById(R.id.domesticPack);
        interPacks = (Button) findViewById(R.id.interPacks);
        groupChoice = (LinearLayout) findViewById(R.id.group_choice);
        myGroup = (Button) findViewById(R.id.myGroup);
        newGroups = (Button) findViewById(R.id.newGroups);

        // recieve intents
        final Intent intent=getIntent();
        option=intent.getStringExtra("Option");

        // get condition to visible layouts
        // value: choice for package
        // value 2 for groups
        if(option.equals("Choice"))
        {
            packagesChoices.setVisibility(View.VISIBLE);
        }
        else if(option.equals("2"))
        {
            groupChoice.setVisibility(View.VISIBLE);
        }
        // click on Domestic button to open domestic packages
        domesticPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), International.class);
                intent.putExtra("pack_type","Domestic");
                startActivity(intent);
            }
        });

        // click on Domestic button to open domestic packages
        interPacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), International.class);
                intent.putExtra("pack_type","International");
                startActivity(intent);
            }
        });

        // Layout 2
        // click on Joined groups
        myGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Choice.this, BigPic.class);
                intent1.putExtra("image","");
                intent1.putExtra("type","6");
                intent1.putExtra("text", GetPresistenceData.getMyIMEI(getApplicationContext()));
                startActivity(intent1);
                finish();
            }
        });
        // click on upcoming public groups
        newGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Choice.this, BigPic.class);
                intent1.putExtra("image","");
                intent1.putExtra("type","7");
                intent1.putExtra("text", GetPresistenceData.getMyIMEI(getApplicationContext()));
                startActivity(intent1);
                finish();
            }
        });


    } //oncreate closer

    // back key pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {
            Intent back1Intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(back1Intent);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
