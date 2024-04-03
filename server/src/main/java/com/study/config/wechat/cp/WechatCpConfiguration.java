package com.study.config.wechat.cp;

import com.study.business.wechat.cp.repo.model.WechatCpAccount;
import com.study.business.wechat.cp.service.IWechatCpAccountService;
import com.study.config.wechat.cp.handler.*;
import com.google.common.collect.Maps;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.impl.WxCpServiceImpl;
import me.chanjar.weixin.cp.config.impl.WxCpDefaultConfigImpl;
import me.chanjar.weixin.cp.constant.WxCpConsts;
import me.chanjar.weixin.cp.message.WxCpMessageRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Configuration
public class WechatCpConfiguration {

    @Autowired
    private LogHandler logHandler;
    @Autowired
    private MsgHandler msgHandler;
    @Autowired
    private ChangeExternalContactHandler changeExternalContactHandler;
    @Autowired
    private IWechatCpAccountService cpAccountService;

    private static Map<String, WxCpMessageRouter> routers = Maps.newHashMap();
    private static Map<String, WxCpService> cpServices = Maps.newHashMap();

    public static Map<String, WxCpMessageRouter> getRouters() {
        return routers;
    }

    public static WxCpService getCpService(String corpId, Integer agentId) {
        WxCpService cpService = cpServices.get(corpId + agentId);
        return Optional.ofNullable(cpService).orElseThrow(() -> new WxRuntimeException("未配置此service"));
    }

    /**
     * 初始化加载所有企业微信应用配置
     */
    @PostConstruct
    public void initServices() {
        List<WechatCpAccount> wechatCpAccountList = cpAccountService.selectAllInUseCpAccount();
        if (null !=wechatCpAccountList && !wechatCpAccountList.isEmpty()) {
            cpServices = wechatCpAccountList.stream().map(item -> {
                WxCpDefaultConfigImpl config = new WxCpDefaultConfigImpl();
                config.setCorpId(item.getCorpId());
                config.setAgentId(item.getAgentId());
                config.setCorpSecret(item.getAgentSecret());
                config.setToken(item.getCallBackToken());
                config.setAesKey(item.getCallBackKey());

                WxCpServiceImpl service = new WxCpServiceImpl();
                service.setWxCpConfigStorage(config);

                routers.put(item.getCorpId() + item.getAgentId(), this.newRouter(service));
                return service;
            }).collect(Collectors.toMap(service -> service.getWxCpConfigStorage().getCorpId() + service.getWxCpConfigStorage().getAgentId(), a -> a));
        }
    }

    private WxCpMessageRouter newRouter(WxCpService wxCpService) {
        final WxCpMessageRouter newRouter = new WxCpMessageRouter(wxCpService);

        // 记录所有事件的日志 （异步执行）
        newRouter.rule().handler(this.logHandler).next();

        // 外部联系人变更事件
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxCpConsts.EventType.CHANGE_EXTERNAL_CONTACT).handler(this.changeExternalContactHandler)
                .end();

        // 默认
        newRouter.rule().async(false).handler(this.msgHandler).end();

        return newRouter;
    }

}
