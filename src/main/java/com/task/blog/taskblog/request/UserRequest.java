package com.task.blog.taskblog.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotBlank(message = "Username Cannot Empty")
    private String username;
    @NotBlank(message = "Email Cannot Empty")
    private String email;
    @NotBlank(message = "Password Cannot Empty")
    private String password;
}
