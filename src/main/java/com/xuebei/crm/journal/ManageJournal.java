package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class ManageJournal {
    public void setShowDate(int showDate) {
        this.showDate = showDate;
    }

    @Expose
    private int showDate;
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

    public int getShowDate() {
        return showDate;
    }

    public void setShowDate() {
        if (tagertDate!=null) {
            if (tagertDate.getHours() < 8 || (tagertDate.getHours() < 9 && tagertDate.getMinutes() < 30)) {
                showDate = tagertDate.getDate() - 1;
            } else {
                showDate = tagertDate.getDate();
            }
        }
    }
}
