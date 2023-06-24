package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.Booking;
import com.nhom10.touringweb.model.user.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Booking getBookingById(Long id);


    List<Booking> findAllByStatusTour(String status);

    @Query("SELECT b FROM Booking b WHERE b.idUser = :userId")
    List<Booking> findByUserId(int userId);

    @Query("SELECT b FROM Booking b WHERE b.idUser = :userId and b.statusTour = :statusTour")
    List<Booking> findByUserIdAndStatusTour(int userId, String statusTour);

    @Query("SELECT b FROM Booking b GROUP BY b.idTour ORDER BY COUNT(b) DESC")
    List<Booking> getAllBookingPopular();

    List<Booking> getAllByIdTour(Long idTour);

}
