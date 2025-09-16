package com.meli.inventory.repository;

import com.meli.inventory.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCustomerId(String customerId);

    @Query("SELECT r FROM Reservation r WHERE r.status = 'RESERVED' AND r.expiresAt < :now")
    List<Reservation> findExpiredReservations(@Param("now") LocalDateTime now);

}
