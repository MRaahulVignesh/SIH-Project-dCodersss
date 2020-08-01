package com.example.agri.pojos;

import java.util.List;

public class Farmers {
    List<Crops> myCrops;
    String Id;
    String AadhaarNo, MobNo, Email;

    public Farmers() {
    }

    public Farmers(List<Crops> myCrops, String id, String aadhaarNo, String mobNo, String email) {
        this.myCrops = myCrops;
        Id = id;
        AadhaarNo = aadhaarNo;
        MobNo = mobNo;
        Email = email;
    }

    public String getAadhaarNo() {
        return AadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        AadhaarNo = aadhaarNo;
    }

    public String getMobNo() {
        return MobNo;
    }

    public void setMobNo(String mobNo) {
        MobNo = mobNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public List<Crops> getMyCrops() {
        return myCrops;
    }

    public void setMyCrops(List<Crops> myCrops) {
        this.myCrops = myCrops;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
