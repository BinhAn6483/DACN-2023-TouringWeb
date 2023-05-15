package com.nhom10.touringweb.service;

import com.nhom10.touringweb.model.user.DepartureDates;
import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;

@Service
public class TourService {

    @Autowired
    TourRepository tourRepository;

    public Tour getTourById(Long id) {
        return tourRepository.getById(id);
    }

    public List<Tour> getAllTourByStartingPoint(String startingPoint) {
        return tourRepository.getAllByStartingPoint(startingPoint);
    }

    public List<Tour> getAllTourByLocation(String location) {
        return tourRepository.getAllByLocation(location);
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

    public List<Tour> getToursBySearch(String location, Date start, Date end) {
        return tourRepository.getToursBySearch(location,start,end);
    }
    public List<Tour> getToursBySearch( Date start, Date end) {
        return tourRepository.getToursBySearch(start,end);
    }
    public List<Tour> getToursBySearch(String location) {
        return tourRepository.getToursBySearch(location);
    }

    public List<Date> getAllDateStart(Long idTour) {
        return tourRepository.getAllDateStart(idTour);
    }

    public List<DepartureDates> getAllDateStartByIdTour(Long idTour) {
        return tourRepository.getAllDateStartByIdTour(idTour);
    }
    public Map<String, Integer> getTopDestinations(){
        List<String> list = tourRepository.getTopDestinations();
        System.out.println("List of top: " + list);
        Map<String, Integer> result = new HashMap<>();
        if(!list.isEmpty()){
            for(int i =0; i<6 ; i++){
                List<Tour> tourList = new ArrayList<>();
                tourList = getAllTourByLocation(list.get(i));
                result.put(list.get(i), tourList.size() );
                System.out.println("Map list: " +list.get(i)+ "\t|" + tourList.size());
            }
        }
        return result;
    }

    public List<String> getAllLocation() {
        return tourRepository.getAllLocation();

    }
}
