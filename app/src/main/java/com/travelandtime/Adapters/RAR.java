package com.travelandtime.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travelandtime.GetterSetter.Reviews;
import com.travelandtime.R;

import java.util.ArrayList;



public class RAR extends RecyclerView.Adapter<RAR.RViewHolder> {
    private Context mcontext;
    private ArrayList<Reviews> reviews;
    public RAR(Context mcontext, ArrayList<Reviews> reviews) {
        this.mcontext=mcontext;
        this.reviews=reviews;
    }

    @NonNull
    @Override
    public RViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.review_lay,viewGroup,false);
        return new RViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RViewHolder rViewHolder, int i) {
        String url=reviews.get(i).getUser_pic();
        Picasso.get().load(url).into(rViewHolder.userPic);
        rViewHolder.reviewUser.setText(reviews.get(i).getUser_name());
        rViewHolder.reviewTime.setText(reviews.get(i).getTime());
        rViewHolder.userComment.setText(reviews.get(i).getMsgg());
        String id=reviews.get(i).getSr();
        rViewHolder.txtRating.setText(reviews.get(i).getUser_rating());
    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class RViewHolder extends RecyclerView.ViewHolder {

        private CardView reviewLay;
        private ImageView userPic;
        private TextView reviewUser;
        private TextView reviewTime;
        private TextView userComment;
        private TextView txtRating;







        public RViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewLay = (CardView) itemView.findViewById(R.id.review_lay);
            userPic = (ImageView) itemView.findViewById(R.id.user_pic);
            reviewUser = (TextView) itemView.findViewById(R.id.review_user);
            reviewTime = (TextView) itemView.findViewById(R.id.review_time);
            userComment = (TextView) itemView.findViewById(R.id.user_comment);
            txtRating = (TextView) itemView.findViewById(R.id.txtRating);

        }
    }
}
