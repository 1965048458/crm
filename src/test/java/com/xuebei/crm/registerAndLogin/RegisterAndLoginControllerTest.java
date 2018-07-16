package com.xuebei.crm.registerAndLogin;

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

import static com.xuebei.crm.registerAndLogin.RegisterAndLoginController.SUCCESS_FLG;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Administrator on 2018/7/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterAndLoginControllerTest {

    @InjectMocks
    private RegisterAndLoginController registerAndLoginController;

    @Mock
    private RegisterAndLoginService registerAndLoginService;

    private MockMvc mockMvc;

    @Before
    public void setup () {
        mockMvc = MockMvcBuilders.standaloneSetup(registerAndLoginController).build();
    }

    @Test
    public void testLoginIndex() throws Exception {
        String url = "/login";
        mockMvc.perform(get(url))
                .andExpect(view().name("registerAndLogin"));
    }

    @Test
    public void testLogin () throws Exception {
        String url = "/login/login";
        String tel = "12435678910";
        String pwd = "123456";

        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setPwd(pwd);
        userList.add(user);
        when(registerAndLoginService.searchTel(tel)).thenReturn(userList);

        mockMvc.perform(post(url)
                .param("tel", tel)
                .param("pwd", pwd))
                .andExpect(jsonPath(SUCCESS_FLG).value(true));
    }

    @Test
    public void testLoginPwdWrong () throws Exception {
        String url = "/login/login";
        String tel = "12435678910";
        String pwd = "123456";

        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setPwd("654321");
        userList.add(user);
        when(registerAndLoginService.searchTel(tel)).thenReturn(userList);

        mockMvc.perform(post(url)
                .param("tel", tel)
                .param("pwd", pwd))
                .andExpect(jsonPath(SUCCESS_FLG).value(false));
    }


    @Test
    public void testLoginNoTel () throws Exception {
        String url = "/login/login";
        String tel = "12435678910";
        String pwd = "123456";

        mockMvc.perform(post(url)
                .param("tel", tel)
                .param("pwd", pwd))
                .andExpect(jsonPath(SUCCESS_FLG).value(false));
    }

    @Test
    public void testForgetPwd() throws Exception {
        String url ="/login/forgetPwd";

        mockMvc.perform(get(url))
                .andExpect(view().name("findPwd"));
    }

    @Test
    public void testResetPwd() throws Exception {
        String url = "/login/findPwd";
        String tel = "12345678910";
        String newPwd = "123456";
        String captcha = "1234";
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setTel(tel);
        userList.add(user);
        when(registerAndLoginService.searchTel(tel)).thenReturn(userList);

        mockMvc.perform(post(url).param("tel", tel)
                .param("pwd", newPwd)
                .param("captcha", captcha))
                .andExpect(jsonPath(SUCCESS_FLG).value(true));

        verify(registerAndLoginService).changePwd(tel, newPwd);
    }

    @Test
    public void testResetPwdNoSuchTel() throws Exception {
        String url = "/login/findPwd";
        String tel = "12345678910";
        String newPwd = "123456";
        String captcha = "1234";

        mockMvc.perform(post(url)
                .param("tel", tel)
                .param("pwd", newPwd)
                .param("captcha", captcha))
                .andExpect(jsonPath(SUCCESS_FLG).value(false));

        verify(registerAndLoginService, never()).changePwd(tel, newPwd);
    }

    @Test
    public void testResetPwdTooShort() throws Exception {
        String url = "/login/findPwd";
        String tel = "12345678910";
        String newPwd = "12345";
        String captcha = "1234";
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setTel(tel);
        userList.add(user);
        when(registerAndLoginService.searchTel(tel)).thenReturn(userList);

        mockMvc.perform(post(url)
                .param("tel", tel)
                .param("pwd", newPwd)
                .param("captcha", captcha))
                .andExpect(jsonPath(SUCCESS_FLG).value(false))
                .andExpect(jsonPath("errMsg").value("密码至少6位"));

        verify(registerAndLoginService, never()).changePwd(tel, newPwd);
    }
}