package cs353.proje.usecases.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReportResults {
    private List<String> columnNames;
    private List<List<String>> rows;
}
