package com.nhom10.touringweb.web;


import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.UserRepository;
import com.nhom10.touringweb.service.UserService;
import com.nhom10.touringweb.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;
    @Autowired
    UserRepository userRepository;

    public UserRegistrationController(UserService userService) {
        super();
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto, Model model, RedirectAttributes redirectAttrs) {
        User user = userRepository.findByEmail(registrationDto.getEmail());
        if (user != null) {
            model.addAttribute("error", "Email này đã tồn tại!");
            return "registration";
        } else {
            if (!isPasswordValid(registrationDto.getPassword())) {
                model.addAttribute("error", "Mật khẩu không hợp lệ!");
                return "registration";
            }
            userService.save(registrationDto);
            redirectAttrs.addFlashAttribute("message", "Bạn đã đăng ký thành công!");
            return "redirect:/login";
        }
    }


    public static boolean isPasswordValid(String password) {
        if (password == null) {
            return false;
        }
        // Kiểm tra độ dài của mật khẩu
        if (password.length() < 8 || password.length() > 15) {
            return false;
        }
        // Kiểm tra xem mật khẩu có chứa chữ in hoa, chữ thường, số và ký tự đặc biệt không
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,15}$";
        return password.matches(regex);
    }


}
