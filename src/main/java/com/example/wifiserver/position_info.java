package com.example.wifiserver;

public class position_info {
    private int floor;
    private int x_p;
    private int y_p;
    position_info(int floor, int x_p, int y_p){
        this.floor = floor;
        this.x_p = x_p;
        this.y_p = y_p;
    }
    position_info(){};

    public int getX_p() {
        return x_p;
    }

    public int getY_p() {
        return y_p;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setX_p(int x_p) {
        this.x_p = x_p;
    }

    public void setY_p(int y_p) {
        this.y_p = y_p;
    }
}
