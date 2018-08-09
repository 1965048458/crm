package com.xuebei.crm.member;

import com.google.gson.annotations.Expose;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/7.
 */
public class Member {
    @Expose
    private String memberId;
    @Expose
    private String memberName;
    @Expose
    private String memberGender;
    @Expose
    private Integer subMemberNum = 0;
    @Expose
    private List<Member> subMemberList = new ArrayList<>();
    @Expose
    private String companyId;
    @Expose
    private String leaderId;
    @Expose
    private Member leader;
    @Expose
    private MemberTypeEnum memberType;



    public Member getLeader() {
        return leader;
    }

    public void setLeader(Member leader) {
        this.leader = leader;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public MemberTypeEnum getMemberType() {
        return memberType;
    }

    public void setMemberType(MemberTypeEnum memberType) {
        this.memberType = memberType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberGender() {
        return memberGender;
    }

    public void setMemberGender(String memberGender) {
        this.memberGender = memberGender;
    }

    public Integer getSubMemberNum() {
        return subMemberNum;
    }

    public void setSubMemberNum(Integer subMemberNum) {
        this.subMemberNum = subMemberNum;
    }

    public List<Member> getSubMemberList() {
        return subMemberList;
    }

    public void setSubMemberList(List<Member> subMemberList) {
        this.subMemberList = subMemberList;
    }

    public void addSubMember(Member member){
        this.subMemberList.add(member);
    }

    public void addSubMemberNum(Integer num){this.subMemberNum+=num;}
}
