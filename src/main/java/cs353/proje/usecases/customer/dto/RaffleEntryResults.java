package cs353.proje.usecases.customer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RaffleEntryResults {

    private int newEntries;
    private int totalEntries;
    private int minimumEntryAmount;

}
