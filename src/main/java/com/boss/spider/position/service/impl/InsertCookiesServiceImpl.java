package com.boss.spider.position.service.impl;

import com.boss.spider.position.dao.CookiesMapper;
import com.boss.spider.position.entity.Cookies;
import com.boss.spider.position.service.InsertCookiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author minnan
 * @date 2021/1/19-18:50
 */
@Service
public class InsertCookiesServiceImpl implements InsertCookiesService {
    @Autowired
    private CookiesMapper cookiesMapper;
    @Override
    public int insertCookiesByTXT() {
        InputStream is=null;
        BufferedReader reader=null;
        int effectRow=0;
        try {
            is= new FileInputStream("E:\\360MoveData\\Users\\jj\\Desktop\\code.txt");
            Cookies cookies=new Cookies();
            reader =new BufferedReader(new InputStreamReader(is));
            String str=null;
            while (true){
                str=reader.readLine();
                if (str!=null){
                    cookies.setCookieValue(str);
                    effectRow=cookiesMapper.insertCookies(cookies);
                }else
                    break;
            }

        }catch (Exception e){

        }finally {
            try {
                if (is!=null)
                    is.close();
                if (reader!=null)
                    reader.close();
            }catch (Exception e){}
        }
        return effectRow;
    }

    @Override
    public List<Cookies> findALL() {
        return cookiesMapper.findALL();
    }

    @Override
    public int DeleteById(Integer id) {
        return cookiesMapper.DeleteCookie(id);
    }
}
