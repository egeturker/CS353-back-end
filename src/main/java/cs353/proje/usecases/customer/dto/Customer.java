package cs353.proje.usecases.customer.dto;

import cs353.proje.usecases.loginregister.dto.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper=true)
public class Customer extends User {

    private String address;
    private int region_id;

}
