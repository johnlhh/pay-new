package com.smartzhe.pay.common.strategy;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.smartzhe.pay.common.AbstractPayStrategy;
import com.smartzhe.pay.common.PayConfig;
import com.smartzhe.pay.common.config.MyWXPayConfig;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付的具体实现类
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 14:11  .
 */
public class WXPayStrategy extends AbstractPayStrategy {

    private static volatile WXPayStrategy instance;
    private static Object object = new Object();

    private WXPay wxPay;

    private WXPayStrategy(PayConfig payConfig) {
        super(payConfig);
    }

    public static WXPayStrategy getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new WXPayStrategy(MyWXPayConfig.getInstance());
                }
            }
        }
        return instance;
    }

    @Override
    public void init(PayConfig payConfig) {
        if (!(payConfig instanceof MyWXPayConfig)) {
            throw new RuntimeException("不支持的支付配置");
        }
        MyWXPayConfig myAlipayConfig = (MyWXPayConfig) payConfig;
        if (myAlipayConfig.isUseSandbox()) {
            wxPay = new WXPay(myAlipayConfig, WXPayConstants.SignType.MD5, myAlipayConfig.isUseSandbox());
        } else {
            wxPay = new WXPay(myAlipayConfig, WXPayConstants.SignType.HMACSHA256, myAlipayConfig.isUseSandbox());
        }

    }

    public Map<String, String> doOrder(Map<String, String> data) {
        try {
            if (data != null) {

                Map<String, String> intransferData = new HashMap<String, String>();

                BigDecimal totalAmount = new BigDecimal(data.get("totalAmount"));
                String amount = totalAmount.multiply(new BigDecimal(100)).toString();
                amount = amount.substring(0,amount.indexOf("."));

                intransferData.put("body", "关爱通充值业务");
                intransferData.put("out_trade_no", data.get("tradeNo"));
                intransferData.put("device_info", "");
                intransferData.put("fee_type", "CNY");
                intransferData.put("total_fee",amount);
                intransferData.put("spbill_create_ip", "123.12.12.123");
                intransferData.put("notify_url", "http://118.89.229.222:8080/test-obj-war/receive/notify");
                intransferData.put("trade_type", "NATIVE");
                intransferData.put("product_id", "12");
                Map<String, String> result = wxPay.unifiedOrder(intransferData);
                Map<String, String> outtransferData = new HashMap<String, String>();
                outtransferData.put("qrcode",result.get("code_url"));
                return outtransferData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void doPayment() {

    }

    public void doRefund(Map<String, String> data) {
        try {
            wxPay.refund(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getOrder(Map<String, String> data) {
        try {
            return wxPay.orderQuery(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
