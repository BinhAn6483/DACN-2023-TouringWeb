package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.*;
import com.nhom10.touringweb.repository.CommentRepository;
import com.nhom10.touringweb.repository.ScheduleDetailRepository;
import com.nhom10.touringweb.repository.ScheduleRepository;
import com.nhom10.touringweb.service.LinkImgService;
import com.nhom10.touringweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleDetailRepository scheduleDetailRepository;

    @Autowired
    TourService tourService;

    @Autowired
    LinkImgService linkImgService;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/tour/detail/{idTour}" )
    public ModelAndView getTourDetail(@PathVariable("idTour") Long idTour){
        ModelAndView mav = new ModelAndView("detaill");
        List<Date> listDateStart= tourService.getAllDateStart(idTour);
        Map<String, Object> model = new HashMap<>();
        Tour tour = tourService.getTourById(idTour);
        List<Schedule> scheduleList = getAllScheduleByIdTour(idTour);
        List<String> imgs= getAllLinkImgOfTour(idTour);
        List<DepartureDates> departureDates = getAllDateStartByIdTour(idTour);
        List<Comment> commentList = commentRepository.getAllByIdTour(idTour);
        model.put("commentList",commentList);
        model.put("departureDates",departureDates);
        model.put("imgs",imgs);
        model.put("dates",listDateStart);
        model.put("tour", tour);
        model.put("scheduleList",scheduleList);
        mav.addAllObjects(model);
        return mav;
    }

    public List<Schedule> getAllScheduleByIdTour(Long idTour) {
        try {
            List list = scheduleRepository.getAllByIdTour(idTour);
            if(!list.isEmpty()) {
                return list;
            }
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
        return null;

    }

    public List<ScheduleDetail> getAllScheduleDetail(Long idSchedule) {
        try {
            List list = scheduleDetailRepository.getAllByIdSchedule(idSchedule);
            if(!list.isEmpty()) {
                return list;
            }
        }
        catch (Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getAllLinkImgOfTour(Long idTour) {
        return linkImgService.getAllLinkImg(idTour);
    }

    public List<DepartureDates> getAllDateStartByIdTour(Long idTour) {
        return tourService.getAllDateStartByIdTour(idTour);
    }


}
