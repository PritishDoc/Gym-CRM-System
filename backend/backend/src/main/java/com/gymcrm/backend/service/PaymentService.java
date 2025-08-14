package com.gymcrm.backend.service;

import com.gymcrm.backend.dto.PaymentDTO;
import com.gymcrm.backend.dto.PaymentRequest;

import java.util.List;

public interface PaymentService {
    PaymentDTO createPayment(PaymentRequest paymentRequest);
    PaymentDTO getPaymentById(Long id);
    List<PaymentDTO> getPaymentsByUser(Long userId);
    List<PaymentDTO> getAllPayments();
    PaymentDTO updatePaymentStatus(Long id, String status);
}