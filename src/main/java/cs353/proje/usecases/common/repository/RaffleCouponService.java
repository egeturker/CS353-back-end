package cs353.proje.usecases.common.repository;

import cs353.proje.usecases.common.dto.Coupon;
import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.service.RaffleCouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaffleCouponService {

    @Autowired
    RaffleCouponRepository raffleCouponRepository;

    public Response getCoupons(int customer_id) {
        List<Coupon> coupons = raffleCouponRepository.getCoupons(customer_id);
        if(coupons.size() >= 1)
            return new Response(true, "Success", coupons);
        else
            return new Response(false, "No coupons found", null);
    }
}
