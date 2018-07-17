package com.xuebei.crm.journal;

import com.xuebei.crm.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Administrator on 2018/7/13.
 */
@RunWith(MockitoJUnitRunner.class)
public class JournalControllerTest {

    @InjectMocks
    private JournalController journalController;

    @Mock
    private JournalService journalService;


    MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(journalController).build();
    }

    @Test
    public void testDetail() throws Exception{

        String journalId = "123144";
        String url = "/journal/detail";
        User user = new User();
        user.setUserId("547568");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        User nUser = new User();
        nUser.setUserId("96465");
        List<User> nUserList = new ArrayList<>();
        nUserList.add(nUser);

        Journal journal = new Journal();
        journal.setJournalId("123144");
        journal.setUserId("463465");
        List<Journal> journalList = new ArrayList<>();
        journalList.add(journal);

        List result = new ArrayList();

        result.add(journalList);
        result.add(userList);
        result.add(nUserList);

        when(journalService.searchDatail(journalId)).thenReturn(result);

        mockMvc.perform(get(url).param("journalId", journalId))
                .andExpect(jsonPath("successFlg").value(true))
                .andExpect(jsonPath("journal[0].userId").exists())
                .andExpect(jsonPath("unread[0].userId").exists())
                .andExpect(jsonPath("read[0].userId").exists());

        verify(journalService).searchDatail(journalId);

    }


}
