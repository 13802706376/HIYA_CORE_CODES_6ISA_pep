package yunnex.pep.biz.sys.mapper;

import yunnex.pep.biz.sys.dto.SysPositionPageReqDto;
import yunnex.pep.biz.sys.dto.SysPositionPageRespDto;
import yunnex.pep.biz.sys.entity.SysPosition;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 *  Mapper 接口
 *
 * @author 政经平台
 * @since 2018-10-25
 */
public interface SysPositionMapper extends BaseMapper<SysPosition> {
    List<SysPositionPageRespDto> getPositionPage(SysPositionPageReqDto sysPositionPageReqDto);
}
