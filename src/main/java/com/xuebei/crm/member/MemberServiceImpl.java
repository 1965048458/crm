package com.xuebei.crm.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<Member> rltList = new ArrayList<>();
        Map<String, Member> memberMap = new HashMap<>();
        for(Member member:memberList){
            memberMap.put(member.getMemberId(), member);
        }
        for(Member member:memberList){
            if(member.getLeader() == null){
                rltList.add(member);
            }else {
                String leaderId = member.getLeader().getMemberId();
                Member leaderMember = memberMap.get(leaderId);
                leaderMember.addSubMember(member);
            }
        }
        return rltList;
    }
}
