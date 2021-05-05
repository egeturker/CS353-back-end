package cs353.proje.usecases.common.service;

import cs353.proje.usecases.common.dto.Coupon;
import cs353.proje.usecases.common.dto.Raffle;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.repository.RaffleCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
            return new Response(false, "No coupons found", Collections.emptyList());
    }

    public Response checkCoupon(int restaurantId, String couponId){
        List<Coupon> coupons = raffleCouponRepository.checkCoupon(restaurantId, couponId);
        if(coupons.size() >= 1)
            return new Response(true, "Success", coupons.get(0));
        else
            return new Response(false,"No coupons found", null);
    }

    public Response getRaffle(int restaurantId){
        List<Raffle> raffles = raffleCouponRepository.getRaffle(restaurantId);
        if(raffles.size() >= 1)
            return new Response(true, "Success", raffles.get(0));
        else
            return new Response(true,"No ongoing raffles found for the restaurant", null);
    }

    public Response getEntryAmount(int restaurantId, int customerId){
        if(raffleCouponRepository.getRaffle(restaurantId).size() < 1)
            return new Response(true, "No ongoing raffles found for the restaurant", null);
        List<Integer> sqlData = raffleCouponRepository.getEntryAmount(customerId, restaurantId);
        boolean customerHasParticipated = (sqlData.size() > 0);
        if(!customerHasParticipated)
            return new Response(true, "Customer has not participated in any ongoing raffles", 0);
        else{
            int entryAmount = sqlData.get(0);
            if(entryAmount < 1)
                return new Response(true, "Unsuccessful",null);//This case should not occur if customer has participated
            else
                return new Response(true, "Success", entryAmount);
        }
    }

    public Response newRaffle(int restaurantId, Raffle raffle){
        List<Raffle> raffles = raffleCouponRepository.getRaffle(restaurantId);
        if(raffles.size() >= 1)
            return new Response(false, "An ongoing raffle already exists for the restaurant", raffles.get(0));
        else{
            if(raffleCouponRepository.newRaffle(restaurantId, raffle))
                return new Response(true, "New raffle started successfully", null );
            else
                return new Response(false,"Unsuccessful",null);
        }
    }

}
