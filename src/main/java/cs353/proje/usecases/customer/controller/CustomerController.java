package cs353.proje.usecases.customer.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("CustomerController")
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/allRestaurants")
    public Response getAllRestaurants()
    {
        Response response = customerService.getAllRestaurants();
        return response;
    }
}
