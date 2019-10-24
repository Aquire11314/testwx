package com.example.testwx.bean;

import com.example.testwx.bean.base.BaseEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
@Data
@XStreamAlias("xml")
public class NewsMsg extends BaseEntity {
    @XStreamAlias("ArticleCount")
    private String articleCount;
    @XStreamAlias("Articles")
    private List<Article> articles;

    public NewsMsg(HashMap<String, String> map,  List<Article> articles) {
        super(map);
        this.setMsgType("news");
        this.articles = articles;
        this.articleCount = this.articles.size()+"";
    }
}
