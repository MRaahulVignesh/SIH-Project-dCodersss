package com.example.agri.pojos;

public class Farmers {
    String Id;
    String AadhaarNo, MobNo, Email;

    public Farmers() {
    }

    public Farmers(String id, String aadhaarNo, String mobNo, String email) {
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

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
