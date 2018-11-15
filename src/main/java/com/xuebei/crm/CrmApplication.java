package com.xuebei.crm;

import com.xuebei.crm.error.CrmErrorController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

	@Bean
    public CrmErrorController getCrmErrorController(ApplicationContext context) {
		List<ErrorViewResolver> errorViewResolvers = new ArrayList<>();
		errorViewResolvers.add(new DefaultErrorViewResolver(context, new ResourceProperties()));
	    return new CrmErrorController(new DefaultErrorAttributes(false), new ErrorProperties(), errorViewResolvers);
    }
}
