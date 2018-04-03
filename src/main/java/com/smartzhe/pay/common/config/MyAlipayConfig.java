package com.smartzhe.pay.common.config;

import com.smartzhe.pay.common.PayConfig;
import com.smartzhe.pay.common.utils.PropertiesUtil;

import java.util.Properties;

/**
 * 支付宝支付的配置类
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 14:15  .
 */
public class MyAlipayConfig implements PayConfig {


    /**
     * 支付网关地址
     */
    private String openApiDomain;
    /**
     * 支付宝商户ID
     */
    private String pId;

    /**
     * 应用ID
     */
    private String appId;
    /**
     * 应用私钥
     */
    private String privateKey;

    /**
     * 应用公钥
     */
    private String publicKey;

    /**
     * 支付宝公钥
     */
    private String alipayPublicKey;

    /**
     * 签名类型
     */
    private String signType;

    /**
     * 请求响应格式
     */
    private String format;
    /**
     * 编码格式
     */
    private String charset;

    private static volatile MyAlipayConfig instance;

    private static String CONFIG_FILE_NAME = "alipay.properties";

    private static Object object = new Object();

    private MyAlipayConfig() {
        initConfig();
    }

    public static MyAlipayConfig getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new MyAlipayConfig();
                }
            }
        }
        return instance;
    }

    @Override
    public void initConfig() {
        Properties properties = PropertiesUtil.getProperties(CONFIG_FILE_NAME);
        if (properties == null) {
            throw new RuntimeException("找不到wxpay.properties文件");
        }
        this.openApiDomain = properties.getProperty("openApiDomain");
        this.appId = properties.getProperty("appId");
        this.privateKey = properties.getProperty("privateKey");
        this.pId = properties.getProperty("pId");
        this.publicKey = properties.getProperty("publicKey");
        this.alipayPublicKey = properties.getProperty("useSandbox");
        this.signType = properties.getProperty("signType");
        this.format = properties.getProperty("format");
        this.charset = properties.getProperty("charset");
    }

    public String getOpenApiDomain() {
        return openApiDomain;
    }

    public String getpId() {
        return pId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getAppId() {
        return appId;
    }

    public String getSignType() {
        return signType;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getFormat() {
        return format;
    }

    public String getCharset() {
        return charset;
    }
}
