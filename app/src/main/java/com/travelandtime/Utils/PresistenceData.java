package com.travelandtime.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PresistenceData {

    public static void saveIMEI(Context mcontext, String IMEI) {

        SharedPreferences.Editor editor = mcontext.getSharedPreferences("myimei", MODE_PRIVATE).edit();
        editor.putString("imei", IMEI);
        editor.apply();
    }

    // check registration state, registered or not
    public static void checkRegistration(Context mcontext, String res) {

        SharedPreferences.Editor editor = mcontext.getSharedPreferences("registration", MODE_PRIVATE).edit();
        editor.putString("response", res);
        editor.apply();
    }

    // check user is login or not
    public static void checkLogon(Context mcontext, String res) {

        SharedPreferences.Editor editor = mcontext.getSharedPreferences("logon", MODE_PRIVATE).edit();
        editor.putString("status", res);
        editor.apply();
    }
    // check user is login or not
    public static void profileData(Context mcontext, String email,String pass) {

        SharedPreferences.Editor editor = mcontext.getSharedPreferences("profile", MODE_PRIVATE).edit();
        editor.putString("email", email);
        editor.putString("pass", pass);
        editor.apply();
    }

    // check user is login or not
    public static void kycDocs(Context mcontext, String adhaar,String lic,String passport) {

        SharedPreferences.Editor editor = mcontext.getSharedPreferences("KYCDocs", MODE_PRIVATE).edit();
        editor.putString("adhaar", adhaar);
        editor.putString("lic", lic);
        editor.putString("passport", passport);
        editor.apply();
    }

    // check user is login or not
    public static void saveDocType(Context mcontext, String ty) {

        SharedPreferences.Editor editor = mcontext.getSharedPreferences("KycDocType", MODE_PRIVATE).edit();
        editor.putString("ty", ty);
        editor.apply();
    }

    // collect meta data
    public static void metaData(Context mcontext,String name, String mobb, String state, String email, String my_pic) {

        SharedPreferences.Editor editor = mcontext.getSharedPreferences("Metadata", MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.putString("mobb", mobb);
        editor.putString("state", state);
        editor.putString("email", email);
        editor.putString("my_pic", my_pic);
        editor.apply();
    }

    // check status for collect meta data if 1 then data is set otherwise not set
    public static void metaStatus(Context mcontext,String status) {

        SharedPreferences.Editor editor = mcontext.getSharedPreferences("MetaStatus", MODE_PRIVATE).edit();
        editor.putString("status", status);
        editor.apply();
    }

    // trip flights
    public static void tripFlights(Context mcontext,String response) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("tripflights", MODE_PRIVATE).edit();
        editor.putString("response", response);
        editor.apply();
    }

    // flights first time login
    public static void FlightsLogin(Context mcontext,String response) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("flightLogin", MODE_PRIVATE).edit();
        editor.putString("res", response);
        editor.apply();
    }

    // Hotels first time login
    public static void HotelLogin(Context mcontext,String response) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("hotelLogin", MODE_PRIVATE).edit();
        editor.putString("res", response);
        editor.apply();
    }

    // Itinary first time login
    public static void ItiLogin(Context mcontext,String response) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("itiLogin", MODE_PRIVATE).edit();
        editor.putString("res", response);
        editor.apply();
    }

    // userName
    public static void MyName(Context mcontext,String response) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("myName", MODE_PRIVATE).edit();
        editor.putString("name", response);
        editor.apply();
    }

    // My Kyc documents after click on download document on social
    public static void MyDocs(Context mcontext,String adhar,String lic,String passport) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("myDocs", MODE_PRIVATE).edit();
        editor.putString("adhaar", adhar);
        editor.putString("lic", lic);
        editor.putString("passport", passport);
        editor.apply();
    }
    // wallet details
    public static void Wallets(Context mcontext,String points,String posts) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("myWallet", MODE_PRIVATE).edit();
        editor.putString("points",points);
        editor.putString("posts", posts);
        editor.apply();
    }

    // Save Festivals Presistence
    public static void Festivals(Context mcontext,String fest_img) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("festivals", MODE_PRIVATE).edit();
        editor.putString("fest_img",fest_img);
        editor.apply();
    }
    // Save Close Festivals click Presistence
    public static void CloseFest(Context mcontext,String closeFest,String date1) {
        SharedPreferences.Editor editor = mcontext.getSharedPreferences("close_fest", MODE_PRIVATE).edit();
        editor.putString("closeFest",closeFest);
        editor.putString("date",date1);
        editor.apply();
    }





}
