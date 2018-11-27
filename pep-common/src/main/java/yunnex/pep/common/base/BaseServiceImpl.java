package yunnex.pep.common.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.exception.BizException;
import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.BeanUtils;
import yunnex.pep.common.util.validate.Default;
import yunnex.pep.common.util.validate.ValidateUtils;

/**
 * 基础服务实现类，提供通用实现
 *
 * @param <M>
 * @param <T>
 */
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends ServiceImpl<M, T> implements BaseService<T> {

    @Autowired
    protected Validator validator;
    @Autowired
    protected ObjectMapper objectMapper;


    @Override
    public <D> D fetchById(Serializable id, Class<D> dto) {
        T t = super.getById(id);
        if (t != null) {
            try {
                D d = dto.newInstance();
                BeanUtils.copyProperties(t, d);
                return d;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new BizException(e);
            }
        }
        return null;
    }

    @Override
    public <D> BizOptional<D> queryById(Serializable id, Class<D> dto) {
        return BizOptional.of(fetchById(id, dto));
    }

    @Override
    public <D> BizResult<D> findById(Serializable id, Class<D> dto) {
        return BizResult.success(fetchById(id, dto));
    }

    /**
     * 创建泛型实体类对象
     *
     * @return
     */
    protected T newInstance() {
        try {
            ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
            Class<T> type = (Class<T>) superClass.getActualTypeArguments()[Constant.Num.ONE];
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new BizException(e);
        }
    }

    @Transactional
    @Override
    public <D> Boolean saved(D dto) {
        validateWithException(dto);
        T entity = newInstance();
        BeanUtils.copyProperties(dto, entity);
        boolean result = super.save(entity);
        BeanUtils.copyProperties(entity, dto);
        return result;
    }

    @Transactional
    @Override
    public <D> BizOptional<Boolean> saveOpt(D dto) {
        return BizOptional.of(saved(dto));
    }

    @Transactional
    @Override
    public <D> BizResult<Boolean> saveRes(D dto) {
        return BizResult.success(saved(dto));
    }

    @Transactional
    @Override
    public <D> Boolean updateByPk(D dto) {
        validateWithException(dto);
        T entity = newInstance();
        BeanUtils.copyProperties(dto, entity);
        return super.updateById(entity);
    }

    @Transactional
    @Override
    public <D> BizOptional<Boolean> updateByIdOpt(D dto) {
        return BizOptional.of(updateByPk(dto));
    }

    @Transactional
    @Override
    public <D> BizResult<Boolean> updateByIdRes(D dto) {
        return BizResult.success(updateByPk(dto));
    }

    @Transactional
    @Override
    public BizOptional<Boolean> deleteByIdOpt(Serializable id) {
        return BizOptional.of(super.removeById(id));
    }

    @Transactional
    @Override
    public BizResult<Boolean> deleteByIdRes(Serializable id) {
        return BizResult.success(super.removeById(id));
    }

    @Transactional
    @Override
    public <D extends BaseDto> Boolean insertOrUpdate(D dto) {
        if (dto.getId() == null) {
            return this.saved(dto);
        } else {
            return this.updateByPk(dto);
        }
    }

    @Transactional
    @Override
    public <D extends BaseDto> BizOptional<Boolean> insertOrUpdateOpt(D dto) {
        return BizOptional.of(insertOrUpdate(dto));
    }

    @Transactional
    @Override
    public <D extends BaseDto> BizResult<Boolean> insertOrUpdateRes(D dto) {
        return BizResult.success(insertOrUpdate(dto));
    }

    @Override
    public <Req extends Page<Resp>, Resp> BizResult<IPage<Resp>> page(Req req, Class<Resp> resp) {
        IPage<T> page = super.page((Page) req, new QueryWrapper<T>().orderByDesc(Constant.Field.LAST_MODIFIED_DATE));
        return copyPage(page, resp);
    }

    /**
     * 复制分页结果，将实体对象数据复制到DTO对象
     * @param page
     * @param resp
     * @param <T>
     * @param <Resp>
     * @return
     */
    protected <T, Resp> BizResult<IPage<Resp>> copyPage(IPage<T> page, Class<Resp> resp) {
        IPage<Resp> result = new Page<>();
        List<T> records = page.getRecords();
        page.setRecords(null);
        BeanUtils.copyProperties(page, result);
        if (CollectionUtils.isNotEmpty(records)) {
            result.setRecords(BeanUtils.copy(records, resp));
        }
        return BizResult.success(result);
    }

    /**
     * 校验默认分组Default.class
     * @param dto
     * @param <D>
     */
    protected <D> void validateWithException(D dto) {
        ValidateUtils.validateWithException(validator, dto, Default.class);
    }

    /**
     * jackson: 对象转json
     * @param o
     * @return
     */
    protected String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            String msg = "json转换异常！";
            log.error(msg, e);
            throw new BizException(msg, e);
        }
    }

}
