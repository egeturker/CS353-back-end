package cs353.proje.usecases.common.service;

import cs353.proje.usecases.common.dto.Response;
import cs353.proje.usecases.common.dto.Review;
import cs353.proje.usecases.common.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ReviewService{

    @Autowired
    ReviewRepository reviewRepository;

    public Response getReview(int orderId){
        List<Review> reviews = reviewRepository.getReview(orderId);

        if(reviews.size() == 1)
            return new Response(true, "Review returned", reviews.get(0));
        else
            return new Response(true, "Review not found for the order", null);
    }

    public Response makeReview(int orderId, Review review){
        if(reviewRepository.reviewExistsWithOrderId(orderId))
            return new Response(false, "Review for the order already exists", null);
        else{
            int reviewId = reviewRepository.makeReview(orderId, review);
            if(reviewId > 0)
                return new Response(true, "Review made", reviewId);
            else
                return new Response(false, "Unsuccessful", null);
        }
    }

    public Response makeResponse(int reviewId, String response){
        if(!reviewRepository.reviewExistsWithReviewId(reviewId))
            return new Response(false, "Review does not exist", null);
        if(reviewRepository.responseExists(reviewId))
            return new Response(false, "A response to this review already exists", null);

        if (reviewRepository.makeResponse(reviewId, response))
            return new Response(true, "Response made", null);
        else
            return new Response(false,"Response failed", null);
    }

    public Response getRestaurantReviews(int restaurantId){
        List<Review> reviews = reviewRepository.getRestaurantReviews(restaurantId);

        if(reviews.size() >= 1)
            return new Response(true, "Reviews for the restaurant returned", reviews);
        else
            return new Response(true, "No reviews found for the restaurant", Collections.emptyList());
    }
}
