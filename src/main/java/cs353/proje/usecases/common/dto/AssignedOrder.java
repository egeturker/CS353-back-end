package cs353.proje.usecases.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class AssignedOrder extends Order{
    private String decision;
    private Timestamp assignmentTime;
    private Timestamp decisionTime;
    private int courierId;
}
