package com.boss.spider.position.utils;

import com.boss.spider.position.entity.Point;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author minnan
 * @date 2021/2/1-19:15
 */
public class MapPointHandlerUtil {
    private static PoolingHttpClientConnectionManager pools;
    static {
        //防止资源反复销毁及创建，使用连接池的方式进行管理
        pools=new PoolingHttpClientConnectionManager();
        pools.setMaxTotal(100);//连接池总数;
        pools.setDefaultMaxPerRoute(10);//每个页面最多分配数
    }
    public static Point getPoint(String address){
        //对字符串进行编码
        try {
            String addressEncode= URLEncoder.encode(address, StandardCharsets.UTF_8);
            String URL="http://api.map.baidu.com/geocoder?address="+addressEncode+"&output=json&key=423ecafaa9cd6fe72ae4cddd77c0da5d";
            //模拟请求
            BasicHeader  headers=new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/"+Math.random()*999+".36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
            HttpGet request=new HttpGet(URL);
            request.addHeader(headers);
            CloseableHttpClient httpClient= HttpClientBuilder.create().setConnectionManager(pools)
                    .build();
           CloseableHttpResponse response= httpClient.execute(request);
           if (response!=null){
               String[] strS=EntityUtils.toString(response.getEntity(), "utf-8").replaceAll(" ", "").replaceAll("\n", "").split(",");
               String x=strS[1].replaceAll("\"r.*?lng\":", "");
               String y=strS[2].substring(strS[2].indexOf(":")+1,strS[2].length()-1);
               return new Point(x, y,address);
           }
           else
               return null;
        } catch (Exception e) {
            System.out.println("解析坐标点出现错误，请核对！:"+e.getMessage());
        }
        return null;
    }
}
