package com.travelandtime.Social;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travelandtime.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class CmtRec extends RecyclerView.Adapter<CmtRec.Holder> {
    Context mcontext;
    ArrayList<Comment2> comments;
    CmtRec(Context mcontext, ArrayList<Comment2> comments) {
        this.comments=comments;
        this.mcontext=mcontext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.comment_design,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
    String profile_pic=comments.get(i).getProfile_pic();

        Picasso.get().load(profile_pic).into(holder.cuserImg);
        holder.commentUser.setText(comments.get(i).getUser_name());
        holder.comments.setText(comments.get(i).getComment_msg());

    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private CircleImageView cuserImg;
        private TextView commentUser;
        private TextView comments;



        public Holder(@NonNull View itemView) {
            super(itemView);
            cuserImg = (CircleImageView) itemView.findViewById(R.id.cuser_img);
            commentUser = (TextView) itemView.findViewById(R.id.comment_user);
            comments = (TextView) itemView.findViewById(R.id.comments);
        }
    }
}
