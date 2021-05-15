package cs353.proje.usecases.restaurant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class OrderWithCustomerName {
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
    private String customerNameSurname;

}