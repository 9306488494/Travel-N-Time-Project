package com.travelandtime.GetterSetter;

public class Reviews {
    String sr,pack_id,user_name,msgg,time,user_pic,user_rating;
    public Reviews(String sr, String pack_id, String user_name, String msgg, String time,String user_pic,String user_rating) {
        this.sr=sr;
        this.pack_id=pack_id;
        this.user_name=user_name;
        this.msgg=msgg;
        this.time=time;
        this.user_pic=user_pic;
        this.user_rating=user_rating;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getPack_id() {
        return pack_id;
    }

    public void setPack_id(String pack_id) {
        this.pack_id = pack_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMsgg() {
        return msgg;
    }

    public void setMsgg(String msgg) {
        this.msgg = msgg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public void setUser_pic(String user_pic) {
        this.user_pic = user_pic;
    }

    public String getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(String user_rating) {
        this.user_rating = user_rating;
    }
}
