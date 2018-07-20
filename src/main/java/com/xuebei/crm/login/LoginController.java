package com.xuebei.crm.login;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    private LoginService loginService;

    public static final String SUCCESS_FLG = "successFlg";

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
        }
        else {
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
                            @RequestParam("pwd") String pwd) {

        GsonView gsonView = new GsonView();
        User user = loginService.searchTel(tel);
        if (user==null) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
        }
        else {
            if (pwd.length() < 6) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute("errMsg", "密码至少6位");
            } else {
                loginService.changePwd(tel, pwd);
                gsonView.addStaticAttribute(SUCCESS_FLG, true);
            }
        }
        return gsonView;
    }



}
