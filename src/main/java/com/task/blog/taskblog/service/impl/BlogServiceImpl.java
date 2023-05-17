package com.task.blog.taskblog.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.task.blog.taskblog.common.exception.ValidationException;
import com.task.blog.taskblog.entity.Blog;
import com.task.blog.taskblog.entity.User;
import com.task.blog.taskblog.repository.BlogRepository;
import com.task.blog.taskblog.repository.UserRepository;
import com.task.blog.taskblog.request.BlogRequest;
import com.task.blog.taskblog.request.SearchBlogRequest;
import com.task.blog.taskblog.response.BlogResponse;
import com.task.blog.taskblog.response.SearchBlogResponse;
import com.task.blog.taskblog.service.BlogService;
import org.apache.commons.lang3.StringUtils;

import jakarta.transaction.Transactional;

@Service
@Repository
@Transactional
public class BlogServiceImpl implements BlogService{
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public BlogResponse createBlog(BlogRequest req, String username) {

        Blog blog = blogRepository.getBlogByName(req.getBlogName());
        if (blog != null) {
            throw new ValidationException(String.format( "This Blog : [%s] Is Already Exist",req.getBlogName()));
        }
        Blog entity = new Blog();
        BeanUtils.copyProperties(req, entity);
        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            throw new ValidationException(String.format("This Username : [%s] Not Found",username));
        }
        entity.setBlogAuthor(req.getBlogAuthor() +" With Email "+ user.getEmail());
        entity.setUser(user);
        entity.setCreateBy(username);
        entity.setCreateDt(new Date(System.currentTimeMillis()));
        blogRepository.save(entity);

        BlogResponse response = new BlogResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    public BlogResponse update(BlogRequest req, Long blogId, String username) {

        Blog blog = blogRepository.findById(blogId).orElse(null);
        if (blog == null) {
            throw new ValidationException( String.format("This Blog Id: [%s] Not Found", blogId.toString()));
        }
        
        BeanUtils.copyProperties(req, blog);
        User user = userRepository.findById(username).orElse(null);
        if (user == null) {
            throw new ValidationException( String.format("This Username : [%s] Not Found",username));
        }
        blog.setBlogAuthor(req.getBlogAuthor() +" With Email "+ user.getEmail());
        blog.setUser(user);
        blogRepository.save(blog);

        BlogResponse response = new BlogResponse();
        BeanUtils.copyProperties(blog, response);
        return response;
    }

    @Override
    public void deleteBlog(Long blogId, String username) {
        Blog blog = blogRepository.findById(blogId).orElse(null);
        if (blog == null) {
            throw new ValidationException( String.format("This Blog Id: %s Not Found", blogId.toString()));
        }
        blogRepository.delete(blog);
    }

    @Override
    public SearchBlogResponse search(SearchBlogRequest request) {
        Page<Blog> page = blogRepository.search(
            this.convertValueForLike(request.getBlogName()), 
            this.convertValueForLike(request.getBlogTitle()), 
            request.getBlogId() == null ||request.getBlogId() == 0 ? null : request.getBlogId(),
            this.getPageable(request));

        return this.createSearchResponse(page, request);
    }

    private String convertValueForLike(String value) {
        return value != null ? value : StringUtils.EMPTY;
    }

    protected Pageable getPageable(SearchBlogRequest request) {
        return PageRequest.of(request.getPageNo() - 1, request.getPageSize());
    }

    private SearchBlogResponse createSearchResponse(Page<Blog> page, SearchBlogRequest request) {
        List<Blog> data = page.toList();

        return new SearchBlogResponse(request.getPageNo(), request.getPageSize(), data.size(), page.getTotalElements(),
                page.getTotalPages(), data);
    }

    @Override
    public BlogResponse getByBlogId(Long blogId) {
        
        Blog blog = blogRepository.findById(blogId).orElse(null);
        if (blog == null) {
            throw new ValidationException(  String.format("This Blog Id: %s Not Found", blogId.toString()));
        }

        BlogResponse response = new BlogResponse();
        BeanUtils.copyProperties(blog, response);
        return response;
    }
    
}
