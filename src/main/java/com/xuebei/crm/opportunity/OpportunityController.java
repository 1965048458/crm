package com.xuebei.crm.opportunity;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.customer.CustomerServiceImpl;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.journal.VisitRecord;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberServiceImpl;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.xuebei.crm.login.LoginController.SUCCESS_FLG;

/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("/opportunity")
public class OpportunityController {

    @Autowired
    private OpportunityService opportunityService;
    @Autowired
    private MemberServiceImpl memberService;
    @Autowired
    private CustomerServiceImpl customerService;

    @RequestMapping("")
    public String opportunity() {
        return "opportunity";
    }

    @RequestMapping("detail")
    public String opportunityDetail() {
        return "opportunityDetail";
    }

    @RequestMapping("newSale")
    public String newSale() {
        return "newSale";
    }

    @RequestMapping("getCustomers")
    public GsonView getCustomers(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        List<Customer> customerList = opportunityService.getMyCustomers(userId);//test userId "57259d54f9994209a813e8ad2b297b3a"
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("customerList", customerList);
        return gsonView;
    }

    @RequestMapping("addSale")
    public GsonView addSale(@RequestBody Opportunity opportunity,
                            HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        opportunity.setUserId(userId);//"57259d54f9994209a813e8ad2b297b3a"
        opportunityService.addSale(opportunity);
        opportunityService.addOpportunityContact(opportunity.getOpportunityId(), opportunity.getContactId());
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("queryOpportunity")
    public GsonView queryOpportunity(OpportunitySearchParam opportunitySearchParam,
                                     HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        opportunitySearchParam.setScene(userId);
        GsonView gsonView = new GsonView();
        if (opportunitySearchParam.getUserId() != null && !opportunitySearchParam.getUserId().equals("")) {
            if (opportunitySearchParam.getUserId().equals("mine")) {
                opportunitySearchParam.setUserId(userId);
            } else if (opportunitySearchParam.getUserId().equals("sub")) {
                opportunitySearchParam.setUserId("all");
                List<Member> members = memberService.searchSubMemberList(userId);
                List<String> subUserId = new ArrayList<>();
                Iterator<Member> it = members.iterator();
                if (!members.isEmpty()) {
                    while (it.hasNext()) {
                        subUserId.add(it.next().getMemberId());
                    }
                    opportunitySearchParam.setSubUserId(subUserId);
                } else {
                    subUserId.add("0");
                    opportunitySearchParam.setSubUserId(subUserId);
                }
            }
        }

        if (opportunitySearchParam.getSubUser() != null && !opportunitySearchParam.getSubUser().equals("")) {
            opportunitySearchParam.setChooseSubUser((opportunitySearchParam.getSubUser().split(",")));
        }

        List<Opportunity> opportunities = opportunityService.queryOpportunity(opportunitySearchParam);
        gsonView.addStaticAttribute("opportunityList", opportunities);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("subMemberList")
    public GsonView subMemberList(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        List<Member> members = memberService.searchSubMemberList(userId);
        gsonView.addStaticAttribute("subMemberList", members);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("opportunityToDetail")
    public GsonView opportunityToDetail(@RequestParam("opportunityId")String opportunityId, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        GsonView gsonView = new GsonView();
        request.getSession().setAttribute("opportunityId",opportunityId);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("opportunityDetail")
    public GsonView opportunityDetal(@RequestParam("opportunityId")String opportunityId ,
                                     HttpServletRequest request) {
//        String opportunityId = (String) request.getSession().getAttribute("opportunityId");
        GsonView gsonView = new GsonView();
        Opportunity opportunity = opportunityService.opportunityDetail(opportunityId);
        if(opportunity.getCheckDate() != null){
            opportunity.setCheckDateString(opportunity.getCheckDateString());
        }
        if(opportunity.getClinchDate()!=null){
            opportunity.setClinchDateString(opportunity.getClinchDateString());
        }
        Contacts contact =customerService.queryOpportunityDetail(opportunityId);
        gsonView.addStaticAttribute("opportunity", opportunity);
        gsonView.addStaticAttribute("opportunityId", opportunityId);
        String creatorName = opportunityService.queryOpportunityCreator(opportunityId);
        gsonView.addStaticAttribute("creatorName", creatorName);
        if(contact != null){
            gsonView.addStaticAttribute("contact", contact);
        }
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("modification")
    public GsonView modificationOpportunity(@RequestBody Opportunity opportunity,
                            HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        opportunity.setUserId(userId);
        opportunityService.modifyOpportunity(opportunity);
        opportunityService.addModificationRecord(opportunity.getOpportunityId(),userId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("modificationRecord")
    public GsonView modificationRecord(@RequestParam("opportunityId")int opportunityId,
                                            HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<OpportunityModify> record = opportunityService.queryModifyRecord(opportunityId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("modificationRecord",record);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("opportunityRecord")
    public GsonView opportunityVisitRecord(@RequestParam("opportunityId")int opportunityId,
                                       HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<VisitRecord> visitRecords = opportunityService.queryVisitRecord(opportunityId);
//        List<applySupport> applySupports = opportunityService.queryApplySupport(opportunityId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("visitRecords",visitRecords);
//        gsonView.addStaticAttribute("applySupports",applySupports);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }



    @RequestMapping("applySupport")
    public String applySupport(ModelMap modelMap) {
        SupportTypeEnum[] supportTypes = SupportTypeEnum.values();
        modelMap.addAttribute("supportTypes", supportTypes);

        return "applySupport";
    }
}
