package com.smartzhe.pay.common;

import com.smartzhe.pay.common.enums.PayTypeEnum;
import com.smartzhe.pay.common.strategy.AcpStrategy;
import com.smartzhe.pay.common.strategy.AlipayStrategy;
import com.smartzhe.pay.common.strategy.WXPayStrategy;

/**
 * 简单的支付策略工厂类
 * 用于生产支付策略单利
 * <p>
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 14:46  .
 */
public class PayFactory {

    public static PayStrategy getPayStrategy(PayTypeEnum payTypeEnum) {
        switch (payTypeEnum) {
            case WXPAY:
                return WXPayStrategy.getInstance();
            case ALIPAY:
                return AlipayStrategy.getInstance();
            case ACP:
                return AcpStrategy.getInstance();
            default:
                return null;
        }
    }


}
