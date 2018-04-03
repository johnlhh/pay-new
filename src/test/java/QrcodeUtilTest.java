import com.smartzhe.pay.common.utils.QrcodeUtil;

/**
 * Description:
 * User: luohuahua
 * Date: 2018/3/28
 * Time: 9:46  .
 */
public class QrcodeUtilTest {

    public static void main(String[] args) {

        String url1="weixin://wxpay/bizpayurl?pr=Nvnbuar";
        String url2="https://qr.alipay.com/bax0196544m7p0nf7q930067";
        QrcodeUtil.getQRCodeImge(url1,512,"c://Users/Administrator/Desktop/qr-1.png");
        QrcodeUtil.getQRCodeImge(url2,256,"c://Users/Administrator/Desktop/qr-2.png");
    }
}
