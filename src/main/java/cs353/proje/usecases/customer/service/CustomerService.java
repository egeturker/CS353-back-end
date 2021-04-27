package cs353.proje.usecases.customer.service;

import cs353.proje.usecases.common.dto.Coupon;
import cs353.proje.usecases.common.dto.Order;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.customer.dto.Customer;
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
        List<Restaurant> selectedRestaurants = customerRepository.getRestaurantsWithFilter(customer_id, open, minRating, maxRating);
        if(selectedRestaurants.size()>=1)
            return new Response(true, "Success", selectedRestaurants);
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
}
