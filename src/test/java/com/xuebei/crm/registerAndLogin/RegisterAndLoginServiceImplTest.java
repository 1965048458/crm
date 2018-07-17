package com.xuebei.crm.registerAndLogin;

import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Administrator on 2018/7/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class RegisterAndLoginServiceImplTest {

    @InjectMocks
    private RegisterAndLoginServiceImpl registerAndLoginService;

    @Mock
    private RegisterAndLoginMapper registerAndLoginMapper;

    @Test
    public void testInsertUser() throws Exception {
        User user = new User();
        String userId = UUIDGenerator.genUUID();
        user.setUserId(userId);
        String tel  ="13264689";
        String realName = "李建国";
        String pwd = "123456";
        user.setTel(tel);
        user.setRealName(realName);
        user.setPwd(pwd);
        registerAndLoginService.insertUser(user);

        verify(registerAndLoginMapper).insertUser(user);
    }

    @Test
    public void testSearchTel() throws Exception {
        String tel = "12345678910";
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setTel(tel);
        userList.add(user);
        when(registerAndLoginMapper.searchTel(tel)).thenReturn(userList);

        List<User> users = registerAndLoginService.searchTel(tel);

        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());

        verify(registerAndLoginMapper).searchTel(tel);
    }

    @Test
    public void testChangPwd() throws Exception {
        String tel = "12345678";
        String pwd = "123456";

        registerAndLoginService.changePwd(tel, pwd);
        verify(registerAndLoginMapper).changePwd(tel, pwd);
    }
}