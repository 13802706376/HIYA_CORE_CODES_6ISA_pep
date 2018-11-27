package ${package.Service};

import ${superServiceClassPackage};

/**
 * ${table.comment!} 服务接口
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName?substring(1)}<T> extends BaseService<T> {

}
</#if>
