package com.travelandtime.Adapters;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.travelandtime.BuildConfig;
import com.travelandtime.Configration.Config;
import com.travelandtime.Inter;
import com.travelandtime.ProdDesc;
import com.travelandtime.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class PackAdapter extends RecyclerView.Adapter<PackAdapter.Holder> {
    private Context mcontext;
    private ArrayList<Inter> intList;


    public PackAdapter(Context mcontext, ArrayList<Inter> intList) {
        this.mcontext = mcontext;
        this.intList = intList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(mcontext).inflate(R.layout.all_packs, viewGroup, false);
        return new Holder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        final String Image = intList.get(i).getImg();
        final String Title = intList.get(i).getTitle();
        final String Price = intList.get(i).getPrice();
        final String Desc1 = intList.get(i).getDesc1();
        final String Loc = intList.get(i).getLocation();
        final String id = intList.get(i).getSr();
        final String night = intList.get(i).getSr();
        final String day = intList.get(i).getDay();
        final String nop = intList.get(i).getNop();

        // set widgets
        Picasso.get().load(intList.get(i).getImg()).into(holder.backImg);
        Picasso.get().load(intList.get(i).getImg()).into(holder.mainImg);
        holder.Loc.setText(Loc);
        holder.prices.setText(Price + "/per person");

        // bookPack call to customer services
        holder.bookPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                String mobData = Config.mobileNo;
                callIntent.setData(Uri.parse("tel:" + mobData));//change the number
                if (ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(callIntent);
            }
        });
        // click on share package

        // click on Back image and send to ProdDesc
        holder.backImg.setOnClickListener(new View.OnClickListener() {
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

        // click on mainimage to send on ProdDesc
holder.mainImg.setOnClickListener(new View.OnClickListener() {
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
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager manager = mcontext.getPackageManager();
                Picasso.get().load(Image).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        /* uri1=Uri.parse(Paths+File.separator+"10.jpg");*/
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        //intent.putExtra(intent.EXTRA_SUBJECT,"Insert Something new");
                        String data = "\n "+"Attractive Packages on"+"\n"+Title+" \n \n "+"@ Rs."+Price+" /- \n"+night+"Night/"+day+"\n \n "+"Click here to install app: \n"+ Uri.parse("http://play.google.com/store/apps/details?id=" + mcontext.getPackageName())+"\n\n visi us on : http://travelntime.com"+"\n\n Call us on : + 91 124 4222401";
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_TEXT,data);
                        intent.putExtra(Intent.EXTRA_STREAM,getLocalBitmapUri(bitmap,mcontext));
                        intent.setPackage(Config.whatsapp_package);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // for particular choose we will set getPackage()
                        /*startActivity(intent.createChooser(intent,"Share Via"));*/// this code use for universal sharing
                        mcontext.startActivity(intent);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                        Toast.makeText(mcontext, "Load Image Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

                // end Share code
            }
        });

    } // on Binder close




    private Uri getLocalBitmapUri(Bitmap bmp, Context context) {

        Uri uriimg = null;

        try {

            File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Tnt" + System.currentTimeMillis() + ".png");

            FileOutputStream out = new FileOutputStream(file);

            bmp.compress(Bitmap.CompressFormat.PNG, 50, out);

            out.close();

            /*uriimg = Uri.fromFile(file);*/
            uriimg = FileProvider.getUriForFile(Objects.requireNonNull(mcontext),
                    BuildConfig.APPLICATION_ID + ".provider", file);
            refreshGallery(file);
        } catch (IOException e) {

            e.printStackTrace();

        }

        return uriimg;

    }

    private void refreshGallery(File file) {
        Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent1.setData(Uri.fromFile(file));
        Objects.requireNonNull(mcontext).sendBroadcast(intent1);
    }

    @Override
    public int getItemCount() {
        return intList.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        private ImageView backImg;
        private ImageView mainImg;
        private TextView Loc;
        private TextView prices;
        private Button bookPack;
        private Button share;



        Holder(@NonNull View itemView) {
            super(itemView);
            backImg = (ImageView) itemView.findViewById(R.id.backImg);
            mainImg = (ImageView) itemView.findViewById(R.id.mainImg);
            Loc = (TextView) itemView.findViewById(R.id.Loc);
            prices = (TextView) itemView.findViewById(R.id.prices);
            bookPack = (Button) itemView.findViewById(R.id.bookPack);
            share = (Button) itemView.findViewById(R.id.share);

        }
    }
}
