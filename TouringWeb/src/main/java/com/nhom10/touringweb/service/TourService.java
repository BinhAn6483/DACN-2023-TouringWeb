package com.nhom10.touringweb.service;

import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    @Autowired
    TourRepository tourRepository;

    public Tour getTourById(Long id) {
        return tourRepository.getByIdA(id);
    }

    public List<Tour> getAllTourByStartingPoint(String startingPoint) {
        return tourRepository.getAllByStartingPoint(startingPoint);
    }

    public List<Tour> getListTourFeatured(){
        return tourRepository.getListTourFeatured();
    }

    public List<Tour> getListTourNew(){
        return tourRepository.getListTourNew();
    }

    public List<Tour> getListTourDiscount(){
        return tourRepository.getListTourDiscount();
    }

    public List<Tour> getAll() {
        return tourRepository.findAll();
    }
}
