package com.study.core.utils.bean;

/**
 * @author ：zhangbaoyu
 * @date ：Created in 2020/7/30 11:26
 */
@FunctionalInterface
public interface ExtendBeanUtilsCallBack<S, T> {
    void callBack(S t, T s);
}
