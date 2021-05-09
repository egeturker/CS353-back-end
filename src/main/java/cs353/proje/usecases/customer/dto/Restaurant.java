package cs353.proje.usecases.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Restaurant {
    private int restaurantId;
    private int ownerId;
    private String restaurantName;
    private double rating;
    private String address;
    private String description;
    private String image;
    private String restaurantCategory;
    private String status;
}
