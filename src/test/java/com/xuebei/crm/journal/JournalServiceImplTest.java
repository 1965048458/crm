package com.xuebei.crm.journal;

import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Administrator on 2018/7/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class JournalServiceImplTest {

    @InjectMocks
    private JournalServiceImpl journalService;

    @Mock
    private JournalMapper journalMapper;

    @Test
    public void testModifyJournal() throws AuthenticationException, InformationNotCompleteException {
        Journal journal = new Journal();
        String userId = "jhskdf21340987";
        journal.setUserId(userId);
        journal.setJournalId("12345");
        journal.setType(JournalTypeEnum.DAILY);
        journal.setSummary("今日总结");
        journal.setPlan("明日计划");
        journal.setHasSubmitted(false);

        Journal oldJournal = new Journal();
        oldJournal.setUserId(userId);
        oldJournal.setHasSubmitted(false);
        when(journalMapper.queryJournalById(journal.getJournalId())).thenReturn(oldJournal);

        journalService.modifyJournal(journal);

        verify(journalMapper).updateJournal(journal);
        verify(journalMapper).deleteJournalReceiver(journal.getJournalId());
    }
}