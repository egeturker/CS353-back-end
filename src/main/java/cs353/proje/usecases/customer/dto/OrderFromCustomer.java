package cs353.proje.usecases.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderFromCustomer {
    private int restaurantId;
    private int customerId;
    private double price;
    private Timestamp optionalDeliveryTime;
    private String paymentMethod;
    private String coupon;

    List<SelectedMenuItemFromCustomer> selectedMenuItems;
}
