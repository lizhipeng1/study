package com.study.core.business.sys.admin.domain.resp;

import cn.xluobo.business.stock.category.repo.model.StockCategory;
import cn.xluobo.business.sys.admin.repo.model.SysDept;
import cn.xluobo.business.sys.admin.repo.model.SysRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 前端select
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-14 22:07
 */
@Data
@Builder
public class RespTreeSelect {

    private String id;

    private String label;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RespTreeSelect> children;

    public RespTreeSelect() {
    }

    public RespTreeSelect(String id, String label, List<RespTreeSelect> children) {
        this.id = id;
        this.label = label;
        this.children = children;
    }

    public RespTreeSelect(SysDept dept) {
        this.id = String.valueOf(dept.getDeptId());
        this.label = dept.getDeptName();
        if(null != dept.getChildren()){
            this.children = dept.getChildren().stream().map(RespTreeSelect::new).collect(Collectors.toList());
        }
    }

    public RespTreeSelect(SysRole role) {
        this.id = String.valueOf(role.getRoleId());
        this.label = role.getRoleName();
        if(null != role.getChildren()){
            this.children = role.getChildren().stream().map(RespTreeSelect::new).collect(Collectors.toList());;
        }
    }

    public RespTreeSelect(StockCategory category) {
        this.id = String.valueOf(category.getCategoryId());
        this.label = category.getCategoryName();
        if(null != category.getChildren()){
            this.children = category.getChildren().stream().map(RespTreeSelect::new).collect(Collectors.toList());;
        }
    }
}
