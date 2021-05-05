package cs353.proje.usecases.restaurant.service;

import cs353.proje.usecases.common.dto.MenuItem;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.restaurant.dto.AllRestaurantData;
import cs353.proje.usecases.restaurant.dto.UpdatedRestaurantData;
import cs353.proje.usecases.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Service
public class RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    public Response updateRestaurantData(int restaurantId, UpdatedRestaurantData updatedRestaurantData){
        if (restaurantRepository.updateRestaurantData(restaurantId, updatedRestaurantData)
        && restaurantRepository.updateRestaurantOwnerData(updatedRestaurantData))
            return new Response(true, "Restaurant data updated successfully", null);
        else
            return new Response(false, "Unsuccessful", null);
    }

    public Response getRestaurantData(int restaurantId){
        List<AllRestaurantData> allRestaurantData = restaurantRepository.getRestaurantData(restaurantId);
        if(allRestaurantData.size() == 1)
            return new Response(true, "Success", null);
        else
            return new Response(true, "Restaurant not found", Collections.emptyList());
    }

    public Response getOldOrders(int restaurantId){
        return null;
    }

    public Response open(int restaurantId){
        return null;
    }

    public Response close(int restaurantId){
        return null;
    }

    public Response addServedRegion(int restaurantId, int regionId){
        return null;
    }

    public Response removeServedRegion(int restaurantId, int regionId){
        return null;
    }

    public Response addMenuItem(int restaurantId, MenuItem menuItem){
        return null;
    }

    public Response removeMenuItem(int restaurantId, int menuItemId){
        return null;
    }
}
