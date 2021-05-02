package cs353.proje.usecases.common.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.repository.RaffleCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("RaffleCouponController")
@RequestMapping("/raffle")
@CrossOrigin
public class RaffleCouponController {

    @Autowired
    RaffleCouponService raffleCouponService;

    @GetMapping("/coupons/id={id}")
    public Response getCoupons(@PathVariable(value="id") int customer_id)
    {
        return raffleCouponService.getCoupons(customer_id);
    }

    //Check whether the the coupon with the given id is applicable for that restaurant,
    //It should be not used within this method, this is just for checking.
    //Return the coupons information in response by putting a Coupon object into data.
    @PostMapping("/checkCoupon/restaurant_id={id}")
    public Response checkCoupon(@PathVariable(value="id") int restaurant_id,
                                @RequestBody String couponID)
    {
        return null;
    }

    //If there is an ongoing raffle for the given restaurant, return its information
    //If there is no ongoing raffle, return (data = null) with (success = true)
    @GetMapping("/getRaffle/restaurant_id={id}")
    public Response getRaffle(@PathVariable(value="id") int restaurant_id)
    {
        return null;
    }

    //If there is an ongoing raffle for the given restaurant,
    //return the amount of entries this customer have in the raffle
    //If there is no raffle, return (data = 0) with (success = true)
    @GetMapping("/getEntryAmount/restaurant_id={restaurant_id}/customer_id={customer_id}")
    public Response getEntryAmount(@PathVariable(value="restaurant_id") int restaurant_id,
                                    @PathVariable(value="customer_id") int customer_id)
    {
        return null;
    }

}
