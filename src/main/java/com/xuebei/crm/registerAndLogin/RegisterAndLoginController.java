package com.xuebei.crm.registerAndLogin;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("/login")
public class RegisterAndLoginController {

    @Autowired
    private RegisterAndLoginService registerAndLoginService;

    public static final String SUCCESS_FLG = "successFlg";

    @RequestMapping("")
    public String registerAndLogin() {
        return "registerAndLogin";
    }

    @RequestMapping("/login")
    public GsonView login(@RequestParam("tel") String tel,
                          @RequestParam("pwd") String pwd, HttpServletRequest request) {

        GsonView gsonView = new GsonView();
        List<User> userList = registerAndLoginService.searchTel(tel);
        if (userList.isEmpty())
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
        else {
            if (userList.get(0).getPwd().equals(pwd)) {
                request.getSession().setAttribute("userId", userList.get(0).getUserId());
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
        List<User> userList = registerAndLoginService.searchTel(tel);

        if (userList.isEmpty())
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
        else {
            if (pwd.length() < 6) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute("errMsg", "密码至少6位");
            } else {
                registerAndLoginService.changePwd(tel, pwd);
                gsonView.addStaticAttribute(SUCCESS_FLG, true);
            }
        }
        return gsonView;
    }

    @RequestMapping("/register")
    public String register() {
        return "telRegister";
    }

    @RequestMapping("/telRegister")
    public GsonView telRegister(@RequestParam("tel") String tel,
                                @RequestParam("realName") String realName,
                                @RequestParam("pwd") String pwd) {
        User user = new User();

        GsonView gsonView = new GsonView();
        if (tel.equals("") || realName.equals("") || pwd.equals("") || pwd.length() < 6) {
            gsonView.addStaticAttribute("success", false);
        } else {
            List<User> userList = registerAndLoginService.searchTel(tel);
            if (userList.size() != 0)
                gsonView.addStaticAttribute("tel", false);
            else {
                String userId = UUIDGenerator.genUUID();
                user.setUserId(userId);
                user.setTel(tel);
                user.setRealName(realName);
                user.setPwd(pwd);
                registerAndLoginService.insertUser(user);
                gsonView.addStaticAttribute("success", true);
            }
        }
        return gsonView;
    }
}
