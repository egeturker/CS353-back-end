package cs353.proje.usecases.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MenuItem {
    private int restaurantId;
    private int menuItemId;
    private String name;
    private String imageLink;
    private String description;
    private double basePrice;
    private String foodCategory;

}
