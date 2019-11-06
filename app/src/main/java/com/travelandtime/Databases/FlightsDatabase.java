package com.travelandtime.Databases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class FlightsDatabase extends SQLiteOpenHelper {
    private Context context;
    public FlightsDatabase(Context context) {
        super(context,"My_Datas",null,1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Flight (id INTEGER PRIMARY KEY AUTOINCREMENT,sr TEXT,today_date TEXT,imei TEXT,too TEXT,fromm TEXT,dept_date TEXT,status1 TEXT,file_url TEXT,plane_pic TEXT,plane_name TEXT,idd TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<newVersion)
        {
            Toast.makeText(context, "working well", Toast.LENGTH_SHORT).show();
        }
    }


    public void insert(String sr, String today_date, String imei, String too, String fromm, String dept_date, String status1, String file_url, String plane_pic, String plane_name,String idd) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("sr",sr);
        contentValues.put("today_date",today_date);
        contentValues.put("imei",imei);
        contentValues.put("too",too);
        contentValues.put("fromm",fromm);
        contentValues.put("dept_date",dept_date);
        contentValues.put("status1",status1);
        contentValues.put("file_url",file_url);
        contentValues.put("plane_pic",plane_pic);
        contentValues.put("plane_name",plane_name);
        contentValues.put("idd",idd);
        sqLiteDatabase.insert("Flight",null,contentValues);
        sqLiteDatabase.close();
    }

    // search the record and check record is exist or not
    public ArrayList<HashMap<String,String>> getDatabaseName(String sr) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM Flight WHERE sr="+sr,null);

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
        sqLiteDatabase.execSQL("delete from Flight");
      /*  sqLiteDatabase.delete("Flight",null,null);*/
        sqLiteDatabase.close();
        return null;
    }



    // Fetch the record
    public ArrayList<HashMap<String,String>> getDatabaseName(String imei,String email) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor1=sqLiteDatabase.rawQuery("SELECT * FROM Flight",null);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        if(Integer.parseInt(String.valueOf(cursor1.getCount()))>0)
        {
            if (cursor1.moveToNext()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("res", String.valueOf(cursor1.getCount()));
                    map.put("sr", String.valueOf(cursor1.getString(1)));
                    map.put("today_date", String.valueOf(cursor1.getString(2)));
                    map.put("imei", String.valueOf(cursor1.getString(3)));
                    map.put("too", String.valueOf(cursor1.getString(4)));
                    map.put("fromm", String.valueOf(cursor1.getString(5)));
                    map.put("dept_date", String.valueOf(cursor1.getString(6)));
                    map.put("status1", String.valueOf(cursor1.getString(7)));
                    map.put("file_url", String.valueOf(cursor1.getString(8)));
                    map.put("plane_pic", String.valueOf(cursor1.getString(9)));
                    map.put("plane_name", String.valueOf(cursor1.getString(10)));
                    map.put("idd", String.valueOf(cursor1.getString(11)));
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
