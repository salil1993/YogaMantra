package com.deftminds.coronavirusapp.model;

public class Yoga {
    private String pose;
    private String time;
    private String url;
    private String pose_desc;

    public Yoga() {
    }

    public Yoga(String pose, String time, String url) {
        this.pose = pose;
        this.time = time;
        this.url = url;
    }

    public String getPose_desc() {
        return pose_desc;
    }

    public void setPose_desc(String pose_desc) {
        this.pose_desc = pose_desc;
    }

    public String getPose() {
        return pose;
    }

    public void setPose(String pose) {
        this.pose = pose;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
