package cs353.proje.usecases.admin.repository;

import cs353.proje.usecases.common.dto.Region;
import cs353.proje.usecases.common.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<List<String>> paymentMethodReportRowMapper = (rs, rowNum) -> {
        List<String> row = new ArrayList<>();
        row.add(rs.getString(1));
        row.add(rs.getInt(2) + "");
        row.add(rs.getInt(3) + "");

        return row;
    };

    public List<List<String>> paymentMethodReport()
    {
        String sql = "SELECT payment_method, COUNT(*), SUM(price) FROM `order` GROUP BY payment_method";
        Object[] params = {};

        return jdbcTemplate.query(sql, params, paymentMethodReportRowMapper);
    }

}
