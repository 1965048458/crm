package com.xuebei.crm.temp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoController {

    private static List<DemoItem> demoItems;

    static {
        demoItems = new ArrayList<>();
        demoItems.add(new DemoItem("日志功能", "/journal/toList"));
        demoItems.add(new DemoItem("增加联系人(三级学院添加)", "/customer/addContactsPage?deptId=dept7"));
        demoItems.add(new DemoItem("增加顶级机构(customerzju)", "/customer/addDepartmentPage?customerId=customerzju"));
    }

    @RequestMapping("/list")
    public String demoListPage(ModelMap modelMap) {

        modelMap.addAttribute("demoItems", demoItems);

        return "demoList";
    }

}
