package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;
import sun.util.calendar.LocalGregorianCalendar;

import java.util.Date;

public class ManageJournal {
    public void setShowDate(int month,int showDate) {
        this.showDate = (month+1)+"月"+showDate+"日";
    }

    public void setShowDate(Date date) {
        this.showDate = (date.getMonth()+1)+"月"+date.getDate()+"日";
    }
    @Expose
    private String showDate;
    @Expose
    private Date tagertDate;
    @Expose
    private Date repairDate;

    public Date getTagertDate() {
        return tagertDate;
    }


    public void setTagertDate(Date tagertDate) {
        this.tagertDate = tagertDate;
    }

    public Date getRepairDate() {
        return repairDate;
    }

    public void setRepairDate(Date repairDate) {
        this.repairDate = repairDate;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate() {
        if (tagertDate!=null) {
            if (tagertDate.getHours() < 8 || (tagertDate.getHours() < 9 && tagertDate.getMinutes() < 30)) {
                Date tmp=new Date(tagertDate.getTime()-86400000);
                showDate=(tmp.getMonth()+1)+"月"+tmp.getDate()+"日";
            } else {
                showDate = (tagertDate.getMonth()+1)+"月"+tagertDate.getDate()+"日";
            }
        }
    }
}
