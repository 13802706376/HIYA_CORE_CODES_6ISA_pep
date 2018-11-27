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
 *  PageRespDto
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="分页响应DTO", description="")
public class SysPositionPageRespDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位名称")
    private String positionName;

    @ApiModelProperty(value = "岗位分类  sales_zx(销售直销),sales_qd(销售渠道),no_sales(非销售)")
    private String positionCategory;

    @ApiModelProperty(value = "专业级")
    private String professionalLevel;

    @ApiModelProperty(value = "管级")
    private String manageLevel;

    @ApiModelProperty(value = "工作职责")
    private String workDuty;

    @ApiModelProperty(value = "工作要求")
    private String workRequire;

    @ApiModelProperty(value = "专业通道")
    private String professionalChannel;

    @ApiModelProperty(value = "专业福利图")
    private String professionalWealPic;

    @ApiModelProperty(value = "管理通道")
    private String manageChannel;

    @ApiModelProperty(value = "管理福利图")
    private String manageWealPic;

    @ApiModelProperty(value = "是否配置面试题模板")
    private String interviewTemplateFlag;
   
    @ApiModelProperty(value = "使用部门")
    private String useOffice;
    @ApiModelProperty(value = "使用部门的主键ids")
    private String officeIds;
    
}
