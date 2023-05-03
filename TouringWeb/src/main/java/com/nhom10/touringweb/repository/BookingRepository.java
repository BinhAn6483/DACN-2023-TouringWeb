package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.Booking;
import com.nhom10.touringweb.model.user.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository  extends JpaRepository<Booking, Long> {

    Booking getBookingById(Long id);


    List<Booking> findAllByStatusTour(String status);

}
