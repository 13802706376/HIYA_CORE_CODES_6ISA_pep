package yunnex.pep.biz.sys.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import yunnex.pep.biz.sys.dto.LoginUser;
import yunnex.pep.biz.sys.dto.SysMenuDto;
import yunnex.pep.biz.sys.dto.SysOfficeDto;
import yunnex.pep.biz.sys.dto.SysRoleDto;
import yunnex.pep.biz.sys.dto.SysUserDto;
import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.constant.Constant;
import yunnex.pep.biz.sys.entity.SysOffice;
import yunnex.pep.biz.sys.entity.SysOfficeUser;
import yunnex.pep.biz.sys.entity.SysUser;
import yunnex.pep.biz.sys.ldap.LdapServiceImpl;
import yunnex.pep.biz.sys.mapper.SysOfficeMapper;
import yunnex.pep.biz.sys.mapper.SysOfficeUserMapper;
import yunnex.pep.biz.sys.mapper.SysUserMapper;
import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.result.BizResult;

/**
 * 用户信息 服务实现类
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService<SysUser> {
    private static Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private LdapServiceImpl ldapServiceImpl;
    @Autowired
    private  SysUserMapper  sysUserMapper;
    @Autowired
    private SysOfficeMapper sysOfficeMapper;
    @Autowired
    private SysOfficeUserMapper sysOfficeUserMapper;
    
    /** 根组织查询路径值 */
    private static final String ROOT_OFFICE_SEARCH_BASE = "ou=weixin,ou=Group,dc=yunnex,dc=com";
    /** 根组织的parentId值 */
    private static final String ROOT_PARENT_ID = "0";
    /** 根用户查询路径值 */
    private static final String ROOT_USER_SEARCH_BASE = "ou=weixin,ou=User,dc=yunnex,dc=com";
    /** 指定的组织部门 */
    private static final String OFFICE_FILTER_BASE = "cn=云移科技";
    /** 组织类型 */
    private static final String OFFICE_TYPE = "ou=Group,dc=yunnex,dc=com";
    /** 组织返回值集合 */
    private static final String[] OFFICE_RETURNING_ATTRIBUTES = {"member", "cn"};
    /** 用户返回值集合 */
    private static final String[] USER_RETURNING_ATTRIBUTES = {"cn", "displayName", "mail", "uid", "mobile"};


    
    
    
    /**
     * 模拟查询返回用户信息
     * @param loginName
     * @return
     */
    public BizResult<SysUserDto> findByLoginName(String loginName) {
        SysUser sysUser = super.getOne(new QueryWrapper<SysUser>().lambda().eq(SysUser::getLoginName, loginName));
        if (sysUser != null) {
            SysUserDto user = new SysUserDto();
            BeanUtils.copyProperties(sysUser, user);
            return BizResult.success(user);
        }
        return BizResult.success();
    }

    /**
     * 查找用户的角色和菜单权限
     *
     * @param loginUser
     * @param menuShowFlag
     * @param isSort
     * @return
     */
    public BizResult<LoginUser> findRoleMenus(LoginUser loginUser, String menuShowFlag, boolean isSort, boolean isTreeData) {
        BizOptional<List<SysRoleDto>> userRoles = sysRoleService.findUserRoles();
        BizOptional<List<SysMenuDto>> roleMenus = BizOptional.empty();
        if (userRoles.isPresent()) {
            loginUser.setRoles(userRoles.get());
            roleMenus = sysMenuService.findRoleMenus(userRoles.get(), menuShowFlag, isSort, isTreeData);
        }
        if (roleMenus.isPresent()) {
            loginUser.setMenus(roleMenus.get());
        }
        return BizResult.success(loginUser);
    }

    /**
     * 根据登录名修改
     * @param userDto
     * @return
     */
    @Transactional
    public BizResult<Boolean> updateByLoginName(SysUserDto userDto) {
        if (userDto == null || StringUtils.isBlank(userDto.getLoginName())) {
            return BizResult.illegalArg();
        }

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userDto, sysUser);
        sysUserMapper.update(sysUser, new UpdateWrapper<SysUser>().lambda().eq(SysUser::getLoginName, sysUser.getLoginName()));
        return BizResult.success(true);
    }
    
    
    

    //同步用户组织机构
    @Override
    @Transactional(readOnly = false)
    public BizResult<Object> syncUserData() {
        logger.info("同步组织与人员。。。start");
        long start=System.currentTimeMillis();
        // 获取ldap
        LdapContext ldapContext = ldapServiceImpl.getLdapContext();
       // 获取Ldap所有的组织结构 和 用户与组织的关系 用户集合
        LdapSyncResult ldapSyncResult = getLdapSyncResult(ldapContext);
        // 整合所有数据，进行处理
        managerData(ldapSyncResult);
        //同步部门人数
        sysOfficeUserMapper.updateOfficeUserCount();
        long diffTime = (System.currentTimeMillis() - start)/1000;
        logger.info("同步组织与人员。。。。end |耗时 ：{} 秒", diffTime);
        return BizResult.builder(CodeMsg.SUCCESS).build();     
    }

    
    
    
    
    
    /**
     * 根据ladp 和 数据库数据 进行处理
     * 
     *
     * @param ldapSyncResult
     * @param ldapUserMap
     * @date 2018年10月19日
     * @author linqunzhi
     */
    private void managerData(LdapSyncResult ldapSyncResult) {
        // 用户数据处理
        Map<String, SysUser> finallyUserMap = managerUserData(ldapSyncResult.getUserResult());
        // 组织数据处理
        Map<String, SysOffice> finallyOfficeMap = mangerOfficeData(ldapSyncResult.getOfficeResult());
        // 用户与组织关系处理
         managerOfficeUserData(finallyOfficeMap, finallyUserMap, ldapSyncResult.getOfficeUserResult());
    }


    private void managerOfficeUserData(Map<String, SysOffice> finallyOfficeMap, Map<String, SysUser> finallyUserMap,
                                       Map<String, SysOfficeUser> officeUserResult) {
           // 数据库所有 用户与组织的关系数据
           Map<String, SysOfficeUser> officeUserDbMap = getAllDbOfficeUserMap();
           // ldap无数据，进行删除db数据
           for (Entry<String, SysOfficeUser> entry : officeUserDbMap.entrySet()) {
               String key = entry.getKey();
               if (officeUserResult.get(key) == null) {
                   sysOfficeUserMapper.deleteById(entry.getValue().getId());
               }
           }
           // ldap有数据，db无，进行新增数据
           for (Entry<String, SysOfficeUser> entry : officeUserResult.entrySet()) {
               String key = entry.getKey();
               if (officeUserDbMap.get(key) == null) {
                   addOfficeUser(entry.getValue(), finallyOfficeMap, finallyUserMap);
               }
           }

        }

    private void addOfficeUser(SysOfficeUser ldapOfficeUser, Map<String, SysOffice> finallyOfficeMap, Map<String, SysUser> finallyUserMap) {
        String userCn = ldapOfficeUser.getUserCn();
        String officeDn = ldapOfficeUser.getOfficeDn();
        String userId = finallyUserMap.get(userCn).getId();
        String officeId = finallyOfficeMap.get(officeDn).getId();
        SysOfficeUser obj = new SysOfficeUser();
        obj.setOfficeId(officeId);
        obj.setUserId(userId);
        obj.setUserCn(userCn);
        obj.setOfficeDn(officeDn);
        sysOfficeUserMapper.insert(obj);
    }
    
    
    
    private Map<String, SysOfficeUser> getAllDbOfficeUserMap() {
        Map<String, SysOfficeUser> result = new HashMap<>();
        List<SysOfficeUser> list = sysOfficeUserMapper.selectList(new QueryWrapper<SysOfficeUser>().lambda());
        if (CollectionUtils.isNotEmpty(list)) {
            String key = null;
            for (SysOfficeUser obj : list) {
                // admin 数据不处理
                if ("1".equals(obj.getId())) {
                    continue;
                }
                key = obj.getUserCn() + Constant.Symbol.DUN_HAO + obj.getOfficeDn();
                result.put(key, obj);
            }
        }
        return result;
    }
    
    
    
    
    private Map<String, SysOffice> mangerOfficeData(Map<String, SysOfficeDto> ldapOfficeResult) {
        Map<String, SysOffice> result = new HashMap<>();
        Map<String, SysOffice> dbMap = getAllDbOfficeMap();
        SysOffice dbOffice = null;
        for (Entry<String, SysOffice> entry : dbMap.entrySet()) {
            dbOffice = entry.getValue();
            String dn = dbOffice.getLdapDn();
            // 如果ldap无数据，进行逻辑删除
            if (ldapOfficeResult.get(dn) == null) {
                sysOfficeMapper.deleteById(dbOffice.getId());
            }
        }
        SysOfficeDto ldapOffice = null;
        for (Entry<String, SysOfficeDto> entry : ldapOfficeResult.entrySet()) {
            ldapOffice = entry.getValue();
            dbOffice = dbMap.get(ldapOffice.getLdapDn());
            if (dbOffice == null) {
                // 新增部门信息
                dbOffice = addOffice(ldapOffice);
            }
        }
        // 再次查询数据库修改
        dbMap = getAllDbOfficeMap();
        if (dbMap.size() != ldapOfficeResult.size()) {
            logger.error("组织 ：ldap 和 数据库 数量不一致");
        }
        for (Entry<String, SysOfficeDto> entry : ldapOfficeResult.entrySet()) {
            ldapOffice = entry.getValue();
            dbOffice = updateOffice(ldapOffice, dbMap);
            result.put(dbOffice.getLdapDn(), dbOffice);
        }
        return result;
    }

    
    private SysOffice updateOffice(SysOfficeDto ldapOffice, Map<String, SysOffice> dbMap) {
        SysOffice dbOffice = dbMap.get(ldapOffice.getLdapDn());
        String parentId = getIdByDn(ldapOffice.getParent().getLdapDn(), dbMap);
        String parentDns = ldapOffice.getParentIds();
        StringBuilder parentIdsBuilder = new StringBuilder();
        for (String dn : parentDns.split(Constant.Symbol.DUN_HAO)) {
            String id = getIdByDn(dn, dbMap);
            parentIdsBuilder.append(id).append(Constant.Symbol.COMMA);
        }
        String parentIds = parentIdsBuilder.toString();
        // 如果parentId值不同 或者 name 不同，则进行修改
        if (!parentIds.equals(dbOffice.getParentIds()) || !stringEquals(ldapOffice.getName(), dbOffice.getName())) {
            dbOffice = updateOffice(dbOffice.getId(), parentId, parentIds, ldapOffice);

        }
        return dbOffice;
    }
    
    
    private SysOffice updateOffice(String id, String parentId, String parentIds, SysOfficeDto ldapOffice) {
        SysOffice dbOffice = new SysOffice();
        dbOffice.setId(id);
        dbOffice.setLdapDn(ldapOffice.getLdapDn());
        dbOffice.setParentId(parentId);
        dbOffice.setParentIds(parentIds);
        dbOffice.setName(ldapOffice.getName());
        dbOffice.setUseAble(Constant.Flag.Y);
        String grade = "0";
        if (StringUtils.isNotBlank(parentIds)) {
            grade = String.valueOf(parentIds.split(Constant.Symbol.COMMA).length - 1);
        }
        dbOffice.setGrade(grade);
        String type = "0".equals(grade) ? "1" : "2";
        dbOffice.setType(type);
        sysOfficeMapper.updateById(dbOffice);
        return dbOffice;
    }

    
    
    
    private String getIdByDn(String dn, Map<String, SysOffice> dbMap) {
        String result = ROOT_PARENT_ID;
        SysOffice obj = dbMap.get(dn);
        if (obj != null) {
            result = obj.getId();
        }
        return result;
    }
    
    
    private SysOffice addOffice(SysOfficeDto ldapOffice) {
        String dn = ldapOffice.getLdapDn();
        String parentIds = ldapOffice.getParentIds();
        String grade = "0";
        if (StringUtils.isNotBlank(parentIds)) {
            grade = String.valueOf(parentIds.split(Constant.Symbol.DUN_HAO).length - 1);
        }
        SysOffice office = new SysOffice();
        office.setLdapDn(dn);
        office.setCode("");
        office.setName(ldapOffice.getName());
        office.setGrade(grade);
        office.setUseAble(Constant.Flag.Y);
        office.setParentId(ldapOffice.getParent().getId());/////////////////////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\
        office.setParentIds(parentIds);
        String type = "0".equals(grade) ? "1" : "2";
        office.setType(type);
        office.setAreaId("2");
        sysOfficeMapper.insert(office);
        return office;
    }

    
    
    
    
    private Map<String, SysOffice> getAllDbOfficeMap() {
        Map<String, SysOffice> result = new HashMap<>();
        List<SysOffice> newDbList = sysOfficeMapper.selectList(new QueryWrapper<SysOffice>().lambda());
        if (CollectionUtils.isNotEmpty(newDbList)) {
            for (SysOffice office : newDbList) {
                result.put(office.getLdapDn(), office);
            }
        }
        return result;
    }
    
    
    
    
    
    private Map<String, SysUser> managerUserData(Map<String, SysUser> ldapUserResult) {
        Map<String, SysUser> result = new HashMap<>();
        Map<String, SysUser> dbUserMap = getAllDbUserMap();
        SysUser dbUser = null;
        for (Entry<String, SysUser> entry : dbUserMap.entrySet()) {
            dbUser = entry.getValue();
            String loginName = dbUser.getLoginName();
            // 如果ldap无数据，进行逻辑删除
            if (ldapUserResult.get(loginName) == null) {
                sysUserMapper.deleteById(dbUser.getId());
            }
        }

        SysUser ldapUser = null;
        for (Entry<String, SysUser> entry : ldapUserResult.entrySet()) {
            ldapUser = entry.getValue();
            dbUser = dbUserMap.get(ldapUser.getLoginName());
            // ldap 有数据，数据库没数据，进行新增
            if (dbUser == null) {
                dbUser = addUser(ldapUser);
            } else if (!equalsUser(ldapUser, dbUser)) {
                // 如果ladp与数据库中user数据不一致，则进行修改
                dbUser = updateUser(ldapUser, dbUser.getId());
            }
            result.put(dbUser.getLoginName(), dbUser);
        }
        return result;
    }
    
    
    /**
     * 修改用户数据
     *
     * @param ldapUser
     * @param id
     * @return
     * @date 2018年10月19日
     * @author linqunzhi
     */
    private SysUser updateUser(SysUser ldapUser, String id) {
        SysUser result = new SysUser();
        result.setId(id);
        result.setLoginName(ldapUser.getLoginName());
        result.setName(ldapUser.getName());
        result.setEmail(ldapUser.getEmail());
        result.setNo(ldapUser.getNo());
        result.setPhone(ldapUser.getPhone());
        updateById(result);
        return result;
    }

    
    
    
    /**
     * 判断user是否一样
     *
     * @param ldapUser
     * @param dbUser
     * @return
     * @date 2018年10月19日
     * @author linqunzhi
     */
    private boolean equalsUser(SysUser ldapUser, SysUser dbUser) {
        boolean nameEquals = stringEquals(ldapUser.getName(), dbUser.getName());
        boolean emailEquals = stringEquals(ldapUser.getEmail(), dbUser.getEmail());
        boolean noEquals = stringEquals(ldapUser.getNo(), dbUser.getNo());
        boolean phoneEquals = stringEquals(ldapUser.getPhone(), dbUser.getPhone());
        return nameEquals && emailEquals && noEquals && phoneEquals;
    }
   
    /**
     * 字符串比较是否一样
     *
     * @param ldapName
     * @param dbName
     * @return
     * @date 2018年10月19日
     */
    private boolean stringEquals(String ldapName, String dbName) {
        return (StringUtils.isEmpty(ldapName) && StringUtils.isEmpty(dbName)) || (ldapName != null && ldapName.equals(dbName));
    }

    private SysUser addUser(SysUser ldapUser) {
        SysUser user = new SysUser();
        user.setLockFlag("N");
        user.setRemarks("Ldap导入");
        user.setPassword("");
        // 复制 ldapUser数据
        user.setLoginName(ldapUser.getLoginName());
        user.setName(ldapUser.getName());
        user.setEmail(ldapUser.getEmail());
        user.setPhone(ldapUser.getPhone());
        user.setNo(ldapUser.getNo());
        // 新增user
       sysUserMapper.insert(user);
        return user;
    }

    
    
    
    
    
    
    
    
    
    

    private Map<String, SysUser> getAllDbUserMap() {
        Map<String, SysUser> result = new HashMap<>();
        List<SysUser> userDbList = sysUserMapper.selectList(new QueryWrapper<SysUser>().lambda());
        if (CollectionUtils.isNotEmpty(userDbList)) {
            for (SysUser user : userDbList) {
                // 排除系统管理员
                if (!Constant.Security.ADMIN_LOGIN_NAME.equals(user.getLoginName())) {
                    result.put(user.getLoginName(), user);
                }
            }
        }
        return result;
    }
    
    private LdapSyncResult getLdapSyncResult(LdapContext ldapContext) {
        SearchControls searchControls = new SearchControls();
        searchControls.setReturningAttributes(OFFICE_RETURNING_ATTRIBUTES);
        SysOfficeDto parentOffice = new SysOfficeDto();
        parentOffice.setLdapDn(ROOT_OFFICE_SEARCH_BASE);
        parentOffice.setParentIds(Constant.Symbol.EMPTY);
        SysOfficeDto office = new SysOfficeDto();
        office.setLdapDn(OFFICE_FILTER_BASE + "," + ROOT_OFFICE_SEARCH_BASE);
        office.setParent(parentOffice);
        office.setParentIds(ROOT_OFFICE_SEARCH_BASE);
        // 获取ldap 所有用户数据
        Map<String, SysUser> ldapUserMap = getLdapUserMap(ldapContext);
        LdapSyncResult result = new LdapSyncResult();
        try {
            // 获取ldap数据
            ldapDataResult(result, ldapContext, OFFICE_FILTER_BASE, searchControls, office, ldapUserMap);
        } catch (NamingException e) {
            logger.error("同步组织人员异常：{}", e);
           // throw new ServiceException("同步组织人员异常");
        }
        return result;
    }
    
    private void ldapDataResult(LdapSyncResult result, LdapContext ldapContext, String filter, SearchControls searchControls, SysOfficeDto office,
                                Map<String, SysUser> ldapUserMap) throws NamingException {
                    NamingEnumeration<SearchResult> answer = ldapContext.search(ROOT_OFFICE_SEARCH_BASE, filter, searchControls);
                    while (answer.hasMore()) {
                        SearchResult searchResult = answer.next();
                        // 获取组织名称
                        Attribute officeCn = searchResult.getAttributes().get("cn");
                        String name = officeCn.get(0).toString();
                        office.setName(name);
                        // 加入组织集合
                        result.getOfficeResult().put(office.getLdapDn(), office);
                        Attribute officeMember = searchResult.getAttributes().get("member");;
                        NamingEnumeration<?> entryList = officeMember.getAll();
                        while (entryList.hasMore()) {
                            String entryStr = entryList.next().toString();
                            // 如果是 组织就递归查询
                            if (entryStr.contains(OFFICE_TYPE)) {
                                SysOfficeDto lastOffice = new SysOfficeDto();
                                lastOffice.setLdapDn(entryStr);
                                lastOffice.setParent(office);
                                String pids = office.getParentIds() + Constant.Symbol.DUN_HAO + office.getLdapDn();
                                lastOffice.setParentIds(pids);
                                String lastFilter = entryStr.split(Constant.Symbol.COMMA)[0];
                                ldapDataResult(result, ldapContext, lastFilter, searchControls, lastOffice, ldapUserMap);
                            } else {
                                // 用户处理
                                SysUser user = new SysUser();
                                String loginName = entryStr.split(Constant.Symbol.COMMA)[0].split(Constant.Symbol.EQUAL)[1];
                                user = ldapUserMap.get(loginName);
                                result.getUserResult().put(loginName, user);
                                // 用户组织关联数据
                                SysOfficeUser officeUser = new SysOfficeUser();
                                officeUser.setUserCn(loginName);
                                officeUser.setOfficeDn(office.getLdapDn());
                                result.getOfficeUserResult().put(loginName + Constant.Symbol.DUN_HAO + office.getLdapDn(), officeUser);
                            }
                        }
                    }
                }
    
    
    
    
    
    
    
    /**
     * 获取ldap所有用户数据
     *
     * @param ldapContext
     * @return
     * @date 2018年10月18日
     * @author linqunzhi
     */
    private Map<String, SysUser> getLdapUserMap(LdapContext ldapContext) {
        Map<String, SysUser> result = new HashMap<>();
        SysUser user = null;
        SearchControls searchControls = new SearchControls();
        searchControls.setReturningAttributes(USER_RETURNING_ATTRIBUTES);
        try {
            NamingEnumeration<SearchResult> answer = ldapContext.search(ROOT_USER_SEARCH_BASE, "(objectClass=person)", searchControls);
            while (answer.hasMore()) {
                SearchResult searchResult = answer.next();
                NamingEnumeration<? extends Attribute> attrs = searchResult.getAttributes().getAll();
                user = new SysUser();
                // 组装user对象
                while (attrs.hasMore()) {
                    Attribute attr = attrs.next();
                    if ("cn".equals(attr.getID())) {
                        user.setLoginName(attr.get().toString());
                        // 将user放入map中
                        result.put(user.getLoginName(), user);
                    } else if ("displayName".equals(attr.getID())) {
                        user.setName(attr.get().toString());
                    } else if ("mail".equals(attr.getID())) {
                        user.setEmail(attr.get().toString());
                    } else if ("uid".equals(attr.getID())) {
                        user.setNo(attr.get().toString());
                    } else if ("mobile".equals(attr.getID())) {
                        user.setPhone(attr.get().toString());
                    }
                }
            }
        } catch (NamingException e) {
            logger.error("同步组织人员异常：{}", e);
            //throw new ServiceException("同步组织人员异常");
        }
        return result;
    }
    
    
    
    
    
    
    
    
    /**
     * 组织同步返回结果集
     * 
     * @date 2018年10月18日
     */
    class LdapSyncResult {
        private Map<String, SysOfficeDto> officeResult = new HashMap<>();
        private Map<String, SysUser> userResult = new HashMap<>();
        private Map<String, SysOfficeUser> officeUserResult = new HashMap<>();
      
        public Map<String, SysOfficeDto> getOfficeResult() {
            return officeResult;
        }
        public void setOfficeResult(Map<String, SysOfficeDto> officeResult) {
            this.officeResult = officeResult;
        }
        public Map<String, SysUser> getUserResult() {
            return userResult;
        }
        public void setUserResult(Map<String, SysUser> userResult) {
            this.userResult = userResult;
        }
        public Map<String, SysOfficeUser> getOfficeUserResult() {
            return officeUserResult;
        }
        public void setOfficeUserResult(Map<String, SysOfficeUser> officeUserResult) {
            this.officeUserResult = officeUserResult;
        }
       

        
    }






    

}

