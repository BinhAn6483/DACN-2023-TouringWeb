package com.nhom10.touringweb.repository;


import com.nhom10.touringweb.model.user.Tour;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour,Long> {
    @Query("SELECT t FROM Tour t WHERE t.id=?1")
    Tour getByIdA(Long id);

    List<Tour> getAllByStartingPoint(String startingPoint);

    @Query("SELECT t FROM Tour t WHERE t.viewCount > 100 ORDER BY t.viewCount DESC")
    List<Tour> getListTourFeatured();

    @Query("SELECT t FROM Tour t WHERE t.sale = 0 AND t.viewCount<50")
    List<Tour> getListTourNew();

    @Query("SELECT t FROM Tour t WHERE t.sale >= 20")
    List<Tour> getListTourDiscount();
}
