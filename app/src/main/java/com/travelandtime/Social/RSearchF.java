package com.travelandtime.Social;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.travelandtime.Utils.GetPresistenceData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

class RSearchF extends RecyclerView.Adapter<RSearchF.Holder> {
    RequestQueue queue;
    Context mcontext;
    ArrayList<Suser> susers;
    public RSearchF(Context mcontext, ArrayList<Suser> susers) {
        this.mcontext=mcontext;
        this.susers=susers;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.search_frnds,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RSearchF.Holder holder, final int i) {
        // set Parameters
        String img_url=susers.get(i).getMy_pic();
        Picasso.get().load(img_url).into(holder.widImg);
        holder.widName.setText(susers.get(i).getName());
        if(susers.get(i).getState().equals("NA"))
        {
            holder.widState.setVisibility(View.GONE);
        }
        else {
            holder.widState.setText(susers.get(i).getState());
        }
        if(susers.get(i).getFrnd().equals("1"))
        {
            holder.addfriendSBtn.setVisibility(View.GONE);
        }
        else
        {
            holder.removefrndSBtn.setVisibility(View.GONE);
        }



        // click on add friend
        holder.addfriendSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend(susers.get(i).getImei(),holder,Config.add_friend,"1");
            }
        });
        // click to open profile
        holder.userSearchLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new saveNotification(susers.get(i).getImei(),GetPresistenceData.getMetaName(mcontext),", visit your profile","1",mcontext,queue).execute();
                Intent intent=new Intent(mcontext,BigPic.class);
                intent.putExtra("image",susers.get(i).getMy_pic());
                intent.putExtra("type","3");
                intent.putExtra("text",susers.get(i).getImei());
                mcontext.startActivity(intent);
            }
        });

        // click on Remove friend
        holder.removefrndSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend(susers.get(i).getImei(),holder,Config.remove_friend,"2");
                new saveNotification(susers.get(i).getImei(),GetPresistenceData.getMetaName(mcontext),", visit your profile","1",mcontext,queue).execute();


            }
        });
    }





    private void addFriend(final String text, final RSearchF.Holder holder, String url, final String types) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response3) {
                parseData3(response3,holder,text,types);
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
    private void parseData3(String response3, RSearchF.Holder holder, String text, String types) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response3);
            String Status = jsonObject.getString("status");
            if (Status.equals("1")) {
                if(types.equals("1")) {
                    Toast.makeText(mcontext, "Friend Added", Toast.LENGTH_SHORT).show();
                    new saveNotification(text, GetPresistenceData.getMetaName(mcontext), ", added you as friend ", "1", mcontext, queue).execute();
                    susers.remove(holder.getLayoutPosition());
                    notifyItemRemoved(holder.getLayoutPosition());
                    notifyItemRangeChanged(holder.getLayoutPosition(), susers.size());
                }
                else
                {
                    Toast.makeText(mcontext, "Friend Removed", Toast.LENGTH_SHORT).show();
                    new saveNotification(text, GetPresistenceData.getMetaName(mcontext), ", removed you as friend ", "1", mcontext, queue).execute();
                    susers.remove(holder.getLayoutPosition());
                    notifyItemRemoved(holder.getLayoutPosition());
                    notifyItemRangeChanged(holder.getLayoutPosition(), susers.size());
                }

            } else {
                Toast.makeText(mcontext, "Server not responding", Toast.LENGTH_SHORT).show();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    @Override
    public int getItemCount() {
        return susers.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private CircleImageView widImg;
        private TextView widName;
        private TextView widState;
        private Button addfriendSBtn;
        private Button removefrndSBtn;
        private LinearLayout userSearchLay;



        public Holder(@NonNull View itemView) {
            super(itemView);
            widImg = (CircleImageView) itemView.findViewById(R.id.wid_img);
            widName = (TextView) itemView.findViewById(R.id.wid_name);
            widState = (TextView) itemView.findViewById(R.id.wid_state);
            addfriendSBtn = (Button) itemView.findViewById(R.id.addfriend_s_btn);
            removefrndSBtn = (Button) itemView.findViewById(R.id.removefrnd_s_btn);
            userSearchLay = (LinearLayout) itemView.findViewById(R.id.user_search_lay);
        }
    }
}
