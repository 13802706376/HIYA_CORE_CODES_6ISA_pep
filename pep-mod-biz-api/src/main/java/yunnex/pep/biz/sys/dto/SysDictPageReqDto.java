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
 * 数据字典 PageReqDto
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="数据字典分页请求DTO", description="数据字典")
public class SysDictPageReqDto extends Page<SysDictPageRespDto> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "名称")
    private String name;

}
