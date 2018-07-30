package com.xuebei.crm.login;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.User;
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
public class LoginController {

    @Autowired
    private LoginService loginService;

    public static final String SUCCESS_FLG = "successFlg";
    public static final String ERRMSG = "errMsg";

    @RequestMapping("")
    public String registerAndLogin() {
        return "login";
    }

    @RequestMapping("/login")
    public GsonView login(@RequestParam("tel") String tel,
                          @RequestParam("pwd") String pwd, HttpServletRequest request) {

        GsonView gsonView = new GsonView();
        User user = loginService.searchTel(tel);
        if (user == null) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
        } else {
            if (user.getPwd().equals(pwd)) {
                request.getSession().setAttribute("crmUserId", user.getCRMUserId());
                gsonView.addStaticAttribute(SUCCESS_FLG, true);
            } else {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
            }
        }
        return gsonView;
    }

    @RequestMapping("/forgetPwd")
    public String forgetPwd() {
        return "findPwd";
    }

    @RequestMapping("/findPwd")
    public GsonView findPwd(@RequestParam("tel") String tel,
                            @RequestParam("pwd") String pwd,
                            @RequestParam("captcha") String captcha, HttpServletRequest request) {

        GsonView gsonView = new GsonView();
        User user = loginService.searchTel(tel);
        if (user == null) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "用户不存在");
        } else {
            Date start = (Date) request.getSession().getAttribute("CAPTCHA_CREATE_TS");
            if (start == null) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute(ERRMSG, "请获取验证码");
            } else {
                Date now = new Date();
                long c = (now.getTime() - start.getTime()) / 1000;
                if (c > 900) {
                    gsonView.addStaticAttribute(SUCCESS_FLG, false);
                    gsonView.addStaticAttribute(ERRMSG, "验证码已过期，请重新发送");
                }
                String capt = (String) request.getSession().getAttribute("CAPTCHA");
                if (!capt.equals(captcha)) {
                    gsonView.addStaticAttribute(SUCCESS_FLG, false);
                    gsonView.addStaticAttribute(ERRMSG, "验证码错误");
                } else {
                    if (pwd.length() < 6) {
                        gsonView.addStaticAttribute(SUCCESS_FLG, false);
                        gsonView.addStaticAttribute(ERRMSG, "密码至少6位");
                    } else {
                        loginService.changePwd(tel, pwd);
                        gsonView.addStaticAttribute(SUCCESS_FLG, true);
                        request.getSession().removeAttribute("CAPTCHA");
                        request.getSession().removeAttribute("CAPTCHA_CREATE_TS");
                    }
                }
            }
        }
        return gsonView;
    }

}
