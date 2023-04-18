package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository  extends JpaRepository<Booking, Long> {
}
