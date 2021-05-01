package cs353.proje.usecases.common.controller;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.dto.Review;
import cs353.proje.usecases.common.dto.ReviewFromCustomer;
import org.springframework.web.bind.annotation.*;

@RestController("ReviewController")
@RequestMapping("/review")
@CrossOrigin
public class ReviewController {

    //Returns the details of the review and also its response from the restaurant (if there is a response)
    //If there is no review for that order yet, return a response with (success = true) and (data = null)
    @GetMapping("/getReview/order_id={id}")
    public Response getReview(@PathVariable(value="id") int order_id)
    {
        Review review;
        return null;
    }

    //Customer makes a new review for the given order id
    //Only one review per order is possible.
    @PostMapping("/makeReview/order_id={id}")
    public Response makeReview(@PathVariable(value="id") int order_id,
                                @RequestBody ReviewFromCustomer review)
    {
        return null;
    }

    //Restaurant makes a response for the given review id
    //Only one response per review is possible.
    @PostMapping("/makeResponse/review_id={id}")
    public Response makeResponse(@PathVariable(value="id") int review_id,
                                 @RequestBody String response)
    {
        return null;
    }

}
