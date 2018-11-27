package yunnex.pep.biz.sys.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import yunnex.pep.common.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 人员部门关联关系表 DTO
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="人员部门关联关系表DTO", description="人员部门关联关系表")
public class SysOfficeUserDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "人员id")
    private String userId;

    @ApiModelProperty(value = "部门id")
    private String officeId;

    @ApiModelProperty(value = "部门dn（部门唯一标识）")
    private String officeDn;
    
    @ApiModelProperty(value = "人员cn（人员登录名）")
    private String userCn;
}
