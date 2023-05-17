package com.task.blog.taskblog.common.controller;

import org.springframework.security.core.Authentication;

import com.task.blog.taskblog.vo.UserAuthVo;

public class BaseController {
    protected UserAuthVo getLoginUserInfo(Authentication authentication) {

        return (UserAuthVo) authentication.getPrincipal();
    }

    protected String getLoginUsername(Authentication authentication) {

        return this.getLoginUserInfo(authentication).getUsername();
    }
}
