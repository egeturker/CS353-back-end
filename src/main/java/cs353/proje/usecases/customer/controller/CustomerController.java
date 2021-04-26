package cs353.proje.usecases.customer.controller;

import cs353.proje.usecases.common.dto.Order;
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

    //Ege
    @GetMapping("/customerData/id={id}")
    public Response getCustomerData(@PathVariable(value="id") int customer_id)
    {
        return null;
    }

    //Ege
    @PostMapping("/customerData/id={id}")
    public Response updateCustomerData(@PathVariable(value="id") int customer_id, @RequestBody Customer newCustomerData)
    {
        return null;
    }

    //Ege
    @GetMapping("/orders/id={id}")
    public Response getOldOrders(@PathVariable(value="id") int customer_id)
    {
        return null;
    }

    //Ege
    @GetMapping("/restaurants/id={id}")
    public Response getRestaurants(@PathVariable(value="id") int customer_id)
    {
        return null;
    }

    //Kaan
    @GetMapping("/restaurants/id={id}/open={open}/rating={min}to{max}")
    public Response getRestaurantsWithFilter(@PathVariable(value="id") int customer_id,
                                             @PathVariable(value="open") boolean open,
                                             @PathVariable(value="min") double minRating,
                                             @PathVariable(value="max") double maxRating)
    {
        return null;
    }

    //Kaan
    @GetMapping("/coupons/id={id}")
    public Response getCoupons(@PathVariable(value="id") int customer_id)
    {
        return null;
    }

    //Kaan
    @GetMapping("/restaurantInfo/id={id}")
    public Response getRestaurantInfo(@PathVariable(value="id") int restaurant_id)
    {
        return null;
    }

    //Kaan
    @PostMapping("/addFavorite/customer_id={customer_id}")
    public Response addFavorite(@PathVariable(value="customer_id") int customer_id,
                                @RequestBody int restaurant_id)
    {
        return null;
    }

    //Kaan
    @PostMapping("/order")
    public Response createNewOrder(@RequestBody Order order)
    {
        return null;
    }

}
