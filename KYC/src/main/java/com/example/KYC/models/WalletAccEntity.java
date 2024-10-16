package com.example.KYC.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "walletAccount")
@ToString
public class WalletAccEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long walletAccId;
    private String walletAcc;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userEntity;

}

