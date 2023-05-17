package com.task.blog.taskblog.response;

import lombok.Data;
import lombok.NonNull;

import java.util.List;

import com.task.blog.taskblog.entity.Blog;

@Data
public class SearchBlogResponse {
    @NonNull
    private Integer pageNo;
    @NonNull
    private Integer pageSize;
    @NonNull
    private Integer totalDataInPage;
    @NonNull
    private Long totalData;
    @NonNull
    private Integer totalPages;
    @NonNull
    private List<Blog> listData;
}
