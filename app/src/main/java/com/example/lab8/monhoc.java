package com.example.lab8;

public class monhoc {
    private  int id;
    private String mamh,tenmh;
    private int sotiet;

    public monhoc() {
    }

    public monhoc(int id, String mamh, String tenmh, int sotiet) {
        this.id = id;
        this.mamh = mamh;
        this.tenmh = tenmh;
        this.sotiet = sotiet;
    }

    public monhoc(String mamh, String tenmh, int sotiet) {
        this.mamh = mamh;
        this.tenmh = tenmh;
        this.sotiet = sotiet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMamh() {
        return mamh;
    }

    public void setMamh(String mamh) {
        this.mamh = mamh;
    }

    public String getTenmh() {
        return tenmh;
    }

    public void setTenmh(String tenmh) {
        this.tenmh = tenmh;
    }

    public int getSotiet() {
        return sotiet;
    }

    public void setSotiet(int sotiet) {
        this.sotiet = sotiet;
    }
}
