package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`author`")
public class Author extends BaseModel {
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 姓名
     */
    @Column(name = "`name`")
    private String name;

    /**
     * 头像
     */
    @Column(name = "`avatar`")
    private String avatar;

    /**
     * 个人简介
     */
    @Column(name = "`bio`")
    private String bio;

    @Column(name = "`title`")
    private String title;
}