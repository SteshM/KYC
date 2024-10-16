package com.example.KYC.services;


import com.example.KYC.dtos.RequestDto.*;
import com.example.KYC.dtos.ResponseDto.ResponseDTO;
import com.example.KYC.enums.OtpType;
import com.example.KYC.enums.Status;
import com.example.KYC.models.OtpEntity;
import com.example.KYC.models.UserEntity;
import com.example.KYC.models.UserTypeEntity;
import com.example.KYC.models.WalletAccEntity;
import com.example.KYC.utils.Utilities;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by SteshM
 * 22/08/20204
 * email: mbithestella0@gmail.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final DataService dataService;
    private final Utilities utilities;
    private final OtpService otpService;
    private final RestTemplate restTemplate;
    private final JsonParser jsonParser;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuditTrail auditTrail;
    private final SmsProducerService producerService;
    ModelMapper modelMapper = new ModelMapper();


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity credential = dataService.findByPhoneNumber(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();


        return new User(credential.getPhoneNumber(), credential.getPassword(), authorities);
    }


    /**
     *  USER-TYPES
     * @param userTypeDTO the request dto
     * @return response dto
     * @throws JsonProcessingException extends I/O exception
     * @throws SavingException when saving data to the db
     */

    public ResponseDTO createUserType(UserTypeDTO userTypeDTO) throws JsonProcessingException, SavingException {
        log.info("Yeeey");
        String username = AuthenticatedUser.username();
        UserTypeEntity userType = new UserTypeEntity();
        userType.setUserType(userTypeDTO.getUserType());
        log.info("about to save userType {}", new ObjectMapper().writeValueAsString(userType));
        try{

            auditTrail.createAuditTrail("Creating a userType","Created a usertype and saved",1,username);

        }catch (RuntimeException e){
            auditTrail.createAuditTrail("Creating a userType",e.getLocalizedMessage(),0,username);
            throw new SavingException(e.getLocalizedMessage());

        }
        var savedUserType = dataService.saveUserType(userType);
        var userTypeResDTO = modelMapper.map(savedUserType,UserTypeResDTO.class);
        return utilities.successResponse("Saved a userType",userTypeResDTO);
    }



    public ResponseDTO fetchUserTypes() throws SavingException {
        String username = AuthenticatedUser.username();
        List<UserTypeEntity> userTypeEntityList = dataService.fetchAll();
        List<UserTypeResDTO>userTypeResDTOS = userTypeEntityList.stream().map(userType -> {
                    return UserTypeResDTO.builder()
                            .userTypeId(userType.getUserTypeId())
                            .userType(userType.getUserType())
                            .build();
                })
                .toList();
        try{

            auditTrail.createAuditTrail("Fetching userTypes","Fetching userTypes from the db",1,username);

        }catch (RuntimeException e){
            auditTrail.createAuditTrail("Fetching userType",e.getLocalizedMessage(),0,username);
            throw new SavingException(e.getLocalizedMessage());

        }
        return utilities.successResponse("Fetched all userTypes",userTypeResDTOS);
    }




    /**
     * This is an account look up method
     * @param accountLookUpDTO requestDTO
     * @return response DTO
     *
     */
    public ResponseDTO accountLookUp(AccountLookUpDTO accountLookUpDTO){
        UserEntity user = dataService.findByPhoneNumber(accountLookUpDTO.getPhoneNo());
        if (user.getPhoneNumber()==null){
            return utilities.failedResponse(01,"Account does not exist",null);
        }
        return utilities.successResponse("Account exists",accountLookUpDTO);
    }


    /**
     * USER
     * @param userDTO request dto
     * @return response dto
     */


    public ResponseDTO register(UserDTO userDTO) {
        try{
            UserTypeEntity userType = dataService.findByUserType(userDTO.getUserType());
            if (userType == null){
                return utilities.failedResponse(01,"Invalid userType!",null);
            }
            UserEntity user = modelMapper.map(userDTO,UserEntity.class);
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setFullName(userDTO.getFullName());
            List<String>prefix = new ArrayList<>();
            prefix.add("TA");
            WalletDTO walletDTO = WalletDTO.builder()
                    .prefix(prefix)
                    .host("0.0.0.0")
                    .currency("KES")
                    .geolocation("Home")
                    .channel_key("1223445Pl")
                    .channel("MOBILE")
                    .client_id("CHURCHILL")
                    .user_agent("other")
                    .user_agent_version("other")
                    .txntimestamp("17078903037011")
                    .customer_names(userDTO.getFullName())
                    .phone_number(userDTO.getPhoneNumber())
                    .build();
            log.info("wallet request {}",walletDTO);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set(HttpHeaders.AUTHORIZATION, "Basic ZjkwNzhlOTQ2MGIxOjlmMGE5MmZjLWJkZTMtNDZjOC1iZTM5LTkxZTg1OWI4ZDhmZg==");


            HttpEntity<WalletDTO> entity = new HttpEntity<>(walletDTO, headers);

            String response =  restTemplate.exchange("http://10.20.2.111:31141/api/service/MAAB2789-ACCOUNT-CREATION", HttpMethod.POST,entity, String.class).getBody();
            log.info("res {}",response);
            JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
            JsonObject data = jsonObject.get("data").getAsJsonObject();
            log.info("data {}",data);
            String accountNumber=data.get("account_number").getAsString();

            user.setAccountNumber(accountNumber);
            UserEntity savedUser = dataService.saveUser(user);
            log.info("saved user {}",savedUser);


            WalletAccEntity walletAcc = new WalletAccEntity();
            walletAcc.setWalletAcc(accountNumber);
            log.info("wallet {}",walletAcc);
            walletAcc.setUserEntity(savedUser);
            dataService.saveWalletAcc(walletAcc);

            //generate and save otp
            String otp = otpService.generateOTP(savedUser);
            log.info("otp {}",otp);
            OtpEntity otpEntity =new OtpEntity();
            otpEntity.setOtp(passwordEncoder.encode(otp));
            otpEntity.setUserEntity(savedUser);
            otpEntity.setStatus(Status.ACTIVE);
            otpEntity.setOtpType(OtpType.REGISTER);
            dataService.saveOTP(otpEntity);
            producerService.sendSms("Use this one time pin to verify your account: "+otp,userDTO.getPhoneNumber());
            //sendOTP via SMS
            var userProfileDTO = modelMapper.map(savedUser, UserProfileDTO.class);
            return utilities.successResponse("Registered successfully",userProfileDTO);
        }catch (Exception e){

            log.error("Caught an exception", e);
            var userEntity = dataService.findByPhoneNumber(userDTO.getPhoneNumber());
            var userProfileDTO = modelMapper.map(userEntity, UserProfileDTO.class);
            return utilities.failedResponse(01, "PhoneNo already exists", userProfileDTO);
        }

    }

    public ResponseDTO login(LoginDTO loginDTO) {
        // Retrieve user by phone number
        UserEntity user = dataService.findByPhoneNumber(loginDTO.getUsername());
        if (user==null){
            return utilities.failedResponse(01,"Account doesn't exist, proceed to register",null);
        }
        log.info("PhoneNo is {}", loginDTO.getUsername());
        // Check if user exists
        if (user != null) {// Verify password
            log.info("user {}",user.getPhoneNumber());
            if (passwordEncoder.matches(loginDTO.getPassword(),(user.getPassword()))) {
                // Create authorities
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                // Use a role instead of phone number for authority
                var userType = dataService.findByUserType(user.getUserType());
                userType.getRolesEntities().forEach((role)->{
                    authorities.add(new SimpleGrantedAuthority(role.getRole()));
                });

                //checks

                if (user.isSoftDelete()){
                    return utilities.failedResponse(01,"Account is deactivated , please contact support",null);
                }
                if (user.getLoginAttempts()>=3){
                    return utilities.failedResponse(01, "Account is blocked",null);
                }

                // Create User object for token generation
                CustomUserDetails customUserDetails = new CustomUserDetails(user.getPhoneNumber(), user.getPassword(), authorities);
                // Generate token
                String token = jwtUtil.generateToken(customUserDetails);
                // Prepare response
                Map<String, String> res = new HashMap<>();
                res.put("token", token);
                res.put("phoneNo", loginDTO.getUsername());

                return utilities.successResponse("Successfully logged in", res);
            } else {
                return utilities.failedResponse(01, "Password Mismatch", null);
            }
        } else {
            return utilities.failedResponse(01, "User not found", null);
        }

    }

    public ResponseDTO viewAll() {
        List<UserEntity>userEntityList = dataService.fetchAllUsers();
        List<UserProfileDTO> userProfileDTOS = userEntityList.stream()
                .map(userEntity -> {
                    return UserProfileDTO.builder()
                            .userId(userEntity.getUserId())
                            .email(userEntity.getEmail())
                            .dateOfBirth(userEntity.getDateOfBirth())
                            .fullName(userEntity.getFullName())
                            .phoneNumber(userEntity.getPhoneNumber())
                            .gender(userEntity.getGender())
                            .nationalId(userEntity.getNationalId())
                            .nationality(userEntity.getNationality())
                            .userType(userEntity.getUserType())
                            .build();
                })
                .toList();
        return utilities.successResponse("Successfully fetched all users from the db",userProfileDTOS);
    }

    public ResponseDTO updateUserDetails(long id, UserDTO userDTO) throws SavingException {
        String username = AuthenticatedUser.username();
        UserEntity user = dataService.findByUserId(id);
        user.setFullName(userDTO.getFullName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setNationalId(userDTO.getNationalId());
        user.setNationality(userDTO.getNationality());
        user.setGender(userDTO.getGender());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setUserType(userDTO.getUserType());
        try{

            auditTrail.createAuditTrail("Updating user","updating user and saving details",1,username);

        }catch (RuntimeException e){
            auditTrail.createAuditTrail("updating user",e.getLocalizedMessage(),0,username);
            throw new SavingException(e.getLocalizedMessage());

        }
        var updatedUserDetails = dataService.saveUser(user);
        var userProfileDTO = modelMapper.map(updatedUserDetails,UserProfileDTO.class);
        return utilities.successResponse("Updated user details successfully",userProfileDTO);

    }

    public ResponseDTO viewOne(long id) throws SavingException {
        String username = AuthenticatedUser.username();
        UserEntity user = dataService.findByUserId(id);
        var userProfileDTO = modelMapper.map(user,UserProfileDTO.class);
        try{

            auditTrail.createAuditTrail("fetching user","fetching a  user details from the db",1,username);

        }catch (RuntimeException e){
            auditTrail.createAuditTrail("fetching user",e.getLocalizedMessage(),0,username);
            throw new SavingException(e.getLocalizedMessage());

        }
        return utilities.successResponse("Fetched a single user from the database",userProfileDTO);
    }

    public ResponseDTO softDeleteUser(long id) throws SavingException {
        String username = AuthenticatedUser.username();
        UserEntity user = dataService.findByUserId(id);
        if (user == null){
            return utilities.failedResponse(01,"user not found",null);
        }
        UserEntity userEntity = dataService.findByUserIdAndSoftDeleteFalse(user.getUserId());
        userEntity.setSoftDelete(true);
        try{

            auditTrail.createAuditTrail("soft deleting a user","soft deleting  user and saving details",1,username);

        }catch (RuntimeException e){
            auditTrail.createAuditTrail("deleting a user",e.getLocalizedMessage(),0,username);
            throw new SavingException(e.getLocalizedMessage());

        }
        dataService.saveUser(userEntity);
        return utilities.successResponse("Deleted a user from the system",null);
    }

}


