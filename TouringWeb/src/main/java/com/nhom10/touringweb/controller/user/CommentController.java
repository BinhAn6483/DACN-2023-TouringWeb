package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.Comment;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.CommentRepository;
import com.nhom10.touringweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class CommentController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/comment/upload")
    public RedirectView uploadComment(@RequestParam("idTour") Long idTour, @RequestParam("star") int star, @RequestParam("content") String content) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy: HH:mm:ss");
        String createAt = now.format(formatter);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            if (user != null) {
                Comment comment = new Comment(user.getId(),idTour,star,content,createAt,0,0L);
                commentRepository.save(comment);
            } else {
                return new RedirectView("/login");
            }

        }else {
           return new RedirectView("/tour/detail/" + idTour);
        }
        return new RedirectView("/tour/detail/" + idTour);
    }
}
