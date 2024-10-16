package com.example.KYC.repos;

import com.example.KYC.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserEntity,Long> {

    UserEntity findByUserId(long userId);

    UserEntity findByUserIdAndSoftDeleteFalse(long userId);

    UserEntity findByPhoneNumber(String username);

}