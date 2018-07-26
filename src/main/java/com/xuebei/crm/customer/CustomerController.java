package com.xuebei.crm.customer;

import com.xuebei.crm.dto.GsonView;
import org.springframework.beans.factory.annotation.Autowired;
import com.xuebei.crm.utils.UUIDGenerator;
import com.xuebei.crm.exception.DepartmentNameDuplicatedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    @RequestMapping("searchCustInfo")
    public String searchInfo(){
        return "searchCustomerInfo";
    }



    @Autowired
    private CustomerServiceImpl customerService;

    private static String AUTHENTICATION_ERROR_MSG = "用户没有改操作权限";


    // TODO: 最终完善登录功能并去掉
    private String acquireUserId() {
        return "00284bca325c4e77b9f30c5671ec1c44";
    }

    @RequestMapping("")
    public String addCustomer() { return "addCustomer"; }

    @RequestMapping("add")
    public GsonView newCustomer(@RequestParam("schoolType") String schoolType,
                                @RequestParam("name") String name,
                                @RequestParam("profile") String profile,
                                @RequestParam("website") String website,
                                HttpServletRequest request) {
        GsonView gsonView = new GsonView();

        String customer_id = UUIDGenerator.genUUID();
        String creator_id = (String)request.getSession().getAttribute("crmUserId") ;
        String create_ts = "";
        String updater_id = creator_id;
        String update_ts = "";


        if(profile.equals("") && website.equals("")){
            customerService.newSchool(customer_id, name, schoolType, null,
                    null, creator_id, create_ts, updater_id, update_ts);
        }else if(profile.equals("")){
            customerService.newSchool(customer_id, name, schoolType, null, website,
                    creator_id, create_ts, updater_id, update_ts);
        }else if(website.equals("")){
            customerService.newSchool(customer_id, name, schoolType, profile,
                    null, creator_id, create_ts, updater_id, update_ts);
        }else{
            customerService.newSchool(customer_id, name, schoolType, profile, website, creator_id, create_ts, updater_id, update_ts);
        }
        gsonView.addStaticAttribute("successFlg",true);
        return gsonView;
    }

    @RequestMapping("searchSchool")
    public GsonView searchSchool(@RequestParam("keyword") String keyword) {
        GsonView gsonView = new GsonView();
        List<String> schList = customerMapper.searchSchool(keyword);
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("schList", schList);
        return gsonView;
    }



    @RequestMapping("/addDepartmentPage")
    public String addDepartmentPage(@RequestParam("customerId") String customerId,
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
        final String DEPT_NAME_BLANK_ERROR_MSG = "部门名称不能为空";

        if (!customerService.isUserHasCustomer(acquireUserId(), customerId)) {
            return GsonView.createErrorView(AUTHENTICATION_ERROR_MSG);
        }

        if (StringUtils.isEmptyOrWhitespace(deptName)) {
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }

        Customer customer = new Customer();
        customer.setCustomerId(customerId);

        Department dept = new Department();
        dept.setDeptId(UUIDGenerator.genUUID());
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

    @RequestMapping("addContactsPage")
    public String addContactsPage(@RequestParam("deptId") String deptId,
                                  ModelMap modelMap) {
        modelMap.addAttribute("deptId", deptId);

        // 部门ID空 或 用户不能修改该部门（因为客户不属于用户的公司）
        Department dept = customerMapper.queryDepartmentById(deptId);
        if (dept == null || !customerService.isUserHasCustomer(acquireUserId(), dept.getCustomer().getCustomerId())) {
            return "error/404";
        }

        // 判断部门是顶级部门
        if (dept.getParent() == null || dept.getParent().getDeptId() == null) {
            modelMap.addAttribute("isTopDept", true);
        } else {
            modelMap.addAttribute("isTopDept", false);
        }

        return "addContacts";
    }

    /**
     * 添加联系人信息
     * 1. 检查参数中的 deptId 对应的客户(公司学校)是否为用户所有 --权限检查
     * 2. 直属于顶级机构(二级学院)的联系人contactsTypeId应为空。不直属的不能为空，且要检查ID是否属于该客户
     * 3. 联系人姓名不能为空，手机号/座机号 至少填一个
     * 4. 手机号、邮箱号 格式检查
     * @param deptId 添加联系人的部门 ID (把联系人加入到哪个部门中)
     * @param contactsTypeId 添加联系人的类型 (注意：顶级机构必为空，不顶级的必须不空)
     * @param contacts 添加联系人信息
     * @return 返回操作状态的JSON
     */
    @RequestMapping("/action/addContacts")
    public GsonView addContacts(@RequestParam("deptId") String deptId,
                                @RequestParam(value = "contactsTypeId", required = false) String contactsTypeId,
                                Contacts contacts) {
        final String TOP_DEPT_CONTACTS_TYPE_NOT_NULL_ERROR_MSG = "顶级机构中联系人不允许有职位";
        final String SUB_DEPT_CONTACTS_NULL_ERROR_MSG = "子机构中联系人需要有职位信息";
        final String REAL_NAME_BLANK_ERROR_MSG = "联系人姓名不能为空";
        final String TEL_AND_PHONE_BLANK_ERROR_MSG ="联系人手机号和座机号不能同时为空";

        // 权限检查
        Department dept = customerMapper.queryDepartmentById(deptId);
        if (dept == null || !customerService.isUserHasCustomer(acquireUserId(), dept.getCustomer().getCustomerId())) {
            return GsonView.createErrorView(AUTHENTICATION_ERROR_MSG);
        }

        // 联系人类型检查
        if (dept.getParent() == null || dept.getParent().getDeptId() == null) {
            if (contactsTypeId != null) {
                return GsonView.createErrorView(TOP_DEPT_CONTACTS_TYPE_NOT_NULL_ERROR_MSG);
            }
        } else {
            if (contactsTypeId == null) {
                return GsonView.createErrorView(SUB_DEPT_CONTACTS_NULL_ERROR_MSG);
            }
            ContactsType type = customerMapper.queryContactsTypeById(contactsTypeId);
            if (type == null || !type.getCustomerId().equals(dept.getCustomer().getCustomerId())) {
                return GsonView.createErrorView(AUTHENTICATION_ERROR_MSG + ", 机构类型错误");
            }
        }

        // 联系人信息
        if (contacts.getRealName() == null || StringUtils.isEmptyOrWhitespace(contacts.getRealName())) {
            return GsonView.createErrorView(REAL_NAME_BLANK_ERROR_MSG);
        }
        if ((contacts.getTel() == null || StringUtils.isEmptyOrWhitespace(contacts.getTel())) &&
                (contacts.getPhone() == null || StringUtils.isEmptyOrWhitespace(contacts.getPhone()))) {
            return GsonView.createErrorView(TEL_AND_PHONE_BLANK_ERROR_MSG);
        }

        // 添加联系人
        ContactsType contactsType = new ContactsType();
        contactsType.setContactsTypeId(contactsTypeId);

        contacts.setContactsId(UUIDGenerator.genUUID());
        contacts.setDepartment(dept);
        contacts.setContactsType(contactsType);
        customerMapper.insertContacts(contacts);

        return GsonView.createSuccessView();
    }

    @RequestMapping("/action/getContactsTypeList")
    public GsonView getContactsTypeList(@RequestParam("deptId") String deptId) {
        Department dept = customerMapper.queryDepartmentById(deptId);
        if (dept == null || !customerService.isUserHasCustomer(acquireUserId(), dept.getCustomer().getCustomerId())) {
            return GsonView.createErrorView(AUTHENTICATION_ERROR_MSG);
        }
        String customerId = dept.getCustomer().getCustomerId();
        List<ContactsType> contactsTypes = customerMapper.queryContactsTypes(customerId);

        GsonView gson = GsonView.createSuccessView();
        gson.addStaticAttribute("contactsTypes", contactsTypes);
        return gson;
    }

    @RequestMapping("/organization")
    public String organization(){return "customer/organization";}

    @RequestMapping("/organization/show")
    public GsonView queryDepartment(@RequestParam("customerId") String customerId,
                                    HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<Department> deptList = customerService.queryDepartment(customerId, userId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg",true);
        gsonView.addStaticAttribute("customerList", deptList);
        return gsonView;
    }

    @RequestMapping("/organization/apply")
    public GsonView applyDepartment(@RequestParam("submitReasons") String submitReasons,
                                    @RequestParam("applyDeptId") String applyDeptId,
                                    EnclosureApply enclosureApply,
                                    HttpServletRequest request) {
//        System.out.println(applyTime);
        final String EMPTY_REASONS_ERROR = "申请理由不能为空";
        if(submitReasons == null)
            return GsonView.createErrorView(EMPTY_REASONS_ERROR);

        String userId = (String) request.getSession().getAttribute("userId");
        //enclosureApply.setEnclosureApplyId(11);
        enclosureApply.setReasons(submitReasons);
        enclosureApply.setDeptId(applyDeptId);
        enclosureApply.setUserId(userId);
//        enclosureApply.setApplyTime(Date);
        customerMapper.insertEnclosureApply(enclosureApply);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }

    @RequestMapping("/customerList")
    public String customerList(){
        return "customerList";
    }

    @RequestMapping("/queryCustomer")
    public GsonView queryCustomerInfo(@RequestParam("searchWord") String keyword){
        List<Customer> customerList = customerService.queryCustomerInfo(keyword);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("customerList", customerList);
        return gsonView;
    }

    @RequestMapping("/customerInfo")
    public String customerInfo(){
        return "customerInfo";
    }

}
