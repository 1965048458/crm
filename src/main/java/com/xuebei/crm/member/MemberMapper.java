package com.xuebei.crm.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/8/7.
 */
@Mapper
public interface MemberMapper {
    List<Member> searchMemberList(@Param("companyId") String memberId);

    void updateRelationship(@Param("upperMemberId") String upperMemberId,
                            @Param("lowerMemberId") String lowerMemberId);

    List<Member> searchSiblingsList(@Param("memberId") String memberId);

    String getCompanyIdByMemberId(@Param("memberId") String memberId);

    void deleteLeader(@Param("memberId") String memberId);
}
