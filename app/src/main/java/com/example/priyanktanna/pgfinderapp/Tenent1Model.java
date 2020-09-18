package com.example.priyanktanna.pgfinderapp;

import java.util.ArrayList;

public class Tenent1Model {

    private String pgName;
    private String pgAdd;
    private String pgFor;
    private String singleSpin;
    private String multiSpin;
    private String avgPerSpi;
    private String multiRent;
    private String singleRent;
    private String uid;
    private String tId;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    ArrayList<String> facilityList;
    ArrayList<String> url;// = new ArrayList<>();
    public ArrayList<String> getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(ArrayList<String> facilityList) {
        this.facilityList = facilityList;
    }
    public ArrayList<String> getUrl() {
        return url;
    }

    public void setUrl(ArrayList<String> url) {
        this.url = url;
    }


    public String getPgName() {
        return pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgAdd() {
        return pgAdd;
    }

    public void setPgAdd(String pgAdd) {
        this.pgAdd = pgAdd;
    }

    public String getPgFor() {
        return pgFor;
    }

    public void setPgFor(String pgFor) {
        this.pgFor = pgFor;
    }

    public String getSingleSpin() {
        return singleSpin;
    }

    public void setSingleSpin(String singleSpin) {
        this.singleSpin = singleSpin;
    }

    public String getMultiSpin() {
        return multiSpin;
    }

    public void setMultiSpin(String multiSpin) {
        this.multiSpin = multiSpin;
    }

    public String getAvgPerSpi() {
        return avgPerSpi;
    }

    public void setAvgPerSpi(String avgPerSpi) {
        this.avgPerSpi = avgPerSpi;
    }

    public String getMultiRent() {
        return multiRent;
    }

    public void setMultiRent(String multiRent) {
        this.multiRent = multiRent;
    }

    public String getSingleRent() {
        return singleRent;
    }

    public void setSingleRent(String singleRent) {
        this.singleRent = singleRent;
    }


}
