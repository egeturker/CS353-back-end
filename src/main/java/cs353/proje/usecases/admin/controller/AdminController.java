package cs353.proje.usecases.admin.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.loginregister.dto.Courier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class AdminController {

    @GetMapping("/paymentMethodReport")
    public Response paymentMethodReport()
    {
        return null;
    }

}
