package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.ScheduleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleDetailRepository extends JpaRepository<ScheduleDetail,Long> {

    List<ScheduleDetail> getAllByIdSchedule(Long idSchedule);
}
