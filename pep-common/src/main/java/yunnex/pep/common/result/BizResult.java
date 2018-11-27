package yunnex.pep.common.result;

import java.io.Serializable;

import lombok.Data;
import yunnex.pep.common.constant.CodeMsg;

/**
 * 业务wrapper. service层向controller层返回结果的封装器。
 * @param <D>
 */
@Data
public class BizResult<D> implements Serializable {

    private int code;
    private String msg;
    private D data;

    private BizResult() {}

    BizResult(int code, String msg, D data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 状态码为200表示成功
     * @return
     */
    public Boolean isSuccess() {
        return CodeMsg.SUCCESS.getCode() == this.code;
    }

    /**
     * 状态码为200，并且数据不为空
     * @param bizResult
     * @return
     */
    public static Boolean isSuccessful(BizResult bizResult) {
        return bizResult != null && bizResult.getCode() == CodeMsg.SUCCESS.getCode() && bizResult.getData() != null;
    }

    public static <D> BizResult<D> success() {
        return success(null);
    }

    /**
     * 成功结果
     * @param data
     * @param <D>
     * @return
     */
    public static <D> BizResult<D> success(D data) {
        return BizResult.builder(CodeMsg.SUCCESS, data).build();
    }

    public static <D> BizResult<D> error() {
        return error(null);
    }

    /**
     * 系统异常
     * @param msg
     * @param <D>
     * @return
     */
    public static <D> BizResult<D> error(String msg) {
        return msg(CodeMsg.ERROR, msg);
    }

    /**
     * 参数不合法。
     * @param <D>
     * @return
     */
    public static <D> BizResult<D> illegalArg() {
        return msg(CodeMsg.ILLEGAL_ARG, null);
    }

    /**
     * 参数不合法，自定义消息内容。
     * @param msg
     * @param <D>
     * @return
     */
    public static <D> BizResult<D> illegalArg(String msg) {
        return msg(CodeMsg.ILLEGAL_ARG, msg);
    }

    /**
     * 复用已定义的状态码，修改消息内容。如参数不合法。
     * @param codeMsg
     * @param msg
     * @param <D>
     * @return
     */
    public static <D> BizResult<D> msg(CodeMsg codeMsg, String msg) {
        BizResultBuilder<D> builder = BizResult.<D>builder(codeMsg);
        if (msg != null) {
            builder.msg(msg);
        }
        return builder.build();
    }

    public static <D> BizResultBuilder<D> builder(CodeMsg codeMsg) {
        return BizResult.<D>builder().codeMsg(codeMsg);
    }

    public static <D> BizResultBuilder<D> builder(CodeMsg codeMsg, D data) {
        return BizResult.<D>builder().codeMsg(codeMsg).data(data);
    }

    public static <D> BizResultBuilder<D> builder(int code, String msg, D data) {
        return BizResult.<D>builder().code(code).msg(msg).data(data);
    }

    public static <D> BizResultBuilder<D> builder() {
        return new BizResultBuilder();
    }

    public static class BizResultBuilder<D> {
        private int code;
        private String msg;
        private D data;

        BizResultBuilder() {
        }

        public BizResultBuilder<D> code(int code) {
            this.code = code;
            return this;
        }

        public BizResultBuilder<D> msg(String msg) {
            this.msg = msg;
            return this;
        }

        public BizResultBuilder<D> data(D data) {
            this.data = data;
            return this;
        }

        public BizResultBuilder<D> codeMsg(CodeMsg codeMsg) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
            return this;
        }

        public BizResult<D> build() {
            return new BizResult(this.code, this.msg, this.data);
        }

        public String toString() {
            return "BizResult.BizResultBuilder(code=" + this.code + ", msg=" + this.msg + ", data=" + this.data + ")";
        }
    }
}
