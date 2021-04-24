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
}
