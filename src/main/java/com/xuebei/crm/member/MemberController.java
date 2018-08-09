package com.xuebei.crm.member;

import com.sun.javafx.sg.prism.NGShape;
import com.xuebei.crm.dto.GsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2018/8/7.
 */
@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private MemberMapper memberMapper;

    @RequestMapping()
    public String member(@RequestParam("companyId") String companyId,
                         ModelMap modelMap){
        modelMap.addAttribute("companyId", companyId);
        return "memberSetting";
    }

    @RequestMapping("/relationship")
    public GsonView getRelationship(@RequestParam("companyId") String companyId){
        List<Member> memberList = memberService.searchMemberList(companyId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("memberList",memberList);
        return gsonView;
    }

    @RequestMapping("/addSubMember")
    public GsonView editRelationship(@RequestParam("upperMemberId") String upperMemberId,
                                     @RequestParam("lowerMemberId") String lowerMemberId){
        GsonView gsonView = new GsonView();
        String[] ids = lowerMemberId.split(",");
        for(String subMemberId:ids){
            memberMapper.updateRelationship(upperMemberId,subMemberId);
        }

        gsonView.addStaticAttribute("successFlg",true);
        return gsonView;
    }

    @RequestMapping("/deleteLeader")
    public GsonView deleteLeader(@RequestParam("memberId") String memberId){
        GsonView gsonView = new GsonView();
        memberMapper.deleteLeader(memberId);
        return gsonView;
    }



    @RequestMapping("searchSiblings")
    public GsonView searchSiblings(@RequestParam("memberId") String memberId){
        GsonView gsonView = new GsonView();
        List<Member> siblingsList = memberService.searchSiblingsList(memberId);
        gsonView.addStaticAttribute("successFlg",true);
        gsonView.addStaticAttribute("siblingsList",siblingsList);
        return gsonView;
    }

}
