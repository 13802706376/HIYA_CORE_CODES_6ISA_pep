package yunnex.pep.biz.sys.ldap;

import yunnex.pep.common.result.BizResult;

public interface LdapService {

    BizResult<Boolean> auth(String loginName, String password);

}
