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

class ITold extends RecyclerView.Adapter<ITold.Holder> {
    private ArrayList<F3List2> f3List2;
    private Context mcontext;
    private DownloadManager dm;
    private long queueid;

    ITold(Context mcontext, ArrayList<F3List2> f3List2) {
        this.f3List2=f3List2;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.itinary,viewGroup,false);
        return new Holder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") final int i) {
        String image_url=f3List2.get(i).getImage_url();
        Picasso.get().load(image_url).into(holder.F2img);
        holder.F2currentDate.setText(f3List2.get(i).getReg_date());
        holder.F2HotelName.setText(f3List2.get(i).getCountry_name());
        holder.F2checkIn.setText("Travel "+f3List2.get(i).getFrom()+"--"+f3List2.get(i).getToo());
        holder.F2statusTxt.setText(f3List2.get(i).getCurrent_status());
        holder.F1statusTxt2.setText(f3List2.get(i).getCurrent_status());
        holder.F2download.setText("Download");
        holder.F2view.setText("View");
        holder.F3show.setText("Show");
        if(f3List2.get(i).getCurrent_status().equals("Completed"))
        {
            holder.F2cardBtn.setVisibility(View.VISIBLE);
            // holder.F1statusBtn.setBackgroundColor(Integer.parseInt(String.valueOf(R.color.dark_green)));

        }
        else
        {

            holder.F1cardBtn2.setVisibility(View.VISIBLE);
        }

        // download and view click
        holder.F2download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dm=(DownloadManager) mcontext.getSystemService(DOWNLOAD_SERVICE);
                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(f3List2.get(i).getFile_url()));
                queueid=dm.enqueue(request);
            }
        });
        holder.F2view.setOnClickListener(new View.OnClickListener() {
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
                Intent intent=new Intent(mcontext, MyWeb.class);
                intent.putExtra("url",f3List2.get(i).getFile_url());
                mcontext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return f3List2.size();
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
        private TextView F2RoomName;

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
            F2RoomName = (TextView) itemView.findViewById(R.id.F2Room_name);
        }
    }
}
