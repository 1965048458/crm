package com.xuebei.crm.journal;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
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
    public GsonView testJson(@RequestBody Journal journal) {
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("journal", journal);
        return gsonView;
    }

    @RequestMapping("/edit")
    public String editJournalPage(@RequestParam("type") String type,
                              @RequestParam(value="journalId", required = false) String journalId,
                              ModelMap modelMap) {
        JournalTypeEnum journalType = JournalTypeEnum.valueOf(type);

        modelMap.addAttribute("newJournal", true);
        modelMap.addAttribute("journalType", type);
        modelMap.addAttribute("journalId", 0);
        modelMap.addAttribute("summaryLabel", journalType.getSummaryName());
        modelMap.addAttribute("planLabel", journalType.getPlanName());

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
    public GsonView journalDetail(String journal_id){
        return null;
    }

}
