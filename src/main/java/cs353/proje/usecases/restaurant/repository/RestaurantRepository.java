package cs353.proje.usecases.restaurant.repository;

import cs353.proje.usecases.loginregister.dto.User;
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

    public boolean updateRestaurantOwnerData(UpdatedRestaurantData updatedRestaurantData) {

        String sql = "UPDATE user " +
                "SET name = ?, " +
                "surname = ?, " +
                "email = ?, " +
                "username = ?, " +
                "password = ?, " +
                "telephone = ?, " +
                "image = ? " +
                "WHERE user_id = ?";

        User owner = updatedRestaurantData.getRestaurantOwnerData();

        Object[] params = {owner.getName(), owner.getSurname(), owner.getEmail(), owner.getUsername(), owner.getPassword(),
                owner.getTelephone(), owner.getImage(), owner.getUserId()};

        return jdbcTemplate.update(sql, params) == 1;
    }
}
