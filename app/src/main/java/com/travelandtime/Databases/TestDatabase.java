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

public class TestDatabase extends SQLiteOpenHelper {
    private Context context;
    public TestDatabase(Context context) {
        super(context,"My_test",null,1);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Test (id INTEGER PRIMARY KEY AUTOINCREMENT,sr TEXT,link TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion<newVersion)
        {
            Toast.makeText(context, "working well", Toast.LENGTH_SHORT).show();
        }
    }


    public void insert(String sr, String link) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("sr",sr);
        contentValues.put("link",link);
        sqLiteDatabase.insert("Test",null,contentValues);
        sqLiteDatabase.close();
    }

    // search the record and check record is exist or not
    public ArrayList<HashMap<String,String>> getDatabaseName(String sr) {

        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM Test WHERE sr="+sr,null);

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
        Cursor cursor1=sqLiteDatabase.rawQuery("SELECT * FROM Test",null);

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        if(Integer.parseInt(String.valueOf(cursor1.getCount()))>0)
        {
            if (cursor1.moveToNext()) {
                do {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("res", String.valueOf(cursor1.getCount()));
                    map.put("sr", String.valueOf(cursor1.getString(1)));
                    map.put("link", String.valueOf(cursor1.getString(2)));

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
