package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.LinkImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinkImgRepository extends JpaRepository<LinkImg,Long> {
    LinkImg getLinKImgByIdTourAndLevel(Long idTour, int level);

    List<LinkImg> getAllByIdTour(Long idTour);
}
