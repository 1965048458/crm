package com.xuebei.crm.login;

import com.taobao.api.ApiException;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * Created by Administrator on 2018/7/30.
 */
public interface SendCaptchaService {
    public String randomNoSeq(int length);
    AlibabaAliqinFcSmsNumSendResponse sendCaptcha(String phoneNo, String captcha) throws ApiException;

    AlibabaAliqinFcSmsNumSendResponse sendAudit(String realName, String tel,String companyName,String result) throws ApiException;
}
