package com.example.uber;

public class Vehicle {
    public String driverid;
    public String vehicleid;
    public String name;


    public Vehicle() {

    }

    public Vehicle(String driverid, String vehicleid, String name) {
        this.driverid = driverid;
        this.vehicleid = vehicleid;
        this.name = name;
    }

    public String getDriverid() {
        return driverid;
    }

    public void setDriverid(String driverid) {
        this.driverid = driverid;
    }

    public String getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(String vehicleid) {
        this.vehicleid = vehicleid;
    }

    public String getName2() {
        return name;
    }

    public void setName2(String name) {
        this.name = name;
    }
}
