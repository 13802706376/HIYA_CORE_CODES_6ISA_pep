package yunnex.pep.biz.sys.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import yunnex.pep.common.base.BaseDto;
import yunnex.pep.common.util.tree.Tree;

/**
 * <p>
 * @since 2018-10-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysMenuDto extends BaseDto implements Tree<SysMenuDto> {

    private static final long serialVersionUID = 1L;

    private SysMenuDto parent;    // 父级菜单
    /**
     * 父级编号
     */
    private String parentId;

    /**
     * 所有父级编号
     */
    private String parentIds;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 链接
     */
    private String href;

    /**
     * 目标
     */
    private String target;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否在菜单中显示
     */
    private String isShow;

    /**
     * 权限标识
     */
    private String permission;
    
    /**
     * 菜单类型
     */
    private String type;
    private List<SysMenuDto> children;

    public void setChildren(List<SysMenuDto> children) {
        this.children = children;
    }
}
