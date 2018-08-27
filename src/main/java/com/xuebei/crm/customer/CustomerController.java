package com.xuebei.crm.customer;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.department.DeptMapper;
import com.xuebei.crm.department.DeptService;
import com.xuebei.crm.department.WarningBeforeCreateEnum;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.member.Member;
import com.xuebei.crm.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import com.xuebei.crm.utils.UUIDGenerator;
import com.xuebei.crm.exception.DepartmentNameDuplicatedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private DeptMapper deptMapper;

    @RequestMapping("searchCustInfo")
    public String searchInfo(){
        return "searchCustomerInfo";
    }



    @Autowired
    private CustomerServiceImpl customerService;

    private static String AUTHENTICATION_ERROR_MSG = "用户没有改操作权限";
    private final static String DEPT_NAME_BLANK_ERROR_MSG = "部门名称不能为空";


    private String acquireUserId(HttpServletRequest request) {
        return (String)request.getSession().getAttribute("userId");
    }

    @RequestMapping("add")
    public String addCustomer() { return "addCustomer"; }

    @RequestMapping("new")
    public GsonView newCustomer(@RequestParam("schoolType") String schoolType,
                                @RequestParam("name") String name,
                                @RequestParam("profile") String profile,
                                @RequestParam("website") String website,
                                HttpServletRequest request) {
        GsonView gsonView = new GsonView();

        String nm = name.trim();
        List<String> schList = customerMapper.searchSchool(nm);
        if(schList.size() !=0){
            gsonView.addStaticAttribute("exist",true);
        }else {

            String customer_id = UUIDGenerator.genUUID();
            String creator_id = (String) request.getSession().getAttribute("userId");
            String create_ts = "";
            String updater_id = creator_id;
            String update_ts = "";


            if (profile.equals("") && website.equals("")) {
                customerService.newSchool(customer_id, name, schoolType, null,
                        null, creator_id, create_ts, updater_id, update_ts);
            } else if (profile.equals("")) {
                customerService.newSchool(customer_id, name, schoolType, null, website,
                        creator_id, create_ts, updater_id, update_ts);
            } else if (website.equals("")) {
                customerService.newSchool(customer_id, name, schoolType, profile,
                        null, creator_id, create_ts, updater_id, update_ts);
            } else {
                customerService.newSchool(customer_id, name, schoolType, profile, website, creator_id, create_ts, updater_id, update_ts);
            }

            String companyId = companyMapper.queryCompanyIdByUserId(creator_id);
            customerMapper.insertCustomerCompanyRelation(customer_id, companyId);

            gsonView.addStaticAttribute("successFlg", true);
        }
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



    @RequestMapping("/addTopDepartment")
    public String addDepartmentPage(@RequestParam("customerId") String customerId,
                                      ModelMap modelMap) {
        modelMap.addAttribute("customerId", customerId);
        return "addTopDepartment";
    }

    @RequestMapping("/addSubDepartment")
    public String addSubDepartment(@RequestParam("deptId") String deptId,
                                   ModelMap modelMap) {

        Department department = customerMapper.queryDepartmentById(deptId);
        modelMap.addAttribute("customerId", department.getCustomer().getCustomerId());
        modelMap.addAttribute("parentDeptId", deptId);
        return "addSubDepartment";
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
                                      @RequestParam(value = "profile", required = false) String profile,
                                      HttpServletRequest request) {
        if (!customerService.isUserHasCustomer(acquireUserId(request), customerId)) {
            return GsonView.createErrorView(AUTHENTICATION_ERROR_MSG);
        }

        if (StringUtils.isEmptyOrWhitespace(deptName)) {
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }
        String userId = (String) request.getSession().getAttribute("userId");
        WarningBeforeCreateEnum warning = deptService.warningBeforeCreate(deptName,customerId,userId);
        switch (warning){
            case APPLY_BY_ME:
                return GsonView.createErrorView(WarningBeforeCreateEnum.APPLY_BY_ME.getName());
            case APPLY_BY_OTHERS:
                return GsonView.createErrorView(WarningBeforeCreateEnum.APPLY_BY_OTHERS.getName());
            case NO_ONE_APPLY:
            default:
                break;
        }
        if(customerMapper.isDepartNameExist(customerId,deptName)){
            Department expiredDept = customerMapper.searchDeptByName(customerId,deptName);
            customerMapper.updateDept(website,profile,expiredDept.getDeptId());
            customerMapper.updateEnclosureApply(expiredDept.getDeptId(),userId);
        }else{
            Customer customer = new Customer();
            customer.setCustomerId(customerId);

            Department dept = new Department();
            dept.setDeptId(UUIDGenerator.genUUID());
            dept.setDeptName(deptName);
            dept.setWebsite(website);
            dept.setProfile(profile);
            dept.setEnclosureStatus(EnclosureStatusEnum.APPLYING);
            dept.setCustomer(customer);
            dept.setParent(new Department());

            EnclosureApply enclosureApply = new EnclosureApply();
            String reasons = "机构编辑申请";
            enclosureApply.setDeptId(dept.getDeptId());
            enclosureApply.setUserId(userId);
            enclosureApply.setReasons(reasons);
            customerMapper.insertDepartment(dept);
            customerMapper.insertEnclosureApply(enclosureApply);
        }
        return GsonView.createSuccessView();
    }

    /**
     * 增加专业（二级机构）
     * 1. 用户是否有操作该一级机构的权限，即用户圈了该专业
     * 2. 机构名检查，机构名不为空，机构名不重复
     */
    @RequestMapping("/action/addSubDepartment")
    public GsonView addSubDepartment(@RequestParam("parentDeptId") String parentDeptId,
                                     @RequestParam("deptName") String deptName) {
        // TODO： 用户是否圈了该专业检查

        // 检查是否为空
        deptName = deptName.trim();
        if (StringUtils.isEmptyOrWhitespace(deptName)) {
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }

        // 检查机构名称是否重复
        if (customerService.isSubDepartmentNameDuplicated(parentDeptId, deptName)) {
            return GsonView.createErrorView("专业(部门)名称重复，请重新输入");
        }

        customerService.addSubDepartment(parentDeptId, deptName);



        return GsonView.createSuccessView();
    }

    /**
     * 为前段提供 实时检测 专业（二级机构）可以使用 的接口
     */
    @RequestMapping("/action/subDepartmentCheck")
    public GsonView subDepartmentCheck(@RequestParam("parentDeptId") String parentDeptId,
                                       @RequestParam("deptName") String deptName) {
        deptName = deptName.trim();
        if (StringUtils.isEmptyOrWhitespace(deptName)) {
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }
        if (customerService.isSubDepartmentNameDuplicated(parentDeptId, deptName)) {
            return GsonView.createErrorView("此专业已存在，无法添加");
        }
        return GsonView.createSuccessView();
    }


    @RequestMapping("/action/departmentCheck")
    public GsonView departmentCheck(@RequestParam("deptName")String deptName,
                                    @RequestParam("customerId") String customerId,
                                    HttpServletRequest request){
        deptName = deptName.trim();
        if(StringUtils.isEmptyOrWhitespace(deptName)){
            return GsonView.createErrorView(DEPT_NAME_BLANK_ERROR_MSG);
        }
        String userId = (String) request.getSession().getAttribute("userId");
        WarningBeforeCreateEnum warning = deptService.warningBeforeCreate(deptName,customerId,userId);
        switch (warning){
            case APPLY_BY_ME:
                return GsonView.createErrorView(WarningBeforeCreateEnum.APPLY_BY_ME.getName());
            case APPLY_BY_OTHERS:
                return GsonView.createErrorView(WarningBeforeCreateEnum.APPLY_BY_OTHERS.getName());
            default:
                break;
        }
        return GsonView.createSuccessView();
    }

    @RequestMapping("addContactsPage")
    public String addContactsPage(@RequestParam("deptId") String deptId,
                                  ModelMap modelMap,
                                  HttpServletRequest request) {
        Department department = customerMapper.queryDepartmentById(deptId);
        modelMap.addAttribute("customerId", department.getCustomer().getCustomerId());
        modelMap.addAttribute("deptId", deptId);

        // 部门ID空 或 用户不能修改该部门（因为客户不属于用户的公司）
        Department dept = customerMapper.queryDepartmentById(deptId);
        if (dept == null || !customerService.isUserHasCustomer(acquireUserId(request), dept.getCustomer().getCustomerId())) {
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
                                Contacts contacts,
                                HttpServletRequest request) {
        final String TOP_DEPT_CONTACTS_TYPE_NOT_NULL_ERROR_MSG = "顶级机构中联系人不允许有职位";
        final String SUB_DEPT_CONTACTS_NULL_ERROR_MSG = "子机构中联系人需要有职位信息";
        final String REAL_NAME_BLANK_ERROR_MSG = "联系人姓名不能为空";
        final String TEL_AND_PHONE_BLANK_ERROR_MSG ="联系人手机号和座机号不能同时为空";

        // 权限检查
        Department dept = customerMapper.queryDepartmentById(deptId);
        if (dept == null || !customerService.isUserHasCustomer(acquireUserId(request), dept.getCustomer().getCustomerId())) {
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
    public GsonView getContactsTypeList(@RequestParam("deptId") String deptId, HttpServletRequest request) {
        Department dept = customerMapper.queryDepartmentById(deptId);
        if (dept == null || !customerService.isUserHasCustomer(acquireUserId(request), dept.getCustomer().getCustomerId())) {
            return GsonView.createErrorView(AUTHENTICATION_ERROR_MSG);
        }
        String customerId = dept.getCustomer().getCustomerId();
        List<ContactsType> contactsTypes = customerMapper.queryContactsTypes(customerId);

        GsonView gson = GsonView.createSuccessView();
        gson.addStaticAttribute("contactsTypes", contactsTypes);
        return gson;
    }

    @RequestMapping("/organization")
    public String organization(@RequestParam("customerId") String customerId,
                               ModelMap modelMap){
        Customer customer = customerMapper.queryCustomer(customerId);
        modelMap.addAttribute("customerId", customer.getCustomerId());
        modelMap.addAttribute("customerName", customer.getCustomerName());
        return "customer/organization";
    }

    @RequestMapping("/organization/show")
    public GsonView queryDepartment(@RequestParam("customerId") String customerId,
                                    HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<Department> departmentList = deptService.departmentList(customerId,userId);
        List searchList = new ArrayList();
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg",true);
        gsonView.addStaticAttribute("customerList", departmentList);
        gsonView.addStaticAttribute("searchList",searchList);
        return gsonView;
    }

    @RequestMapping("/organization/apply")
    public GsonView applyDepartment(@RequestParam("applyReasons") String reasons,
                                    @RequestParam("applyDeptId") String applyDeptId,
                                    HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        final String EMPTY_REASONS_ERROR = "申请理由不能为空";
        if(reasons == null || reasons.equals("")) {
            gsonView.addStaticAttribute("successFlg", false);
            gsonView.addStaticAttribute("errMsg",EMPTY_REASONS_ERROR);
        }
        else{
            String userId = (String) request.getSession().getAttribute("userId");
            deptService.enclosureApply(applyDeptId,userId, reasons);
            gsonView.addStaticAttribute("successFlg", true);
        }
        return gsonView;
    }

    @RequestMapping("/organization/delayApply")
    public GsonView delayApply(@RequestParam("deptId") String deptId,
                               @RequestParam("delayApplyReasons") String reasons,
                               HttpServletRequest request){
        GsonView gsonView = new GsonView();
        String userId = (String) request.getSession().getAttribute("userId");
        deptService.enclosureDelayApply(deptId,userId,reasons);
        gsonView.addStaticAttribute("successFlg",true);
        return gsonView;
    }

    @RequestMapping("/customerList")
    public String customerList(){
        return "customerList";
    }

    @RequestMapping("/queryCustomer")
    public GsonView queryCustomerInfo(@RequestParam("searchWord") String keyword,
                                      HttpServletRequest request){
        String userId = (String) request.getSession().getAttribute("userId");
        List<Customer> myCustomers = customerService.getMyCustomers(userId);
        List<Customer> customerList = customerService.queryCustomerInfo(keyword);
        List<Customer> commonCustomers = customerService.getCommonCustomers(userId);
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("commonCustomers", commonCustomers);
        gsonView.addStaticAttribute("myCustomers", myCustomers);
        gsonView.addStaticAttribute("customerList", customerList);
        return gsonView;
    }

    @RequestMapping("/getMyCustomers")
    public GsonView getMyCustomers(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<Customer> myCustomers = customerService.getMyCustomers(userId);//"57259d54f9994209a813e8ad2b297b3a"
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        gsonView.addStaticAttribute("myCustomers", myCustomers);
        return gsonView;
    }

    @RequestMapping("/lastTime")
    public GsonView lastTime(@RequestParam("customerId") String customerId){
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("lastTime", customerService.lastFollowTs(customerId));
        return gsonView;
    }

    @RequestMapping("/customerInfo")
    public String customerInfo(@RequestParam("customerId")String customerId,
                               ModelMap modelMap){
        Customer customer = customerMapper.queryCustomer(customerId);
        modelMap.addAttribute("customerId", customerId);
        modelMap.addAttribute("customerName", customer.getCustomerName());
        return "customerInfo";
    }

    @RequestMapping("/contactsInfo")
    public String contactsInfoPage(@RequestParam("contactsId")String contactsId,
                                   ModelMap modelMap) {
        Contacts contacts = customerMapper.queryContactsById(contactsId);
        List<FollowUpRecord> followUpRecords = customerMapper.queryFollowUpRecordsByContactsId(contactsId);
        modelMap.addAttribute("followUpRecords", followUpRecords);
        modelMap.addAttribute("contacts", contacts);
        return "contactsInfo";
    }

    @RequestMapping("/editCustomer")
    public String editCustomerPage(@RequestParam("customerId") String customerId,
                                   HttpServletRequest request,
                                   ModelMap modelMap) {
        String userId = (String)request.getSession().getAttribute("userId");

        List<Department> deptList = deptService.myDepartmentList(customerId, userId);

        modelMap.addAttribute("customerId", customerId);
        modelMap.addAttribute("departments", deptList);

        return "editCustomer";
    }

    @RequestMapping("/action/addContactsType")
    public GsonView insertContactsType(@RequestParam("customerId") String customerId,
                                       @RequestParam("contactsTypeName") String contactsTypeName) {
        customerId = customerId.trim();
        Boolean isExist = customerMapper.isContactsTypeExist(customerId, contactsTypeName);
        if (isExist) {
            return GsonView.createErrorView("已经有同名的联系人职位");
        }
        customerMapper.insertContactsType(customerId, contactsTypeName);
        return GsonView.createSuccessView();
    }

}
