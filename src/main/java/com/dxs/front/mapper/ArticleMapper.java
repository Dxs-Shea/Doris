package com.dxs.front.mapper;

import com.dxs.front.pojo.Article;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Insert("insert into article (title,description,gmt_create,gmt_modified,creator,tag)values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void insert(Article article);

    @Select("select * from article limit #{offset},#{size}")
    List<Article> allArticleList(@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);

    @Select("select count(1) from article")
    Integer count();
}
