package yunnex.pep.biz.sys.dto;

import java.io.Serializable;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 人员部门关联关系表 PageReqDto
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="人员部门关联关系表分页请求DTO", description="人员部门关联关系表")
public class SysOfficeUserPageReqDto extends Page<SysOfficeUserPageRespDto> implements Serializable {

    private static final long serialVersionUID = 1L;

}
