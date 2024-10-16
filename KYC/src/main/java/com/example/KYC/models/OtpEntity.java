package com.example.KYC.models;

import com.example.KYC.enums.OtpType;
import com.example.KYC.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "otp")
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long OtpId;
    private String otp;
    private Status status;
    private OtpType otpType;
    private Long otpExpiryDurationInMinutes = 6L;
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date() ;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified = new Date();

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;
}
