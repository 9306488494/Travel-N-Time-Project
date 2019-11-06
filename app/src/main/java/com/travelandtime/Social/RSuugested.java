package com.travelandtime.Social;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;
import com.travelandtime.Configration.Config;
import com.travelandtime.R;
import com.travelandtime.Utils.AppStatus;
import com.travelandtime.Utils.GetPresistenceData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class RSuugested extends RecyclerView.Adapter<RSuugested.Holder> {
    private RequestQueue queue;
    private Context mcontext;
    private ArrayList<Suggested> suggested1;
    RSuugested(Context mcontext, ArrayList<Suggested> suggested1) {
        this.mcontext=mcontext;
        this.suggested1=suggested1;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.suugest_me,viewGroup,false);
        return new Holder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final RSuugested.Holder holder, @SuppressLint("RecyclerView") final int i) {


        String layPos=String.valueOf(holder.getLayoutPosition());
        String arraySizee=String.valueOf(suggested1.size());
        //String newPage=String.valueOf(Integer.parseInt(layPos)+Integer.parseInt(arraySizee));
        if(layPos.equals(String.valueOf(Integer.parseInt(arraySizee)-1)))
        {
            if (AppStatus.getInstance(mcontext).isOnline()) {
                if(arraySizee.equals(String.valueOf(Integer.parseInt(suggested1.get(i).getSr()))))
                {
                   // max fetch value ends on ASC first sr which match to imei
                }
                else {

                    Community.loadSuggest(mcontext, String.valueOf(Integer.parseInt(layPos) + 10),suggested1.get(i).getImei());
                }

            }
        }
        // set Parameters
        String img_url=suggested1.get(i).getMy_pic();
        Picasso.get().load(img_url).into(holder.img);
        holder.name.setText(suggested1.get(i).getName());

        // add friend from suggested
        holder.addFrndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend(suggested1.get(i).getImei(),holder);
            }
        });

        // click on main layout
        holder.suggestMainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,BigPic.class);
                intent.putExtra("image","NA");
                intent.putExtra("text",suggested1.get(i).getImei());
                intent.putExtra("type","3");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });

    }
    private void addFriend(final String text, final Holder holder) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.add_friend, new Response.Listener<String>() {
            @Override
            public void onResponse(String response3) {
                parseData3(response3,holder,text);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mcontext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei", GetPresistenceData.getMyIMEI(mcontext));
                mydata.put("frnd_imei", text);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData3(String response3, Holder holder, String text) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response3);
            String Status = jsonObject.getString("status");
            if (Status.equals("1")) {
                Toast.makeText(mcontext, "Friend Added", Toast.LENGTH_SHORT).show();
                suggested1.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                notifyItemRangeChanged(holder.getLayoutPosition(), suggested1.size());
                saveNotification(text,GetPresistenceData.getMetaName(mcontext),", added you as friend ","1");
            } else {
                Toast.makeText(mcontext, "Server not responding", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    private void saveNotification(final String imei, final String metaName, final String msg, final String type) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.notification, new Response.Listener<String>() {
            @Override
            public void onResponse(String response8) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mcontext, "Server not responding", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> mydata = new HashMap<>();
                mydata.put("imei",imei);
                mydata.put("name",metaName);
                mydata.put("msgg",msg);
                mydata.put("type",type);

                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    @Override
    public int getItemCount() {
        return suggested1.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView name;
        private Button addFrndBtn;
        private LinearLayout suggestMainLay;



        Holder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            addFrndBtn = (Button) itemView.findViewById(R.id.add_frnd_btn);
            suggestMainLay = (LinearLayout) itemView.findViewById(R.id.suggest_main_lay);
        }
    }
}
