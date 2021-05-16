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

    RowMapper<List<String>> raffleStatisticsRowMapper = (rs, rowNum) -> {
        List<String> row = new ArrayList<>();
        row.add(rs.getString(1));
        row.add(rs.getString(2));
        row.add(rs.getString(3));
        row.add(rs.getString(4));
        row.add(rs.getString(5) );

        return row;
    };

    public List<List<String>> paymentMethodReport()
    {
        String sql = "SELECT payment_method, COUNT(*), SUM(price) FROM `order` GROUP BY payment_method";
        Object[] params = {};

        return jdbcTemplate.query(sql, params, paymentMethodReportRowMapper);
    }

    public List<List<String>> raffleStatisticsReport() {
        String sql = "SELECT raffle_id, MAX(num_entries) AS max_entries, SUM(num_entries) AS total_entries, COUNT(*) AS total_participants,\n" +
                "       (SELECT num_entries FROM participates AS p\n" +
                "       WHERE customer_id = (SELECT winner FROM raffle WHERE raffle_id = participates.raffle_id )\n" +
                "         AND raffle_id = participates.raffle_id) AS winner_num_entries\n" +
                "FROM participates\n" +
                "WHERE EXISTS (SELECT * FROM raffle WHERE raffle_id = participates.raffle_id AND winner IS NOT NULL )\n" +
                "GROUP BY raffle_id";
        Object[] params = {};

        return jdbcTemplate.query(sql, params, raffleStatisticsRowMapper);
    }
}
