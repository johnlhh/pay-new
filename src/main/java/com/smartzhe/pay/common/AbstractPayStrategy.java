package com.smartzhe.pay.common;

/**
 *
 * 支付策略父类
 *
 * 作用让子类初始化后自动装载支付配置
 *
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 14:53  .
 */
public abstract class AbstractPayStrategy implements PayStrategy {


    public AbstractPayStrategy(PayConfig payConfig){
        this.init(payConfig);
    }
}
