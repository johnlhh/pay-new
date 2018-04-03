package com.smartzhe.pay.common.enums;

/**
 *
 * 支付类型枚举
 * 列举系统支持的支付方式。
 * 当需要引入新的支付方式时，需要在此处添加新的枚举类型。
 *
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 13:51  .
 */
public enum PayTypeEnum {

    ALIPAY(1, "支付宝"),
    WXPAY(2, "微信"),
    ACP(3, "银联");

    private final String desc;

    private final int code;

    PayTypeEnum(int code, String desc) {
        this.desc = desc;
        this.code = code;
    }


    public static boolean validate(String name) {
        return getByName(name) != null;
    }

    public static PayTypeEnum getByName(String name) {
        for (PayTypeEnum item : PayTypeEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public static PayTypeEnum getByCode(int  code) {
        for (PayTypeEnum item : PayTypeEnum.values()) {
            if (code == item.code) {
                return item;
            }
        }
        return null;
    }
}
