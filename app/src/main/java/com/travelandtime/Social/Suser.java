package com.travelandtime.Social;

class Suser {
    String imei,name,state,my_pic,frnd;
    Suser(String imei, String name, String state, String my_pic, String frnd) {
        this.imei=imei;
        this.name=name;
        this.state=state;
        this.my_pic=my_pic;
        this.frnd=frnd;
    }
    // Getter and setter method


    public String getFrnd() {
        return frnd;
    }

    public void setFrnd(String frnd) {
        this.frnd = frnd;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMy_pic() {
        return my_pic;
    }

    public void setMy_pic(String my_pic) {
        this.my_pic = my_pic;
    }
}
