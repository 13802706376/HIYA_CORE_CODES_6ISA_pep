package yunnex.pep.biz.sys.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import yunnex.pep.common.base.BaseDto;

/**
 * 角色信息 DTO
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="角色信息DTO", description="角色信息")
public class SysRoleDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色说明")
    private String roleExplain;
    
    @ApiModelProperty(value = "英文名称")
    private String enname;

    @ApiModelProperty(value = "数据范围")
    private String dataScope;
   
    @ApiModelProperty(value = "角色对应的菜单id")
    private List<String> roleMenuIdList;  
    
    /*private List<SysMenuDto> menuList = new ArrayList<SysMenuDto>();
    
    public List<String> getMenuIdList() {
        List<String> menuIdList =  new ArrayList<String>();
        for (SysMenuDto menu : menuList) {
            menuIdList.add(menu.getId());
        }
        return menuIdList;
    }

    public void setMenuIdList(List<String> menuIdList) {
        menuList =  new ArrayList<SysMenuDto>();
        for (String menuId : menuIdList) {
            SysMenuDto menu = new SysMenuDto();
            menu.setId(menuId);
            menuList.add(menu);
        }
    }*/
    
}
