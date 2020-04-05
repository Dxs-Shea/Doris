package com.dxs.front.controller;

import com.dxs.front.mapper.ArticleMapper;
import com.dxs.front.mapper.UserMapper;
import com.dxs.front.pojo.Article;
import com.dxs.front.pojo.ArticleDTO;
import com.dxs.front.pojo.PaginationDTO;
import com.dxs.front.pojo.User;
import com.dxs.front.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page",defaultValue="1") Integer page,
                        @RequestParam(name = "size",defaultValue = "4")Integer size) {
        Cookie[] cookies = request.getCookies();
        //request获取cookie，response去设置cookie
        //快捷键iter
        if (cookies != null && cookies.length != 0)
        {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        //返回的不仅带有article信息，还有用户信息
        PaginationDTO paginationDTO = articleService.allArticleList(page, size);
        //把数据放入
        model.addAttribute("paginationDTO",paginationDTO);

        return "index";
    }


}
