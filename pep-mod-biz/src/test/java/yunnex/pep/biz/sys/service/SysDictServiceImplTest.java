package yunnex.pep.biz.sys.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import yunnex.pep.biz.BaseTest;
import yunnex.pep.biz.sys.dto.SysDictDto;
import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.result.BizResult;

public class SysDictServiceImplTest extends BaseTest {

    @Autowired
    private SysDictService dictService;


    @Test
    public void getByType() {
        BizResult demo = dictService.getByType("demo");
        System.out.println(demo);
        demo = dictService.getByType("demo");
        System.out.println(demo);
    }

    @Test
    public void getValue() {
        BizOptional value = dictService.getValue("demo", "hello");
        System.out.println(value);
        BizOptional value1 = dictService.getValue("demo", "foo");
        System.out.println(value1);
        BizOptional value2 = dictService.getValue("test", "hello");
        System.out.println(value2);
    }

    @Test
    public void saveRes() {
        dictService.saveRes(new SysDictDto().setType("demo").setName("foo").setValue("bar"));
    }

    @Test
    public void updateByIdRes() {
        SysDictDto dto = (SysDictDto) new SysDictDto().setType("demo").setName("foo").setValue("car").setId("1055352073005600770");
        dictService.updateByIdRes(dto);
    }

    @Test
    public void getTypes() {
        BizResult types = dictService.findTypes();
        System.out.println(types);
    }

    @Test
    public void clearAllCache() {
        BizResult bizResult = dictService.clearAllCache();
        System.out.println(bizResult);
    }

}
