package cs353.proje.usecases.common.service;

import cs353.proje.usecases.common.dto.Coupon;
import cs353.proje.usecases.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RaffleCouponRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
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

    public List<Coupon> getCoupons(int customer_id) {
        String sql = "SELECT * " +
                "FROM coupon INNER JOIN restaurant ON coupon.restaurant_id = restaurant.restaurant_id " +
                "WHERE customer_id = ? AND used <> true";
        Object[] params = {customer_id};
        return jdbcTemplate.query(sql, params, couponRowMapper);
    }
}
