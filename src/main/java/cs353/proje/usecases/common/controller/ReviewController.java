package cs353.proje.usecases.common.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.dto.Review;
import cs353.proje.usecases.common.dto.ReviewFromCustomer;
import cs353.proje.usecases.common.dto.ReviewResponse;
import cs353.proje.usecases.common.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ReviewController")
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    //Returns the details of the review and also its response from the restaurant (if there is a response)
    //If there is no review for that order yet, return a response with (success = true) and (data = null)
    @GetMapping("/getReview/order_id={id}")
    public Response getReview(@PathVariable(value="id") int order_id)
    {
        return reviewService.getReview(order_id);
    }

    //Customer makes a new review for the given order id
    //Only one review per order is possible.
    @PostMapping("/makeReview/order_id={id}")
    public Response makeReview(@PathVariable(value="id") int order_id,
                                @RequestBody Review review)
    {
        return reviewService.makeReview(order_id, review);
    }

    //Restaurant makes a response for the given review id
    //Only one response per review is possible.
    @PostMapping("/makeResponse/review_id={id}")
    public Response makeResponse(@PathVariable(value="id") int review_id,
                                 @RequestBody ReviewResponse response)
    {
        return reviewService.makeResponse(review_id, response.getResponse());
    }

    @GetMapping("/getReviews/restaurant_id={id}")
    public Response getRestaurantReviews(@PathVariable(value = "id") int restaurant_id){
        return reviewService.getRestaurantReviews(restaurant_id);
    }
}
