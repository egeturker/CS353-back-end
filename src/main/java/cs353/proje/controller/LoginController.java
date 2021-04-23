package cs353.proje.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ManageRegistrationsController")
public class LoginController {

    @GetMapping("/deneme")
    public String deneme()
    {
        return "deneme stringi";
    }

}
