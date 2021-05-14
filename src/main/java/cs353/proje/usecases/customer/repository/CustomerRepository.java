package cs353.proje.usecases.customer.repository;

import cs353.proje.usecases.common.dto.*;
import cs353.proje.usecases.customer.dto.*;
import cs353.proje.usecases.common.repository.RaffleCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class CustomerRepository
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Lazy
    RaffleCouponRepository raffleCouponRepository;

    RowMapper<Integer> integerRowMapper = (rs, rowNum) -> rs.getInt(1);

    RowMapper<Restaurant> restaurantRowMapper = (rs, rowNum) ->{
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rs.getInt("restaurant_id"));
        restaurant.setOwnerId(rs.getInt("owner_id"));
        restaurant.setRestaurantName(rs.getString("restaurant_name"));
        restaurant.setRating(rs.getDouble("rating"));
        restaurant.setAddress(rs.getString("address"));
        restaurant.setDescription(rs.getString("description"));
        restaurant.setRestaurantCategory(rs.getString("restaurant_category"));
        restaurant.setStatus(rs.getString("status"));
        restaurant.setImage(rs.getString("image"));

        return restaurant;
    };

    RowMapper<MenuItem> menuItemRowMapper = (rs, rowNum) ->{
        MenuItem menu_item = new MenuItem();
        menu_item.setRestaurantId(rs.getInt("restaurant_id"));
        menu_item.setMenuItemId(rs.getInt("menu_item_id"));
        menu_item.setName(rs.getString("name"));
        menu_item.setImageLink(rs.getString("image"));
        menu_item.setDescription(rs.getString("description"));
        menu_item.setBasePrice(rs.getDouble("base_price"));
        menu_item.setFoodCategory(rs.getString("food_category"));

        return menu_item;
    };

    RowMapper<SelectedMenuItem> selectedMenuItemRowMapper = (rs, rowNum) ->{
        SelectedMenuItem selected_menu_item = new SelectedMenuItem();
        MenuItem menu_item = new MenuItem();
        menu_item.setRestaurantId(rs.getInt("restaurant_id"));
        menu_item.setMenuItemId(rs.getInt("menu_item_id"));
        menu_item.setName(rs.getString("name"));
        menu_item.setImageLink(rs.getString("image"));
        menu_item.setDescription(rs.getString("description"));
        menu_item.setBasePrice(rs.getDouble("base_price"));
        menu_item.setFoodCategory(rs.getString("food_category"));

        selected_menu_item.setMenuItem(menu_item);
        selected_menu_item.setQuantity(rs.getInt("quantity"));

        return selected_menu_item;
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
        order.setPaymentMethod(rs.getString("payment_method"));
        order.setCoupon(rs.getString("coupon"));
        order.setRestaurantName(getRestaurantInfo(order.getRestaurantId()).getRestaurantName());
        //no such column in order
        return order;
    };

    RowMapper<Ingredient> ingredientRowMapper = (rs, rowNum) ->{
        Ingredient ingredient = new Ingredient();
        ingredient.setMenuItemId(rs.getInt("menu_item_id"));
        ingredient.setIngredientId(rs.getInt("ingredient_id"));
        ingredient.setIngredientName(rs.getString("ingredient_name"));
        ingredient.setDefaultIngredient(rs.getBoolean("default_ingredient"));
        ingredient.setAdditionalPrice(rs.getDouble("additional_price"));

        return ingredient;
    };

    RowMapper<Favorite> favoriteRowMapper = (rs, rowNum) ->{
        Favorite favorite = new Favorite();
        favorite.setCustomerId(rs.getInt("customer_id"));
        favorite.setRestaurantId(rs.getInt("restaurant_id"));

        return favorite;
    };

    public List<Restaurant> getAllRestaurants()
    {
        String sql = "SELECT * FROM restaurant";
        return jdbcTemplate.query(sql, restaurantRowMapper);
    }

    public List<Restaurant> getFavoriteRestaurants(int customerId)
    {
        String sql = "SELECT restaurant.restaurant_id, owner_id, restaurant_name,  restaurant.rating, restaurant.address, " +
                " description, restaurant_category, restaurant.status, image " +
                "FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN region ON region.region_id = serves_at.region_id " +
                "INNER JOIN customer ON customer.region_id = serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = region.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE restaurant.restaurant_id IN " +
                "(SELECT DISTINCT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN customer ON customer.region_id= serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = serves_at.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE (customer.customer_id = ?) " +
                "AND restaurant.restaurant_id IN " +
                "(SELECT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN favorite ON favorite.restaurant_id = restaurant.restaurant_id " +
                "WHERE favorite.customer_id = ?) " +
                ") GROUP BY restaurant_id ";
        Object[] params = {customerId, customerId};
        return jdbcTemplate.query(sql, params, restaurantRowMapper);
    }

    public List<Restaurant> getNonFavoriteRestaurants(int customerId)
    {
        String sql = "SELECT restaurant.restaurant_id, owner_id, restaurant_name,  restaurant.rating, restaurant.address, " +
                " description, restaurant_category, restaurant.status, image " +
                "FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN region ON region.region_id = serves_at.region_id " +
                "INNER JOIN customer ON customer.region_id = serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = region.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE restaurant.restaurant_id IN " +
                "(SELECT DISTINCT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN customer ON customer.region_id= serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = serves_at.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE (customer.customer_id = ?) " +
                "AND restaurant.restaurant_id NOT IN " +
                "(SELECT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN favorite ON favorite.restaurant_id = restaurant.restaurant_id " +
                "WHERE favorite.customer_id = ?) " +
                ") GROUP BY restaurant_id ";
        Object[] params = {customerId, customerId};
        return jdbcTemplate.query(sql, params, restaurantRowMapper);
    }

    public List<Restaurant> getFavoriteRestaurantsWithFilter(int customer_id, boolean open,
                                                             double minRating, double maxRating)
    {
        String sql = "SELECT restaurant.restaurant_id, owner_id, restaurant_name,  restaurant.rating, restaurant.address, " +
                " description, restaurant_category, restaurant.status, image " +
                "FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN region ON region.region_id = serves_at.region_id " +
                "INNER JOIN customer ON customer.region_id = serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = region.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE restaurant.restaurant_id IN " +
                "(SELECT DISTINCT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN customer ON customer.region_id= serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = serves_at.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE (customer.customer_id = ?) " +
                "AND (courier.status = 1) " +
                "AND (restaurant.status IN (?,?)) " +
                "AND restaurant.rating BETWEEN ? AND ? " +
                "AND restaurant.restaurant_id IN " +
                "(SELECT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN favorite ON favorite.restaurant_id = restaurant.restaurant_id " +
                "WHERE favorite.customer_id = ?) " +
                ") GROUP BY restaurant_id ";

        Object[] params = {customer_id, 1, 1, minRating, maxRating, customer_id};
        Object[] params2 = {customer_id, 1, 0, minRating, maxRating, customer_id};
        if (open)
            return jdbcTemplate.query(sql, params, restaurantRowMapper);
        else
            return jdbcTemplate.query(sql, params2, restaurantRowMapper);

    }

    public List<Restaurant> getNonFavoriteRestaurantsWithFilter(int customer_id, boolean open,
                                                             double minRating, double maxRating)
    {
        String sql = "SELECT restaurant.restaurant_id, owner_id, restaurant_name,  restaurant.rating, restaurant.address, " +
                " description, restaurant_category, restaurant.status, image " +
                " FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN region ON region.region_id = serves_at.region_id " +
                "INNER JOIN customer ON customer.region_id = serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = region.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE restaurant.restaurant_id IN " +
                "(SELECT DISTINCT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN customer ON customer.region_id= serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = serves_at.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE (customer.customer_id = ?) " +
                "AND (courier.status = 1) " +
                "AND (restaurant.status IN (?,?)) " +
                "AND restaurant.rating BETWEEN ? AND ? " +
                "AND restaurant.restaurant_id NOT IN " +
                "(SELECT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN favorite ON favorite.restaurant_id = restaurant.restaurant_id " +
                "WHERE favorite.customer_id = ?) " +
                ") GROUP BY restaurant_id ";

        Object[] params = {customer_id, 1, 1, minRating, maxRating, customer_id};
        Object[] params2 = {customer_id, 1, 0, minRating, maxRating, customer_id};
        if (open)
            return jdbcTemplate.query(sql, params, restaurantRowMapper);
        else
            return jdbcTemplate.query(sql, params2, restaurantRowMapper);

    }

    public List<Restaurant> getFavoriteRestaurantsWithSearchKey(int customerId, String searchKey)
    {
        searchKey = "%" + searchKey + "%";
        String sql = "SELECT restaurant.restaurant_id, owner_id, restaurant_name,  restaurant.rating, restaurant.address, \n" +
                " description, restaurant_category, restaurant.status, image " +
                "FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN region ON region.region_id = serves_at.region_id " +
                "INNER JOIN customer ON customer.region_id = serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = region.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE restaurant.restaurant_id IN " +
                "(SELECT DISTINCT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN customer ON customer.region_id= serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = serves_at.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "INNER JOIN undeleted_menu_item ON undeleted_menu_item.restaurant_id = restaurant.restaurant_id " +
                "WHERE (customer.customer_id = ?) " +
                "AND (courier.status = 1)" +
                "AND ((restaurant_name LIKE ?) OR (undeleted_menu_item.name LIKE ?) OR (restaurant_category LIKE ?))" +
                "AND restaurant.restaurant_id IN " +
                "(SELECT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN favorite ON favorite.restaurant_id = restaurant.restaurant_id " +
                "WHERE favorite.customer_id = ?) " +
                ") GROUP BY restaurant_id ";
        Object[] params = {customerId, searchKey, searchKey, searchKey, customerId};
        return jdbcTemplate.query(sql, params, restaurantRowMapper);
    }

    public List<Restaurant> getNonFavoriteRestaurantsWithSearchKey(int customerId, String searchKey)
    {
        searchKey = "%" + searchKey + "%";
        String sql = "SELECT restaurant.restaurant_id, owner_id, restaurant_name,  restaurant.rating, restaurant.address, \n" +
                " description, restaurant_category, restaurant.status, image " +
                "FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN region ON region.region_id = serves_at.region_id " +
                "INNER JOIN customer ON customer.region_id = serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = region.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "WHERE restaurant.restaurant_id IN " +
                "(SELECT DISTINCT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN serves_at ON serves_at.restaurant_id = restaurant.restaurant_id " +
                "INNER JOIN customer ON customer.region_id= serves_at.region_id " +
                "INNER JOIN operates_in ON operates_in.region_id = serves_at.region_id " +
                "INNER JOIN courier ON courier.courier_id = operates_in.courier_id " +
                "INNER JOIN undeleted_menu_item ON undeleted_menu_item.restaurant_id = restaurant.restaurant_id " +
                "WHERE (customer.customer_id = ?) " +
                "AND (courier.status = 1)" +
                "AND ((restaurant_name LIKE ?) OR (undeleted_menu_item.name LIKE ?) OR (restaurant_category LIKE ?))" +
                "AND restaurant.restaurant_id NOT IN " +
                "(SELECT restaurant.restaurant_id FROM restaurant " +
                "INNER JOIN favorite ON favorite.restaurant_id = restaurant.restaurant_id " +
                "WHERE favorite.customer_id = ?) " +
                ") GROUP BY restaurant_id ";
        Object[] params = {customerId, searchKey, searchKey, searchKey, customerId};
        return jdbcTemplate.query(sql, params, restaurantRowMapper);

    }

    public Customer getCustomerData(int customerId)
    {
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

    public boolean updateCustomerData(int userId, Customer customerData)
    {
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

    public List<Order> getOldOrders( int customerId)
    {
        String sql = "SELECT order_id, restaurant.restaurant_id, customer_id, price, order_time, " +
                "delivery_time , payment_method, coupon, restaurant_name, order.status, optional_delivery_time FROM `order` " +
                "INNER JOIN restaurant ON restaurant.restaurant_id = order.restaurant_id " +
                "WHERE customer_id = ? " +
                "ORDER BY order_time DESC;";
        Object[] params = {customerId};

        return jdbcTemplate.query(sql, params, orderRowMapper);
    }

    public Restaurant getRestaurantInfo(int restaurant_id)
    {
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

    public List<MenuItem> getRestaurantMenu(int restaurant_id)
    {
        String sql = "SELECT * FROM undeleted_menu_item INNER JOIN restaurant ON restaurant.restaurant_id = undeleted_menu_item.restaurant_id " +
                     "WHERE undeleted_menu_item.restaurant_id = ? " +
                     "ORDER BY food_category";
        Object[] params = {restaurant_id};
        return jdbcTemplate.query(sql, params, menuItemRowMapper);
    }

    public List<MenuItem> getRestaurantMenu(int restaurant_id, String food_category)
    {
        String sql = "SELECT * FROM undeleted_menu_item INNER JOIN restaurant ON restaurant.restaurant_id = undeleted_menu_item.restaurant_id " +
                     "WHERE undeleted_menu_item.restaurant_id = ? AND undeleted_menu_item.food_category = ? ";
        Object[] params = {restaurant_id, food_category};
        return jdbcTemplate.query(sql, params, menuItemRowMapper);
    }

    public List<CategoryMenu> getRestaurantMenuByCategory(int restaurant_id)
    {
        String sql_category = "SELECT * FROM undeleted_menu_item INNER JOIN restaurant ON restaurant.restaurant_id = undeleted_menu_item.restaurant_id " +
                              "WHERE undeleted_menu_item.restaurant_id = ? " +
                              "GROUP BY food_category";
        Object[] params = {restaurant_id};
        List<MenuItem> menu = jdbcTemplate.query(sql_category, params, menuItemRowMapper);

        List<CategoryMenu> category_menu = new ArrayList<>();
        CategoryMenu category_item = new CategoryMenu();
        String food_category;

       for (int i = 0; i < menu.size(); i++) {
            food_category = menu.get(i).getFoodCategory();
            category_item.setCategoryMenuItems(getRestaurantMenu(restaurant_id, food_category));
            category_item.setCategory(food_category);
            category_menu.add(i,category_item);

            category_item = new CategoryMenu();
        }

        return category_menu;
    }

    public List<Ingredient> getIngredients(int menu_item_id)
    {
        String sql = "SELECT * FROM undeleted_ingredient " +
                     "WHERE menu_item_id = ?";
        Object[] params = {menu_item_id};
        return jdbcTemplate.query(sql, params, ingredientRowMapper);
    }


    public boolean createNewOrder(OrderFromCustomer order)
    {
        //Checks if the coupon is valid
        if(order.getCoupon() != null) {
            List<Coupon> coupons = raffleCouponRepository.checkCoupon(order.getRestaurantId(), order.getCoupon());
            if(coupons.size() > 0 ) {
                String sql_coupon= "UPDATE coupon SET used = ? WHERE coupon_id = ? ";
                Object[] params_coupon = {1, coupons.get(0).getCouponId()};
                jdbcTemplate.update(sql_coupon, params_coupon);
            }
            else {
                return false;
            }
        }

        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        String sql_order = "INSERT INTO `order`(restaurant_id, customer_id, price, order_time, status, " +
                "optional_delivery_time, payment_method, coupon) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
        Object[] params_order = {order.getRestaurantId(), order.getCustomerId(), order.getPrice(), timestamp,
                "Order Taken", order.getOptionalDeliveryTime(), order.getPaymentMethod(), order.getCoupon()};
        int result_order = jdbcTemplate.update(sql_order, params_order);

        String sql_order_id = "SELECT LAST_INSERT_ID()";
        Object[] params_order_id = {};
        int order_id = jdbcTemplate.queryForObject(sql_order_id, integerRowMapper);

        if(result_order == 1 && order_id > 0) {
            List<SelectedMenuItemFromCustomer> selected_menu_items = order.getSelectedMenuItems();
            int size = selected_menu_items.size();
            boolean result = true;
            for(int i = 0; i < size && result; i++){
                result = addSelectedMenuItems(selected_menu_items.get(i), order_id);
            }
            return result;
        }
        else {
            return false;
        }
    }

    private boolean addSelectedMenuItems(SelectedMenuItemFromCustomer selected_menu_items, int order_id)
    {
        String sql_menu_item = "INSERT INTO selected_menu_item (order_id, menu_item_id, quantity) " +
                "VALUES (?, ?, ?) ";
        Object[] params = {order_id, selected_menu_items.getMenuItemId(), selected_menu_items.getQuantity()};
        jdbcTemplate.update(sql_menu_item, params);

        List<Integer> selected_ingredient = selected_menu_items.getSelectedIngredients();
        int ingredient_list_size = selected_menu_items.getSelectedIngredients().size();
        int menu_item_id = selected_menu_items.getMenuItemId();
        boolean result = true;
        for(int i = 0; i <ingredient_list_size && result; i++){
            result = addSelectedIngredients(order_id, menu_item_id, selected_ingredient.get(i));
        }
        return result;
    }

    private boolean addSelectedIngredients(int order_id, int menu_item_id, Integer ingredient_id)
    {
        String sql_ingredient = "INSERT INTO selected_ingredient (order_id, menu_item_id, ingredient_id) " +
                "VALUES (?, ?, ?) ";
        Object[] params = {order_id, menu_item_id, ingredient_id};
        return jdbcTemplate.update(sql_ingredient, params) == 1;
    }

    public OrderDetails getOrderDetails(int order_id)
    {
        String sql = "SELECT * FROM `order` " +
                     "WHERE order_id = ? ";
        Object[] params = {order_id};
        List<Order> order = jdbcTemplate.query(sql, params, orderRowMapper);

        if(order.size() > 0) {
            OrderDetails detail = new OrderDetails();
            detail.setOrder(order.get(0));
            detail.setSelectedMenuItems(getSelectedMenuItems(order_id));
            return detail;
        }
        else {
            return null;
        }

    }

    private List<SelectedMenuItem> getSelectedMenuItems(int order_id)
    {
        String sql = "SELECT * FROM selected_menu_item INNER JOIN menu_item " +
                     "ON selected_menu_item.menu_item_id = menu_item.menu_item_id  " +
                     "WHERE selected_menu_item.order_id = ? ";
        Object[] params = {order_id};
        List<SelectedMenuItem> selected_menu_items = jdbcTemplate.query(sql, params, selectedMenuItemRowMapper);

        for (SelectedMenuItem selected_menu_item : selected_menu_items) {
            int menu_item_id = selected_menu_item.getMenuItem().getMenuItemId();
            selected_menu_item.setSelectedIngredients(getSelectedIngredients(order_id, menu_item_id));
        }
        return selected_menu_items;
    }

    private List<Ingredient> getSelectedIngredients(int order_id, int menu_item_id)
    {
        String sql = "SELECT * FROM selected_ingredient INNER JOIN ingredient " +
                     "ON selected_ingredient.ingredient_id = ingredient.ingredient_id  " +
                     "WHERE selected_ingredient.order_id = ? AND selected_ingredient.menu_item_id = ?";
        Object[] params = {order_id, menu_item_id};
        return jdbcTemplate.query(sql, params, ingredientRowMapper);
    }

    public boolean addFavorite(int customer_id, int restaurant_id)
    {
        String sql = "INSERT INTO favorite " +
                     "VALUES (?, ?) ";
        Object[] params = {customer_id, restaurant_id};
        return jdbcTemplate.update(sql, params) == 1;
    }

    public boolean removeFavorite(int customer_id, int restaurant_id)
    {
        String sql = "DELETE FROM favorite " +
                     "WHERE customer_id = ? AND restaurant_id = ? ";
        Object[] params = {customer_id, restaurant_id};
        return jdbcTemplate.update(sql, params) == 1;
    }

    public List<Favorite> getFavorite(int customer_id)
    {
        String sql = "SELECT * FROM favorite " +
                     "WHERE customer_id = ? ";
        Object[] params = {customer_id};
        return jdbcTemplate.query(sql, params, favoriteRowMapper);
    }

    public RaffleEntryResults updateRaffleParticipation(OrderFromCustomer order) {
        List<Raffle> raffles = raffleCouponRepository.getRaffle(order.getRestaurantId());
        if(raffles.size() > 0) {
            List<Integer> num_entries = raffleCouponRepository.getEntryAmount(order.getCustomerId(), order.getRestaurantId());
            if(num_entries.size() == 0) {
                String sql = "INSERT INTO participates (customer_id, raffle_id, num_entries) " +
                             "VALUES (?, ?, ?) ";
                double min_entry_amount = raffles.get(0).getMinEntryPrice();
                double order_total = order.getPrice();
                int new_entries = (int) (order_total / min_entry_amount);
                Object[] params = {order.getCustomerId(), raffles.get(0).getRaffleId(), new_entries};
                jdbcTemplate.update(sql, params);

                RaffleEntryResults result = new RaffleEntryResults();
                result.setNewEntries(new_entries);
                result.setTotalEntries(new_entries);
                result.setMinimumEntryAmount(min_entry_amount);

                return result;

            }
            else {
                double min_entry_amount = raffles.get(0).getMinEntryPrice();
                double order_total = order.getPrice();
                int new_entries = (int) (order_total / min_entry_amount);
                int new_value = num_entries.get(0) + new_entries;
                String sql = "UPDATE participates SET num_entries = ? " +
                        "WHERE customer_id = ? AND raffle_id = ? ";
                Object[] params = {new_value, order.getCustomerId(), raffles.get(0).getRaffleId()};
                jdbcTemplate.update(sql, params);

                RaffleEntryResults result = new RaffleEntryResults();
                result.setNewEntries(new_entries);
                result.setTotalEntries(new_value);
                result.setMinimumEntryAmount(min_entry_amount);

                return result;
            }
        }
        RaffleEntryResults result = new RaffleEntryResults();
        result.setNewEntries(-1);
        result.setTotalEntries(-1);
        result.setMinimumEntryAmount(-1);

        return result;
    }

    public boolean approveOrder(int order_id) {
        String sql = "UPDATE `order` SET status = 'Delivered-Approved'" +
                "WHERE order_id = ? ";
        Object[] params = {order_id};
        return jdbcTemplate.update(sql, params) == 1;
    }
}
