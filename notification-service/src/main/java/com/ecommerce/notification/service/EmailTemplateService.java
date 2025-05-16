package com.ecommerce.notification.service;

import com.ecommerce.notification.dto.OrderEvent;
import com.ecommerce.notification.dto.PaymentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {

    public String createOrderConfirmationEmail(OrderEvent order) {
        return String.format("""
                Sayın Müşterimiz,
                
                Siparişiniz başarıyla oluşturuldu.
                
                Sipariş Detayları:
                Sipariş No: %d
                Toplam Tutar: %.2f TL
                Sipariş Durumu: %s
                Oluşturulma Tarihi: %s
                
                Siparişiniz için teşekkür ederiz.
                
                Saygılarımızla,
                E-Commerce Platform
                """,
                order.getOrderId(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getCreatedAt());
    }

    public String createOrderStatusUpdateEmail(OrderEvent order) {
        return String.format("""
                Sayın Müşterimiz,
                
                Siparişinizin durumu güncellendi.
                
                Sipariş Detayları:
                Sipariş No: %d
                Yeni Durum: %s
                Güncelleme Tarihi: %s
                
                Siparişinizi takip etmeye devam edebilirsiniz.
                
                Saygılarımızla,
                E-Commerce Platform
                """,
                order.getOrderId(),
                order.getOrderStatus(),
                order.getCreatedAt());
    }

    public String createPaymentConfirmationEmail(PaymentEvent payment) {
        return String.format("""
                Sayın Müşterimiz,
                
                Ödemeniz başarıyla alındı.
                
                Ödeme Detayları:
                Ödeme No: %d
                Sipariş No: %d
                Tutar: %.2f TL
                Ödeme Yöntemi: %s
                Ödeme Durumu: %s
                Tarih: %s
                
                Ödemeniz için teşekkür ederiz.
                
                Saygılarımızla,
                E-Commerce Platform
                """,
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getCreatedAt());
    }

    public String createPaymentFailedEmail(PaymentEvent payment) {
        return String.format("""
                Sayın Müşterimiz,
                
                Ödemeniz işlenirken bir hata oluştu.
                
                Ödeme Detayları:
                Ödeme No: %d
                Sipariş No: %d
                Tutar: %.2f TL
                Ödeme Yöntemi: %s
                Ödeme Durumu: %s
                Tarih: %s
                
                Lütfen ödemenizi tekrar deneyiniz veya farklı bir ödeme yöntemi kullanınız.
                
                Saygılarımızla,
                E-Commerce Platform
                """,
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getCreatedAt());
    }
} 