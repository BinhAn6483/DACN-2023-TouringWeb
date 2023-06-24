/**
 * @(#)AdminIndexController.java 2021/09/10.
 * <p>
 * Copyright(C) 2021 by PHOENIX TEAM.
 * <p>
 * Last_Update 2021/09/10.
 * Version 1.00.
 */
package com.nhom10.touringweb.controller.admin;


import java.util.*;

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
        for (Booking booking : oderRevenue) {
            countRevenue += booking.getTotalPrice();
        }

        List<Booking> toursWaiting = bookingRepository.findAllByStatusTour("Chờ khởi hành"); // tour đang dợi
        List<Tour> popularTours = getAllTourPopular();

        model.addAttribute("sumTour", sumTour.size());
        model.addAttribute("popularTours", popularTours);
        model.addAttribute("countUser", countUser);//"gọi html", truyền vào ở ngoài
        model.addAttribute("orderRevenue", countRevenue); // doanh thu
        model.addAttribute("toursWaiting", toursWaiting.size()); // tour đang đọi
        return "dashaboard-home";
    }

    @GetMapping("/admin/listUser")
    public String list(Model model) {
        List<User> customer = userRepository.getAllUserByRoleUser(); //danh sách khách hàng

        model.addAttribute("customer", customer);

        return "admin/dashboard-listUser";
    }


    public List<Tour> getAllTourPopular() {
        Map<Tour, Double> map = new HashMap<>();
        List<Booking> bookingList = bookingRepository.getAllBookingPopular();
        for (Booking booking : bookingList) {
            Tour tour = tourService.getTourById(booking.getIdTour());
            List<Booking> listBookingByIdTour = bookingRepository.getAllByIdTour(booking.getIdTour());
            double totalPrice=0;
            for (Booking booking1 : listBookingByIdTour){
                 totalPrice += booking1.getTotalPrice();
            }
            map.put(tour,totalPrice);
        }
        Map<Tour, Double> sortedMap = sortMapDescendingByValue(map);
        List<Tour> tourList = new ArrayList<>();
        for (Tour tour : sortedMap.keySet()) {
            tourList.add(tour);
        }
        return tourList;
    }

    public Map<Integer, Integer> getQuantityByIdTour(Long idTour) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Booking> bookingList = bookingRepository.getAllBookingPopular();
        for (Booking booking : bookingList) {
            int noAdults = 0, noChildren = 0;
            if (booking.getIdTour().equals(idTour)) {
                List<Booking> listBookingByIdTour = bookingRepository.getAllByIdTour(booking.getIdTour());
                for (Booking b : listBookingByIdTour) {
                    noAdults += b.getNoAdults();
                    noChildren += b.getNoChildren();
                }
                map.put(noAdults, noChildren);
            }

        }
        return map;
    }

    public double getTotalPrice(Long idTour) {
        double total = 0.0;
        List<Booking> bookingList = bookingRepository.getAllBookingPopular();
        for (Booking booking : bookingList) {
            if (booking.getIdTour().equals(idTour)) {
                List<Booking> listBookingByIdTour = bookingRepository.getAllByIdTour(booking.getIdTour());
                for (Booking b : listBookingByIdTour) {
                    total += b.getTotalPrice();
                }
            }
        }
        return total;
    }

    public Map<Tour, Double> sortMapDescendingByValue(Map<Tour, Double> map) {
        List<Map.Entry<Tour, Double>> entryList = new ArrayList<>(map.entrySet());

        Collections.sort(entryList, new Comparator<Map.Entry<Tour, Double>>() {
            @Override
            public int compare(Map.Entry<Tour, Double> entry1, Map.Entry<Tour, Double> entry2) {
                // Sắp xếp giảm dần dựa trên value
                return Double.compare(entry2.getValue(), entry1.getValue());
            }
        });

        Map<Tour, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Tour, Double> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }


}
