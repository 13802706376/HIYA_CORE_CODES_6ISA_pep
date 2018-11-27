package yunnex.pep.biz.sys.ldap;

import java.util.Hashtable;

import javax.annotation.PostConstruct;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;

import yunnex.pep.common.result.BizResult;

@Service
public class LdapServiceImpl implements LdapService {

    @Autowired
    private LdapDao ldapDao;

   
    @Value("${ldap_url}")
    private String LDAP_URL; // NOSONAR

    @Value("${ldap_admin_name}")
    private String LDAP_ADMIN_NAME; // NOSONAR

    @Value("${ldap_admin_password}")
    private String LDAP_ADMIN_PASSWORD; // NOSONAR

    private static LdapContext ctx = null;

    /**
     * 初始化ldap
     */
    @PostConstruct
    public void initLdap() {
        Hashtable<String,String> hashEnv = new Hashtable<String,String> ();
        hashEnv.put(Context.SECURITY_AUTHENTICATION, "simple"); // LDAP访问安全级别
        hashEnv.put(Context.SECURITY_PRINCIPAL, LDAP_ADMIN_NAME); // AD User
        hashEnv.put(Context.SECURITY_CREDENTIALS, LDAP_ADMIN_PASSWORD); // AD Password
        hashEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory"); // LDAP工厂类
        hashEnv.put(Context.PROVIDER_URL, LDAP_URL);
        try {
            ctx = new InitialLdapContext(hashEnv, null);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public LdapContext getLdapContext() {
        // 该对象是 服务器启动的时候初始化，长时间可能断开连接，每次获取ldap上下文的时候初始化
        initLdap();
        return ctx;
    }
    
    /**
     * LDAP认证
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public BizResult<Boolean> auth(String loginName, String password) {
        return BizResult.success(ldapDao.auth(loginName, password));
    }

}
