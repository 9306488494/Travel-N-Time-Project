package com.travelandtime.Databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class HotelsDatabase extends SQLiteOpenHelper {
    private Context context;
    public HotelsDatabase(Context context) {
        super(context,"My_Hotels",null,1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Hotel (id INTEGER PRIMARY KEY AUTOINCREMENT,sr TEXT,reg_date TEXT,hotel_name TEXT,room_name TEXT,check_in TEXT,current_status TEXT,image_url TEXT,idd TEXT,file_url TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<newVersion)
        {
            Toast.makeText(context, "working well", Toast.LENGTH_SHORT).show();
        }
    }


    public void insert(String sr, String reg_date, String hotel_name, String room_name, String check_in, String current_status, String image_url, String idd, String file_url) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("sr",sr);
        contentValues.put("reg_date",reg_date);
        contentValues.put("hotel_name",hotel_name);
        contentValues.put("room_name",room_name);
        contentValues.put("check_in",check_in);
        contentValues.put("current_status",current_status);
        contentValues.put("image_url",image_url);
        contentValues.put("idd",idd);
        contentValues.put("file_url",file_url);
        sqLiteDatabase.insert("Hotel",null,contentValues);
        sqLiteDatabase.close();
    }

    // search the record and check record is exist or not
    public ArrayList<HashMap<String,String>> getDatabaseName(String sr) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM Hotel WHERE sr="+sr,null);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        if(Integer.parseInt(String.valueOf(cursor.getCount()))>0)
        {
            if (cursor.moveToNext()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("res", String.valueOf(cursor.getCount()));
                    list.add(map);
                }
                while (cursor.moveToNext());
            }
            return  list;
        }
        else {
            // if value in cursor is null then

            HashMap<String, String> map = new HashMap<>();
            map.put("res","0");
            list.add(map);
            return  list;
        }
    }


    // truncate the table
    ArrayList<HashMap<String, String>> getDatabaseName1(String sr) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from Hotel");
        /*  sqLiteDatabase.delete("Flight",null,null);*/
        sqLiteDatabase.close();
        return null;
    }

    // Fetch the record
    public ArrayList<HashMap<String,String>> getDatabaseName(String imei,String email) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor1=sqLiteDatabase.rawQuery("SELECT * FROM Hotel",null);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        if(Integer.parseInt(String.valueOf(cursor1.getCount()))>0)
        {
            if (cursor1.moveToNext()) {
                do {

                    HashMap<String, String> map = new HashMap<>();
                    map.put("res", String.valueOf(cursor1.getCount()));
                    map.put("sr", String.valueOf(cursor1.getString(1)));
                    map.put("reg_date", String.valueOf(cursor1.getString(2)));
                    map.put("hotel_name", String.valueOf(cursor1.getString(3)));
                    map.put("room_name", String.valueOf(cursor1.getString(4)));
                    map.put("check_in", String.valueOf(cursor1.getString(5)));
                    map.put("current_status", String.valueOf(cursor1.getString(6)));
                    map.put("image_url", String.valueOf(cursor1.getString(7)));
                    map.put("idd", String.valueOf(cursor1.getString(8)));
                    map.put("file_url", String.valueOf(cursor1.getString(9)));
                    list.add(map);
                }
                while (cursor1.moveToNext());
            }
            return  list;
        }
        else {
            // if value in cursor is null then
            HashMap<String, String> map = new HashMap<>();
            map.put("res","0");
            list.add(map);
            return  list;

        }

    }


}
