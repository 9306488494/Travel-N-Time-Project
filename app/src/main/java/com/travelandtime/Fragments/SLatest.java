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

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import com.travelandtime.BuildConfig;
import com.travelandtime.MyWeb;
import com.travelandtime.R;
import com.travelandtime.Utils.AppStatus;
import com.travelandtime.Utils.GetPresistenceData;

import static android.content.Context.DOWNLOAD_SERVICE;

class SLatest extends RecyclerView.Adapter<SLatest.Holder> {
    private ArrayList<SList1> sList1;
    private Context mcontext;
    DownloadManager dm;
    long queueid;
    SLatest(Context mcontext, ArrayList<SList1> sList1) {
        this.mcontext=mcontext;
        this.sList1=sList1;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
       view=LayoutInflater.from(mcontext).inflate(R.layout.triphotel,viewGroup,false);
       return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        String image_url=sList1.get(i).getImage_url();
        Picasso.get().load(image_url).into(holder.F2img);
        holder.F2currentDate.setText(sList1.get(i).getReg_date());
        holder.F2HotelName.setText(sList1.get(i).getHotel_name());
        holder.F2RoomName.setText(sList1.get(i).getRoom_name());
        holder.F2checkIn.setText("Check-In "+sList1.get(i).getCheck_in());
        holder.F2statusTxt.setText(sList1.get(i).getCurrent_status());
        holder.F1statusTxt2.setText(sList1.get(i).getCurrent_status());
        holder.F2download.setText("Download");
        holder.F2view.setText("View");
        holder.F3show.setText("Show");
        if(sList1.get(i).getCurrent_status().equals("On Process"))
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
                DownloadManager.Request request=new DownloadManager.Request(Uri.parse(sList1.get(i).getFile_url()));
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
                if(GetPresistenceData.getFlightLogin(Objects.requireNonNull(mcontext)).equals("1"))
                {
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), sList1.get(i).getFile_url());
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
                    intent.putExtra("url",sList1.get(i).getFile_url());
                    mcontext.startActivity(intent);
                }

            }
        });

    }




    @Override
    public int getItemCount() {
        return sList1.size();
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
