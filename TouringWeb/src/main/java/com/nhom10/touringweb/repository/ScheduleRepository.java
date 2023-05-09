package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,Long> {

    List<Schedule> getAllByIdTour(Long idTour);
}
