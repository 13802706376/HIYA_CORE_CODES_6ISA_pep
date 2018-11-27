package yunnex.pep.biz.sys.entity;

import java.io.Serializable;

import yunnex.pep.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 机构表 实体类
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysOffice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 父级编号
     */
    private String parentId;

    /**
     * 所有父级编号
     */
    private String parentIds;

    /**
     * 名称
     */
    private String name;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 归属区域
     */
    private String areaId;

    /**
     * 区域编码
     */
    private String code;

    /**
     * 机构类型
     */
    private String type;

    /**
     * 机构等级
     */
    private String grade;

    /**
     * LDAP DN
     */
    private String ldapDn;

    /**
     * 是否启用
     */
    private String useAble;
    
    /**
     * 部门人数
     */
    private int  userCount;


}
