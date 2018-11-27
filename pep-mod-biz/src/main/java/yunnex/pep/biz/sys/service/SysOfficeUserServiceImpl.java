package yunnex.pep.biz.sys.service;

import com.alibaba.dubbo.config.annotation.Service;

import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.biz.sys.entity.SysOfficeUser;
import yunnex.pep.biz.sys.mapper.SysOfficeUserMapper;

/**
 * 人员部门关联关系表 服务实现类
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Service
public class SysOfficeUserServiceImpl extends BaseServiceImpl<SysOfficeUserMapper, SysOfficeUser> implements SysOfficeUserService<SysOfficeUser> {


}

