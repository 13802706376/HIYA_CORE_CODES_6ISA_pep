package yunnex.pep.biz.sys.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import yunnex.pep.common.base.BaseDto;

/**
 * 角色信息 PageRespDto
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="角色信息分页响应DTO", description="角色信息")
public class SysRolePageRespDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "英文名称")
    private String enname;
   
    @ApiModelProperty(value = "角色说明")
    private String roleExplain;
    
    
    @ApiModelProperty(value = "数据范围")
    private String dataScope;


    
    
}
