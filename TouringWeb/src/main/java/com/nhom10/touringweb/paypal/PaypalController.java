package com.nhom10.touringweb.paypal;

import com.nhom10.touringweb.model.user.Booking;
import com.nhom10.touringweb.model.user.DepartureDates;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.BookingRepository;
import com.nhom10.touringweb.repository.DepartureDatesRepository;
import com.nhom10.touringweb.repository.UserRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/paypal")
public class PaypalController {

    @Autowired
    private PaypalService paypalService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    DepartureDatesRepository departureDatesRepository;

    @PostMapping("/pay")
    public RedirectView pay(Booking booking, HttpServletRequest request) {
        System.out.println("booooooookiiiiiingggggg: " + booking);
        RedirectView redirectView = new RedirectView();
        try {
            String cancelUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/paypal/cancel";
            String successUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/paypal/success";

            // create payment
            Payment payment = paypalService.createPayment(booking, "USD", cancelUrl, successUrl);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    redirectView.setUrl(link.getHref());
                    return redirectView;
                }
            }
        } catch (PayPalRESTException e) {
            // handle exception
        }
        redirectView.setUrl("/paypal/cancel");
        return redirectView;
    }

    @GetMapping("/success")
    public RedirectView success(@RequestParam("paymentId") String paymentId,
                                @RequestParam("PayerID") String payerId) {
        RedirectView redirectView = new RedirectView();
        try {

            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                // payment success, do something here
                Booking booking = getBooking();
                System.out.println("Booking : " + booking.toString());
                int idUser = 0;
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null && authentication.isAuthenticated()) {
                    String username = authentication.getName();
                    User user = userRepository.findByEmail(username);
                    idUser = user.getId();
                }

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy: HH:mm:ss");
                String createAt = now.format(formatter);
                System.out.println("createAt : " + createAt);

                Booking booking1 = new Booking(idUser, booking.getIdTour(), booking.getNoAdults(), booking.getNoChildren(), booking.getTotalPrice(), booking.getDateStart(), booking.getPayment(), "Đã thanh toán", "Chờ khởi hành", createAt);
                bookingRepository.save(booking1);
                DepartureDates departureDates = departureDatesRepository.getDepartureDatesByDateStartAndIdTour(booking1.getDateStart(),booking1.getIdTour());
                System.out.println("date starts: " +departureDates.toString());
                departureDates.setQuantity(departureDates.getQuantity() - (booking1.getNoAdults() + booking1.getNoChildren()));
                departureDatesRepository.save(departureDates);
                String url = "/user/history/detail/" + Long.toString(booking1.getId());
                redirectView.setUrl(url);
                return redirectView;
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        String url = "/user/history/error";
        redirectView.setUrl(url);
        return redirectView;
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> cancel() {
        // handle cancel payment
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment cancelled");
    }

    public Booking getBooking() {
        return paypalService.getBooking();
    }
}



