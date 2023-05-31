package t.lt.file.biz.controller.dept;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import t.lt.file.biz.service.dept.DeptService;

import javax.annotation.Resource;


@Controller
@RequestMapping("/dept")
public class DeptController {

    @Resource
    private DeptService deptService;



    @GetMapping("/test")
    @ResponseBody
    public String test(){
        return "cms后台服务";
    }
}
