package cs353.proje.usecases.loginregister.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.loginregister.dto.User;
import cs353.proje.usecases.loginregister.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("LoginController")
@CrossOrigin
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public Response login(@RequestBody User loginInfo)
    {
        return loginService.login(loginInfo);
    }

    @PostMapping("/register")
    public Response register(@RequestBody User registerInfo)
    {
        return loginService.register(registerInfo);
    }
}
