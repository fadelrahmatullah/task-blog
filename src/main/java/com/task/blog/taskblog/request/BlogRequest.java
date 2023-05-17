package com.task.blog.taskblog.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRequest {

    private String blogName;
    private String blogTitle;
    private String blogBody;
    private String blogAuthor;
}
