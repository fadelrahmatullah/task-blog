package com.task.blog.taskblog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.task.blog.taskblog.entity.Blog;
public interface BlogRepository extends JpaRepository<Blog, Long>{

    @Query("FROM Blog WHERE blogName = :blogName")
	Blog getBlogByName(@Param("blogName") String blogName);

    @Query(value = """ 
            from Blog b where :blogName = '' or lower(b.blogName) like lower(concat('%', :blogName, '%'))
        and (:blogTitle = '' or lower(b.blogTitle) like lower(concat('%', :blogTitle, '%'))) 
        and (:blogId is null or b.blogId = :blogId) 
        order by b.blogId asc """
    )
    Page<Blog> search(@Param("blogName") String blogName,
                   @Param("blogTitle") String blogTitle, 
                   @Param("blogId") Long blogId,
                   Pageable pageable);
}
