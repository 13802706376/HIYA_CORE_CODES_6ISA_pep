package yunnex.pep.biz.sys.ldap;

import java.util.List;

import javax.naming.directory.Attributes;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import lombok.extern.slf4j.Slf4j;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.common.constant.Constant.Ldap;


import static org.springframework.ldap.query.LdapQueryBuilder.query;

@Slf4j
@Repository
public class LdapDao {

    @Autowired
    protected LdapTemplate ldapTemplate;


    /**
     * 登录账号是否存在
     * 
     * @param cn
     * @return true: 存在，false: 不存在
     */
    public Boolean isCnExists(String cn) {
        if (StringUtils.isBlank(cn)) {
            return true; // 为空则当作已存在
        }
        List<String> result = ldapTemplate.search(
                        query().attributes(Ldap.ATTR_CN).where(Ldap.ATTR_OBJECT_CLASS).is(Ldap.OBJECT_CLASS_PERSON)
                                        .and(Ldap.ATTR_CN).is(cn),
                        (Attributes attributes) -> attributes.get(Ldap.ATTR_CN).get().toString());
        return !CollectionUtils.isEmpty(result);
    }

    /**
     * 用户认证。
     * 
     * @param username
     * @param password
     * @return
     */
    public boolean auth(String username, String password) {
        return auth(ldapTemplate, Constant.Symbol.EMPTY, username, password);
    }

    protected boolean auth(LdapTemplate ldapTemplate, String base, String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return false;
        }
        return ldapTemplate.authenticate(base, Ldap.ATTR_CN + Constant.Symbol.EQUAL + username, password);
    }

}
