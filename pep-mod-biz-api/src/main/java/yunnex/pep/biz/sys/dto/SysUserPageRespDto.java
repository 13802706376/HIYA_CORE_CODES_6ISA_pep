package yunnex.pep.biz.sys.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import yunnex.pep.common.base.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户信息 PageRespDto
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
@ApiModel(value="用户信息分页响应DTO", description="用户信息")
public class SysUserPageRespDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "锁定标识")
    private String lockFlag;

    @ApiModelProperty(value = "最后登陆IP")
    private String lastLoginedIp;

    @ApiModelProperty(value = "最后登陆时间")
    private LocalDateTime lastLoginedDate;


}
