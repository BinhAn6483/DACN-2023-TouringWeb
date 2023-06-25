package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.DepartureDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface DepartureDatesRepository extends JpaRepository<DepartureDates,Long> {
    DepartureDates getDepartureDatesByDateStartAndIdTour(Date date,Long idTour);
}
