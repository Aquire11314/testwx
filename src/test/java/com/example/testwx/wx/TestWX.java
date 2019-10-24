package com.example.testwx.wx;


import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.ocr.AipOcr;
import com.example.testwx.bean.*;
import com.example.testwx.bean.base.AbstractButton;
import com.example.testwx.bean.base.BaseEntity;
import com.example.testwx.util.HttpUtil;
import com.example.testwx.util.WxController;
import com.example.testwx.util.WxService;
import com.thoughtworks.xstream.XStream;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestWX {
    //设置APPID/AK/SK  百度ai
    public static final String APP_ID = "17589257";
    public static final String API_KEY = "F0AG9sYVzFDQMIQFN7zSOZVm";
    public static final String SECRET_KEY = "4y2FfIy2O6fK2MjdDOplGCG00jUnGkAH";


    /**
     * 微信设置行业
     */
    @Test
    public void set(){
        String url="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+WxService.getAccessToken();
        String data="{\n" +
                "    \"industry_id1\":\"1\",\n" +
                "    \"industry_id2\":\"4\"\n" +
                "}";
        String res = HttpUtil.sendPost(url, data);
        System.out.println(res);
    }
    @Test
    public void get(){
        String url="https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token="+WxService.getAccessToken();

        String res = HttpUtil.sendGet(url);
        System.out.println(res);
    }

    @Test
    public void send(){
        String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+WxService.getAccessToken();
        String data="{\n" +
                "   \"touser\":\"oUajqwM5_ITZPRbYoymdGxbN0z_8\",\n" +
                "   \"template_id\":\"AVmzMh2tzDJ7zxJhqbggUSkU7dFSDC2vEJIGw-U8Wp0\",\n" +
                "   \"data\":{\n" +
                "           \"first\": {\n" +
                "               \"value\":\"恭喜你购买成功！\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           },\n" +
                "           \"keyword1\":{\n" +
                "               \"value\":\"巧克力\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           },\n" +
                "           \"keyword2\": {\n" +
                "               \"value\":\"39.8元\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           },\n" +
                "           \"keyword3\": {\n" +
                "               \"value\":\"2014年9月22日\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           },\n" +
                "           \"remark\":{\n" +
                "               \"value\":\"欢迎再次购买！\",\n" +
                "               \"color\":\"#173177\"\n" +
                "           }\n" +
                "   }\n" +
                "}";
        String res = HttpUtil.sendPost(url,data);
        System.out.println(res);
    }



    @Test
    public void sample() {

        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);


        // 通用文字识别, 图片参数为远程url图片
        String url="http://mmbiz.qpic.cn/mmbiz_jpg/Jxs3Wth7dDJ7l8SczIUhRuaR1ZW0KjsTYLGxSOLdNVIWQToQfgNyNDRYNDc4bdicQdhiaGWsK0hE9tnsBBTWqlJw/0";
        org.json.JSONObject res = client.basicGeneralUrl(url, new HashMap<String, String>());
        JSONObject jsonObject=JSONObject.parseObject(res.toString());
        System.out.println(res.toString(2));

    }


    @Test
    public  void testButton(){
        Button button=new Button();
        ClickButton clickButton=new ClickButton("点击","1");
        ViewButton viewButton=new ViewButton("百度","http://www.baidu.com");
        List<AbstractButton>buttons=new ArrayList<>();
        buttons.add(new ClickButton("点击1","31"));
        buttons.add(new PhotoOrAlbumButton("传图","32",null));
        MenuButton menuButton=new MenuButton("菜单",buttons);
        button.getButton().add(clickButton);
        button.getButton().add(viewButton);
        button.getButton().add(menuButton);
        String jsonString = JSONObject.toJSONString(button);
        System.out.println(jsonString);
        System.out.println(viewButton);
    }

    @Test
    public void testMsg() {
        HashMap map=new HashMap();
        map.put("FromUserName","form");
        map.put("ToUserName","to");
        map.put("MediaType","type");
        TextMsg msg=new TextMsg(map,"nina");
        System.out.println(msg);

        XStream xStream=new XStream();
        xStream.processAnnotations(TextMsg.class);
        String xml = xStream.toXML(msg);
        System.out.println(xml);

    }

    @Test
    public void testToken(){
        System.out.println(WxService.getAccessToken());
        System.out.println(WxService.getAccessToken());
    }

    @Test
    public void testUpload(){
        String path="F:\\yang\\wx\\src\\main\\resources\\1.jpg";
        String image = WxService.upload(path, "image");
        System.out.println(image);
    }

    @Test
    public void testQr(){
        String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+WxService.getAccessToken();
        String data="{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"test\"}}}";
        String res = HttpUtil.sendPost(url,data);
        System.out.println(res);

        //gQFF8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAySWlEVUJYaEVmRzAxS0tpVDF0Y1UAAgQumK5dAwSAOgkA
    }
}
