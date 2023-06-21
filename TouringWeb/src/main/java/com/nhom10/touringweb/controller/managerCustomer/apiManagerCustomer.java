package com.nhom10.touringweb.controller.managerCustomer;

import com.nhom10.touringweb.model.user.Booking;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.BookingRepository;
import com.nhom10.touringweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class apiManagerCustomer {
    @Autowired
    UserRepository managerCustomRepository;
//    @GetMapping("/managerCustomDetail/{id}")
//    public Optional<User> getManagerCustom(@PathVariable int id) {
//        Optional<User> user =  managerCustomRepository.findById(id);
//
//        return user;
//    }

    @GetMapping ("/deleteProduct")
    public  String deleteProduct(@RequestParam int id) {
        managerCustomRepository.deleteById(id);
        return "success";
    }
    @GetMapping("/getUser")
    public  List<User> getUser() {
        return managerCustomRepository.findAll();
    }
    @Autowired
    private BookingRepository bookingRepository;
    @GetMapping("/getUserByIdHistory/{userId}")
    public List<Booking> getBookingsByUserId(@PathVariable int userId) {
        return bookingRepository.findByUserId(userId);
    }
}
