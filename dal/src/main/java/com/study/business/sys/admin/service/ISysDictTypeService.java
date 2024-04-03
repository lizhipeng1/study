package com.study.business.sys.admin.service;

import com.study.business.sys.admin.repo.model.SysDictType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 字典类型表 服务类
 * </p>
 *
 * @author zhangby
 * @since 2020-01-15
 */
public interface ISysDictTypeService extends IService<SysDictType> {

    SysDictType selectByDictType(String dictType);
}
