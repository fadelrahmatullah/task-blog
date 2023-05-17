package com.task.blog.taskblog.common.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class CommonEntity {
    @Column(name = "createBy")
    private String createBy;

    @Column(name = "createDt")
    private Date createDt;

}

