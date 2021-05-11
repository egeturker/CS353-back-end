package cs353.proje.usecases.courier.service;

import cs353.proje.usecases.common.dto.AssignedOrder;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.courier.dto.OperateRegion;
import cs353.proje.usecases.courier.repository.CourierRepository;
import cs353.proje.usecases.loginregister.dto.Courier;
import cs353.proje.usecases.loginregister.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CourierService {

    @Autowired
    CourierRepository courierRepository;

    public Response getCourierData(int courierId){
        List<Courier> courier = courierRepository.getCourierData(courierId);
        if(courier.size() == 1)
            return new Response(true, "Success", courier.get(0));
        else
            return new Response(false,"Courier not found", null);
    }

    public Response updateCourierData(int courierId, User updatedCourierData){
        if(courierRepository.updateCourierData(courierId, updatedCourierData))
            return new Response(true, "Success", null);
        else
            return new Response(false, "Courier not found", null);
    }

    public Response updateOperateRegions(int courierId, List<OperateRegion> regions){
        if(courierRepository.updateOperateRegions(courierId, regions))
            return new Response(true, "Success", null);
        else
            return new Response(false, "Courier not found", null);
    }

    public Response getCurrentAssignments(int courierId){
        List<AssignedOrder> assignments = courierRepository.getCurrentAssignments(courierId);
        if(assignments.size() >= 1)
            return new Response(true, "Success", assignments);
        else
            return new Response(true, "No assignments found", Collections.emptyList());
    }

}
