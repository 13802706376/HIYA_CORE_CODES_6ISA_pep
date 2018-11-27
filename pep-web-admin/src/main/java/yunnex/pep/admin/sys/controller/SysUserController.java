package yunnex.pep.admin.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import yunnex.common.config.ConfigLoader;
import yunnex.pep.biz.sys.dto.LoginUser;
import yunnex.pep.biz.sys.dto.SysUserDto;
import yunnex.pep.biz.sys.dto.SysUserPageReqDto;
import yunnex.pep.biz.sys.dto.SysUserPageRespDto;
import yunnex.pep.biz.sys.service.SysUserService;
import yunnex.pep.common.annotation.Auth;
import yunnex.pep.common.base.BaseController;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.result.BizResult;


import static yunnex.pep.common.constant.Constant.Auth.AUTHENTICATION;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息 前端控制器
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Api(tags = "用户信息操作")
@RestController
@RequestMapping("${admin_path}/sys")
//@RequestMapping("/sys")
public class SysUserController extends BaseController {

    @Reference
	private SysUserService sysUserService;
    @Autowired
    private ConfigLoader loader;

    @ApiOperation("根据ID查询用户信息")
    @GetMapping("/user/{id}")
    public ResponseEntity<BizResult<SysUserDto>> findById(@PathVariable String id) {
        return result(sysUserService.findById(id, SysUserDto.class));
    }

    @ApiOperation("新增用户信息")
    @PostMapping("/user")
    public ResponseEntity<BizResult<Boolean>> save(@RequestBody SysUserDto sysUserDto) {
        return result(sysUserService.saveRes(sysUserDto));
    }

    @ApiOperation("修改用户信息")
    @PutMapping("/user")
    public ResponseEntity<BizResult<Boolean>> update(@RequestBody SysUserDto sysUserDto) {
        return result(sysUserService.updateByIdRes(sysUserDto));
    }

    @ApiOperation("用户信息页面")
    @GetMapping("/users")
    public ResponseEntity<BizResult<?>> page() {
        return result(BizResult.success());
    }

    @ApiOperation("用户信息页面分页数据列表")
    @PostMapping("/users")
    public ResponseEntity<BizResult<IPage<SysUserPageRespDto>>> pageData(@RequestBody SysUserPageReqDto sysUserPageReqDto) {
        return result(sysUserService.page(sysUserPageReqDto, SysUserPageRespDto.class));
    }

    @ApiOperation("获取当前登录用户的角色和菜单权限")
    @PostMapping("/user_role_menus")
    public ResponseEntity<BizResult<LoginUser>> getUserRoleMenus(@Auth(AUTHENTICATION) LoginUser loginUser) {
        return result(sysUserService.findRoleMenus(loginUser, Constant.Flag.Y, true, true));
    }

    
    @ApiOperation("同步LDAP用户组织机构数据")
    @PostMapping("/user/sync")
    public ResponseEntity<BizResult<Boolean>> sync() {
        return result(sysUserService.syncUserData());
    }
    
    
    @ApiOperation("获取用户共用数据")
    @GetMapping("/user/publicData")
    public ResponseEntity<BizResult<Map<String, Object>>> publicData() {
        Map<String, Object>map =new HashMap<String, Object>();
        String fileAddressPrefix = loader.getProperty( Constant.File.FILE_ADDRESS_PREFIX);
        map.put("fileAddressPrefix", fileAddressPrefix);
        return result(BizResult.success(map));
    }
    
    
}
