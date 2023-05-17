package com.task.blog.taskblog.service;

import com.task.blog.taskblog.request.BlogRequest;
import com.task.blog.taskblog.request.SearchBlogRequest;
import com.task.blog.taskblog.response.BlogResponse;
import com.task.blog.taskblog.response.SearchBlogResponse;

public interface BlogService {

    BlogResponse createBlog(BlogRequest req, String username);
    BlogResponse update(BlogRequest req,Long blogId, String username);
    BlogResponse getByBlogId(Long blogId);
    void deleteBlog(Long blogId, String username);
    SearchBlogResponse search(SearchBlogRequest request);
}
