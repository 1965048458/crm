package com.xuebei.crm.journal;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12.
 */


public interface JournalService {
    List<Journal> searchJournal(String keyword);

    //void insertUser(User user);

}
