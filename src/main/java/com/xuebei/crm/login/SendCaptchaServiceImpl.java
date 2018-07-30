package com.xuebei.crm.login;

import com.taobao.api.*;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import java.util.Random;

/**
 * Created by Administrator on 2018/7/26.
 */
public class SendCaptchaServiceImpl  {

    public static String randomNoSeq(int length) {
        String str = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    public static String buildSmsParams(String captcha, String expireTime) {
        StringBuffer paramRlt = new StringBuffer();
        paramRlt.append("{number:'").append(captcha).append("',expire_minutes:'").append(expireTime).append("'}");
        return paramRlt.toString();

    }

    public static AlibabaAliqinFcSmsNumSendResponse sendCaptcha(String phoneNo, String captcha) {
        String signName = "学呗课堂";
        String templateCode = "SMS_18270121";
        AlibabaAliqinFcSmsNumSendResponse rsp = new AlibabaAliqinFcSmsNumSendResponse();
        try {
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setExtend("");
            req.setSmsType("normal");
            req.setSmsFreeSignName(signName);
            Integer expireTime =900/ 60;
            req.setSmsParamString(buildSmsParams(captcha, Integer.toString(expireTime)));
            req.setRecNum(phoneNo);
            req.setSmsTemplateCode(templateCode);
            TaobaoClient client = getClient();
            rsp = client.execute(req);
//            if (rsp.isSuccess()) {
//               logger.debug("短信功能|发送服务返回结果[rsp body={}]", rsp.getBody());
//            }else{
//                logger.error("TaobaoClient返回异常，短信发送失败, errCode:{},errMessage:{}", rsp.getSubCode(), rsp.getSubMsg());
//                throw new WalkclassBizException(rsp.getSubCode(), "短信发送失败");
//            }
        }catch (Exception e){
//            logger.error("api异常，短信发送失败, errCode:{},errMessage:{}",e.getErrCode(), e.getErrMsg());
//            throw new WalkclassBizException(e.getErrCode(), "短信发送失败");
        }

            return rsp;

        }

    public static synchronized TaobaoClient getClient() {
        TaobaoClient taobaoClient = null;
        if(taobaoClient == null){
            String apiUrl = "https://eco.taobao.com/router/rest";
            String appKey = "23482410";
            String appSecret = "a16292d8b6e36503e44d199c550655cd";
            taobaoClient = new DefaultTaobaoClient(apiUrl, appKey, appSecret);
        }
        return taobaoClient;
    }
}
