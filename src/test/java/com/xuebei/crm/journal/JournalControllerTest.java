package com.xuebei.crm.journal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Administrator on 2018/7/13.
 */
@RunWith(MockitoJUnitRunner.class)
public class JournalControllerTest {

    @InjectMocks
    //创建一个实例，其它用@Mock注解创建的Mock将会被注入到该实例当中
    private JournalController journalController;

    @Mock
    //创建一个Mock
    private JournalService journalService;

    private MockMvc mockMvc;

    @Before
    public void setup () {
        mockMvc = MockMvcBuilders.standaloneSetup(journalController).build();
    }

    @Test
    public void testSearchJournal() throws Exception {
        String keyword = "day";
        String isRead = "on";
        mockMvc.perform(get("/journal/search")
                .param("keyword", keyword)
                .param("isRead", isRead))
                .andExpect(view().name("searchRlt"))
                .andExpect(model().attributeExists("journalList"));

        //verify(journalService).searchJournal(keyword);
    }

//    @Test
//    public void testList() throws Exception{
//        String journalType = "DAILY";
//        String isRead = "false";
//        mockMvc.perform(get("/journal/list"))
//                .param("journalType", journalType)
//                .param("isRead",isRead)
//                .andExpect(model().attributeExists("journalList"));
//    }

}
