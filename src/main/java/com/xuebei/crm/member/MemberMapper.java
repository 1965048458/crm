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
}
