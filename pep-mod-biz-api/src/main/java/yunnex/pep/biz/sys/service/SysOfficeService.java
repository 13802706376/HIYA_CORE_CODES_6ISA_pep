package yunnex.pep.biz.sys.service;

import java.util.List;
import yunnex.pep.biz.sys.dto.SysOfficeDto;
import yunnex.pep.common.base.BaseService;
import yunnex.pep.common.result.BizResult;

/**
 * 机构表 服务接口
 *
 * @author 政经平台
 * @since 2018-10-23
 */
public interface SysOfficeService<T> extends BaseService<T> {
    
    BizResult<List<SysOfficeDto>> findAllOffice();
    

}
