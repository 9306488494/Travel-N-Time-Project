package com.travelandtime.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

public class GetPresistenceData {

    // check logon now if registered
    public static String getMyLogon(Context context) {
        SharedPreferences checkLogon = context.getSharedPreferences("logon", MODE_PRIVATE);
        String myLogon = checkLogon.getString("status", "0");
        return myLogon;
    }

    // collect imei details
    public static String getMyIMEI(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("myimei", MODE_PRIVATE);
        String imei = prefs.getString("imei", null);
        return imei;
    }
    // check user is registered or not
    public static String getMyRegStatus(Context context) {
        // collect imei details
        SharedPreferences prefs2 = context.getSharedPreferences("registration", MODE_PRIVATE);
        String myLogin_status = prefs2.getString("response", null);
        return myLogin_status;
    }

    // check user email id
    public static String getEmail(Context context) {
        // collect imei details
        SharedPreferences prefs2 = context.getSharedPreferences("profile", MODE_PRIVATE);
        String email = prefs2.getString("email", null);
        return email;
    }

    // check user pass
    public static String getPass(Context context) {
        // collect imei details
        SharedPreferences prefs2 = context.getSharedPreferences("profile", MODE_PRIVATE);
        String pass = prefs2.getString("pass", null);
        return pass;
    }

    // check user kycdoc adhaar status
    public static String getAdhar(Context context) {
        // collect imei details
        SharedPreferences prefs2 = context.getSharedPreferences("KYCDocs", MODE_PRIVATE);
        String adhaar = prefs2.getString("adhaar", null);
        return adhaar;
    }

    // check user kycdoc lic status
    public static String getLic(Context context) {
        // collect imei details
        SharedPreferences prefs2 = context.getSharedPreferences("KYCDocs", MODE_PRIVATE);
        String lic = prefs2.getString("lic", null);
        return lic;
    }

    // check user kycdoc passport status
    public static String getPassport(Context context) {
        // collect imei details
        SharedPreferences prefs2 = context.getSharedPreferences("KYCDocs", MODE_PRIVATE);
        String passport = prefs2.getString("passport", null);
        return passport;
    }
    // check user kycdoc passport status
    public static String getkycTy(Context context) {
        // collect imei details
        SharedPreferences prefs2 = context.getSharedPreferences("KycDocType", MODE_PRIVATE);
        String ty = prefs2.getString("ty", null);
        return ty;
    }

    // return number of adults array
    public static String[] getAdults(Context context) {
        // collect imei details
        String[] adults = { "Choose", "1", "2", "3", "4", "5", "6", "7", "8","9"};
        return adults;
    }

    // return position of adults array
    public static String getAdultsPos(Context context, int position) {
        // collect imei details
        String[] adults = { "Choose", "1", "2", "3", "4", "5", "6", "7", "8","9"};
        return adults[position];
    }

    // return position of childs array
    public static String[] getChild(Context context) {
        // collect imei details
        String[] child = { "Choose","0", "1", "2", "3"};
        return child;
    }

    // return number of childs array
    public static String getChildPos(Context context, int position) {
        // collect imei details
        String[] child = { "Choose","0", "1", "2", "3"};
        return child[position];
    }

    // return number of infants array
    public static String[] getInfants(Context context) {
        // collect imei details
        String[] infants = { "Choose","0", "1", "2", "3"};
        return infants;
    }

    // return position of infants array
    public static String getInfantsPos(Context context, int position) {
        // collect imei details
        String[] infants = { "Choose","0", "1", "2", "3"};
        return infants[position];
    }

    // return number of classes array
    public static String[] getClasses(Context context) {
        // collect imei details
        String[] classes = { "Choose","Economy", "Premium Economy", "Business", "First"};
        return classes;
    }

    // return position of classes array
    public static String getClassesPos(Context context, int position) {
        // collect imei details
        String[] classes = { "Choose","Economy", "Premium Economy", "Business", "First"};
        return classes[position];
    }

    // return names of the Month
    public static String[] getMonth(Context context) {
        // collect imei details
        String[] month = { "Choose", "January", "February", "March", "April", "May", "June", "July", "August","September","October","November","December"};
        return month;
    }

    // return position of the Month
    public static String getMonthPos(Context context, int position) {
        // collect imei details
        String[] month = { "Choose", "January", "February", "March", "April", "May", "June", "July", "August","September","October","November","December"};
        return month[position];
    }

    // return names of the Years
    public static String[] getYears(Context context) {
        // collect year details
        String[] years = { "Choose", "2019", "2020"};
        return years;
    }

    // return position of the Month
    public static String getYearsPos(Context context, int position) {
        // collect imei details
        String[] years = { "Choose", "2019", "2020"};
        return years[position];
    }

    // return Meta Name
    public static String getMetaName(Context context) {
        // collect meta name details
        SharedPreferences prefs2 = context.getSharedPreferences("Metadata", MODE_PRIVATE);
        String name = prefs2.getString("name", null);
        return name;
    }

    // return Meta Mobb
    public static String getMetaMobb(Context context) {
        // collect meta name details
        SharedPreferences prefs2 = context.getSharedPreferences("Metadata", MODE_PRIVATE);
        String mobb = prefs2.getString("mobb", null);
        return mobb;
    }

    // return Meta State
    public static String getMetaState(Context context) {
        // collect meta name details
        SharedPreferences prefs2 = context.getSharedPreferences("Metadata", MODE_PRIVATE);
        String state = prefs2.getString("state", null);
        return state;
    }

    // return Meta email
    public static String getMetaEmail(Context context) {
        // collect meta name details
        SharedPreferences prefs2 = context.getSharedPreferences("Metadata", MODE_PRIVATE);
        String email = prefs2.getString("email", null);
        return email;
    }

    // return Meta pic
    public static String getMetaPic(Context context) {
        // collect meta name details
        SharedPreferences prefs2 = context.getSharedPreferences("Metadata", MODE_PRIVATE);
        String my_pic = prefs2.getString("my_pic", null);
        return my_pic;
    }
    // return Meta Status
    public static String getMetaStatus(Context context) {
        // collect meta name details
        SharedPreferences prefs2 = context.getSharedPreferences("MetaStatus", MODE_PRIVATE);
        String status = prefs2.getString("status", null);
        return status;
    }


    // return tripFlights
    public static String gettripFlights(Context context) {
        SharedPreferences prefs2 = context.getSharedPreferences("tripflights", MODE_PRIVATE);
        String status = prefs2.getString("response", null);
        return status;
    }

    // return value fo First time Flight get data login
    public static String getFlightLogin(Context context) {
        SharedPreferences prefs2 = context.getSharedPreferences("flightLogin", MODE_PRIVATE);
        String status1 = prefs2.getString("res", null);
        return status1;
    }

    // return value fo First time Hotel get data login
    public static String getHotelLogin(Context context) {
        SharedPreferences prefs2 = context.getSharedPreferences("hotelLogin", MODE_PRIVATE);
        String status2 = prefs2.getString("res", null);
        return status2;
    }

    // return value fo First time Itinary get data login
    public static String getItiLogin(Context context) {
        SharedPreferences prefs2 = context.getSharedPreferences("itiLogin", MODE_PRIVATE);
        String status3 = prefs2.getString("res", null);
        return status3;
    }


    // return My name
    public static String getMyName(Context context) {
        SharedPreferences prefs3 = context.getSharedPreferences("myName", MODE_PRIVATE);
        String status4 = prefs3.getString("name", null);
        return status4;
    }

    // return Current date
    public static String getCurrentDate(Context context) {
        // set Current date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String filename = dateFormat.format(date);
        return filename;
    }

    // return Current date
    public static String getNPDates(Context context,String previousDays) {
        // set Current date
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, Integer.parseInt(previousDays));
        Date todate1 = cal.getTime();
        String filename1 = dateFormat.format(todate1);
        return filename1;
    }


    // return Kyc documents adhaar
    public static String getMyAdhaar(Context context) {
        SharedPreferences prefs3 = context.getSharedPreferences("myDocs", MODE_PRIVATE);
        String adhaar = prefs3.getString("adhaar", null);
        return adhaar;
    }

    // return Kyc documents Lic
    public static String getMyLic(Context context) {
        SharedPreferences prefs3 = context.getSharedPreferences("myDocs", MODE_PRIVATE);
        String lic = prefs3.getString("lic", null);
        return lic;
    }

    // return Kyc documents Passport
    public static String getMyPassport(Context context) {
        SharedPreferences prefs3 = context.getSharedPreferences("myDocs", MODE_PRIVATE);
        String passport = prefs3.getString("passport", null);
        return passport;
    }
    // Get wallet details such points
    public static String getwalletPoints(Context context) {
        SharedPreferences points = context.getSharedPreferences("myWallet", MODE_PRIVATE);
        return points.getString("points", null);
    }

    // Get wallet details such posts
    public static String getWalletsPosts(Context context) {
        SharedPreferences points = context.getSharedPreferences("myWallet", MODE_PRIVATE);
        return points.getString("posts", null);
    }


    // Get Festivals image
    public static String getFestivals(Context context) {
        SharedPreferences fest_img = context.getSharedPreferences("festivals", MODE_PRIVATE);
        return fest_img.getString("fest_img", null);
    }

    // Get Close Festivals click
    public static String getCloseFest(Context context) {
        SharedPreferences closeFest = context.getSharedPreferences("close_fest", MODE_PRIVATE);
        return closeFest.getString("closeFest", null);
    }

    // Get Close Festivals click
    public static String getCloseFestDate(Context context) {
        SharedPreferences closeFest = context.getSharedPreferences("close_fest", MODE_PRIVATE);
        return closeFest.getString("date", null);
    }


}
