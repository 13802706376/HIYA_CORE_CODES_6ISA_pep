package yunnex.pep.biz.sys.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import yunnex.pep.biz.sys.entity.SysDict;

/**
 * 数据字典 Mapper 接口
 *
 * @author 政经平台
 * @since 2018-10-25
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * 查找所有字典类型
     * @return
     */
    List<String> findTypes();

}
