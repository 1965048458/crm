package com.xuebei.crm.journal;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import com.xuebei.crm.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @Autowired
    private JournalMapper journalMapper;

    private String acquireUserId(HttpServletRequest request) throws AuthenticationException {
        String userId = (String) request.getSession().getAttribute("userId");
        if (userId == null) {
            throw new AuthenticationException("会话已过期");
        }
        return userId;
    }

    @RequestMapping("/action/editSubmit")
    public GsonView editJournal(@RequestBody Journal journal,
                                HttpServletRequest request) {
        try {
            journal.setUserId(acquireUserId(request));
        } catch (AuthenticationException e) {
            return GsonView.createErrorView(e.getMessage());
        }

        try {
            if (journal.getJournalId() != null) {
                // 更新日志内容
                journalService.modifyJournal(journal);
            } else {
                // 插入新日志
                journalService.createJournal(journal);
            }
        } catch (InformationNotCompleteException | AuthenticationException e) {
            GsonView failedView = new GsonView();
            failedView.addStaticAttribute("successFlg", false);
            failedView.addStaticAttribute("errMsg", e.getMessage());
            return failedView;
        }

        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/delete")
    public GsonView deleteJournal(@RequestParam("journalId") String journalId,
                                  HttpServletRequest request) {
        try {
            journalService.deleteJournalById(acquireUserId(request), journalId);
        } catch (AuthenticationException e) {
            GsonView failedView = new GsonView();
            failedView.addStaticAttribute("successFlg", false);
            failedView.addStaticAttribute("errMsg", e.getMessage());
            return failedView;
        }

        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/query")
    public GsonView getJournalInfoById(@RequestParam("journalId") String journalId,
                                       HttpServletRequest request) throws AuthenticationException {
        GsonView gsonView = new GsonView();
        Journal journal = journalService.queryJournalById(acquireUserId(request), journalId);
        List<User> colleagues = journalMapper.queryColleagues(acquireUserId(request));
        gsonView.addStaticAttribute("journal", journal);
        gsonView.addStaticAttribute("colleagues", colleagues);
        return gsonView;
    }

    @RequestMapping("/action/getColleagueList")
    public GsonView getColleagueList(HttpServletRequest request) {

        List<User> colleagues;
        try {
            colleagues = journalMapper.queryColleagues(acquireUserId(request));
        } catch (AuthenticationException e) {
            return GsonView.createErrorView(e.getMessage());
        }
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("colleagues", colleagues);
        return gsonView;
    }

    @RequestMapping("/edit")
    public String editJournalPage(@RequestParam(value="type", required = false) String type,
                              @RequestParam(value="journalId", required = false) String journalId,
                              ModelMap modelMap, HttpServletRequest request) {

        if (journalId == null && type == null) {
            return "error/500";
        }

        if (journalId == null) {
            JournalTypeEnum journalType = JournalTypeEnum.valueOf(type);
            modelMap.addAttribute("newJournal", true);
            modelMap.addAttribute("journalType", type);
            modelMap.addAttribute("journalId", 0);
            modelMap.addAttribute("summaryLabel", journalType.getSummaryName());
            modelMap.addAttribute("planLabel", journalType.getPlanName());
            modelMap.addAttribute("summary","");
            modelMap.addAttribute("plan", "");
        } else {
            try {
                Journal journal = journalService.queryJournalById(acquireUserId(request), journalId);
                modelMap.addAttribute("newJournal", false);
                modelMap.addAttribute("journal", journal);
                modelMap.addAttribute("journalType", journal.getType());
                modelMap.addAttribute("journalId", journal.getJournalId());
                modelMap.addAttribute("summaryLabel", journal.getType().getSummaryName());
                modelMap.addAttribute("planLabel", journal.getType().getPlanName());
                modelMap.addAttribute("summary", journal.getSummary());
                modelMap.addAttribute("plan", journal.getPlan());
            } catch (AuthenticationException e) {
                return "error/500";
            }
        }

        return "editJournal";
    }

    @RequestMapping("/toList")
    public String toJournalList(HttpServletRequest request) {
        return "journalList";
    }

    @RequestMapping("/list")
    public GsonView list(JournalSearchParam param, HttpServletRequest request){
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        param.setUserId(userId);
        List<Journal> journals =journalService.searchJournal(param);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("journalList", journals);
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/detail")
    public GsonView detail(String journalId){
        List journal = journalService.searchDatail(journalId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("journal", journal.get(0));
        gsonView.addStaticAttribute("unread", journal.get(1));
        gsonView.addStaticAttribute("read", journal.get(2));
        return gsonView;
    }

    @RequestMapping("/searchLog")
    public String searchJournal(){

        return "selectLog";
    }

}
