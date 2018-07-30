package com.xuebei.crm.login;

import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    public static final String SUCCESS_FLG = "successFlg";


    @RequestMapping("/register")
    public String register() {
        return "telRegister";
    }

    @RequestMapping("/telRegister")
    public GsonView telRegister(@RequestParam("tel") String tel,
                                @RequestParam("realName") String realName,
                                @RequestParam("pwd") String pwd,
                                @RequestParam("captcha") String captcha, HttpServletRequest request) {
        User user = new User();
        GsonView gsonView = new GsonView();
        if (tel.equals("") || tel.length() != 11 || realName.equals("") || pwd.length() < 6 || captcha.length() != 4) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute("errMsg", "注册手机号、姓名或密码不能为空，且密码至少6位");
        } else {
            User userExist = registerService.searchTel(tel);
            if (userExist != null) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute("errMsg", "用户已存在");
            } else {
                Date start = (Date) request.getSession().getAttribute("CAPTCHA_CREATE_TS");
                Date now = new Date();
                long c = (now.getTime() - start.getTime()) / 1000;
                if (c > 60) {
                    gsonView.addStaticAttribute("time", false);
                } else if (!captcha.equals(request.getSession().getAttribute("CAPTCHA"))) {
                    gsonView.addStaticAttribute("captcha", false);
                    } else {
                        String userId = UUIDGenerator.genUUID();
                        user.setUserId(userId);
                        user.setTel(tel);
                        user.setRealName(realName);
                        user.setPwd(pwd);
                        registerService.insertUser(user);
                        gsonView.addStaticAttribute(SUCCESS_FLG, true);
                        request.getSession().removeAttribute("CAPTCHA");
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
            send = false;
            gsonView.addStaticAttribute("exist", true);
        } else {
            Date ts = (Date) request.getSession().getAttribute("CAPTCHA_CREATE_TS");
            if (ts == null) {
                send = true;
            } else {
                Date start = (Date) request.getSession().getAttribute("CAPTCHA_CREATE_TS");
                Date now = new Date();
                long c = (now.getTime() - start.getTime()) / 1000;
                if (c < 60) {
                    send = false;
                    gsonView.addStaticAttribute("notExpire", true);
                } else {
                    send = true;
                }
            }
        }
        if (send) {
            String captcha = SendCaptchaServiceImpl.randomNoSeq(4);
            AlibabaAliqinFcSmsNumSendResponse rsp = SendCaptchaServiceImpl.sendCaptcha(tel, captcha);
            if (rsp == null) {
                gsonView.addStaticAttribute("successFlag", false);
            } else if (!rsp.isSuccess()) {
                gsonView.addStaticAttribute("successFlag", false);
            } else {
                request.getSession().setAttribute("CAPTCHA_CREATE_TS", new Date());
                request.getSession().setAttribute("CAPTCHA", captcha);
                gsonView.addStaticAttribute("successFlag", true);
            }
        }
        return gsonView;
    }

    @RequestMapping("/findPwd/captcha")
    public GsonView findGetCaptcha(@RequestParam("tel") String tel, HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        boolean send = false;
        User userExist = registerService.searchTel(tel);
        if (userExist == null) {
            send = false;
            gsonView.addStaticAttribute("exist", false);
        } else {
            Date ts = (Date) request.getSession().getAttribute("CAPTCHA_CREATE_TS");
            if (ts == null) {
                send = true;
            } else {
                Date start = (Date) request.getSession().getAttribute("CAPTCHA_CREATE_TS");
                Date now = new Date();
                long c = (now.getTime() - start.getTime()) / 1000;
                if (c < 60) {
                    send = false;
                    gsonView.addStaticAttribute("notExpire", true);
                } else {
                    send = true;
                }
            }
        }
        if (send) {
            String captcha = SendCaptchaServiceImpl.randomNoSeq(4);
            AlibabaAliqinFcSmsNumSendResponse rsp = SendCaptchaServiceImpl.sendCaptcha(tel, captcha);
            if (rsp == null) {
                gsonView.addStaticAttribute("successFlag", false);
            } else if (!rsp.isSuccess()) {
                gsonView.addStaticAttribute("successFlag", false);
            } else {
                request.getSession().setAttribute("CAPTCHA_CREATE_TS", new Date());
                request.getSession().setAttribute("CAPTCHA", captcha);
                gsonView.addStaticAttribute("successFlag", true);
            }
        }
        return gsonView;
    }
}
