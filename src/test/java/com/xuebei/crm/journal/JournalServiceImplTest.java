package com.xuebei.crm.journal;

import com.xuebei.crm.exception.AuthenticationException;
import com.xuebei.crm.exception.InformationNotCompleteException;
import com.xuebei.crm.user.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Ignore
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

    @Test
    @Ignore
    public void testSearchJournal(){

        JournalSearchParam param = new JournalSearchParam();
        param.setUserId("12345");
        List<Journal> journalList = new ArrayList<>();
        List<Journal> Journals = new ArrayList<>();
        Journal journal = new Journal();
        journal.setJournalId("2");
        journal.setUserId("12345");
        journal.setCreateTs(new Date());
        Journals.add(journal);

        when(journalMapper.searchMyJournal(param)).thenReturn(Journals);
        when(journalMapper.searchReceivedJournal(param)).thenReturn(Journals);

        journalList = journalService.searchJournal(param);

        Assert.assertNotNull(journalList);
        Assert.assertEquals(2, journalList.size());

        verify(journalMapper).searchMyJournal(param);
        verify(journalMapper).searchReceivedJournal(param);

    }

    @Test
    public void testSearchDetail(){

        String jId = "1234";
        Journal journal = new Journal();
        journal.setUserId("757674");
        journal.setJournalId("1234");
        User user = new User();
        user.setUserId("123423");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        List result = new ArrayList();

        when(journalMapper.searchJournal(jId)).thenReturn(journal);
        when(journalMapper.searchRead(jId)).thenReturn(userList);
        when(journalMapper.searchUnread(jId)).thenReturn(userList);

        result = journalService.searchDatail(jId);

        Assert.assertNotNull(result);
        Assert.assertEquals(3,result.size());

        verify(journalMapper).searchJournal(jId);
        verify(journalMapper).searchRead(jId);
        verify(journalMapper).searchUnread(jId);

    }

}