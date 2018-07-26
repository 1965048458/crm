package com.xuebei.crm.login;


import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * Created by Administrator on 2018/7/26.
 */
public interface SendCaptchaService {

    static AlibabaAliqinFcSmsNumSendResponse sendCaptcha(String phoneNo, String captcha){
        return new AlibabaAliqinFcSmsNumSendResponse();
    }

}
