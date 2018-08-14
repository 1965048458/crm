package com.xuebei.crm.filter;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rong Weicheng on 2018/8/13.
 */
@Component
public class CrmUserFilter implements Filter {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    private List<String> beforeLoginUrls = new ArrayList<>();

    private List<String> endsWithList = new ArrayList<>();

    @PostConstruct
    public void initWhiteList() {
        beforeLoginUrls.add("");
        beforeLoginUrls.add("/");
        beforeLoginUrls.add("/login**");
        beforeLoginUrls.add("/forgetPwd**");
        beforeLoginUrls.add("/findPwd**");
        beforeLoginUrls.add("/findPwd/**");

        beforeLoginUrls.add("/register**");
        beforeLoginUrls.add("/telRegister**");
        beforeLoginUrls.add("/telRegister/**");
        beforeLoginUrls.add("/supplementaryInformation/add**");

        endsWithList.add(".js");
        endsWithList.add(".css");
        endsWithList.add(".png");
        endsWithList.add(".jpg");
        endsWithList.add(".svg");
        endsWithList.add(".gif");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String reqPath = request.getServletPath();
        if (isBeforeLogin(reqPath)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String crmUserId = (String) request.getSession().getAttribute("crmUserId");
            if (crmUserId == null) {
                servletRequest.getRequestDispatcher("/").forward(servletRequest, servletResponse);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    private boolean isBeforeLogin(String reqPath) {
        for (String url : beforeLoginUrls) {
            if (antPathMatcher.match(url, reqPath)) {
                return true;
            }
        }
        for (String s : endsWithList) {
            if (reqPath.endsWith(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
