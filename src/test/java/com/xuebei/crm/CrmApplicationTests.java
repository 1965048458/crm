package com.xuebei.crm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DecimalFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrmApplicationTests {

	@Test
	public void contextLoads() {
		DecimalFormat f1,f2;

		f1=new DecimalFormat("0.000");
		f2=new DecimalFormat("#.000");

		System.out.println("带0号的"+f1.format(2.20));
		System.out.println("带#的"+f2.format(2.20));

		f1=new DecimalFormat("0.00");
		f2=new DecimalFormat("0.##");

		System.out.println("小数点00："+f1.format(0.2));
		System.out.println("小数点##："+f2.format(0.2));


		f1=new DecimalFormat("0.00");
		f2=new DecimalFormat("##.00");

		System.out.println("00的整数："+f1.format(22));
		System.out.println("##的整数："+f2.format(22));
	}

}
