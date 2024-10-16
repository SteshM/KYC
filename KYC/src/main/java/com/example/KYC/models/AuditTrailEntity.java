package com.example.KYC.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Setter
@Getter
@Builder
@Table(name = "auditTrail")
public class AuditTrailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int auditTrailId;

    @Column(name = "action", nullable = false)
    private String action;
    @Lob
    @Column(name = "action_description", columnDefinition = "TEXT", nullable = false)
    private String actionDescription;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "performed_on")
    private Instant performedOn;

    @Column(name = "deleted_by")
    private String deletedBy;

    private String username;

    @Column(name = "status")
    private int isSuccessful;
}
