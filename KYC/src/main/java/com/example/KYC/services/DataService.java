package com.example.KYC.services;


import com.example.KYC.models.*;
import com.example.KYC.repos.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Setter
@Getter
public class DataService {
    private final RolesRepo rolesRepo;
    private final UserRepo userRepo;
    private final UserTypeRepo userTypeRepo;
    private final UserTypeRoleRepo userTypeRoleRepo;
    private final OtpRepo otpRepo;
    private final WalletRepo walletRepo;
    private final AuditTrailRepo auditTrailRepo;

    public UserEntity findByPhoneNumber(String username) {
        return userRepo.findByPhoneNumber(username);
    }

    public OtpEntity findOtpByUserId(long userId){
        return otpRepo.findByUserId(userId);
    }
    public UserTypeEntity findById(long userTypeId){
        return userTypeRepo.findByUserTypeId(userTypeId);
    }

    public UserTypeEntity saveUserType(UserTypeEntity userType) {
        return userTypeRepo.save(userType);
    }

    public List<UserTypeEntity> fetchAll() {
        return userTypeRepo.findAll();
    }

    public void saveOTP(OtpEntity otpEntity) {
        otpRepo.save(otpEntity);

    }

    public UserEntity saveUser(UserEntity user) {
        return userRepo.save(user);
    }


    public UserEntity findByUserId(long userId) {
        return userRepo.findByUserId(userId);
    }

    public List<UserEntity> fetchAllUsers() {
        return userRepo.findAll();
    }

    public List<RolesEntity> saveRoles(List<RolesEntity> rolesEntityList) {
        return rolesRepo.saveAll(rolesEntityList);
    }

    public UserTypeEntity findByUserType(String userType){
        return userTypeRepo.findByUserType(userType);
    }

    public UserEntity findByUserIdAndSoftDeleteFalse(long userId) {
        return userRepo.findByUserIdAndSoftDeleteFalse(userId);
    }

    public void saveWalletAcc(WalletAccEntity walletAcc) {
        walletRepo.save(walletAcc);

    }

    public void saveAuditTrail(AuditTrailEntity auditTrail) {
        auditTrailRepo.save(auditTrail);

    }



}