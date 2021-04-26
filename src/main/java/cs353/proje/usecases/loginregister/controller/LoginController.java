package cs353.proje.usecases.loginregister.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.customer.dto.Customer;
import cs353.proje.usecases.loginregister.dto.Courier;
import cs353.proje.usecases.loginregister.dto.RestaurantOwner;
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

    @PostMapping("/registerCustomer")
    public Response registerCustomer(@RequestBody Customer customerRegisterInfo)
    {
        return loginService.registerCustomer(customerRegisterInfo);
    }

    @PostMapping("/registerCourier")
    public Response registerCourier(@RequestBody Courier courierRegisterInfo)
    {
        return loginService.registerCourier(courierRegisterInfo);
    }

    @PostMapping("/registerRestaurantOwner")
    public Response registerRestaurantOwner(@RequestBody RestaurantOwner restaurantOwnerRegisterInfo)
    {
        return loginService.registerRestaurantOwner(restaurantOwnerRegisterInfo);
    }
}
