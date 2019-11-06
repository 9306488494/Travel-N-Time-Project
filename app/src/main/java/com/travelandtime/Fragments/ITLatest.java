package com.travelandtime.Fragments;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import com.travelandtime.BuildConfig;
import com.travelandtime.Configration.Config;
import com.travelandtime.MyWeb;
import com.travelandtime.R;

import com.travelandtime.Utils.AppStatus;
import com.travelandtime.Utils.GetPresistenceData;
import com.travelandtime.Webviews.PropertyView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.DOWNLOAD_SERVICE;

class ITLatest extends RecyclerView.Adapter<ITLatest.Holder> {
    private ArrayList<F3List1> f3List1;
    private Context mcontext;
    DownloadManager dm;
    private long queueid;
    ProgressDialog pd;
    ArrayList<String>arr=new ArrayList<>();
    ArrayList<String>arr1=new ArrayList<>();
    AlertDialog.Builder builder;
    RequestQueue queue;
    ITLatest(Context mcontext, ArrayList<F3List1> f3List1) {
        this.f3List1=f3List1;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view=LayoutInflater.from(mcontext).inflate(R.layout.itinary,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, @SuppressLint("RecyclerView") final int i) {
        pd=new ProgressDialog(mcontext);
        final String image_url=f3List1.get(i).getImage_url();
        Picasso.get().load(image_url).into(holder.F2img);
        holder.F2currentDate.setText(f3List1.get(i).getReg_date());
        holder.F2HotelName.setText(f3List1.get(i).getCountry_name());
        holder.F2checkIn.setText("Travel "+f3List1.get(i).getFrom()+"--"+f3List1.get(i).getToo());
        holder.F2statusTxt.setText(f3List1.get(i).getCurrent_status());
        holder.F1statusTxt2.setText(f3List1.get(i).getCurrent_status());
        holder.F2download.setText("Open");
        holder.F2view.setText("Locations");
        holder.F3show.setText("Show");
        final String file=f3List1.get(i).getFile_url();
        // open downloaded file in offline
        if (AppStatus.getInstance(mcontext).isOnline()) {

        }
        else
        {
            holder.F2download.setVisibility(View.VISIBLE);
        }

        if(f3List1.get(i).getCurrent_status().equals("Processing"))
        {
            holder.F2cardBtn.setVisibility(View.VISIBLE);
            // holder.F1statusBtn.setBackgroundColor(Integer.parseInt(String.valueOf(R.color.dark_green)));

        }
        else
        {

            holder.F1cardBtn2.setVisibility(View.VISIBLE);
        }
        // download and view click
       /* holder.F2download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm=(DownloadManager) mcontext.getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(f3List1.get(i).getFile_url()));
                queueid=dm.enqueue(request);
            }
        });*/
        holder.F2download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
                Toast.makeText(mcontext, "File Downloaded click on View button to see", Toast.LENGTH_SHORT).show();
                mcontext.startActivity(intent);
            }
        });
        holder.F3show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetPresistenceData.getFlightLogin(Objects.requireNonNull(mcontext)).equals("1"))
                {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), f3List1.get(i).getFile_url());
                    if(file.exists()){
                        Uri path1 = Uri.fromFile(file);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri data = FileProvider.getUriForFile(mcontext, BuildConfig.APPLICATION_ID +".provider",file);
                        intent.setDataAndType(data, "application/pdf");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mcontext.startActivity(intent);
                    }
                }
                else
                {
                    Intent intent=new Intent(mcontext, MyWeb.class);
                    intent.putExtra("url",f3List1.get(i).getFile_url());
                    mcontext.startActivity(intent);
                }
            }
        });
        // click on F2view for property
        holder.F2view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arr.clear();
                arr1.clear();
                pd.setTitle("Preparing Locations...");
                pd.setMessage("Please Wait...");
                pd.show();

                collectMenu(f3List1.get(i).getCountry_name(),image_url);

        }
        });

    }

    private void collectMenu(final String country_name, final String image) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.prop_country, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                pd.dismiss();
                parseData1(response1,country_name,image);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mcontext,"Server not responding", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("country",country_name);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData1(String response1, String country_name, String image) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response1);
            String Status=jsonObject.getString("status");
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String prop_name = jsonObject1.getString("prop_name");
                String keyword = jsonObject1.getString("keyword");
                arr.add(prop_name);
                arr1.add(keyword);

            }
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(mcontext,android.R.layout.select_dialog_item,arr);

            builder = new AlertDialog.Builder(mcontext);
            builder.setTitle(country_name+" Property");
            LayoutInflater inflater=LayoutInflater.from(mcontext);
            View customTitle=inflater.inflate(R.layout.customtitlebar, null);
            ImageView icon = (ImageView) customTitle.findViewById(R.id.icon);
            Picasso.get().load(image).into(icon);
            TextView customtitlebar = (TextView) customTitle.findViewById(R.id.customtitlebar);
            customtitlebar.setText(country_name+" Locations");

            builder.setCustomTitle(customTitle);
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   Intent intent=new Intent(mcontext, PropertyView.class);
                   intent.putExtra("myTitle",arr.get(i));
                   intent.putExtra("url",arr1.get(i));
                   mcontext.startActivity(intent);
                }
            });

            builder.show();

        } catch (JSONException e) {
            e.printStackTrace();
        };
    }


    @Override
    public int getItemCount() {
        return f3List1.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView F2currentDate;
        private TextView F2HotelName;
        private TextView F2checkIn;
        private CardView F2cardBtn;
        private TextView F2statusTxt;
        private CardView F1cardBtn2;
        private TextView F1statusTxt2;
        private TextView F2download;
        private TextView F2view;
        private TextView F3show;
        private ImageView F2img;



        Holder(@NonNull View itemView) {
            super(itemView);

            F2currentDate = (TextView) itemView.findViewById(R.id.F2current_date);
            F2HotelName = (TextView) itemView.findViewById(R.id.F2Hotel_name);
            F2checkIn = (TextView) itemView.findViewById(R.id.F2check_in);
            F2cardBtn = (CardView) itemView.findViewById(R.id.F2card_btn);
            F2statusTxt = (TextView) itemView.findViewById(R.id.F2status_txt);
            F1cardBtn2 = (CardView) itemView.findViewById(R.id.F1card_btn2);
            F1statusTxt2 = (TextView) itemView.findViewById(R.id.F1status_txt2);
            F2download = (TextView) itemView.findViewById(R.id.F2download);
            F2view = (TextView) itemView.findViewById(R.id.F2view);
            F3show = (TextView) itemView.findViewById(R.id.F3show);
            F2img = (ImageView) itemView.findViewById(R.id.F2img);

        }
    }
}
