package com.xuebei.crm.sample;

import com.xuebei.crm.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Administrator on 2018/7/13.
 */
@RunWith(MockitoJUnitRunner.class)
public class SampleServiceImplTest {

    @InjectMocks
    private SampleServiceImpl sampleService;

    @Mock
    private SampleMapper sampleMapper;

    @Test
    public void testSearchUser() {
        String keyword = "张三";
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setUserId("ksjdhfs");
        userList.add(user);
        when(sampleMapper.searchUser(keyword)).thenReturn(userList);

        List<User> users = sampleService.searchUser(keyword);

        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());
        verify(sampleMapper).searchUser(keyword);
    }

    @Test
    public void testInseartUser() {
        User user = new User();
        sampleService.editUser(user);

        verify(sampleMapper).editUser(user);
    }
}