package com.seller66.admin.entity;

import com.seller66.admin.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@ApiModel("导航菜单表")
@Table(name = "`menu`")
public class Menu extends BaseModel {
    /**
     * 主键
     */
    @Id
    @Column(name = "`id`")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("主键")
    private Integer id;

    /**
     * 父级菜单ID，顶级为NULL
     */
    @Column(name = "`parent_id`")
    @ApiModelProperty("父级菜单ID，顶级为NULL")
    private Integer parentId;

    /**
     * 菜单名称(i18n key或直接显示名)
     */
    @Column(name = "`name`")
    @ApiModelProperty("菜单名称(i18n key或直接显示名)")
    private String name;

    /**
     * 路由路径
     */
    @Column(name = "`path`")
    @ApiModelProperty("路由路径")
    private String path;

    /**
     * 图标类名
     */
    @Column(name = "`icon`")
    @ApiModelProperty("图标类名")
    private String icon;

    /**
     * 排序值，越小越靠前
     */
    @Column(name = "`sort`")
    @ApiModelProperty("排序值，越小越靠前")
    private Integer sort;

    /**
     * 是否外部链接(0否1是)
     */
    @Column(name = "`external`")
    @ApiModelProperty("是否外部链接(0否1是)")
    private Boolean external;

    /**
     * 移动端是否分隔线(0否1是)
     */
    @Column(name = "`mobile_divider`")
    @ApiModelProperty("移动端是否分隔线(0否1是)")
    private Boolean mobileDivider;

    /**
     * 是否显示(0否1是)
     */
    @Column(name = "`visible`")
    @ApiModelProperty("是否显示(0否1是)")
    private Boolean visible;

    /**
     * 菜单标题
     */
    @Column(name = "`title`")
    @ApiModelProperty("菜单标题")
    private String title;

    /**
     * 菜单描述
     */
    @Column(name = "`subtitle`")
    @ApiModelProperty("菜单描述")
    private String subtitle;

    @Column(name = "`created_at`")
    @ApiModelProperty("")
    private Date createdAt;

    @Column(name = "`updated_at`")
    @ApiModelProperty("")
    private Date updatedAt;
}