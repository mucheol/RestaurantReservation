package kr.co.fastcam.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findAllByRestaurantId(Long restaurantId);  //리뷰 목록 얻기

    List<Review> findAll();

    Review save(Review review);
}
