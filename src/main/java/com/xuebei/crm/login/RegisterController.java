package com.xuebei.crm.login;


import com.taobao.api.ApiException;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.GenderEnum;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static com.xuebei.crm.login.LoginController.ERRMSG;


/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private SendCaptchaService sendCaptchaService;

    public static final String SUCCESS_FLG = "successFlg";
    public static final String CAPTCHA = "CAPTCHA";
    public static final String CAPTCHA_CREATE_TS = "CAPTCHA_CREATE_TS";

    @RequestMapping("/register")
    public String register() {
        return "telRegister";
    }

    @RequestMapping("/supplementaryInformation/add")
    public GsonView supplementaryAdd(@RequestParam("tel") String tel,
                                     @RequestParam("realName") String realName,
                                     @RequestParam("pwd") String pwd,
                                     @RequestParam("gender") String gender,
                                     @DateTimeFormat(pattern = "yyyy-MM-dd") Date birth,
                                     @RequestParam("mail") String mail,
                                     HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        GenderEnum genderEnum = GenderEnum.valueOf(gender);
        User user = new User();
        String userId = UUIDGenerator.genUUID();
        user.setUserId(userId);
        user.setTel(tel);
        user.setRealName(realName);
        user.setPwd(pwd);
        user.setGenderEnum(genderEnum);
        user.setMail(mail);
        user.setBirth(birth);
        registerService.insertUser(user);
        request.getSession().setAttribute("crmUserId", user.getCRMUserId());
        gsonView.addStaticAttribute(SUCCESS_FLG, true);
        request.getSession().removeAttribute(CAPTCHA);
        request.getSession().removeAttribute(CAPTCHA_CREATE_TS);
        return gsonView;
    }

    @RequestMapping("/telRegister")
    public GsonView telRegister(@RequestParam("tel") String tel,
                                @RequestParam("realName") String realName,
                                @RequestParam("pwd") String pwd,
                                @RequestParam("captcha") String captcha, HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        if (tel.equals("") || tel.length() != 11 || realName.equals("") || pwd.length() < 6 || captcha.length() != 4) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "注册手机号、姓名或密码不能为空，且密码至少6位");
        } else {
            User userExist = registerService.searchTel(tel);
            if (userExist != null) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute(ERRMSG, "用户已存在");
            } else {
                Date start = (Date) request.getSession().getAttribute(CAPTCHA_CREATE_TS);
                if (start == null) {
                    gsonView.addStaticAttribute(SUCCESS_FLG, false);
                    gsonView.addStaticAttribute(ERRMSG, "请获取验证码");
                } else {
                    Date now = new Date();
                    long c = (now.getTime() - start.getTime()) / 1000;
                    if (c > 900) {
                        gsonView.addStaticAttribute(SUCCESS_FLG, false);
                        gsonView.addStaticAttribute(ERRMSG, "验证码已过期，请重新发送");

                    } else if (!captcha.equals(request.getSession().getAttribute(CAPTCHA))) {
                        gsonView.addStaticAttribute(SUCCESS_FLG, false);
                        gsonView.addStaticAttribute(ERRMSG, "验证码错误");

                    } else {
                        gsonView.addStaticAttribute(SUCCESS_FLG, true);
                    }
                }
            }
        }
        return gsonView;
    }

    @RequestMapping("/telRegister/captcha")
    public GsonView getCaptcha(@RequestParam("tel") String tel, HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        boolean send = false;
        User userExist = registerService.searchTel(tel);
        if (userExist != null) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "用户已存在");
        } else {
            send = check(gsonView, request);
        }
        if (send) {
            sendCaptcha(gsonView, tel, request);
        }
        return gsonView;
    }

    @RequestMapping("/findPwd/captcha")
    public GsonView findGetCaptcha(@RequestParam("tel") String tel, HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        boolean send = false;
        User userExist = registerService.searchTel(tel);
        if (userExist == null) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "用户不存在");
        } else {
            send = check(gsonView, request);
        }
        if (send) {
            sendCaptcha(gsonView, tel, request);
        }
        return gsonView;
    }

    public boolean check(GsonView gsonView, HttpServletRequest request) {
        Date ts = (Date) request.getSession().getAttribute(CAPTCHA_CREATE_TS);
        boolean s = false;
        if (ts == null) {
            s = true;
        } else {
            Date start = (Date) request.getSession().getAttribute(CAPTCHA_CREATE_TS);
            Date now = new Date();
            long c = (now.getTime() - start.getTime()) / 1000;
            if (c < 60) {
                s = false;
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                String message = "验证码已发送，请" + (60 - c) + "s后再次点击发送";
                gsonView.addStaticAttribute(ERRMSG, message);
            } else {
                s = true;
            }
        }
        return s;
    }

    public void sendCaptcha(GsonView gsonView, String tel, HttpServletRequest request) {
        String captcha = sendCaptchaService.randomNoSeq(4);
        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = sendCaptchaService.sendCaptcha(tel, captcha);
        }
        catch (ApiException e) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "短信发送失败");
            return;
        }

        if (rsp == null || !rsp.isSuccess()) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "发送失败");
        } else {
            request.getSession().setAttribute(CAPTCHA_CREATE_TS, new Date());
            request.getSession().setAttribute(CAPTCHA, captcha);
            gsonView.addStaticAttribute(SUCCESS_FLG, true);
            gsonView.addStaticAttribute(ERRMSG, "发送成功");
        }
    }
}
