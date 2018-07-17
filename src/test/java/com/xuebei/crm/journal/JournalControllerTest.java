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
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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


    MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(journalController).build();
    }

    @Test
    public void editJournal() throws Exception {

    }

    @Test
    public void deleteJournal() throws Exception {

    }

    @Test
    public void getJournalInfoById() throws Exception {

    }

    @Test
    public void getColleagueList() throws Exception {

    }

    @Test
    public void editJournalPage() throws Exception {
        String type = null;
        String journalId = null;
        ModelMap modelMap;
        mockMvc.perform(get("/journal/edit")
                .param("type", type)
                .param("journalId",journalId))
                .andExpect(view().name("error/500"));

    }

    @Test
    public void toJournalList() throws Exception {
        mockMvc.perform(get("/journal/toList"))
                .andExpect(view().name("journalList"));

    }

    @Test
    public void list() throws Exception {
        String journalType = "DAILY";
        String isRead = "0";
        List<Journal> journalList = new ArrayList<>();
        JournalSearchParam param = null;

        //参数输入正确时应该返回的值
        mockMvc.perform(get("/journal/list")
                .param("journalType", journalType)
                .param("isRead",isRead))
                //验证返回的GsonView中是否存在journalList
                //.andExpect(jsonPath("journalSearchParam").exists())
                .andExpect(jsonPath("journalList").exists())
                .andExpect(jsonPath("successFlg").value(true));

        //验证执行journalService.searchJournal后是否返回一个List<Journal>
        when(journalService.searchJournal(param)).thenReturn(journalList);

        //验证方法执行次数
        verify(journalService,times(1)).searchJournal(any(JournalSearchParam.class));

    }

    @Test
    public void detail() throws Exception {

    }

    @Test
    public void searchJournal() throws Exception {


    }

   @Ignore
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
