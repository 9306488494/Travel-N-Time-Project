package com.travelandtime.Fragments;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.travelandtime.MyWeb;
import com.travelandtime.R;

import java.util.ArrayList;



import static android.content.Context.DOWNLOAD_SERVICE;

class ROld extends RecyclerView.Adapter<ROld.Holder> {
    DownloadManager dm;
    long queueid;
    ArrayList<FList2> fList2;
    Context mcontext;
    ROld(Context mcontext, ArrayList<FList2> fList2) {
        this.fList2=fList2;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.tripflight1,viewGroup,false);
        return new Holder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ROld.Holder holder, final int i) {
        holder.F1currentDate.setText(fList2.get(i).getToday_date());
        holder.F1txtTo.setText(fList2.get(i).getToo());
        holder.F1txtFrom.setText(fList2.get(i).getFromm());
        holder.F1deptTime.setText("Depart "+fList2.get(i).getDept_date());
        holder.F1statusBtn.setText(fList2.get(i).getStatus1());
        holder.F1flightName.setText(fList2.get(i).getPlane_name());
        holder.F1download.setText("Download");
        holder.F1view.setText("View");
        holder.F1Cardbtn.setVisibility(View.VISIBLE);

       /* if(fList2.get(i).getStatus1().equals("E-Ticket Released"))
        {
            //holder.F1Cardbtn.setCardBackgroundColor(Integer.parseInt(String.valueOf(R.color.dark_green)));
            holder.F1statusBtn.setTextColor(Color.GREEN);

        }
        else
        {

            //holder.F1Cardbtn.setCardBackgroundColor(Integer.parseInt(String.valueOf(R.color.dark_green)));

            holder.F1Cardbtn.setCardBackgroundColor(Color.GREEN);
            holder.F1Cardbtn.setBackgroundColor(Color.GREEN);

        }*/

        String img_url=fList2.get(i).getPlane_pic();
        Picasso.get().load(img_url).into(holder.F1img);

        // download and view click
        holder.F1download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm=(DownloadManager) mcontext.getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(fList2.get(i).getFile_url()));
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
                Intent intent=new Intent(mcontext, MyWeb.class);
                intent.putExtra("url",fList2.get(i).getFile_url());
                mcontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return fList2.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private TextView F1currentDate;
        private TextView F1txtTo;
        private TextView F1txtFrom;
        private TextView F1deptTime;
        private TextView F1statusBtn;
        private TextView F1download;
        private TextView F1view;
        private ImageView F1img;
        private TextView F1flightName;
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
            F1Cardbtn = (CardView) itemView.findViewById(R.id.F1Cardbtn);
            F1show = (TextView) itemView.findViewById(R.id.F1show);
        }
    }
}
