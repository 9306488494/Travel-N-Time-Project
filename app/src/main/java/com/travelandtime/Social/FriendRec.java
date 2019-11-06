package com.travelandtime.Social;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.travelandtime.Chat.FriendChat;
import com.travelandtime.Configration.Config;
import com.travelandtime.R;
import com.travelandtime.Utils.GetPresistenceData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

class FriendRec extends RecyclerView.Adapter<FriendRec.Holder> {
    ArrayList<FriendList> friendlist1;
    Context mcontext;
    RequestQueue queue;
    FriendRec(Context mcontext, ArrayList<FriendList> friendlist1) {
        this.mcontext=mcontext;
        this.friendlist1=friendlist1;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.friendrec,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int i) {
        String img_url=friendlist1.get(i).getPic();
        Picasso.get().load(img_url).into(holder.frndImg);
        holder.frndName.setText(friendlist1.get(i).getName());
        // set Parameters as friend and chat
        if(friendlist1.get(i).getImage().equals("chat"))
        {
            holder.btn.setText("Chat");
        }
        else {
            holder.btn.setText("Remove");
        }

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friendlist1.get(i).getImage().equals("chat"))
                {
                    Intent intent=new Intent(mcontext, FriendChat.class);
                    intent.putExtra("frnd_imei",friendlist1.get(i).getImei());
                    intent.putExtra("group","NA");
                    intent.putExtra("frnd_pic",friendlist1.get(i).getPic());
                    intent.putExtra("frnd_name",friendlist1.get(i).getName());
                    intent.putExtra("type","1");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mcontext.startActivity(intent);

                }
                else {
                    Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
                    Network network1 = new BasicNetwork(new HurlStack());

                    queue = new RequestQueue(cache1, network1);
                    queue.start();
                    StringRequest request = new StringRequest(Request.Method.POST, Config.remove_friend, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response3) {
                            parseData3(response3, "2", holder);
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
                            mydata.put("frnd_imei", friendlist1.get(i).getImei());
                            return mydata;
                        }
                    };
                    int socketTimeout = 30000;
                    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    request.setRetryPolicy(policy);
                    queue.add(request);
                }
            }
        });
    }

    private void parseData3(String response3, String types, Holder holder) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response3);
            String Status = jsonObject.getString("status");
            if (Status.equals("1")) {
                friendlist1.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                notifyItemRangeChanged(holder.getLayoutPosition(), friendlist1.size());
                    if(friendlist1.size()==0)
                    {
                        Intent intent=new Intent(mcontext,Community.class);
                        mcontext.startActivity(intent);
                    }
                }
             else {
                Toast.makeText(mcontext, "Network Issue", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        };
    }


    @Override
    public int getItemCount() {
        return friendlist1.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private CircleImageView frndImg;
        private TextView frndName;
        private Button btn;



        Holder(@NonNull View itemView) {
            super(itemView);
            frndImg = (CircleImageView) itemView.findViewById(R.id.frnd_img);
            frndName = (TextView) itemView.findViewById(R.id.frnd_name);
            btn = (Button) itemView.findViewById(R.id.btn);
        }
    }
}
