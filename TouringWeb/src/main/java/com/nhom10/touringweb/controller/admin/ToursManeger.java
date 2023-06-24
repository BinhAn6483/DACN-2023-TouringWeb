package com.nhom10.touringweb.controller.admin;

import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.model.user.WishList;
import com.nhom10.touringweb.repository.TourRepository;
import com.nhom10.touringweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.HashMap;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Controller

public class ToursManeger {
    @Autowired
    TourService tourService;

    @Autowired
    TourRepository tourRepository;


    @GetMapping("/admin/tours-manage")
    public ModelAndView getListTourManage(HttpServletRequest request, @RequestParam(name = "page", defaultValue = "0") int page) {
        ModelAndView mav = new ModelAndView("admin/dashboard-listTour");
        Map<String, Object> model = new HashMap<>();
        int pageSize = 10;

        try {

            String queryString = request.getQueryString();
            if (queryString == null) {
                queryString = "";
            }
            String url = request.getRequestURL() + "?" + queryString;
            int index = url.indexOf("&page=");
            if (index >= 0) {
                // &page= found, remove everything after it
                url = url.substring(0, index);
            }
            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
            Page<Tour> listTours = tourService.getAll(pageable);
            if (!listTours.isEmpty()) {
                model.put("listTour", listTours.getContent());
                model.put("totalPages", listTours.getTotalPages()); //Thêm thuộc tính totalPages vào model
                model.put("currentPage", page); //Thêm thuộc tính currentPage vào model
                model.put("url", url);
                mav.addAllObjects(model);
                return mav;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    @GetMapping("/admin/delete/{idTour}")
    @ResponseBody
    public void removeTourByAdmin(@PathVariable Long idTour, HttpServletResponse response) {
        try {
                Tour tour = tourService.getTourById(idTour);
                tourRepository.delete(tour);
                response.sendRedirect("/admin/tours-manage");
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
