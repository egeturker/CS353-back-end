package cs353.proje.usecases.courier.controller;

import cs353.proje.usecases.common.dto.Order;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.courier.dto.OperateRegion;
import cs353.proje.usecases.courier.service.CourierService;
import cs353.proje.usecases.loginregister.dto.Courier;
import cs353.proje.usecases.loginregister.dto.User;
import cs353.proje.usecases.restaurant.dto.UpdatedRestaurantData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("CourierController")
@RequestMapping("/courier")
@CrossOrigin
public class CourierController {

    @Autowired
    CourierService courierService;

    @GetMapping("/courierData/courier_id={id}")
    public Response getCourierData(@PathVariable(value="id") int courier_id)
    {
        return courierService.getCourierData(courier_id);
    }

    //Similar to updateCustomerData
    //Only the fields given in the User class will be updated.
    @PostMapping("/courierData/courier_id={id}")
    public Response updateCourierData(@PathVariable(value="id") int courier_id,
                                         @RequestBody User updatedCourierData)
    {
        return courierService.updateCourierData(courier_id, updatedCourierData);
    }

    //Couriers update the regions that they operate in, together with the fees
    //If a courier was operating in region 1 before, and this new list doesn't contain the region 1, delete it from operates_in table
    //Otherwise insert into the table or update the fee of a currently existing row.
    @PostMapping("/updateRegions/courier_id={courier_id}")
    public Response updateOperateRegions(@PathVariable(value="courier_id") int courier_id,
                                     @RequestBody List<OperateRegion> regions)
    {
        return courierService.updateOperateRegions(courier_id, regions);
    }

    //If there are no assignments, don't send null.
    //Just return (success=true) with (data=empty list)
    @GetMapping("/getCurrentAssignments/courier_id={id}")
    public Response getCurrentAssignments(@PathVariable(value="id") int courier_id)
    {
        return courierService.getCurrentAssignments(courier_id);
    }

    //This is the assignment that the courier has accepted and currently delivering.
    //There can be only one such order at a time.
    //This order must have status="Delivering".
    //If no such order, return (success=true) with (data=null)
    @GetMapping("/getAcceptedOrder/courier_id={id}")
    public Response getAcceptedOrder(@PathVariable(value="id") int courier_id)
    {
        return courierService.getAcceptedOrder(courier_id);
    }

    //Courier accepts one of the assigned orders.
    //Just changing the "decision" column in the "Assigned_To" table should be enough.
    //We can do the rest of the operations using a trigger. Talk about this in discord.

    //Use trigger to reject the other assignments.
    @PostMapping("/acceptAssignment/courier_id={courier_id}/order_id={order_id}")
    public Response acceptAssignment(@PathVariable(value="courier_id") int courier_id,
                                @PathVariable(value="order_id") int order_id)
    {
        return courierService.acceptAssignment(courier_id, order_id);
    }

    //Courier rejects one of the assigned orders.
    //Just changing the "decision" column in the "Assigned_To" table should be enough.
    //There is nothing else to do here.

    //Use trigger to assign to another courier.
    @PostMapping("/rejectAssignment/courier_id={courier_id}/order_id={order_id}")
    public Response rejectAssignment(@PathVariable(value="courier_id") int courier_id,
                                     @PathVariable(value="order_id") int order_id)
    {
        return courierService.rejectAssignment(courier_id, order_id);
    }

    //Courier finalizes the delivering process of the accepted order.
    //Status of the order will be changed to "Delivered-Waiting Your Approval"
    @PostMapping("/finalizeOrder/courier_id={courier_id}/order_id={order_id}")
    public Response finalizeOrder(@PathVariable(value="courier_id") int courier_id,
                                     @PathVariable(value="order_id") int order_id)
    {
        return courierService.finalizeOrder(courier_id, order_id);
    }

    @GetMapping("/orders/courier_id={id}")
    public Response getOldOrders(@PathVariable(value="id") int courier_id)
    {
        return courierService.getOldOrders(courier_id);
    }

    @GetMapping("/open/courier_id={id}")
    public Response open(@PathVariable(value="id") int courier_id)
    {
        return courierService.open(courier_id);
    }

    @GetMapping("/close/courier_id={id}")
    public Response close(@PathVariable(value="id") int courier_id)
    {
        return courierService.close(courier_id);
    }

}
