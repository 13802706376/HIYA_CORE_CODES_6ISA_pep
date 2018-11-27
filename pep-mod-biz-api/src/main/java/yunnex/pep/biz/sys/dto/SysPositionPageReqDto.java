package yunnex.pep.biz.sys.dto;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  PageReqDto
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="分页请求DTO", description="")
public class SysPositionPageReqDto extends Page<SysPositionPageRespDto> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "岗位名称")
    private String positionName;
    
    @ApiModelProperty(value = "岗位分类  sales_zx(销售直销),sales_qd(销售渠道),no_sales(非销售)")
    private String positionCategory;
    
    @ApiModelProperty(value = "适用部门")
    private List<String> officeIdList; 
    
    @ApiModelProperty(value = "是否配置面试题模板")
    private String interviewTemplateFlag; 


}
