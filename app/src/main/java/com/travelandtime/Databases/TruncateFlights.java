package com.travelandtime.Databases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class TruncateFlights extends AsyncTask<String, String, Void> {
    @SuppressLint("StaticFieldLeak")
    Context context;
    private FlightsDatabase flightsDatabase;
    private HotelsDatabase hotelsDatabase;
    private ItiDatabase itiDatabase;

    public TruncateFlights(Context context, FlightsDatabase flightsDatabase, HotelsDatabase hotelsDatabase, ItiDatabase itiDatabase) {
        this.context=context;
        this.flightsDatabase=flightsDatabase;
        this.hotelsDatabase=hotelsDatabase;
        this.itiDatabase=itiDatabase;
    }

    @Override
    protected Void doInBackground(String... strings) {
        // Delete the record from database
        ArrayList<HashMap<String, String>> list = flightsDatabase.getDatabaseName1("1");
        ArrayList<HashMap<String, String>> list1 = hotelsDatabase.getDatabaseName1("1");
        ArrayList<HashMap<String, String>> list2 = itiDatabase.getDatabaseName1("1");

        return null;
    }

}
