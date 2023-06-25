package com.nhom10.touringweb.controller.user;

import com.nhom10.touringweb.model.user.ImgAvatar;
import com.nhom10.touringweb.model.user.User;
import com.nhom10.touringweb.repository.ImgAvatarRepository;
import com.nhom10.touringweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class userController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ImgAvatarRepository imgAvatarRepository;

    @GetMapping("/user/profile")
    public String getProfile(Model model) {
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        User user = userRepository.getUserById(idUser);
        model.addAttribute("user", user);
        return "user_setting";
    }

    @PostMapping("/user/profile/update")
    public String update(@ModelAttribute("user") User user, Model model) {
        User user1 = userRepository.findByEmail(user.getEmail());
        if (user1 != null) {
            user1.setName(user.getName());
            user1.setAddress(user.getAddress());
            user1.setPhone(user.getPhone());
            user1.setDateOfBirth(user.getDateOfBirth());
            user1.setGender(user.getGender());
            model.addAttribute("isProfileUpdatePage", true);
            userRepository.save(user1);
            model.addAttribute("successful", "Cập nhật thông tin thành công!");

            return "user_setting";
        }
        model.addAttribute("isProfileUpdatePage", false);
        model.addAttribute("error", "Cập nhật thông tin không thành công!");

        return "user_setting";
    }

    @GetMapping("/user/name/")
    public String nameByEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            return user.getName();
        } else {
            return "";
        }
    }

    @PostMapping("/user/upload-avatar")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "uploadFailed";
        }
        if (file.getSize() > 1048576) {
            redirectAttributes.addFlashAttribute("message", "File size exceeds maximum permitted size of 1MB.");
            return "file exceeded size";
        }
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        System.out.println("file format: " + extension);
        String[] fileFormats = {"jpg", "png", "svg", "jpeg", "gif"};
        try {
            //lấy id user hiện tại
            int idUser = 0;
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                String username = authentication.getName();
                User user = userRepository.findByEmail(username);
                idUser = user.getId();
            }
            // Tạo đường dẫn tới thư mục chứa ảnh đại diện trên server
            String uploadDir = "src/main/resources/static/images/avatar";
            File dir = new File(uploadDir);

            // Kiểm tra xem thư mục lưu trữ avatar có tồn tại hay không, nếu không thì tạo mới
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // Lấy danh sách các tệp tin trong thư mục lưu trữ avatar
            File[] files = dir.listFiles();

            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

            // Tạo tên file mới
            String newFileName = idUser + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());

            ImgAvatar imgAvatar = imgAvatarRepository.getImgAvatarByIdUser(idUser);
            imgAvatarRepository.delete(imgAvatar);
            imgAvatarRepository.save(new ImgAvatar(idUser, newFileName));
            // Tạo đường dẫn tới file ảnh mới trên server
            Path newFilePath = uploadPath.resolve(newFileName);
            // Ghi ảnh đại diện vào đường dẫn mới
            Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);
            // Thông báo tải ảnh đại diện thành công
            redirectAttributes.addFlashAttribute("message", "You have successfully uploaded " + newFilePath + "!");
        } catch (IOException ex) {
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + "!");
        }
        return "redirect:/user/profile";
    }

    @GetMapping("/user/img/avatar")
    public String getImgAvatar() {
        int idUser = 0;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userRepository.findByEmail(username);
            idUser = user.getId();
        }
        ImgAvatar imgAvatar = imgAvatarRepository.getImgAvatarByIdUser(idUser);
        if (imgAvatar.getImg() ==null){
            imgAvatar.setImg("default.jpg");
            imgAvatarRepository.save(imgAvatar);
        }
        return imgAvatar.getImg();
    }

//    @PostMapping("/user/upload-avatar")
//    public String uploadFile(@RequestParam("avatar") MultipartFile avatar) throws Exception {
//        User user = null;
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated()) {
//            String username = authentication.getName();
//            user = userRepository.findByEmail(username);
//        }
//        String fileName = StringUtils.cleanPath(avatar.getOriginalFilename());
//        user.setAvatar("/avatars/" + fileName);
//        userRepository.save(user);
//        Path uploadDir = Paths.get("avatars");
//        try (InputStream inputStream = avatar.getInputStream()) {
//            Files.copy(inputStream, uploadDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//        }
//
//        return "redirect:/user/profile";
//    }

    public User getUserById (int idUser) {
        return userRepository.getUserById(idUser);
    }
    public String getImgAvatar(int idUser) {
        ImgAvatar imgAvatar = imgAvatarRepository.getImgAvatarByIdUser(idUser);
        if (imgAvatar.getImg() ==null){
            imgAvatar.setImg("default.jpg");
            imgAvatarRepository.save(imgAvatar);
        }
        return imgAvatar.getImg();
    }
}