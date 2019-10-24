package com.example.testwx.util;

public class TemplateManager {
    /**
     * 设置行业
     */

    public void set(){
        String url="https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+WxService.getAccessToken();
        String data="{\n" +
                "    \"industry_id1\":\"1\",\n" +
                "    \"industry_id2\":\"4\"\n" +
                "}";
        String res = HttpUtil.sendPost(url, data);
        System.out.println(res);
    }

    /**
     * 获取行业信息
     */
    public void get(){
        String url="https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token="+WxService.getAccessToken();

        String res = HttpUtil.sendGet(url);
        System.out.println(res);
    }

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


}
