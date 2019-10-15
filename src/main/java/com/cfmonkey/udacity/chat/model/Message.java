package com.cfmonkey.udacity.chat.model;

import com.alibaba.fastjson.JSON;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * WebSocket message model
 */
@Valid
public class Message {
    @NotNull
    private String username;

    @NotNull
    private String msg;

    @NotNull
    private String type;
    
    private int onlineCount;

    public Message(){
    }

    public Message(String username, String msg, String type, int onlineCount){
        this.username = username;
        this.msg = msg;
        this.type = type;
        this.onlineCount = onlineCount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public static String strToJson(String username, String msg, String type, int onlineCount){
        return JSON.toJSONString( new Message(username, msg, type, onlineCount));
    }
}
