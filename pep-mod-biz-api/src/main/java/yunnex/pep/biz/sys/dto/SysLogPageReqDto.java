package yunnex.pep.biz.sys.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 操作日志 PageReqDto
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="操作日志分页请求DTO", description="操作日志")
public class SysLogPageReqDto extends Page<SysLogPageRespDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "请求URI")
    private String requestUri;

    @ApiModelProperty(value = "IP")
    private String ip;

    @ApiModelProperty(value = "异常标记")
    private String exFlag;

    @ApiModelProperty(value = "用户名")
    private String username;

}
