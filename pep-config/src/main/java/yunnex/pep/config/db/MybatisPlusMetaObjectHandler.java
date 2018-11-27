package yunnex.pep.config.db;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.util.ShiroUtils;

/**
 * 为公共属性自动填充，如每次执行插入或修改操作时都要设置登录人的当前时间
 */
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        String loginUserId = ShiroUtils.getUserId(false);
        LocalDateTime now = LocalDateTime.now();
        metaObject.setValue(Constant.Property.CREATED_BY, loginUserId);
        metaObject.setValue(Constant.Property.CREATED_DATE, now);
        metaObject.setValue(Constant.Property.LAST_MODIFIED_BY, loginUserId);
        metaObject.setValue(Constant.Property.LAST_MODIFIED_DATE, now);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now();
        this.setFieldValByName(Constant.Property.LAST_MODIFIED_BY, ShiroUtils.getUserId(false), metaObject);
        this.setFieldValByName(Constant.Property.LAST_MODIFIED_DATE, now, metaObject);
    }

}
