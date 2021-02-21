package com.boss.spider.position;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

/**
 * 测试无忧代理动态转发代理，本段代码支持请求HTTP和HTTPS协议的网址，比如http://www.example.com、https://www.example.com
 * @author www.data5u.com
 *
 */
public class CodeCookieApplication {
    static String order = "5b30439c90c86298343872de5ad21297";
    public static boolean gameOver = false;
    // 定时获取动态IP
    public class GetIP implements Runnable{
        long sleepMs = 1000;
        int maxTime = 3;
        String order = "";
        String targetUrl;
        boolean useJs;
        int timeOut;
        String referer;
        boolean https;
        boolean outputHeaderInfo;

        public GetIP(long sleepMs, int maxTime, String order, String targetUrl, boolean useJs, int timeOut, String referer, boolean https, boolean outputHeaderInfo) {
            this.sleepMs = sleepMs;
            this.maxTime = maxTime;
            this.order = order;
            this.targetUrl = targetUrl;
            this.useJs = useJs;
            this.timeOut = timeOut;
            this.referer=referer;
            this.https=https;
            this.outputHeaderInfo=outputHeaderInfo;
        }

        @Override
        public void run() {
            int time = 1;
            while(!gameOver){
                if(time >= 4){
                    gameOver = true;
                    break;
                }
                try {
                    java.net.URL url = new java.net.URL("http://api.ip.data5u.com/dynamic/get.html?order=" + order + "&ttl&random=true");

                    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                    connection.setConnectTimeout(3000);
                    connection = (HttpURLConnection)url.openConnection();

                    InputStream raw = connection.getInputStream();
                    InputStream in = new BufferedInputStream(raw);
                    byte[] data = new byte[in.available()];
                    int bytesRead = 0;
                    int offset = 0;
                    while(offset < data.length) {
                        bytesRead = in.read(data, offset, data.length - offset);
                        if(bytesRead == -1) {
                            break;
                        }
                        offset += bytesRead;
                    }
                    in.close();
                    raw.close();
                    String[] res = new String(data, StandardCharsets.UTF_8).split("\n");
                    System.out.println(">>>>>>>>>>>>>>当前返回IP量 " + res[0]);
                    Set<String> ipport=new HashSet<>();
                    ipport.add(res[0].substring(0, res[0].indexOf(",")));
                    if (ipport.size()>50){
                        break;
                    }
                } catch (Exception e) {
                    System.err.println(">>>>>>>>>>>>>>获取IP出错, " + e.getMessage());
                }
                try {
                    Thread.sleep(sleepMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main(String[] args) {
        new Thread(new CodeCookieApplication().new GetIP(2 * 1000, 3,order , "https://www.zhipin.com/job_detail/?query=java&city=101230100&industry=&position=", false, 3, "https://www.zhipin.com/web/common/security-check.html?seed=b%2FEghb7hkDoTE%2F5jONnNN37vihlAhPuM%2FRePAMRJlv8%3D&name=725590d9&ts=1612771157772&callbackUrl=%2Fjob_detail%2F%3Fquery%3Djava%26city%3D101230100%26industry%3D%26position%3D&srcReferer=https%3A%2F%2Fwww.zhipin.com%2Fjob_detail%2F%3Fka%3Dheader-job", true, true)).start();
    }

}


