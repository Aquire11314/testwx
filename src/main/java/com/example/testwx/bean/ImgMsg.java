package com.example.testwx.bean;

import com.example.testwx.bean.base.BaseEntity;

import java.util.HashMap;

public class ImgMsg extends BaseEntity {
    private String picUrl;
    private String mediaId;

    public ImgMsg(HashMap<String, String> map, String picUrl, String mediaId) {
        super(map);
        this.setMsgType("image");
        this.picUrl = picUrl;
        this.mediaId = mediaId;
    }
}
