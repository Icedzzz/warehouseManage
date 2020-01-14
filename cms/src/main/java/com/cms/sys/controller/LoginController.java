package com.cms.sys.controller;


import cn.hutool.crypto.SecureUtil;
import com.cms.sys.common.ActiverUser;
import com.cms.sys.common.ResultObj;
import com.cms.sys.common.WebUtils;
import com.cms.sys.domain.Loginfo;
import com.cms.sys.service.LoginfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;
import java.util.Date;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginfoService loginfoService;
 @RequestMapping("login")
    public ResultObj login(String loginname, String pwd){
     Subject subject = SecurityUtils.getSubject();
     AuthenticationToken usernamePasswordToken = new UsernamePasswordToken(loginname,pwd);
     try {
         subject.login(usernamePasswordToken);
        ActiverUser principal = (ActiverUser) subject.getPrincipal();
         WebUtils.getSession().setAttribute("user",principal.getUser());

         //记录登陆日志
         Loginfo entity = new Loginfo();
         entity.setLoginip(WebUtils.getRequest().getRemoteAddr());
         entity.setLoginname(principal.getUser().getName()+"-"+principal.getUser().getLoginname());
         entity.setLogintime(new Date());
        loginfoService.save(entity);

         return ResultObj.LOGIN_SUCCESS;
     } catch (AuthenticationException e) {
         e.printStackTrace();
         return ResultObj.LOGIN_ERROR_PASS;
     }
 }
}
