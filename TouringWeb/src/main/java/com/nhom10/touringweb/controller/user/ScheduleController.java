package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.*;
import com.nhom10.touringweb.repository.CommentRepository;
import com.nhom10.touringweb.repository.ScheduleDetailRepository;
import com.nhom10.touringweb.repository.ScheduleRepository;
import com.nhom10.touringweb.repository.TourRepository;
import com.nhom10.touringweb.service.LinkImgService;
import com.nhom10.touringweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.*;

@Controller
public class ScheduleController {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleDetailRepository scheduleDetailRepository;

    @Autowired
    TourService tourService;

    @Autowired
    TourRepository tourRepository;

    @Autowired
    LinkImgService linkImgService;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/tour/detail/{idTour}")
    public ModelAndView getTourDetail(@PathVariable("idTour") Long idTour) {
        ModelAndView mav = new ModelAndView("detaill");
        List<Date> listDateStart = tourService.getAllDateStart(idTour);
        Map<String, Object> model = new HashMap<>();
        Tour tour = tourService.getTourById(idTour);
        List<Schedule> scheduleList = getAllScheduleByIdTour(idTour);
        List<String> imgs = getAllLinkImgOfTour(idTour);
        List<DepartureDates> departureDates = getAllDateStartByIdTour(idTour);
        List<Comment> commentList = commentRepository.getAllByIdTour(idTour);

        int temp = 0;
        for (Comment c : commentList) {
            temp += c.getStar();
        }
        float averageStar = 0;
        averageStar = (float) temp / commentList.size();
        if (commentList.size() == 0) {
            averageStar = 5;
        }

        List<Tour> relatedTours = getRelatedTours(tour.getId());
        System.out.println("temp: " + temp + "\tAVG: " + averageStar + commentList.size());
        model.put("averageStar", averageStar);
        model.put("relatedTours", relatedTours);
        System.out.println("=================" + relatedTours);
        model.put("commentList", commentList);
        model.put("departureDates", departureDates);
        model.put("imgs", imgs);
        model.put("dates", listDateStart);
        System.out.println("-----------------------------" + departureDates.toString());
        model.put("tour", tour);
        model.put("scheduleList", scheduleList);
        mav.addAllObjects(model);
        return mav;
    }

    public int getCountStar(int star, Long idTour) {
        int result = 0;
        List<Comment> commentList = commentRepository.getAllByIdTour(idTour);
        for (Comment c : commentList) {
            if (c.getStar() == star) {
                result++;
            }
        }
        return result;
    }

    public List<Schedule> getAllScheduleByIdTour(Long idTour) {
        try {
            List list = scheduleRepository.getAllByIdTour(idTour);
            if (!list.isEmpty()) {
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<ScheduleDetail> getAllScheduleDetail(Long idSchedule) {
        try {
            List list = scheduleDetailRepository.getAllByIdSchedule(idSchedule);
            if (!list.isEmpty()) {
                return list;
            }
        } catch (Exception e) {
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

    public List<Tour> getRelatedTours(Long idTour) {
        List<Tour> result = new ArrayList<>();
        Tour tour = tourService.getTourById(idTour);
        List<Tour> list1 = tourRepository.getAllByLocation(tour.getLocation());
        List<Tour> list2 = tourRepository.getAllByTime(tour.getTime());
        if (list2 != null) {
            for (int i = 0; i < list2.size(); i++) {
                if (i < 4) {
                    if (tour.getId() != list2.get(i).getId())
                        result.add(list2.get(i));
                }
            }
        }
        if (list1 != null) {
            for (int i = 0; i < list1.size(); i++) {
                if (result.get(i).getId() != list1.get(i).getId() && result.get(i).getId() != tour.getId())
                    result.add(list1.get(i));
            }
        }
        Collections.shuffle(result);
        return (result.size() > 8) ? result.subList(0, 8) : result;
    }


    public Date getDateStartByIdTour(Long idTour) {
        List<Date> listDateStart = tourService.getAllDateStart(idTour);
        return listDateStart.get(0);
    }

    public int getQuantityOfTour(Long idTour) {
        List<DepartureDates> departureDates = getAllDateStartByIdTour(idTour);
        return departureDates.get(0).getQuantity();
    }

    public int getSizeCommentByIdTour(Long idTour) {
        List<Comment> commentList = commentRepository.getAllByIdTour(idTour);
        return commentList.size();
    }

    public float getAverageStarByIdTour(Long idTour) {
        List<Comment> commentList = commentRepository.getAllByIdTour(idTour);
        int temp = 0;
        for (Comment c : commentList) {
            temp += c.getStar();
        }
        float averageStar = 0;
        averageStar = (float) temp / commentList.size();
        if (commentList.size() == 0) {
            averageStar = 5;
        }
        return averageStar;
    }

}

