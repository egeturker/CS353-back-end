package cs353.proje.usecases.customer.repository;

import cs353.proje.usecases.common.dto.CategoryMenu;
import cs353.proje.usecases.common.dto.Coupon;
import cs353.proje.usecases.common.dto.MenuItem;
import cs353.proje.usecases.common.dto.Order;
import cs353.proje.usecases.customer.dto.Customer;
import cs353.proje.usecases.customer.dto.Restaurant;
import cs353.proje.usecases.loginregister.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
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

    RowMapper<MenuItem> menuRowMapper = (rs, rowNum) ->{
        MenuItem menu = new MenuItem();
        menu.setRestaurantId(rs.getInt("restaurant_id"));
        menu.setMenuItemId(rs.getInt("menu_item_id"));
        menu.setName(rs.getString("name"));
        menu.setImageLink(rs.getString("image"));
        menu.setDescription(rs.getString("description"));
        menu.setBasePrice(rs.getDouble("base_price"));
        menu.setFoodCategory(rs.getString("food_category"));

        return menu;
    };

    RowMapper<Customer> customerRowMapper = (rs, rowNum) ->{
        Customer customer = new Customer();
        customer.setUserId(rs.getInt("customer_id"));
        customer.setEmail(rs.getString("email"));
        customer.setUsername(rs.getString("username"));
        customer.setPassword(rs.getString("password"));
        customer.setTelephone(rs.getString("telephone"));
        customer.setImage(rs.getString("image"));
        customer.setRegistrationDate(rs.getDate("registration_date"));
        customer.setName(rs.getString("name"));
        customer.setSurname(rs.getString("surname"));
        customer.setUserType(rs.getString("user_type"));
        customer.setAddress(rs.getString("address"));
        customer.setRegion_id(rs.getInt("region_id"));

        return customer;
    };

    RowMapper<Order> orderRowMapper = (rs, rowNum) ->{
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setRestaurantId(rs.getInt("restaurant_id"));
        order.setCustomerId(rs.getInt("customer_id"));
        order.setPrice(rs.getDouble("price"));
        order.setOrderTime(rs.getTimestamp("order_time"));
        order.setDeliveryTime(rs.getTimestamp("delivery_time"));
        order.setStatus(rs.getString("status"));
        order.setOptionalDeliveryTime(rs.getTimestamp("optional_delivery_time"));
        order.setRestaurantName(rs.getString("restaurant_name"));

        return order;
    };

    RowMapper<Coupon> couponRowMapper = (rs, rowNum) ->{
        Coupon coupon = new Coupon();
        coupon.setCouponId(rs.getString("coupon_id"));
        coupon.setDiscountAmount(rs.getInt("discount_amount"));
        coupon.setCustomerId(rs.getInt("customer_id"));
        coupon.setRestaurantId(rs.getInt("restaurant_id"));
        coupon.setUsed(false);
        coupon.setRestaurantName(getRestaurantInfo(coupon.getRestaurantId()).getRestaurant_name());

        return coupon;
    };
    public List<Restaurant> getAllRestaurants(){
        String sql = "SELECT * FROM restaurant";
        return jdbcTemplate.query(sql, restaurantRowMapper);
    }

    public List<Restaurant> getRestaurantsWithFilter(int customer_id, String open, double minRating, double maxRating) {
        String sql = "SELECT * FROM restaurant INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                     "INNER JOIN region ON region.region_id = serves_at.region_id " +
                     "INNER JOIN customer ON region.region_id = ? " +
                     "WHERE status = ? AND rating BETWEEN ? AND ?";
        Object[] params = {customer_id,open, minRating, maxRating};
        return jdbcTemplate.query(sql, params, restaurantRowMapper);
    }

    public Customer getCustomerData(int customerId){
        String sql = "SELECT * FROM customer " +
                "INNER JOIN user ON user_id = customer_id " +
                "WHERE customer_id = ?";
        Object[] params = {customerId};
        List<Customer> customerInfo =  jdbcTemplate.query(sql, params, customerRowMapper);

        if(customerInfo.size() == 1)
            return customerInfo.get(0);
        else
            return null;
    }

    public boolean updateCustomerData(int userId, Customer customerData){
        String sql = "UPDATE customer " +
                "INNER JOIN user ON user.user_id = customer.customer_id " +
                "SET email = ?, " +
                "username = ?,  " +
                "password = ?, " +
                "telephone = ?, " +
                "image = ?, " +
                "registration_date = ?, " +
                "name = ?, " +
                "surname = ?, " +
                "user_type = ?, " +
                "address = ?, " +
                "region_id = ? " +
                "WHERE customer_id = ?;";

        Object[] params = {customerData.getEmail(), customerData.getUsername(), customerData.getPassword(),
            customerData.getTelephone(), customerData.getImage(), customerData.getRegistrationDate(),
            customerData.getName(), customerData.getSurname(), customerData.getUserType(),
            customerData.getAddress(), customerData.getRegion_id(), userId };

        return jdbcTemplate.update(sql,params) == 2;
    }

    public List<Order> getOldOrders( int customerId){
        String sql = "SELECT order_id, restaurant.restaurant_id, customer_id, price, order_time, " +
                "delivery_time , restaurant_name, order.status, optional_delivery_time FROM `order` " +
                "INNER JOIN restaurant ON restaurant.restaurant_id = order.restaurant_id " +
                "WHERE customer_id = ? " +
                "ORDER BY order_time DESC;";
        Object[] params = {customerId};

        return jdbcTemplate.query(sql, params, orderRowMapper);
    }


    public List<Coupon> getCoupons(int customer_id) {
        String sql = "SELECT * " +
                     "FROM coupon INNER JOIN restaurant ON coupon.restaurant_id = restaurant.restaurant_id " +
                     "WHERE customer_id = ? AND used <> true";
        Object[] params = {customer_id};
        return jdbcTemplate.query(sql, params, couponRowMapper);
    }

    public Restaurant getRestaurantInfo(int restaurant_id) {
        String sql = "SELECT * " +
                     "FROM restaurant " +
                     "WHERE restaurant_id = ? ";
        Object[] params = {restaurant_id};
        List<Restaurant> restaurant = jdbcTemplate.query(sql, params, restaurantRowMapper);

        if(restaurant.size() == 1)
            return restaurant.get(0);
        else
            return null;
    }

    public List<MenuItem> getRestaurantMenu(int restaurant_id) {
        String sql = "SELECT * FROM menu_item INNER JOIN restaurant ON restaurant.restaurant_id = menu_item.restaurant_id " +
                     "WHERE menu_item.restaurant_id = ? " +
                     "ORDER BY food_category";
        Object[] params = {restaurant_id};
        return jdbcTemplate.query(sql, params, menuRowMapper);
    }

    public List<MenuItem> getRestaurantMenu(int restaurant_id, String food_category) {
        String sql = "SELECT * FROM menu_item INNER JOIN restaurant ON restaurant.restaurant_id = menu_item.restaurant_id " +
                     "WHERE menu_item.restaurant_id = ? AND menu_item.food_category = ? ";
        Object[] params = {restaurant_id, food_category};
        return jdbcTemplate.query(sql, params, menuRowMapper);
    }

    public List<CategoryMenu> getRestaurantMenuByCategory(int restaurant_id){
        String sql_category = "SELECT * FROM menu_item INNER JOIN restaurant ON restaurant.restaurant_id = menu_item.restaurant_id " +
                              "WHERE menu_item.restaurant_id = ? " +
                              "GROUP BY food_category";
        Object[] params = {restaurant_id};
        List<MenuItem> menu = jdbcTemplate.query(sql_category, params, menuRowMapper);

        List<CategoryMenu> category_menu = new ArrayList<>();
        CategoryMenu category_item = new CategoryMenu();
        String food_category;

       for(int i = 0; i < menu.size(); i++) {
            food_category = menu.get(i).getFoodCategory();
            category_item.setCategoryMenuItems(getRestaurantMenu(restaurant_id, food_category));
            category_item.setCategory(food_category);
            category_menu.add(i,category_item);

            category_item = new CategoryMenu();
        }

        return category_menu;
    }
}
