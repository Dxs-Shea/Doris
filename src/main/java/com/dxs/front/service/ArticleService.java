package com.dxs.front.service;

import com.dxs.front.mapper.ArticleMapper;
import com.dxs.front.mapper.UserMapper;
import com.dxs.front.pojo.Article;
import com.dxs.front.pojo.ArticleDTO;
import com.dxs.front.pojo.PaginationDTO;
import com.dxs.front.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;

    //首页列表有头像，而数据库中的用户没有头像
    public PaginationDTO allArticleList(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer count = articleMapper.count();
        paginationDTO.setPagination(count, page, size);

        //校验页码小于1或者大于应有页码数
        if (page < 1) page = 1;
        if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();

        //分页
        Integer offset = size * (page - 1);

        List<Article> articles = articleMapper.allArticleList(offset, size);
        List<ArticleDTO> articleDTOList = new ArrayList<>();

        for (Article article : articles) {
            User user = userMapper.findById(article.getCreator());
            ArticleDTO articleDTO = new ArticleDTO();
            BeanUtils.copyProperties(article, articleDTO);
            articleDTO.setUser(user);
            articleDTOList.add(articleDTO);
        }

        paginationDTO.setArticles(articleDTOList);

        //paginationDTO.setPagination(count,page,size);

        return paginationDTO;
    }
}
