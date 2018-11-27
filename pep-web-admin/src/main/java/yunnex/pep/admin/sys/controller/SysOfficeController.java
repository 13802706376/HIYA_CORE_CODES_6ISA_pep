package yunnex.pep.admin.sys.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import yunnex.pep.biz.sys.dto.SysOfficeDto;
import yunnex.pep.biz.sys.dto.SysOfficePageReqDto;
import yunnex.pep.biz.sys.dto.SysOfficePageRespDto;
import yunnex.pep.biz.sys.service.SysOfficeService;
import yunnex.pep.common.base.BaseController;
import yunnex.pep.common.result.BizResult;

/**
 * 机构表 前端控制器
 *
 * @author 政经平台
 * @since 2018-10-23
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Api(tags = "机构表操作")
@RestController
@RequestMapping("${admin_path}/sys")
//@RequestMapping("/sys")
public class SysOfficeController extends BaseController {

    @Reference
	private SysOfficeService sysOfficeService;


    @ApiOperation("根据ID查询机构表")
    @GetMapping("/office/{id}")
    public ResponseEntity<BizResult<SysOfficeDto>> findById(@PathVariable String id) {
        return result(sysOfficeService.findById(id, SysOfficeDto.class));
    }

    @ApiOperation("新增机构表")
    @PostMapping("/office")
    public ResponseEntity<BizResult<Boolean>> save(@RequestBody SysOfficeDto sysOfficeDto) {
        return result(sysOfficeService.saveRes(sysOfficeDto));
    }

    @ApiOperation("修改机构表")
    @PutMapping("/office")
    public ResponseEntity<BizResult<Boolean>> update(@RequestBody SysOfficeDto sysOfficeDto) {
        return result(sysOfficeService.updateByIdRes(sysOfficeDto));
    }

    @ApiOperation("机构表页面")
    @GetMapping("/offices")
    public ResponseEntity<BizResult<?>> page() {
        return result(BizResult.success());
    }

    @ApiOperation("机构表页面分页数据列表")
    @PostMapping("/offices")
    public ResponseEntity<BizResult<IPage<SysOfficePageRespDto>>> pageData(@RequestBody SysOfficePageReqDto sysOfficePageReqDto) {
        return result(sysOfficeService.page(sysOfficePageReqDto, SysOfficePageRespDto.class));
    }
    
    @ApiOperation("机构数")
    @GetMapping("/offices/tree")
    public ResponseEntity<List<SysOfficeDto>> tree() {
        return result(sysOfficeService.findAllOffice());
    }

}
