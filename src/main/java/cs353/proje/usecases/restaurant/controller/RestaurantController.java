package cs353.proje.usecases.restaurant.controller;

import cs353.proje.usecases.common.dto.MenuItem;
import cs353.proje.usecases.common.dto.Order;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.customer.dto.Customer;
import cs353.proje.usecases.restaurant.dto.AllRestaurantData;
import cs353.proje.usecases.restaurant.dto.UpdatedRestaurantData;
import cs353.proje.usecases.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("RestaurantController")
@RequestMapping("/restaurant")
@CrossOrigin
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("/restaurantData/restaurant_id={id}")
    public Response getRestaurantData(@PathVariable(value="id") int restaurant_id)
    {
        return restaurantService.getRestaurantData(restaurant_id);
    }

    //Similar to updateCustomerData
    //Only the fields given in the UpdatedRestaurantData class will be updated
    @PostMapping("/restaurantData/restaurant_id={id}")
    public Response updateRestaurantData(@PathVariable(value="id") int restaurant_id,
                                         @RequestBody UpdatedRestaurantData updatedRestaurantData)
    {
        return restaurantService.updateRestaurantData(restaurant_id, updatedRestaurantData);
    }

    //Returns the orders taken from this restaurant starting from the newest.
    //If there are no orders, return (success = true) and (data = empty list)
    @GetMapping("/orders/restaurant_id={id}")
    public Response getOldOrders(@PathVariable(value="id") int restaurant_id)
    {
        List<Order> orderList;
        return null;
    }

    @GetMapping("/getRestaurantId/owner_id={id}")
    public Response getRestaurantId(@PathVariable(value="id") int owner_id)
    {
        return null;
    }

    //Restaurant states it is open now, set its status to 1
    @PostMapping("/open/restaurant_id={id}")
    public Response open(@PathVariable(value="id") int restaurant_id)
    {
        return null;
    }

    //Restaurant states it is closed now, set its status to 0
    @PostMapping("/close/restaurant_id={id}")
    public Response close(@PathVariable(value="id") int restaurant_id)
    {
        return null;
    }

    //Restaurant adds a new region that it serves food to
    @PostMapping("/addRegion/restaurant_id={restaurant_id}/region_id={region_id}")
    public Response addServedRegion(@PathVariable(value="restaurant_id") int restaurant_id,
                          @PathVariable(value="region_id") int region_id)
    {
        return restaurantService.addServedRegion(restaurant_id, region_id);
    }

    //Restaurant removes a region that it serves food to
    @PostMapping("/removeRegion/restaurant_id={restaurant_id}/region_id={region_id}")
    public Response removeServedRegion(@PathVariable(value="restaurant_id") int restaurant_id,
                                    @PathVariable(value="region_id") int region_id)
    {
        return restaurantService.removeServedRegion(restaurant_id, region_id);
    }

    //Restaurant adds a new MenuItem to its menu. Its will have no ingredients initially.
    //The RequestBody MenuItem includes all the information except the menu_item_id
    @PostMapping("/addMenuItem/restaurant_id={restaurant_id}")
    public Response addMenuItem(@PathVariable(value="restaurant_id") int restaurant_id,
                                @RequestBody MenuItem menuItem)
    {
        return null;
    }


    //THIS ONE CAN CAUSE PROBLEMS WHEN LOOKING AT THE OLD ORDERS, SO DON'T IMPLEMENT THIS FOR NOW
    //Restaurant removes one of its menu items, don't forget to check for the restaurant
    //and don't forget to remove all corresponding ingredients from the ingredient_list
    @PostMapping("/removeMenuItem/restaurant_id={restaurant_id}/menu_item_id={menu_item_id}")
    public Response removeMenuItem(@PathVariable(value="restaurant_id") int restaurant_id,
                                   @PathVariable(value="menu_item_id") int menu_item_id)
    {
        return null;
    }

}
