package yunnex.pep.biz.sys.service;

import com.alibaba.dubbo.config.annotation.Service;

import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.biz.sys.entity.SysPositionOffice;
import yunnex.pep.biz.sys.mapper.SysPositionOfficeMapper;

/**
 * 岗位部门关联关系 服务实现类
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Service
public class SysPositionOfficeServiceImpl extends BaseServiceImpl<SysPositionOfficeMapper, SysPositionOffice> implements SysPositionOfficeService<SysPositionOffice> {


}

