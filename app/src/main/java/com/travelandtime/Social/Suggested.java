package com.travelandtime.Social;

class Suggested {
    private String imei,name,my_pic,email,myIMEI,sr;
    Suggested(String imei, String name, String my_pic, String email, String myIMEI,String sr) {
        this.imei=imei;
        this.name=name;
        this.my_pic=my_pic;
        this.myIMEI=myIMEI;
        this.sr=sr;
    }
    // Getter and setter Method

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMy_pic() {
        return my_pic;
    }

    public void setMy_pic(String my_pic) {
        this.my_pic = my_pic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMyIMEI() {
        return myIMEI;
    }

    public void setMyIMEI(String myIMEI) {
        this.myIMEI = myIMEI;
    }
}
