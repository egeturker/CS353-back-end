package cs353.proje.usecases.restaurant.repository;

import cs353.proje.usecases.common.dto.MenuItem;
import cs353.proje.usecases.common.dto.Order;
import cs353.proje.usecases.common.dto.Region;
import cs353.proje.usecases.customer.dto.Restaurant;
import cs353.proje.usecases.customer.repository.CustomerRepository;
import cs353.proje.usecases.loginregister.dto.User;
import cs353.proje.usecases.restaurant.dto.AllRestaurantData;
import cs353.proje.usecases.restaurant.dto.UpdatedRestaurantData;
import cs353.proje.usecases.common.repository.RaffleCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Lazy
    CustomerRepository customerRepository;

    RowMapper<Integer> integerRowMapper = (rs, rowNum) -> rs.getInt(1);

    RowMapper<AllRestaurantData> allRestaurantDataRowMapper = (rs, rowNum) -> {
        AllRestaurantData allRestaurantData = new AllRestaurantData();
        User ownerData = new User();
        ownerData.setUserId(rs.getInt("user_id"));
        ownerData.setEmail(rs.getString("email"));
        ownerData.setUsername(rs.getString("username"));
        ownerData.setPassword(rs.getString("password"));
        ownerData.setTelephone(rs.getString("telephone"));
        ownerData.setImage(rs.getString("image"));
        ownerData.setRegistrationDate(rs.getDate("registration_date"));
        ownerData.setName(rs.getString("name"));
        ownerData.setSurname(rs.getString("surname"));
        ownerData.setUserType(rs.getString("user_type"));

        allRestaurantData.setOwnerData(ownerData);
        allRestaurantData.setRestaurantId(rs.getInt("restaurant_id"));
        allRestaurantData.setRestaurantName(rs.getString("restaurant_name"));
        allRestaurantData.setAddress(rs.getString("address"));
        allRestaurantData.setDescription(rs.getString("description"));
        allRestaurantData.setRestaurantCategory(rs.getString("restaurant_category"));
        allRestaurantData.setRating(rs.getDouble("rating"));
        allRestaurantData.setStatus(rs.getBoolean("status"));
        allRestaurantData.setServedRegions(getServedRegions(allRestaurantData.getRestaurantId()));
        allRestaurantData.setImage(rs.getString("image"));

        return allRestaurantData;
    };

    RowMapper<Region> regionRowMapper = (rs, rowNum) -> {
        Region region = new Region();
        region.setRegionId(rs.getInt("region_id"));
        region.setRegionName(rs.getString("region_name"));

        return region;
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
        order.setRestaurantName(customerRepository.getRestaurantInfo(order.getRestaurantId()).getRestaurantName());

        return order;
    };

    public boolean updateRestaurantData(int restaurantId, UpdatedRestaurantData updatedRestaurantData){
        String sql = "UPDATE restaurant " +
                "SET owner_id = ?, " +
                "restaurant_name = ?, " +
                "address = ?, " +
                "description = ?, " +
                "restaurant_category = ?, " +
                "image = ? " +
                "WHERE restaurant_id = ?";
        Object[] params = {updatedRestaurantData.getRestaurantOwnerData().getUserId(), updatedRestaurantData.getRestaurantName(),
            updatedRestaurantData.getAddress(), updatedRestaurantData.getDescription(), updatedRestaurantData.getRestaurantCategory(),
            updatedRestaurantData.getImage(), restaurantId};

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

    public List<Region> getServedRegions(int restaurantId){
        String sql = "SELECT * FROM serves_at " +
                "INNER JOIN region ON region.region_id = serves_at.region_id " +
                "WHERE restaurant_id = ?";
        Object[] params = {restaurantId};

        return jdbcTemplate.query(sql, params, regionRowMapper);
    }

    public List<AllRestaurantData> getRestaurantData(int restaurantId){
        String sql = "SELECT * FROM restaurant " +
                "INNER JOIN user ON user.user_id = restaurant.owner_id " +
                "WHERE restaurant_id = ?";
        Object[] params = {restaurantId};

        return jdbcTemplate.query(sql, params, allRestaurantDataRowMapper);
    }

    public boolean addServedRegion(int restaurantId, int regionId){
        String sql = "INSERT INTO serves_at(restaurant_id, region_id) VALUES (?,?)";
        Object[] params = {restaurantId, regionId};

        return jdbcTemplate.update(sql, params) == 1;
    }

    public boolean removeServedRegion(int restaurantId, int regionId){
        String sql = "DELETE FROM serves_at WHERE restaurant_id = ? AND region_id = ? ";
        Object[] params = {restaurantId, regionId};

        return jdbcTemplate.update(sql, params) == 1;
    }

    public int getRestaurantId(int ownerId) {
        String sql = "SELECT restaurant_id FROM restaurant " +
                "WHERE owner_id = ?";
        Object[] params = {ownerId};
        List<Integer> restaurant_ids = jdbcTemplate.query(sql, params, integerRowMapper);
        if (restaurant_ids.size() > 0)
            return restaurant_ids.get(0);
        else
            return -1;
    }

    public List<Order> getOldOrders(int restaurantId) {
        String sql = "SELECT * FROM `order` " +
                     "WHERE restaurant_id = ?";
        Object[] params = {restaurantId};

        return jdbcTemplate.query(sql, params, orderRowMapper);

    }

    public boolean open(int restaurantId) {
        String sql = "UPDATE restaurant SET status = ? " +
                     "WHERE restaurant_id = ?";
        Object[] params = {1, restaurantId};

        return jdbcTemplate.update(sql, params) == 1;
    }

    public boolean close(int restaurantId) {
        String sql = "UPDATE restaurant SET status = ? " +
                     "WHERE restaurant_id = ?";
        Object[] params = {0, restaurantId};

        return jdbcTemplate.update(sql, params) == 1;
    }

    public boolean addMenuItem(int restaurantId, MenuItem menuItem) {
        String sql = "INSERT INTO menu_item(restaurant_id, name, image, description, base_price, food_category) " +
                     "VALUES(?, ?, ?, ?, ?, ?) ";
        Object[] params = {restaurantId, menuItem.getName(), menuItem.getImageLink(), menuItem.getDescription(),
                          menuItem.getBasePrice(), menuItem.getFoodCategory()};

        return jdbcTemplate.update(sql, params) == 1;
    }
}
