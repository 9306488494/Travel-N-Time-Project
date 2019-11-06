package com.travelandtime.Social;

class FriendList {
    String name,imei,pic,image;
    FriendList(String name, String imei, String pic,String image) {
        this.name=name;
        this.imei=imei;
        this.pic=pic;
        this.image=image;
    }
    // getter and setter method


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
