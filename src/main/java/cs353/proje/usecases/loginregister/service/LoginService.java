package cs353.proje.usecases.loginregister.service;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.loginregister.dto.User;
import cs353.proje.usecases.loginregister.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    public Response login(User loginInfo)
    {
        User userInfo = loginRepository.login(loginInfo);
        if (userInfo != null)
            return new Response(true, "Login is successful", userInfo);
        else
            return new Response(false, "Login is unsuccessful", null);
    }

    public Response register(User registerInfo) {
        int result = loginRepository.register(registerInfo);
        if (result > 0)
            return new Response(true, "Registration is successful", null);
        else
            return new Response(false, "Registration is unsuccessful", null);
    }
}
