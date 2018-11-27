package yunnex.pep.biz.demo.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.core.metadata.IPage;

import yunnex.pep.biz.demo.dto.UserDto;
import yunnex.pep.biz.demo.dto.UserReqDto;
import yunnex.pep.biz.demo.dto.UserRespDto;
import yunnex.pep.common.base.BaseService;
import yunnex.pep.common.result.BizResult;

/**
 * <p>
 *  服务类
 *
 *  Service层只返回 Optional 和 BizResult 包装的对象
 * </p>
 *
 * @author mybatis-plus
 * @since 2018-09-24
 */
public interface UserService<T> extends BaseService<T> {

    BizResult<Boolean> saveUser(UserDto userDto);

    BizResult<UserDto> findById(String id);

    BizResult<Boolean> updateById(UserDto userDto);

    BizResult<Boolean> deleteById(String id);

    BizResult<List<UserDto>> getUsers(String id);

    BizResult<String> sayHello(String msg);


    Set<String> getPermsByUserId(String uid);

    Set<String> getRolesByUserId(String uid);

    BizResult<IPage<UserRespDto>> getUserPage(UserReqDto userReqDto);

    BizResult<List<String>> getAllUserNames();

    BizResult<UserDto> json(String id);

}
