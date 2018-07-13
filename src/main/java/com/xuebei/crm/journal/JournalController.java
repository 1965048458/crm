package com.xuebei.crm.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12.
 */
@Controller
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @RequestMapping("")
    public String toSelectJournal() {
        return "selectLog";
    }

    @RequestMapping("afterSelected")
    public String toAfterSelected(){return "afterSelected";}

    @RequestMapping("journalDetails")
    public String toJournalDetails(){return "journalDetails";}

    @RequestMapping("/search")
    public String searchUser(@RequestParam("keyword") String keyword,
                             @RequestParam("isRead") String isRead,
                             ModelMap modelMap) {
        List<Journal> journalList = journalService.searchJournal(keyword);
        modelMap.put("journalList", journalList);
        return "searchRlt";
    }




}
