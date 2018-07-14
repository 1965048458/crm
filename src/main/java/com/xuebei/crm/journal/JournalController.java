package com.xuebei.crm.journal;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    // TODO:
    private String acquireUserId() {
        return "00284bca325c4e77b9f30c5671ec1c44";
    }

    @RequestMapping("/action/editSubmit")
    public GsonView editJournal(@RequestBody Journal journal) {
        journal.setUserId(acquireUserId());

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
    public GsonView deleteJournal(@RequestParam("journalId") String journalId) {
        try {
            journalService.deleteJournalById(acquireUserId(), journalId);
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

    @RequestMapping("/testJson")
    public GsonView testJson(@RequestParam("journalId") String journalId) throws AuthenticationException {
        GsonView gsonView = new GsonView();
        Journal journal = journalService.queryJournalById(acquireUserId(), journalId);
        gsonView.addStaticAttribute("journal", journal);
        return gsonView;
    }

    @RequestMapping("/edit")
    public String editJournalPage(@RequestParam("type") String type,
                              @RequestParam(value="journalId", required = false) String journalId,
                              ModelMap modelMap) {

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
                Journal journal = journalService.queryJournalById(acquireUserId(), journalId);
                modelMap.addAttribute("newJournal", false);
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

    @RequestMapping("/list")
    public GsonView list(JournalSearchParam param){
        List<Journal> journals =journalService.searchJournal(param);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("journalList", journals);
        return gsonView;
    }

    @RequestMapping("/detail")
    public GsonView detail(String journalId){
        List journal = journalService.searchDatail(journalId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("journal", journal.get(0));
        gsonView.addStaticAttribute("unread", journal.get(1));
        gsonView.addStaticAttribute("read", journal.get(2));
        return gsonView;
    }

}
