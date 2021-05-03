package cs353.proje.usecases.common.service;

import cs353.proje.usecases.common.dto.Coupon;
import cs353.proje.usecases.common.dto.Raffle;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.repository.RaffleCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaffleCouponService {

    @Autowired
    RaffleCouponRepository raffleCouponRepository;

    public Response getCoupons(int customerId) {
        List<Coupon> coupons = raffleCouponRepository.getCoupons(customerId);
        if(coupons.size() >= 1)
            return new Response(true, "Success", coupons);
        else
            return new Response(false, "No coupons found", null);
    }

    public Response checkCoupon(int restaurantId, String couponId){
        List<Coupon> coupons = raffleCouponRepository.checkCoupon(restaurantId, couponId);
        if(coupons.size() >= 1)
            return new Response(true, "Success", coupons);
        else
            return new Response(false,"No coupons found", null);
    }

    public Response getRaffle(int restaurantId){
        List<Raffle> raffles = raffleCouponRepository.getRaffle(restaurantId);
        if(raffles.size() >= 1)
            return new Response(true, "Success", raffles);
        else
            return new Response(true,"No ongoing raffles found for the restaurant", null);
    }

    public Response getEntryAmount(int customerId, int restaurantId){
        if(raffleCouponRepository.getRaffle(restaurantId).size() < 1)
            return new Response(true, "No ongoing raffles found for the restaurant", null);
        List<Integer> sqlData = raffleCouponRepository.getEntryAmount(customerId, restaurantId);
        int entryAmount = sqlData.get(1);
        boolean customerHasParticipated = (sqlData.get(0) > 0);
        if(!customerHasParticipated)
            return new Response(true, "Customer has not participated in any ongoing raffles", null);
        if(entryAmount < 1)
            return new Response(true, "Unsuccessful",null);//This base should not occur if customer has participated
        else
            return new Response(true, "Success", entryAmount);
    }

}
