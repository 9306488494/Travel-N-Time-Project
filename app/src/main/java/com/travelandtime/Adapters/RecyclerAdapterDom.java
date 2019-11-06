package com.travelandtime.Adapters;

import android.annotation.SuppressLint;
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

import com.squareup.picasso.Picasso;
import com.travelandtime.BuildConfig;
import com.travelandtime.Domestic;
import com.travelandtime.MyWeb;
import com.travelandtime.ProdDesc;
import com.travelandtime.R;
import com.travelandtime.Social.BigPic;
import com.travelandtime.Utils.AppStatus;

import java.io.File;
import java.util.ArrayList;



public class RecyclerAdapterDom extends RecyclerView.Adapter<RecyclerAdapterDom.DomViewHolder> {
    private ArrayList<Domestic> domestic;
    private Context mcontext;

    public RecyclerAdapterDom(Context mContext, ArrayList<Domestic> domestic) {
        this.mcontext = mContext;
        this.domestic = domestic;
    }

    @NonNull
    @Override
    public DomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(mcontext).inflate(R.layout.dom_pack, viewGroup, false);
        return new DomViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DomViewHolder domViewHolder, @SuppressLint("RecyclerView") final int i) {
      /*  Glide.with(mcontext)
                .load(R.drawable.loading)
                .into(domViewHolder.img1);*/
final String Image=domestic.get(i).getImg();
final String Title=domestic.get(i).getTitle();
final String Price=domestic.get(i).getPrice();
final String Desc1=domestic.get(i).getDesc1();
final String Loc=domestic.get(i).getLocation();
final String id=domestic.get(i).getSr();
final String night=domestic.get(i).getSr();
final String day=domestic.get(i).getDay();
final String nop=domestic.get(i).getNop();

      /*  if (AppStatus.getInstance(mcontext).isOnline()) {
            Picasso.get().load(domestic.get(i).getImg()).into(domViewHolder.img1);
        }
        else
        {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), domestic.get(i).getImg());
            if(file.exists()){
                Uri path1 = Uri.fromFile(file);
                Picasso.get().load(path1).into(domViewHolder.img1);
            }
        }*/
        Picasso.get().load(Image).into(domViewHolder.img1);

        domViewHolder.title.setText(domestic.get(i).getLocation());
        domViewHolder.starting.setText("Starting");
        domViewHolder.price.setText(" Rs." + domestic.get(i).getPrice() + "/-");
        domViewHolder.nop.setText(domestic.get(i).getNop());

        // click on image to open prod desc
             domViewHolder.cardid.setOnClickListener(new View.OnClickListener() {
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
        return domestic.size();
    }



    //------------------------------------------------------------------------



    //------------------------------------------------------------------------

    class DomViewHolder extends RecyclerView.ViewHolder {
        private ImageView img1;
        private TextView starting;
        private TextView price;
        private TextView nop;
        private TextView title;
        private CardView cardid;



        DomViewHolder(@NonNull View itemView) {
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






