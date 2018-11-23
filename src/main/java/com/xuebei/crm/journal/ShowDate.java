package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;
import  java.text.SimpleDateFormat;
import java.util.Date;

public class ShowDate {

    @Expose
    private String date;

    public String getDateStr() {
        return dateStr;
    }
    public  ShowDate(Date date)
    {
        date=new Date(date.getTime()+86399000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        this.date=sdf.format(date);
        this.dateStr="补:"+(date.getMonth()+1)+"-"+date.getDate();
    }


    @Expose
    private String dateStr;


    public String getDate() {
        return date;
    }
}
