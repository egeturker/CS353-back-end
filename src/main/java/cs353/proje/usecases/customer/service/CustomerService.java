package cs353.proje.usecases.customer.service;

import cs353.proje.usecases.common.dto.*;
import cs353.proje.usecases.customer.dto.Customer;
import cs353.proje.usecases.customer.dto.OrderFromCustomer;
import cs353.proje.usecases.customer.dto.Restaurant;
import cs353.proje.usecases.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Response getAllRestaurants() {
        List<Restaurant> allRestaurants = customerRepository.getAllRestaurants();

        return new Response(true, "No restaurants found to be listed", allRestaurants);
    }
    
    public Response getRestaurantsWithFilter(int customer_id, String open, double minRating, double maxRating) {
        List<Restaurant> selected_restaurants = customerRepository.getRestaurantsWithFilter(customer_id, open, minRating, maxRating);
        if(selected_restaurants.size()>=1)
            return new Response(true, "Success", selected_restaurants);
        else
            return new Response(false, "No restaurants found matching the filters", null);
    }

    public Response getCustomerData(int customerId){
        Customer customerData = customerRepository.getCustomerData(customerId);
        if(customerData != null)
            return new Response(true, "Success", customerData);
        else
            return new Response(false, "Customer not found", null);
    }

    public Response updateCustomerData(int customerId, Customer newCustomerData){
        if(customerRepository.updateCustomerData(customerId,newCustomerData))
            return new Response(true, "Success", null);
        else
            return new Response(false, "Customer not found", null);
    }

    public Response getOldOrders(int customerId){
        List<Order> oldOrders = customerRepository.getOldOrders(customerId);
        if(oldOrders.size() >= 1)
            return new Response(true, "Success", oldOrders);
        else
            return new Response(false, "No orders found", null);
    }

    public Response getCoupons(int customer_id) {
        List<Coupon> coupons = customerRepository.getCoupons(customer_id);
        if(coupons.size() >= 1)
            return new Response(true, "Success", coupons);
        else
            return new Response(false, "No coupons found", null);
    }

    public Response getRestaurantInfo(int restaurant_id) {
        Restaurant restaurant_info = customerRepository.getRestaurantInfo(restaurant_id);
        if(restaurant_info != null)
            return new Response(true, "Success", restaurant_info);
        else
            return new Response(false, "Restaurant not found", null);
    }

    public Response getRestaurantMenu(int restaurant_id) {
        List<MenuItem> menu = customerRepository.getRestaurantMenu(restaurant_id);
        if(menu.size() >= 1)
            return new Response(true, "Success", menu);
        else
            return new Response(false, "No menu found", null);
    }

    public Response getRestaurantMenuByCategory(int restaurant_id) {
        List<CategoryMenu> menu = customerRepository.getRestaurantMenuByCategory(restaurant_id);
        if(menu.size() >= 1)
            return new Response(true, "Success", menu);
        else
            return new Response(false, "No menu found", null);
    }

    public Response getIngredients(int menu_item_id) {
        List<Ingredient> ingredients = customerRepository.getIngredients(menu_item_id);
        if(ingredients.size() >= 1)
            return new Response(true, "Success", ingredients);
        else
            return new Response(false, "No ingredients found", null);
    }

    public Response createNewOrder(OrderFromCustomer order) {
        if(customerRepository.createNewOrder(order))
            return new Response(true, "Success", null);
        else
            return new Response(false, "Order not placed", null);
    }
}
