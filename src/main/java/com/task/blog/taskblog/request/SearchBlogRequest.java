package com.task.blog.taskblog.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SearchBlogRequest {
    @NotNull(message = "pageNo Cannot Be null")
    @Min(1)
    private Integer pageNo = 1;
    @NotNull(message = "pageSize Cannot Be null")
    @Min(1)
    private Integer pageSize = 10;
    private Long blogId;
    private String blogName; 
    private String blogTitle;

}
