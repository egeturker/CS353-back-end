package cs353.proje.usecases.customer.repository;

import cs353.proje.usecases.customer.dto.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomerRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Restaurant> restaurantRowMapper = (rs, rowNum) ->{
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rs.getInt("restaurant_id"));
        restaurant.setOwnerId(rs.getInt("owner_id"));
        restaurant.setRestaurant_name(rs.getString("restaurant_name"));
        restaurant.setRating(rs.getDouble("rating"));
        restaurant.setAddress(rs.getString("address"));
        restaurant.setDescription(rs.getString("description"));
        restaurant.setRestaurant_category(rs.getString("restaurant_category"));
        restaurant.setStatus(rs.getString("status"));

        return restaurant;
    };

    public List<Restaurant> getAllRestaurants(){
        String sql = "SELECT * FROM restaurant";
        return jdbcTemplate.query(sql,restaurantRowMapper);
    }

    public List<Restaurant> getRestaurantsWithFilter(String open, double minRating, double maxRating) {
        String sql = "SELECT * FROM restaurant WHERE status = ? AND rating BETWEEN ? AND ?";
        Object[] params = {open, minRating, maxRating};
        return jdbcTemplate.query(sql, params, restaurantRowMapper);
    }


}
