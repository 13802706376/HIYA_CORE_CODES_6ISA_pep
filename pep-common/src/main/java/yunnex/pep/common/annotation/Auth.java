package yunnex.pep.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import yunnex.pep.common.constant.Constant;

/**
 * 认证与授权信息注入注解
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {

    String value() default Constant.Auth.ID;

}
