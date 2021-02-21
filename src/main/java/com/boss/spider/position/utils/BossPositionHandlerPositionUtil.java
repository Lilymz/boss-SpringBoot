package com.boss.spider.position.utils;

import com.boss.spider.position.entity.Cookies;
import com.boss.spider.position.entity.Position;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;

/**
 * @author minnan
 * @date 2021/1/13-15:13
 */
public class BossPositionHandlerPositionUtil {
    private final static String[] _ZP_STOKENS={'传入登录cookie信息'};//美国号 871
    //Http工具类日志
    private  static Map<String,String> log=new HashMap<>();
    //Client连接池数量
    private static PoolingHttpClientConnectionManager manager=null;
    static {
        //防止资源反复销毁及创建，使用连接池的方式进行管理
        manager=new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(100);//连接池总数;
        manager.setDefaultMaxPerRoute(10);//每个页面最多分配数
    }

    /**
     * @param uri 页面请求地址
     * @param params 页面请求参数
     * @param headers 页面请求headers
     * @return
     */
    //get方式获取Entity
    public static HttpEntity httpGetHtml(String uri,List<NameValuePair> params,List<BasicHeader> headers) {
        CloseableHttpResponse response=null;
        try {
            CloseableHttpClient httpClient= HttpClientBuilder.create().setConnectionManager(manager)
                    .build();
            URIBuilder uriBuilder=new URIBuilder(uri);
            if (params!=null){
                uriBuilder.addParameters(params);
            }
            //构建GET请求
            HttpGet request= new HttpGet(uriBuilder.build());
            for (Header header:headers) {
                request.setHeader(header);
            }
            //添加GET请求Header信息
            response= httpClient.execute(request);
            System.out.println(response.getStatusLine().getStatusCode());
            HttpEntity entity=response.getEntity();
            return entity;
        } catch (Exception e) {
            log.put("Request_GET","GET请求发生异常");
            e.printStackTrace();
            return null;
        }
    }
    //CloseStream
    public static void closeStream(List<Closeable> resources){
        try{
            for (Closeable resource:resources) {
                resource.close();
            }
            log.put("closeStream", "流关闭成功");
        }catch (Exception e){
            log.put("closeStream", "流关闭异常");
        }
    }
    //获取positionTitle
    public static String getPositionTitle(Element  element){
        return element.getElementsByClass("job-name").first().getElementsByTag("a").first().text();
    }
    //获取职位的链接信息
    public static String getPositionLink(Element element) {
        return "https://www.zhipin.com"+element.getElementsByClass("job-name").first().getElementsByTag("a").first().attr("href");
    }
    //获取岗位薪资
    public static String getPositionSalaryRange(Element element) {
        return element.getElementsByClass("job-limit clearfix").first().child(0).text();
    }
    //岗位需求
    public static String getPositionRequire(String html) throws IOException {
        Document document=Jsoup.parse(html);
        if (html.contains("job-sec")) {
            Elements elementsParent = document.getElementsByClass("job-sec").first().children();
            String positionRequire = elementsParent.get(1).text();
            return positionRequire;
        }
        return "";
    }
    //岗位需求
    public static String getPositionCompanySIZE(String html) throws IOException {
            //获取document实例
            Document document=Jsoup.parse(html);
            //得到元素
        if (document.getElementsByClass("sider-company").isEmpty()){
            return "";
        }
        Elements elementsParent =document.getElementsByClass("sider-company").first().getElementsByTag("p");
        return elementsParent.get(2).text();
    }
    //成立时间
    public static String getPositionBuildTime(String html) {
        Document document=Jsoup.parse(html);
        Elements elementsParent =document.getElementsByClass("res-time");
        return elementsParent.text().replaceAll("成立时间：", "");
    }
    //所在地区
    public static String getPositionArea(String html){
        Document document=Jsoup.parse(html);
        Elements elementsParent =document.getElementsByClass("location-address");
        return elementsParent.text();
    }
    //公司名称
    public static String getCompanyName(String html){
        Document document=Jsoup.parse(html);
        Elements elementsParent =document.getElementsByClass("job-sec");
        if(elementsParent.size()<4)
            return "";
        return elementsParent.get(3).getElementsByClass("name").text();
    }
    /**
     * @param html html
     * @param selectClass 需要进行循环获取的根标签类属性
     * @return
     */
    //获取总div标签
    public static Elements getFirstElements(String html,String selectClass){
        Document document=Jsoup.parse(html);
        return document.getElementsByClass(selectClass);
    }
    //获取子div中title,link,薪资,要求,公司规模,成立时间,每日工作时间,创建时间
    public static List<Position> getPosition(Elements elements,List<Cookies> cookies,String city,int nums) throws Exception {
        List<Position> positions=new ArrayList<>();
        List<BasicHeader> headers=new ArrayList<>();
        headers.add(new BasicHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/84.0"));
        for(int i=0;i<nums;i++){
            String title=getPositionTitle(elements.get(i));
            String link=getPositionLink(elements.get(i));
            String salary=getPositionSalaryRange(elements.get(i));
            headers.add(new BasicHeader("cookie", _ZP_STOKENS[(int) (Math.random()*_ZP_STOKENS.length)]+
                    cookies.get(++i).getCookieValue()));
           Thread.sleep(3000);
           String ClearHtml=EntityUtils.toString(
                    BossPositionHandlerPositionUtil.httpGetHtml(
                            link.trim(), null, headers),
                    "utf-8");
            String require=getPositionRequire(ClearHtml);
            String scale=getPositionCompanySIZE(ClearHtml);
            String buildTime=getPositionBuildTime(ClearHtml);
            String area=getPositionArea(ClearHtml);
            String name=getCompanyName(ClearHtml);
            positions.add(new Position(null,title,salary,link,require.replaceAll("\\\\x.*?[0-9\\\\]",""),scale,buildTime,new Date(),new Date(),area,city,name));
        }
        return positions;
    }
}
