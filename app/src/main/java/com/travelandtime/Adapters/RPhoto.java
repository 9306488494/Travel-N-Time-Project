package com.travelandtime.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import com.travelandtime.GetterSetter.Photo1;
import com.travelandtime.R;

public class RPhoto extends RecyclerView.Adapter<RPhoto.Pholder> {
    private Context mcontext;
    private ArrayList<Photo1> photo1;
    public RPhoto(Context mcontext, ArrayList<Photo1> photo1) {
        this.mcontext=mcontext;
        this.photo1=photo1;
    }


    @NonNull
    @Override
    public Pholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.photo_frames,viewGroup,false);
        return new RPhoto.Pholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Pholder pholder, int i) {
        String url=photo1.get(i).getUrl();
        Picasso.get().load(url).into(pholder.Image);


    }



    @Override
    public int getItemCount() {
        return photo1.size();
    }

    class Pholder extends RecyclerView.ViewHolder {
        private ImageView Image;


        Pholder(@NonNull View itemView) {
            super(itemView);

            Image = (ImageView) itemView.findViewById(R.id.Image);

        }
    }
}
