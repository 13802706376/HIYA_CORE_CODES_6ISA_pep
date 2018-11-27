package yunnex.pep.biz.sys.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import yunnex.pep.biz.BaseTest;
import yunnex.pep.biz.sys.dto.SysLogPageReqDto;
import yunnex.pep.biz.sys.dto.SysLogPageRespDto;
import yunnex.pep.common.result.BizResult;

public class SysLogServiceImplTest extends BaseTest {

    @Autowired
    private SysLogService logService;


    @Test
    public void page() {
        SysLogPageReqDto reqDto = new SysLogPageReqDto();
        BizResult page = logService.page(reqDto, SysLogPageRespDto.class);
        System.out.println(page);
    }

}
