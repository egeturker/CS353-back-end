package cs353.proje.usecases.courier.repository;

import cs353.proje.usecases.courier.dto.OperateRegion;
import cs353.proje.usecases.loginregister.dto.Courier;
import cs353.proje.usecases.loginregister.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class CourierRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Courier> courierRowMapper = (rs, rowNum) -> {
        Courier courier = new Courier();
        courier.setUserId(rs.getInt("courier_id"));
        courier.setStatus(rs.getBoolean("status"));
        courier.setRating(rs.getDouble("rating"));
        courier.setEmail(rs.getString("email"));
        courier.setUsername(rs.getString("username"));
        courier.setPassword(rs.getString("password"));
        courier.setTelephone(rs.getString("telephone"));
        courier.setImage(rs.getString("image"));
        courier.setRegistrationDate(rs.getDate("registration_date"));
        courier.setName(rs.getString("name"));
        courier.setSurname(rs.getString("surname"));
        courier.setUserType(rs.getString("user_type"));

        return courier;
    };

    public List<Courier> getCourierData(int courierId){

        String sql = "SELECT * FROM courier INNER JOIN user " +
                "ON user.user_id = courier.courier_id WHERE courier.courier_id = ?";
        Object[] params = {courierId};

        return jdbcTemplate.query(sql, params, courierRowMapper);
    }

    public boolean updateCourierData(int courierId, User updatedCourierData){

        String sql = "UPDATE user " +
                "SET email = ?, " +
                "username = ?, " +
                "password = ?, " +
                "telephone = ?, " +
                "image = ?, " +
                "name = ?, " +
                "surname = ?," +
                "userType = ? WHERE user.user_id = ?";
        Object[] params = {updatedCourierData.getEmail(), updatedCourierData.getUsername(), updatedCourierData.getPassword(),
        updatedCourierData.getTelephone(), updatedCourierData.getImage(), updatedCourierData.getName(),
        updatedCourierData.getSurname(), updatedCourierData.getUserType(), courierId};

        return jdbcTemplate.update(sql, params) == 1;
    }

    public boolean updateOperateRegions(int courierId, List<OperateRegion> regions){

        int result, result2 = 0;
        String sql = "DELETE FROM operates_in WHERE courier_id = ? ";
        Object[] params = {courierId};
        result = jdbcTemplate.update(sql, params);

        sql = "INSERT INTO operates_in(courier_id, region_id, fee) VALUES (?,?,?)";
        for (OperateRegion region : regions) {
            Object[] params2 = {courierId, region.getRegionId(), region.getFee()};
            result2 = jdbcTemplate.update(sql, params2);
        }

        return (result > 0 && result2 > 0);
    }

}
