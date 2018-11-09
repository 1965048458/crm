package com.xuebei.crm.login;

import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.xuebei.crm.company.Company;
import com.xuebei.crm.company.CompanyMapper;
import com.xuebei.crm.company.CompanyUser;
import com.xuebei.crm.dto.GsonView;
import com.xuebei.crm.message.Apply;
import com.xuebei.crm.message.MsgService;
import com.xuebei.crm.message.ProjectApply;
import com.xuebei.crm.user.User;
import com.xuebei.crm.utils.UUIDGenerator;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rong Weicheng on 2018/7/9.
 */
@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private LoginRegisterMapper loginRegisterMapper;

    @Autowired
    private SendCaptchaService sendCaptchaService;


    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private MsgService msgService;

    public static final String SUCCESS_FLG = "successFlg";
    public static final String ERRMSG = "errMsg";

    @RequestMapping("")
    public String registerAndLogin() {
        return "login";
    }


    @RequestMapping("myAccountInfor")
    public String mine() {
        return "mine";
    }



    @RequestMapping("staffAudit")
    public String staffAudit(@RequestParam("companyId")String companyId,
                             HttpServletRequest request) {
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");
        String userId = loginRegisterMapper.queryUserIdByCompanyId(crmUserId, companyId);
        request.getSession().setAttribute("userId", userId);
        return "staffAudit";
    }

    @RequestMapping("accountSecurity")
    public String accountSecurity() {
        return "accountAndSecurity";
    }

    @RequestMapping("accountSecurity/ini")
    public GsonView accountSecurityIni(HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");

        User user = loginRegisterMapper.searchMessage(crmUserId);
        gsonView.addStaticAttribute(SUCCESS_FLG, true);

        gsonView.addStaticAttribute("user", user);


        return gsonView;
    }

    @RequestMapping("accountSecurity/dropOut")
    public GsonView accountSecurityDropOut(HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        request.getSession().removeAttribute("crmUserId");
        request.getSession().removeAttribute("userId");
        gsonView.addStaticAttribute(SUCCESS_FLG, true);
        return gsonView;
    }

    @RequestMapping("staffAudit/check")
    public GsonView staffAuditCheck(HttpServletRequest request) {

        GsonView gsonView = new GsonView();
        String userId = (String) request.getSession().getAttribute("userId");


        List<CompanyUser> companyUsers = companyMapper.queryApplyStaff(userId);
        gsonView.addStaticAttribute(SUCCESS_FLG, true);

        gsonView.addStaticAttribute("companyUsers", companyUsers);

        return gsonView;
    }

    @RequestMapping("/administrator")
    public String administrator(ModelMap modelMap, HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userId");
        List<CompanyUser> companyUsers = companyMapper.queryApplyStaff(userId);
        String companyName = companyMapper.queryCompanyName(userId);
        String companyId = companyMapper.queryCompanyIdByUserId(userId);
        modelMap.addAttribute("companyName", companyName);
        modelMap.addAttribute("companyId", companyId);
        if (companyUsers == null) {
            modelMap.addAttribute("applyStaff", "0");
        } else {
            modelMap.addAttribute("applyStaff", companyUsers.size());
        }
        return "administrator";
    }

    @RequestMapping("/myAccount")
    public String myAccount(ModelMap modelMap, HttpServletRequest request) {
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");

//        String userType = companyMapper.queryUserType(crmUserId);
        String userType = "staff";
        String realName = loginRegisterMapper.queryRealName(crmUserId);

//        if(userType != null) {
//            if (userType.equals("ADMIN")) {
//                List<CompanyUser> companyUsers = companyMapper.queryApplyStaff(crmUserId);
//                if (companyUsers == null) {
//                    modelMap.addAttribute("applyStaff", "0");
//                } else {
//                    modelMap.addAttribute("applyStaff", companyUsers.size());
//                    modelMap.addAttribute("applyStaffList", companyUsers);
//
//                }
//            }
//        }
        modelMap.addAttribute("realName", realName);
        modelMap.addAttribute("type", userType);
        return "myAccount";
    }

    @RequestMapping("/myCompany")
    public String  myCompany(HttpServletRequest request) {
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");
        if(crmUserId!= null) {

            return "myCompany";
        }

        return "error/500";
    }

    @RequestMapping("/myCompany/query")
	public GsonView myCompanyQuery(HttpServletRequest request) {
		GsonView gsonView = new GsonView();
		String crmUserId = (String) request.getSession().getAttribute("crmUserId");
		boolean tag1 = false;
		boolean tag2 = false;
		boolean tag3 = false;
		List<Company> company = companyMapper.queryCompanyList(crmUserId);
		for (Company c : company) {
			String userId = loginRegisterMapper.queryUserIdByCompanyId(crmUserId, c.getCompanyId());
			if (userId != null) {
				String userType = companyMapper.queryUserType(userId);
				if (userType.equals("ADMIN")) {
					c.setAdmin(true);
					if (!tag1) {
						tag1 = true;
					}
				}
				List<CompanyUser> companyUsers = companyMapper.queryApplyStaff(userId);
				if (companyUsers == null) {
					c.setApplyStaff(0);
				} else {
					c.setApplyStaff(companyUsers.size());
				}

				List<Apply> applyList = msgService.applyList(userId);
				List<ProjectApply> projectApplyList = msgService.getProjectApply(userId);

				int messageNum = applyList.size() + projectApplyList.size();
				c.setMessage(messageNum);
			} else {
				if (!tag2) {
					if (c.getStatus() != null && c.getStatus().equals("PERMITTED")) {
						tag2 = true;
					}
				}
				if (!tag3) {
					if (c.getStatus() != null && c.getStatus().equals("PENDING")) {
						tag3 = true;
					}
				}
			}
		}
		gsonView.addStaticAttribute(SUCCESS_FLG, true);
		gsonView.addStaticAttribute("myCompany", company);
		gsonView.addStaticAttribute("tag2", tag2);
		gsonView.addStaticAttribute("tag1", tag1);
		gsonView.addStaticAttribute("tag3", tag3);
		return gsonView;
	}

    @RequestMapping("/agreeApply")
    public GsonView agreeApply(@RequestParam("userId") String userId, HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        String crmUserId = companyMapper.queryCrmUserId(userId);
        companyMapper.agreeApply(userId, crmUserId);


        String realName = loginRegisterMapper.queryRealName(crmUserId);
        String tel = loginRegisterMapper.queryTel(crmUserId);

        String companyName = companyMapper.queryCompanyName(userId);
        String result = companyMapper.queryStatus(userId);

        if (result.equals("PERMITTED")) {
            result = "审核通过";
        } else if (result.equals("REFUSE")) {
            result = "审核未通过";
        }

        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = sendCaptchaService.sendAudit(realName, tel, companyName, result);
        } catch (ApiException e) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "短信发送失败");
            return gsonView;
        }
        if (rsp == null || !rsp.isSuccess()) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "发送失败");
        } else {
            gsonView.addStaticAttribute(SUCCESS_FLG, true);
            gsonView.addStaticAttribute(ERRMSG, "发送成功");
        }

        return gsonView;
    }

    @RequestMapping("/refuseApply")
    public GsonView refuseApply(@RequestParam("userId") String userId, HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        String crmUserId = companyMapper.queryCrmUserId(userId);
        companyMapper.refuseApply(userId, crmUserId);

        String realName = loginRegisterMapper.queryRealName(crmUserId);
        String tel = loginRegisterMapper.queryTel(crmUserId);
        String companyName = companyMapper.queryCompanyName(userId);
        String result = companyMapper.queryStatus(userId);

        if (result.equals("PERMITTED")) {
            result = "审核通过";
        } else if (result.equals("REFUSE")) {
            result = "审核未通过";
        }

        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = sendCaptchaService.sendAudit(realName, tel, companyName, result);
        } catch (ApiException e) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "短信发送失败");
            return gsonView;
        }
        if (rsp == null || !rsp.isSuccess()) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "发送失败");
        } else {
            gsonView.addStaticAttribute(SUCCESS_FLG, true);
            gsonView.addStaticAttribute(ERRMSG, "发送成功");
        }
        return gsonView;
    }


    @RequestMapping("/searchCompany")
    public GsonView searchCompany(@RequestParam("name") String name) {
        GsonView gsonView = new GsonView();

        List<Company> companyList = companyMapper.queryCompany(name);
        gsonView.addStaticAttribute("companyList", companyList);

        gsonView.addStaticAttribute(SUCCESS_FLG, true);

        return gsonView;
    }

    @RequestMapping("/applyCompany")
    public GsonView applyCompany(@RequestParam("name") String name, HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");
        Company company = companyMapper.queryCompanyComplete(name);
        if (company != null) {
            String status = companyMapper.queryApplyStatus(crmUserId, company.getCompanyId());
            if (status == null) {
                String userId = UUIDGenerator.genUUID();
                companyMapper.joinCompany(crmUserId, userId, company.getCompanyId());

                request.getSession().setAttribute("userId", userId);
                gsonView.addStaticAttribute(SUCCESS_FLG, true);
            } else if (status.equals("PENDING")) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute(ERRMSG, "已申请加入公司，等待审核！");
            } else if (status.equals("PERMITTED")) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute(ERRMSG, "已加入公司，请勿重复加入！");
            } else if (status.equals("REFUSE")) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                companyMapper.applyAgain(crmUserId,company.getCompanyId());
                gsonView.addStaticAttribute("again", true);
                gsonView.addStaticAttribute(ERRMSG, "已再次发送申请，请等待审核。");
            }
        } else {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "请输入正确的公司名称！");

        }

        return gsonView;
    }


    @RequestMapping("/login")
    public GsonView login(@RequestParam("tel") String tel,
                          @RequestParam("pwd") String pwd,
                          HttpServletRequest request) {

        GsonView gsonView = new GsonView();
        User user = loginService.searchTel(tel);
        if (user == null) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
        } else {
            if (user.getPwd().equals(pwd)) {
                request.getSession().setAttribute("crmUserId", user.getCRMUserId());
                gsonView.addStaticAttribute(SUCCESS_FLG, true);
            } else {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute("loginError", true);
            }
        }
        return gsonView;
    }

    @RequestMapping("/forgetPwd")
    public String forgetPwd() {
        return "findPwd";
    }

    @RequestMapping("/findPwd")
    public GsonView findPwd(@RequestParam("tel") String tel,
                            @RequestParam("pwd") String pwd,
                            @RequestParam("captcha") String captcha, HttpServletRequest request) {

        GsonView gsonView = new GsonView();
        User user = loginService.searchTel(tel);
        if (user == null) {
            gsonView.addStaticAttribute(SUCCESS_FLG, false);
            gsonView.addStaticAttribute(ERRMSG, "用户不存在");
        } else {
            Date start = (Date) request.getSession().getAttribute("CAPTCHA_CREATE_TS");
            if (start == null) {
                gsonView.addStaticAttribute(SUCCESS_FLG, false);
                gsonView.addStaticAttribute(ERRMSG, "请获取验证码");
            } else {
                Date now = new Date();
                long c = (now.getTime() - start.getTime()) / 1000;
                if (c > 900) {
                    gsonView.addStaticAttribute(SUCCESS_FLG, false);
                    gsonView.addStaticAttribute(ERRMSG, "验证码已过期，请重新发送");
                }
                String capt = (String) request.getSession().getAttribute("CAPTCHA");
                if (!capt.equals(captcha)) {
                    gsonView.addStaticAttribute(SUCCESS_FLG, false);
                    gsonView.addStaticAttribute(ERRMSG, "验证码错误");
                } else {
                    if (pwd.length() < 6) {
                        gsonView.addStaticAttribute(SUCCESS_FLG, false);
                        gsonView.addStaticAttribute(ERRMSG, "密码至少6位");
                    } else {
                        loginService.changePwd(tel, pwd);
                        gsonView.addStaticAttribute(SUCCESS_FLG, true);
                        request.getSession().removeAttribute("CAPTCHA");
                        request.getSession().removeAttribute("CAPTCHA_CREATE_TS");
                    }
                }
            }
        }

        return gsonView;
    }

    @RequestMapping("/chooseCompany")
    public GsonView loginWithCompany(@RequestParam("companyId") String companyId,
                                     HttpServletRequest request) {
        GsonView gsonView = new GsonView();
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");
        if (crmUserId == null) {
            return GsonView.createErrorView("用户未登陆");
        }
        String userId = loginRegisterMapper.queryUserIdByCompanyId(crmUserId, companyId);
        if (userId == null) {
            return GsonView.createErrorView("用户未加入公司或审核未通过");
        } else {
            request.getSession().setAttribute("userId", userId);
            String userType = companyMapper.queryUserType(userId);
            if (userType.equals("ADMIN")) {
                gsonView.addStaticAttribute("ADMIN", true);
                gsonView.addStaticAttribute("successFlg", true);
                return gsonView;
            } else {
                return GsonView.createSuccessView();
            }
        }
    }

    @RequestMapping("registerSuccess")
    public String registerSuccess(HttpServletRequest request) {
        String crmUserId = (String) request.getSession().getAttribute("crmUserId");
        if(crmUserId != null){
            return "registerSuccess";
        }else{
            return "error/500";
        }
    }

}
