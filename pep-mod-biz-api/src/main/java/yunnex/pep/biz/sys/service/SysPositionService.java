package yunnex.pep.biz.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import yunnex.pep.biz.sys.dto.SysPositionDto;
import yunnex.pep.biz.sys.dto.SysPositionPageReqDto;
import yunnex.pep.biz.sys.dto.SysPositionPageRespDto;
import yunnex.pep.common.base.BaseService;
import yunnex.pep.common.result.BizResult;

/**
 *  服务接口
 *
 * @author 政经平台
 * @since 2018-10-25
 */
public interface SysPositionService<T> extends BaseService<T> {
    BizResult<Object> savePosition( SysPositionDto sysPositionDto);
   
    BizResult<IPage<SysPositionPageRespDto>> getPositionPage(SysPositionPageReqDto sysPositionPageReqDto);
    
    BizResult<SysPositionDto>findpositionInfoById(String id);
    
    BizResult<Object>  updateUsrOfficeInfo(SysPositionDto sysPositionDto);
    
    BizResult<Object>  deletPositionById(String id);
    
    
  
}
