package com.task.blog.taskblog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "com.task.blog.taskblog.repository")
public class JpaConfig {
    
}
