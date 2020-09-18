package com.example.priyanktanna.pgfinderapp;

public class Feedback {

    String feedbackId,pg_Id,userid,feed_des,feed_rating;
    int R;

    public int getR() {
        return R;
    }

    public void setR(int r) {
        R = r;
    }

    public String getFeed_rating() {
        return feed_rating;
    }

    public void setFeed_rating(String feed_rating) {
        this.feed_rating = feed_rating;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getPg_Id() {
        return pg_Id;
    }

    public void setPg_Id(String pg_Id) {
        this.pg_Id = pg_Id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFeed_des() {
        return feed_des;
    }

    public void setFeed_des(String feed_des) {
        this.feed_des = feed_des;
    }
}
