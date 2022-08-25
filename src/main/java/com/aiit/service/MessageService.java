package com.aiit.service;

import com.aiit.entity.Article;

import java.util.List;
import java.util.Map;

public interface MessageService {
    public Map<String,Object> getArticleType();
    public Map<String,Object> getArticleList(String articleType);
    public Map<String,Object>getArticleInclude(int id);
}
