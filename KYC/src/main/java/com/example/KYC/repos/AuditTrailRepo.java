package com.example.KYC.repos;


import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditTrailRepo extends JpaRepository<AuditTrailEntity,Long> {
}
