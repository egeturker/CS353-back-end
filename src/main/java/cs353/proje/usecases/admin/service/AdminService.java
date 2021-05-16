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

    public Response raffleStatisticsReport()
    {
        ReportResults reportResults = new ReportResults();
        List<String> columnNames = Arrays.asList("Raffle Id", "Max Entries", "Total Entries", "Total Participants", "Winner Entries");
        List<List<String>> rows = adminRepository.raffleStatisticsReport();

        reportResults.setRows(rows);
        reportResults.setColumnNames(columnNames);
        return new Response(true, "Successfully generated the report.", reportResults);
    }

    public Response restaurantOrderStatisticsReport(){
        ReportResults reportResults = new ReportResults();
        List<String> columnNames = Arrays.asList("Restaurant Name", "Total Orders", "Total Price of Orders",
                "Total Reviews for Orders", "Review Score Average");
        List<List<String>> rows = adminRepository.restaurantOrderStatisticsReport();

        reportResults.setRows(rows);
        reportResults.setColumnNames(columnNames);
        return new Response(true, "Successfully generated the report.", reportResults);
    }
}
