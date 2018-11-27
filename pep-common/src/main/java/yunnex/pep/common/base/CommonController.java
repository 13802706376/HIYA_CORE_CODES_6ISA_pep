package yunnex.pep.common.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.result.BizResult;

/**
 * 公共请求映射
 */
@RestController
public class CommonController extends BaseController {


    @GetMapping("/deployment")
    public String deployment()
    {
        return "<div style='margin-top:50px;font-size:18px;font-weight:800;font-family:微软雅黑;color:green;text-align:center;margin-top:20px;'>pep-web-admin application deployment successfully!</div>";
    }

    @GetMapping("/404")
    public ResponseEntity<BizResult<Object>> notFound() {
        return notFound(BizResult.builder(CodeMsg.NOT_FOUND).build());
    }

    @GetMapping("/500")
    public ResponseEntity<BizResult<Object>> error() {
        return error(BizResult.error());
    }

}
