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
    //It should be not used
    //Return the coupons information in response.
    @PostMapping("/checkCoupon/restaurant_id={id}")
    public Response checkCoupon(@PathVariable(value="id") int restaurant_id,
                                @RequestBody String couponID)
    {
        return null;
    }

}
