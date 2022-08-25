package com.aiit.mapper;

import com.aiit.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {
    @Select("SELECT articleType FROM article_master")
    public List<String> getArticleType();
    @Select("SELECT ars.id,TIME,author,title FROM article_master am,article_slave ars WHERE am.articleMasterId = ars.articleMasterId AND articleType = #{articleType}")
    public List<Article> getArticleList(String articleType);
    @Select("SELECT include FROM article_slave WHERE id = #{id}")
    public List<String> getArticleInclude(int id);
}
