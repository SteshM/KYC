package com.example.KYC.repos;

import com.example.KYC.models.UserTypeRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRoleRepo extends JpaRepository<UserTypeRoleEntity,Long> {
//    List<UserTypeRoleEntity> findByUserId(long userId);
//
//    UserTypeRoleEntity findByRoleId(long roleId);
}
