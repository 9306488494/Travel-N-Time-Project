package com.travelandtime.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.travelandtime.Testing;

import java.util.ArrayList;
import java.util.HashMap;

public class InternationDatabase extends SQLiteOpenHelper {
    private Context context;
    public InternationDatabase(Context context) {
        super(context,"My_IntPack",null,1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Intpack (id INTEGER PRIMARY KEY AUTOINCREMENT,sr TEXT,title TEXT,desc1 TEXT,img TEXT,location TEXT,price TEXT,nop TEXT,cat TEXT,day TEXT,night TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<newVersion)
        {
            Toast.makeText(context, "working well", Toast.LENGTH_SHORT).show();
        }
    }


    public void insert(String sr, String title, String desc1, String img, String location, String price, String nop, String cat, String day, String night) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("sr",sr);
        contentValues.put("title",title);
        contentValues.put("desc1",desc1);
        contentValues.put("img",img);
        contentValues.put("location",location);
        contentValues.put("price",price);
        contentValues.put("nop",nop);
        contentValues.put("cat",cat);
        contentValues.put("day",day);
        contentValues.put("night",night);
        sqLiteDatabase.insert("Intpack",null,contentValues);
        sqLiteDatabase.close();
    }

    // search the record and check record is exist or not
    public ArrayList<HashMap<String,String>> getDatabaseName(String sr) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM Intpack WHERE sr="+sr,null);

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

    // Fetch the record
    public ArrayList<HashMap<String,String>> getDatabaseName(String imei,String email) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor1=sqLiteDatabase.rawQuery("SELECT * FROM Intpack",null);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        if(Integer.parseInt(String.valueOf(cursor1.getCount()))>0)
        {
            if (cursor1.moveToNext()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("res", String.valueOf(cursor1.getCount()));

                    map.put("sr", String.valueOf(cursor1.getString(1)));
                    map.put("title", String.valueOf(cursor1.getString(2)));
                    map.put("desc1", String.valueOf(cursor1.getString(3)));
                    map.put("img", String.valueOf(cursor1.getString(4)));
                    map.put("location", String.valueOf(cursor1.getString(5)));
                    map.put("price", String.valueOf(cursor1.getString(6)));
                    map.put("nop", String.valueOf(cursor1.getString(7)));
                    map.put("cat", String.valueOf(cursor1.getString(8)));
                    map.put("day", String.valueOf(cursor1.getString(9)));
                    map.put("night", String.valueOf(cursor1.getString(10)));
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
