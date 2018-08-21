package com.xuebei.crm.message;

import com.google.gson.Gson;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2018/8/21.
 */
@Controller
@RequestMapping("/message")
public class MsgController {
    @Autowired
    private MsgService msgService;

    @Autowired
    private MsgMapper msgMapper;

    @RequestMapping("/exam")
    GsonView queryList(HttpServletRequest request){
        String userId = (String)request.getSession().getAttribute("userId");
        List<Query> queryList = msgService.searchQueryList(userId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg",true);
        gsonView.addStaticAttribute("queryList",queryList);
        return gsonView;
    }
}
