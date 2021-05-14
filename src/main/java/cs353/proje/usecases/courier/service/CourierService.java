package cs353.proje.usecases.courier.service;

import cs353.proje.usecases.common.dto.AssignedOrder;
import cs353.proje.usecases.common.dto.Order;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.courier.dto.AllCourierData;
import cs353.proje.usecases.courier.dto.OperateRegion;
import cs353.proje.usecases.courier.dto.OrderDetailsForCourier;
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
        List<OrderDetailsForCourier> orderDetailsForCouriers = courierRepository.getCurrentAssignments(courierId);
        if(orderDetailsForCouriers.size() >= 1)
            return new Response(true, "Success", orderDetailsForCouriers);
        else
            return new Response(true, "No assignments found", Collections.emptyList());
    }

    public Response getAcceptedOrder(int courierId){
        List<OrderDetailsForCourier> orderDetailsForCouriers = courierRepository.getAcceptedOrder(courierId);
        if(orderDetailsForCouriers.size() == 1)
            return new Response(true, "Success", orderDetailsForCouriers.get(0));
        else
            return new Response(true,"Accepted order not found", null);
    }

    public Response acceptAssignment(int courierId, int orderId){
        if(courierRepository.acceptAssignment(courierId, orderId)){
            List<OrderDetailsForCourier> orderDetailsForCouriers = courierRepository.getCurrentAssignments(courierId);
            for (OrderDetailsForCourier orderDetailsForCourier : orderDetailsForCouriers)
                if (orderDetailsForCourier.getOrder().getOrderId() != orderId)
                    courierRepository.rejectAssignment(courierId, orderId);
            if(courierRepository.statusUpdateDelivering(orderId) && courierRepository.close(courierId))
                return new Response(true, "Success", null );
            else
                return new Response(false, "Unsuccessful", null);
        }
        else
            return new Response(false, "Unsuccessful", null);
    }

    public Response rejectAssignment(int courierId, int orderId){
        if(courierRepository.rejectAssignment(courierId, orderId) &&
                courierRepository.statusUpdateWaitingCourier(orderId))
            return new Response(true, "Success", null );
        else
            return new Response(false, "Unsuccessful", null);
    }

    public Response finalizeOrder(int courierId, int orderId){
        if(courierRepository.finalizeOrder(courierId, orderId) && courierRepository.open(courierId))
            return new Response(true, "Success", null);
        else
            return new Response(false, "Unsuccessful", null);
    }

    public Response getOldOrders(int courierId){
        List<OrderDetailsForCourier> orderDetailsForCouriers = courierRepository.getOldOrders(courierId);
        if(orderDetailsForCouriers.size() > 0)
            return new Response(true, "Success", orderDetailsForCouriers);
        else
            return new Response(true, "No orders found", Collections.emptyList());
    }

    public Response open(int courierId){
        if (courierRepository.getCourierStatus(courierId))
            return new Response(false, "Courier already open", null);
        else
            if(courierRepository.open(courierId))
                return new Response(true,"Success", null);
            else
                return new Response(false,"Unsuccessful", null);
    }

    public Response close(int courierId){
        if (!courierRepository.getCourierStatus(courierId))
            return new Response(false, "Courier already close", null);
        else
        if(courierRepository.close(courierId))
            return new Response(true,"Success", null);
        else
            return new Response(false,"Unsuccessful", null);
    }



}
