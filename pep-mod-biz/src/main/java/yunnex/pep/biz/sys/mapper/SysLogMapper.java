package yunnex.pep.biz.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import yunnex.pep.biz.sys.dto.SysLogPageReqDto;
import yunnex.pep.biz.sys.dto.SysLogPageRespDto;
import yunnex.pep.biz.sys.entity.SysLog;

/**
 * 操作日志 Mapper 接口
 *
 * @author 政经平台
 * @since 2018-10-23
 */
public interface SysLogMapper extends BaseMapper<SysLog> {

    List<SysLogPageRespDto> list(SysLogPageReqDto reqDto);

}
