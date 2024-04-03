package com.study.business.wechat.cp.repo.mapper;

import com.study.business.wechat.cp.repo.model.WechatCpAccount;
import com.baomidou.mybatisplus.annotation.SqlParser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 企业应用信息 Mapper 接口
 * </p>
 *
 * @author zhangby
 * @since 2024-01-23 06:59:58
 */
public interface WechatCpAccountMapper extends com.baomidou.mybatisplus.core.mapper.BaseMapper<WechatCpAccount> {

    @SqlParser(filter = true)
    List<WechatCpAccount> selectAllInUserCpAccount();

    @SqlParser(filter = true)
    WechatCpAccount selectByCropIdAgentIdIgnoreTenant(@Param("corpId") String corpId, @Param("agentId") String agentId);

}
