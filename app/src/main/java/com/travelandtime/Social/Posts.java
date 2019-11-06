package com.travelandtime.Social;

class Posts {
    private String sr,post_like,post_comments,imei,email,date1,name,post_txt,post_img,shared,shared_name,like,dp,total;
    Posts(String sr, String post_like,String post_comments, String imei, String email, String date1, String name, String post_txt, String post_img,String shared, String shared_name,String like,String dp,String total) {
        this.sr=sr;
        this.post_like=post_like;
        this.post_comments=post_comments;
        this.imei=imei;
        this.email=email;
        this.date1=date1;
        this.name=name;
        this.post_txt=post_txt;
        this.post_img=post_img;
        this.shared=shared;
        this.shared_name=shared_name;
        this.like=like;
        this.dp=dp;
        this.total=total;
    }

    // create getter and setter method

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPost_comments() {
        return post_comments;
    }

    public void setPost_comments(String post_comments) {
        this.post_comments = post_comments;
    }

    public String getPost_like() {
        return post_like;
    }

    public void setPost_like(String post_like) {
        this.post_like = post_like;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    String getShared() {
        return shared;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }

    public String getShared_name() {
        return shared_name;
    }

    public void setShared_name(String shared_name) {
        this.shared_name = shared_name;
    }

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost_txt() {
        return post_txt;
    }

    public void setPost_txt(String post_txt) {
        this.post_txt = post_txt;
    }

    public String getPost_img() {
        return post_img;
    }

    public void setPost_img(String post_img) {
        this.post_img = post_img;
    }
}
