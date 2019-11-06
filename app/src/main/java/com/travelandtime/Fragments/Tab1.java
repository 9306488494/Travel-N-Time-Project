package com.travelandtime.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.travelandtime.Configration.Config;
import com.travelandtime.Databases.FlightsDatabase;
import com.travelandtime.Nstate.NetworkNot;
import com.travelandtime.R;
import com.travelandtime.Testing;
import com.travelandtime.Utils.AppStatus;
import com.travelandtime.Utils.GetPresistenceData;
import com.travelandtime.Utils.PresistenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab1.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {

    private RecyclerView tab1Rec1;
    private RecyclerView tab1Rec2;
    ArrayList<FList1>fList1;
    ArrayList<FList2>fList2;
    String IMEI,email;
    private TextView F1LatestOrder;
    private TextView F1Oldorders;
    ProgressDialog pd;
    FlightsDatabase flightsDatabase;







    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab1.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        flightsDatabase=new FlightsDatabase(getActivity());

        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.tab1,container,false);
        tab1Rec1 = (RecyclerView) view.findViewById(R.id.tab1_rec1);
        tab1Rec2 = (RecyclerView) view.findViewById(R.id.tab1_rec2);
        F1Oldorders = (TextView) view.findViewById(R.id.F1Oldorders);
        F1LatestOrder = (TextView) view.findViewById(R.id.F1LatestOrder);
        // initilized widget
        F1LatestOrder.setText("Latest Flights");
        F1Oldorders.setText("Old Flights");
        // setup progress dialogue box
        pd=new ProgressDialog(getContext());
        pd.setTitle("Loading Data...");
        pd.setMessage("Fetching Data...");
        pd.setCancelable(true);
        IMEI= GetPresistenceData.getMyIMEI(Objects.requireNonNull(getActivity()));
        email=GetPresistenceData.getEmail(Objects.requireNonNull(getActivity()));
        fList1=new ArrayList<>();
        fList2=new ArrayList<>();

        parseData2();
        if(GetPresistenceData.getFlightLogin(Objects.requireNonNull(getContext())).equals("1"))
        {
            fetchFlights();
        }
        else
        {
            parseData1();
        }

        // click listner on rec1
        F1LatestOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tab1Rec1.getVisibility()==View.VISIBLE)
                {
                   tab1Rec1.setVisibility(View.GONE);
                   F1LatestOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.uparrow, 0);
                }
                else
                {
                    tab1Rec1.setVisibility(View.VISIBLE);
                    F1LatestOrder.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.downarrow, 0);
                }
            }
        });
        // click listner on rec2
        F1Oldorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tab1Rec2.getVisibility()==View.VISIBLE)
                {
                    tab1Rec2.setVisibility(View.GONE);
                    F1Oldorders.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.uparrow, 0);
                }
                else
                {
                    tab1Rec2.setVisibility(View.VISIBLE);
                    F1Oldorders.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.downarrow, 0);
                }
            }
        });


        return view;
    }

    private void fetchFlights() {
        // search the record from database
        ArrayList<HashMap<String,String>> list=flightsDatabase.getDatabaseName(GetPresistenceData.getMyIMEI(Objects.requireNonNull(getContext())),GetPresistenceData.getEmail(getContext()));
        for(HashMap<String,String> s:list)
        {
            if(Integer.parseInt(String.valueOf(s.get("res")))>0)
            {
                String res=s.get("res");
                String sr=s.get("sr");
                String today_date=s.get("today_date");
                String imei=s.get("imei");
                String too=s.get("too");
                String fromm=s.get("fromm");
                String dept_date=s.get("dept_date");
                String status1=s.get("status1");
                String file_url=s.get("file_url");
                String plane_pic=s.get("plane_pic");
                String plane_name=s.get("plane_name");
                String idd=s.get("idd");

                fList1.clear();

                FList1 fList1s=new FList1(sr,today_date,too,fromm,dept_date,status1,file_url,plane_pic,plane_name);
                fList1.add(fList1s);

                RLatest rLatest= new RLatest(getContext(),fList1);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                tab1Rec1.setLayoutManager(layoutManager);
                tab1Rec1.setHasFixedSize(true);
                tab1Rec1.setAdapter(rLatest);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(GetPresistenceData.getFlightLogin(Objects.requireNonNull(getContext())).equals("1"))
        {
            fetchFlights();
        }
        else
        {
            parseData1();
        }
        // if network is available then parse old data the data
        if (AppStatus.getInstance(getContext()).isOnline()) {
            parseData2();
        }

    }

    private void parseData1() {
        RequestQueue queue = Volley.newRequestQueue((Objects.requireNonNull(getContext())));
        StringRequest request = new StringRequest(Request.Method.POST, Config.trip_flight, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                parseData11(response1);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei",IMEI);
                mydata.put("email",email);
                mydata.put("id","1");
                return mydata;
            }
        };

        queue.add(request);
    }

    private void parseData2() {
        RequestQueue queue = Volley.newRequestQueue((Objects.requireNonNull(getContext())));
        StringRequest request = new StringRequest(Request.Method.POST, Config.trip_flight, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                parseData22(response2);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei",IMEI);
                mydata.put("email",email);
                mydata.put("id","2");
                return mydata;
            }
        };

        queue.add(request);
    }

    private void parseData22(String response2) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response2);
            String status=jsonObject.getString("status");
            if(status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String sr = jsonObject1.getString("sr");
                    String today_date = jsonObject1.getString("today_date");
                    String too = jsonObject1.getString("too");
                    String fromm = jsonObject1.getString("fromm");
                    String dept_date = jsonObject1.getString("dept_date");
                    String status1 = jsonObject1.getString("status1");
                    String file_url = jsonObject1.getString("file_url");
                    String plane_pic = jsonObject1.getString("plane_pic");
                    String plane_name = jsonObject1.getString("plane_name");

                    fList2.clear();

                    FList2 fList2s=new FList2(sr,today_date,too,fromm,dept_date,status1,file_url,plane_pic,plane_name);
                    fList2.add(fList2s);

                    ROld rOld= new ROld(getContext(),fList2);

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    tab1Rec2.setLayoutManager(layoutManager);
                    tab1Rec2.setHasFixedSize(true);
                    tab1Rec2.setAdapter(rOld);


                }
            }
            else
            {
                pd.dismiss();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void parseData11(String response1) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response1);
            String status=jsonObject.getString("status");
            if(status.equals("1")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    String sr = jsonObject1.getString("sr");
                    String today_date = jsonObject1.getString("today_date");
                    String too = jsonObject1.getString("too");
                    String fromm = jsonObject1.getString("fromm");
                    String dept_date = jsonObject1.getString("dept_date");
                    String status1 = jsonObject1.getString("status1");
                    String file_url = jsonObject1.getString("file_url");
                    String plane_pic = jsonObject1.getString("plane_pic");
                    String plane_name = jsonObject1.getString("plane_name");
                    fList1.clear();

                    FList1 fList1s=new FList1(sr,today_date,too,fromm,dept_date,status1,file_url,plane_pic,plane_name);
                    fList1.add(fList1s);

                    RLatest rLatest= new RLatest(getContext(),fList1);

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    tab1Rec1.setLayoutManager(layoutManager);
                    tab1Rec1.setHasFixedSize(true);
                    tab1Rec1.setAdapter(rLatest);

                    // set flight first login to 1
                    PresistenceData.FlightsLogin(Objects.requireNonNull(getContext()),"1");
                }
            }
            else
            {
                pd.dismiss();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
