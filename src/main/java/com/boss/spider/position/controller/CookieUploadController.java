package com.boss.spider.position.controller;

import com.boss.spider.position.dao.CookiesMapper;
import com.boss.spider.position.entity.Cookies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author minnan
 * @date 2021/2/10-17:48
 */
@RestController
@RequestMapping("/upload")
public class CookieUploadController {
    @Autowired
    private CookiesMapper cookiesMapper;
    //文件上传
    @PostMapping("/cookie")
    public Map<String,Object> uploadCookie(@RequestParam("file")MultipartFile file){
        Map<String,Object> modelMap=new HashMap<>();
        if (file.isEmpty()){
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传失败,请选择文件！");
            return modelMap;
        }
        if(!file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1).contains("txt")){
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传失败,文件类型仅限txt");
            return modelMap;
        }
        InputStream is=null;
        BufferedReader reader=null;
        int effectRow=0;
        try {
            is=file.getInputStream();
            if(!new BufferedReader(new InputStreamReader(file.getInputStream())).readLine().matches(".*?%3D")){
                modelMap.put("success", false);
                modelMap.put("errMsg", "不符合stoken格式");
                return modelMap;
            }
            Cookies cookies=new Cookies();
            reader =new BufferedReader(new InputStreamReader(is));
            String str=null;
            while (true){
                str=reader.readLine();
                if (str!=null){
                    cookies.setCookieValue(str);
                    cookiesMapper.insertCookies(cookies);
                        effectRow++;
                }else
                    break;
            }
        }catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传失败,"+e.getMessage());
            return modelMap;
        }finally {
            try {
                if (is!=null)
                    is.close();
                if (reader!=null)
                    reader.close();
            }catch (Exception e){}
        }
        modelMap.put("success", true);
        modelMap.put("count","已提供"+effectRow+"STOKEN");
        return modelMap;
    }
}
