package com.nhom10.touringweb.repository;


import com.nhom10.touringweb.model.user.Tour;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour,Long> {
    List<Tour> getAllByStartingPoint(String startingPoint);

    @Query("SELECT t FROM Tour t WHERE t.viewCount > 100 ORDER BY t.viewCount DESC")
    List<Tour> getListTourFeatured(Pageable pageable);
}
