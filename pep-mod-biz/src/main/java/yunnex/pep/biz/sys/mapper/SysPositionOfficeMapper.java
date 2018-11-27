package yunnex.pep.biz.sys.mapper;

import yunnex.pep.biz.sys.entity.SysPositionOffice;

import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 岗位部门关联关系 Mapper 接口
 *
 * @author 政经平台
 * @since 2018-10-25
 */
public interface SysPositionOfficeMapper extends BaseMapper<SysPositionOffice> {
    int addBatchPositionOfficeInfo(Map<String, Object>map);
}
