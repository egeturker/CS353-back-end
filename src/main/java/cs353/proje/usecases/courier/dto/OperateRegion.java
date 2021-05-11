package cs353.proje.usecases.courier.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class OperateRegion {
    private int regionId;
    private double fee;
}
