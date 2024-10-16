package com.example.KYC.utils;


import com.example.KYC.models.AuditTrailEntity;
import com.example.KYC.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class AuditTrail {
    private final DataService dataService;
    public void createAuditTrail( String action,String actionDescription,int isSuccessful,String username){
        AuditTrailEntity auditTrail = AuditTrailEntity.builder()
                .action(action)
                .actionDescription(actionDescription)
                .isSuccessful(isSuccessful)
                .username(username)
                .performedOn(Instant.now())
                .createdBy(username)
                .build();
        dataService.saveAuditTrail(auditTrail);

    }

}
