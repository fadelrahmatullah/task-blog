package com.task.blog.taskblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class TaskBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskBlogApplication.class, args);
	}

}
