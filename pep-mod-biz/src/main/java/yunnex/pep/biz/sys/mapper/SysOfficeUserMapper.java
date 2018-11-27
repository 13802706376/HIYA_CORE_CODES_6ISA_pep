package yunnex.pep.biz.sys.mapper;

import yunnex.pep.biz.sys.entity.SysOfficeUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 人员部门关联关系表 Mapper 接口
 *
 * @author 政经平台
 * @since 2018-10-23
 */
public interface SysOfficeUserMapper extends BaseMapper<SysOfficeUser> {
    int updateOfficeUserCount();
}
