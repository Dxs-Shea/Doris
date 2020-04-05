package com.dxs.front.controller;

import com.dxs.front.mapper.UserMapper;
import com.dxs.front.pojo.AccessTokenDTO;
import com.dxs.front.pojo.GithubUserDTO;
import com.dxs.front.pojo.User;
import com.dxs.front.service.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);

        //通过accessToken获取用户信息
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUserDTO githubUserDTO = githubProvider.getUser(accessToken);
        System.out.println(githubUserDTO.getName());
        if (githubUserDTO != null && githubUserDTO.getId() != null) {
            //获取登录信息
            User user = new User();
            //UUID防止id重复
            //自己写token，就像JSESSIONID
            //key为token存在，登录过，成功
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUserDTO.getName());
            user.setAccountId(String.valueOf(githubUserDTO.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtModified());
            user.setAvatarUrl(githubUserDTO.getAvatarUrl());
            userMapper.insert(user);

            //写入cookie
            response.addCookie(new Cookie("token", token));

            //登录成功，写cookie和session
            // request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        } else {
            return "redirect:/";
        }

    }
}
