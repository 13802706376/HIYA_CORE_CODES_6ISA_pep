package yunnex.pep.biz.sys.mapper;

import yunnex.pep.biz.sys.dto.SysOfficeDto;
import yunnex.pep.biz.sys.entity.SysOffice;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 机构表 Mapper 接口
 *
 * @author 政经平台
 * @since 2018-10-23
 */
public interface SysOfficeMapper extends BaseMapper<SysOffice> {
    
    List<SysOfficeDto> findAllOffice();
}
