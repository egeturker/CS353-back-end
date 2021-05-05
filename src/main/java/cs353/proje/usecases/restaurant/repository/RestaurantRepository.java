package cs353.proje.usecases.restaurant.repository;

import cs353.proje.usecases.restaurant.dto.UpdatedRestaurantData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public boolean updateRestaurantData(int restaurantId, UpdatedRestaurantData updatedRestaurantData){

        String sql = "UPDATE restaurant " +
                "SET owner_id = ?, " +
                "restaurant_name = ?, " +
                "address = ?, " +
                "description = ?, " +
                "restaurant_category = ? " +
                "WHERE restaurant_id = ?";

        Object[] params = {updatedRestaurantData.getRestaurantOwnerData().getUserId(), updatedRestaurantData.getRestaurantName(),
            updatedRestaurantData.getAddress(), updatedRestaurantData.getDescription(), updatedRestaurantData.getRestaurantCategory(),
            restaurantId};

        return jdbcTemplate.update(sql, params) == 1;
    }
}
