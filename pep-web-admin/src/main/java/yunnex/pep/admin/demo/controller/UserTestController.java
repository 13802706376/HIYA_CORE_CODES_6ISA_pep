package yunnex.pep.admin.demo.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import yunnex.common.config.ConfigLoader;
import yunnex.pep.biz.demo.dto.UserDto;
import yunnex.pep.biz.demo.dto.UserReqDto;
import yunnex.pep.biz.demo.dto.UserRespDto;
import yunnex.pep.biz.demo.service.UserService;
import yunnex.pep.common.annotation.Log;
import yunnex.pep.common.base.BaseController;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.excel.ExportExcel;
import yunnex.pep.config.shiro.ShiroRealm;

/**
 * <p>
 * 前端控制
 * </p>
 *
 * @author mybatis-plus
 * @since 2018-09-24
 */
@Api(tags = "用户操作")
@RestController
// @RequestMapping("${admin_path}")
public class UserTestController extends BaseController
{

	@Reference
	private UserService userService;

	@Autowired
	private ConfigLoader loader;
	@Value("${common.test}")
	private String test;

	@Autowired
	private ShiroRealm shiroRealm;

	@GetMapping("/user/{id}")
	public ResponseEntity<BizResult<UserDto>> findById(@PathVariable String id)
	{
		return result(userService.findById(id));
	}

	@GetMapping("/user/export")
	public ResponseEntity<BizResult<UserDto>> export(HttpServletResponse response) {
		List<UserDto> data = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			data.add(new UserDto().setAge(i * 10).setEmail(i + "email@163.com").setName("少数人日光灯显然" + i)
					.setSalary(543.4389 * i).setBirth(LocalDateTime.now().minusDays(i)));
		}
		new ExportExcel().exportToClient(null, "abc", "导出中文名ABC.xls", response);
		return result(BizResult.success());
	}

	@Log("保存用户")
	@PostMapping("/user")
	public ResponseEntity<BizResult<Boolean>> save(@RequestBody UserDto userDto)
	{
		return result(userService.saveUser(userDto));
	}

	@Log("空返回值")
	@GetMapping("/empty")
	public void empty(@RequestParam String id, @RequestParam Integer age)
	{
		System.out.println(id + ": " + age);
		System.out.println(3/0);
	}

	@Log
	@GetMapping("/admin/shiro")
	public String shiro()
	{
		boolean permitted = SecurityUtils.getSubject().isPermitted("shiro:hello");
		boolean mvnInstall = SecurityUtils.getSubject().isPermitted("mvn:install");
		System.out.println(permitted);
		System.out.println(mvnInstall);

		Object principal = SecurityUtils.getSubject().getPrincipal();
		System.out.println(principal);

		Cache<Object, AuthorizationInfo> authorizationCache = shiroRealm.getAuthorizationCache();
		Collection<AuthorizationInfo> values = authorizationCache.values();
		System.out.println(values);

		return "shiro";
	}

	@RequiresGuest
	@GetMapping("/guest")
	public String guest()
	{
		return "@RequiresGuest";
	}

	@GetMapping("/cache/{id}")
	@Cacheable(value = "user", key = "'user:'+#id")
	public UserDto cache(@PathVariable String id)
	{
		return ((UserDto) new UserDto().setId(id)).setName("test01").setAge(30).setEmail("test01@abc.com");
	}

	@GetMapping("/user/opt")
	public ResponseEntity<BizResult<UserDto>> userOpt()
	{
		UserDto user01 = new UserDto().setName("user01").setAge(30).setEmail("user01@qq.com");
		user01.setCreatedDate(LocalDateTime.now()).setLastModifiedDate(LocalDateTime.now());
		return result(BizResult.success(user01));
	}

	@GetMapping("/user/biz")
	public ResponseEntity<BizResult<?>> userBiz()
	{
		return result(userService.getUsers("23"));
	}

	@GetMapping("/config")
	public void config() {
		String property = loader.getProperty("common.test");
		System.out.println(property);
		System.out.println(test);
	}


	// ------------ 分页 ---------------

    // 页面跳转：GET请求"/users"
    @Log
    @RequiresPermissions("xxx:yyy:zzz")
    @GetMapping("/users")
	public ResponseEntity<BizResult<?>> users() {
        return result(BizResult.success());
    }

    // 页面数据列表：POST请求"/users"
	@PostMapping("/users")
	public ResponseEntity<BizResult<IPage<UserRespDto>>> users(@RequestBody UserReqDto userReqDto) {
		return result(userService.getUserPage(userReqDto));
	}

    // ------------ 分页 ---------------


	// LDAP
	@GetMapping("/ldap")
	public ResponseEntity<BizResult<List<String>>> getAllUserNames() {
		return result(userService.getAllUserNames());
	}
}
