package com.xuebei.crm.error;

import com.xuebei.crm.dto.GsonView;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 统一异常返回controller, 只负责返回异常结果.
 * 返回json格式异常结果时使用{@link #errorJson(HttpServletRequest)}
 * 返回text/html结果时使用
 * {@link org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController#errorHtml(HttpServletRequest, HttpServletResponse)}
 * 按HttpStatus返回与code对应的视图
 * 异常详细由@{@link CrmExceptionHandler}处理
 * Created by Rong Weicheng on 2018/7/12.
 */
public class CrmErrorController extends BasicErrorController {

    public CrmErrorController(ErrorAttributes errorAttributes,
                              ErrorProperties errorProperties,
                              List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, errorProperties, errorViewResolvers);
    }

    @RequestMapping(produces = "application/json")
    public GsonView errorJson(HttpServletRequest request) {
        String errMsg = (String)request.getAttribute("errMsg");
        GsonView failedView = new GsonView();
        failedView.addStaticAttribute("successFlg", false);
        failedView.addStaticAttribute("errMsg", errMsg);
        return failedView;
    }
}
