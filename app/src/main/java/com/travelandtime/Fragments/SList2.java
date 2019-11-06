package com.travelandtime.Fragments;

class SList2 {
    private String sr,reg_date,hotel_name,room_name,check_in,current_status,image_url,idd,file_url;
    SList2(String sr, String reg_date, String hotel_name, String room_name, String check_in, String current_status, String image_url, String idd,String file_url) {
        this.sr=sr;
        this.reg_date=reg_date;
        this.hotel_name=hotel_name;
        this.room_name=room_name;
        this.check_in=check_in;
        this.current_status=current_status;
        this.image_url=image_url;
        this.idd=idd;
        this.file_url=file_url;
    }

    // getter and setter method

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getReg_date() {
        return reg_date;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
