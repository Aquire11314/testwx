package com.example.testwx.bean;

import com.example.testwx.bean.base.BaseEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.HashMap;

@XStreamAlias("xml")
@Data
public class TextMsg extends BaseEntity {
    @XStreamAlias("Content")
    private String content;

    public TextMsg(HashMap<String, String> map, String content) {
        super(map);
        this.setMsgType("text");
        this.content = content;
    }


}
