package cs353.proje.usecases.restaurant.dto;


import cs353.proje.usecases.loginregister.dto.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatedRestaurantData {

    private User restaurantOwnerData;
    private String restaurantName;
    private String address;
    private String description;
    private String restaurantCategory;

}
