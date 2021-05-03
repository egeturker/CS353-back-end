package cs353.proje.usecases.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Raffle {
    private int raffleId;
    private Timestamp startingDate;
    private Timestamp endingDate;
    private double minEntryPrice;
    private double couponPrize;
    private int restaurantId;
}
