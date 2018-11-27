package ${package.Controller};

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ${package.Entity?substring(0, package.Entity?last_index_of("."))}.dto.${entity}Dto;
import ${package.Entity?substring(0, package.Entity?last_index_of("."))}.dto.${entity}PageReqDto;
import ${package.Entity?substring(0, package.Entity?last_index_of("."))}.dto.${entity}PageRespDto;
import ${package.Service}.${table.serviceName?substring(1)};
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
import yunnex.pep.common.result.BizResult;

/**
 * ${table.comment!} 前端控制器
 *
 * @author ${author}
 * @since ${date}
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Api(tags = "${table.comment!}操作")
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("${r'${admin_path}'}/${package.ModuleName?replace('.', '/')}")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends BaseController {
<#else>
public class ${table.controllerName} extends BaseController {
</#if>

    <#assign serviceName = table.serviceName?substring(1)?uncap_first>
    <#assign dto>${entity}Dto</#assign>
    <#assign dtoName = dto?uncap_first>
    <#assign url>/${util.camelToUnderLine(entity?substring(moduleNameLength!0))}</#assign>
    @Reference
	private ${table.serviceName?substring(1)} ${serviceName};


    @ApiOperation("根据ID查询${table.comment!}")
    @GetMapping("${url}/{id}")
    public ResponseEntity<BizResult<${dto}>> findById(@PathVariable String id) {
        return result(${serviceName}.findById(id, ${dto}.class));
    }

    @ApiOperation("新增${table.comment!}")
    @PostMapping("${url}")
    public ResponseEntity<BizResult<Boolean>> save(@RequestBody ${dto} ${dtoName}) {
        return result(${serviceName}.saveRes(${dtoName}));
    }

    @ApiOperation("修改${table.comment!}")
    @PutMapping("${url}")
    public ResponseEntity<BizResult<Boolean>> update(@RequestBody ${dto} ${dtoName}) {
        return result(${serviceName}.updateByIdRes(${dtoName}));
    }

    @ApiOperation("${table.comment!}页面")
    @GetMapping("${url}s")
    public ResponseEntity<BizResult<?>> page() {
        return result(BizResult.success());
    }

    @ApiOperation("${table.comment!}页面分页数据列表")
    @PostMapping("${url}s")
    public ResponseEntity<BizResult<IPage<${entity}PageRespDto>>> pageData(@RequestBody ${entity}PageReqDto ${entity?uncap_first}PageReqDto) {
        return result(${serviceName}.page(${entity?uncap_first}PageReqDto, ${entity}PageRespDto.class));
    }

}
</#if>
