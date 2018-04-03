package com.smartzhe.pay.common;

import java.util.Map;

/**
 * 支付策略接口
 *
 * 定义了支付的行为规范，其中有
 * 初始化配置：
 * 下单：
 * 付款：
 * 退款：
 * 订单查询：
 *
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 13:57  .
 */
public interface PayStrategy {

    void init(PayConfig payConfig);

    //下单
    Map<String,String> doOrder(Map<String,String> data);

    //付款
    void doPayment();

    //退款
    void doRefund(Map<String,String> data);

    //订单查询
    Map<String,String> getOrder(Map<String,String> data);

}
