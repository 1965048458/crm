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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void queryCustomerInfo() throws Exception {
        Date tmpDate=new Date();
        if (tmpDate.getHours()<8||(tmpDate.getHours()<9&&tmpDate.getMinutes()<30)) {
            tmpDate.setDate(tmpDate.getDate()-1);
        }
        tmpDate.setHours(8);
        tmpDate.setMinutes(30);
        tmpDate.setSeconds(0);
        Date tmpDate2=new Date(tmpDate.getTime()-259200000);
        tmpDate2.setHours(0);
        tmpDate2.setMinutes(0);
        Date firstD=new Date(tmpDate2.getTime()+86400000);
        Date secondD=new Date(firstD.getTime()+86400000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String ddd=sdf.format(new Date());
         System.out.println(ddd);
         System.out.println(sdf.parse(ddd));
//        String searchWord = "张三";
//        String userId = "23959345";
//        String url = "/customer/queryCustomer";
//        List<Customer> customerList = new ArrayList<>();
//
//        when(customerService.queryCustomerInfo(searchWord, userId)).thenReturn(customerList);
//
//        mockMvc.perform(get(url)
//                .param("searchWord", searchWord))
//                .andExpect(jsonPath("successFlg").value(true))
//                .andExpect(jsonPath("customerList").exists());
//
//        verify(customerService).queryCustomerInfo(searchWord, userId);

    }

}