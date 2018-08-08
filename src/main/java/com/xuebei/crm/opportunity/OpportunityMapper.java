package com.xuebei.crm.opportunity;

import com.xuebei.crm.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Rong Weicheng on 2018/7/10.
 */
@Mapper
public interface OpportunityMapper {

    void addSale(Opportunity opportunity);

}
