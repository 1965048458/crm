package com.xuebei.crm.login;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                                @RequestParam("pwd") String pwd) {
        User user = new User();
        GsonView gsonView = new GsonView();
        if (tel.equals("") || realName.equals("") || pwd.length() < 6) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute("errMsg", "注册手机号、姓名或密码不能为空，且密码至少6位");
        } else {
            User userExist = registerService.searchTel(tel);
            if (userExist!=null) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute("errMsg", "用户已存在");
            }
            else {
                String userId = UUIDGenerator.genUUID();
                user.setUserId(userId);
                user.setTel(tel);
                user.setRealName(realName);
                user.setPwd(pwd);
                registerService.insertUser(user);
                gsonView.addStaticAttribute(SUCCESS_FLG, true);
            }
        }
        return gsonView;
    }
}
