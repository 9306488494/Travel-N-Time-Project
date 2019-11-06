package com.travelandtime.Fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.travelandtime.BuildConfig;
import com.travelandtime.MyWeb;
import com.travelandtime.R;
import com.travelandtime.Testing;
import com.travelandtime.Utils.AppStatus;
import com.travelandtime.Utils.GetPresistenceData;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


import static android.content.Context.DOWNLOAD_SERVICE;
import static java.security.AccessController.getContext;

class RLatest extends RecyclerView.Adapter<RLatest.Holder> {
    Context mcontext;
    DownloadManager dm;
    long queueid;
    ArrayList<FList1> fList1;
    RLatest(Context mcontext, ArrayList<FList1> fList1) {
        this.fList1=fList1;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.tripflight1,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RLatest.Holder holder, final int i) {
        holder.F1currentDate.setText(fList1.get(i).getToday_date());
        holder.F1txtTo.setText(fList1.get(i).getToo());
        holder.F1txtFrom.setText(fList1.get(i).getFromm());
        holder.F1deptTime.setText("Depart "+fList1.get(i).getDept_date());
        holder.F1statusBtn.setText(fList1.get(i).getStatus1());
        holder.F1statusBtn1.setText(fList1.get(i).getStatus1());
        holder.F1flightName.setText(fList1.get(i).getPlane_name());
        holder.F1download.setText("Download");
        holder.F1view.setText("View");
        if(fList1.get(i).getStatus1().equals("On Process"))
        {
            holder.F1Cardbtn.setVisibility(View.VISIBLE);
            // holder.F1statusBtn.setBackgroundColor(Integer.parseInt(String.valueOf(R.color.dark_green)));

        }
        else
        {

            holder.F1Cardbtn1.setVisibility(View.VISIBLE);
        }

        String img_url=fList1.get(i).getPlane_pic();
        Picasso.get().load(img_url).into(holder.F1img);

        // download and view click
        holder.F1download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm=(DownloadManager) mcontext.getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(fList1.get(i).getFile_url()));
                queueid=dm.enqueue(request);
            }
        });
        holder.F1view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS);
                Toast.makeText(mcontext, "File Downloaded click on View button to see", Toast.LENGTH_SHORT).show();
                mcontext.startActivity(intent);
            }
        });
        holder.F1show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetPresistenceData.getFlightLogin(Objects.requireNonNull(mcontext)).equals("1"))
                {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fList1.get(i).getFile_url());
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
                    intent.putExtra("url",fList1.get(i).getFile_url());
                    mcontext.startActivity(intent);
                }

            }
        });



    }


    @Override
    public int getItemCount() {
        return fList1.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView F1currentDate;
        private TextView F1txtTo;
        private TextView F1txtFrom;
        private TextView F1deptTime;
        private TextView F1statusBtn;
        private TextView F1statusBtn1;
        private TextView F1download;
        private TextView F1view;
        private ImageView F1img;
        private TextView F1flightName;
        private CardView F1Cardbtn1;
        private CardView F1Cardbtn;
        private TextView F1show;




        Holder(@NonNull View itemView) {
            super(itemView);

            F1currentDate = (TextView) itemView.findViewById(R.id.F1current_date);
            F1txtTo = (TextView) itemView.findViewById(R.id.F1txtTo);
            F1txtFrom = (TextView) itemView.findViewById(R.id.F1txtFrom);
            F1deptTime = (TextView) itemView.findViewById(R.id.F1dept_time);
            F1statusBtn = (TextView) itemView.findViewById(R.id.F1status_btn);
            F1download = (TextView) itemView.findViewById(R.id.F1download);
            F1view = (TextView) itemView.findViewById(R.id.F1view);
            F1img = (ImageView) itemView.findViewById(R.id.F1img);
            F1flightName = (TextView) itemView.findViewById(R.id.F1flight_name);
            F1Cardbtn1 = (CardView) itemView.findViewById(R.id.F1Cardbtn1);
            F1statusBtn1 = (TextView) itemView.findViewById(R.id.F1status_btn1);
            F1Cardbtn = (CardView) itemView.findViewById(R.id.F1Cardbtn);
            F1show = (TextView) itemView.findViewById(R.id.F1show);
        }
    }
}
