package com.xuebei.crm.customer;

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

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerMapper customerMapper;

    @Test
    public void testAddTopDepartment() {

    }

    @Test
    public void queryCustomerInfo() throws Exception {

        String searchWord = "张三";
        String userId = "23959345";
        List<Customer> customerList = new ArrayList<>();
        when(customerMapper.queryCustomerInfo(searchWord, userId)).thenReturn(customerList);

        customerList = customerService.queryCustomerInfo(searchWord, userId);

        Assert.assertNotNull(customerList);
        Assert.assertEquals(0,customerList.size());

        verify(customerMapper).queryCustomerInfo(searchWord, userId);

    }
}