package cs353.proje.usecases.restaurant.controller;

import cs353.proje.usecases.common.dto.Ingredient;
import cs353.proje.usecases.common.dto.MenuItem;
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

    @GetMapping("/finalizedOrders/restaurant_id={id}")
    public Response getFinalizedOrders(@PathVariable(value="id") int restaurant_id)
    {
        return restaurantService.getFinalizedOrders(restaurant_id);
    }

    @GetMapping("/activeOrders/restaurant_id={id}")
    public Response getActiveOrders(@PathVariable(value="id") int restaurant_id)
    {
        return restaurantService.getActiveOrders(restaurant_id);
    }

    @GetMapping("/getRestaurantId/owner_id={id}")
    public Response getRestaurantId(@PathVariable(value="id") int owner_id)
    {
        return restaurantService.getRestaurantId(owner_id);
    }

    //Restaurant states it is open now, set its status to 1
    @PostMapping("/open/restaurant_id={id}")
    public Response open(@PathVariable(value="id") int restaurant_id)
    {
        return restaurantService.open(restaurant_id);
    }

    //Restaurant states it is closed now, set its status to 0
    @PostMapping("/close/restaurant_id={id}")
    public Response close(@PathVariable(value="id") int restaurant_id)
    {
        return restaurantService.close(restaurant_id);
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

    //Restaurant adds a new MenuItem to its menu. It will have no ingredients initially.
    //The RequestBody MenuItem includes all the information except the menu_item_id
    @PostMapping("/addMenuItem/restaurant_id={restaurant_id}")
    public Response addMenuItem(@PathVariable(value="restaurant_id") int restaurant_id,
                                @RequestBody MenuItem menuItem)
    {
        return restaurantService.addMenuItem(restaurant_id, menuItem);
    }

    //Restaurant updates a new MenuItem in its menu.
    //No changes to the ingredients in this method.
    @PostMapping("/updateMenuItem/restaurant_id={restaurant_id}")
    public Response updateMenuItem(@PathVariable(value="restaurant_id") int restaurant_id,
                                @RequestBody MenuItem menuItem)
    {
        return restaurantService.updateMenuItem(restaurant_id, menuItem);
    }

    //Talk about this in discord
    //THIS ONE CAN CAUSE PROBLEMS WHEN LOOKING AT THE OLD ORDERS, SO DON'T IMPLEMENT THIS FOR NOW
    //Restaurant removes one of its menu items, don't forget to check for the restaurant
    //and don't forget to remove all corresponding ingredients from the ingredient_list
    @PostMapping("/removeMenuItem/restaurant_id={restaurant_id}/menu_item_id={menu_item_id}")
    public Response removeMenuItem(@PathVariable(value="restaurant_id") int restaurant_id,
                                   @PathVariable(value="menu_item_id") int menu_item_id)
    {
        return restaurantService.removeMenuItem(restaurant_id, menu_item_id);
    }

    //For the given menu_item_id, its ingredients are updated with the new given list of ingredients.
    //There are three cases to consider:
    //1. If the ingredient_id=-1 for an ingredient in the list, it is a newly added ingredient, insert it into the ingredient table
    //2. If ingredient_id != -1, the information about this ingredient is being updated, so just update the ingredient table
    //3. If this menuItem had an ingredient that was not given in this list, then it is being deleted. We can talk about this case in discord
    @PostMapping("/updateIngredients/restaurant_id={restaurant_id}/menu_item_id={menu_item_id}")
    public Response updateIngredients(@PathVariable(value="restaurant_id") int restaurant_id,
                                      @PathVariable(value="menu_item_id") int menu_item_id,
                                      @RequestBody List<Ingredient> ingredients)
    {
        return restaurantService.updateIngredients(restaurant_id, menu_item_id, ingredients);
    }

    //Simply, status of the order will just be changed to "Preparing Food"
    @PostMapping("/statusUpdate/preparing/order_id={order_id}")
    public Response statusUpdatePreparing(@PathVariable(value="order_id") int order_id)
    {
        return restaurantService.statusUpdatePreparing(order_id);
    }

    //Status of the order will be changed to "Waiting Courier"
    //An available courier will be assigned to this order.
    //What if no available courier?

    //Trigger for assigning random courier.
    @PostMapping("/statusUpdate/finalize/order_id={order_id}")
    public Response statusUpdateFinalize(@PathVariable(value="order_id") int order_id)
    {
        return restaurantService.statusUpdateFinalize(order_id);
    }

}
