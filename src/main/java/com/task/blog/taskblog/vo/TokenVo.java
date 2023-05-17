package com.task.blog.taskblog.vo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenVo {
    @NonNull
	private String token;
    @NonNull
    private String subject;
    @NonNull
	private Date issuedAt;
    @NonNull
    private Date expireDate;
}
