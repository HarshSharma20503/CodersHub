package com.xoxoharsh.codershub.model;

public class Contest {
    private String title;
    private String platform;
    private String date;
    private String time;
    public Contest(){
    }

    public Contest(String title, String platform, String date, String time) {
        this.title = title;
        this.platform = platform;
        this.date = date;
        this.time = time;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
