package com.xuebei.crm.member;

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
        String companyName = memberMapper.getCompanyNameById(companyId);
        modelMap.addAttribute("companyId", companyId);
        modelMap.addAttribute("companyName", companyName);

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

    @RequestMapping("memberInfo")
    public GsonView getMemberInfoList(@RequestParam("companyId") String companyId){
        GsonView gsonView = new GsonView();
        List<Member> memberInfoList = memberMapper.searchMemberList(companyId);
        gsonView.addStaticAttribute("successFlg",true);
        gsonView.addStaticAttribute("memberInfoList",memberInfoList);
        return gsonView;
    }

    @RequestMapping("deleteMember")
    public GsonView deleteMember(@RequestParam("memberId") String ids){
        GsonView gsonView = new GsonView();
        String[] memberId = ids.split(",");
        for(String id:memberId){
            memberMapper.deleteLeaderId(id);
            memberMapper.deleteMember(id);
        }
        gsonView.addStaticAttribute("successFlg",true);
        return gsonView;
    }



}
