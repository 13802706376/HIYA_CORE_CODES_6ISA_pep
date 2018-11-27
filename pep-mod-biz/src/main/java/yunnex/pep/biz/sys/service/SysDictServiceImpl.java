package yunnex.pep.biz.sys.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import yunnex.pep.biz.sys.dto.SysDictDto;
import yunnex.pep.biz.sys.dto.SysDictPageReqDto;
import yunnex.pep.biz.sys.entity.SysDict;
import yunnex.pep.biz.sys.mapper.SysDictMapper;
import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.BeanUtils;

/**
 * 数据字典 服务实现类
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Service
public class SysDictServiceImpl extends BaseServiceImpl<SysDictMapper, SysDict> implements SysDictService<SysDict> {

    @Autowired
    private SysDictCache dictCache;


    /**
     * 从缓存获取指定类型和名称对应的值
     * @param type
     * @param name
     * @return
     */
    public BizOptional<String> getValue(String type, String name) {
        List<SysDictDto> dicts = dictCache.getByType(type);
        if (dicts == null) {
            return null;
        }
        Optional<String> opt = dicts.stream().filter(dict -> dict.getName().equals(name)).findFirst().map(SysDictDto::getValue);
        return opt.isPresent() ? BizOptional.of(opt.get()) : BizOptional.empty();
    }

    /**
     * 根据type查找。
     * @param type
     * @return
     */
    public BizResult<List<SysDictDto>> getByType(String type) {
        return BizResult.success(dictCache.getByType(type));
    }

    /**
     * 新增
     * @param dto
     * @param <D>
     * @return
     */
    @Override
    @Transactional
    public <D> BizResult<Boolean> saveRes(D dto) {
        return BizResult.success(dictCache.saved(dto));
    }

    /**
     * 修改
     * @param dto DTO
     * @param <D>
     * @return
     */
    @Override
    @Transactional
    public <D> BizResult<Boolean> updateByIdRes(D dto) {
        return BizResult.success(dictCache.updateByPk(dto));
    }

    /**
     * 查找所有字典类型
     * @return
     */
    public BizResult<List<String>> findTypes() {
        return BizResult.success(dictCache.findTypes());
    }

    /**
     * 清空所有字典缓存
     * @return
     */
    public BizResult<Boolean> clearAllCache() {
        return BizResult.success(dictCache.clearAll());
    }

    @Override
    public <Req extends Page<Resp>, Resp> BizResult<IPage<Resp>> page(Req req, Class<Resp> resp) {
        SysDictPageReqDto reqDto = (SysDictPageReqDto) req;
        SysDict query = new SysDict();
        BeanUtils.copyProperties(reqDto, query);
        LambdaQueryWrapper<SysDict> queryWrapper = new QueryWrapper().lambda();
        if (StringUtils.isNotBlank(query.getType())) {
            queryWrapper.eq(SysDict::getType, query.getType());
        }
        if (StringUtils.isNotBlank(query.getName())) {
            queryWrapper.like(SysDict::getName, query.getName());
        }
        queryWrapper.orderByDesc(SysDict::getLastModifiedDate);
        IPage<SysDict> page = page((IPage) req, queryWrapper);
        return copyPage(page, resp);
    }

}

