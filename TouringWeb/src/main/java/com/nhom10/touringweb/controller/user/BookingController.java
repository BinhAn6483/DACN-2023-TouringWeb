package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.Booking;
import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.BookingRepository;
import com.nhom10.touringweb.repository.UserRepository;
import com.nhom10.touringweb.service.LinkImgService;
import com.nhom10.touringweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    TourService tourService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LinkImgService linkImgService;

    @PostMapping("/confirm_booking")
    public ModelAndView bookingPage(@RequestParam("idTour") Long idTour, @RequestParam("dateStart") String dateStart) {
        ModelAndView mav = new ModelAndView("confirm_booking");
        Map<String, Object> model = new HashMap<>();
        Tour tour = tourService.getTourById(idTour);
        List<Date> listDateStart= tourService.getAllDateStart(idTour);
        model.put("tour", tour);
        model.put("date",dateStart);
        Booking booking =new Booking();
        model.put("booking",booking);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if (user != null) {
                model.put("user", user);
                System.out.println(user.toString());
                mav.addAllObjects(model);
                return mav;
            } else {
                RedirectView redirectView = new RedirectView("/login");
                return new ModelAndView(redirectView);
            }

        }else {
            RedirectView redirectView = new RedirectView("/login");
            return new ModelAndView(redirectView);
        }
    }

    @GetMapping("/user/history/detail/{idBooking}")
    public ModelAndView getBookingDetail(@PathVariable Long idBooking) {
        ModelAndView mav = new ModelAndView("user_history_detail");
        Booking booking = bookingRepository.getBookingById(idBooking);
        Tour tour= tourService.getTourById(booking.getIdTour());
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        User user = userRepository.getUserById(idUser);
        Map<String, Object> model = new HashMap<>();
        model.put("booking",booking);
        model.put("tour",tour);
        model.put("user",user);
        mav.addAllObjects(model);

        return mav;
    }

    @GetMapping("/user/history")
    public ModelAndView pageHistory(){
        ModelAndView mav = new ModelAndView("user_history");
        List<Booking> listAll = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<Booking> listDone = bookingRepository.findAllByStatusTour("Đã Đi");
        List<Booking> listWait = bookingRepository.findAllByStatusTour("Chờ khỏi hành");

        Map<String, Object> model = new HashMap<>();
        model.put("listAll",listAll);
        model.put("listDone",listDone);
        model.put("listWait",listWait);
        mav.addAllObjects(model);

        return mav;
    }

    public Tour getTourById(Long id) {
        return tourService.getTourById(id);
    }
    @GetMapping("/mainImgLinkByBooking/{idBooking}")
    public String getMainImgLinkByBooking(@PathVariable("idBooking") Long idBooking) {
        Booking booking = bookingRepository.getBookingById(idBooking);
        Tour tour = getTourById(booking.getIdTour());
        return linkImgService.getNameImgByIdTour(tour.getId());
    }


}
