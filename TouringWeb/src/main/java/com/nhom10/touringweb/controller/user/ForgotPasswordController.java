package com.nhom10.touringweb.controller.user;


import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.UserRepository;
import com.nhom10.touringweb.service.UserOtpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserOtpServiceImpl userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> processForgotPassword(@RequestParam("email") String email) throws MessagingException {

        // verify that email is valid and exists in the system
        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Email not found");
        }

        // generate OTP code and store it in the system
        String otpCode = generateOtpCode();
        userService.saveOtpCode(email, otpCode);
        System.out.println("otp controller:" + otpCode);

        // send OTP code to the user's email address
        sendOtpCodeByEmail(email, otpCode);

        return ResponseEntity.ok().body("OTP code sent to email");
    }


    // helper method to send OTP code by email
    private void sendOtpCodeByEmail(String email, String optCode) throws MessagingException {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("OTP Code");
        mailMessage.setText("Your OTP code is: " + optCode);

        javaMailSender.send(mailMessage);
    }

    // helper method to generate OTP code
    private String generateOtpCode() {
        int otpCode = (int) (Math.random() * 900000 + 100000);

        return String.valueOf(otpCode);
    }

    @GetMapping("/confirmOtp")
    public String confirmOtp(@RequestParam("email") String email, @RequestParam("otp") String otp) {
        boolean isValidOtpCode = userService.isValidOtpCode(email, otp);
        if (isValidOtpCode) {
            return "forgotPassword";
        } else
            return "error";
    }

    @GetMapping("/changNewPass")
    public ResponseEntity<?> changNewPass(@RequestParam("email") String email, @RequestParam("newPass") String newPass) {
        userService.updatePassword(email, newPass);
        return ResponseEntity.ok().body("successfully");
    }

    @GetMapping("/checkPass")
    public boolean check(@RequestParam("pass") String pass) {
        String email = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            email = authentication.getName();
        }
        String oldPass = userRepository.getPass(email);
        boolean isMatch = passwordEncoder.matches(pass, oldPass);
        return ((isMatch) ? true : false);
    }


    @GetMapping("/changePass")
    public String changPassModal(@RequestParam("oldPass") String oldPass, @RequestParam("newPass") String newPass,@RequestParam("otp") String otp) throws MessagingException {
        boolean checkPass = check(oldPass);
        if (checkPass) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String email = authentication.getName();
                User user = userRepository.findByEmail(email);
                String otpCode = generateOtpCode();
                userService.saveOtpCode(email, otpCode);
                sendOtpCodeByEmail(email, otpCode);
                if (confirmOtp(email, otp).equals("forgotPassword")) {
                    user.setPassword(passwordEncoder.encode(newPass));
                    userRepository.save(user);
                    return "Change pass successfully";
                }
            }
        }
        return "Change pass failed";
    }
}

