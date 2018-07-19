package com.xuebei.crm.login;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/7/19.
 */
@Service
public class CrmUserIdServiceImpl implements CrmUserIdService {

    @Override
    public String getCrmUserId(HttpServletRequest request){
        return (String)request.getSession().getAttribute("crmUserId");
    }
}
