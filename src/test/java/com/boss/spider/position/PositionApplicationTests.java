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
    private final static String[] _ZP_STOKENS={'登录cookie信息'};//美国号522
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
