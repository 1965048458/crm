package com.xuebei.crm.login;

import com.xuebei.crm.company.CompanyMapper;
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
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private CompanyMapper companyMapper;

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

                final String companyId = "eb4fd11d472249359eb6acef2e5bf8e5";
                String userId = companyMapper.queryUserId(user.getCRMUserId(), companyId);
                if (userId == null) {
                    userId = UUIDGenerator.genUUID();
                    companyMapper.joinCompany(user.getCRMUserId(), userId, companyId);
                }
                request.getSession().setAttribute("userId", userId);
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
                            @RequestParam("captcha") String captcha,HttpServletRequest request) {

        GsonView gsonView = new GsonView();
        User user = loginService.searchTel(tel);
        if (user==null) {
            gsonView.addStaticAttribute("exist", false);
        }
        else {
            Date start = (Date) request.getSession().getAttribute("CAPTCHA_CREATE_TS");
            Date now = new Date();
            long c = (now.getTime() - start.getTime()) / 1000;
            if (c > 60) {
                gsonView.addStaticAttribute("time", false);
            }
            String capt = (String) request.getSession().getAttribute("CAPTCHA");
            if (!capt.equals(captcha)) {
                gsonView.addStaticAttribute("captcha", false);
            } else {
                if (pwd.length() < 6) {
                    gsonView.addStaticAttribute(SUCCESS_FLG, false);
                    gsonView.addStaticAttribute("errMsg", "密码至少6位");
                } else {
                    loginService.changePwd(tel, pwd);
                    gsonView.addStaticAttribute(SUCCESS_FLG, true);
                    request.getSession().removeAttribute("CAPTCHA");
                }
            }
        }
        return gsonView;
    }




}
