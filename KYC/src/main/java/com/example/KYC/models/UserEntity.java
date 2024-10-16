package com.example.KYC.models;
import com.example.KYC.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "users")
@ToString
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String fullName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    private String nationalId;
    private String gender;
    private Date dateOfBirth;
    private String nationality;
    private String username;
    private String password;
    private boolean firstTimeLogin = false;
    private Status status;
    private Date firstLogin = new Date();
    private Date lastLogin = new Date();
    private int LoginAttempts = 0;
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated = new Date();
    private String createdBy;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModified = new Date();
    private Date deletedOn = new Date();
    private String modifiedBy;
    private Date lastActivity = new Date();
    private String userType;

    private boolean softDelete = false;
    private String accountNumber;
}
