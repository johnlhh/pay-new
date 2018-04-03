package com.smartzhe.pay.common.config;

import com.github.wxpay.sdk.WXPayConfig;
import com.smartzhe.pay.common.PayConfig;
import com.smartzhe.pay.common.utils.PropertiesUtil;

import java.io.InputStream;
import java.util.Properties;

/**
 * 微信支付的配置类
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 14:15  .
 */
public class MyWXPayConfig implements WXPayConfig, PayConfig {

    private static volatile MyWXPayConfig instance;

    private static String CONFIG_FILE_NAME = "wxpay.properties";

    private static Object object = new Object();

    private MyWXPayConfig() {
        initConfig();
    }

    public static MyWXPayConfig getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new MyWXPayConfig();
                }
            }
        }
        return instance;
    }

    private String appId;

    private String mchId;

    private String key;

    private int httpConnectTimeoutMs;

    private int httpReadTimeoutMs;

    private boolean useSandbox;

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return mchId;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return httpConnectTimeoutMs;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return httpReadTimeoutMs;
    }

    public boolean isUseSandbox() {
        return useSandbox;
    }

    @Override
    public void initConfig() {
        Properties properties = PropertiesUtil.getProperties(CONFIG_FILE_NAME);
        if (properties == null) {
            throw new RuntimeException("找不到wxpay.properties文件");
        }
        this.appId = properties.getProperty("appId");
        this.mchId = properties.getProperty("mchId");
        this.key = properties.getProperty("key");
        this.httpConnectTimeoutMs = Integer.parseInt(properties.getProperty("httpConnectTimeoutMs"));
        this.httpReadTimeoutMs = Integer.parseInt(properties.getProperty("httpReadTimeoutMs"));
        this.useSandbox = Boolean.parseBoolean(properties.getProperty("useSandbox"));
    }
}
