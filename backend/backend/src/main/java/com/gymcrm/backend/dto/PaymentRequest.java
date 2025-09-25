package com.gymcrm.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Long memberId;
    private Long planId;
    private Double amount;
    private String paymentMethod;
    private String status;
}