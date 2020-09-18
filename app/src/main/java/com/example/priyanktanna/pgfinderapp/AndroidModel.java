package com.example.priyanktanna.pgfinderapp;

public class AndroidModel {

    String strpgid;
    int imgid;


    public AndroidModel(String strpgid, int imgid) {

        this.strpgid = strpgid;
        this.imgid= imgid;
    }

    public String getStrpgid() {
        return strpgid;
    }

    public void setStrpgid(String strpgid) {
        this.strpgid = strpgid;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
