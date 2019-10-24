package com.example.testwx.bean;

import lombok.Data;

@Data
public class AccessToken {
    private String accessToken;
    private Long expireTime;

    public AccessToken(String accessToken, String expireIn) {
        this.accessToken = accessToken;
        this.expireTime = System.currentTimeMillis()+Integer.parseInt(expireIn)*1000;
    }
    public boolean isExpire(){
        return System.currentTimeMillis()>expireTime;
    }



}
