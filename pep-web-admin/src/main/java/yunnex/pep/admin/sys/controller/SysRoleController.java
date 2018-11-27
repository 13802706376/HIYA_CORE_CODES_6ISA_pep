package yunnex.pep.admin.sys.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import yunnex.pep.biz.sys.dto.SysRoleDto;
import yunnex.pep.biz.sys.dto.SysRolePageReqDto;
import yunnex.pep.biz.sys.dto.SysRolePageRespDto;
import yunnex.pep.biz.sys.service.SysRoleService;
import yunnex.pep.common.base.BaseController;
import yunnex.pep.common.result.BizResult;

/**
 * 角色信息 前端控制器
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Api(tags = "角色信息操作")
@RestController
@RequestMapping("${admin_path}/sys")
public class SysRoleController extends BaseController {

    @Reference
	private SysRoleService sysRoleService;


    @ApiOperation("根据ID查询角色信息")
    @GetMapping("/role/{id}")
    public ResponseEntity<BizResult<SysRoleDto>> findById(@PathVariable String id) {
        return result(sysRoleService.findRoleInfoById(id));
    }

    @ApiOperation("新增角色信息")
    @PostMapping("/role")
    public ResponseEntity<BizResult<Boolean>> save(@RequestBody SysRoleDto sysRoleDto) {
        return result(sysRoleService.saveRole(sysRoleDto));
    }
    @ApiOperation("删除角色信息")
    @DeleteMapping("/role/{id}")
    public ResponseEntity<BizResult<?>>  deleteRole(@PathVariable String id) {
        return result(sysRoleService.deletRoleInfoById(id));
    }

    @ApiOperation("角色信息页面分页数据列表")
    @PostMapping("/roles")
    public ResponseEntity<BizResult<IPage<SysRolePageRespDto>>> pageData(@RequestBody SysRolePageReqDto sysRolePageReqDto) {
        return result(sysRoleService.getRolePage(sysRolePageReqDto));
    }

}
