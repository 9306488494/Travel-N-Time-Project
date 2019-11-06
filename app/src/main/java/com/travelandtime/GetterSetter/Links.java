package com.travelandtime.GetterSetter;

public class Links {
    String sr,link,new_link,total_click;
    public Links(String sr, String link, String new_link, String total_click) {
        this.sr=sr;
        this.link=link;
        this.new_link=new_link;
        this.total_click=total_click;

    }
    //Getter and setter method

    public String getSr() {
        return sr;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNew_link() {
        return new_link;
    }

    public void setNew_link(String new_link) {
        this.new_link = new_link;
    }

    public String getTotal_click() {
        return total_click;
    }

    public void setTotal_click(String total_click) {
        this.total_click = total_click;
    }
}
