package cs353.proje.usecases.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class Review {

    private int reviewId;
    private Timestamp date;
    private int restaurantScore;
    private int courierScore;
    private String comment;
    private int orderId;
    private String response;

}
