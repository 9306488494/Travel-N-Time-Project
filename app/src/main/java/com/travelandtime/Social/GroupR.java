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
import com.travelandtime.Chat.FriendChat;
import com.travelandtime.Configration.Config;
import com.travelandtime.R;
import com.travelandtime.Utils.GetPresistenceData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class GroupR extends RecyclerView.Adapter<GroupR.Holder> {
    private Context mcontext;
    private ArrayList<MyGroup> myGroup;
    RequestQueue queue;
    GroupR(Context mcontext, ArrayList<MyGroup> myGroup) {
        this.mcontext=mcontext;
        this.myGroup=myGroup;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.my_group,viewGroup,false);
        return new Holder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final GroupR.Holder holder, final int i) {
    // set Parameters
        holder.groupName.setText("Group Name: "+myGroup.get(i).getGroup_name());
        holder.groupDesc.setText(myGroup.get(i).getGroup_desc());
        holder.owner.setText("Owner : "+myGroup.get(i).getUser_name());
        holder.greenTag.setVisibility(View.VISIBLE);
        holder.userCount.setText(myGroup.get(i).getUsers_count());

        if(myGroup.get(i).getTypes().equals("6")) {
            holder.chatTag.setVisibility(View.VISIBLE);
            if(myGroup.get(i).getOwner().equals("1")) {
                holder.groupBtn1.setText("Delete Group");
                holder.groupBtn2.setVisibility(View.GONE);
                if(myGroup.get(i).getDate().equals(GetPresistenceData.getCurrentDate(mcontext)))
                {
                    holder.newTag.setVisibility(View.VISIBLE);
                }

            }
            else
            {
                holder.groupBtn1.setVisibility(View.GONE);
                holder.groupBtn2.setText("Leave Group");
                if(myGroup.get(i).getDate().equals(GetPresistenceData.getCurrentDate(mcontext)))
                {
                    holder.newTag.setVisibility(View.VISIBLE);
                }
            }
        }
        else
        {
            // if types is 7
            holder.groupBtn1.setText("Join Group");
            holder.groupBtn2.setVisibility(View.GONE);
            // new tag display if the data is equal to today
            if(myGroup.get(i).getDate().equals(GetPresistenceData.getCurrentDate(mcontext)))
            {
                holder.newTag.setVisibility(View.VISIBLE);
            }
        }
        // click listner on button 1
        holder.groupBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myGroup.get(i).getTypes().equals("6")) {
                    if(myGroup.get(i).getOwner().equals("1")) {
                       // Delete query here
                        deleteGroup(Config.delete_group,"1",myGroup.get(i).getGroup_id(),holder,myGroup.get(i).getGrp_checksum());
                    }
                }
                else
                {
                    // if types is 7
                    // join group query run
                    deleteGroup(Config.join_group,"2",myGroup.get(i).getGroup_id(),holder,myGroup.get(i).getGrp_checksum());
                }
            }
        });
        // Button 2 click listner Leave group
        holder.groupBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myGroup.get(i).getTypes().equals("6")) {
                    if(myGroup.get(i).getOwner().equals("1")) {

                    }
                    else
                    {
                        // Leave group query
                        deleteGroup(Config.leave_group,"3",myGroup.get(i).getGroup_id(),holder,myGroup.get(i).getGrp_checksum());
                    }
                }
            }
        });

        // click on green tag and open list of group members
        holder.greenTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mcontext,BigPic.class);
                intent.putExtra("image","");
                intent.putExtra("type","8");
                intent.putExtra("text",myGroup.get(i).getGroup_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);

            }
        });
        // click on Chat tag and open chat
        holder.chatTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mcontext, FriendChat.class);
                intent.putExtra("frnd_imei",myGroup.get(i).getGroup_name());
                intent.putExtra("group",myGroup.get(i).getGrp_checksum());
                intent.putExtra("frnd_pic","NA");
                intent.putExtra("frnd_name",myGroup.get(i).getUsers_count());
                intent.putExtra("type","2");
                mcontext.startActivity(intent);
            }
        });

    }

    private void deleteGroup(String delete_group, final String option, final String group_id, final Holder holder, final String grp_checksum) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST,delete_group, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                parseData2(response2,option,holder);
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
                // no need to pass imei but as security i passed it
                mydata.put("user_imei", GetPresistenceData.getMyIMEI(mcontext));
                mydata.put("group_id",group_id);
                mydata.put("user_pic", GetPresistenceData.getMetaPic(mcontext));
                mydata.put("user_name", GetPresistenceData.getMetaName(mcontext));
                mydata.put("grp_checksum", grp_checksum);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData2(String response2, String options, Holder holder) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response2);
            String Status = jsonObject.getString("status");
            if(Status.equals("1"))
            {
                // option value 1=Delete group, 2- Join group, 3- Left group
                if(options.equals("1"))
                {
                    Toast.makeText(mcontext, "Group Deleted", Toast.LENGTH_SHORT).show();
                    myGroup.remove(holder.getLayoutPosition());
                    notifyItemRemoved(holder.getLayoutPosition());
                    notifyItemRangeChanged(holder.getLayoutPosition(), myGroup.size());
                }
                else if(options.equals("2"))
                {
                    Toast.makeText(mcontext, "You Joined a new group", Toast.LENGTH_SHORT).show();
                    myGroup.remove(holder.getLayoutPosition());
                    notifyItemRemoved(holder.getLayoutPosition());
                    notifyItemRangeChanged(holder.getLayoutPosition(), myGroup.size());
                }
                else
                {
                    Toast.makeText(mcontext, "Group enter", Toast.LENGTH_SHORT).show();
                    myGroup.remove(holder.getLayoutPosition());
                    notifyItemRemoved(holder.getLayoutPosition());
                    notifyItemRangeChanged(holder.getLayoutPosition(), myGroup.size());
                }
            }
            else
            {
                Toast.makeText(mcontext,"Server not responding, Try again later", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        };
    }


    @Override
    public int getItemCount() {
        return myGroup.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView groupName;
        private TextView groupDesc;
        private TextView owner;
        private Button groupBtn1;
        private Button groupBtn2;
        private ImageView newTag;
        private TextView userCount;
        private LinearLayout greenTag;
        private LinearLayout chatTag;
        private TextView chatText;




        Holder(@NonNull View itemView) {
            super(itemView);
            groupName = (TextView) itemView.findViewById(R.id.group_name);
            groupDesc = (TextView) itemView.findViewById(R.id.group_desc);
            owner = (TextView) itemView.findViewById(R.id.owner);
            groupBtn1 = (Button) itemView.findViewById(R.id.group_btn_1);
            groupBtn2 = (Button) itemView.findViewById(R.id.group_btn_2);
            newTag = (ImageView) itemView.findViewById(R.id.new_tag);
            userCount = (TextView) itemView.findViewById(R.id.user_count);
            greenTag = (LinearLayout) itemView.findViewById(R.id.green_tag);
            chatTag = (LinearLayout) itemView.findViewById(R.id.chat_tag);
            chatText = (TextView) itemView.findViewById(R.id.chat_text);
        }
    }
}
