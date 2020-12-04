package com.example.uber;

public class History {

    public String driverid;
    public String tripstartdate;
    public String startaddress;
    public String endaddress;
    public String tripid;
    public String tripstarttime;
    public String tripendtime;
    public String triptotalcost;
    public String tripcommission;


    public History() {
    }

    public History(String driverid, String tripstartdate, String startaddress, String endaddress, String tripid, String tripstarttime, String tripendtime, String triptotalcost, String tripcommission) {

        this.driverid = driverid;
        this.tripstartdate = tripstartdate;
        this.startaddress = startaddress;
        this.endaddress = endaddress;
        this.tripid = tripid;
        this.triptotalcost = triptotalcost;
        this.tripcommission = tripcommission;
        this.tripstarttime = tripstarttime;
        this.tripendtime = tripendtime;
    }


    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }


    public String getStartaddress() {
        return startaddress;
    }

    public void setStartaddress(String startaddress) {
        this.startaddress = startaddress;
    }

    public String getEndaddress() {
        return endaddress;
    }

    public void setEndaddress(String endaddress) {
        this.endaddress = endaddress;
    }

    public String getTripid() {
        return tripid;
    }

    public void setTripid(String tripid) {
        this.tripid = tripid;
    }

    public String getTriptotalcost() {
        return triptotalcost;
    }

    public void setTriptotalcost(String triptotalcost) {
        this.triptotalcost = triptotalcost;
    }

    public String getTripcommission() {
        return tripcommission;
    }

    public void setTripcommission(String tripcommission) {
        this.tripcommission = tripcommission;
    }

    public String getTripstartdate() {
        return tripstartdate;
    }

    public void setTripstartdate(String tripstartdate) {
        this.tripstartdate = tripstartdate;
    }

    public String getTripstarttime() {
        return tripstarttime;
    }

    public void setTripstarttime(String tripstarttime) {
        this.tripstarttime = tripstarttime;
    }

    public String getTripendtime() {
        return tripendtime;
    }

    public void setTripendtime(String tripendtime) {
        this.tripendtime = tripendtime;
    }
}
