import com.smartzhe.pay.common.PayFactory;
import com.smartzhe.pay.common.PayStrategy;
import com.smartzhe.pay.common.enums.PayTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * User: luohuahua
 * Date: 2018/3/26
 * Time: 16:29  .
 */
public class DoOrderTest {

    public static void main(String[] args) {
        Map<String, String> data = new HashMap<String, String>();

        PayStrategy payStrategy = PayFactory.getPayStrategy(PayTypeEnum.WXPAY);
        payStrategy.doOrder(data);

       /* payStrategy = PayFactory.getPayStrategy(PayTypeEnum.ALIPAY);
        payStrategy.doOrder(data);*/
    }
}
