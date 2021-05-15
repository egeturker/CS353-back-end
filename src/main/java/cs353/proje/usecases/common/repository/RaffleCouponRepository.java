package cs353.proje.usecases.common.repository;

import cs353.proje.usecases.common.dto.Coupon;
import cs353.proje.usecases.common.dto.Raffle;
import cs353.proje.usecases.common.dto.RaffleEntry;
import cs353.proje.usecases.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        coupon.setRestaurantName(customerRepository.getRestaurantInfo(coupon.getRestaurantId()).getRestaurantName());

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

    RowMapper<RaffleEntry> raffleEntryRowMapper = (rs, rowNum) ->{
        RaffleEntry raffleEntry = new RaffleEntry();
        raffleEntry.setRaffleId(rs.getInt("raffle_id"));
        raffleEntry.setCustomerId(rs.getInt("customer_id"));
        raffleEntry.setNumEntries(rs.getInt("num_entries"));

        return raffleEntry;
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

    public List<Raffle> getUnfinishedRaffle(int restaurantId){
        String sql = "SELECT * FROM raffle WHERE restaurant_id = ?" +
                " AND (ending_date >= ? AND starting_date <= ?) OR (winner IS NULL)";
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

    public boolean newRaffle(int restaurantId, Raffle raffle){
        String sql = "INSERT INTO raffle(coupon_prize, ending_date, min_entry_price," +
                " restaurant_id, starting_date, winner) VALUES (?,?,?,?,?,?)";
        Object[] params = {raffle.getCouponPrize(), raffle.getEndingDate(), raffle.getMinEntryPrice(),
            restaurantId, raffle.getStartingDate(), null};

        return (jdbcTemplate.update(sql, params) > 0);
    }

    //Returns winner
    public int finishRaffle(int restaurantId, int raffleId){
        if(!raffleCanFinish(restaurantId, raffleId))
            return -1;

        int entryAmount, customerId, upperBound, winner;

        List<RaffleEntry> participants = getRaffleParticipants(raffleId);
        ArrayList<Integer> entries = new ArrayList<>();

        for (RaffleEntry participant : participants) {
            entryAmount = participant.getNumEntries();
            customerId = participant.getCustomerId();

            for (int j = 0; j < entryAmount; j++)
                entries.add(customerId);
        }

        Random random = new Random();
        upperBound = entries.size();
        winner = entries.get(random.nextInt(upperBound));

        String sql = "UPDATE raffle SET winner = ? WHERE raffle_id = ?";
        Object[] params = {winner, raffleId};

        if(jdbcTemplate.update(sql, params) == 1)
            return winner;
        else
            return -1;
    }

    public List<RaffleEntry> getRaffleParticipants(int raffleId){
        String sql = "SELECT * FROM participates WHERE raffle_id = ?";
        Object[] params = {raffleId};

        return jdbcTemplate.query(sql, params, raffleEntryRowMapper);
    }

    public boolean raffleCanFinish(int restaurantId, int raffleId){
        String sql = "SELECT COUNT(*) FROM raffle " +
                "WHERE raffle_id = ? AND restaurant_id = ? AND ending_date < ? AND winner IS NULL";
        Object[] params = {raffleId, restaurantId, Date.from(Instant.now())};

        return jdbcTemplate.queryForObject(sql, params, Integer.class) == 1;
    }


}
