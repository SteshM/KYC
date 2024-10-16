package com.example.KYC.repos;

import com.example.KYC.models.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepo extends JpaRepository<UserTypeEntity,Long> {
    UserTypeEntity findByUserTypeId(long userTypeId);

    UserTypeEntity findByUserType(String userType);
}

