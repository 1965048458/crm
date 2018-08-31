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

    @RequestMapping("applyList")
   public GsonView applyList(HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        List<Apply> applyList = msgService.applyList(userId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg",true);
        gsonView.addStaticAttribute("applyList",applyList);
        return gsonView;
    }

    @RequestMapping("applyCheck")
    public GsonView applyCheck(@RequestParam("applyType") ApplyTypeEnum applyType,
                        @RequestParam("applyId") String applyId,
                        @RequestParam("isApprove") Boolean isApprove,
                        HttpServletRequest request){
        GsonView gsonView = new GsonView();
        String userId = (String) request.getSession().getAttribute("userId");
        msgService.applyCheck(applyType,applyId,isApprove,userId);
        gsonView.addStaticAttribute("successFlg",true);
        return gsonView;
    }

    @RequestMapping("showApplyList")
    public String showApplyList(){
        return "examAndApproval";
    }

    @RequestMapping("/projectApply")
    public GsonView projectApply(HttpServletRequest request){
        GsonView gsonView = new GsonView();
        String userId = (String) request.getSession().getAttribute("userId");
        List<ProjectApply> projectApplyList =  msgService.getProjectApply(userId);
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("projectApplyList", projectApplyList);
        return gsonView;
    }


}
