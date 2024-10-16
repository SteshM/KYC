package com.example.KYC.services;

import com.example.KYC.models.OtpEntity;
import com.example.KYC.models.UserEntity;
import com.example.KYC.utils.OtpGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private final DataService dataService;
    private final FTPService ftpService;
    private final PasswordEncoder passwordEncoder;
    private final SmsProducerService producerService;

    public String generateOTP(UserEntity userEntity){
        //generate a random number
        String otp = OtpGenerator.generate6DigitsOtp();
        return otp;

    }


    public Boolean verifyOtp(VerifyOtpDTO verifyOtpDTO , long id) {
        try {
            var otpEntity = dataService.findOtpByUserId(id);
            log.info("OTP Entity Returned:{}", otpEntity);
            String encodedOtp = otpEntity.getOtp();
            log.info("Encoded OTP is:{}. Proceed to compare it with the OTP fetched from the DB", encodedOtp);
            if(encodedOtp.equals(otpEntity.getOtp())){
                log.info("OTP matches. Proceed to check if it is expired");
                boolean isOtpExpired = isOtpExpired(otpEntity.getDateCreated());
                if(isOtpExpired){
                    log.info("The otp is expired");
                    return false;
                } else {
                    log.info("The otp is not expired proceed to invalidate and return true");
                    otpEntity.setStatus(Status.INVALID);
                    dataService.saveOTP(otpEntity);
                    UserEntity user = dataService.findByUserId(id);
                    String ftp = ftpService.generateFtp();

                    log.info("The Ftp is {}",ftp);
                    user.setPassword(passwordEncoder.encode(ftp));
                    dataService.saveUser(user);
                    producerService.sendSms("Use this first time pin to login: "+ftp, user.getPhoneNumber());
                    return true;
                }
            }else{
                log.info("OTP does not match - {}", encodedOtp);
            }
            return false;
        } catch (Exception e){
            log.error("Caught an exception:",e);
            return false;

        }

    }
    private boolean isOtpExpired(Date dateCreated){
        OtpEntity otpEntity = new OtpEntity();
        Date now = new Date();
        long duration = otpEntity.getOtpExpiryDurationInMinutes();
        log.info("About to check if date is expired given: Duration {} minutes. Date Created:{}. Current Date:{}",duration, dateCreated, now);
        // Create a Calendar instance and set it to the creation date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCreated);
        // Add the expiry duration in minutes to the creation date
        calendar.add(Calendar.MINUTE, Math.toIntExact(duration));
        // Get the expiry date from the calendar
        Date expiryDate = calendar.getTime();
        // Check if the current date is after the expiry date
        return now.after(expiryDate);
    }
}


