package com.study.base.business.sc.base.service.impl;

import cn.xluobo.business.sc.base.domain.req.ReqSchoolSelect;
import cn.xluobo.business.sc.base.domain.resp.RespSchoolSelect;
import cn.xluobo.business.sc.base.repo.model.ScSchool;
import cn.xluobo.business.sc.base.repo.mapper.ScSchoolMapper;
import cn.xluobo.business.sc.base.service.IScSchoolService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 学校信息 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-04-27 07:13:36
 */
@Service
public class ScSchoolServiceImpl extends ServiceImpl<ScSchoolMapper, ScSchool> implements IScSchoolService {

    @Override
    public List<RespSchoolSelect> selectSchoolSelect(ReqSchoolSelect schoolSelect) {
        return baseMapper.selectForSelect(schoolSelect);
    }

    @Override
    public Long getSchoolId(String schoolName) {
        if(StringUtils.isNotEmpty(schoolName)) {
            QueryWrapper<ScSchool> qw = new QueryWrapper<>();
            qw.eq("school_name", schoolName);
            List<ScSchool> list = this.list(qw);
            if(null != list && list.size() >0 ){
                return list.get(0).getSchoolId();
            } else {
                ScSchool scSchool = new ScSchool();
                scSchool.setSchoolName(schoolName);
                this.save(scSchool);
                return scSchool.getSchoolId();
            }
        }
        return null;
    }
}
