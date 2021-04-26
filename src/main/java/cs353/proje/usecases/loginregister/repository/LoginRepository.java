package cs353.proje.usecases.loginregister.repository;

import cs353.proje.usecases.customer.dto.Customer;
import cs353.proje.usecases.loginregister.dto.Courier;
import cs353.proje.usecases.loginregister.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Repository
public class LoginRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setTelephone(rs.getString("telephone"));
        user.setImage(rs.getString("image"));
        user.setRegistrationDate(rs.getDate("registration_date"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));
        user.setUserType(rs.getString("user_type"));

        return user;
    };

    RowMapper<Integer> integerRowMapper = (rs, rowNum) -> rs.getInt(1);

    public User login(User loginInfo)
    {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        Object [] params = {loginInfo.getUsername(), loginInfo.getPassword()};
        List<User> userInfo = jdbcTemplate.query(sql, params, userRowMapper);

        if (userInfo.size() == 1)
            return userInfo.get(0);
        else
            return null;
    }

    public int addUser(User registerInfo)
    {
        String sql = "INSERT INTO user(name, surname, email, username, password, telephone, registration_date, image, user_type) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object [] params = {registerInfo.getName(), registerInfo.getSurname(), registerInfo.getEmail(),
                registerInfo.getUsername(), registerInfo.getPassword(),
                registerInfo.getTelephone(), LocalDate.now(),
                registerInfo.getImage(), registerInfo.getUserType()};

        if (jdbcTemplate.update(sql, params) < 0)
            return -1;
        else {
            String sqlGetId = "SELECT LAST_INSERT_ID()";
            Object [] params2 = {};
            int id = jdbcTemplate.queryForObject(sqlGetId, integerRowMapper);

            return id;
        }
    }

    public int addCustomer(Customer registerInfo)
    {
        String sql = "INSERT INTO customer(customer_id, address, region_id) " +
                "VALUES(?, ?, ?)";

        Object [] params = {registerInfo.getUserId(), registerInfo.getAddress(), registerInfo.getRegion_id()};

        return jdbcTemplate.update(sql, params);
    }

    public int addCourier(Courier registerInfo)
    {
        String sql = "INSERT INTO courier(courier_id, status, rating) " +
                "VALUES(?, ?, ?)";

        Object [] params = {registerInfo.getUserId(), "Not Available", 0.00};

        return jdbcTemplate.update(sql, params);
    }

    public int addEmptyRestaurant(int owner_id)
    {
        String sql = "INSERT INTO restaurant(owner_id, restaurant_name, rating, address, description, restaurant_category, status) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        Object [] params = {owner_id, "RESTAURANT NAME NOT SET", 0.00, "ADRESS NOT SET", "", "CATEGORY NOT SET", "Closed"};

        return jdbcTemplate.update(sql, params);
    }

}
