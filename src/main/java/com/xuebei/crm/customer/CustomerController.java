package com.xuebei.crm.customer;

import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.dto.UUIDGenerator;
import com.xuebei.crm.exception.DepartmentNameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    public static String AUTHENTICATION_ERROR_MSG = "用户没有改操作权限";
    public static String DEPT_NAME_BLANK_ERROR_MSG = "部门名称不能为空";

    // TODO: 最终完善登录功能并去掉
    private String acquireUserId() {
        return "00284bca325c4e77b9f30c5671ec1c44";
    }

    @RequestMapping("")
    public String addCustomer() { return "addCustomer"; }


    @RequestMapping("/addDepartmentPage")
    public String addOrganizationPage(@RequestParam("customerId") String customerId,
                                      ModelMap modelMap) {
        modelMap.addAttribute("customerId", customerId);
        return "addTopDepartment";
    }


    @RequestMapping("/addOrganizationPage")
    public String addOrganizationPage() {
        return "addTopOrg";
    }

    /**
     * 添加 客户-顶级机构(二级学院) 信息
     * 1. 认证出错：提供的 客户ID 不属于 用户的公司，无权限为它添加机构
     * 2. 机构名检查：机构名不能为空，机构名不能与客户的顶级机构(二级学院)重复
     * @param customerId 客户ID
     * @param deptName 新增的顶级机构(二级学院)名称
     * @param website 机构(学院)的网址
     * @param profile 机构(学院)的简介
     * @return 返回操作状态的JSON
     * successFlg(Bool): 操作成功与否
     * errMsg(String): 当successFlg==false时含有此字段，表示错误信息
     */
    @RequestMapping("/action/addTopDepartment")
    public GsonView addTopDepartment(@RequestParam("customerId") String customerId,
                                      @RequestParam("deptName") String deptName,
                                      @RequestParam(value = "website", required = false) String website,
                                      @RequestParam(value = "profile", required = false) String profile) {
        if (!customerService.isUserHasCustomer(acquireUserId(), customerId)) {
            return GsonView.createErrorView(AUTHENTICATION_ERROR_MSG);
        }

        if (StringUtils.isEmptyOrWhitespace(deptName)) {
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }

        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Department dept = new Department();
        dept.setDeptId(UUIDGenerator.genId());
        dept.setDeptName(deptName);
        dept.setWebsite(website);
        dept.setProfile(profile);
        dept.setEnclosureStatus(EnclosureStatusEnum.NORMAL);
        dept.setCustomer(customer);
        dept.setParent(new Department());

        try {
            customerService.addTopDepartment(dept);
        } catch (DepartmentNameDuplicatedException e) {
            return GsonView.createErrorView(e.getMessage());
        }

        return GsonView.createSuccessView();
    }


}
