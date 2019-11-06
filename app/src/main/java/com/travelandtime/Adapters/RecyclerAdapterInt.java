package com.travelandtime.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.travelandtime.Inter;
import com.travelandtime.ProdDesc;
import com.travelandtime.R;
import com.travelandtime.Utils.AppStatus;

import java.io.File;
import java.util.ArrayList;



public class RecyclerAdapterInt extends RecyclerView.Adapter<RecyclerAdapterInt.IntViewHolder> {
    private ArrayList<Inter> international;
    private Context mcontext;
    public RecyclerAdapterInt(Context mContext,ArrayList<Inter> international) {
        this.mcontext = mContext;
      this.international=international;
    }

    @NonNull
    @Override
    public IntViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(mcontext).inflate(R.layout.dom_pack, viewGroup, false);
        return new RecyclerAdapterInt.IntViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull IntViewHolder intViewHolder, int i) {
       /* Glide.with(mcontext)
                .load(R.drawable.loading)
                .into(intViewHolder.img1);*/

       /* if (AppStatus.getInstance(mcontext).isOnline()) {
            Picasso.get().load(international.get(i).getImg()).into(intViewHolder.img1);
        }
        else
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), international.get(i).getImg());
            if(file.exists()) {
                Uri path1 = Uri.fromFile(file);
                Picasso.get().load(path1).into(intViewHolder.img1);
            }
        }*/



        final String Image=international.get(i).getImg();
        final String Title=international.get(i).getTitle();
        final String Price=international.get(i).getPrice();
        final String Desc1=international.get(i).getDesc1();
        final String Loc=international.get(i).getLocation();
        final String id=international.get(i).getSr();
        final String night=international.get(i).getSr();
        final String day=international.get(i).getDay();
        final String nop=international.get(i).getNop();

        Picasso.get().load(Image).into(intViewHolder.img1);
        intViewHolder.title.setText(international.get(i).getLocation());
        intViewHolder.starting.setText("Starting");
        intViewHolder.price.setText(" Rs." + international.get(i).getPrice() + "/-");
        intViewHolder.nop.setText(international.get(i).getNop());

        // click on cardid and open in product details
        intViewHolder.cardid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(mcontext, ProdDesc.class);
                intent1.putExtra("Image",Image);
                intent1.putExtra("Title",Title);
                intent1.putExtra("Price",Price);
                intent1.putExtra("Desc",Desc1);
                intent1.putExtra("Location",Loc);
                intent1.putExtra("ID",id);
                intent1.putExtra("Night",night);
                intent1.putExtra("Day",day);
                intent1.putExtra("Nop",nop);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent1);
            }
        });

    }


    @Override
    public int getItemCount() {
        return international.size();
    }

    class IntViewHolder extends RecyclerView.ViewHolder {
        private ImageView img1;
        private TextView starting;
        private TextView price;
        private TextView nop;
        private TextView title;
        private CardView cardid;

        IntViewHolder(@NonNull View itemView) {
            super(itemView);

            img1 = (ImageView) itemView.findViewById(R.id.img1);
            starting = (TextView) itemView.findViewById(R.id.starting);
            price = (TextView) itemView.findViewById(R.id.price);
            nop = (TextView) itemView.findViewById(R.id.nop);
            title = (TextView) itemView.findViewById(R.id.title);
            cardid = (CardView) itemView.findViewById(R.id.cardid);
        }
    }
}
