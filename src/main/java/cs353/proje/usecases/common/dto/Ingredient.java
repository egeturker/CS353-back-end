package cs353.proje.usecases.common.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Ingredient {
    private int menuItemId;
    private int ingredientId;
    private String ingredientName;
    private boolean defaultIngredient;
    private double additionalPrice;
    private boolean deleted;
}
