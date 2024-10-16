package com.example.KYC.repos;

import com.example.KYC.models.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OtpRepo extends JpaRepository<OtpEntity,Long> {
    @Query(nativeQuery = true, value = "select * FROM otp WHERE user_id = ?")
    OtpEntity findByUserId(long userId);
}
