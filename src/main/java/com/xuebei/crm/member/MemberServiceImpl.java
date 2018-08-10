package com.xuebei.crm.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/7.
 */
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public List<Member> searchMemberList(String companyId) {
        List<Member> memberList = memberMapper.searchMemberList(companyId);
        List<Member> memberTree = new ArrayList<>();
        Map<String, Member> memberMap = new HashMap<>();
        for (Member member : memberList) {
            memberMap.put(member.getMemberId(), member);
        }
        getMemberTree(memberList, memberTree, memberMap);
        return memberTree;
    }

    private void getMemberTree(List<Member> memberList, List<Member> memberTree, Map<String, Member> memberMap) {
        for (Member member : memberList) {
            if (member.getLeader() == null) {
                memberTree.add(member);
            } else {
                String leaderId = member.getLeader().getMemberId();
                Member leaderMember = memberMap.get(leaderId);
                leaderMember.addSubMember(member);
                leaderMember.addSubMemberNum(1);
                while (leaderMember.getLeader() != null) {
                    leaderMember = memberMap.get(leaderMember.getLeader().getMemberId());
                    leaderMember.addSubMemberNum(1);
                }
            }
        }
    }

    @Override
    public List<Member> searchSiblingsList(String memberId) {
        String companyId = memberMapper.getCompanyIdByMemberId(memberId);
        List<Member> memberList = memberMapper.searchMemberList(companyId);
        List<Member> memberTree = new ArrayList<>();
        Map<String, Member> memberMap = new HashMap<>();
        for (Member member : memberList) {
            memberMap.put(member.getMemberId(), member);
        }
        Member currentMember = memberMap.get(memberId);
        while (currentMember.getLeader() != null) {
            currentMember = memberMap.get(currentMember.getLeader().getMemberId());
        }
        getMemberTree(memberList, memberTree, memberMap);
        memberTree.remove(currentMember);

        return memberTree;
    }

    @Override
    public List<Member> searchSubMemberList(String userId) {
        String companyId = memberMapper.getCompanyIdByMemberId(userId);
        List<Member> memberList = memberMapper.searchMemberList(companyId);
        List<Member> memberTree = new ArrayList<>();
        Map<String, Member> memberMap = new HashMap<>();
        for (Member member : memberList) {
            memberMap.put(member.getMemberId(), member);
        }
        for (Member member : memberList) {

            if (member.getMemberId().equals(userId) || member.getLeader() == null){
                continue;
            }

            Member temp = member;
            while (temp != null &&  !temp.getMemberId().equals(userId) ){
                if (temp.getLeader() != null){
                    String leaderId = temp.getLeader().getMemberId();
                    temp = memberMap.get(leaderId);
                }else{
                    break;
                }
            }
            if (temp != null && temp.getMemberId().equals(userId)){
                memberTree.add(member);
            }
        }
        return memberTree;

    }
}
