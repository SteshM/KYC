package com.example.KYC.repos;

import com.example.KYC.models.WalletAccEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepo extends JpaRepository<WalletAccEntity, Long> {
}
