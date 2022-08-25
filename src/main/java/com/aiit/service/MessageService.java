package com.aiit.service;

import com.aiit.entity.Article;

import java.util.List;
import java.util.Map;

public interface MessageService {
    /**
     * 获取文章类型
     * @return Map<String,Object>
     */
    Map<String,Object> getArticleType();

    /**
     * 获取文章列表
     * @param articleType 文章类型
     * @return Map<String,Object>
     */
    Map<String,Object> getArticleList(String articleType);

    /**
     * 获取文章内容
     * @param id 编号
     * @return Map<String,Object>
     */
    Map<String,Object>getArticleInclude(int id);
}
