package com.example.KYC.controller;


import com.example.KYC.dtos.RequestDto.*;
import com.example.KYC.dtos.ResponseDto.ResponseDTO;
import com.example.KYC.services.OtpService;
import com.example.KYC.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/kyc")
public class UserController {
    private final UserService userService;
    private final OtpService otpService;

    @GetMapping("/type")
    public ResponseDTO createUserTyp(@RequestBody UserTypeDTO userTypeDTO) throws JsonProcessingException, SavingException {
        log.info("Creating a userType {}",userTypeDTO);
        return userService.createUserType(userTypeDTO);
    }

    @GetMapping("/types")
    public ResponseDTO fetchUserTypes() throws SavingException {
        return userService.fetchUserTypes();
    }

    @PostMapping("/account-lookUp")
    public ResponseDTO accountLookUp( @Valid @RequestBody AccountLookUpDTO accountLookUpDTO){
        return userService.accountLookUp(accountLookUpDTO);
    }

    @PostMapping("/register")
    public ResponseDTO register( @Valid @RequestBody UserDTO userDTO){
        return userService.register(userDTO);
    }
    @PostMapping("/{id}/verify-otp")
    public boolean verifyOtp(@Valid @RequestBody VerifyOtpDTO verifyOtpDTO, @PathVariable long id ){
        return otpService.verifyOtp(verifyOtpDTO,id);
    }
    @PostMapping("/login")
    public ResponseDTO login(@RequestBody LoginDTO loginDTO){
        return userService.login(loginDTO);
    }

    @GetMapping("/all")
    public ResponseDTO viewAll(){
        return userService.viewAll();
    }
    @PutMapping("/{id}")
    public ResponseDTO updateUser(@PathVariable long id,@RequestBody UserDTO userDTO) throws SavingException {
        return userService.updateUserDetails(id , userDTO);
    }
    @GetMapping("single/{id}")
    public ResponseDTO viewOne(@PathVariable long id) throws SavingException {
        return userService.viewOne(id);
    }

    @DeleteMapping("/del/{id}")
    public ResponseDTO softDelete(@PathVariable long id) throws SavingException {
        return userService.softDeleteUser(id);
    }

}
