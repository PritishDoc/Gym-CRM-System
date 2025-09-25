package com.gymcrm.backend.service.impl;

import com.gymcrm.backend.dto.PaymentDTO;
import com.gymcrm.backend.dto.PaymentRequest;
import com.gymcrm.backend.exception.PaymentNotFoundException;
import com.gymcrm.backend.model.Member;
import com.gymcrm.backend.model.Payment;
import com.gymcrm.backend.model.Plan;
import com.gymcrm.backend.model.User;
import com.gymcrm.backend.repository.MemberRepository;
import com.gymcrm.backend.repository.PaymentRepository;
import com.gymcrm.backend.repository.PlanRepository;
import com.gymcrm.backend.repository.UserRepository;
import com.gymcrm.backend.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final PlanRepository planRepository;

    @Override
    public PaymentDTO createPayment(PaymentRequest paymentRequest) {
        Member member = memberRepository.findById(paymentRequest.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Plan plan = planRepository.findById(paymentRequest.getPlanId())
                .orElseThrow(() -> new RuntimeException("Plan not found"));

        Payment payment = Payment.builder()
                .member(member)
                .plan(plan)
                .amount(paymentRequest.getAmount())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .transactionId(generateTransactionId())
                .paymentDate(LocalDateTime.now())
                .expiryDate(LocalDateTime.now().plusDays(plan.getDurationDays()))
                .status("Completed")
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        return mapToDTO(savedPayment);
    }

    @Override
    public PaymentDTO getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        return mapToDTO(payment);
    }

    @Override
    public List<PaymentDTO> getPaymentsByMember(Long memberId) {
        return paymentRepository.findByMemberId(memberId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDTO updatePaymentStatus(Long id, String status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with id: " + id));
        payment.setStatus(status);
        Payment updatedPayment = paymentRepository.save(payment);
        return mapToDTO(updatedPayment);
    }

    private PaymentDTO mapToDTO(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .memberId(payment.getMember().getId())
                .planId(payment.getPlan().getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .transactionId(payment.getTransactionId())
                .paymentDate(payment.getPaymentDate())
                .expiryDate(payment.getExpiryDate())
                .status(payment.getStatus())
                .build();
    }

    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
}

