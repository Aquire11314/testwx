package com.example.testwx.service;

import com.alibaba.fastjson.JSONObject;
import com.example.testwx.bean.*;
import com.example.testwx.bean.base.AbstractButton;
import com.example.testwx.util.HttpUtil;
import com.example.testwx.util.WxService;

import java.util.ArrayList;
import java.util.List;

public class CreateMenu {
    public static void create(){
        Button button=new Button();
        ClickButton clickButton=new ClickButton("点击","1");
        ViewButton viewButton=new ViewButton("百度","http://www.baidu.com");
        List<AbstractButton> buttons=new ArrayList<>();
        buttons.add(new ClickButton("点击1","31"));
        buttons.add(new PhotoOrAlbumButton("传图","32",null));
        MenuButton menuButton=new MenuButton("菜单",buttons);
        button.getButton().add(clickButton);
        button.getButton().add(viewButton);
        button.getButton().add(menuButton);
        String jsonString = JSONObject.toJSONString(button);
        System.out.println(jsonString);
        String url=" https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
        String result = HttpUtil.sendPost(url.replace("ACCESS_TOKEN", WxService.getAccessToken()), jsonString);
        System.out.println(result);
    }
}
