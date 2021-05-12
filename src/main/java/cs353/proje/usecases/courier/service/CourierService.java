package cs353.proje.usecases.courier.service;

import cs353.proje.usecases.common.dto.AssignedOrder;
import cs353.proje.usecases.common.dto.Order;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.courier.dto.AllCourierData;
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
        List<AllCourierData> allCourierData = courierRepository.getCourierData(courierId);
        if(allCourierData.size() == 1)
            return new Response(true, "Success", allCourierData.get(0));
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

    public Response getAcceptedOrder(int courierId){
        List<Order> order = courierRepository.getAcceptedOrder(courierId);
        if(order.size() == 1)
            return new Response(true, "Success", order.get(0));
        else
            return new Response(true,"Accepted order not found", null);
    }

    public Response acceptAssignment(int courierId, int orderId){
        if(courierRepository.acceptAssignment(courierId, orderId))
            return new Response(true, "Success", null );
        else
            return new Response(false, "Unsuccessful", null);
    }

    public Response rejectAssignment(int courierId, int orderId){
        if(courierRepository.rejectAssignment(courierId, orderId))
            return new Response(true, "Success", null );
        else
            return new Response(false, "Unsuccessful", null);
    }

    public Response finalizeOrder(int courierId, int orderId){
        if(courierRepository.finalizeOrder(courierId, orderId))
            return new Response(true, "Success", null);
        else
            return new Response(false, "Unsuccessful", null);
    }

    public Response getOldOrders(int courierId){
        List<Order> oldOrders = courierRepository.getOldOrders(courierId);
        if(oldOrders.size() > 0)
            return new Response(true, "Success", oldOrders);
        else
            return new Response(true, "No orders found", Collections.emptyList());
    }

}
