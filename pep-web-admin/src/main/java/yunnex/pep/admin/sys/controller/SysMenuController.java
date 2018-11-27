package yunnex.pep.admin.sys.controller;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;

import io.swagger.annotations.ApiOperation;
import yunnex.pep.biz.sys.dto.LoginUser;
import yunnex.pep.biz.sys.dto.SysMenuDto;
import yunnex.pep.biz.sys.service.SysMenuService;
import yunnex.pep.common.annotation.Auth;
import yunnex.pep.common.base.BaseController;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.result.BizResult;


import static yunnex.pep.common.constant.Constant.Auth.AUTHORIZATION;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author mybatis-plus
 * @since 2018-10-11
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@RestController
@RequestMapping("${admin_path}/sys")
public class SysMenuController extends BaseController {
    @Reference
    private SysMenuService menuService;
    
    @GetMapping("/menus")
    public ResponseEntity<BizResult<List<SysMenuDto>>>  findAllMenu() {
        return result(menuService.findAllMenu()); 
    }
    
    @PostMapping("/menu")
    public ResponseEntity<BizResult<?>> save(@RequestBody SysMenuDto menu) {
        return result(menuService.saveMenu(menu));
    }
   
    @GetMapping(value = "/menu/form/{id}")
    public ResponseEntity<BizResult<?>>  form(@PathVariable String id) {
      
        return result(menuService.form(id));
    }
    
    @DeleteMapping("/menu/{id}")
    public ResponseEntity<BizResult<?>>  deleteMenu(@PathVariable String id) {
        return result(menuService.deleteMenu(id));
    }
    /**
     * isShowHide是否显示隐藏菜单
     * @param extId
     * @param isShowHidden
     * @param response
     * @return
     */
    @GetMapping(value = "/menu/menuTree")
    public  ResponseEntity<BizResult<?>>getMemutreeData(String extId,String isShowHide) {
        return result(menuService.getMemutreeData(extId,isShowHide));
    }

    @ApiOperation("获取当前用户拥有的所有菜单权限")
    @GetMapping("/menus/all")
    public ResponseEntity<BizResult<Set<String>>> getChildren(@Auth(AUTHORIZATION) LoginUser loginUser) {
        return result(BizResult.success(loginUser.getMenusStr()));
    }

}
