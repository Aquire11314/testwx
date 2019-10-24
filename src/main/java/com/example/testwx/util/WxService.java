package com.example.testwx.util;

import com.alibaba.fastjson.JSONObject;
import com.example.testwx.bean.AccessToken;
import jdk.nashorn.internal.parser.Token;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WxService {
    private static final String  TOKEN="123456";
    public static final String GET_TOKEN_URL =" https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static final String APPID="wx01c67c8b346a671e";
    public static final String APPSECRET="731e7fc9cbef1da766f5c021a8864f56";

    private static AccessToken accessToken;
    /**
     * 签明验证
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    public static boolean check(String signature, String timestamp, String nonce, String echostr) {
        //1)将token、timestamp、nonce三个参数进行字典序排序
        String arr[]={TOKEN,timestamp,nonce};
        Arrays.sort(arr);
        // 2）将三个参数字符串拼接成一个字符串进行sha1加密
        String sha1 = getSha1(arr[0] + arr[1] + arr[2]);
        // 3）开发者获得加密后的字符串可与signature对比
        return signature.equalsIgnoreCase(sha1);
    }

    /**
     * sha1加密
     * @param str
     * @return
     */
    private static String getSha1(String str) {

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    public static HashMap<String,String> xml2Map(InputStream is) throws IOException {
        HashMap<String,String> map=new HashMap<>();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }finally {
            is.close();
            is=null;
        }
        // 得到xml根元素
        Element root = document.getRootElement();
        List<Element> elements = root.elements();

        for (Element el : elements) {
            map.put(el.getName(),el.getStringValue());
        }
        System.out.println(map);
        return map;
    }

    public static String getAccessToken(){
        if(accessToken==null||accessToken.isExpire()){
            getToken();
        }
        return accessToken.getAccessToken();
    }

    private static void getToken(){
        String url=GET_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
        try {
            String token = HttpUtil.sendGet(url);
            JSONObject jsonObject = JSONObject.parseObject(token);
            accessToken=new AccessToken(jsonObject.getString("access_token"),jsonObject.getString("expires_in"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 临时文件上传
     * @param path
     * @param type
     * @return
     */
    public static String upload(String path,String type) {
        File file = new File(path);
        //地址
        String url="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
        url = url.replace("ACCESS_TOKEN", getAccessToken()).replace("TYPE", type);
        try {
            URL urlObj = new URL(url);
            //强转为案例连接
            HttpsURLConnection conn = (HttpsURLConnection) urlObj.openConnection();
            //设置连接的信息
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            //设置请求头信息
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "utf8");
            //数据的边界
            String boundary = "-----"+System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
            //获取输出流
            OutputStream out = conn.getOutputStream();
            //创建文件的输入流
            InputStream is = new FileInputStream(file);
            //第一部分：头部信息
            //准备头部信息
            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(boundary);
            sb.append("\r\n");
            sb.append("Content-Disposition:form-data;name=\"media\";filename=\""+file.getName()+"\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            out.write(sb.toString().getBytes());
            System.out.println(sb.toString());
            //第二部分：文件内容
            byte[] b = new byte[1024];
            int len;
            while((len=is.read(b))!=-1) {
                out.write(b, 0, len);
            }
            is.close();
            //第三部分：尾部信息
            String foot = "\r\n--"+boundary+"--\r\n";
            out.write(foot.getBytes());
            out.flush();
            out.close();
            //读取数据
            InputStream is2 = conn.getInputStream();
            StringBuilder resp = new StringBuilder();
            while((len=is2.read(b))!=-1) {
                resp.append(new String(b,0,len));
            }
            return resp.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
