package com.task.blog.taskblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.blog.taskblog.common.controller.BaseController;
import com.task.blog.taskblog.common.model.Response;
import com.task.blog.taskblog.request.BlogRequest;
import com.task.blog.taskblog.request.SearchBlogRequest;
import com.task.blog.taskblog.response.BlogResponse;
import com.task.blog.taskblog.response.SearchBlogResponse;
import com.task.blog.taskblog.service.BlogService;
import com.task.blog.taskblog.util.UtilResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("blog")
public class BlogController extends BaseController{
    
    @Autowired
    BlogService blogService;

    @Autowired
    private UtilResponse utilResponse;
    
    @PostMapping("/create-blog")
    public Response<BlogResponse> create(Authentication authentication, @RequestBody @Valid BlogRequest user) {
        BlogResponse response = blogService.createBlog(user, this.getLoginUsername(authentication));
        return utilResponse.generateResponseSuccess(response);
    }

    @PutMapping("/update-blog/{blogId}")
    public Response<BlogResponse> update(Authentication authentication,
        @PathVariable @NotNull(message = "Blog Id Cannot Null") Long blogId, 
        @RequestBody @Valid BlogRequest user) {

        BlogResponse response = blogService.update(user, blogId, this.getLoginUsername(authentication));
        return utilResponse.generateResponseSuccess(response);
    }

    @DeleteMapping("/delete-blog/{blogId}")
    public Response<Object> delete(Authentication authentication,
        @PathVariable @NotNull(message = "Blog Id Cannot Null") Long blogId) {

        blogService.deleteBlog(blogId, this.getLoginUsername(authentication));
        return utilResponse.generateResponseSuccess(null);
    }

    @GetMapping("/get-blog/{blogId}")
    public Response<BlogResponse> getBlog(Authentication authentication,
        @PathVariable @NotNull(message = "Blog Id Cannot Null") Long blogId) {

        BlogResponse response = blogService.getByBlogId(blogId);
        return utilResponse.generateResponseSuccess(response);
    }

    @GetMapping("/search-blog")
    public Response<SearchBlogResponse> searchBlog(Authentication authentication, @Valid SearchBlogRequest req) {

        SearchBlogResponse response = blogService.search(req);
        return utilResponse.generateResponseSuccess(response);
    }
}
