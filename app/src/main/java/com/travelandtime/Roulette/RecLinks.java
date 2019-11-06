package com.travelandtime.Roulette;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.travelandtime.GetterSetter.Links;
import com.travelandtime.R;


import java.util.ArrayList;

class RecLinks extends RecyclerView.Adapter<RecLinks.Holder> {
    Context mcontext;
    ArrayList<Links>linksNew;
    RecLinks(Context mcontext, ArrayList<Links> linksNew) {
        this.mcontext=mcontext;
        this.linksNew=linksNew;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.rec_links,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.newLinkText.setText(linksNew.get(i).getNew_link());
        holder.linkText.setText(linksNew.get(i).getLink());
        holder.stateText.setText(linksNew.get(i).getTotal_click());
    }


    @Override
    public int getItemCount() {
        return linksNew.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView newLinkText;
        private TextView linkText;
        private TextView stateText;



        public Holder(@NonNull View itemView) {
            super(itemView);
            newLinkText = (TextView) itemView.findViewById(R.id.newLinkText);
            linkText = (TextView) itemView.findViewById(R.id.linkText);
            stateText = (TextView) itemView.findViewById(R.id.stateText);

        }
    }
}
