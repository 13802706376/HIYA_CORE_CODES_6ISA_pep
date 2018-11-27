package yunnex.pep.admin.sys.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import yunnex.pep.biz.sys.dto.SysPositionDto;
import yunnex.pep.biz.sys.dto.SysPositionPageReqDto;
import yunnex.pep.biz.sys.dto.SysPositionPageRespDto;
import yunnex.pep.biz.sys.service.SysPositionService;
import yunnex.pep.common.base.BaseController;
import yunnex.pep.common.result.BizResult;

/**
 *  前端控制器
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Api(tags = "操作")
@RestController
@RequestMapping("${admin_path}/sys")
//@RequestMapping("/sys")
public class SysPositionController extends BaseController {

    @Reference
	private SysPositionService sysPositionService;


    @ApiOperation("根据ID查询")
    @GetMapping("/position/{id}")
    public ResponseEntity<BizResult<SysPositionDto>> findpositionInfoById(@PathVariable String id) {
        return result(sysPositionService.findpositionInfoById(id));
    }

    @ApiOperation("新增/修改")
    @PostMapping("/position")
    public ResponseEntity<BizResult<Boolean>> save(@RequestBody SysPositionDto sysPositionDto) {
        return result(sysPositionService.savePosition(sysPositionDto));
    }

    @ApiOperation("修改使用部门信息")
    @PutMapping("/position")
    public ResponseEntity<BizResult<Boolean>> updateUsrOfficeInfo(@RequestBody SysPositionDto sysPositionDto) {
        return result(sysPositionService.updateUsrOfficeInfo(sysPositionDto));
    }

    @ApiOperation("页面")
    @GetMapping("/positions")
    public ResponseEntity<BizResult<?>> page() {
        return result(BizResult.success());
    }

    @ApiOperation("页面分页数据列表")
    @PostMapping("/positions")
    public ResponseEntity<BizResult<IPage<SysPositionPageRespDto>>> pageData(@RequestBody SysPositionPageReqDto sysPositionPageReqDto) {
        return result(sysPositionService.getPositionPage(sysPositionPageReqDto));
    }
    @ApiOperation("删除岗位")
    @DeleteMapping("/position/{id}")
    public ResponseEntity<BizResult<?>>  deletPositionById(@PathVariable String id) {
        return result(sysPositionService.deletPositionById(id));
    }
    
    
    
}
