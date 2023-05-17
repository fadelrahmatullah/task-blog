package com.task.blog.taskblog.service;

import com.task.blog.taskblog.request.UserRequest;
import com.task.blog.taskblog.response.LoginResponse;
import com.task.blog.taskblog.response.UserResponse;

public interface AuthenticationService {
    
    UserResponse registerUser(UserRequest req);
    LoginResponse login(String username, String password);
}
