package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

public class JournalExcel {

    public String getShowJournalMonth() {
        return showJournalMonth;
    }



    public int getMonthIndex() {
        return monthIndex;
    }

    public JournalExcel(int nowMoth,int indexMonth)
    {
        showJournalMonth=""+(nowMoth-indexMonth+1)+"月份日志处罚表下载";
        monthIndex=indexMonth;
    }

    @Expose
    private String showJournalMonth;
    @Expose
    private int monthIndex;
}
