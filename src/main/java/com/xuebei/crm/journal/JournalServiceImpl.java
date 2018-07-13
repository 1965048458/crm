package com.xuebei.crm.journal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/7/12.
 */
@Service
public class JournalServiceImpl implements JournalService {
    @Autowired
    private JournalMapper journalMapper;

    @Override
    public List<Journal> searchJournal(String keyword) {
        return journalMapper.searchJournal(keyword);
    }

}
