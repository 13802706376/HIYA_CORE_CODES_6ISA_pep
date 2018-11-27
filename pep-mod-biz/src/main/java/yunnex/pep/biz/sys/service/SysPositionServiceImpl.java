package yunnex.pep.biz.sys.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import yunnex.pep.common.base.BaseServiceImpl;
import yunnex.pep.common.constant.CodeMsg;
import yunnex.pep.common.result.BizResult;
import yunnex.pep.biz.sys.dto.SysPositionDto;
import yunnex.pep.biz.sys.dto.SysPositionPageReqDto;
import yunnex.pep.biz.sys.dto.SysPositionPageRespDto;
import yunnex.pep.biz.sys.entity.SysPosition;
import yunnex.pep.biz.sys.entity.SysPositionOffice;
import yunnex.pep.biz.sys.mapper.SysPositionMapper;
import yunnex.pep.biz.sys.mapper.SysPositionOfficeMapper;

/**
 *  服务实现类
 *
 * @author 政经平台
 * @since 2018-10-25
 */
@Service
public class SysPositionServiceImpl extends BaseServiceImpl<SysPositionMapper, SysPosition> implements SysPositionService<SysPosition> {

    @Autowired
    private SysPositionOfficeMapper sysPositionOfficeMapper;
    @Autowired
    private SysPositionMapper sysPositionMapper;
    
    
    @Override
    public BizResult<Object> savePosition(SysPositionDto sysPositionDto) {
        SysPosition sysPosition=new SysPosition();
        BeanUtils.copyProperties(sysPositionDto, sysPosition);
        if (StringUtils.isBlank(sysPositionDto.getId())) {
            save(sysPosition);
        }else{
            updateById(sysPosition);
        }
        //删除之前的部门岗位关系
        sysPositionOfficeMapper.delete(new QueryWrapper<SysPositionOffice>().lambda().eq(SysPositionOffice::getPositionId, sysPosition.getId()));
        if (sysPositionDto.getOfficeIdList().size() > 0) {
            Map<String, Object> map =new HashMap<String, Object>();
            sysPosition.setCreatedBy("1");
            sysPosition.setCreatedDate(LocalDateTime.now());
            sysPosition.setLastModifiedBy("1");
            sysPosition.setLastModifiedDate(LocalDateTime.now());
            map.put("sysPosition", sysPosition);
            map.put("officeIdList", sysPositionDto.getOfficeIdList());
            //批量插入
            sysPositionOfficeMapper.addBatchPositionOfficeInfo(map);
        }
        return BizResult.builder(CodeMsg.SUCCESS).build();
    }

    
    @Override
    public BizResult<IPage<SysPositionPageRespDto>> getPositionPage(SysPositionPageReqDto sysPositionPageReqDto) {
        
        IPage<SysPositionPageRespDto> roleRespPage = sysPositionPageReqDto.setRecords(sysPositionMapper.getPositionPage(sysPositionPageReqDto));
        return BizResult.success(roleRespPage);
    }


    @Override
    public BizResult<SysPositionDto> findpositionInfoById(String id) {
        SysPosition sysPosition= sysPositionMapper.selectById(id);
        SysPositionDto sysPositionDto=new SysPositionDto();
        BeanUtils.copyProperties(sysPosition, sysPositionDto);
        List<SysPositionOffice>list= sysPositionOfficeMapper.selectList(new QueryWrapper<SysPositionOffice>().lambda().eq(SysPositionOffice::getPositionId, id));
        List<String> officeIdList =  new ArrayList<String>();
        for(SysPositionOffice positionOffice:list){
            officeIdList.add(positionOffice.getOfficeId());
        }
        sysPositionDto.setOfficeIdList(officeIdList);
        return BizResult.success(sysPositionDto);
    }
               
                  
     
    @Override
    public BizResult<Object> updateUsrOfficeInfo(SysPositionDto sysPositionDto) {
        if(sysPositionDto==null){
            
        }
        SysPosition position= sysPositionMapper.selectById(sysPositionDto.getId());
        position.setUseOffice(sysPositionDto.getUseOffice());
        updateById(position);
        //删除之前的部门岗位关系
        sysPositionOfficeMapper.delete(new QueryWrapper<SysPositionOffice>().lambda().eq(SysPositionOffice::getPositionId, sysPositionDto.getId()));
        if (sysPositionDto.getOfficeIdList().size() > 0) {
            Map<String, Object> map =new HashMap<String, Object>();
            position.setCreatedDate(LocalDateTime.now());
            position.setLastModifiedBy("1");
            position.setLastModifiedDate(LocalDateTime.now());
            map.put("sysPosition", position);
            map.put("officeIdList", sysPositionDto.getOfficeIdList());
            //批量插入
            sysPositionOfficeMapper.addBatchPositionOfficeInfo(map);
        }
        return BizResult.builder(CodeMsg.SUCCESS).build();
        
    }


    @Override
    public BizResult<Object> deletPositionById(String id) {
        sysPositionMapper.deleteById(id);
        sysPositionOfficeMapper.delete(new QueryWrapper<SysPositionOffice>().lambda().eq(SysPositionOffice::getPositionId, id));
        return BizResult.builder(CodeMsg.SUCCESS).build();
    }


}

