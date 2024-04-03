package com.study.business.wechat.cp.domain.cp;


import lombok.*;

import java.util.List;

/**
 * @projectName: qyxt
 * @package: com.study.business.wechat.cp.domain.cp
 * @className: ReqCpExternalContact
 * @author: xluobo
 * @description: TODO
 * @date: 2024/1/25 18:50
 */
@Data
public class ReqCpExternalContact extends ReqCpBase {
    private List<String> userIdList;
    private String cursor;
    private Integer limit;
}