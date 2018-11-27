package yunnex.pep.biz.sys.entity;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import yunnex.pep.common.base.BaseEntity;

/**
 * 操作日志 实体类
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysLog extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 操作人
     */
    private String userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 方法参数
     */
    private String methodParams;

    /**
     * 请求方式
     */
    private String httpMethod;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 查询参数
     */
    private String queryParams;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * IP
     */
    private String ip;

    /**
     * 耗时(ms)
     */
    private Integer cost;

    /**
     * 异常标记
     */
    private String exFlag;

    /**
     * 异常堆栈
     */
    private String exception;


}
