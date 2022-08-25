package com.aiit.service.impl;

import com.aiit.entity.Article;
import com.aiit.mapper.MessageMapper;
import com.aiit.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    public Map<String,Object> getArticleType(){
        List<String> typeList = messageMapper.getArticleType();
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<Object> dataList = new ArrayList<Object>();
        dataMap.put("firstList",getArticleList(typeList.get(0)));
        for(int i = 0;i< typeList.size();i++){
            Map<String,Object> infoMap = new HashMap<String,Object>();
            infoMap.put("name",typeList.get(i));
            dataList.add(infoMap);
        }
        dataMap.put("result",dataList);
        return dataMap;
    }
    public Map<String,Object> getArticleList(String articleType){
        List<Article> articleList = messageMapper.getArticleList(articleType);
        Map<String,Object> dataMap = new HashMap<String,Object>();
        List<Object> dataList = new ArrayList<Object>();
        for(int i = 0;i< articleList.size();i++){
            Map<String,Object> infoMap = new HashMap<String,Object>();
            String date = articleList.get(i).getTime();
            String title = articleList.get(i).getTitle();
            String author = articleList.get(i).getAuthor();
            int id = articleList.get(i).getId();
            infoMap.put("id",id);
            infoMap.put("date",date);
            infoMap.put("title",title);
            infoMap.put("author",author);
            dataList.add(infoMap);
        }
        dataMap.put("result",dataList);
        return dataMap;
    }
    public Map<String,Object> getArticleInclude(int id){
        Map<String,Object> dataMap = new HashMap<String,Object>();
        String include = messageMapper.getArticleInclude(id).get(0);
        dataMap.put("include",include);
        return dataMap;
    }
}
