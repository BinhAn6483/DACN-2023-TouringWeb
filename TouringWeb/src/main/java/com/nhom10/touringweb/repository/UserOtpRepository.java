package com.nhom10.touringweb.repository;


import com.nhom10.touringweb.model.user.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserOtpRepository extends JpaRepository<UserOtp, Integer> {

    @Query("SELECT u FROM UserOtp u WHERE u.email=?1 AND u.otpCode=?2")
    UserOtp findByEmailAndOtp(String email, String otpCode);

    void deleteByExpiryTime(LocalDateTime now);
    void deleteByEmail(String email);
}