package yunnex.pep.common.constant;

/**
 * 系统通用常量（非业务）
 */
public interface Constant {

    /**
     * 标识
     */
    interface Flag {
        // 是
        String Y = "Y";
        // 否
        String N = "N";
        // true
        String TRUE = "true";
        // false
        String FALSE = "false";
    }

    /**
     * 数字
     */
    interface Num {
        // 0
        Integer ZERO = 0;
        // 1
        Integer ONE = 1;
        // -1
        Integer NEGATIVE_ONE = -1;
        // 一千
        Integer THOUSAND = 1000;
    }

    /**
     * 标点符号
     */
    interface Symbol {
        /* 英文符号 */

        // "+"
        String PLUS = "+";
        // "-"
        String DASH = "-";
        // 等号
        String EQUAL = "=";
        // 大于号
        String GT = ">";
        // 小于号
        String LT = "<";

        // 句号，点号
        String PERIOD = ".";
        // 半角逗号
        String COMMA = ",";
        // 冒号
        String COLON = ":";
        // 分号
        String SEMICOLON = ";";
        // 问号
        String QUESTION = "?";
        // 感叹号
        String EXCLAMATORY_MARK = "!";
        // @符号
        String AT = "@";
        // 井号（英镑）
        String POUND_SIGN = "#";
        // 美元
        String DOLLAR = "$";
        // 百分号
        String PERCENT = "%";
        String HAT = "^";
        // 与号
        String AMPERSAND = "&";
        // 星号
        String ASTERISK = "*";
        // 左圆括号
        String OPEN_PAREN = "(";
        // 右圆括号
        String CLOSE_PAREN = ")";
        // 下划线
        String UNDERLINE = "_";
        // 竖线"|"
        String VERTICAL_LINE = "|";
        // 斜杠
        String SLASH = "/";
        // 反斜杠
        String BACK_SLASH = "\\";
        // 双引号
        String DOUBLE_QUOTATION = "\"";
        // 单引号
        String SINGLE_QUOTATION = "'";

        // 空字符串
        String EMPTY = "";
        // 一个空格
        String SPACE = " ";


        /* 中文符号 */

        // 顿号
        String DUN_HAO = "、";
        // 逗号
        String COMMA_FULL = "，";
        // 句号
        String PERIOD_FULL = "。";
    }

    /**
     * 系统
     */
    interface Sys {
        // 开发
        String DEV = "dev";
        // 测试
        String TEST = "test";
        // 生产
        String PROD = "prod";

        // 项目名前缀
        String SYS_PREFIX = "pep";
    }

    /**
     * 模式。正则表达式，Ant风格路径表达式等。
     */
    interface Pattern {
        // servlet filter 过滤所有请求
        String FILTER_ALL = "/*";
    }

    /**
     * 日期格式
     */
    interface DateFormat {
        String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
        String DATE = "yyyy-MM-dd";
        String TIME = "HH:mm:ss";
    }

    /**
     * 安全
     */
    interface Security {
        // 默认密码
        String DEFAULT_PASSWORD = "888888";
        // 管理员账号
        String ADMIN_LOGIN_NAME = "admin";
        // 登录用户ID的KEY
        String LOGIN_USER_ID = "loginUserId";
        // 删除标识
        String FLAG_DELETE_ME = "DeleteMe";
    }

    /**
     * 认证&授权信息
     */
    interface Auth {
        // 只传递认证信息
        String AUTHENTICATION = "authentication";
        // 只传递授权信息
        String AUTHORIZATION = "authorization";
        // 传递所有认证与授权信息
        String ALL = "all";
        // 只传递登录用户ID
        String ID = "id";
    }

    /**
     * HTTP请求/响应头
     */
    interface HttpHeader {
        // 登录凭证
        String AUTHORIZATION = "authorization";
        // 记住我
        String REMEMBER_ME = "remember-me";
        // 向客户端暴露的响应头
        String EXPOSE_HEADERS = String.format("%s, %s", AUTHORIZATION, REMEMBER_ME);

        String USER_AGENT = "User-Agent";
        String UNKNOWN = "unknown";
        String X_REAL_IP = "X-Real-IP";
        String X_FORWARDED_FOR = "X-Forwarded-For";
        String PROXY_CLIENT_IP = "Proxy-Client-IP";
        String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
        String HTTP_CLIENT_IP = "HTTP_CLIENT_IP";
        String HTTP_X_FORWARDED_FOR = "HTTP_X_FORWARDED_FOR";
    }

    /**
     * 数据库字段名
     */
    interface Field {
        String ID = "id";
        String CREATED_BY = "created_by";
        String CREATED_DATE = "created_date";
        String LAST_MODIFIED_BY = "last_modified_by";
        String LAST_MODIFIED_DATE = "last_modified_date";
        String DEL_FLAG = "del_flag";
        String REMARKS = "remarks";
    }

    /**
     * 实体类属性名
     */
    interface Property {
        String ID = "id";
        String CREATED_BY = "createdBy";
        String CREATED_DATE = "createdDate";
        String LAST_MODIFIED_BY = "lastModifiedBy";
        String LAST_MODIFIED_DATE = "lastModifiedDate";
        String DEL_FLAG = "delFlag";
        String REMARKS = "remarks";
    }

    interface DubboRegisterParams 
    {
        String DUBBO_REGISTER_KEY = "dubbo.registry.register";
        String DUBBO_REGISTER_ADDRESS =  "dubbo.registry.address";
        String DUBBO_REGISTER_VAL = "false";
        String DUBBO_ENV_VAR = "DUBBO_CONFIG";
        String ZKSTRING = "ZKSTRING";
        String DUBBO_CONSUMER_FILTER ="dubbo.consumer.filter";
        String DUBBO_FILTER_USER ="userFilter";
    }

    interface PepModBizDubboParams 
    {
        String PEP_MOD_BIZ = "pep-mod-biz";
        String DUBBO_APPLICATION_ID = "dubbo.application.id";
        String DUBBO_APPLICATION_NAME = "dubbo.application.name";
        String DUBBO_REGISTRY_KEY = "dubbo.registry.id";
        String DUBBO_REGISTRY_BIZ_VAL = "pep-mod-biz-registry";
        String DUBBO_BASE_PACKAGE_KEY  = "dubbo.scan.basePackages";
        String DUBBO_BASE_PACKAGE_VAL  = "yunnex.pep.biz";
        String DUBBO_PROTOCOL_PORT_KEY  = "dubbo.protocol.port";
        String DUBBO_PROTOCOL_PORT_VAL  = "12345";
        String DUBBO_CONSUMER_TIMEOUT_KEY = "dubbo.consumer.timeout";
        String DUBBO_CONSUMER_TIMEOUT_VAL = "5000000";
    }
    
    interface PepWebAdminDubboParams 
    {
        String PEP_WEB_ADMIN= "pep-web-admin";
        String DUBBO_REGISTRY_ADMIN_VAL = "pep-web-admin-registry";
    }

    /**
     * LDAP常量
     */
    interface Ldap {
        // User
        String USER = "User";

        // yunnex base dn
        String BASE_DN_YUNNEX = "dc=yunnex,dc=com";
        // dn: ou
        String DN_OU = "ou";

        // objectClass
        String ATTR_OBJECT_CLASS = "objectClass";
        // 属性：cn
        String ATTR_CN = "cn";

        // top
        String OBJECT_CLASS_TOP = "top";
        // organizationalUnit
        String OBJECT_CLASS_OU = "organizationalUnit";
        // person
        String OBJECT_CLASS_PERSON = "person";
    }

    /**
     * 缓存名称
     */
    interface Cache {
        /** 项目名 */
        String PREFIX = "pep:";
        /** 数据字典 */
        String DICT = PREFIX + "dict:";
        /** 类型 */
        String DICT_TYPES = DICT + "__types";
    }

    /**
     * 文件上传
     */
    interface File {
        /** 文件路径 配置*/
        String USERFILES_BASEDIR = "userfiles.basedir";
        /**文件地址前缀*/
        String FILE_ADDRESS_PREFIX= "file.address.prefix";
    }
}
