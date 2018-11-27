package yunnex.pep.biz.sys.service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import yunnex.pep.biz.sys.dto.SysRoleDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import yunnex.pep.biz.sys.dto.SysRolePageReqDto;
import yunnex.pep.biz.sys.dto.SysRolePageRespDto;
import yunnex.pep.biz.sys.entity.SysRole;
import yunnex.pep.biz.sys.entity.SysRoleMenu;
import yunnex.pep.biz.sys.mapper.SysRoleMapper;
import yunnex.pep.biz.sys.mapper.SysRoleMenuMapper;
import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.common.result.BizOptional;
import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.common.util.ShiroUtils;

/**
 * 角色信息 服务实现类
 *
 * @author 政经平台
 * @since 2018-10-19
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService<SysRole> {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public BizResult<Object> saveRole(SysRoleDto sysRoleDto) {
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(sysRoleDto, sysRole);
        if (StringUtils.isBlank(sysRoleDto.getId())) {
            sysRoleMapper.insert(sysRole);
        } else {
            sysRoleMapper.updateById(sysRole);
        }
        // 更新角色与菜单关联
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().lambda().eq(SysRoleMenu::getRoleId, sysRole.getId()));
        if (sysRoleDto.getRoleMenuIdList().size() > 0) {
            Map<String, Object> map =new HashMap<String, Object>();
            sysRole.setCreatedBy("1");
            sysRole.setCreatedDate(LocalDateTime.now());
            sysRole.setLastModifiedBy("1");
            sysRole.setLastModifiedDate(LocalDateTime.now());
            map.put("sysRole", sysRole);
            map.put("menuIdList", sysRoleDto.getRoleMenuIdList());
            sysRoleMenuMapper.addBatchRoleMenuInfo(map);
        }
        return BizResult.builder(CodeMsg.SUCCESS).build();
    }

    //查询角色信息
    @Override
    public BizResult<SysRoleDto> findRoleInfoById(String id) {
        SysRole sysRole=  sysRoleMapper.selectById(id);
        SysRoleDto sysRoleDto = new SysRoleDto();
        BeanUtils.copyProperties(sysRole, sysRoleDto);
        List<SysRoleMenu>list= sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().lambda().eq(SysRoleMenu::getRoleId, id));
        List<String> menuIdList =  new ArrayList<String>();
        for (SysRoleMenu roleMenu : list) {
            menuIdList.add(roleMenu.getMenuId());
        }
        sysRoleDto.setRoleMenuIdList(menuIdList);
        return BizResult.builder(CodeMsg.SUCCESS,sysRoleDto).build();
    }

    @Override
    public BizResult<Object> deletRoleInfoById(String id) {
        sysRoleMapper.deleteById(id);
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().lambda().eq(SysRoleMenu::getRoleId, id));
        return BizResult.builder(CodeMsg.SUCCESS).build();
    }

    @Override
    public BizResult<IPage<SysRolePageRespDto>> getRolePage(SysRolePageReqDto sysRolePageReqDto) {
        IPage<SysRolePageRespDto> roleRespPage = sysRolePageReqDto.setRecords(sysRoleMapper.getRolePage(sysRolePageReqDto));
        return BizResult.success(roleRespPage);
    }
    public BizOptional<List<SysRoleDto>> findUserRoles() {
        List<SysRoleDto> userRoles = sysRoleMapper.findUserRoles(ShiroUtils.getUserId());
        return BizOptional.of(userRoles != null ? userRoles : Collections.EMPTY_LIST);
    }

}

