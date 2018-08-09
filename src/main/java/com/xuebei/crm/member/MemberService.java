package com.xuebei.crm.member;

import java.util.List;

/**
 * Created by Administrator on 2018/8/7.
 */
public interface MemberService {
    List<Member> searchMemberList(String companyId);

    List<Member> searchSiblingsList(String memberId);

    List<Member> searchSubMemberList(String userId);
}
