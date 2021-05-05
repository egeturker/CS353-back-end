package cs353.proje.usecases.customer.service;

import cs353.proje.usecases.common.dto.*;
import cs353.proje.usecases.customer.dto.Customer;
import cs353.proje.usecases.customer.dto.OrderDetails;
import cs353.proje.usecases.customer.dto.OrderFromCustomer;
import cs353.proje.usecases.customer.dto.Restaurant;
import cs353.proje.usecases.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerService
{

    @Autowired
    CustomerRepository customerRepository;

    public Response getAllRestaurants()
    {
        List<Restaurant> allRestaurants = customerRepository.getAllRestaurants();

        return new Response(true, "No restaurants found to be listed", allRestaurants);
    }

    public Response getRestaurants(int customerId)
    {
        List<Restaurant> favoriteRestaurants = customerRepository.getFavoriteRestaurants(customerId);
        List<Restaurant> nonFavoriteRestaurants = customerRepository.getNonFavoriteRestaurants(customerId);
        List<Restaurant> restaurants =  Stream.concat(favoriteRestaurants.stream(), nonFavoriteRestaurants.stream()).collect(Collectors.toList());
        return new Response(true, "Restaurants listed", restaurants);
    }
    
    public Response getRestaurantsWithFilter(int customerId, boolean open, double minRating, double maxRating)
    {
        List<Restaurant> favoriteRestaurantsWithFilter = customerRepository.getFavoriteRestaurantsWithFilter(customerId, open, minRating, maxRating);
        List<Restaurant> nonFavoriteRestaurantsWithFilter = customerRepository.getNonFavoriteRestaurantsWithFilter(customerId, open, minRating, maxRating);

        if (favoriteRestaurantsWithFilter.size() < 1)
            if (nonFavoriteRestaurantsWithFilter.size() < 1)
                return new Response(true, "No restaurants found matching the filter", Collections.emptyList());
            else
                return new Response(true, "Success", nonFavoriteRestaurantsWithFilter);

        if (nonFavoriteRestaurantsWithFilter.size() < 1)
            return new Response(true, "Success", favoriteRestaurantsWithFilter);

        List<Restaurant> restaurantsWithFilter =  Stream.concat(favoriteRestaurantsWithFilter.stream(),
                nonFavoriteRestaurantsWithFilter.stream()).collect(Collectors.toList());
        if(restaurantsWithFilter.size()>=1)
            return new Response(true, "Success", restaurantsWithFilter);
        else
            return new Response(true, "No restaurants found matching the filters", Collections.emptyList());
    }

    public Response getRestaurantsWithSearch(int customerId, String searchKey)
    {
        List<Restaurant> favoriteRestaurantsWithSearch = customerRepository.getFavoriteRestaurantsWithSearchKey(customerId, searchKey);
        List<Restaurant> nonFavoriteRestaurantsWithSearch = customerRepository.getNonFavoriteRestaurantsWithSearchKey(customerId, searchKey);

        if (favoriteRestaurantsWithSearch.size() < 1)
            if (nonFavoriteRestaurantsWithSearch.size() < 1)
                return new Response(true, "No restaurants found matching the searchkey", Collections.emptyList());
            else
                return new Response(true, "Success", nonFavoriteRestaurantsWithSearch);

        if (nonFavoriteRestaurantsWithSearch.size() < 1)
            return new Response(true, "Success", favoriteRestaurantsWithSearch);

        List<Restaurant> restaurantsWithSearch =  Stream.concat(favoriteRestaurantsWithSearch.stream(),
                nonFavoriteRestaurantsWithSearch.stream()).collect(Collectors.toList());
        if(restaurantsWithSearch.size()>=1)
            return new Response(true, "Success", restaurantsWithSearch);
        else
            return new Response(true, "No restaurants found matching the filters", Collections.emptyList());
    }

    public Response getCustomerData(int customerId)
    {
        Customer customerData = customerRepository.getCustomerData(customerId);
        if(customerData != null)
            return new Response(true, "Success", customerData);
        else
            return new Response(false, "Customer not found", null);
    }

    public Response updateCustomerData(int customerId, Customer newCustomerData)
    {
        if(customerRepository.updateCustomerData(customerId,newCustomerData))
            return new Response(true, "Success", null);
        else
            return new Response(false, "Customer not found", null);
    }

    public Response getOldOrders(int customerId)
    {
        List<Order> oldOrders = customerRepository.getOldOrders(customerId);
        if(oldOrders.size() >= 1)
            return new Response(true, "Success", oldOrders);
        else
            return new Response(true, "No orders found", Collections.emptyList());
    }

    public Response getRestaurantInfo(int restaurant_id)
    {
        Restaurant restaurant_info = customerRepository.getRestaurantInfo(restaurant_id);
        if(restaurant_info != null)
            return new Response(true, "Success", restaurant_info);
        else
            return new Response(false, "Restaurant not found", null);
    }

    public Response getRestaurantMenu(int restaurant_id)
    {
        List<MenuItem> menu = customerRepository.getRestaurantMenu(restaurant_id);
        if(menu.size() >= 1)
            return new Response(true, "Success", menu);
        else
            return new Response(false, "No menu found", null);
    }

    public Response getRestaurantMenuByCategory(int restaurant_id)
    {
        List<CategoryMenu> menu = customerRepository.getRestaurantMenuByCategory(restaurant_id);
        if(menu.size() >= 1)
            return new Response(true, "Success", menu);
        else
            return new Response(false, "No menu found", null);
    }

    public Response getIngredients(int menu_item_id)
    {
        List<Ingredient> ingredients = customerRepository.getIngredients(menu_item_id);
        if(ingredients.size() >= 1)
            return new Response(true, "Success", ingredients);
        else
            return new Response(true, "No ingredients found", Collections.emptyList());
    }

    public Response createNewOrder(OrderFromCustomer order)
    {
        if(customerRepository.createNewOrder(order)) {
            customerRepository.updateRaffleParticipation(order);
            return new Response(true, "Success", null);
        }
        else {
            return new Response(false, "Order not placed", null);
        }
    }

    public Response getOrderDetails(int order_id)
    {
        OrderDetails order_details = customerRepository.getOrderDetails(order_id);
        if(order_details != null)
            return new Response(true, "Success", order_details);
        else
            return new Response(false, "Order details not found", null);
    }

    public Response addFavorite(int customer_id, int restaurant_id)
    {
        if(customerRepository.addFavorite(customer_id, restaurant_id))
            return new Response(true, "Success", null);
        else
            return new Response(false, "Order not placed", null);
    }

    public Response removeFavorite(int customerId, int restaurantId)
    {
        if(customerRepository.removeFavorite(customerId, restaurantId))
            return new Response(true,"Success", null);
        else
            return new Response(false, "Restaurant is not in favorites", null);
    }


    public Response getFavorite(int customer_id)
    {
        List<Favorite> favorite = customerRepository.getFavorite(customer_id);
        if(favorite.size() >= 1)
            return new Response(true, "Success", favorite);
        else
            return new Response(false, "No favorite restaurant found", null);
    }
}
