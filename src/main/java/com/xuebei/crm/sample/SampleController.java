package com.xuebei.crm.sample;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    private SampleMapper sampleMapper;

    @RequestMapping("")
    public String toIndex(ModelMap modelMap) {
        modelMap.addAttribute("welcomeMsg", "welcome to crm sample!");
        return "sample";
    }

    @RequestMapping("searchUser")
    public GsonView searchUser (@RequestParam("keyword") String keyword) {
        List<User> userList = sampleMapper.searchUser(keyword);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("userList", userList);
        return gsonView;
    }

    @RequestMapping("insert")
    public String insertUser() {
        return "insert";
    }

}
