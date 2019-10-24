package com.example.testwx.bean;

import com.example.testwx.bean.base.BaseEntity;

import java.util.HashMap;

public class VoiceMsg extends BaseEntity {
    private String mediaId;
    private String format;

    public VoiceMsg(HashMap<String, String> map, String mediaId, String format) {
        super(map);
        this.setMsgType("voice");
        this.mediaId = mediaId;
        this.format = format;
    }
}
