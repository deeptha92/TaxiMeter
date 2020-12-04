package com.example.uber;

public class User {
    public String mobile;
    public String email;
    public Integer index;
    public String totalearn;
    public String dname;

    public User() {

    }


    public User(String mobile, String email, Integer index, String totalearn, String dname) {
        this.mobile = mobile;
        this.email = email;
        this.index = index;
        this.totalearn = totalearn;
        this.dname = dname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }


    public String getTotalearn() {
        return totalearn;
    }

    public void setTotalearn(String totalearn) {
        this.totalearn = totalearn;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
}
