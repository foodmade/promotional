package com.seller66.admin.dto;

import com.seller66.admin.model.BaseModel;
        import java.util.Date;
import java.util.List;
import lombok.Data;

/**
* @description Menu视图
* @author  jcb
* @since  2025-06-05 19:37:19
*/

@Data
public class MenuDTO extends BaseModel {

    /**
    * id
    * 主键
    */
    private Integer id;
    /**
    * parentId
    * 父级菜单ID，顶级为NULL
    */
    private Integer parentId;
    /**
    * name
    * 菜单名称(i18n key或直接显示名)
    */
    private String name;
    /**
    * path
    * 路由路径
    */
    private String path;
    /**
    * icon
    * 图标类名
    */
    private String icon;
    /**
    * sort
    * 排序值，越小越靠前
    */
    private Integer sort;
    /**
    * external
    * 是否外部链接(0否1是)
    */
    private Boolean external;
    /**
    * mobileDivider
    * 移动端是否分隔线(0否1是)
    */
    private Boolean mobileDivider;
    /**
    * visible
    * 是否显示(0否1是)
    */
    private Boolean visible;
    /**
    * title
    * 菜单标题
    */
    private String title;
    /**
    * subtitle
    * 菜单描述
    */
    private String subtitle;
    /**
    * createdAt
    * 
    */
    private Date createdAt;
    /**
    * updatedAt
    * 
    */
    private Date updatedAt;

    /**
    * 子菜单列表
    */
    private List<MenuDTO> children;

    private String menuUsName;

    private String menuCnName;

}
