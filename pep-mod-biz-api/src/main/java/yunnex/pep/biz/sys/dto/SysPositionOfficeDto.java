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
 * 岗位部门关联关系 DTO
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="岗位部门关联关系DTO", description="岗位部门关联关系")
public class SysPositionOfficeDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位id")
    private String positionId;

    @ApiModelProperty(value = "部门id")
    private String officeId;


}
