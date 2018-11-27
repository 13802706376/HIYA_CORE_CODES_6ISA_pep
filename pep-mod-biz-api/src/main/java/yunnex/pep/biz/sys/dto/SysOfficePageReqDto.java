package yunnex.pep.biz.sys.dto;

import java.io.Serializable;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 机构表 PageReqDto
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="机构表分页请求DTO", description="机构表")
public class SysOfficePageReqDto extends Page<SysOfficePageRespDto> implements Serializable {

    private static final long serialVersionUID = 1L;

}
