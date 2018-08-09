package com.xuebei.crm.login;

import com.taobao.api.*;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Administrator on 2018/7/26.
 */
@Service
public class SendCaptchaServiceImpl implements SendCaptchaService{

    public String randomNoSeq(int length) {
        String str = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    public String buildSmsParams(String captcha, String expireTime) {
        StringBuilder paramRlt = new StringBuilder();
        paramRlt.append("{number:'").append(captcha).append("',expire_minutes:'").append(expireTime).append("'}");
        return paramRlt.toString();

    }

    public AlibabaAliqinFcSmsNumSendResponse sendCaptcha(String phoneNo, String captcha) throws ApiException {
        String signName = "学呗课堂";
        String templateCode = "SMS_18270121";
        AlibabaAliqinFcSmsNumSendResponse rsp ;
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

        return rsp;

        }

    public synchronized TaobaoClient getClient() {
        TaobaoClient taobaoClient = null;
            String apiUrl = "https://eco.taobao.com/router/rest";
            String appKey = "23482410";
            String appSecret = "a16292d8b6e36503e44d199c550655cd";
            taobaoClient = new DefaultTaobaoClient(apiUrl, appKey, appSecret);
        return taobaoClient;
    }

    public AlibabaAliqinFcSmsNumSendResponse sendAudit(String realName, String tel,String companyName,String result) throws ApiException {
        String signName = "学呗课堂";
        String templateCode = "SMS_141616864";
        AlibabaAliqinFcSmsNumSendResponse rsp ;
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("");
        req.setSmsType("normal");
        req.setSmsFreeSignName(signName);
//        Integer expireTime =900/ 60;
        StringBuilder paramRlt = new StringBuilder();
        paramRlt.append("{name:'").append(realName).append("',company:'")
                .append(companyName).append("',result:'").append(result).append("'}");
        req.setSmsParamString(paramRlt.toString());
        req.setRecNum(tel);
        req.setSmsTemplateCode(templateCode);
        TaobaoClient client = getClient();
        rsp = client.execute(req);

        return rsp;

    }
}
