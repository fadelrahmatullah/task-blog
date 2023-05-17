package com.task.blog.taskblog.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("com.task.blog.taskblog.entity")
public class EntityConfig {
    
}
