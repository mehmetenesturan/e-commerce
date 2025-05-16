package com.ecommerce.payment.service;

import com.ecommerce.payment.model.Payment;
import com.ecommerce.payment.model.PaymentStatus;
import com.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    @Transactional
    public Payment createPayment(Payment payment) {
        payment.setStatus(PaymentStatus.PENDING);
        Payment savedPayment = paymentRepository.save(payment);
        kafkaTemplate.send("payment-created", savedPayment);
        return savedPayment;
    }

    @Transactional
    public Payment updatePaymentStatus(Long id, PaymentStatus status) {
        Payment payment = getPaymentById(id);
        payment.setStatus(status);
        Payment updatedPayment = paymentRepository.save(payment);
        kafkaTemplate.send("payment-status-updated", updatedPayment);
        return updatedPayment;
    }

    @Transactional
    public Payment processPayment(Long id) {
        Payment payment = getPaymentById(id);
        // Burada gerçek ödeme işlemi simüle edilecek
        // Gerçek uygulamada burada ödeme sağlayıcısı entegrasyonu yapılacak
        payment.setStatus(PaymentStatus.COMPLETED);
        Payment processedPayment = paymentRepository.save(payment);
        kafkaTemplate.send("payment-processed", processedPayment);
        return processedPayment;
    }

    @Transactional
    public Payment refundPayment(Long id) {
        Payment payment = getPaymentById(id);
        if (payment.getStatus() != PaymentStatus.COMPLETED) {
            throw new RuntimeException("Only completed payments can be refunded");
        }
        payment.setStatus(PaymentStatus.REFUNDED);
        Payment refundedPayment = paymentRepository.save(payment);
        kafkaTemplate.send("payment-refunded", refundedPayment);
        return refundedPayment;
    }

    @Transactional
    public void deletePayment(Long id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);
        kafkaTemplate.send("payment-deleted", payment);
    }
} 