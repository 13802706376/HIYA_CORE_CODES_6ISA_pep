package yunnex.pep.biz.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import yunnex.pep.biz.sys.dto.SysOfficeDto;
import yunnex.pep.biz.sys.entity.SysOffice;
import yunnex.pep.biz.sys.mapper.SysOfficeMapper;
import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.tree.TreeUtils;

/**
 * 机构表 服务实现类
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Service
public class SysOfficeServiceImpl extends BaseServiceImpl<SysOfficeMapper, SysOffice> implements SysOfficeService<SysOffice> {
    @Autowired
    private SysOfficeMapper sysOfficeMapper;
    
    @Override
    public BizResult<List<SysOfficeDto>> findAllOffice() {
        List<SysOfficeDto>  office = sysOfficeMapper.findAllOffice();
        List<SysOfficeDto>  resultOffice =  TreeUtils.tree(office);
        return  BizResult.success(resultOffice);         
    }


}

