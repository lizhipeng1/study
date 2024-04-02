package com.study.base.business.sys.admin.service.impl;

import cn.xluobo.business.sys.admin.repo.model.SysDept;
import cn.xluobo.business.sys.admin.repo.mapper.SysDeptMapper;
import cn.xluobo.business.sys.admin.service.ISysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-18
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    /**
     * 是否存在子部门
     * @param departId
     * @return
     */
    public boolean hadChild(Long departId){
        QueryWrapper qw = new QueryWrapper();
        qw.eq("parent_id",departId);
        List list = list(qw);
        if(list.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean hadUser(Long departId) {
        // TODO: 2020-01-18 是否存在用户
        return false;
    }

    @Override
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("parent_id",deptId);
        List<SysDept> childrenList = list(qw);
        if(!childrenList.isEmpty()){
            for (SysDept children : childrenList) {
                children.setAncestors(children.getAncestors().replace(oldAncestors, newAncestors));
            }
            updateBatchById(childrenList);
        }

    }

    @Override
    public List<SysDept> selectUserCampusList(String userId) {
        return baseMapper.selectUserCampusList(userId, "2");
    }

    @Override
    public boolean checkUserDeptInUse(String userId, Long deptId) {
        SysDept sysDept = baseMapper.selectUserDeptInUse(userId, deptId);
        return null != sysDept;
    }
}
