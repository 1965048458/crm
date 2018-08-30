package com.xuebei.crm.dto;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class GsonView extends AbstractView {

    private static Logger logger = LoggerFactory.getLogger(GsonView.class);

    private String datePattern = "yyyy-MM-dd HH:mm:ss";
    /** Default content type. Overridable as bean property. */
    private static final String DEFAULT_JSON_CONTENT_TYPE = "application/json;charset=UTF-8";
    private String jsonObjectName;

    private boolean escapeHtml = true;

    private Map<Class, Object> typeAdapterMap = new HashMap<Class, Object>();


    public GsonView() {
        super();
    }
    public GsonView(String jsonObjectName, ExclusionStrategy excludeStrategy) {
        super();
        this.jsonObjectName = jsonObjectName;
        this.excludeStrategy = excludeStrategy;
    }
    private int responseStatus = HttpStatus.OK.value();
    private ExclusionStrategy excludeStrategy;
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setStatus(getResponseStatus());
        response.setContentType(getContentType());
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(datePattern);
        if (excludeStrategy != null) {
            gsonBuilder.setExclusionStrategies(excludeStrategy);
        }else{
            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
        }

        if(!escapeHtml){
            gsonBuilder.disableHtmlEscaping();
        }

        for (Map.Entry<Class, Object> adapterEntry: typeAdapterMap.entrySet()) {
            gsonBuilder.registerTypeAdapter(adapterEntry.getKey(), adapterEntry.getValue());
        }

        Gson gson = gsonBuilder.create();
        String output = gson.toJson(jsonObjectName == null ? model : model.get(jsonObjectName));
        response.getWriter().write(output);
    }
    /**
     * @return the objectName in model
     */
    public String getJsonObjectName() {
        return jsonObjectName;
    }

    public void setDatePattern(String pattern){
        this.datePattern = pattern;
    }
    /**
     * @param objectName the objectName in model that will be convert to json, if not set, then convert the hole model to json
     */
    public void setJsonObjectName(String objectName) {
        this.jsonObjectName = objectName;
    }
    /**
     * @return the excludeStrategy
     */
    public ExclusionStrategy getExcludeStrategy() {
        return excludeStrategy;
    }
    /**
     * @param excludeStrategy the excludeStrategy to set
     */
    public void setExcludeStrategy(ExclusionStrategy excludeStrategy) {
        this.excludeStrategy = excludeStrategy;
    }
    /* (non-Javadoc)
     * @see org.springframework.web.servlet.view.AbstractView#getContentType()
     */
    @Override
    public String getContentType() {
        return DEFAULT_JSON_CONTENT_TYPE;
    }
    /**
     * @return the responseStatus
     */
    public int getResponseStatus() {
        return responseStatus;
    }
    /**
     * @param responseStatus the responseStatus to set
     */
    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    public boolean isEscapeHtml() {
        return escapeHtml;
    }

    public void setEscapeHtml(boolean escapeHtml) {
        this.escapeHtml = escapeHtml;
    }

    public void addTypeAdapter(Class clz, Object typeAdapter){
        typeAdapterMap.put(clz, typeAdapter);
    }

    public static GsonView createErrorView(String errMsg) {
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", false);
        gsonView.addStaticAttribute("errMsg", errMsg);
        return gsonView;
    }

    public static GsonView createSuccessView() {
        GsonView gsonView = new GsonView();
        gsonView.addStaticAttribute("successFlg", true);
        return gsonView;
    }
}
