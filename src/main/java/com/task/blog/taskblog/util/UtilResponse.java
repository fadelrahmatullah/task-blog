package com.task.blog.taskblog.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.task.blog.taskblog.common.model.Response;
import com.task.blog.taskblog.common.model.ResponseStatus;

@Component
public class UtilResponse {
    public <T> Response<T> generateResponseSuccess(String message, T data, String... varValues) {
		Response<T> result = new Response<>(ResponseStatus.SUCCESS, new Date());
		result.setMessage(message);
		result.setData(data);

		return result;
	}

	public <T> Response<T> generateResponseSuccess(T data) {

		return this.generateResponseSuccess("Processed Successfully", data);
	}

	public <T> Response<T> generateResponseError(String message, T data, String... varValues) {

		Response<T> result = new Response<>(ResponseStatus.ERROR, new Date());
		result.setMessage(message);
		result.setData(null);
		return result;

	}

	public Response<Object> generateResponseSuccess() {
		return this.generateResponseSuccess(null);
	}

	public Response<Object> generateResponseError(Exception ex) {
		Response<Object> result = new Response<>(ResponseStatus.ERROR, new Date());
		result.setMessage(ex.getMessage());
        result.setData(null);
		return result;
	}

	public Response<Object> generateResponseError(String message, String... varValues) {
		Response<Object> result = new Response<>(ResponseStatus.ERROR, new Date());
		result.setMessage(message);

		return result;
	}
}
