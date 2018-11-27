package yunnex.pep.biz.demo.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Validator;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import yunnex.pep.biz.demo.dto.UserDto;
import yunnex.pep.biz.demo.dto.UserReqDto;
import yunnex.pep.biz.demo.dto.UserRespDto;
import yunnex.pep.biz.demo.entity.User;
import yunnex.pep.biz.demo.mapper.UserTestMapper;
import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.ShiroUtils;
import yunnex.pep.common.util.excel.ExportExcel;
import yunnex.pep.common.util.validate.ValidateUtils;


import static org.springframework.ldap.query.LdapQueryBuilder.query;

/**
 * <p>
 * 逻辑实现类
 * </p>
 *
 * @author mybatis-plus
 * @since 2018-09-24
 */
@com.alibaba.dubbo.config.annotation.Service
public class UserTestServiceImpl extends BaseServiceImpl<UserTestMapper, User> implements UserService<User> {

    @Autowired
    private UserTestMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${common.test}")
    private String hello;
    @Autowired
    private Validator validator;


    public static void main(String[] args) throws IOException {
        List<UserDto> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new UserDto().setAge(i * 10).setEmail(i + "email@163.com").setName("少数人日光灯显然" + i)
            .setSalary(543.4389 * i).setBirth(LocalDateTime.now().minusDays(i)));
        }
        new ExportExcel().exportToFile(data, "abc", "D:/导出Excel.xls");
    }

    @Transactional
    @Override
    public BizResult<Boolean> saveUser(UserDto userDto) {
        ValidateUtils.validateWithException(validator, userDto);
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return BizResult.success(save(user));
    }

    @Cacheable(value = "user_service", key = "'user:'+#id")
    @Override
    public BizResult<UserDto> findById(String id) {
        UserDto userDto = super.fetchById(id, UserDto.class);
        redisTemplate.opsForValue().set("abc_xyz", userDto);
        try {
            System.out.println(objectMapper.writeValueAsString(userDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return BizResult.success(userDto);
    }

    @Override
    public BizResult<Boolean> updateById(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        return BizResult.success(updateById(user));
    }

    @Override
    public BizResult<Boolean> deleteById(String id) {
        return BizResult.success(removeById(id));
    }

    @Override
    public BizResult<List<UserDto>> getUsers(String id) {
        ShiroUtils.getUserId();
        // System.out.println(3/0);
        return BizResult.<List<UserDto>>builder(CodeMsg.SUCCESS, new ArrayList<>()).build();
    }

    @Override
    public BizResult<String> sayHello(String msg) {
        return BizResult.builder(CodeMsg.SUCCESS, "hello " + msg).build();
    }




    public void t5() {
        // UPDATE demo SET name=? WHERE id = ? AND email = ?
        User user = new User().setName("update");
        int rows = baseMapper.update(user, new UpdateWrapper<User>().lambda().eq(User::getId, 10000L).eq(User::getEmail, "abc"));

        int rows5 = baseMapper.delete(new QueryWrapper<User>().lambda().eq(User::getId, 100L));
        System.out.println("rows5: " + rows5);



        List<User> users2 = baseMapper.selectList(new QueryWrapper<User>().lambda().le(User::getId, 3L));
        System.out.println(users2);

        Integer count2 = baseMapper.selectCount(new QueryWrapper<User>().lambda().like(User::getEmail, "baomidou"));
        System.out.println("count2: " + count2);
    }



    /**
     * 模拟根据用户id查询返回用户的所有权限，实际查询语句参考：
     * SELECT p.pval FROM perm p, role_perm rp, user_role ur
     * WHERE p.pid = rp.perm_id AND ur.role_id = rp.role_id
     * AND ur.user_id = #{userId}
     * @param uid
     * @return
     */
    public Set<String> getPermsByUserId(String uid){
        Set<String> perms = new HashSet<>();
        //三种编程语言代表三种角色：js程序员、java程序员、c++程序员
        //js程序员的权限
        perms.add("static:edit");
        //c++程序员的权限
        perms.add("hardware:debug");
        //java程序员的权限
        perms.add("mvn:install");
        perms.add("mvn:clean");
        perms.add("mvn:test");
        return perms;
    }


    /**
     * 模拟根据用户id查询返回用户的所有角色，实际查询语句参考：
     * SELECT r.rval FROM role r, user_role ur
     * WHERE r.rid = ur.role_id AND ur.user_id = #{userId}
     * @param uid
     * @return
     */
    public Set<String> getRolesByUserId(String uid){
        Set<String> roles = new HashSet<>();
        //三种编程语言代表三种角色：js程序员、java程序员、c++程序员
        roles.add("js");
        roles.add("java");
        roles.add("cpp");
        return roles;
    }

    @Override
    public BizResult<IPage<UserRespDto>> getUserPage(UserReqDto userReqDto) {
        // mybatis-plus拦截器在遇到分页参数Page时，会自动计算总数
        List<UserRespDto> userList = userMapper.getUserList(userReqDto);
        IPage<UserRespDto> userRespPage = userReqDto.setRecords(userList);
        return BizResult.success(userRespPage);
    }

    /**
     * LDAP获取所有用户名
     * @return
     */
    public BizResult<List<String>> getAllUserNames() {
        System.out.println(hello);
        return BizResult.success(ldapTemplate.search(query()
                .attributes("cn")
                .where("objectClass").is("person"),
                (AttributesMapper<String>) attributes -> attributes.get("cn").get().toString()
        ));
    }

    public BizResult<UserDto> json(String id) {
        UserDto userDto = super.fetchById(id, UserDto.class);
        try {
            System.out.println(objectMapper.writeValueAsString(userDto));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return BizResult.success(userDto);
    }

}
