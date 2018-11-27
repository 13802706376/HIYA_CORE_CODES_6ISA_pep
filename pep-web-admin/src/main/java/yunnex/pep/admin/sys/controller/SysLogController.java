package yunnex.pep.admin.sys.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import yunnex.pep.biz.sys.dto.SysLogDto;
import yunnex.pep.biz.sys.dto.SysLogPageReqDto;
import yunnex.pep.biz.sys.dto.SysLogPageRespDto;
import yunnex.pep.biz.sys.service.SysLogService;
import yunnex.pep.common.base.BaseController;
import yunnex.pep.common.result.BizResult;

/**
 * 操作日志 前端控制器
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Api(tags = "操作日志操作")
@RestController
@RequestMapping("${admin_path}/sys")
public class SysLogController extends BaseController {

    @Reference
	private SysLogService sysLogService;


    @ApiOperation("根据ID查询操作日志")
    @GetMapping("/log/{id}")
    public ResponseEntity<BizResult<SysLogDto>> findById(@PathVariable String id) {
        return result(sysLogService.findById(id, SysLogDto.class));
    }

    @ApiOperation("操作日志页面")
    @GetMapping("/logs")
    public ResponseEntity<BizResult<?>> page() {
        return result(BizResult.success());
    }

    @ApiOperation("操作日志页面分页数据列表")
    @PostMapping("/logs")
    public ResponseEntity<BizResult<IPage<SysLogPageRespDto>>> pageData(@RequestBody SysLogPageReqDto sysLogPageReqDto) {
        return result(sysLogService.page(sysLogPageReqDto, SysLogPageRespDto.class));
    }

}
