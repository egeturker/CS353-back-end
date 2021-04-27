package cs353.proje.usecases.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Coupon {
    private int couponId;
    private double discountAmount;
    private int customerId;
    private String used;
    private int restaurantId;
}
