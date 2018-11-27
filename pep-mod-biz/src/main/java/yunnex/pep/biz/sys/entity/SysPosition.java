package yunnex.pep.biz.sys.entity;

import java.io.Serializable;

import yunnex.pep.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  实体类
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysPosition extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 岗位分类  sales_zx(销售直销),sales_qd(销售渠道),no_sales(非销售)
     */
    private String positionCategory;

    /**
     * 专业级
     */
    private String professionalLevel;

    /**
     * 管级
     */
    private String manageLevel;

    /**
     * 工作职责
     */
    private String workDuty;

    /**
     * 工作要求
     */
    private String workRequire;

    /**
     * 专业通道
     */
    private String professionalChannel;

    /**
     * 专业福利图
     */
    private String professionalWealPic;

    /**
     * 管理通道
     */
    private String manageChannel;

    /**
     * 管理福利图
     */
    private String manageWealPic;

    /**
     * 是否配置面试题模板
     */
    private String interviewTemplateFlag;
    /**
     * 使用部门
     */
    private String useOffice;
}
