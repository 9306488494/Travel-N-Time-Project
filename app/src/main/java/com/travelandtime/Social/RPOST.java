package com.travelandtime.Social;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.travelandtime.Configration.Config;
import com.travelandtime.R;
import com.travelandtime.Utils.AppStatus;
import com.travelandtime.Utils.GetPresistenceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

class RPOST extends RecyclerView.Adapter<RPOST.Holder> {
    private ArrayList<Posts> posts;
    private ArrayList<Comment2>comments;
    private Context mcontext;
    private RequestQueue queue;
    private ProgressDialog pd;


    RPOST(Context mcontext, ArrayList<Posts> posts) {
        this.mcontext=mcontext;
        this.posts=posts;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view= LayoutInflater.from(mcontext).inflate(R.layout.my_posts,viewGroup,false);
        return new Holder(view);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull final RPOST.Holder holder, @SuppressLint("RecyclerView") final int i) {


        // paggination start
      /*  String layPos=String.valueOf(posts.get(i).getSr());
        String arraySizee=String.valueOf(posts.size());
        //String newPage=String.valueOf(Integer.parseInt(layPos)+Integer.parseInt(arraySizee));
        Toast.makeText(mcontext,layPos, Toast.LENGTH_SHORT).show();*/

        /*if(layPos.equals(String.valueOf(Integer.parseInt(arraySizee)-1)))
        {
            if (AppStatus.getInstance(mcontext).isOnline()) {
                if(arraySizee.equals(String.valueOf(Integer.parseInt(posts.get(i).getTotal()))))
                {
                    // max fetch value ends on ASC first sr which match to imei
                }
                else {
                    Community.loadPost(mcontext,String.valueOf(Integer.parseInt(layPos)),posts.get(i).getSr());
                }

            }
        }*/


        comments=new ArrayList<>();
        pd=new ProgressDialog(mcontext);
        pd.setTitle("Processing...");
        pd.setMessage("Please wait");
    //set parameters
        // post menu show only on my post not at all
        if(posts.get(i).getImei().equals(GetPresistenceData.getMyIMEI(mcontext)))
        {
            holder.postMenu.setVisibility(View.VISIBLE);
        }


        if(posts.get(i).getPost_img().equals("NA"))
        {
            holder.postImg.setVisibility(View.GONE);
        }
        else {

            Picasso.get().load(posts.get(i).getPost_img()).into(holder.postImg);
        }
        if(posts.get(i).getShared().equals("0"))
        {
            holder.userName.setText(posts.get(i).getName());
        }
        else
        {
            holder.userName.setText(posts.get(i).getName()+" Shared post of "+posts.get(i).getShared_name());
        }
        // if total likes count is more than zero then make visible views and put total counts
        if(posts.get(i).getPost_like().equals("0")) {

        }
        else
        {
            holder.likeCount.setText(posts.get(i).getPost_like());
            holder.likeCount.setBackgroundResource(R.drawable.green_double_rounded);
        }
        // if total comments count is more than zero then make visible no of comments and put total comments counts
        if(posts.get(i).getPost_comments().equals("0")) {

        }
        else
        {
            holder.cmtCount.setText(posts.get(i).getPost_comments());
            holder.cmtCount.setBackgroundResource(R.drawable.green_double_rounded);
        }
        // set other parameters
        String user_img = posts.get(i).getDp();
        Picasso.get().load(user_img).into(holder.userProfilePic);
        holder.postText.setText(posts.get(i).getPost_txt());
        holder.date.setText(posts.get(i).getDate1());



        // set red color on like button
        if(posts.get(i).getLike().equals("1"))
        {
            holder.like.setTextColor(ContextCompat.getColor(mcontext, R.color.drawable_red));
        }

        // click on image and show it on a big
        holder.postImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,BigPic.class);
                intent.putExtra("image",posts.get(i).getPost_img());
                intent.putExtra("type","1");
                intent.putExtra("text","NA");
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });
        // click on shared
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GetPresistenceData.getMyIMEI(mcontext).equals(posts.get(i).getImei()))
                {
                    Toast.makeText(mcontext, "You cant shared your post || Share other's post", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(mcontext, "Sharing...", Toast.LENGTH_SHORT).show();
                    Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
                    Network network1 = new BasicNetwork(new HurlStack());

                    queue = new RequestQueue(cache1, network1);
                    queue.start();
                    StringRequest request = new StringRequest(Request.Method.POST, Config.share_post, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response1) {
                            parseData1(response1);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(mcontext,"Server not responding", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        public Map<String, String> getParams() {
                            Map<String, String> mydata = new HashMap<>();
                            mydata.put("imei", GetPresistenceData.getMyIMEI(mcontext));
                            mydata.put("email", GetPresistenceData.getMetaEmail(mcontext));
                            mydata.put("name", GetPresistenceData.getMetaName(mcontext));
                            mydata.put("post_txt", posts.get(i).getPost_txt());
                            mydata.put("post_img", posts.get(i).getPost_img());
                            mydata.put("shared", "1");
                            mydata.put("shared_name", posts.get(i).getName());
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
        // click on post menu image to openn edit and delete posts
        holder.postMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(mcontext, holder.postMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if(R.id.edit==item.getItemId())
                        {
                            Intent intent=new Intent(mcontext,BigPic.class);
                            intent.putExtra("image",posts.get(i).getPost_img());
                            intent.putExtra("type","2");
                            intent.putExtra("text",posts.get(i).getPost_txt());
                            mcontext.startActivity(intent);
                        }
                        else if(R.id.delete==item.getItemId())
                        {
                            if(GetPresistenceData.getMyIMEI(mcontext).equals(posts.get(i).getImei())) {

                                deletePost(posts.get(i).getSr(), holder);
                            }
                            else
                            {
                                Toast.makeText(mcontext,"You cant delete | Its not your post", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        // click on like button
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(posts.get(i).getLike().equals("1"))
                {
                    Toast.makeText(mcontext, "You already liked", Toast.LENGTH_SHORT).show();
                }
                else {
                    likePost(GetPresistenceData.getMyIMEI(mcontext), posts.get(i).getSr(), holder,i);
                }
            }
        });
        // click on comment box and open comment rec and editbox for saving comment
        holder.commentBoxLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(holder.commentLay.getVisibility()==View.VISIBLE)
               {
                   holder.commentLay.setVisibility(View.GONE);
                   holder.recCmt.setVisibility(View.GONE);
                   holder.commentEditboxLay.setVisibility(View.GONE);
                   holder.commentsTxt.setVisibility(View.GONE);


               }
               else {
                   holder.commentLay.setVisibility(View.VISIBLE);
                   holder.recCmt.setVisibility(View.VISIBLE);
                   holder.commentEditboxLay.setVisibility(View.VISIBLE);
                   holder.commentsTxt.setVisibility(View.VISIBLE);
                   // load comments
                   String imei=GetPresistenceData.getMyIMEI(mcontext);
                   String video_id=posts.get(i).getSr();
                   comments.clear();
                   loadComments(imei,video_id,holder);
               }
            }
        });
        // click to save comments
        holder.saveCmtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // validate text box and save comments with videoid and imei
                String comment_txt = holder.enterComment.getText().toString();
                if (comment_txt.length() < 2) {
                    Toast.makeText(mcontext, "Required minimum 2 words", Toast.LENGTH_SHORT).show();
                    holder.enterComment.requestFocus();
                } else if (comment_txt.length() > 100) {

                    Toast.makeText(mcontext, "Required maximum 100 words", Toast.LENGTH_SHORT).show();
                    holder.enterComment.requestFocus();
                }
                else
                {
                    String imei=GetPresistenceData.getMyIMEI(mcontext);
                    String video_id=posts.get(i).getSr();
                    String User_name=GetPresistenceData.getMetaName(mcontext);
                    
                    saveComments(imei,video_id,User_name,holder,comment_txt,i);


                }
            }
        });
        // click on username text and open profile
        holder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(mcontext,BigPic.class);
                intent.putExtra("image","NA");
                intent.putExtra("text",posts.get(i).getImei());
                intent.putExtra("type","3");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });
        holder.userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,BigPic.class);
                intent.putExtra("image","NA");
                intent.putExtra("text",posts.get(i).getImei());
                intent.putExtra("type","3");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mcontext.startActivity(intent);
            }
        });




    }  // closer

    private void loadComments(final String imei, final String video_id, final Holder holder) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue = new RequestQueue(cache1, network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.show_comments, new Response.Listener<String>() {
            @Override
            public void onResponse(String response6) {

                parseData6(response6,holder);
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
                mydata.put("video_id", video_id);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData6(String response6, Holder holder) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response6);
            String Status=jsonObject.getString("status");
            JSONArray jsonArray=jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String sr = jsonObject1.getString("sr");
                String user_imei = jsonObject1.getString("imei");
                String video_id = jsonObject1.getString("video_id");
                String user_name = jsonObject1.getString("user_name");
                String comment_msg = jsonObject1.getString("comment_msg");
                String profile_pic = jsonObject1.getString("profile_pic");


                Comment2 comment2 = new Comment2(sr,user_imei,video_id,user_name,comment_msg,profile_pic);
                comments.add(comment2);

                CmtRec cmtRec = new CmtRec(mcontext, comments);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(mcontext);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                holder.recCmt.setLayoutManager(layoutManager);
                holder.recCmt.setHasFixedSize(true);
                holder.recCmt.setAdapter(cmtRec);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    private void saveComments(final String imei, final String video_id, final String user_name, final Holder holder, final String comment_txt, final int i) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.save_comment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response5) {
                parseData5(response5,holder,video_id,i);
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
                mydata.put("video_id",video_id);
                mydata.put("user_name",user_name);
                mydata.put("comment_msg",comment_txt);
                mydata.put("profile_pic",GetPresistenceData.getMetaPic(mcontext));
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData5(String response5, Holder holder, String video_id, int i) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response5);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {
                Toast.makeText(mcontext, "Comment Saved", Toast.LENGTH_SHORT).show();
                holder.enterComment.setText("");
                String imei=GetPresistenceData.getMyIMEI(mcontext);
                comments.clear();
                loadComments(imei,video_id,holder);
                saveNotification(posts.get(i).getImei(),GetPresistenceData.getMetaName(mcontext),", Comment on a your post ","1");
            }
            else{
                Toast.makeText(mcontext, "Network Issue | Try again", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    private void likePost(final String myIMEI, final String sr, final Holder holder, final int i) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.save_like, new Response.Listener<String>() {
            @Override
            public void onResponse(String response4) {
                parseData4(response4,holder,i);
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
                mydata.put("imei",myIMEI);
                mydata.put("sr",sr);
             
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData4(String response4, Holder holder, int i) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response4);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {
                Toast.makeText(mcontext, "You Liked it", Toast.LENGTH_SHORT).show();
                holder.like.setTextColor(ContextCompat.getColor(mcontext, R.color.drawable_red));
                // 1 for save 2 for open 3 for delete 
                saveNotification(posts.get(i).getImei(),GetPresistenceData.getMetaName(mcontext),", Liked your post","1");
            }
            else if(Status.equals("2")) {

                Toast.makeText(mcontext, "Already Liked", Toast.LENGTH_SHORT).show();
                holder.like.setTextColor(ContextCompat.getColor(mcontext, R.color.drawable_red));
            }
            else{
                pd.dismiss();
                Toast.makeText(mcontext, "Network Issue | Try again", Toast.LENGTH_SHORT).show();
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



    private void deletePost(final String sr, final Holder holder) {
        Cache cache1 = new DiskBasedCache(mcontext.getCacheDir(), 1024 * 1024);
        Network network1 = new BasicNetwork(new HurlStack());

        queue=new RequestQueue(cache1,network1);
        queue.start();
        StringRequest request = new StringRequest(Request.Method.POST, Config.delete_posts, new Response.Listener<String>() {
            @Override
            public void onResponse(String response3) {
                parseData3(response3,holder);
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
                mydata.put("sr",sr);
                return mydata;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        queue.add(request);
    }

    private void parseData3(String response3, Holder holder) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response3);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {
                posts.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
                notifyItemRangeChanged(holder.getLayoutPosition(), posts.size());
                holder.layout.setVisibility(View.GONE);
            }
            else{
                Toast.makeText(mcontext, "Network Issue", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    private void parseData1(String response1) {
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(response1);
            String Status=jsonObject.getString("status");
            if(Status.equals("1")) {
                pd.dismiss();
                Toast.makeText(mcontext, "Updated Successfully", Toast.LENGTH_SHORT).show();
            }
            else{
                pd.dismiss();
                Toast.makeText(mcontext, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        };
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }


    class Holder extends RecyclerView.ViewHolder {
        private CircleImageView userProfilePic;
        private TextView userName;
        private TextView date;
        private TextView postText;
        private PhotoView postImg;
        private TextView like;
        private TextView comment;
        private TextView share;
        private ImageView postMenu;
        private FrameLayout layout;
        private LinearLayout commentLay;
        private RecyclerView recCmt;
        private LinearLayout commentEditboxLay;
        private EditText enterComment;
        private Button saveCmtBtn;
        private TextView commentsTxt;
        private TextView likeCount;
        private TextView cmtCount;
        private TextView shareCount;
        private LinearLayout commentBoxLay;

        Holder(@NonNull View itemView) {
            super(itemView);
            userProfilePic = (CircleImageView) itemView.findViewById(R.id.user_profile_pic);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            date = (TextView) itemView.findViewById(R.id.date);
            postText = (TextView) itemView.findViewById(R.id.post_text);
            postImg = (PhotoView) itemView.findViewById(R.id.post_img);
            like = (TextView) itemView.findViewById(R.id.like);
            comment = (TextView) itemView.findViewById(R.id.comment);
            share = (TextView) itemView.findViewById(R.id.share);
            postMenu = (ImageView) itemView.findViewById(R.id.post_menu);
            layout = (FrameLayout) itemView.findViewById(R.id.layout);
            commentLay = (LinearLayout) itemView.findViewById(R.id.commentLay);
            recCmt = (RecyclerView) itemView.findViewById(R.id.recCmt);
            commentEditboxLay = (LinearLayout) itemView.findViewById(R.id.comment_editbox_lay);
            enterComment = (EditText) itemView.findViewById(R.id.enterComment);
            saveCmtBtn = (Button) itemView.findViewById(R.id.save_cmt_btn);
            commentsTxt = (TextView) itemView.findViewById(R.id.comments_txt);
            likeCount = (TextView) itemView.findViewById(R.id.like_count);
            cmtCount = (TextView) itemView.findViewById(R.id.cmt_count);
            shareCount = (TextView) itemView.findViewById(R.id.share_count);
            commentBoxLay = (LinearLayout) itemView.findViewById(R.id.comment_box_lay);

        }
    }
}
