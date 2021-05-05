package cs353.proje.usecases.common.repository;

import cs353.proje.usecases.common.dto.Coupon;
import cs353.proje.usecases.common.dto.Raffle;
import cs353.proje.usecases.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Repository
public class RaffleCouponRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    @Lazy
    CustomerRepository customerRepository;

    RowMapper<Coupon> couponRowMapper = (rs, rowNum) ->{
        Coupon coupon = new Coupon();
        coupon.setCouponId(rs.getString("coupon_id"));
        coupon.setDiscountAmount(rs.getInt("discount_amount"));
        coupon.setCustomerId(rs.getInt("customer_id"));
        coupon.setRestaurantId(rs.getInt("restaurant_id"));
        coupon.setUsed(false);
        coupon.setRestaurantName(customerRepository.getRestaurantInfo(coupon.getRestaurantId()).getRestaurant_name());

        return coupon;
    };

    RowMapper<Raffle> raffleRowMapper = (rs, rowNum) ->{
        Raffle raffle = new Raffle();
        raffle.setRaffleId(rs.getInt("raffle_id"));
        raffle.setStartingDate(rs.getTimestamp("starting_date"));
        raffle.setEndingDate(rs.getTimestamp("ending_date"));
        raffle.setMinEntryPrice(rs.getDouble("min_entry_price"));
        raffle.setCouponPrize(rs.getDouble("coupon_prize"));
        raffle.setRestaurantId(rs.getInt("restaurant_id"));

        return raffle;
    };

    public List<Coupon> getCoupons(int customerId) {
        String sql = "SELECT * " +
                "FROM coupon INNER JOIN restaurant ON coupon.restaurant_id = restaurant.restaurant_id " +
                "WHERE customer_id = ? AND used <> true";
        Object[] params = {customerId};
        return jdbcTemplate.query(sql, params, couponRowMapper);
    }

    public List<Coupon> checkCoupon(int restaurantId, String couponId){
        String sql = "SELECT * FROM coupon INNER JOIN restaurant ON " +
                "coupon.restaurant_id = restaurant.restaurant_id " +
                "WHERE restaurant.restaurant_id = ? AND coupon_id = ? AND used <> true";
        Object[] params = {restaurantId, couponId};
        return jdbcTemplate.query(sql, params, couponRowMapper);
    }

    public List<Raffle> getRaffle(int restaurantId){
        String sql = "SELECT * FROM raffle WHERE restaurant_id = ?" +
                " AND ending_date >= ? AND starting_date <= ?";
        Object[] params = {restaurantId, Timestamp.from(Instant.now()),Timestamp.from(Instant.now())};
        return  jdbcTemplate.query(sql, params, raffleRowMapper);

    }

    public List<Integer> getEntryAmount(int customerId, int restaurantId){
        String sql = "SELECT num_entries FROM raffle " +
                "INNER JOIN participates ON participates.raffle_id = raffle.raffle_id " +
                "WHERE raffle.restaurant_id = ? AND participates.customer_id = ? " +
                "AND ending_date >= ? AND starting_date <= ?";
        Object[] params = {restaurantId, customerId, Timestamp.from(Instant.now()), Timestamp.from(Instant.now())};

        return jdbcTemplate.queryForList(sql, params, Integer.class);
    }
}
