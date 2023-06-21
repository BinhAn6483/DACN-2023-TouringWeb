/**
 * @(#)AdminIndexController.java 2021/09/10.
 *
 * Copyright(C) 2021 by PHOENIX TEAM.
 *
 * Last_Update 2021/09/10.
 * Version 1.00.
 */
package com.nhom10.touringweb.controller.admin;


import java.util.List;
import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.model.user.Booking;

import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.BookingRepository;
import com.nhom10.touringweb.repository.UserRepository;
import com.nhom10.touringweb.service.TourService;
import com.nhom10.touringweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * Class de hien thi trang chu quan tri
 *
 */
@Controller
public class AdminIndexController {

    @Autowired
    UserService userService;

    @Autowired
    TourService tourService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    /**
     * Hien thi trang chu cua giao dien nguoi dung
     *
     * @return trang admin index.html
     */
    @GetMapping("/admin/dashboard")
    public String index(Model model) {
        List<Tour> sumTour = tourService.getAll(); // danh sach tour
        int countUser = userRepository.countUserWithRoleUser(); // danh sach khach hang dăng ki
        List<Booking> oderRevenue = bookingRepository.findAllByStatusTour("Đã đi"); // doanh thu
        double countRevenue = 0.0;
        for(Booking booking : oderRevenue){
            countRevenue += booking.getTotalPrice();
        };

        List<Booking> toursWaiting = bookingRepository.findAllByStatusTour("Chờ khởi hành"); // tour đang dợi

        model.addAttribute("sumTour", sumTour.size());
        model.addAttribute("countUser", countUser);//"gọi html", truyền vào ở ngoài
        model.addAttribute("orderRevenue",countRevenue); // doanh thu
        model.addAttribute("toursWaiting",toursWaiting.size()); // tour đang đọi
         return "dashaboard-home";
    }
    @GetMapping({"show"})
    List<User> getAlluser(){return this.getAlluser();}
}
