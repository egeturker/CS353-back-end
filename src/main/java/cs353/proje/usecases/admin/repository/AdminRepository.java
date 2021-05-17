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

    RowMapper<List<String>> restaurantOrderStatisticsRowMapper = (rs, rowNum) -> {
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

    public List<List<String>> restaurantOrderStatisticsReport(){
        String sql = "WITH review_info AS (SELECT restaurant.restaurant_id, COUNT(*) AS total_reviews, AVG(restaurant_score) AS average_score FROM review\n" +
                "                INNER JOIN `order`ON `order`.order_id = review.order_id\n" +
                "                INNER JOIN restaurant ON restaurant.restaurant_id = `order`.restaurant_id\n" +
                "                WHERE order_time > DATE_SUB(CURDATE(), INTERVAL 1 DAY)\n" +
                "                GROUP BY restaurant.restaurant_id ORDER BY restaurant.restaurant_id ASC)\n" +
                "SELECT restaurant_name, COUNT(*) AS total_orders, SUM(price) AS total_price, review_info.total_reviews, review_info.average_score\n" +
                "                FROM `order`\n" +
                "                INNER JOIN restaurant ON restaurant.restaurant_id = `order`.restaurant_id\n" +
                "                INNER JOIN review_info ON review_info.restaurant_id = restaurant.restaurant_id\n" +
                "                WHERE order_time > DATE_SUB(CURDATE(), INTERVAL 1 DAY)\n" +
                "                GROUP BY `order`.restaurant_id ORDER BY `order`.restaurant_id ASC";
        Object[] params = {};
        List<List<String>> report = jdbcTemplate.query(sql, params, restaurantOrderStatisticsRowMapper);
        
        return report;
    }
}
