package com.example.KYC.repos;


import com.example.KYC.models.AuditTrailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailRepo extends JpaRepository<AuditTrailEntity,Long> {
}
