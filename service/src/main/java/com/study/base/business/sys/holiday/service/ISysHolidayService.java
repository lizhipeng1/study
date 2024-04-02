package com.study.base.business.sys.holiday.service;

import cn.xluobo.business.sys.holiday.repo.model.SysHoliday;
import cn.xluobo.core.api.APIBaseResponse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 节假日表 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-09-15
 */
public interface ISysHolidayService extends IService<SysHoliday> {

    /**
     * 获取今年及之后的 节假日
     * @param year
     * @return
     */
    List<SysHoliday> selectYearOrLaterHoliday(String year);

    /**
     * 节假日map
     * @return key：yyyyMMdd 日期  value: 1
     */
    Map<String, String> getHolidayMap();

    /**
     * 调用接口 节假日 自动入库
     * @param year 年
     */
    APIBaseResponse autoInsertHoliday(String year);

}
