package com.xuebei.crm.journal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12.
 */

@Mapper
public interface JournalMapper {
    List<Journal> searchJournal(@Param("keyword") String keyword);

    //void insertUser(User user);
}
