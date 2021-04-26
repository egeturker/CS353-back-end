package cs353.proje.usecases.customer.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.customer.dto.Customer;
import cs353.proje.usecases.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("CustomerController")
@RequestMapping("/customer")
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

    @GetMapping("/customerData/id={id}")
    public Response getCustomerData(@PathVariable(value="id") int customer_id)
    {
        return null;
    }

    @PostMapping("/customerData/id={id}")
    public Response updateCustomerData(@PathVariable(value="id") int customer_id, @RequestBody Customer newCustomerData)
    {
        return null;
    }

}
