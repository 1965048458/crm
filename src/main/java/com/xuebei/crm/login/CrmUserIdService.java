package com.xuebei.crm.login;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/7/19.
 */
public interface CrmUserIdService {

    String getCrmUserId(HttpServletRequest request);
}
