package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    WishList findByIdTourAndIdUser (Long idTour, int idUser);

    List<WishList> getAllByIdUser(int idUser);
}
