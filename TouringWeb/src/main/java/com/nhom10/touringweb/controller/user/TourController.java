package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.model.user.WishList;
import com.nhom10.touringweb.repository.UserRepository;
import com.nhom10.touringweb.repository.WishListRepository;
import com.nhom10.touringweb.service.LinkImgService;
import com.nhom10.touringweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Date;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TourController {

    @Autowired
    TourService tourService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    LinkImgService linkImgService;


    @GetMapping
    public ModelAndView getAll() {
        List<Tour> list = tourService.getAll();
        List<Tour> featuredTours = (List<Tour>) getListTourFeatured();
        List<Tour> listTourNew = (List<Tour>) getListTourNew();
        List<Tour> listTourDiscount = (List<Tour>) getListTourDiscount();
        Map<String, Integer> topList = tourService.getTopDestinations();
        System.out.println(topList);
        ModelAndView mav = new ModelAndView("home");
        Map<String, Object> model = new HashMap<>();
        model.put("tours", list);
        model.put("featuredTours", featuredTours);
        model.put("listTourNew", listTourNew);
        model.put("listTourDiscount", listTourDiscount);
        model.put("listTopDestinations", topList);
        mav.addAllObjects(model);
        return mav;
    }

    @GetMapping("/tours-search")
    public ModelAndView getTourSearch(@RequestParam("location_name") String locationName, @RequestParam("start") String start, @RequestParam("end") String end) {
        ModelAndView mav = new ModelAndView("tour_search_sidebar");
        Map<String, Object> model = new HashMap<>();
        List<Tour> list = null;
        Date dateStart = null;
        Date dateEnd = null;
        if (!(start.equals("") && end.equals(""))) {

            dateStart = convertDate(start);
            dateEnd = convertDate(end);
        }

        if (!locationName.isEmpty() && !start.isEmpty() && !end.isEmpty()) {
            list = tourService.getToursBySearch(locationName, dateStart, dateEnd);
        } else if (!locationName.isEmpty() && start.equals("")) {
            System.out.println("kajfiae");
            list = tourService.getToursBySearch(locationName);
        } else if (!start.isEmpty() && !end.isEmpty()) {
            list = tourService.getToursBySearch(dateStart, dateEnd);
        }

        if (list != null && !list.isEmpty()) {
            model.put("listTourSearch", list);
        }

        mav.addAllObjects(model);
        return mav;
    }

    public Date convertDate(String start) {
        Date date;
        String[] s = start.split("/");
        System.out.println(s[0] + "\t" + s[1] + "\t" + s[2]);
        int day = Integer.parseInt(s[1]);
        int month = Integer.parseInt(s[0]) - 1;
        int year = Integer.parseInt(s[2]) - 1900;
        date = new Date(year, month, day);
        System.out.println(date);
        return date;
    }


    @GetMapping("/home")
    public ModelAndView home() {
        List<Tour> list = tourService.getAll();
        List<Tour> featuredTours = (List<Tour>) getListTourFeatured();
        List<Tour> listTourNew = (List<Tour>) getListTourNew();
        List<Tour> listTourDiscount = (List<Tour>) getListTourDiscount();
        Map<String, Integer> topList = tourService.getTopDestinations();
        System.out.println(topList);
        ModelAndView mav = new ModelAndView("home");
        Map<String, Object> model = new HashMap<>();
        model.put("tours", list);
        model.put("featuredTours", featuredTours);
        model.put("listTourNew", listTourNew);
        model.put("listTourDiscount", listTourDiscount);
        model.put("listTopDestinations", topList);
        mav.addAllObjects(model);
        return mav;
    }


    @GetMapping("/mainImgLink/{idTour}")
    public String getMainImgLink(@PathVariable("idTour") Long idTour) {
        return linkImgService.getNameImgByIdTour(idTour);
    }

    @GetMapping("/{idTour}")
    public ResponseEntity<Tour> getTourById(@PathVariable("idTour") Long idTour) {
        Tour tour = tourService.getTourById(idTour);

        return new ResponseEntity<Tour>(tour, HttpStatus.OK);
    }


    @GetMapping("/startingPoint/{startingPoint}")
    public ResponseEntity<List<Tour>> getListTourByStartingPoint(@PathVariable("startingPoint") String startingPoint) {
        try {
            List list = tourService.getAllTourByStartingPoint(startingPoint);
            if (list.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Tour>>(list, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/location/{location}")
    public ModelAndView getListTourByLocation(@PathVariable("location") String location) {
        ModelAndView mav = new ModelAndView("activity_search_topbar");
        Map<String, Object> model = new HashMap<>();
        try {

            List list = tourService.getAllTourByLocation(location);
            if (!list.isEmpty()) {
                model.put("listTourSearchLocation", list);
                mav.addAllObjects(model);
                return mav;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return mav;
        }
        return mav;
    }


    public List<Tour> getListTourFeatured() {
        try {
            List<Tour> featuredTours = tourService.getListTourFeatured();

            if (featuredTours.isEmpty()) {
                return null;
            }
            return featuredTours;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tour> getListTourNew() {
        try {
            List<Tour> featuredTours = tourService.getListTourNew();
            if (featuredTours.isEmpty()) {
                return null;
            }
            return featuredTours;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tour> getListTourDiscount() {
        try {
            List<Tour> featuredTours = tourService.getListTourDiscount();
            if (featuredTours.isEmpty()) {
                return null;
            }
            return featuredTours;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/featuredTour")
    public ModelAndView getListTourFeaturedNew() {
        ModelAndView mav = new ModelAndView("activity_search_topbar");
        Map<String, Object> model = new HashMap<>();
        try {
            List<Tour> featuredTours = tourService.getListTourFeatured();
            if (!featuredTours.isEmpty()) {
                model.put("listTourSearchLocation", featuredTours);
                mav.addAllObjects(model);
                return mav;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;

    }

    @GetMapping("/tours-new")
    public ModelAndView getListTourNew2() {
        ModelAndView mav = new ModelAndView("activity_search_topbar");
        Map<String, Object> model = new HashMap<>();
        try {
            List<Tour> featuredTours = tourService.getListTourNew();
            if (!featuredTours.isEmpty()) {
                model.put("listTourSearchLocation", featuredTours);
                mav.addAllObjects(model);
                return mav;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;

    }

    @GetMapping("/tours-discount")
    public ModelAndView getListTourDiscount2() {
        ModelAndView mav = new ModelAndView("activity_search_topbar");
        Map<String, Object> model = new HashMap<>();
        try {
            List<Tour> featuredTours = tourService.getListTourDiscount();
            if (!featuredTours.isEmpty()) {
                model.put("listTourSearchLocation", featuredTours);
                mav.addAllObjects(model);
                return mav;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;

    }


    @GetMapping("/getTopDestinations")
    public Map<String, Integer> getTopDestinations() {
        Map<String, Integer> topList = tourService.getTopDestinations();
        return topList;
    }

    @PostMapping("/addToWishlist/{idTour}")
    public String addToWishlist(@PathVariable Long idTour, Principal principal) {
        System.out.println("úm ba la si bùa");
        try {
            if (principal == null) {
                return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
            } else {
                String userEmail = principal.getName();
                User user = userRepository.findByEmail(userEmail);
                int idUser = user.getId();
                WishList wishList = wishListRepository.findByIdTourAndIdUser(idTour, idUser);
                if (wishList == null) {
                    wishListRepository.save(new WishList(idTour, idUser));
                }
                return "successfully";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
//    @GetMapping("/addToWishlist/{idTour}")
//    public String addToWishlist2(@PathVariable Long idTour, Principal principal) {
//        System.out.println("ủm ba la xi bùa");
//        try {
//            if (principal == null) {
//                return "redirect:/login"; // Nếu chưa đăng nhập, chuyển hướng đến trang đăng nhập
//            } else {
//                String userEmail = principal.getName();
//                User user = userRepository.findByEmail(userEmail);
//                int idUser = user.getId();
//                WishList wishList = wishListRepository.findByIdTourAndIdUser(idTour, idUser);
//                if (wishList == null) {
//                    wishListRepository.save(new WishList(idTour, idUser));
//                }
//                return "successfully";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "error";
//        }
//    }

//    @GetMapping("/user/name/")
//    public String nameByEmail() {
//        System.out.println("quần què");
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            String username = authentication.getName();
//            User user = userRepository.findByEmail(username);
//            return user.getName();
//        }else {
//            return "";
//        }
//    }
}
