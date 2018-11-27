package yunnex.pep.common.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.result.BizResult;

@SuppressWarnings("rawtypes")
public class BaseController {

    /**
     * 200 结果：执行成功
     * @return
     */
    public ResponseEntity result(BizResult<?> bizResult) {
        return ResponseEntity.ok(bizResult);
    }

    /**
     * 201 结果：创建成功
     * @param bizResult
     * @return
     */
    public ResponseEntity created(BizResult<?> bizResult) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bizResult);
    }

    /**
     * 404 结果：找不到
     * @param bizResult
     * @return
     */
    public ResponseEntity notFound(BizResult<?> bizResult) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bizResult);
    }

    /**
     * 500 结果：服务器内部错误
     * @param bizResult
     * @return
     */
    public ResponseEntity error(BizResult<?> bizResult) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(bizResult);
    }

}
