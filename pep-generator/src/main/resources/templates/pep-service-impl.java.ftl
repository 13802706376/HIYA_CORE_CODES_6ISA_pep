package ${package.ServiceImpl};

import com.alibaba.dubbo.config.annotation.Service;

import lombok.extern.slf4j.Slf4j;
import ${superServiceImplClassPackage};
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};

/**
 * ${table.comment!} 服务实现类
 *
 * @author ${author}
 * @since ${date}
 */
@Slf4j
@Service
public class ${table.serviceImplName} extends BaseServiceImpl<${table.mapperName}, ${entity}> implements ${table.serviceName?substring(1)}<${entity}> {

}

