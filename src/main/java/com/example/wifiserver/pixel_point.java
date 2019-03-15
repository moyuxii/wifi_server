package com.example.wifiserver;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class pixel_point {
    @Id
    private Integer id;
    private String position;
    private int x_p;
    private int y_p;
    void setId(Integer id){
        this.id =id;
    }
    void setPosition(String position){
        this.position = position;
    }
    void setX_p(int x_p){
        this.x_p = x_p;
    }
    void setY_p(int y_p){
        this.y_p = y_p;
    }
    Integer getId(){
        return this.id;
    }
    String getPosition(){
        return this.position;
    }
    int getX_p(){
        return  this.x_p;
    }
    int getY_p(){
        return this.y_p;
    }
}
