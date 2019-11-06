package com.travelandtime.Fragments;

class FList1 {
    String sr,today_date,too,fromm,dept_date,status1,file_url,plane_pic,plane_name;
    FList1(String sr, String today_date, String too, String fromm, String dept_date, String status1, String file_url, String plane_pic, String plane_name) {
    this.sr=sr;
    this.today_date=today_date;
    this.too=too;
    this.fromm=fromm;
    this.dept_date=dept_date;
    this.status1=status1;
    this.file_url=file_url;
    this.plane_pic=plane_pic;
    this.plane_name=plane_name;
    }
    // Getter and Setter

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getToday_date() {
        return today_date;
    }

    public void setToday_date(String today_date) {
        this.today_date = today_date;
    }

    public String getToo() {
        return too;
    }

    public void setToo(String too) {
        this.too = too;
    }

    public String getFromm() {
        return fromm;
    }

    public void setFromm(String fromm) {
        this.fromm = fromm;
    }

    public String getDept_date() {
        return dept_date;
    }

    public void setDept_date(String dept_date) {
        this.dept_date = dept_date;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getPlane_pic() {
        return plane_pic;
    }

    public void setPlane_pic(String plane_pic) {
        this.plane_pic = plane_pic;
    }

    public String getPlane_name() {
        return plane_name;
    }

    public void setPlane_name(String plane_name) {
        this.plane_name = plane_name;
    }
}
