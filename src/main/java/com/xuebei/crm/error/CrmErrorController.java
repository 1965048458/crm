package com.xuebei.crm.error;

import com.google.gson.GsonBuilder;
import com.xuebei.crm.dto.GsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;

/**
 * 统一异常返回结果controller.
 * <p>
 * 新增以json格式返回异常结果的接口
 * Created by Rong Weicheng on 2018/7/12.
 */
public class CrmErrorController extends BasicErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrmErrorController.class);

    private ErrorAttributes errorAttributes;

    public CrmErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        logError(request);
        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(
                request, isIncludeStackTrace(request, MediaType.TEXT_HTML)));
        return new ModelAndView("error/500", model);
    }

    @RequestMapping(produces = "application/json")
    public GsonView errorJson(HttpServletRequest request) {
        Throwable cause = this.errorAttributes.getError(new ServletWebRequest(request));
        logError(request, cause);

        GsonView failedView = new GsonView();
        failedView.addStaticAttribute("successFlg", false);
        failedView.addStaticAttribute("errMsg", cause.getMessage());
        return failedView;
    }

    private void logError(HttpServletRequest request) {
        Throwable cause = this.errorAttributes.getError(new ServletWebRequest(request));
        logError(request, cause);
    }

    private void logError(HttpServletRequest request, Throwable cause) {
        HttpServletRequest originalRequest = (HttpServletRequest)((ServletRequestWrapper)request).getRequest();
        String uri = originalRequest.getServletPath();
        String params = getParamAsString(request);
        LOGGER.error("程序异常. uri={}, params={}, errMsg={}", uri, params, cause.getMessage(), cause);
    }

    private String getParamAsString(HttpServletRequest request) {
        return new GsonBuilder().create().toJson(request.getParameterMap());
    }
}
