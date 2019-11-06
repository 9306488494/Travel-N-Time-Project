package com.travelandtime;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import com.travelandtime.Utils.GetPresistenceData;


public class Flights extends AppCompatActivity {
    private TextView title,travelerText,returnTravellerText;
    private LinearLayout optionsLay,multiLay1,multiLay2;
    private Button oneWayBtn,AirCalender,multiCity,returnBtn,onewaySearchBtn;
    private CardView onewayLay,multicityLay1;
    private EditText onewayFrom,onewayGoto,onewayDeptDate;
    private Spinner onewayAdult,onewayChildren,onewayInfants,onewayClass;
    private CardView returnLay;
    private EditText returnFrom,returnGoto,returnDeptDate,returnReturnDate;
    private Spinner returnAdult,returnChildren,returnInfants,returnClass;
    private Button returnSearchBtn;
    private EditText multiFrom1,multicityGoto1,multicityDeptDate1;
    private TextView multiCityTravellerText;
    private Spinner multicityAdult1,multicityChildren1,multicityInfants1,multicityClass1;
    private Button multicityPlus1,multicityMinus1;
    private EditText multiFrom2,multicityGoto2,multicityDeptDate2;
    private TextView multiCityTravellerText2;
    private Spinner multicityAdult2,multicityChildren2,multicityInfants2,multicityClass2;
    private Button multicityPlus2,multicityMinus2,AirSearchBtn;
    private LinearLayout multiLay4;
    private EditText multiFrom4,multicityGoto4,multicityDeptDate4;
    private TextView multiCityTravellerText4;
    private Spinner multicityAdult4,multicityChildren4,multicityInfants4,multicityClass4;
    private Button multicityPlus4,multicityMinus4;
    private LinearLayout multiLay3;
    private EditText multiFrom3,multicityGoto3,multicityDeptDate3;
    private TextView multiCityTravellerText3;
    private Spinner multicityAdult3,multicityChildren3,multicityInfants3,multicityClass3;
    private Button multicityPlus3,multicityMinus3,multicitySearchBtn;
    private CardView AirLay;
    private EditText AirFrom,AirGoto;
    private Spinner AirMonth,AirYear;
    int count=1;
    String class1="",class2="",class3="",class4="";
    ArrayAdapter adult,child,infants,classes,Months,years;
    String One_Adult,Return_Adult,M_Adult1,M_Adult2,M_Adult3,M_Adult4;
    String One_child,Return_child,M_child1,M_child2,M_child3,M_child4;
    String One_infant,R_infant,M_infant1,M_infant2,M_infant3,M_infant4;
    String One_class,R_class,M_class1,M_class2,M_class3,M_class4;
    String A_Year,A_Month;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flights);
        title = (TextView) findViewById(R.id.title);
        optionsLay = (LinearLayout) findViewById(R.id.options_lay);
        oneWayBtn = (Button) findViewById(R.id.one_way_btn);
        returnBtn = (Button) findViewById(R.id.return_btn);
        multiCity = (Button) findViewById(R.id.multi_city);
        AirCalender = (Button) findViewById(R.id.Air_calender);
        onewayLay = (CardView) findViewById(R.id.oneway_lay);
        onewayFrom = (EditText) findViewById(R.id.oneway_from);
        onewayGoto = (EditText) findViewById(R.id.oneway_goto);
        onewayDeptDate = (EditText) findViewById(R.id.oneway_deptDate);
        travelerText = (TextView) findViewById(R.id.traveler_text);
        onewayAdult = (Spinner) findViewById(R.id.oneway_adult);
        onewayChildren = (Spinner) findViewById(R.id.oneway_children);
        onewayInfants = (Spinner) findViewById(R.id.oneway_infants);
        onewayClass = (Spinner) findViewById(R.id.oneway_class);
        onewaySearchBtn = (Button) findViewById(R.id.oneway_searchBtn);
        returnLay = (CardView) findViewById(R.id.return_lay);
        returnFrom = (EditText) findViewById(R.id.return_from);
        returnGoto = (EditText) findViewById(R.id.return_goto);
        returnDeptDate = (EditText) findViewById(R.id.return_deptDate);
        returnReturnDate = (EditText) findViewById(R.id.return_returnDate);
        returnTravellerText = (TextView) findViewById(R.id.return_Traveller_text);
        returnAdult = (Spinner) findViewById(R.id.return_adult);
        returnChildren = (Spinner) findViewById(R.id.return_children);
        returnInfants = (Spinner) findViewById(R.id.return_infants);
        returnClass = (Spinner) findViewById(R.id.return_class);
        returnSearchBtn = (Button) findViewById(R.id.return_searchBtn);
        multicityLay1 = (CardView) findViewById(R.id.multicity_lay1);
        multiLay1 = (LinearLayout) findViewById(R.id.multi_lay1);
        multiFrom1 = (EditText) findViewById(R.id.multi_from1);
        multicityGoto1 = (EditText) findViewById(R.id.multicity_goto1);
        multicityDeptDate1 = (EditText) findViewById(R.id.multicity_deptDate1);
        multiCityTravellerText = (TextView) findViewById(R.id.multi_city_Traveller_text);
        multicityAdult1 = (Spinner) findViewById(R.id.multicity_adult1);
        multicityChildren1 = (Spinner) findViewById(R.id.multicity_children1);
        multicityInfants1 = (Spinner) findViewById(R.id.multicity_infants1);
        multicityClass1 = (Spinner) findViewById(R.id.multicity_class1);
        multicityPlus1 = (Button) findViewById(R.id.multicity_plus1);
        multicityMinus1 = (Button) findViewById(R.id.multicity_minus1);
        multiLay2 = (LinearLayout) findViewById(R.id.multi_lay2);
        multiFrom2 = (EditText) findViewById(R.id.multi_from2);
        multicityGoto2 = (EditText) findViewById(R.id.multicity_goto2);
        multicityDeptDate2 = (EditText) findViewById(R.id.multicity_deptDate2);
        multiCityTravellerText2 = (TextView) findViewById(R.id.multi_city_Traveller_text2);
        multicityAdult2 = (Spinner) findViewById(R.id.multicity_adult2);
        multicityChildren2 = (Spinner) findViewById(R.id.multicity_children2);
        multicityInfants2 = (Spinner) findViewById(R.id.multicity_infants2);
        multicityClass2 = (Spinner) findViewById(R.id.multicity_class2);
        multicityPlus2 = (Button) findViewById(R.id.multicity_plus2);
        multicityMinus2 = (Button) findViewById(R.id.multicity_minus2);
        multiLay4 = (LinearLayout) findViewById(R.id.multi_lay4);
        multiFrom4 = (EditText) findViewById(R.id.multi_from4);
        multicityGoto4 = (EditText) findViewById(R.id.multicity_goto4);
        multicityDeptDate4 = (EditText) findViewById(R.id.multicity_deptDate4);
        multiCityTravellerText4 = (TextView) findViewById(R.id.multi_city_Traveller_text4);
        multicityAdult4 = (Spinner) findViewById(R.id.multicity_adult4);
        multicityChildren4 = (Spinner) findViewById(R.id.multicity_children4);
        multicityInfants4 = (Spinner) findViewById(R.id.multicity_infants4);
        multicityClass4 = (Spinner) findViewById(R.id.multicity_class4);
        multicityPlus4 = (Button) findViewById(R.id.multicity_plus4);
        multicityMinus4 = (Button) findViewById(R.id.multicity_minus4);
        multiLay3 = (LinearLayout) findViewById(R.id.multi_lay3);
        multiFrom3 = (EditText) findViewById(R.id.multi_from3);
        multicityGoto3 = (EditText) findViewById(R.id.multicity_goto3);
        multicityDeptDate3 = (EditText) findViewById(R.id.multicity_deptDate3);
        multiCityTravellerText3 = (TextView) findViewById(R.id.multi_city_Traveller_text3);
        multicityAdult3 = (Spinner) findViewById(R.id.multicity_adult3);
        multicityChildren3 = (Spinner) findViewById(R.id.multicity_children3);
        multicityInfants3 = (Spinner) findViewById(R.id.multicity_infants3);
        multicityClass3 = (Spinner) findViewById(R.id.multicity_class3);
        multicityPlus3 = (Button) findViewById(R.id.multicity_plus3);
        multicityMinus3 = (Button) findViewById(R.id.multicity_minus3);
        multicitySearchBtn = (Button) findViewById(R.id.multicity_searchBtn);
        AirLay = (CardView) findViewById(R.id.Air_lay);
        AirFrom = (EditText) findViewById(R.id.Air_from);
        AirGoto = (EditText) findViewById(R.id.Air_goto);
        AirMonth = (Spinner) findViewById(R.id.Air_Month);
        AirYear = (Spinner) findViewById(R.id.Air_Year);
        AirSearchBtn = (Button) findViewById(R.id.Air_searchBtn);

        // set title text
        title.setText("Flights");

        // set Spinners with values
        setupSpinners();

        //one way button click
        oneWayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide & Visible the layouts
            onewayLay.setVisibility(View.VISIBLE);
            returnLay.setVisibility(View.GONE);
            multicityLay1.setVisibility(View.GONE);
            AirLay.setVisibility(View.GONE);




            }
        });

        // Return flight click
        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide & Visible the layouts
                onewayLay.setVisibility(View.GONE);
                returnLay.setVisibility(View.VISIBLE);
                multicityLay1.setVisibility(View.GONE);
                AirLay.setVisibility(View.GONE);

            }
        });
        // multicity button click
        multiCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide & Visible the layouts
                onewayLay.setVisibility(View.GONE);
                returnLay.setVisibility(View.GONE);
                multicityLay1.setVisibility(View.VISIBLE);
                AirLay.setVisibility(View.GONE);
                multicityMinus1.setVisibility(View.GONE);

            }
        });
        // Aircalender on click listner
        AirCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide & Visible the layouts
                onewayLay.setVisibility(View.GONE);
                returnLay.setVisibility(View.GONE);
                multicityLay1.setVisibility(View.GONE);
                AirLay.setVisibility(View.VISIBLE);
            }
        });
        // show multiple layout
        multicityPlus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(class1.equals(""))
                {
                    Toast.makeText(Flights.this, "Please fill above form", Toast.LENGTH_SHORT).show();
                    multicityAdult1.requestFocus();
                }
                else
                {
                    count=count+1;
                    multiLay2.setVisibility(View.VISIBLE);
                    multicityPlus1.setVisibility(View.GONE);
                }
            }
        });
        // show multi2 layout
        multicityPlus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(class2.equals(""))
                {
                    Toast.makeText(Flights.this, "Please fill above form", Toast.LENGTH_SHORT).show();
                    multicityAdult2.requestFocus();
                }
                else
                {
                    count=count+1;
                    multiLay3.setVisibility(View.VISIBLE);
                    multicityPlus2.setVisibility(View.GONE);
                }
            }
        });
        // show multi3 layout
        multicityPlus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(class3.equals(""))
                {

                    Toast.makeText(Flights.this, "Please fill above form", Toast.LENGTH_SHORT).show();
                    multicityAdult3.requestFocus();
                }
                else
                {
                    count=count+1;
                    multiLay4.setVisibility(View.VISIBLE);
                    multicityPlus3.setVisibility(View.GONE);
                    multicityPlus4.setVisibility(View.GONE);
                }
            }
        });
// Hide multi4 layout
        multicityMinus4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=count-1;
                    multiLay4.setVisibility(View.GONE);
            }
        });
        // Hide multi3 layout
        multicityMinus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=count-1;
                multiLay3.setVisibility(View.GONE);
            }
        });
        // Hide multi3 layout
        multicityMinus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count=count-1;
                multiLay2.setVisibility(View.GONE);
            }
        });


        // search buttons click listner
        onewaySearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do for one way search query
            }
        });
        // search buttons click listner for return
        returnSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do for return search query
            }
        });
        // search buttons click listner for multicity
        multicitySearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do for multicity search query
            }
        });
        // search buttons click listner for Air
        AirSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do for Air search query
            }
        });

        // click on One way adults spinner
        onewayAdult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    One_Adult=String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position));
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // click on return adults spinner
        returnAdult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    Return_Adult=String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position));
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // click on return adults spinner
        multicityAdult1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_Adult1=String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position));
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // click on return adults spinner
        multicityAdult2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_Adult2=String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position));
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // click on return adults spinner
        multicityAdult3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_Adult3=String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // click on return adults spinner
        multicityAdult4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_Adult4=String.valueOf(GetPresistenceData.getAdultsPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

       // click on children spinner and got value
        onewayChildren.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    One_child=String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position));
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        returnChildren.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    Return_child=String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position));
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        multicityChildren1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_child1=String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityChildren2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_child2=String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityChildren3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_child3=String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityChildren4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_child4=String.valueOf(GetPresistenceData.getChildPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // click on infants spinner and got value
        onewayInfants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    One_infant=String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        returnInfants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    R_infant=String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityInfants1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_infant1=String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityInfants2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_infant2=String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityInfants3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_infant3=String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityInfants4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_infant4=String.valueOf(GetPresistenceData.getInfantsPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // click on class spinner to get values
        onewayClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    One_class=String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        returnClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    R_class=String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityClass1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_class1=String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityClass2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_class2=String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityClass3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_class3=String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        multicityClass4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    M_class4=String.valueOf(GetPresistenceData.getClassesPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // click on Month Spinner
        AirMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getMonthPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    A_Month=String.valueOf(GetPresistenceData.getMonthPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        AirYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(String.valueOf(GetPresistenceData.getYearsPos(getApplicationContext(),position)).equals("Choose")) {
                }
                else
                {
                    A_Year=String.valueOf(GetPresistenceData.getYearsPos(getApplicationContext(),position));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



    } //onCreate closer

    private void setupSpinners() {
        adult = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, GetPresistenceData.getAdults(getApplicationContext()));
        adult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        child = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, GetPresistenceData.getChild(getApplicationContext()));
        child.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        infants = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, GetPresistenceData.getInfants(getApplicationContext()));
        infants.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classes = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, GetPresistenceData.getClasses(getApplicationContext()));
        classes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Months = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, GetPresistenceData.getMonth(getApplicationContext()));
        Months.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        years = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, GetPresistenceData.getYears(getApplicationContext()));
        years.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        onewayAdult.setAdapter(adult);
        returnAdult.setAdapter(adult);
        multicityAdult1.setAdapter(adult);
        multicityAdult2.setAdapter(adult);
        multicityAdult3.setAdapter(adult);
        multicityAdult4.setAdapter(adult);

        onewayChildren.setAdapter(child);
        returnChildren.setAdapter(child);
        multicityChildren1.setAdapter(child);
        multicityChildren2.setAdapter(child);
        multicityChildren3.setAdapter(child);
        multicityChildren3.setAdapter(child);

        onewayInfants.setAdapter(infants);
        returnInfants.setAdapter(infants);
        multicityInfants1.setAdapter(infants);
        multicityInfants2.setAdapter(infants);
        multicityInfants3.setAdapter(infants);
        multicityInfants4.setAdapter(infants);

        onewayClass.setAdapter(classes);
        returnClass.setAdapter(classes);
        multicityClass1.setAdapter(classes);
        multicityClass2.setAdapter(classes);
        multicityClass3.setAdapter(classes);
        multicityClass4.setAdapter(classes);

        AirMonth.setAdapter(Months);
        AirYear.setAdapter(years);


    }

    // onBack key pressed
    // back key pressed
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode==KeyEvent.KEYCODE_BACK))
        {
            Intent back1Intent=new Intent(Flights.this,MainActivity.class);
            startActivity(back1Intent);
            finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
