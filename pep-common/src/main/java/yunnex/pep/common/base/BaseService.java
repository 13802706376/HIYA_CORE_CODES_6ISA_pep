package yunnex.pep.common.base;

import java.io.Serializable;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.result.BizResult;

/**
 * 基础服务接口，定义通用方法
 *
 * @param <T>
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 根据主键查询，封装结果到指定类型的DTO
     *
     * @param id 主键
     * @param dto DTO
     * @param <D> DTO
     * @return DTO
     */
    <D> D fetchById(Serializable id, Class<D> dto);

    /**
     * 根据主键查询，封装结果到指定类型的DTO，返回BizOptional
     *
     * @param id
     * @param dto
     * @param <D>
     * @return
     */
    <D> BizOptional<D> queryById(Serializable id, Class<D> dto);

    /**
     * 根据主键查询，封装结果到指定类型的DTO，返回BizResult
     *
     * @param id
     * @param dto
     * @param <D>
     * @return
     */
    <D> BizResult<D> findById(Serializable id, Class<D> dto);

    /**
     * 将指定类型的DTO转换为Entity之后保存
     *
     * @param dto DTO
     * @param <D> DTO
     * @return DTO
     */
    <D> Boolean saved(D dto);

    /**
     * 将指定类型的DTO转换为Entity之后保存，返回BizOptional
     *
     * @param dto DTO
     * @param <D> DTO
     * @return
     */
    <D> BizOptional<Boolean> saveOpt(D dto);

    /**
     * 将指定类型的DTO转换为Entity之后保存，返回BizResult
     *
     * @param dto
     * @param <D>
     * @return
     */
    <D> BizResult<Boolean> saveRes(D dto);

    /**
     * 将指定类型的DTO转换为Entity之后，根据主键修改
     *
     * @param dto DTO
     * @param <D> DTO
     * @return DTO
     */
    <D> Boolean updateByPk(D dto);

    /**
     * 将指定类型的DTO转换为Entity之后，根据主键修改，返回BizOptional
     *
     * @param dto DTO
     * @param <D> DTO
     * @return
     */
    <D> BizOptional<Boolean> updateByIdOpt(D dto);

    /**
     * 将指定类型的DTO转换为Entity之后，根据主键修改，返回BizResult
     *
     * @param dto DTO
     * @param <D> DTO
     * @return
     */
    <D> BizResult<Boolean> updateByIdRes(D dto);

    /**
     * 根据主键删除，返回BizOptional
     *
     * @param id
     * @return
     */
    BizOptional<Boolean> deleteByIdOpt(Serializable id);

    /**
     * 根据主键删除，返回BizResult
     *
     * @param id
     * @return
     */
    BizResult<Boolean> deleteByIdRes(Serializable id);

    /**
     * 新增或修改。如果ID为空，新增，否则修改。
     *
     * @param dto
     * @return
     */
    <D extends BaseDto> Boolean insertOrUpdate(D dto);

    /**
     * 新增或修改。如果ID为空，新增，否则修改。返回BizOptional
     *
     * @param dto
     * @param <D>
     * @return
     */
    <D extends BaseDto> BizOptional<Boolean> insertOrUpdateOpt(D dto);

    /**
     * 新增或修改。如果ID为空，新增，否则修改。返回BizResult
     *
     * @param dto
     * @param <D>
     * @return
     */
    <D extends BaseDto> BizResult<Boolean> insertOrUpdateRes(D dto);

    /**
     * 单表分页，按修改时间倒序排列
     *
     * @param req
     * @param <Req>
     * @param <Resp>
     * @return
     */
    <Req extends Page<Resp>, Resp> BizResult<IPage<Resp>> page(Req req, Class<Resp> resp);
}
