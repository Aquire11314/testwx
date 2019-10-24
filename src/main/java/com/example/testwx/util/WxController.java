package com.example.testwx.util;

import com.alibaba.fastjson.JSONObject;
import com.example.testwx.bean.AccessToken;
import com.example.testwx.bean.Article;
import com.example.testwx.bean.NewsMsg;
import com.example.testwx.bean.TextMsg;
import com.example.testwx.bean.base.BaseEntity;
import com.thoughtworks.xstream.XStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("wx")
public class WxController {
    //配置您申请的KEY
    public static final String APPKEY ="411784f5c5f20bf975325e4b54b1da7f";





    @GetMapping()
    public String config(HttpServletRequest request){
        /**
         * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
         * timestamp	时间戳
         * nonce	随机数
         * echostr	随机字符串
         */
        String signature= request.getParameter("signature");
        String timestamp= request.getParameter("timestamp");
        String nonce= request.getParameter("nonce");
        String echostr= request.getParameter("echostr");

        if(WxService.check(signature,timestamp,nonce,echostr)){
            return echostr;
        }else{
            return null;
        }
    }

    @PostMapping()
    public String getMsg(HttpServletRequest request) throws IOException {
        HashMap<String, String> map = WxService.xml2Map(request.getInputStream());
        BaseEntity msg=null;
        switch (map.get("MsgType")){
            case "text":
                msg=dealTextMsg(map);
                break;
            case "image":
                break;
            case "voice":
                break;
            case "event":
                msg=dealEvent(map);
                break;
             default:
                 break;
        }
        if(msg!=null){
            XStream xStream=new XStream();
            xStream.processAnnotations(TextMsg.class);
            xStream.processAnnotations(NewsMsg.class);
            String toXML = xStream.toXML(msg);
            System.out.println(toXML);
            return toXML;
        }
        return null;
    }

    @GetMapping("/code")
    public void getCode(String code){
        String url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        url=url.replace("APPID","wx01c67c8b346a671e").replace("SECRET","731e7fc9cbef1da766f5c021a8864f56").replace("CODE",code);
        String result = HttpUtil.sendGet(url);
        System.out.println(result);
        JSONObject jsonObject = JSONObject.parseObject(result);

        String access_token = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
        url="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        url=url.replace("ACCESS_TOKEN",access_token).replace("OPENID",openid);
        result = HttpUtil.sendGet(url);
        System.out.println(result);

    }
    private BaseEntity dealEvent(HashMap<String, String> map) {
        String event=map.get("Event");
        BaseEntity msg=null;
        switch (event){
            case "CLICK":
                msg=dealClick(map);
                break;

        }
        return msg;
    }

    private BaseEntity dealClick(HashMap<String, String> map) {
        switch (map.get("EventKey")){
            case "1":
                return new TextMsg(map,"点击事件");
            case "31":
                return new TextMsg(map,"菜单点击事件");
        }
        return null;
    }

    /**
     * 文本消息
     * @param map
     * @return
     */
    private BaseEntity dealTextMsg(HashMap<String, String> map) {
        String content = map.get("Content");
        if(content.equals("图文")){
            List<Article>articles=new ArrayList<>();
            Article article = new Article("123", "123", "http://mmbiz.qpic.cn/mmbiz_jpg/Jxs3Wth7dDKyG4GnvrDic0Z5H6licfhlP9hTH7jPMO1UXCVPHNyxyMkEicQtGkdQb6FZ24AVgG5KRSrH8vLR6JjsA/0", "http://www.baidu.com");
            articles.add(article);
            NewsMsg newsMsg = new NewsMsg(map, articles);
            System.out.println(newsMsg);
            return newsMsg;
        }

        if(content.equals("登录")){
           String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
           url=url.replace("APPID","wx01c67c8b346a671e").replace("REDIRECT_URI","http://testwxin.free.idcfengye.com/wx/code");
           TextMsg textMsg=new TextMsg(map,"点击<a href=\""+url+"\">登录</a>");
            System.out.println(textMsg);
            return textMsg;
        }

        String result = chat(content);
        TextMsg tm=new TextMsg(map,result);
        return tm;
    }

    /**
     * 机器人
     * @param content
     * @return
     */
    private String chat(String content) {
        String result =null;
        String url ="http://op.juhe.cn/iRobot/index";//请求接口地址
        Map params = new HashMap();//请求参数
        params.put("key",APPKEY);//您申请到的本接口专用的APPKEY
        params.put("info",content);//要发送给机器人的内容，不要超过30个字符
        params.put("dtype","");//返回的数据的格式，json或xml，默认为json
        params.put("loc","");//地点，如北京中关村
        params.put("lon","");//经度，东经116.234632（小数点后保留6位），需要写为116234632
        params.put("lat","");//纬度，北纬40.234632（小数点后保留6位），需要写为40234632
        params.put("userid","");//1~32位，此userid针对您自己的每一个用户，用于上下文的关联

        try {
            result =JuHeReboot.net(url, params, "GET");
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("error_code")==0){
                System.out.println(object.get("result"));
                String res = JSONObject.parseObject(object.getString("result")).getString("text");
                return res;
            }else{
                System.out.println(object.get("error_code")+":"+object.get("reason"));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
