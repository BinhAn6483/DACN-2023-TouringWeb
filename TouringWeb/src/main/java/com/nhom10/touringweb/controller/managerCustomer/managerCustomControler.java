package com.nhom10.touringweb.controller.managerCustomer;

import com.nhom10.touringweb.model.user.Booking;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.BookingRepository;
import com.nhom10.touringweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class managerCustomControler {
    @Autowired
    UserRepository managerCustomRepository;
//    @GetMapping("/managerCustom")
//    public String showLoginPage() {
//
//        return "managerCustom";
//    }
    @GetMapping("/admin/managerCustom")
    public String getListCustom(Model model) {
        Collection<User> userList =managerCustomRepository.findAll();
        model.addAttribute("userList",userList);
        return "managerCustom";
    }
    @Autowired
    private BookingRepository bookingRepository;
    @GetMapping("/admin/managerCustomDetai/{userId}")
    public ModelAndView getCustomDetailById(@PathVariable int userId) {

        ModelAndView mav = new ModelAndView("managerCustomDetail");

        Collection<Booking> userList = bookingRepository.findByUserId(userId);
        Optional<User> user =  managerCustomRepository.findById(userId);
        List<Booking> listDone = bookingRepository.findByUserIdAndStatusTour(userId,"Đã Đi");
        List<Booking> listWait = bookingRepository.findByUserIdAndStatusTour(userId,"Chờ khởi hành");
        Map<String, Object> model = new HashMap<>();
        model.put("nameT",user);
        model.put("userList",userList);
        model.put("listDone",listDone);
        model.put("listWait",listWait);
        mav.addAllObjects(model);
        return mav;
    }





}
