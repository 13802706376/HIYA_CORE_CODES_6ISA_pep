package yunnex.pep.biz.sys.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import yunnex.pep.common.base.BaseEntity;

/**
 * 角色信息 实体类
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class SysRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    private String name;
    
    /**
     * 角色说明
     */
    private String roleExplain;

    /**
     * 英文名称
     */
    private String enname;

    /**
     * 数据范围
     */
    private String dataScope;


}
