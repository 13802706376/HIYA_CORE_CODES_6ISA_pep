package yunnex.pep.admin.sys.controller;

import java.util.List;

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
import yunnex.pep.biz.sys.dto.SysDictDto;
import yunnex.pep.biz.sys.dto.SysDictPageReqDto;
import yunnex.pep.biz.sys.dto.SysDictPageRespDto;
import yunnex.pep.biz.sys.service.SysDictService;
import yunnex.pep.common.base.BaseController;
import yunnex.pep.common.result.BizResult;

/**
 * 数据字典 前端控制器
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Api(tags = "数据字典操作")
@RestController
@RequestMapping("${admin_path}/sys")
public class SysDictController extends BaseController {

    @Reference
	private SysDictService sysDictService;


    @ApiOperation("根据ID查询数据字典")
    @GetMapping("/dict/{id}")
    public ResponseEntity<BizResult<SysDictDto>> findById(@PathVariable String id) {
        return result(sysDictService.findById(id, SysDictDto.class));
    }

    @ApiOperation("新增数据字典")
    @PostMapping("/dict")
    public ResponseEntity<BizResult<Boolean>> save(@RequestBody SysDictDto sysDictDto) {
        return result(sysDictService.saveRes(sysDictDto));
    }

    @ApiOperation("修改数据字典")
    @PutMapping("/dict")
    public ResponseEntity<BizResult<Boolean>> update(@RequestBody SysDictDto sysDictDto) {
        return result(sysDictService.updateByIdRes(sysDictDto));
    }

    @ApiOperation("删除数据字典")
    @DeleteMapping("/dict/{id}")
    public ResponseEntity<BizResult<Boolean>> delete(@PathVariable String id) {
        return result(sysDictService.deleteByIdRes(id));
    }

    @ApiOperation("数据字典页面")
    @GetMapping("/dicts")
    public ResponseEntity<BizResult<?>> page() {
        return result(BizResult.success());
    }

    @ApiOperation("数据字典页面分页数据列表")
    @PostMapping("/dicts")
    public ResponseEntity<BizResult<IPage<SysDictPageRespDto>>> pageData(@RequestBody SysDictPageReqDto sysDictPageReqDto) {
        return result(sysDictService.page(sysDictPageReqDto, SysDictPageRespDto.class));
    }

    @ApiOperation("查找所有字典类型")
    @GetMapping("/dict/type/all")
    public ResponseEntity<BizResult<List<String>>> findTypes() {
        return result(sysDictService.findTypes());
    }

    @ApiOperation("清空所有字典缓存")
    @PostMapping("/dict/clear_all_cache")
    public ResponseEntity<BizResult<Boolean>> clearAllCache() {
        return result(sysDictService.clearAllCache());
    }

}
