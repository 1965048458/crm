package com.xuebei.crm.opportunity;

import com.xuebei.crm.journal.VisitRecord;
import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by Rong Weicheng on 2018/7/10.
 */
@Mapper
public interface OpportunityMapper {

    Integer addSale(Opportunity opportunity);

    void addOpportunityContact(@Param("opportunityId") Integer opportunityId, @Param("contactId") String contactId);

    List<Opportunity> queryOpportunity(OpportunitySearchParam opportunitySearchParam);

    Opportunity opportunityDetail(String opportunityId);

    String queryOpportunityCreator(String opportunityId);

    void modifyOpportunity(Opportunity opportunity);

    void addModificationRecord(@Param("opportunityId") int opportunityId, @Param("userId") String userId);

    List<OpportunityModify> queryModifyRecord(@Param("opportunityId")int opportunityId);

    List<VisitRecord> queryVisitRecord(@Param("opportunityId")int opportunityId);

//    List<ApplySupport> queryApplySupport(@Param("opportunityId")int opportunityId);

    void insertFailReason(@Param("opportunityId")int opportunityId, @Param("failReason")String failReason);


}
