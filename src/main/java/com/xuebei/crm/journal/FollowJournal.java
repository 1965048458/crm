package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

public class FollowJournal {
    @Expose
    private String name;

    @Expose
    private int delayCount;

    @Expose
    private int dropCount;

    public FollowJournal(String name,int delayCount,int dropCount,float delayMoney,float dropMoney)
    {
        this.name=name;
        this.delayCount=delayCount;
        this.dropCount=dropCount;
        this.toalMoney = delayCount*delayMoney+dropMoney*dropCount;
    }
    public float getToalMoney() {
        return toalMoney;
    }


    @Expose
    private  float toalMoney;

    public String getName() {
        return name;
    }


    public int getDelayCount() {
        return delayCount;
    }


    public int getDropCount() {
        return dropCount;
    }

}
