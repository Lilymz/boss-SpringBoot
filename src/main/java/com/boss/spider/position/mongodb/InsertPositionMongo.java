package com.boss.spider.position.mongodb;

import com.boss.spider.position.entity.EnchartsSalary;
import com.boss.spider.position.entity.Position;
import com.boss.spider.position.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author minnan
 * @date 2021/1/28-19:28
 */
@Component
public class InsertPositionMongo {
//    @Autowired
//    private MongoTemplate mongoTemplate;
    @Autowired
    private PositionService positionService;
//    public void addPosition(Position positionEntity){
//        if (!mongoTemplate.collectionExists("position")) {
//            mongoTemplate.createCollection("position");
//        }
//        mongoTemplate.insert(positionEntity);
//    }
    public List<String> mysqlFindCity(String keyWord){
        System.out.println(positionService);
        List<Position> positions= positionService.findPositionLikeByTitle(keyWord);
        List<String> cityS=new ArrayList<>();
        for (Position pos:positions){
            cityS.add(pos.getPositionCity());
        }
        Set<String> filer=new HashSet<>();
        for (String city:cityS){
            filer.add(city);
        }
        cityS.removeAll(cityS);
        cityS.addAll(filer);
        return cityS;
    }
    public EnchartsSalary mysqlFindPositionByKeyWordAndCity(Position position){
        List<Position> positions = positionService.findPositionLikeByCity(position);
        if (positions==null||positions.size()==0){
            return null;
        }
        System.out.println("=======================================");
        List<Position> filter=new ArrayList<>();
        for (Position pos:positions){
            if (pos.getPositionSalaryRange().contains("K")) {
                if (pos.getPositionSalaryRange().contains("·"))
                    pos.setPositionSalaryRange(pos.getPositionSalaryRange().substring(0,
                            pos.getPositionSalaryRange().indexOf("·")));
                filter.add(pos);
            }
        }
        AtomicReference<Double> salary= new AtomicReference<>((double) 0);
        filter.forEach((position1)->{
            String low=position1.getPositionSalaryRange().substring(0, position1.getPositionSalaryRange().indexOf("-"));
            String up=position1.getPositionSalaryRange().substring(position1.getPositionSalaryRange().indexOf("-")+1,
                    position1.getPositionSalaryRange().indexOf("K"));
            salary.updateAndGet(v -> new Double(v + (double) (Integer.valueOf(low) + Integer.valueOf(up)) * 1.0 / 2 * 1000));
        });
        System.out.println(salary);
        return new EnchartsSalary(position.getPositionCity(), String.valueOf((int)(salary.get() /filter.size())));
    }
//    public List<String> findCity(String keyWord){
//        Query query=new Query();
//        query.addCriteria(Criteria.where("positionTitle").regex(keyWord));
//        List<Position> positions=mongoTemplate.find(query, Position.class, "position");
//        List<String> cityS=new ArrayList<>();
//        for (Position pos:positions){
//            cityS.add(pos.getPositionCity());
//        }
//        Set<String> filer=new HashSet<>();
//        for (String city:cityS){
//            filer.add(city);
//        }
//        cityS.removeAll(cityS);
//        cityS.addAll(filer);
//        return cityS;
//    }
//    public EnchartsSalary findPositionByKeyWordAndCity(String keyWord, String city){
//        Query query=new Query();
//        query.addCriteria(Criteria.where("positionTitle").regex(keyWord));
//        query.addCriteria(Criteria.where("positionCity").is(city));
//        List<Position> positions=mongoTemplate.find(query, Position.class, "position");
//        for (Position position:positions){
//            System.out.println(position);
//        }
//        System.out.println("=======================================");
//        List<Position> filter=new ArrayList<>();
//        for (Position position:positions){
//            if (position.getPositionSalaryRange().contains("K")) {
//                if (position.getPositionSalaryRange().contains("·"))
//                    position.setPositionSalaryRange(position.getPositionSalaryRange().substring(0,
//                            position.getPositionSalaryRange().indexOf("·")));
//                filter.add(position);
//            }
//        }
//        AtomicReference<Double> salary= new AtomicReference<>((double) 0);
//        filter.forEach((position)->{
//            String low=position.getPositionSalaryRange().substring(0, position.getPositionSalaryRange().indexOf("-"));
//            String up=position.getPositionSalaryRange().substring(position.getPositionSalaryRange().indexOf("-")+1,
//                    position.getPositionSalaryRange().indexOf("K"));
//            salary.updateAndGet(v -> new Double((double) (v + (double) (Integer.valueOf(low) + Integer.valueOf(up)) * 1.0 / 2 * 1000)));
//        });
//        return new EnchartsSalary(city, String.valueOf((int)(salary.get() /filter.size())));
//    }

}
