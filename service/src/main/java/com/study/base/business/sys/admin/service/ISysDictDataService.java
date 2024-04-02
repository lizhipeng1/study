package com.study.base.business.sys.admin.service;

import cn.xluobo.business.sys.admin.domain.req.ReqPageSelect;
import cn.xluobo.business.sys.admin.repo.model.SysDictData;
import cn.xluobo.core.page.RespPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 字典数据表 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-15
 */
public interface ISysDictDataService extends IService<SysDictData> {

    List<SysDictData> dictTypeDataList(String dictType);

    RespPage<SysDictData> dictTypeDataList(String dictType, ReqPageSelect reqPageSelect);

    List<SysDictData> dictTypeDataListByParentValue(String dictType,String parentValue);

    /**
     * 删除缓存
     * @param dictType
     * @return
     */
    boolean removeCache(String dictType);

    /**
     * 获取字典label
     * @param dictType
     * @param dictValue
     * @return
     */
    SysDictData getDictLabel(String dictType, String dictValue);

}
