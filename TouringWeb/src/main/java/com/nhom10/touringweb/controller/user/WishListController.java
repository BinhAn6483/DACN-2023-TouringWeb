package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.model.user.WishList;
import com.nhom10.touringweb.repository.UserRepository;
import com.nhom10.touringweb.repository.WishListRepository;
import com.nhom10.touringweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class WishListController {

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TourService tourService;

    @GetMapping("/wishlist")
    public ModelAndView getList() {
        ModelAndView mav = new ModelAndView("user_dashboard");
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        List<WishList> list = wishListRepository.getAllByIdUser(idUser);
        List<Tour> tourList = new ArrayList<>();
        if (!list.isEmpty()) {
            for (WishList w : list) {
                Tour tour = tourService.getTourById(w.getIdTour());
                tourList.add(tour);
            }
        }
        Map<String, Object> model = new HashMap<>();
        model.put("wishlist", tourList);
        mav.addAllObjects(model);
        return mav;
    }

//    @Transactional
//    @DeleteMapping("/deleteFromWishlist/{idTour}")
//    @ResponseBody
//    public ResponseEntity<String> removeFromWishlist(@PathVariable Long idTour, Principal principal) {
//        System.out.println("có vào được không");
//        try {
//            if (principal == null) {
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Chưa đăng nhập, trả về mã lỗi 401
//            } else {
//                String userEmail = principal.getName();
//                User user = userRepository.findByEmail(userEmail);
//                int idUser = user.getId();
//                WishList wishList = wishListRepository.findByIdTourAndIdUser(idTour, idUser);
//                wishListRepository.delete(wishList);
//                return ResponseEntity.ok("Xóa tour khỏi danh sách yêu thích thành công!");
//
//            }
//
//        } catch (
//                Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi xóa tour khỏi danh sách yêu thích!");
//        }
//    }

    @GetMapping("/deleteFromWishlist/{idTour}")
    @ResponseBody
    public void removeFromWishlist(@PathVariable Long idTour, Principal principal , HttpServletResponse response) {
        System.out.println("có vào được không");
        try {
            if (principal == null) {
                response.sendRedirect("/login");
            } else {
                String userEmail = principal.getName();
                User user = userRepository.findByEmail(userEmail);
                int idUser = user.getId();
                WishList wishList = wishListRepository.findByIdTourAndIdUser(idTour, idUser);
                wishListRepository.delete(wishList);
                response.sendRedirect("/user/wishlist");

            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }

}
