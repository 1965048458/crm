package com.xuebei.crm.sample;

import com.xuebei.crm.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by Administrator on 2018/7/10.
 */
@RunWith(MockitoJUnitRunner.class)
public class SampleControllerTest {

    @InjectMocks
    private SampleController sampleController;

    @Mock
    private SampleMapper sampleMapper;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(sampleController).build();
    }

    @Test
    public void testSearchUser() throws Exception {
        String keyword = "keyword";
        String url = "/sample/searchUser";
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUserId("ksjdhfs");
        userList.add(user);
        when(sampleMapper.searchUser(keyword)).thenReturn(userList);

        mockMvc.perform(get(url).param("keyword", keyword))
                .andExpect(jsonPath("successFlg").value(true))
                .andExpect(jsonPath("userList[0].userId").exists());
    }

    @Test
    public void testEditUser() throws Exception {
        String url = "/sample/edit";
        String userId = "123";
        String userName = "张三";

        mockMvc.perform(post(url)
                .param("userId", userId)
                .param("realName", userName))
                .andExpect(jsonPath("success").value(true));

        verify(sampleMapper).editUser(any(User.class));
    }
}