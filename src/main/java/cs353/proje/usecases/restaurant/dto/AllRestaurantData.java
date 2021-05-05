package cs353.proje.usecases.restaurant.dto;

import cs353.proje.usecases.common.dto.Region;
import cs353.proje.usecases.loginregister.dto.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AllRestaurantData {

    private User ownerData;

    private int restaurantId;
    private String restaurantName;
    private String address;
    private String description;
    private String restaurantCategory;
    private double rating;
    private boolean status;

    private List<Region> servedRegions;

}
