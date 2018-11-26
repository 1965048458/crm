package com.xuebei.crm.company;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by Rong Weicheng on 2018/8/13.
 */
@RunWith(MockitoJUnitRunner.class)
public class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    private MockMvc mockMvc;

    @Mock
    private CompanyMapper companyMapper;

    @Before
    public void setup() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/template");
        resolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(companyController)
                .setViewResolvers(resolver).build();
    }

    @Test
    public void testToChooseCompanyPage() throws Exception {

        long a=1000;
        long b=600;
        System.out.println(a/b);
//        String url = "/company/chooseCompany";
//        mockMvc.perform(get(url))
//                .andExpect(view().name("chooseCompany"));
    }

    @Test
    public void testGetCompanyList() throws Exception {
        String url = "/company/action/getCompanyList";
        when(companyMapper.queryCompanyListByCrmUserId(anyString())).thenReturn(mockCompanyList());
        mockMvc.perform(get(url).sessionAttr("crmUserId","crmUserId"))
                .andExpect(jsonPath("companys").exists());
    }

    private List<Company> mockCompanyList() {
        List<Company> companies = new ArrayList<>();
        companies.add(new Company());
        return companies;
    }
}