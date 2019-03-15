package com.example.wifiserver;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class wifi_info {
    @Id
    private
    Integer id;
    private String position_id;
    private String bssid;
    private float range;
    private int level;
    public void setId(Integer id){
        this.id = id;
    }
    public void setPosition_id(String position_id){
        this.position_id = position_id;
    }
    public void setBssid(String bssid){
        this.bssid = bssid;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public void setRange(float range){
        this.range = range;
    }
    public Integer getId(){
        return this.id;
    }
    public String getBssid(){
        return this.bssid;
    }
    public String getPosition_id(){
        return this.position_id;
    }
    public int getLevel(){
        return this.level;
    }
    public float getRange(){
        return this.range;
    }
}
