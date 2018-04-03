package com.smartzhe.pay.common.strategy;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.smartzhe.pay.common.AbstractPayStrategy;
import com.smartzhe.pay.common.PayConfig;
import com.smartzhe.pay.common.config.MyAlipayConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 支付宝具体支付的实现类
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 14:11  .
 */
public class AlipayStrategy extends AbstractPayStrategy {


    private DefaultAlipayClient defaultAlipayClient;


    private static volatile AlipayStrategy instance;
    private static Object object = new Object();


    private AlipayStrategy(PayConfig payConfig) {
        super(payConfig);
    }

    public static AlipayStrategy getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new AlipayStrategy(MyAlipayConfig.getInstance());
                }
            }
        }
        return instance;
    }


    @Override
    public void init(PayConfig payConfig) {
        if (!(payConfig instanceof MyAlipayConfig)) {
            throw new RuntimeException("不支持的支付配置");
        }
        MyAlipayConfig myAlipayConfig = (MyAlipayConfig) payConfig;
        defaultAlipayClient = new DefaultAlipayClient(
                myAlipayConfig.getOpenApiDomain(),
                myAlipayConfig.getAppId(),
                myAlipayConfig.getPrivateKey(),
                myAlipayConfig.getFormat(),
                myAlipayConfig.getCharset(),
                myAlipayConfig.getAlipayPublicKey(),
                myAlipayConfig.getSignType()
        );
    }

    public Map<String, String> doOrder(Map<String, String> data) {

        if (data != null) {
            Map<String, String> transferData = new HashMap<String, String>();
            transferData.put("subject", "关爱通充值业务");
            transferData.put("body", "充值金额" + data.get("totalAmount"));
            transferData.put("out_trade_no", data.get("tradeNo"));
            transferData.put("device_info", "");
            transferData.put("fee_type", "CNY");
            transferData.put("total_amount", data.get("totalAmount"));
            transferData.put("undiscountable_amount", "0");
            transferData.put("timeout_express", "120m");

            //String bizContent = "{\"out_trade_no\":\"tradeprecreate15221385051976534336\",\"seller_id\":\"\",\"total_amount\":\"0.01\",\"undiscountable_amount\":\"0\",\"subject\":\"xxx品牌xxx门店当面付扫码消费\",\"body\":\"购买商品3件共20.00元\",\"goods_detail\":[{\"goods_id\":\"goods_id001\",\"goods_name\":\"xxx小面包\",\"quantity\":1,\"price\":\"10\"},{\"goods_id\":\"goods_id002\",\"goods_name\":\"xxx牙刷\",\"quantity\":2,\"price\":\"5\"}],\"operator_id\":\"test_operator_id\",\"store_id\":\"test_store_id\",\"extend_params\":{\"sys_service_provider_id\":\"2088100200300400500\"},\"timeout_express\":\"120m\"}";
            String bizContent = JSON.toJSONString(transferData);
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            request.setApiVersion("1.0");
            request.setBizContent(bizContent);
            request.setNotifyUrl("");

            try {
                AlipayTradePrecreateResponse response = defaultAlipayClient.execute(request);
                Map<String, String> result = new HashMap<String, String>();
                result.put("qrcode", response.getQrCode());
                return result;
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }


        return null;
    }

    public void doPayment() {

    }

    public void doRefund(Map<String, String> data) {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setNotifyUrl(data.get("notifyUrl"));
        request.putOtherTextParam("app_auth_token", data.get("authToken"));
        request.setBizContent(JSON.toJSONString(data));
        AlipayTradeRefundResponse response = null;
        try {
            response = defaultAlipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response != null && "10000".equals(response.getCode())) {

        }
    }

    public Map<String, String> getOrder(Map<String, String> data) {

        return null;
    }
}
