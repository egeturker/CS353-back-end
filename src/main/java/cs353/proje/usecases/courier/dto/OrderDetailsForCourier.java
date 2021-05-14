package cs353.proje.usecases.courier.dto;

import cs353.proje.usecases.common.dto.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetailsForCourier {
    private Order order;

    private String customerName;
    private String customerSurname;
    private String customerImage;
    private String customerAddress;
    private String customerRegionName;
    private String customerTelephone;

    private int courierScore; //If reviewed, otherwise this can be 0

}
