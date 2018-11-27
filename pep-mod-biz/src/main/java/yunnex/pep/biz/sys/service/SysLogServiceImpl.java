package yunnex.pep.biz.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import yunnex.pep.biz.sys.dto.SysLogPageReqDto;
import yunnex.pep.biz.sys.dto.SysLogPageRespDto;
import yunnex.pep.biz.sys.entity.SysLog;
import yunnex.pep.biz.sys.mapper.SysLogMapper;
import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.common.result.BizResult;

/**
 * 操作日志 服务实现类
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Service
public class SysLogServiceImpl extends BaseServiceImpl<SysLogMapper, SysLog> implements SysLogService<SysLog> {

    @Autowired
    private SysLogMapper logMapper;


    @Override
    public <Req extends Page<Resp>, Resp> BizResult<IPage<Resp>> page(Req req, Class<Resp> resp) {
        List<SysLogPageRespDto> list = logMapper.list((SysLogPageReqDto) req);
        IPage<Resp> page = req.setRecords((List<Resp>) list);
        return BizResult.success(page);
    }

}

