package cs353.proje.usecases.customer.controller;

import cs353.proje.usecases.common.dto.Order;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.customer.dto.Customer;
import cs353.proje.usecases.customer.dto.OrderFromCustomer;
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
        return customerService.getAllRestaurants();
    }

    //Ege
    @GetMapping("/customerData/id={id}")
    public Response getCustomerData(@PathVariable(value="id") int customer_id)
    {
        return customerService.getCustomerData(customer_id);
    }

    //Ege
    @PostMapping("/customerData/id={id}")
    public Response updateCustomerData(@PathVariable(value="id") int customer_id, @RequestBody Customer newCustomerData)
    {
        return customerService.updateCustomerData(customer_id, newCustomerData);
    }

    //Ege
    @GetMapping("/orders/id={id}")
    public Response getOldOrders(@PathVariable(value="id") int customer_id)
    {
        return customerService.getOldOrders(customer_id);
    }

    //Ege
    @GetMapping("/restaurants/id={id}")
    public Response getRestaurants(@PathVariable(value="id") int customer_id)
    {
        return null;
    }

    @GetMapping("/restaurants/id={id}/search={searchkey}")
    public Response getRestaurantsWithSearch(@PathVariable(value="id") int customer_id,
                                             @PathVariable(value="searchkey") String searchkey)
    {
        return null;
    }

    //Kaan
    @GetMapping("/restaurants/id={id}/open={open}/rating={min}to{max}")
    public Response getRestaurantsWithFilter(@PathVariable(value="id") int customer_id,
                                             @PathVariable(value="open") String open,
                                             @PathVariable(value="min") double minRating,
                                             @PathVariable(value="max") double maxRating)
    {
        return customerService.getRestaurantsWithFilter(customer_id, open, minRating, maxRating);
    }

    //Kaan
    @GetMapping("/coupons/id={id}")
    public Response getCoupons(@PathVariable(value="id") int customer_id)
    {

        return customerService.getCoupons(customer_id);
    }

    //Kaan
    @GetMapping("/restaurantInfo/id={id}")
    public Response getRestaurantInfo(@PathVariable(value="id") int restaurant_id)
    {

        return customerService.getRestaurantInfo(restaurant_id);
    }

    @GetMapping("/restaurantMenu/id={id}")
    public Response getRestaurantMenu(@PathVariable(value="id") int restaurant_id)
    {
        return customerService.getRestaurantMenu(restaurant_id);
    }

    @GetMapping("/restaurantMenuByCategory/id={id}")
    public Response getRestaurantMenuByCategory(@PathVariable(value="id") int restaurant_id)
    {
        return customerService.getRestaurantMenuByCategory(restaurant_id);
    }

    //Returns List<Ingredient> in the Response
    @GetMapping("/ingredients/id={menu_item_id}")
    public Response getIngredients(@PathVariable(value="menu_item_id") int menu_item_id)
    {

        return customerService.getIngredients(menu_item_id);
    }

    //Kaan
    @PostMapping("/order")
    public Response createNewOrder(@RequestBody OrderFromCustomer order)
    {
        return customerService.createNewOrder(order);
    }

    //Kaan
    @PostMapping("/addFavorite/customer_id={customer_id}")
    public Response addFavorite(@PathVariable(value="customer_id") int customer_id,
                                @RequestBody int restaurant_id)
    {
        return null;
    }
}
