package cs353.proje.usecases.admin.controller;

import cs353.proje.usecases.admin.service.AdminService;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.loginregister.dto.Courier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("AdminController")
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/paymentMethodReport")
    public Response paymentMethodReport()
    {
        return adminService.paymentMethodReport();
    }

}
