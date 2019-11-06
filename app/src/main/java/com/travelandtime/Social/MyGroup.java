package com.travelandtime.Social;

class MyGroup {
    private String group_id,group_name,group_desc,user_imei,user_name,owner,types,date,users_count,grp_checksum;
    MyGroup(String group_id, String group_name, String group_desc, String user_imei, String user_name, String owner,String types,String date,String users_count,String grp_checksum) {
        this.group_id=group_id;
        this.group_name=group_name;
        this.group_desc=group_desc;
        this.user_imei=user_imei;
        this.user_name=user_name;
        this.owner=owner;
        this.types=types;
        this.date=date;
        this.users_count=users_count;
        this.grp_checksum=grp_checksum;
    }
    // Getter and Setter Method


    public String getGrp_checksum() {
        return grp_checksum;
    }

    public void setGrp_checksum(String grp_checksum) {
        this.grp_checksum = grp_checksum;
    }

    public String getUsers_count() {
        return users_count;
    }

    public void setUsers_count(String users_count) {
        this.users_count = users_count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    String getGroup_desc() {
        return group_desc;
    }

    public void setGroup_desc(String group_desc) {
        this.group_desc = group_desc;
    }

    public String getUser_imei() {
        return user_imei;
    }

    public void setUser_imei(String user_imei) {
        this.user_imei = user_imei;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
