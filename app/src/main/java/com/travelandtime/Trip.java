package com.travelandtime;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
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
import com.travelandtime.Configration.Config;
import com.travelandtime.Fragments.PagerAdapter;
import com.travelandtime.Fragments.Tab1;
import com.travelandtime.Fragments.Tab2;
import com.travelandtime.Fragments.Tab3;
import com.travelandtime.Social.BigPic;
import com.travelandtime.Utils.GetPresistenceData;

import java.util.HashMap;
import java.util.Map;


public class Trip extends AppCompatActivity implements Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener {
    private TabLayout tabLay;
    private   ViewPager viewPager;
    private ImageView backClick;
    RequestQueue queue;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_lay);
        tabLay = (TabLayout) findViewById(R.id.tabLay);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        backClick = (ImageView) findViewById(R.id.backClick);


        //setup fragment
        //// first make Adapeter class and then set
        final PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),tabLay.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLay));
        tabLay.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
// back click event
        backClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Trip.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    } // onCreate closer

    // back key pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {
            Intent back1Intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(back1Intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
