package yunnex.pep.biz.sys.service;

import java.util.List;

import yunnex.pep.biz.sys.dto.SysDictDto;
import yunnex.pep.common.base.BaseService;
import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.result.BizResult;

/**
 * 数据字典 服务接口
 *
 * @author 政经平台
 * @since 2018-10-25
 */
public interface SysDictService<T> extends BaseService<T> {

    BizOptional<String> getValue(String type, String name);

    BizResult<List<SysDictDto>> getByType(String type);

    BizResult<List<String>> findTypes();

    BizResult<Boolean> clearAllCache();

}
