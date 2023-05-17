package com.task.blog.taskblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.blog.taskblog.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
    
}
