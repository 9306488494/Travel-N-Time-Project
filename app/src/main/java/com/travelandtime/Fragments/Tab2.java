package com.travelandtime.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import com.travelandtime.Configration.Config;
import com.travelandtime.Databases.HotelsDatabase;
import com.travelandtime.Nstate.NetworkNot;
import com.travelandtime.R;
import com.travelandtime.Splash;
import com.travelandtime.Utils.AppStatus;
import com.travelandtime.Utils.GetPresistenceData;
import com.travelandtime.Utils.PresistenceData;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Tab2.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends Fragment {
    private RecyclerView tab1Rec1;
    private RecyclerView tab1Rec2;
    ArrayList<SList1> sList1;
    ArrayList<SList2>sList2;
    String IMEI,email;
    private TextView F1LatestOrder;
    private TextView F1Oldorders;
    ProgressDialog pd;
    HotelsDatabase hotelsDatabase;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
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
        hotelsDatabase=new HotelsDatabase(getContext());
        // Inflate the layout for this fragment
        View view;
        view=inflater.inflate(R.layout.tab1,container,false);
        tab1Rec1 = (RecyclerView) view.findViewById(R.id.tab1_rec1);
        tab1Rec2 = (RecyclerView) view.findViewById(R.id.tab1_rec2);
        F1Oldorders = (TextView) view.findViewById(R.id.F1Oldorders);
        F1LatestOrder = (TextView) view.findViewById(R.id.F1LatestOrder);
        // initilized widget
        F1LatestOrder.setText("Latest Hotels Tickets");
        F1Oldorders.setText("Old Hotels Tickets");
        // setup progress dialogue box
        pd=new ProgressDialog(getContext());
        pd.setTitle("Loading Data...");
        pd.setMessage("Fetching Data...");
        pd.setCancelable(true);
        IMEI= GetPresistenceData.getMyIMEI(Objects.requireNonNull(getActivity()));
        email=GetPresistenceData.getEmail(Objects.requireNonNull(getActivity()));
        sList1=new ArrayList<>();
        sList2=new ArrayList<>();
        if(GetPresistenceData.getHotelLogin(Objects.requireNonNull(getContext())).equals("1"))
        {
            fetchHotels();
        }
        else
        {
            parseData1();
        }
        parseData2();

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

    @Override
    public void onStop() {
        super.onStop();
        if(GetPresistenceData.getHotelLogin(Objects.requireNonNull(getContext())).equals("1"))
        {
            fetchHotels();
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

    private void fetchHotels() {
        // search the record from database
        ArrayList<HashMap<String,String>> list=hotelsDatabase.getDatabaseName(GetPresistenceData.getMyIMEI(Objects.requireNonNull(getContext())),GetPresistenceData.getEmail(getContext()));
        for(HashMap<String,String> s:list)
        {
            if(Integer.parseInt(String.valueOf(s.get("res")))>0)
            {

                String res=s.get("res");
                String sr=s.get("sr");
                String reg_date=s.get("reg_date");
                String hotel_name=s.get("hotel_name");
                String room_name=s.get("room_name");
                String check_in=s.get("check_in");
                String current_status=s.get("current_status");
                String image_url=s.get("image_url");
                String idd=s.get("idd");
                String file_url=s.get("file_url");

                sList1.clear();

                SList1 slist1=new SList1(sr,reg_date,hotel_name,room_name,check_in,current_status,image_url,idd,file_url);
                sList1.add(slist1);

                SLatest sLatest= new SLatest(getContext(),sList1);

                final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                tab1Rec1.setLayoutManager(layoutManager);
                tab1Rec1.setHasFixedSize(true);
                tab1Rec1.setAdapter(sLatest);

                // set Hotel first login to 1
                PresistenceData.HotelLogin(getContext(),"1");
            }
        }
    }

    private void parseData1() {
        RequestQueue queue = Volley.newRequestQueue((Objects.requireNonNull(getContext())));
        StringRequest request = new StringRequest(Request.Method.POST, Config.trip_hotel, new Response.Listener<String>() {
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
        StringRequest request = new StringRequest(Request.Method.POST, Config.trip_hotel, new Response.Listener<String>() {
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
                    String reg_date = jsonObject1.getString("today_date");
                    String hotel_name = jsonObject1.getString("hotel_name");
                    String room_name = jsonObject1.getString("room_name");
                    String check_in = jsonObject1.getString("check_in");
                    String current_status = jsonObject1.getString("current_status");
                    String image_url = jsonObject1.getString("image_url");
                    String idd = jsonObject1.getString("idd");
                    String file_url = jsonObject1.getString("file_url");

                    sList2.clear();

                    SList2 sList21=new SList2(sr,reg_date,hotel_name,room_name,check_in,current_status,image_url,idd,file_url);
                    sList2.add(sList21);

                    SOld sold= new SOld(getContext(),sList2);

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    tab1Rec2.setLayoutManager(layoutManager);
                    tab1Rec2.setHasFixedSize(true);
                    tab1Rec2.setAdapter(sold);


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
                    String reg_date = jsonObject1.getString("today_date");
                    String hotel_name = jsonObject1.getString("hotel_name");
                    String room_name = jsonObject1.getString("room_name");
                    String check_in = jsonObject1.getString("check_in");
                    String current_status = jsonObject1.getString("current_status");
                    String image_url = jsonObject1.getString("image_url");
                    String idd = jsonObject1.getString("idd");
                    String file_url = jsonObject1.getString("file_url");

                    sList1.clear();

                    SList1 slist1=new SList1(sr,reg_date,hotel_name,room_name,check_in,current_status,image_url,idd,file_url);
                    sList1.add(slist1);

                    SLatest sLatest= new SLatest(getContext(),sList1);

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    tab1Rec1.setLayoutManager(layoutManager);
                    tab1Rec1.setHasFixedSize(true);
                    tab1Rec1.setAdapter(sLatest);


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
