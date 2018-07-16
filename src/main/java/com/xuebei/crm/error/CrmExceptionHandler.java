package com.xuebei.crm.error;


import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一异常处理器
 * 所有从controller层抛出的异常交由该类打印异常信息
 * 处理完毕后将请求转发到"/error"{@link CrmErrorController},由controller返回异常结果
 * @author Rong Weicheng
 */
@ControllerAdvice
public class CrmExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrmExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public void resolveException(HttpServletRequest request, HttpServletResponse response, Exception e) {
        String uri = request.getRequestURI();
        String requestParams = getParamAsString(request);
        String requestUserId = (String)request.getSession().getAttribute("userId");
        LOGGER.error("request uri:{}, userId:{}, params:{}, exception:",
                uri, requestUserId, requestParams, e.getMessage(), e);
        try {
            request.setAttribute("errMsg", "系统异常");
            request.getRequestDispatcher("/error").forward(request, response);
        } catch (ServletException | IOException otherException) {
            LOGGER.error(otherException.getMessage(), otherException);
        }
    }

    private String getParamAsString(HttpServletRequest request) {
        return new GsonBuilder().create().toJson(request.getParameterMap());
    }
}
