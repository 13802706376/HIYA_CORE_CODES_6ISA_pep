package yunnex.pep.biz.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import yunnex.pep.biz.sys.dto.SysDictDto;
import yunnex.pep.biz.sys.entity.SysDict;
import yunnex.pep.biz.sys.mapper.SysDictMapper;
import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.common.util.BeanUtils;


import static yunnex.pep.common.constant.Constant.Cache.DICT;

/**
 * 数据字典缓存操作。
 * 要使缓存生效，必须通过代理对象来调用接口，也就是说，在同一个类中调用缓存接口是不走缓存的。
 * 该SpringBean是为了对外提供一个代理对象而创建。
 */
@Service
public class SysDictCache extends BaseServiceImpl<SysDictMapper, SysDict> {

    @Autowired
    private SysDictMapper dictMapper;


    /**
     * 根据type查找，并按type存到缓存中。
     *
     * @param type
     * @return
     */
    @Cacheable(value = DICT, key = "T(yunnex.pep.common.constant.Constant.Cache).DICT+#type")
    public List<SysDictDto> getByType(String type) {
        List<SysDict> list = super.list(new QueryWrapper<SysDict>().lambda().eq(SysDict::getType, type));
        return BeanUtils.copy(list, SysDictDto.class);
    }

    /**
     * 新增时，清除当前字典类型（包含完整的字典信息）和所有字典类型（只有类型名称）的缓存
     * @param dto
     * @param <D>
     * @return
     */
    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = DICT, key = "T(yunnex.pep.common.constant.Constant.Cache).DICT+#dto.type"),
        @CacheEvict(value = DICT, key = "T(yunnex.pep.common.constant.Constant.Cache).DICT_TYPES")
    })
    public <D> Boolean saved(D dto) {
        return super.saved(dto);
    }

    /**
     * 修改时，清除当前字典类型（包含完整的字典信息）和所有字典类型（只有类型名称）的缓存
     * @param dto DTO
     * @param <D>
     * @return
     */
    @Override
    @Transactional
    @Caching(evict = {
        @CacheEvict(value = DICT, key = "T(yunnex.pep.common.constant.Constant.Cache).DICT+#dto.type"),
        @CacheEvict(value = DICT, key = "T(yunnex.pep.common.constant.Constant.Cache).DICT_TYPES")
    })
    public <D> Boolean updateByPk(D dto) {
        return super.updateByPk(dto);
    }

    /**
     * 查找所有字典类型
     * @return
     */
    @Cacheable(value = DICT, key = "T(yunnex.pep.common.constant.Constant.Cache).DICT_TYPES")
    public List<String> findTypes() {
        return dictMapper.findTypes();
    }

    /**
     * 清空所有字典缓存
     */
    @CacheEvict(value = DICT, allEntries = true)
    public Boolean clearAll() {
        return true;
    }

}
