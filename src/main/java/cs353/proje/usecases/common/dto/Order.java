package cs353.proje.usecases.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    private int orderId;
    private int restaurantId;
    private int customerId;
    private double price;
    private Timestamp orderTime;
    private Timestamp deliveryTime;
    private String status;
    private Timestamp optionalDeliveryTime;
    private String paymentMethod;
    private String restaurantName;
    private String coupon;
    private Double deliveryFee;

}