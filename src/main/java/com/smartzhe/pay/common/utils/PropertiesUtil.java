package com.smartzhe.pay.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 17:37  .
 */
public class PropertiesUtil {

    public static Properties getProperties(String fileName) {
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getResourceAsStream("/"+fileName);
            Properties properties = new Properties();
            properties.load(is);
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
