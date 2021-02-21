package com.boss.spider.position.utils;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author minnan
 * @date 2021/1/21-21:45
 */
public class BossSeedTSJsNameUtil {
    //请求cookie的url
    private static String URL="https://www.zhipin.com/job_detail/";
    //seed
    private static String seed="";
    //ts
    private static String ts="";
    //js_name
    private static String jsName="";
    public static String getJsName() {
        return jsName;
    }
    public static String getSeed() {
        return seed;
    }

    public static String getTs() {
        return ts;
    }
    public static int getSeedTSName(){
        RequestConfig config=RequestConfig.custom().setRedirectsEnabled(false).build();
        CloseableHttpClient client= HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpGet request=new HttpGet(URL);
        request.addHeader("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/"+new Random().nextInt(999)+".36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
        CloseableHttpResponse response= null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            System.out.println("seed方法有误：  "+e.getMessage());
        }
        int code = response.getStatusLine().getStatusCode();
        if (code==302){
            Header header = response.getFirstHeader("location");
            String url=header.getValue();
            String regex1="/.*?";
            String[] tempStr=url.replaceAll("/.*?l","").replace("?", "").split("&");
            seed=tempStr[0].substring(5);
            jsName=tempStr[1].substring(5);
            ts=tempStr[2].substring(3, tempStr[1].length());
            return 1;
        }
        return -1;
    }
    public static String produceCookie(){
        CloseableHttpClient client=HttpClientBuilder.create().build();
        CloseableHttpResponse response=null;
        try {
            int reslut=getSeedTSName();
            if (reslut==-1){
                System.out.println("未成功获取seed");
                return null;
            }
            String produceCookieURL="https://www.zhipin.com/web/common/security-js/"+jsName+".js";
            response=client.execute(new HttpGet(produceCookieURL));
            String JsText=EntityUtils.toString(response.getEntity(), "utf-8");
            String seedDecode= URLDecoder.decode(getSeed(), StandardCharsets.UTF_8);
            String functionCookie="function encryption(seed, ts) {\n" +
                    "    var code = new ABC().z(seed, parseInt(ts) + (480 + new Date().getTimezoneOffset()) * 60 * 1000);\n" +
                    "    return encodeURIComponent(code)\n" +
                    "}";
            //获取到narshron执行js
            ScriptEngineManager manager=new ScriptEngineManager();
            ScriptEngine jsEngine=manager.getEngineByName("javascript");
            jsEngine.eval(JsText);
            if (jsEngine instanceof Invocable){
                Invocable JSstatement = (Invocable) jsEngine;
                Object object=JSstatement.invokeFunction("encryption",getSeed(),getTs());
                System.out.println(object);
            }
            //最后一步调用生成__zp_stoken__方法，这里没有写完明天继续写;
            return "返回cookie";
        }catch (Exception e){
            System.out.println("生产Cookie执行异常："+e.getMessage());
            e.printStackTrace();
        }finally {
            try {
                if (response!=null&client!=null){
                    client.close();
                    response.close();
                }
            }catch (Exception e){
                System.out.println("流关闭异常："+e.getMessage());
            }
        }
        return null;
    }
    //使用selenium調用
    public static String produceCookieSelenium(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options=new ChromeOptions();
//        options.addArguments("--user-data-dir= C:\\Users\\jj\\AppData\\Local\\Google\\Chrome\\User Data ");
        options.addArguments("--user-agent=iphone'");
       //        options.addArguments("headless");
        WebDriver driver=new ChromeDriver(options);
        try {
            String produceCookieURL="https://www.zhipin.com/web/common/security-check.html?seed=cj0IRfnuStFwuySz75SjDicQvUTSuJb0O3VZuPjtDPY%3D";
            driver.get(produceCookieURL);
            Thread.sleep(4000);
            Cookie cookie=driver.manage().getCookieNamed("__zp_stoken__");
            String stoken=cookie.getValue();
            System.out.println(URLDecoder.decode(stoken.trim(), StandardCharsets.UTF_8));
            return URLDecoder.decode(stoken.trim(), StandardCharsets.UTF_8);
        }catch (Exception e){
            System.out.println("生产Cookie执行异常："+e.getMessage());
            e.printStackTrace();
        }finally {
//            driver.close();
//            driver.quit();
        }
        return null;
    }
}
