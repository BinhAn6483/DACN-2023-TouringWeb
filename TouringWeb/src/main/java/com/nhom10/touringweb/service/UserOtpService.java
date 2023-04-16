package com.nhom10.touringweb.service;

import com.nhom10.touringweb.model.user.User;
import org.springframework.stereotype.Service;

@Service
public interface UserOtpService {
    User findByEmail(String email);

    void saveOtpCode(String email, String otpCode);

    boolean isValidOtpCode(String email, String otpCode);

    void updatePassword(String email, String newPassword);
}
