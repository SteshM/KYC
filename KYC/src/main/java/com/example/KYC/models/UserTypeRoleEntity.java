package com.example.KYC.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "userTypeRole")
public class UserTypeRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userTypeRoleId;

    @ManyToOne
    @JoinColumn(name = "userTypeId")
    private UserTypeEntity userType;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private RolesEntity rolesEntity;
}
