package yunnex.pep.biz.sys.dto;

import java.io.Serializable;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 岗位部门关联关系 PageReqDto
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="岗位部门关联关系分页请求DTO", description="岗位部门关联关系")
public class SysPositionOfficePageReqDto extends Page<SysPositionOfficePageRespDto> implements Serializable {

    private static final long serialVersionUID = 1L;

}
