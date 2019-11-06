package com.travelandtime.GetterSetter;

public class Photo1 {
    String sr,pack_id,url;
    public Photo1(String sr, String pack_id, String url) {
        this.sr=sr;
        this.pack_id=pack_id;
        this.url=url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
