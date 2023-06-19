package com.nhom10.touringweb.repository;

import com.nhom10.touringweb.model.user.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> getAllByIdTour(Long idTour);



}
