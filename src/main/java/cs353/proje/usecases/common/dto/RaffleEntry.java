package cs353.proje.usecases.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RaffleEntry {
    private int raffleId;
    private int customerId;
    private int numEntries;
}
