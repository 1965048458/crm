package com.xuebei.crm;

import com.xuebei.crm.error.CrmErrorController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

	@Bean
    public CrmErrorController getCrmErrorController() {
	    return new CrmErrorController(new DefaultErrorAttributes(true), new ErrorProperties());
    }
}
