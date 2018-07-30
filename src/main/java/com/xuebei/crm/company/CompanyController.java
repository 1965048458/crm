package com.xuebei.crm.company;

import com.xuebei.crm.customer.Customer;
import com.xuebei.crm.dto.GsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMapper companyMapper;

    private final String CRMUSER_LOGIN_OUT_OF_DATE_ERROR_MSG = "用户登录已失效，请重新登录";

    @RequestMapping("/chooseCompany")
    public String chooseCompanyPage() {
        return "chooseCompany";
    }

    @RequestMapping("/action/getCompanyList")
    public GsonView getCompanyList(HttpServletRequest request) {
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");
        if (crmUserId == null) {
            return GsonView.createErrorView(CRMUSER_LOGIN_OUT_OF_DATE_ERROR_MSG);
        }

        List<Company> companyList = companyMapper.queryCompanyListByCrmUserId(crmUserId);

        GsonView gsonView = GsonView.createSuccessView();
        gsonView.addStaticAttribute("companys", companyList);

        return gsonView;
    }

    @RequestMapping("/action/confirmCompany")
    public GsonView confirmCompany(@RequestParam("companyId") String companyId,
                                   HttpServletRequest request) {
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");
        if (crmUserId == null) {
            return GsonView.createErrorView(CRMUSER_LOGIN_OUT_OF_DATE_ERROR_MSG);
        }

        String userId = companyMapper.queryUserId(crmUserId, companyId);
        if (userId == null) {
            return GsonView.createErrorView("");
        }

        request.getSession().setAttribute("userId", userId);
        return GsonView.createSuccessView();
    }

    @RequestMapping("/structure")
    public String organizationStructure() {
        return "organizationStructure";
    }

    @RequestMapping("/newCompany")
    public String invite(){
        return "newCompany";
    }

    @RequestMapping("/addCompany")
    public void addCompany(String companyName, String myPos, List<CompanyUser> colList){
        companyService.addCompany(companyName, myPos, colList);
    }

    @RequestMapping("/addMember")
    public void insertMember(String name, String tel){
        companyService.insertMember(name, tel);
    }

}
