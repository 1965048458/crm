package com.xuebei.crm.company;

import com.xuebei.crm.customer.Contacts;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.journal.JournalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private JournalMapper journalMapper;

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
    public GsonView addCompany(@RequestBody Company company,
                               HttpServletRequest request){
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");
        String companyName = company.getCompanyName();
        CompanyUser me = companyService.getUserInfo(crmUserId);
        List<CompanyUser> companyUserList = company.getCompanyUserList();
        companyUserList.add(me);
        companyService.addCompany(companyName, companyUserList);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/addMember")
    public void insertMember(CompanyUser companyUser){
        companyService.insertMember(companyUser);
    }

    @RequestMapping("/getCompanyNames")
    public GsonView getCompanyNames(@RequestParam("word")String word){
        GsonView gsonView = new GsonView();
        List<Company> companies = companyService.queryCompany(word);
        gsonView.addStaticAttribute("companyList", companies);
        return gsonView;
    }

    @RequestMapping("/memberDetail")
    public String memberDetail(){
        return "memberDetail";
    }

    @RequestMapping("/oppData")
    public GsonView oppData(@RequestParam("customerId")String customerId,HttpServletRequest request)
    {
        String userId = (String) request.getSession().getAttribute("userId");
        List<String> userIds=getAllSubordinatesUserId(userId);
          GsonView gsonView=new GsonView();
          CompanyData companyData=companyService.queryCompanyData(userIds,customerId);
          int visitCount=companyMapper.searVisitCount(userIds,customerId);
          List<Contacts> contacts=companyMapper.searContactsACount(customerId);
          int a=0;
          for (Contacts contacts1:contacts)
          {
              if (contacts1.getTel()!=null&&!contacts1.getTel().equals(""))
              {
                  if ((contacts1.getQQ()!=null&&!contacts1.getQQ().equals(""))||(contacts1.getWechat()!=null&&!contacts1.getWechat().equals("")))
                  {
                      System.out.println(contacts1.getContactsId());
                      a++;
                  }
              }
          }
          DecimalFormat df=new DecimalFormat("0.00");
          gsonView.addStaticAttribute("companyData",companyData);
        gsonView.addStaticAttribute("visitCount",visitCount);
        gsonView.addStaticAttribute("a",a);
        if (contacts.size()==0)
        {
            gsonView.addStaticAttribute("aRate","0.00");
        }
        else {
            gsonView.addStaticAttribute("aRate", df.format(a * 1.0 / contacts.size()));
        }
          return  gsonView;
    }

    private List<String> getAllSubordinatesUserId(String userId) {
        List<String> userSet = new ArrayList<>();
        List<String> tSet = new ArrayList<>();
        tSet.add(userId);

        while (!tSet.isEmpty()) {
            List<String> childsSet = new ArrayList<>();
            for (String si: tSet) {
                List<String> childs = journalMapper.querySubordinatesByUserId(si);
                for (String child: childs) {
                    if (!userSet.contains(child) && !tSet.contains(child)) {
                        childsSet.add(child);
                    }
                }
            }
            userSet.addAll(tSet);
            tSet = childsSet;
        }

        return userSet;
    }

}
