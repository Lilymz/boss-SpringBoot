package com.boss.spider.position.controller;

import com.boss.spider.position.dao.CookiesMapper;
import com.boss.spider.position.dao.HotWordMapper;
import com.boss.spider.position.entity.Cookies;
import com.boss.spider.position.entity.EnchartsSalary;
import com.boss.spider.position.entity.HotWord;
import com.boss.spider.position.entity.Position;
import com.boss.spider.position.enums.CityEnum;
import com.boss.spider.position.enums.SalaryEnum;
import com.boss.spider.position.service.PositionService;
import com.boss.spider.position.utils.BossPositionHandlerPositionUtil;
import com.boss.spider.position.utils.SalaryAnalysisUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.http.HttpEntity;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author minnan
 * @date 2021/1/26-16:17
 */
@RestController
@RequestMapping("/position")
public class PositionController {
    private final static String[] _ZP_STOKENS={'登录cookie信息'};//美国号 252
    @Autowired
    private PositionService positionService;
    @Autowired
    private CookiesMapper cookiesMapper;
    @Autowired
    private HotWordMapper hotWordMapper;
    @Autowired
    private SalaryAnalysisUtil salaryAnalysisUtil;
    @GetMapping("/getPosition")
    public List<Position> getPosition(@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                      @RequestParam(value = "pageSize",defaultValue = "5")int pageSize,
                                      @RequestParam(value = "keyWord",defaultValue = "")String keyWord,
                                      @RequestParam(value = "city",defaultValue = "")String city){
        PageHelper.startPage(pageNum, pageSize);

        List<Position> positions;
        if (keyWord.equals("")&&city.equals("")){
            positions=positionService.findPositionLikeByTitle("java");
        }else {
            Position position=new Position();
            position.setPositionTitle(keyWord);
            position.setPositionCity(city);
            positions=positionService.findPositionLikeByCity(position);
        }
        PageInfo<Position> pageInfo=new PageInfo<>(positions,pageSize);
        return positions;
    }
    @GetMapping("/getCount/{keyWord}/{city}")
    public String getCount(@PathVariable String keyWord,@PathVariable String city){
        String count="0";
        if (keyWord.equals("kill")&&city.equals("you")){
           return count=String.valueOf(positionService.findPositionLikeByTitle("java").size());
        }
        Position position=new Position();
        position.setPositionTitle(keyWord);
        position.setPositionCity(city);
        count=String.valueOf(positionService.findPositionLikeByCity(position).size());
       return count;
    }
    @PostMapping("/addPos")
    public Map<String,
            Object> submitPosition(@RequestParam("keyWord")String keyWord,
                                             @RequestParam("city")String city,
                                             @RequestParam("salary")String salary){
        Map<String,Object> modelMap=new HashMap<>();
        String url="https://www.zhipin.com/";
        if (!city.equals("")){
            url+="c"+ CityEnum.getCode(city);
        }else {
            modelMap.put("success", false);
            modelMap.put("errMsg","未成功获取到城市信息");
            return modelMap;
        }
        if (!salary.equals("")){
            url+="/y_"+ SalaryEnum.getName(salary).substring(SalaryEnum.getName(salary).length()-1);
        }
        if (!keyWord.equals("")){
            if (url.contains("y_")){
                url+="&query="+keyWord;
            }else
                url+="/?query="+keyWord;
        }
        List<BasicHeader> headers=new ArrayList<>();
        try{
            int i=0;
            for (int j=1;j<=5;j++){
                //添加headers
                List<Cookies> cookies=cookiesMapper.findALL();
                if (cookies.size()==0){
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "动态cookie已用完，请管理员继续添加！");
                    modelMap.put("count", "成功爬取条数"+i);
                    modelMap.put("url", "/");
                    return modelMap;
                }
                if (url.contains("page="))
                    url=url.substring(0, url.indexOf("page="))+"&page="+j;
                else
                    url+="&page="+j;
                System.out.println("爬取网址："+url);
                headers.add(new BasicHeader("user-agent", "Mozilla/5.0 (X11; Linux x86_64; rv:60.0) Gecko/20100101 Firefox/84.0"));
                int k=(int) (Math.random()*_ZP_STOKENS.length);
                System.out.println("这是第"+k+"个cookie");
                headers.add(new BasicHeader("cookie",_ZP_STOKENS[k]+
                        cookies.get(0).getCookieValue()));
                //获取entity
                HttpEntity entity=BossPositionHandlerPositionUtil.httpGetHtml(url, null, headers);
                String isFake=EntityUtils.toString(entity, "utf-8");
                System.out.println(isFake);

                if(isFake.contains("点击进行验证")){
                    cookiesMapper.DeleteCookie(cookies.get(0).getCookieId());
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "需要进行验证爬取");
                    modelMap.put("url", "/");
                    return modelMap;
                }
                if(isFake.contains("正在加载中")){
                    cookiesMapper.DeleteCookie(cookies.get(0).getCookieId());
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "cookie失效,请重试！");
                    modelMap.put("url", "/");
                    return modelMap;
                }
                Elements elements=BossPositionHandlerPositionUtil.getFirstElements(isFake, "job-primary");
                if (elements.size()>0)
                    for (Cookies cookie:cookies){
                        cookiesMapper.DeleteCookie(cookie.getCookieId());
                    }
                List<Position> positions=BossPositionHandlerPositionUtil.getPosition(elements, cookies,city,cookies.size()-1);
                if (positions!=null){
                    for (Position position:positions){
                        positionService.insertPositionService(position);
                        i++;
                    }
                    List<HotWord> hotWordss= hotWordMapper.findHotWordALL();
                    List<String> words=new ArrayList<>();
                    hotWordss.forEach(hotWord -> {words.add(hotWord.getHotWord());});
                    int flag=0;
                    for (String word:words) {
                        if (!keyWord.equals(word)){
                            flag++;
                        }
                    }
                    if (flag==words.size())
                        hotWordMapper.insertHotWord(new HotWord(null, keyWord));
                    modelMap.put("success", true);
                    modelMap.put("count", "成功爬取条数"+i);
                    modelMap.put("url", "/");
                }else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg","未成功爬取到岗位");
                    return modelMap;
                }
            }
        } catch(Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg",e.getMessage());
            modelMap.put("url", "/");
            e.printStackTrace();
        }
        return modelMap;
    }
    @PostMapping("/getSalaryS")
    public Map<String,Object> getSalaryS(@RequestParam("keyWord")String keyWord){
        Map<String,Object> modelMap=new HashMap<>();
        if (keyWord!=null&&!keyWord.equals("")){
            try {
             List<EnchartsSalary> salaryS=salaryAnalysisUtil.AvgSalaryByArea(keyWord);
             if (salaryS!=null&&salaryS.size()>0){
                modelMap.put("success",true);
                modelMap.put("salaryS",salaryS);
                return modelMap;
             }else {
                modelMap.put("success",false);
                modelMap.put("errMsg","你未添加该字段，还未对该地区进行统计");
                modelMap.put("url","/");
                return modelMap;
             }
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                modelMap.put("url","/");
                return modelMap;
            }
        }
        modelMap.put("success",false);
        modelMap.put("errMsg","搜索关键词为空");
        modelMap.put("url","/");
        return modelMap;
    }
    @GetMapping("/infoSelect")
    public Map<String,Object> getInfo(){
        Map<String,Object> modelMap=new HashMap<>();
        List<HotWord> hotWords=hotWordMapper.findHotWordALL();
        if (hotWords != null && hotWords.size() > 0) {
            modelMap.put("success",true);
            modelMap.put("hotWords",hotWords);
            return modelMap;
        }
        modelMap.put("success",false);
        modelMap.put("errMsg","未有职位搜索信息");
        return modelMap;
    }
}
