package com.xuebei.crm.journal;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2018/7/12.
 */
public class Journal {
    @Expose
    private String userId;
    @Expose
    private String realName;
    @Expose
    private String userLog;
    @Expose
    private int numRead;
    @Expose
    private String imgSrc;
    @Expose
    private String dateTime;

    public String getUserId(){return userId;}
    public String getRealName(){return realName;}
    public String getUserLog(){return userLog;}
    public int getNumRead(){return numRead;}
    public String getImgSrc(){return imgSrc;}
    public String getDateTime(){return dateTime;}

    public void setUserId(String userId){this.userId = userId;}
    public void setRealName(String realName){this.realName = realName;}
    public void setUserLog(String userLog){this.userLog = userLog;}
    public void setNumRead(int numRead){this.numRead = numRead;}
    public void setImgSrc(String imgSrc){this.imgSrc = imgSrc;}
    public void setDateTime(String dateTime){this.dateTime = dateTime;}

}
