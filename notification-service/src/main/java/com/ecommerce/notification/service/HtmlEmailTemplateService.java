package com.ecommerce.notification.service;

import com.ecommerce.notification.dto.OrderEvent;
import com.ecommerce.notification.dto.PaymentEvent;
import org.springframework.stereotype.Service;

@Service
public class HtmlEmailTemplateService {

    private static final String EMAIL_TEMPLATE = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #4CAF50; color: white; padding: 20px; text-align: center; }
                    .content { padding: 20px; background-color: #f9f9f9; }
                    .footer { text-align: center; padding: 20px; font-size: 12px; color: #666; }
                    .button { display: inline-block; padding: 10px 20px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; }
                    .details { margin: 20px 0; padding: 15px; background-color: white; border-radius: 5px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                    </div>
                    <div class="content">
                        %s
                        <div class="details">
                            %s
                        </div>
                        %s
                    </div>
                    <div class="footer">
                        <p>Bu e-posta E-Commerce Platform tarafından gönderilmiştir.</p>
                        <p>© 2024 E-Commerce Platform. Tüm hakları saklıdır.</p>
                    </div>
                </div>
            </body>
            </html>
            """;

    public String createOrderConfirmationEmail(OrderEvent order) {
        String title = "Sipariş Onayı";
        String greeting = "<p>Sayın Müşterimiz,</p><p>Siparişiniz başarıyla oluşturuldu.</p>";
        String details = String.format("""
                <h3>Sipariş Detayları:</h3>
                <p><strong>Sipariş No:</strong> %d</p>
                <p><strong>Toplam Tutar:</strong> %.2f TL</p>
                <p><strong>Sipariş Durumu:</strong> %s</p>
                <p><strong>Oluşturulma Tarihi:</strong> %s</p>
                """,
                order.getOrderId(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getCreatedAt());
        String closing = "<p>Siparişiniz için teşekkür ederiz.</p><p>Saygılarımızla,<br>E-Commerce Platform</p>";

        return String.format(EMAIL_TEMPLATE, title, greeting, details, closing);
    }

    public String createOrderStatusUpdateEmail(OrderEvent order) {
        String title = "Sipariş Durumu Güncellendi";
        String greeting = "<p>Sayın Müşterimiz,</p><p>Siparişinizin durumu güncellendi.</p>";
        String details = String.format("""
                <h3>Sipariş Detayları:</h3>
                <p><strong>Sipariş No:</strong> %d</p>
                <p><strong>Yeni Durum:</strong> %s</p>
                <p><strong>Güncelleme Tarihi:</strong> %s</p>
                """,
                order.getOrderId(),
                order.getOrderStatus(),
                order.getCreatedAt());
        String closing = "<p>Siparişinizi takip etmeye devam edebilirsiniz.</p><p>Saygılarımızla,<br>E-Commerce Platform</p>";

        return String.format(EMAIL_TEMPLATE, title, greeting, details, closing);
    }

    public String createPaymentConfirmationEmail(PaymentEvent payment) {
        String title = "Ödeme Onayı";
        String greeting = "<p>Sayın Müşterimiz,</p><p>Ödemeniz başarıyla alındı.</p>";
        String details = String.format("""
                <h3>Ödeme Detayları:</h3>
                <p><strong>Ödeme No:</strong> %d</p>
                <p><strong>Sipariş No:</strong> %d</p>
                <p><strong>Tutar:</strong> %.2f TL</p>
                <p><strong>Ödeme Yöntemi:</strong> %s</p>
                <p><strong>Ödeme Durumu:</strong> %s</p>
                <p><strong>Tarih:</strong> %s</p>
                """,
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getCreatedAt());
        String closing = "<p>Ödemeniz için teşekkür ederiz.</p><p>Saygılarımızla,<br>E-Commerce Platform</p>";

        return String.format(EMAIL_TEMPLATE, title, greeting, details, closing);
    }

    public String createPaymentFailedEmail(PaymentEvent payment) {
        String title = "Ödeme Hatası";
        String greeting = "<p>Sayın Müşterimiz,</p><p>Ödemeniz işlenirken bir hata oluştu.</p>";
        String details = String.format("""
                <h3>Ödeme Detayları:</h3>
                <p><strong>Ödeme No:</strong> %d</p>
                <p><strong>Sipariş No:</strong> %d</p>
                <p><strong>Tutar:</strong> %.2f TL</p>
                <p><strong>Ödeme Yöntemi:</strong> %s</p>
                <p><strong>Ödeme Durumu:</strong> %s</p>
                <p><strong>Tarih:</strong> %s</p>
                """,
                payment.getPaymentId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getCreatedAt());
        String closing = "<p>Lütfen ödemenizi tekrar deneyiniz veya farklı bir ödeme yöntemi kullanınız.</p><p>Saygılarımızla,<br>E-Commerce Platform</p>";

        return String.format(EMAIL_TEMPLATE, title, greeting, details, closing);
    }
} 