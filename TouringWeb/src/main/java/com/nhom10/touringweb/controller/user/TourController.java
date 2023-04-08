package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.service.LinkImgService;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TourController {

    @Autowired
    TourService tourService;

    @Autowired
    LinkImgService linkImgService;


    @GetMapping
    public ModelAndView getAll() {
        List<Tour> list= tourService.getAll();
        List<Tour> featuredTours = (List<Tour>) getListTourFeatured();
        List<Tour> listTourNew = (List<Tour>) getListTourNew();
        List<Tour> listTourDiscount = (List<Tour>) getListTourDiscount();
        ModelAndView mav = new ModelAndView("home");
        Map<String, Object> model = new HashMap<>();
        model.put("tours", list);
        model.put("featuredTours", featuredTours);
        model.put("listTourNew", listTourNew);
        model.put("listTourDiscount", listTourDiscount);
        mav.addAllObjects(model);
        return mav;
    }
    @GetMapping("/home")
    public ModelAndView home() {
        List<Tour> list= tourService.getAll();
        List<Tour> featuredTours = (List<Tour>) getListTourFeatured();
        List<Tour> listTourNew = (List<Tour>) getListTourNew();
        List<Tour> listTourDiscount = (List<Tour>) getListTourDiscount();
        ModelAndView mav = new ModelAndView("home");
        Map<String, Object> model = new HashMap<>();
        model.put("tours", list);
        model.put("featuredTours", featuredTours);
        model.put("listTourNew", listTourNew);
        model.put("listTourDiscount", listTourDiscount);
        mav.addAllObjects(model);
        return mav;
    }


    @GetMapping("/mainImgLink/{idTour}")
    public String getMainImgLink(@PathVariable("idTour") Long idTour){
        return linkImgService.getNameImgByIdTour(idTour);
    }

    @GetMapping("/{idTour}")
    public ResponseEntity<Tour> getTourById(@PathVariable("idTour") Long idTour){
        Tour tour = tourService.getTourById(idTour);

        return new ResponseEntity<Tour>(tour, HttpStatus.OK);
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
    public List<Tour> getListTourFeatured() {
        try {
            List<Tour> featuredTours = tourService.getListTourFeatured();
            if(featuredTours.isEmpty()) {
                return null;
            }
            return featuredTours;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tour> getListTourNew() {
        try {
            List<Tour> featuredTours = tourService.getListTourNew();
            if(featuredTours.isEmpty()) {
                return null;
            }
            return featuredTours;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tour> getListTourDiscount() {
        try {
            List<Tour> featuredTours = tourService.getListTourDiscount();
            if(featuredTours.isEmpty()) {
                return null;
            }
            return featuredTours;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
