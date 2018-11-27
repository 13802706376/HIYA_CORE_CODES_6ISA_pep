package yunnex.pep.biz.sys.entity;

import java.io.Serializable;

import yunnex.pep.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 岗位部门关联关系 实体类
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysPositionOffice extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 岗位id
     */
    private String positionId;

    /**
     * 部门id
     */
    private String officeId;


}
