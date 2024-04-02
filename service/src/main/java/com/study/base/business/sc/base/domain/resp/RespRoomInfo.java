package com.study.base.business.sc.base.domain.resp;

import cn.xluobo.business.sc.base.repo.model.ScRoom;
import lombok.Data;

/**
 * 教室信息
 * @author ：zhangbaoyu
 * @date ：Created in 2020/9/24 09:27
 */
@Data
public class RespRoomInfo extends ScRoom {

    private String deptName;

}
