package yunnex.pep.common.util;

import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 扩展spring的BeanUtils，支持复制复杂对象，如集合，嵌套对象等
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {


    /**
     * 深复制集合
     * @param source
     * @param targetClass
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> copy(Collection<S> source, Class<T> targetClass) {
        return JSON.parseArray(JSON.toJSONString(source), targetClass);
    }


    /**
     * 深复制对象
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T copyObject(Object source, Class<T> targetClass) {
        return JSON.parseObject(JSON.toJSONString(source), targetClass);
    }


}
