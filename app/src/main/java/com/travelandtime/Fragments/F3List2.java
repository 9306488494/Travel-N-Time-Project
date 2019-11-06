package com.travelandtime.Fragments;

class F3List2 {
    private String sr,reg_date,country_name,from,too,current_status,image_url,file_url;
    F3List2(String sr,String reg_date,String country_name,String from,String too,String current_status,String image_url,String file_url) {
        this.sr=sr;
        this.reg_date=reg_date;
        this.country_name=country_name;
        this.from=from;
        this.too=too;
        this.current_status=current_status;
        this.image_url=image_url;
        this.file_url=file_url;
    }
    // Getter and Setter method


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

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getToo() {
        return too;
    }

    public void setToo(String too) {
        this.too = too;
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

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }
}
