package com.xuebei.crm.customer;

import com.xuebei.crm.dto.GsonView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @RequestMapping("")
    public String addCustomer() { return "addCustomer"; }



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
    @RequestMapping("/action/addTopOrgnization")
    public GsonView addTopDepartment(@RequestParam("customerId") String customerId,
                                      @RequestParam("deptName") String deptName,
                                      @RequestParam("website") String website,
                                      @RequestParam("profile") String profile) {
        Department dept = new Department();
        dept.setDeptName(deptName);
        dept.setWebsite(website);
        dept.setProfile(profile);
        return null;
    }


}
