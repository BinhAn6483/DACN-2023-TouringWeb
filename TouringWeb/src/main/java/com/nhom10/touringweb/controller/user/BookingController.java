package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.Booking;
import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.BookingRepository;
import com.nhom10.touringweb.repository.UserRepository;
import com.nhom10.touringweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    TourService tourService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/confirm_booking")
    public String bookingPage(@RequestParam("idTour") Long idTour, @RequestParam("noAdults") int noAdults, @RequestParam("noChildren") int noChildren, Model model) {
        Tour tour = tourService.getTourById(idTour);
        List<Date> listDateStart= tourService.getAllDateStart(idTour);
        model.addAttribute("tour", tour);
        model.addAttribute("dates",listDateStart);
        model.addAttribute("noAdults", noAdults);
        model.addAttribute("noChildren", noChildren);
        Booking booking =new Booking();
        model.addAttribute("booking",booking);

        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        User user =  userRepository.getUserById(idUser);
        model.addAttribute("user",user);
        System.out.println(user.toString());

        return "confirm_booking";
    }


}
