package com.travelandtime.Social;

class Comment2 {
    String sr,user_imei,video_id,user_name,comment_msg,profile_pic;
    Comment2(String sr, String user_imei, String video_id, String user_name, String comment_msg, String profile_pic) {
        this.sr=sr;
        this.user_imei=user_imei;
        this.video_id=video_id;
        this.user_name=user_name;
        this.comment_msg=comment_msg;
        this.profile_pic=profile_pic;
    }
    // getter and setter method

    public String getUser_imei() {
        return user_imei;
    }

    public void setUser_imei(String user_imei) {
        this.user_imei = user_imei;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getComment_msg() {
        return comment_msg;
    }

    public void setComment_msg(String comment_msg) {
        this.comment_msg = comment_msg;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
