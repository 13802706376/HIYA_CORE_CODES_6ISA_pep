package yunnex.pep.common.util.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import yunnex.pep.common.exception.ValidateException;

/**
 * JSR校验工具类。
 * 为了更好的扩展性，请给所有校验注解指定分组group，因为如果开始没有指定分组，后来又有这种需要，
 * 那么原来没有指定分组的校验将失效，因为不知道你要校验的是哪组规则，这是JSR的规定。
 */
public abstract class ValidateUtils {

    private static final String MSG_NOT_NULL = "数据不能为空！";

    /**
     * 校验，如果有错误，取出第一条错误消息
     * @param validator
     * @param object
     * @param groups
     * @return
     */
    public static String validateWithMessage(Validator validator, Object object, Class<?>... groups) { // NOSONAR
        if (object == null) {
            return MSG_NOT_NULL;
        }
        String msg = null;
        Set<ConstraintViolation<Object>> result = validator.validate(object, groups);
        if (!result.isEmpty()) {
            msg = result.iterator().next().getMessage();
        }
        return msg;
    }

    /**
     * 调用JSR303的validate方法, 验证失败时抛出异常
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void validateWithException(Validator validator, Object object, Class<?>... groups) { // NOSONAR
        if (object == null) {
            throw new ValidateException(MSG_NOT_NULL);
        }
        Set<ConstraintViolation<Object>> result = validator.validate(object, groups);
        if (!result.isEmpty()) {
            throw new ValidateException(result.iterator().next().getMessage());
        }
    }

}
