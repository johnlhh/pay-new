<#-- 引入布局指令的命名空间 -->
<#import "layout.ftl" as defaultLayout>

<#-- 调用布局指令 -->
<@defaultLayout.bannerLayout>

<div class="main">

    <div class="pull-left">订单提交成功，请尽快支付！订单号：${tradeNo!}</div>

    <div class="pull-right">
        <div>应付金额：<b style="color:red;font-size: 20px">${totalAmount!}</b></div>
        <div style="font-size: 10px">订单详情</div>
    </div>
    <br/>
    <br/>
    <br/>
    <div>请选择如下支付方式</div>
    <br/>
    <ul class="nav nav-tabs">
        <li class="active"><a href="#tab-1" data-toggle="tab" onclick='getQrcode("qrcode","${tradeNo}","${totalAmount}",1)'>支付宝</a></li>
        <li class=""><a href="#tab-1" data-toggle="tab" onclick='getQrcode("qrcode","${tradeNo}","${totalAmount}",2)'>微信</a></li>
        <li class=""><a href="#tab-3" data-toggle="tab" onclick="goACP()">银联</a></li>
        <li class=""><a href="#tab-4" data-toggle="tab" onclick="tabClick()">关爱积分</a></li>
        <li class=""><a href="#tab-5" data-toggle="tab" onclick="tabClick()">午餐额度</a></li>
        <li class=""><a href="#tab-6" data-toggle="tab" onclick="tabClick()">更多支付方式</a></li>
    </ul>
    <div class="tab-content">
        <div id="tab-1" class="tab-pane active">
            <div class="panel-body">
                <div>
                    <div class="pull-left">
                        <div class="timework">距离二维码过期还有<b id="second" style="color: #d43f3a">60</b>秒，过期后页面自动重新获取二维码</div>
                        <div class="qrcodeBorder">
                            <div id="qrcode" class="qrcode"></div>
                        </div>
                        <div id="qrcodeWarnText" class="qrcodeWarnText btn btn-danger"></div>
                    </div>

                    <div class="qrcodeWarnImg pull-right">
                        <img id="qrcodeWarnImg" src="" />
                    </div>
                </div>

            </div>
        </div>
        <div id="tab-3" class="tab-pane">
            <div class="panel-body">
               <div id="acp"></div>
            </div>
        </div>
        <div id="tab-4" class="tab-pane">
            <div class="panel-body">
                关爱积分
            </div>
        </div>
        <div id="tab-5" class="tab-pane">
            <div class="panel-body">
                午餐额度
            </div>
        </div>
        <div id="tab-6" class="tab-pane">
            <div class="panel-body">
                更多支付方式
            </div>
        </div>
    </div>
</div>



</@defaultLayout.bannerLayout>
<style>
    .main {
        clear: both;
        padding-top: 20px;
    }

    .timework{
        height: 30px;
        margin-top:45px;
        margin-left: 200px;
    }

    .qrcodeBorder{
        width: 280px;
        height: 280px;
        padding-top: 20px;
        padding-left: 20px;
        margin-top:15px;
        margin-left: 200px;
        border:1px solid #ccc;
    }

    .qrcodeWarnText{
        width: 280px;
        height: 60px;
        margin-top:30px;
        margin-left: 200px;
        border:1px solid #ccc;
        text-align: center;
        padding-top: 20px;
    }

    .qrcodeWarnImg{
        width: 340px;
        height: 340px;
        margin-top:65px;
        margin-right: 200px;
    }

</style>

<script>

    function makeCode(id, value,type) {
        $("#" + id).text("");
        var qrcode = new QRCode(document.getElementById(id), {
            width: 240,
            height: 240
        });
        qrcode.makeCode(value);
        var warnImg = "";
        var warnText = ""
        switch (type){
            case 1: warnImg = "/img/weixin_pay_warn.png";warnText="请使用支付宝扫一扫";break;
            case 2: warnImg = "/img/weixin_pay_warn.png";warnText="请使用微信扫一扫";break;
        }
        $("#" + id + "WarnImg").attr('src',warnImg);
        //$("#" + id).attr('title',"");
        $("#" + id + "WarnText").text(warnText);
        timeWork();
    }

    function getQrcode(id,tradeNo,totalAmount,type) {
        payType = type;
        expireTime = 60;
        var url = "/pay/qrcode";
        $.ajax({
            type: 'POST',
            url: url,
            data: {
                tradeNo:tradeNo,
                totalAmount:totalAmount,
                payType:type
            },
            success: function (data) {
                makeCode(id,data.qrcode,type)
            }
        });
    }

    var expireTime = 60;

    var timeWorkThread;

    var payType = 1;

    function timeWork(){
        expireTime = 60;
        clearInterval(timeWorkThread);
        timeWorkThread = setInterval(function () {
            if(expireTime > 0){
                $("#second").text(--expireTime);
            }else {
                getQrcode("qrcode","${tradeNo}","${totalAmount}",payType);
                expireTime = 60;
            }
            console.log("haha")
        },1000);
    }


    getQrcode("qrcode","${tradeNo}","${totalAmount}",1);

    function clearWork() {
        clearInterval(timeWorkThread);
    }

    function tabClick() {
        clearWork();
    }

    function goACP(){
        clearWork();
        var url = "/pay/acp";
        $.ajax({
            type: 'POST',
            url: url,
            data: {
                tradeNo:${tradeNo},
                totalAmount:${totalAmount}
            },
            success: function (data) {
                $("#acp").html(data.html);
            }
        });
    }
</script>

