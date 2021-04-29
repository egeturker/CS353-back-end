package cs353.proje.usecases.customer.dto;

import cs353.proje.usecases.common.dto.MenuItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SelectedMenuItem {
    private int orderId;
    private int menuItemId;
    private int quantity;

    //ingredient_ids of the selected ingredients for this menu item.
    private List<Integer> selectedIngredients;

}
