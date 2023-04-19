package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
@Controller
public class userController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/user/profile")
    public String getProfile(Model model) {
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        User user =  userRepository.getUserById(idUser);
        model.addAttribute("user",user);
        return "userEdit";
    }

    @PostMapping("/user/profile/update")
    public String update(@ModelAttribute("user") User user, Model model) {
        User user1 = userRepository.findByEmail(user.getEmail());
        if(user1 != null) {
            user1.setName(user.getName());
            user1.setAddress(user.getAddress());
            user1.setPhone(user.getPhone());
            user1.setDateOfBirth(user.getDateOfBirth());
            user1.setGender(user.getGender());
        }
        userRepository.save(user1);
        model.addAttribute("successful", "Update thành công!");
        return "userEdit";
    }
}