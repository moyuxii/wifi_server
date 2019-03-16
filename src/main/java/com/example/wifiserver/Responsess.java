package com.example.wifiserver;

public class Responsess {
    public String code;
    public String message;
    public Responsess(String code,String message){
        this.code = code;
        this.message = message;
    }
    public Responsess(){};

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
