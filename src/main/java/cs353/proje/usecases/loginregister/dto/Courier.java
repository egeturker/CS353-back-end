package cs353.proje.usecases.loginregister.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper=true)
public class Courier extends User {
    private boolean status;
    private double rating;
}
