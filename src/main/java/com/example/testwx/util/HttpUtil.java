package com.example.testwx.util;



import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpUtil {

    public static String sendGet(String url) {
        try {
            URL obj = new URL(url);
            URLConnection con = null;
            con = obj.openConnection();
            System.out.println(con);
            InputStream is=con.getInputStream();
            byte[]b=new byte[1024];
            int len;
            StringBuilder sb=new StringBuilder();
            while((len=is.read(b))!=-1){
                sb.append(new String(b,0,len));
            }
            System.out.println(sb.toString());
            is.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sendPost(String url,String data){

        try {
            URL obj = new URL(url);
            URLConnection con = obj.openConnection();
            con.setDoOutput(true);
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(data.getBytes());
            outputStream.close();
            InputStream is=con.getInputStream();
            byte[]b=new byte[1024];
            int len;
            StringBuilder sb=new StringBuilder();
            while((len=is.read(b))!=-1){
                sb.append(new String(b,0,len));
            }
            is.close();
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
      return  null;
    }
}
