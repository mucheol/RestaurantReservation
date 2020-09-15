package kr.co.fastcam.eatgo.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    List<Reservation> findALlByRestaurantId(Long restaurantId);

    Reservation save(Reservation reservation);
}
