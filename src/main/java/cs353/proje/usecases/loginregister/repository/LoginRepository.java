package cs353.proje.usecases.loginregister.repository;

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

    public int register(User registerInfo)
    {
        String sql = "INSERT INTO user(name, surname, email, username, password, telephone, registration_date, image, user_type) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Object [] params = {registerInfo.getName(), registerInfo.getSurname(), registerInfo.getEmail(),
                registerInfo.getUsername(), registerInfo.getPassword(),
                registerInfo.getTelephone(), LocalDate.now(),
                registerInfo.getImage(), registerInfo.getUserType()};

        return jdbcTemplate.update(sql, params);
    }

}
