package cs353.proje.usecases.courier.dto;

import cs353.proje.usecases.loginregister.dto.Courier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AllCourierData {

    Courier courier;
    List<OperateRegion> operateRegions;

}
