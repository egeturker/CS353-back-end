package cs353.proje.usecases.customer.dto;

import cs353.proje.usecases.common.dto.Ingredient;
import cs353.proje.usecases.common.dto.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDetails {

    private Order order;
    private List<SelectedMenuItem> selectedMenuItems;
}
