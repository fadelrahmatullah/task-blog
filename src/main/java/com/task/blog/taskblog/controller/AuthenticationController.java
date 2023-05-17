package com.task.blog.taskblog.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.blog.taskblog.common.controller.BaseController;
import com.task.blog.taskblog.common.model.Response;
import com.task.blog.taskblog.request.LoginRequest;
import com.task.blog.taskblog.request.UserRequest;
import com.task.blog.taskblog.response.LoginResponse;
import com.task.blog.taskblog.response.UserResponse;
import com.task.blog.taskblog.service.AuthenticationService;
import com.task.blog.taskblog.util.UtilResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("authentication")
public class AuthenticationController extends BaseController{

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private UtilResponse utilResponse;

    @PostMapping("/login")
    public Response<LoginResponse> login(@RequestBody @Valid LoginRequest user) {
        LoginResponse response = authenticationService.login(user.getUsername(), user.getPassword());
        return utilResponse.generateResponseSuccess(response);
    }

    @PostMapping("/register-user")
    public Response<UserResponse> register(@RequestBody @Valid UserRequest user) {
        UserResponse response = authenticationService.registerUser(user);
        return utilResponse.generateResponseSuccess(response);
    }
}
