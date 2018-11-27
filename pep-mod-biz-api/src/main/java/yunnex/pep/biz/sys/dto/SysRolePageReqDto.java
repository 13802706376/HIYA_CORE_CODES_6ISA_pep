package yunnex.pep.biz.sys.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 角色信息 PageReqDto
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="角色信息分页请求DTO", description="角色信息")
public class SysRolePageReqDto extends Page<SysRolePageRespDto> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String roleName;
    
}
