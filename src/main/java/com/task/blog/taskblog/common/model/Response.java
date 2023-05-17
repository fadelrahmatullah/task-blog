package com.task.blog.taskblog.common.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NonNull;

@Data
public class Response<T> {
    @NonNull
	private ResponseStatus status;
	private String message;
	@NonNull
	@JsonFormat(pattern = "dd MMM yyyy HH:mm")
	private Date processFinishDate;
	private T data;
}
