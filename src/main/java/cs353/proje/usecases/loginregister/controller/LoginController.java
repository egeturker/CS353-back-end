package cs353.proje.usecases.loginregister.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.loginregister.dto.User;
import cs353.proje.usecases.loginregister.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController("LoginController")
@CrossOrigin
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public Response login(@Valid @RequestBody User loginInfo)
    {
        return loginService.login(loginInfo);
    }

    @PostMapping("/register")
    public Response register(@RequestBody User registerInfo)
    {
        return loginService.register(registerInfo);
    }
}
