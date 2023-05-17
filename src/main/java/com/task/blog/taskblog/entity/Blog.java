package com.task.blog.taskblog.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.blog.taskblog.common.entity.CommonEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_blog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blog extends CommonEntity{

    @Id
    @GeneratedValue(generator = "blog_id_GENERATOR", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "blog_id_GENERATOR", sequenceName = "tb_m_blog_blog_id_seq",allocationSize=1)
    @Column(name = "blogId", unique = true, insertable = false,updatable = false)
    private Long blogId;

    @ManyToOne
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    @Column(name = "blogName")
    private String blogName;

    @Column(name = "blogTitle")
    private String blogTitle;

    @Column(name = "blogBody")
    private String blogBody;

    @Column(name = "blogAuthor")
    private String blogAuthor;

}
