package cs353.proje.usecases.customer.service;

import cs353.proje.usecases.common.dto.Response;
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

        return new Response(true, "Success", allRestaurants);
    }
    
    public Response getRestaurantsWithFilter(String open, double minRating, double maxRating) {
        List<Restaurant> selectedRestaurants = customerRepository.getRestaurantsWithFilter(open, minRating, maxRating);
        if(selectedRestaurants.size()>=1)
            return new Response(true, "Success", selectedRestaurants);
        else
            return new Response(false, "Failure", selectedRestaurants);
    }
    
}
