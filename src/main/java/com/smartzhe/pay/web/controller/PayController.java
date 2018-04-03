package com.smartzhe.pay.web.controller;

import com.smartzhe.pay.common.PayFactory;
import com.smartzhe.pay.common.PayStrategy;
import com.smartzhe.pay.common.enums.PayTypeEnum;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * User: luohuahua
 * Date: 2018/3/29
 * Time: 14:25  .
 */
@Controller
@RequestMapping("/pay")
public class PayController {


    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(String tradeNo,
                        String totalAmount,
                        Model model) {
        model.addAttribute("tradeNo", tradeNo);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("userName", "John");
        return "index";
    }


    @RequestMapping(value = "qrcode", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, String> qrcode(String tradeNo,
                                      String totalAmount,
                                      int payType
    ) {

        PayStrategy payStrategy = PayFactory.getPayStrategy(PayTypeEnum.getByCode(payType));

        //String jsonString = JSON.toJSONString(map);
        Map<String, String> data = new HashMap<String, String>();
        data.put("tradeNo", tradeNo);
        data.put("totalAmount", totalAmount);
        return payStrategy.doOrder(data);
    }


    @RequestMapping(value = "acp",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, String> acp(String tradeNo,
                    String totalAmount,
                    HttpServletResponse resp
    ) throws IOException {
        PayStrategy payStrategy = PayFactory.getPayStrategy(PayTypeEnum.ACP);

        Map<String, String> data = new HashMap<String, String>();
        data.put("orderId", tradeNo);
        data.put("txnAmt", totalAmount+"00"); //
        Map<String,String> result = payStrategy.doOrder(data);
        System.out.println(result.get("html"));
        return result;
    }

}
