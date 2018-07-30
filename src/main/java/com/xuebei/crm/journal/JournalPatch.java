package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by Administrator on 2018/7/27.
 */
public class JournalPatch {

    @Expose
    private String jouPatchId;
    @Expose
    private String journalId;
    @Expose
    private String content;
    @Expose
    private Date creatTm;

    public String getJouPatchId() {
        return jouPatchId;
    }

    public void setJouPatchId(String jouPatchId) {
        this.jouPatchId = jouPatchId;
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatTm() {
        return creatTm;
    }

    public void setCreatTm(Date creatTm) {
        this.creatTm = creatTm;
    }
}
