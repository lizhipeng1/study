package com.study.core.business.sys.admin.domain.resp;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020-01-13 20:28
 */
@Data
@Builder
public class RespUserInfo implements Serializable {

    private List<String> roles;
    private List<String> permissions;
    private String introduction;
    private String avatar;
    private String name;
    private String username;

}
