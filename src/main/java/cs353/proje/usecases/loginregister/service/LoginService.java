package cs353.proje.usecases.loginregister.service;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.customer.dto.Customer;
import cs353.proje.usecases.loginregister.dto.Courier;
import cs353.proje.usecases.loginregister.dto.RestaurantOwner;
import cs353.proje.usecases.loginregister.dto.User;
import cs353.proje.usecases.loginregister.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    public Response login(User loginInfo)
    {
        User userInfo = loginRepository.login(loginInfo);
        if (userInfo != null)
            return new Response(true, "Login is successful", userInfo);
        else
            return new Response(false, "Login is unsuccessful", null);
    }

    public Response registerCustomer(Customer customerRegisterInfo) {
        int user_id = loginRepository.addUser(customerRegisterInfo);
        if (user_id < 0)
        {
            return new Response(false, "Registration is unsuccessful", null);
        }
        else
        {
            customerRegisterInfo.setUserId(user_id);
            int result = loginRepository.addCustomer(customerRegisterInfo);
            if (result > 0)
                return new Response(true, "Registration is successful", null);
            else
            {
                return new Response(false, "Registration is unsuccessful", null);
                //Might need to delete the inserted user tuple here.
            }
        }
    }

    public Response registerCourier(Courier courierRegisterInfo) {
        int user_id = loginRepository.addUser(courierRegisterInfo);
        if (user_id < 0)
        {
            return new Response(false, "Registration is unsuccessful", null);
        }
        else
        {
            courierRegisterInfo.setUserId(user_id);
            int result = loginRepository.addCourier(courierRegisterInfo);
            if (result > 0)
                return new Response(true, "Registration is successful", null);
            else
            {
                return new Response(false, "Registration is unsuccessful", null);
                //Might need to delete the inserted user tuple here.
            }
        }
    }

    public Response registerRestaurantOwner(RestaurantOwner restaurantOwnerRegisterInfo) {
        int user_id = loginRepository.addUser(restaurantOwnerRegisterInfo);
        if (user_id < 0)
        {
            return new Response(false, "Registration is unsuccessful", null);
        }
        else
        {
            int result = loginRepository.addEmptyRestaurant(user_id);
            if (result > 0)
                return new Response(true, "Registration is successful", null);
            else
            {
                return new Response(false, "Registration is unsuccessful", null);
                //Might need to delete the inserted user tuple here.
            }
        }
    }
}
