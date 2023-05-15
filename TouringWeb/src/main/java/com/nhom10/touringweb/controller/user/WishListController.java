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

@Controller
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

    @GetMapping("/addToWishlist/{idTour}")
    public ResponseEntity<String> addToWishlist(@PathVariable Long idTour) {
        System.out.println("dã vào okeeeee.....");
        try {
            int idUser = 0;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bạn cần phải đăng nhập để thêm vào danh sách yêu thích.");
            } else {
                String username = authentication.getName();
                User user = userRepository.findByEmail(username);
                idUser = user.getId();
                WishList wishList = wishListRepository.findByIdTourAndIdUser(idTour, idUser);
                if (wishList == null) {
                    wishListRepository.save(new WishList(idTour, idUser));
                    return ResponseEntity.ok("Sản phẩm đã được thêm vào danh sách yêu thích.");
                } else {
                    return ResponseEntity.badRequest().body("Sản phẩm đã tồn tại trong danh sách yêu thích.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Bạn cần đăng nhập trước khi thêm vào danh sách yêu thích.");
        }
    }



}
