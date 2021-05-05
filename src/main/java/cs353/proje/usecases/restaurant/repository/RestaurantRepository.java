package cs353.proje.usecases.restaurant.repository;

import cs353.proje.usecases.common.dto.Region;
import cs353.proje.usecases.loginregister.dto.User;
import cs353.proje.usecases.restaurant.dto.AllRestaurantData;
import cs353.proje.usecases.restaurant.dto.UpdatedRestaurantData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantRepository {

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

        return allRestaurantData;
    };

    RowMapper<Region> regionRowMapper = (rs, rowNum) -> {
        Region region = new Region();
        region.setRegionId(rs.getInt("region_id"));
        region.setRegionName(rs.getString("region_name"));

        return region;
    };

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
}
