package yunnex.pep.biz.demo;

import java.util.List;

import org.apache.shiro.authz.AuthorizationInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import yunnex.common.config.ConfigLoader;
import yunnex.pep.biz.BaseTest;
import yunnex.pep.biz.demo.dto.UserDto;
import yunnex.pep.biz.demo.service.UserTestServiceImpl;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.ShiroUtils;

/**
 * 用户表 单元测试
 * @author zjq
 *
 */
public class UserTest extends BaseTest
{
	
	@Autowired
	UserTestServiceImpl userService;
	@Autowired
	private ConfigLoader loader;
	@Value("${dubbo.registry.protocol}")
	private String zk;
	@Autowired
	private ShiroUtils shiroUtils;


	@Test
    public void tt() {
        UserDto userDto = new UserDto().setName("ac").setEmail("abc.com");
        userService.saved(userDto);
    }
	
	@Test
	//@Ignore
    public void save() 
	{
		UserDto userDto = new UserDto();
		userDto.setName("WADE");
		userDto.setAge(30);
		userDto.setEmail("287119069@qq.com");
		userDto.setStrategy("heat");
		BizResult<Boolean> save = userService.saveUser(userDto);
		System.out.println(save);
    }
	
	@Test
    public void findById() 
	{
		UserDto userDto =  userService.findById("1045242886313660418").getData();
        System.out.println(userDto.toString());
    }
	

    @Test
	public void t1() {
		String property = loader.getProperty("interactive_activity_share_picture_path");
		System.out.println(property);
		System.out.println(zk);
	}

    @Test
	public void t2() {
		BizResult<List<String>> allUserNames = userService.getAllUserNames();
		allUserNames.getData().forEach(System.out::println);
	}

	@Test
	public void t3() {
		AuthorizationInfo auth = shiroUtils.getAuthorizationInfo();
		boolean perm = shiroUtils.checkPermission(auth, "aaa:bbb:ccc");
		boolean role = shiroUtils.checkRole(auth, "user");
		System.out.println(perm);
		System.out.println(role);
	}

	@Test
	public void t4() {
		userService.json("1052121017732894721");
	}

}
