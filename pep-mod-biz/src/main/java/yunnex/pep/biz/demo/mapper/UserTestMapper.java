package yunnex.pep.biz.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import yunnex.pep.biz.demo.dto.UserReqDto;
import yunnex.pep.biz.demo.dto.UserRespDto;
import yunnex.pep.biz.demo.entity.User;
import yunnex.pep.biz.demo.sql.UserSqlBuilder;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mybatis-plus
 * @since 2018-09-24
 */
public interface UserTestMapper extends BaseMapper<User> {

    @SelectProvider(type = UserSqlBuilder.class, method = "queryById")
    User queryById(String id);


    @Select("select id, name, age from user")
    List<UserRespDto> getUserList(UserReqDto userReqDto);

}
