package yunnex.pep.common.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel(value="BaseDto", description="DTO基础属性")
public class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "创建人")
    private String createdBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdDate;

    @ApiModelProperty(value = "最后修改人")
    private String lastModifiedBy;

    @ApiModelProperty(value = "最后修改时间")
    private LocalDateTime lastModifiedDate;

    @ApiModelProperty(value = "逻辑删除标识", notes = "Y:删除，N:正常")
    private String delFlag;

    @ApiModelProperty(value = "备注")
    private String remarks;

}
