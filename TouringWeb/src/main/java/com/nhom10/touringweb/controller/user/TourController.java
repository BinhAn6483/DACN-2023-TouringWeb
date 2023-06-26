package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.Tour;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.model.user.WishList;
import com.nhom10.touringweb.repository.UserRepository;
import com.nhom10.touringweb.repository.WishListRepository;
import com.nhom10.touringweb.service.LinkImgService;
import com.nhom10.touringweb.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Date;

import java.util.*;
import java.util.stream.Collectors;

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
        List<String> locations = getAllLocation();
        System.out.println(topList);
        ModelAndView mav = new ModelAndView("home");
        Map<String, Object> model = new HashMap<>();
        model.put("tours", list);
        model.put("featuredTours", featuredTours);
        model.put("listTourNew", listTourNew);
        model.put("listTourDiscount", listTourDiscount);
        model.put("listTopDestinations", topList);
        model.put("locations", locations);

        mav.addAllObjects(model);
        return mav;
    }
    // Phương thức getTourSearch sẽ trả về 1 đối tượng ModelAndView với model name là "tourSearchSidebar"
    @GetMapping("/tours-search")
    public ModelAndView getTourSearch(HttpServletRequest request,@RequestParam("location_name") String locationName, @RequestParam("start") String start, @RequestParam("end") String end,@RequestParam(name = "page", defaultValue = "0") int page) {
        ModelAndView mav = new ModelAndView("tour_search_sidebar");
        Map<String, Object> model = new HashMap<>();
        List<String> locations = getAllLocation();
        model.put("locations", locations);

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
        Pageable pageable = PageRequest.of(page, 9);

        Page<Tour> list = null;
        Date dateStart = null;
        Date dateEnd = null;
        if (!(start.equals("") && end.equals(""))) {
            dateStart = convertDate(start);
            dateEnd = convertDate(end);
        }
        if (!locationName.isEmpty() && !start.isEmpty() && !end.isEmpty()) {
            list = tourService.getToursBySearch(locationName, dateStart, dateEnd, pageable); // paging
        } else if (!locationName.isEmpty() && start.equals("")) {
            list = tourService.getToursBySearch(locationName, pageable); // paging
        } else if (!start.isEmpty() && !end.isEmpty()) {
            list = tourService.getToursBySearch(dateStart, dateEnd, pageable); // paging
        }

        // Nếu danh sách kết quả tìm kiếm khác null và không trống, thì thêm danh sách vào model
        if (list != null && !list.isEmpty()) {
            model.put("size",list.getTotalElements());
            model.put("listTourSearch", list.getContent());
            model.put("totalPages", list.getTotalPages());
            model.put("currentPage", page);
            model.put("url", url);
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
        List<String> locations = getAllLocation();
        Map<String, Integer> topList = tourService.getTopDestinations();
        ModelAndView mav = new ModelAndView("home");
        Map<String, Object> model = new HashMap<>();
        model.put("tours", list);
        model.put("featuredTours", featuredTours);
        model.put("listTourNew", listTourNew);
        model.put("listTourDiscount", listTourDiscount);
        model.put("listTopDestinations", topList);
        model.put("locations", locations);
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
    public ModelAndView getListTourByLocation(HttpServletRequest request, @PathVariable("location") String location,
                                              @RequestParam(name = "page", defaultValue = "0") int page) {
        ModelAndView mav = new ModelAndView("activity_search_topbar");
        Map<String, Object> model = new HashMap<>();
        try {
            String url = request.getRequestURL() + "";
            int index = url.indexOf("?page=");
            if (index >= 0) {
                // &page= found, remove everything after it
                url = url.substring(0, index);
            }
            List<String> locations = getAllLocation();
            model.put("locations", locations);

            // sử dụng kiểu đối tượng Tour trong ví dụ này
            // thay vì biến toàn cục Page list, tạo một biến pageTour để xử lý dữ liệu phân trang
            Pageable pageable = PageRequest.of(page, 8);
            Page<Tour> pageTour = tourService.getAllTourByLocation(location, pageable);

            // sử dụng HashSet để lưu trữ các tour có id duy nhất
            Set<Long> uniqueTourIds = new HashSet<>();
            List<Tour> uniqueTours = new ArrayList<>();

            for (Tour tour : pageTour) {
                // kiểm tra xem id đã được thêm vào set chưa
                if (uniqueTourIds.add(tour.getId())) {
                    uniqueTours.add(tour);
                }
            }

            if (!uniqueTours.isEmpty()) {
                model.put("url", url);
                model.put("size", uniqueTours.size());
                model.put("category", "location");
                model.put("listTourSearchLocation", uniqueTours);
                model.put("totalPages", pageTour.getTotalPages());
                model.put("currentPage", page);

                mav.addAllObjects(model);
                return mav;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // không nên trả về mav ở đây khi gặp lỗi, mà trả về thông báo lỗi hoặc trang 404
        return new ModelAndView("error");
    }



    public List<Tour> getListTourFeatured() {
        try {
            Pageable pageable = PageRequest.of(0, 12, Sort.by("id").descending());
            Page<Tour> featuredTours = tourService.getListTourFeatured(pageable);

            if (featuredTours.isEmpty()) {
                return null;
            }
            return featuredTours.getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tour> getListTourNew() {
        try {
            Pageable pageable = PageRequest.of(0, 12, Sort.by("id").descending());
            Page<Tour> featuredTours = tourService.getListTourNew(pageable);
            if (featuredTours.isEmpty()) {
                return null;
            }
            return featuredTours.getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Tour> getListTourDiscount() {
        try {
            Pageable pageable = PageRequest.of(0, 12, Sort.by("id").descending());
            Page<Tour> featuredTours = tourService.getListTourDiscount(pageable);
            if (featuredTours.isEmpty()) {
                return null;
            }
            return featuredTours.getContent();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/featuredTour")
    public ModelAndView getListTourFeaturedNew(@RequestParam(name = "page",defaultValue = "0") int page) {
        ModelAndView mav = new ModelAndView("activity_search_topbar");
        Map<String, Object> model = new HashMap<>();
        int pageSize = 8;

        try {
            List<String> locations = getAllLocation();
            model.put("locations", locations);
            model.put("location", "Tour nổi bật");

            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
            Page<Tour> featuredTours = tourService.getListTourFeatured(pageable);
            if (!featuredTours.isEmpty()) {
                model.put("size",featuredTours.getTotalElements());
                model.put("category","featured");
                model.put("listTourSearchLocation", featuredTours.getContent());
                model.put("totalPages", featuredTours.getTotalPages()); //Thêm thuộc tính totalPages vào model
                model.put("currentPage", page); //Thêm thuộc tính currentPage vào model
                mav.addAllObjects(model);
                return mav;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }


    @GetMapping("/tours-new")
    public ModelAndView getListTourNew2(@RequestParam(name = "page",defaultValue = "0") int page) {
        ModelAndView mav = new ModelAndView("activity_search_topbar");
        Map<String, Object> model = new HashMap<>();
        int pageSize = 8;

        try {
            List<String> locations = getAllLocation();
            model.put("locations", locations);
            model.put("location", "Tour nổi bật");

            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
            Page<Tour> featuredTours = tourService.getListTourNew(pageable);
            if (!featuredTours.isEmpty()) {
                model.put("size",featuredTours.getTotalElements());
                model.put("category","new");
                model.put("listTourSearchLocation", featuredTours.getContent());
                model.put("totalPages", featuredTours.getTotalPages()); //Thêm thuộc tính totalPages vào model
                model.put("currentPage", page); //Thêm thuộc tính currentPage vào model
                mav.addAllObjects(model);
                return mav;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mav;
    }

    @GetMapping("/tours-discount")
    public ModelAndView getListTourDiscount2(@RequestParam(name = "page",defaultValue = "0") int page) {
        ModelAndView mav = new ModelAndView("activity_search_topbar");
        Map<String, Object> model = new HashMap<>();
        int pageSize = 8;

        try {
            List<String> locations = getAllLocation();
            model.put("locations", locations);
            model.put("location", "Tour nổi bật");

            Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
            Page<Tour> featuredTours = tourService.getListTourDiscount(pageable);
            if (!featuredTours.isEmpty()) {
                model.put("size",featuredTours.getTotalElements());
                model.put("category","discount");
                model.put("listTourSearchLocation", featuredTours.getContent());
                model.put("totalPages", featuredTours.getTotalPages()); //Thêm thuộc tính totalPages vào model
                model.put("currentPage", page); //Thêm thuộc tính currentPage vào model
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


    public List<String> getAllLocation() {
        return tourService.getAllLocation();
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

    public String getImgTopList(String location) {
        String result = "";
        switch (location) {
            case "Hà Nội":
                result = "hanoi.jpg";
                break;
            case "Hồ Chí Minh":
                result = "hochiminh.jpg";
                break;
            case "Miền Tây":
                result = "mientay.jpg";
                break;
            case "Quảng Bình":
                result = "quangbinh2.jpg";
                break;
            case "Đà Nẵng":
                result = "danang.jpg";
                break;
            case "Cà Mau":
                result = "camau.jpg";
                break;
            case "Phú Quốc":
                result = "phuquoc.jpg";
                break;
            default:
                result = "default.jpg";

        }
        return result;
    }

//    @GetMapping("/tours/filter")
//    public String filterProduct(@RequestParam("stars") String star,
//                                @RequestParam("times") String time,
//                                @RequestParam("prices") String price, Model model) {
//
//        String[] starStrings = star.split("/");
//        List<Integer> stars = Arrays.stream(starStrings)
//                .filter(s -> !s.isEmpty())
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
//
//        String[] timeStrings = time.split("/");
//        List<Integer> times = Arrays.stream(timeStrings)
//                .filter(s -> !s.isEmpty())
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
//
//        String[] priceStrings = price.split("/");
//        List<Integer> prices = Arrays.stream(priceStrings)
//                .filter(s -> !s.isEmpty())
//                .map(Integer::parseInt)
//                .collect(Collectors.toList());
//
//        System.out.println("brand[] " +times + "\tsizes[] " +stars);
//        List<Tour> list = null;
////        if (!time.isEmpty() && !brands.isEmpty()) {
////            if (priceSort == 1) {
////                list = productService.filterProductsDesc(sizes, brands);
////            } else {
////                list = productService.filterProductsAsc(sizes, brands);
////            }
////        } else if (!brands.isEmpty()) {
////            list = productService.filterProductsByBrandAndPrice(brands);
////            if (priceSort == 0) {
////                Collections.reverse(list);
////            }
////        } else if (!sizes.isEmpty()) {
////            list = productService.filterProductsExceptBrand(sizes);
////            if (priceSort == 0) {
////                Collections.reverse(list);
////            }
////        } else {
////            list = productService.filterProductsBYPrice();
////            if (priceSort == 0) {
////                Collections.reverse(list);
////            }
////        }
////
////        model.addAttribute("totalPrice", totalPrice);
////        model.addAttribute("carts",cartList);
//        model.addAttribute("products", list);
//        return "tour_search_sidebar";
//    }

}
