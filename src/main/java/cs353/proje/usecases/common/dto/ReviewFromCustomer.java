package cs353.proje.usecases.common.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewFromCustomer {

    private int restaurant_score;
    private int courier_score;
    private String comment;
    private int order_id;

}
