package cs353.proje.usecases.admin.service;

import cs353.proje.usecases.admin.dto.ReportResults;
import cs353.proje.usecases.admin.repository.AdminRepository;
import cs353.proje.usecases.common.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    public Response paymentMethodReport()
    {
        ReportResults reportResults = new ReportResults();
        List<String> columnNames = Arrays.asList("Payment Method", "Number of Orders", "Total Price");
        List<List<String>> rows = adminRepository.paymentMethodReport();

        reportResults.setRows(rows);
        reportResults.setColumnNames(columnNames);
        return new Response(true, "Successfully generated the report.", reportResults);
    }
}
