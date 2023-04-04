package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tours")
public class TourController {

    @Autowired
    TourService tourService;

    @GetMapping("/{idTour}")
    public ResponseEntity<Tour> getTourById(@PathVariable("idTour") Long idTour){
        Optional<Tour> optionalTour = tourService.getTourById(idTour);
        if (optionalTour.isPresent()) {
            Tour tour = optionalTour.get();
            return new ResponseEntity<Tour>(tour, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/startingPoint/{startingPoint}")
    public ResponseEntity<List<Tour>> getListTourByStartingPoint(@PathVariable("startingPoint") String startingPoint) {
        try {
            List list = tourService.getAllTourByStartingPoint(startingPoint);
            if(list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Tour>>(list ,HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/featuredTour")
    public ResponseEntity<List<Tour>> getListTourFeatured() {
        try {
            Pageable pageable = PageRequest.of(0, 10, Sort.by("viewCount").descending());
            List<Tour> featuredTours = tourService.getListTourFeatured(pageable);
            if(featuredTours.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Tour>>(featuredTours ,HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
