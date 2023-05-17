package com.task.blog.taskblog.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private String tokenType;
    @JsonFormat(pattern = "dd MMM yyyy HH:MM")
    private Date loginDate;
}
