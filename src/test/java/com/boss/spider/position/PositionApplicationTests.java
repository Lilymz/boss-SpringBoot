package com.boss.spider.position;

import com.boss.spider.position.dao.CookiesMapper;
import com.boss.spider.position.dao.HotWordMapper;
import com.boss.spider.position.dao.PositionMapper;
import com.boss.spider.position.dao.SalaryViewMapper;
import com.boss.spider.position.entity.HotWord;
import com.boss.spider.position.entity.Position;
import com.boss.spider.position.mongodb.InsertPositionMongo;
import com.boss.spider.position.service.InsertCookiesService;
import com.boss.spider.position.service.PositionService;
import com.boss.spider.position.service.impl.InsertCookiesServiceImpl;
import com.boss.spider.position.utils.BossPositionHandlerPositionUtil;
import com.boss.spider.position.utils.BossSeedTSJsNameUtil;
import com.boss.spider.position.utils.SalaryAnalysisUtil;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@MapperScan("com.boss.spider.position.dao")
class PositionApplicationTests {
    private final static String[] _ZP_STOKENS={"_bl_uid=vdkpRkL9fy5gz0us4c4L7qIwO644; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1612429019,1612429816,1612529655,1612679694; __fid=d6ef1ccfc71f71822b602f2f41263a60; lastCity=100010000; __g=-; wt2=rGm3s0lsGMdbrKch; __l=l=%2Fwww.zhipin.com%2Fweb%2Fgeek%2Frecommend%3Frandom%3D1612679728962&s=3&friend_source=0; __c=1612679717; __a=23908512.1611752328.1612529658.1612679717.132.9.30.132; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1612698004; __zp_stoken__="
            , "_bl_uid=vdkpRkL9fy5gz0us4c4L7qIwO644;Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1612872919,1612872940,1612873117,1612873125; __fid=d6ef1ccfc71f71822b602f2f41263a60;lastCity=100010000; __g=-; wt2=7GSjbhxwhMMIAq9h;__l=r=http%3A%2F%2Flocalhost%3A9999%2F&l=%2Fwww.zhipin.com%2Fweb%2Fgeek%2Frecommend&s=3&g=&friend_source=0&s=3&friend_source=0; toUrl=https%3A%2F%2Fwww.zhipin.com%2F; __zp_stoken__="
            ,"_bl_uid=vdkpRkL9fy5gz0us4c4L7qIwO644; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1612872919,1612872940,1612873117,1612873125; __fid=d6ef1ccfc71f71822b602f2f41263a60; lastCity=100010000; __g=-; wt2=xGJ0f5iIkMaMuorh; __l=r=http%3A%2F%2Flocalhost%3A9999%2F&l=%2Fwww.zhipin.com%2Fweb%2Fgeek%2Frecommend&s=3&g=&friend_source=0&s=3&friend_source=0; toUrl=https%3A%2F%2Fwww.zhipin.com%2F; __zp_stoken__="
            //以下位美国号cookie
            ,"_bl_uid=8jk1LkjIypR3q66Onz5kj892enyX; __g=-; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1612880146,1612881817,1612951641,1613299464; lastCity=100010000; __fid=d6ef1ccfc71f71822b602f2f41263a60; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1613304140; wt2=iGedlXQYzMTuBhfh; __c=1613299455; __l=l=%2Fwww.zhipin.com%2Fweb%2Fgeek%2Frecommend%3Frandom%3D1613304149003&r=&g=&s=3&friend_source=0&s=3&friend_source=0; __a=60637468.1612880143.1612951640.1613299455.153.3.35.153; __zp_stoken__="  // 美国号  923
            ,"_bl_uid=8jk1LkjIypR3q66Onz5kj892enyX; __g=-; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1612880146,1612881817,1612951641,1613299464; lastCity=100010000; __fid=d6ef1ccfc71f71822b602f2f41263a60; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1613304454; wt2=QP76qgvlzIsHir0h; __c=1613299455; __l=l=%2Fwww.zhipin.com%2Fweb%2Fgeek%2Frecommend%3Frandom%3D1613304469398&r=&g=&s=3&friend_source=0&s=3&friend_source=0; __a=60637468.1612880143.1612951640.1613299455.157.3.39.157; __zp_stoken__=" //美国号  228
            ,"_bl_uid=8jk1LkjIypR3q66Onz5kj892enyX; __g=-; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1612880146,1612881817,1612951641,1613299464; lastCity=100010000; __fid=d6ef1ccfc71f71822b602f2f41263a60; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1613304511; wt2=QPrXLy6wrMvMj51h; __l=l=%2Fwww.zhipin.com%2Fweb%2Fgeek%2Frecommend%3Frandom%3D1613304552782&r=&g=&s=3&friend_source=0&s=3&friend_source=0; __c=1613299455; __a=60637468.1612880143.1612951640.1613299455.162.3.44.162; __zp_stoken__="//美国号 417
            ,"_bl_uid=8jk1LkjIypR3q66Onz5kj892enyX; __g=-; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1612880146,1612881817,1612951641,1613299464; lastCity=100010000; __fid=d6ef1ccfc71f71822b602f2f41263a60; wt2=fG3Tb0SK1MQnYxnh; __c=1613299455; __l=l=%2Fwww.zhipin.com%2Fjob_detail%2F%3Fcity%3D100010000&r=&g=&s=3&friend_source=0&s=3&friend_source=0; __a=60637468.1612880143.1612951640.1613299455.167.3.49.167; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1613304654; __zp_stoken__=" //美国号 036
            ,"wt2=ThI3mkH6hI7PtNQh; __zp_stoken__="//美国号 178
            ,"wt2=PGKLO7Q72MSnKTQh; __zp_stoken__="};//美国号 871
    @Autowired
    private InsertCookiesServiceImpl insertCookiesService;
    @Autowired
    InsertCookiesService insertCookiesServices;
    @Autowired
    HotWordMapper hotWordMapper;
    @Test
    public void cookieIsUseful() throws Exception {//+insertCookiesServices.findALL().get(0).getCookieValue()+
        String url="https://www.zhipin.com/c101230100/?query=java%E5%AE%9E%E4%B9%A0%E7%94%9F&ka=sel-salary-0";
        String url1="https://www.zhipin.com/c101280100/?query=java&page=1";
        List<BasicHeader> headers=new ArrayList<>();
        headers.add(new BasicHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/84.0"));
        int i=(int) (Math.random()*_ZP_STOKENS.length);
        headers.add(new BasicHeader("referer", "https://www.zhipin.com/wapi/zpAntispam/verify/sliderNew?u=JfRD7m6yQ3wE&callbackUrl=http%3A%2F%2Fwww.zhipin.com%2Fjob_detail%2F%3Fka%3Dheader-job&fromUri=https%3A%2F%2Fwww.zhipin.com%2Fweb%2Fgeek%2Frecommend%3Frandom%3D1613384086541&p=IvdF9GS2TGsGRmszOA%7E%7E"));
        headers.add(new BasicHeader("cookie"," _bl_uid=vdkpRkL9fy5gz0us4c4L7qIwO644; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1612429019,1612429816,1612529655,1612679694; __fid=d6ef1ccfc71f71822b602f2f41263a60; lastCity=100010000; __g=-; wt2=rGm3s0lsGMdbrKch; __l=l=%2Fwww.zhipin.com%2Fweb%2Fgeek%2Frecommend%3Frandom%3D1612679728962&s=3&friend_source=0; __c=1612679717; __a=23908512.1611752328.1612529658.1612679717.132.9.30.132; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1612698004; __zp_stoken__=9d52bEFd0d0p3UkVMXApENVNnYl86UCdDdlIyd1pDDm1QKXBZflpJanobLn4MS2JuAntSV2xzLW4NDDsCeCFndRsnYkB7YBwPEXsUbC0VIxQibi4wRnsAVyBtQSN3GwJMBCpYbn9bLU5ndHJhBw%3D%3D"));
        //        insertCookiesServices.DeleteById(insertCookiesServices.findALL().get(0).getCookieId());
        HttpEntity entity=BossPositionHandlerPositionUtil.httpGetHtml(url1,null,headers);
        String html=EntityUtils.toString(entity, "utf-8");
        System.out.println(html);
        System.out.println(i);
        System.out.println(BossPositionHandlerPositionUtil.getCompanyName(html));
    }
    @Test
    public void testCookie(){
        insertCookiesService.insertCookiesByTXT();
    }
    //测试职位关键词mapper
    @Test
    public void context(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options=new ChromeOptions();
        options.addArguments("--proxy-server=http://"+"27.191.169.110:28554");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://www.zhipin.com/job_detail/?query=java&city=101230100&industry=&position=");
    }
}
