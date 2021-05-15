package cs353.proje.usecases.common.controller;

import cs353.proje.usecases.common.dto.Raffle;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.service.RaffleCouponService;
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
        return raffleCouponService.checkCoupon(restaurant_id, couponID);
    }

    //If there is an ongoing raffle for the given restaurant, return its information
    //If there is no ongoing raffle, return (data = null) with (success = true)
    @GetMapping("/getRaffle/restaurant_id={id}")
    public Response getRaffle(@PathVariable(value="id") int restaurant_id)
    {

        return raffleCouponService.getRaffle(restaurant_id);
    }

    @GetMapping("/getUnfinishedRaffle/restaurant_id={id}")
    public Response getUnfinishedRaffle(@PathVariable(value="id") int restaurant_id)
    {

        return raffleCouponService.getUnfinishedRaffle(restaurant_id);
    }

    //If there is an ongoing raffle for the given restaurant,
    //return the amount of entries this customer have in the raffle
    //If there is no raffle, return (data = 0) with (success = true)
    @GetMapping("/getEntryAmount/restaurant_id={restaurant_id}/customer_id={customer_id}")
    public Response getEntryAmount(@PathVariable(value="restaurant_id") int restaurant_id,
                                    @PathVariable(value="customer_id") int customer_id)
    {
        return raffleCouponService.getEntryAmount(restaurant_id, customer_id);
    }

    //Restaurant starts a new raffle, a restaurant must have only one raffle at a time,
    //so check for overlaps with the other raffles of this restaurant
    //The RequestBody Raffle includes all the information except the raffle_id
    @PostMapping("/newRaffle/restaurant_id={restaurant_id}")
    public Response newRaffle(@PathVariable(value="restaurant_id") int restaurant_id,
                              @RequestBody Raffle raffle)
    {
        return raffleCouponService.newRaffle(restaurant_id, raffle);
    }

    //Restaurant finishes the raffle
    @PostMapping("/finishRaffle/restaurant_id={restaurant_id}/raffle_id={raffle_id}")
    public Response finishRaffle(@PathVariable(value="restaurant_id") int restaurant_id,
                                 @PathVariable(value="raffle_id") int raffle_id)
    {
        return raffleCouponService.finishRaffle(restaurant_id, raffle_id);
    }

}
