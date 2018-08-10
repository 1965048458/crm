package com.xuebei.crm.customer;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


/**
 * Created by Administrator on 2018/7/18.
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }


    @Test
    @Ignore
    public void queryCustomerInfo() throws Exception {

        String searchWord = "张三";
        String url = "/customer/queryCustomer";
        List<Customer> customerList = new ArrayList<>();

        when(customerService.queryCustomerInfo(searchWord)).thenReturn(customerList);

        mockMvc.perform(get(url)
                .param("searchWord", searchWord))
                .andExpect(jsonPath("successFlg").value(true))
                .andExpect(jsonPath("customerList").exists());

        verify(customerService).queryCustomerInfo(searchWord);

    }

}