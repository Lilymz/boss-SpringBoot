package com.boss.spider.position.controller;

import com.boss.spider.position.entity.Point;
import com.boss.spider.position.entity.Position;
import com.boss.spider.position.service.PositionService;
import com.boss.spider.position.utils.MapPointHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author minnan
 * @date 2021/2/4-18:05
 */
@RestController
@RequestMapping(value = "/Map",method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
public class MapCompanyController {

    @Autowired
    private PositionService positionService;
    @GetMapping("/getCompany/{keyWord}/{city}")
    public Map<String,Object> getCompany(@PathVariable("keyWord")String keyWord,
                                         @PathVariable("city")String city){
        Map<String,Object> modelMap=new HashMap<>();
        if (keyWord!=null&&city!=null){
            Position position=new Position();
            position.setPositionTitle(keyWord);
            position.setPositionCity(city);
            List<Position> positions=positionService.findPositionForPoint(position);
            List<String> area=new ArrayList<>();
            positions.forEach((pos)->{area.add(pos.getPositionArea());});
            if (area.size()>0){
                List<Point> points=new ArrayList<>();
                area.forEach((tempArea)->{points.add(MapPointHandlerUtil.getPoint(tempArea));});
                modelMap.put("success", true);
                modelMap.put("points",points);
                return modelMap;
            }
            modelMap.put("success", false);
            modelMap.put("errMsg", "未成功获取公司位置");
            return modelMap;
        }
        modelMap.put("success", false);
        modelMap.put("errMsg", "当前未成功获取到职位,城市");
        return modelMap;
    }
}
