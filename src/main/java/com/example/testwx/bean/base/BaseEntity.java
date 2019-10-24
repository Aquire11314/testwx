package com.example.testwx.bean.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

@Data
public class BaseEntity implements Serializable {
    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("FromUserName")
    private String fromUserName;
    @XStreamAlias("MsgType")
    private String msgType;
    @XStreamAlias("CreateTime")
    private String createTime;

    public BaseEntity(HashMap<String,String>map) {
        this.toUserName=map.get("FromUserName");
        this.fromUserName=map.get("ToUserName");
        this.createTime = System.currentTimeMillis()/1000+"";
    }

}
