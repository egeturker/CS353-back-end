package cs353.proje.usecases.courier.controller;

import cs353.proje.usecases.common.dto.Order;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.courier.dto.OperateRegion;
import cs353.proje.usecases.loginregister.dto.Courier;
import cs353.proje.usecases.loginregister.dto.User;
import cs353.proje.usecases.restaurant.dto.UpdatedRestaurantData;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("CourierController")
@RequestMapping("/courier")
@CrossOrigin
public class CourierController {

    @GetMapping("/courierData/courier_id={id}")
    public Response getCourierData(@PathVariable(value="id") int courier_id)
    {
        Courier courier; //Return a Courier object in Response's data
        return null;
    }

    //Similar to updateCustomerData
    //Only the fields given in the User class will be updated.
    @PostMapping("/courierData/courier_id={id}")
    public Response updateCourierData(@PathVariable(value="id") int courier_id,
                                         @RequestBody User updatedCourierData)
    {
        return null;
    }

    //Couriers update the regions that they operate in, together with the fees
    //If a courier was operating in region 1 before, and this new list doesn't contain the region 1, delete it from Assigned_To table
    //Otherwise insert into the table or update the fee of a currently existing row.
    @PostMapping("/updateRegions/courier_id={courier_id}")
    public Response updateOperateRegions(@PathVariable(value="courier_id") int courier_id,
                                     @RequestBody List<OperateRegion> regions)
    {
        return null;
    }

    //If there are no assignments, don't send null.
    //Just return (success=true) with (data=empty list)
    @GetMapping("/getCurrentAssignments/courier_id={id}")
    public Response getCurrentAssignments(@PathVariable(value="id") int courier_id)
    {
        List<Order> assignedOrderList;
        return null;
    }

    //This is the assignment that the courier has accepted and currently delivering.
    //There can be only one such order at a time.
    //This order must have status="Delivering".
    //If no such order, return (success=true) with (data=null)
    @GetMapping("/getAcceptedOrder/courier_id={id}")
    public Response getAcceptedOrder(@PathVariable(value="id") int courier_id)
    {
        Order acceptedOrder;
        return null;
    }

    //Courier accepts one of the assigned orders.
    //Just changing the "decision" column in the "Assigned_To" table should be enough.
    //We can do the rest of the operations using a trigger. Talk about this in discord.
    @PostMapping("/acceptAssignment/courier_id={courier_id}/order_id={order_id}")
    public Response acceptAssignment(@PathVariable(value="courier_id") int courier_id,
                                @PathVariable(value="order_id") int order_id)
    {
        return null;
    }

    //Courier rejects one of the assigned orders.
    //Just changing the "decision" column in the "Assigned_To" table should be enough.
    //There is nothing else to do here.
    @PostMapping("/rejectAssignment/courier_id={courier_id}/order_id={order_id}")
    public Response rejectAssignment(@PathVariable(value="courier_id") int courier_id,
                                     @PathVariable(value="order_id") int order_id)
    {
        return null;
    }

    //Courier finalizes the delivering process of the accepted order.
    //Status of the order will be changed to "Delivered-Waiting Your Approval"
    @PostMapping("/finalizeOrder/courier_id={courier_id}/order_id={order_id}")
    public Response finalizeOrder(@PathVariable(value="courier_id") int courier_id,
                                     @PathVariable(value="order_id") int order_id)
    {
        return null;
    }

}
