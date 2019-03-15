package com.example.wifiserver;

public class wifi_get {
    private String bssid;
    private int level;

    public wifi_get(String bssid, int level) {
        this.bssid = bssid;
        this.level =level;
    }
    public wifi_get(){}

    public void  setBssid(String bssid){
        this.bssid = bssid;
    }
    public void setLevel(int level){
        this.level = level;
    }

    public String getBssid() {
        return bssid;
    }

    public int getLevel() {
        return level;
    }
}
