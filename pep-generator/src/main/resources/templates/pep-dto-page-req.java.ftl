package ${package.Entity?substring(0, package.Entity?last_index_of("."))}.dto;

import java.io.Serializable;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

<#if swagger2>
import io.swagger.annotations.ApiModel;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
</#if>

/**
 * ${table.comment!} PageReqDto
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
<#if superEntityClass??>
@EqualsAndHashCode(callSuper = true)
<#else>
@EqualsAndHashCode(callSuper = false)
    </#if>
@Accessors(chain = true)
</#if>
@ToString(callSuper = true)
<#if table.convert>
@TableName("${table.name}")
</#if>
<#if swagger2>
@ApiModel(value="${table.comment!}分页请求DTO", description="${table.comment!}")
</#if>
<#if superEntityClass??>
public class ${entity}PageReqDto extends Page<${entity}PageRespDto> implements Serializable {
<#elseif activeRecord>
public class ${entity}PageReqDto extends Model<${entity}PageRespDto> implements Serializable {
<#else>
public class ${entity}PageReqDto extends Page<${entity}PageRespDto> implements Serializable {
</#if>

    private static final long serialVersionUID = 1L;

}
