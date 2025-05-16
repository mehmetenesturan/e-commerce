# Bildirim Servisi

E-ticaret platformunun bildirim yönetim servisi. Kullanıcılara çok kanallı (e-posta, SMS, push) bildirimler gönderir ve kullanıcı tercihlerine göre özelleştirilmiş bildirimler sunar.

## Özellikler

- Çok kanallı bildirim gönderimi (e-posta, SMS, push)
- Kullanıcı tercihlerine göre özelleştirilmiş bildirimler
- HTML formatında güzel görünümlü e-postalar
- Kafka üzerinden olay bazlı bildirimler
- Keycloak ile güvenlik entegrasyonu
- Rol tabanlı yetkilendirme

## Kullanılan Teknolojiler

- Java 17
- Spring Boot 3.2.3
- Spring Data JPA
- Spring Security
- Spring Cloud
- PostgreSQL
- Kafka
- Keycloak
- Lombok
- Maven

## Başlangıç

### Gereksinimler

- Java 17
- Maven
- PostgreSQL
- Kafka
- Keycloak

### Kurulum

1. Projeyi klonlayın:
```bash
git clone https://github.com/your-username/e-commerce.git
cd notification-service
```

2. Bağımlılıkları yükleyin:
```bash
mvn clean install
```

3. `application.yml` dosyasını düzenleyin:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/notification_db
    username: your_username
    password: your_password
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: notification-service
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring.json.trusted.packages: com.ecommerce.notification.dto
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer

keycloak:
  auth-server-url: http://localhost:8080/auth
  realm: e-commerce
  resource: notification-service
  public-client: true
  principal-attribute: preferred_username
```

4. Uygulamayı başlatın:
```bash
mvn spring-boot:run
```

## Projeyi Açma ve Çalıştırma

### IDE ile Açma (IntelliJ IDEA)

1. IntelliJ IDEA'yı açın
2. `File -> Open` menüsünden projenin ana dizinini seçin
3. Maven projesi olarak açılmasını onaylayın
4. Bağımlılıkların yüklenmesini bekleyin
5. `src/main/java/com/ecommerce/notification/NotificationServiceApplication.java` dosyasını açın
6. Sağ tıklayıp `Run 'NotificationServiceApplication'` seçeneğini seçin

### IDE ile Açma (Eclipse)

1. Eclipse'i açın
2. `File -> Import -> Maven -> Existing Maven Projects` seçeneğini seçin
3. Projenin ana dizinini seçin
4. `Finish` butonuna tıklayın
5. Bağımlılıkların yüklenmesini bekleyin
6. `src/main/java/com/ecommerce/notification/NotificationServiceApplication.java` dosyasını açın
7. Sağ tıklayıp `Run As -> Java Application` seçeneğini seçin

### Komut Satırından Çalıştırma

1. Terminal veya komut istemcisini açın
2. Projenin ana dizinine gidin:
```bash
cd path/to/notification-service
```

3. Maven ile derleyin:
```bash
mvn clean package
```

4. JAR dosyasını çalıştırın:
```bash
java -jar target/notification-service-0.0.1-SNAPSHOT.jar
```

### Docker ile Çalıştırma

1. Docker'ın yüklü ve çalışır durumda olduğundan emin olun
2. Projenin ana dizininde Docker imajını oluşturun:
```bash
docker build -t notification-service .
```

3. Docker konteynerini çalıştırın:
```bash
docker run -p 8080:8080 notification-service
```

### Gerekli Servislerin Çalıştırılması

Projenin düzgün çalışması için aşağıdaki servislerin çalışır durumda olması gerekir:

1. PostgreSQL:
```bash
# Docker ile
docker run -d -p 5432:5432 -e POSTGRES_DB=notification_db -e POSTGRES_USER=your_username -e POSTGRES_PASSWORD=your_password postgres:latest
```

2. Kafka:
```bash
# Docker ile
docker-compose up -d zookeeper kafka
```

3. Keycloak:
```bash
# Docker ile
docker run -d -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:latest start-dev
```

### Servisin Çalıştığını Doğrulama

1. Tarayıcıda `http://localhost:8080/actuator/health` adresine gidin
2. Aşağıdaki gibi bir yanıt almalısınız:
```json
{
    "status": "UP",
    "components": {
        "db": {
            "status": "UP"
        },
        "kafka": {
            "status": "UP"
        },
        "keycloak": {
            "status": "UP"
        }
    }
}
```

## API Uç Noktaları

### Bildirim Tercihleri

#### Kullanıcı İşlemleri
- `GET /api/v1/notification-preferences/me` - Kullanıcının kendi bildirim tercihlerini getirir
- `PUT /api/v1/notification-preferences/me` - Kullanıcının kendi bildirim tercihlerini günceller
- `DELETE /api/v1/notification-preferences/me` - Kullanıcının kendi bildirim tercihlerini siler

#### Yönetici İşlemleri
- `GET /api/v1/notification-preferences/{userId}` - Belirli bir kullanıcının bildirim tercihlerini getirir
- `PUT /api/v1/notification-preferences/{userId}` - Belirli bir kullanıcının bildirim tercihlerini günceller
- `DELETE /api/v1/notification-preferences/{userId}` - Belirli bir kullanıcının bildirim tercihlerini siler

### Bildirimler

#### Kullanıcı İşlemleri
- `GET /api/v1/notifications/me` - Kullanıcının kendi bildirimlerini getirir
- `GET /api/v1/notifications/me/{id}` - Kullanıcının belirli bir bildirimini getirir
- `PUT /api/v1/notifications/me/{id}/read` - Kullanıcının belirli bir bildirimini okundu olarak işaretler

#### Yönetici İşlemleri
- `GET /api/v1/notifications` - Tüm bildirimleri getirir
- `GET /api/v1/notifications/{id}` - Belirli bir bildirimi getirir
- `POST /api/v1/notifications` - Yeni bir bildirim oluşturur
- `PUT /api/v1/notifications/{id}` - Belirli bir bildirimi günceller
- `DELETE /api/v1/notifications/{id}` - Belirli bir bildirimi siler

## Bildirim Tipleri

- `ORDER_CONFIRMATION`: Sipariş onayı bildirimi
- `ORDER_STATUS_UPDATE`: Sipariş durumu güncelleme bildirimi
- `PAYMENT_CONFIRMATION`: Ödeme onayı bildirimi
- `PAYMENT_FAILURE`: Ödeme hatası bildirimi
- `MARKETING`: Pazarlama bildirimi

## Bildirim Kanalları

- E-posta: HTML formatında güzel görünümlü e-postalar
- SMS: (Yakında)
- Push: (Yakında)

## Kafka Konuları

- `order-events`: Sipariş olaylarını dinler
- `payment-events`: Ödeme olaylarını dinler

## Güvenlik

Servis, Keycloak ile entegre edilmiştir ve aşağıdaki rolleri destekler:

- `ROLE_USER`: Temel kullanıcı işlemleri
- `ROLE_ADMIN`: Tüm işlemler

## Geliştirme

### Yeni Bildirim Tipi Ekleme

1. `NotificationType` enum'una yeni tip ekleyin
2. `HtmlEmailTemplateService`'e yeni şablon ekleyin
3. `NotificationService`'de yeni tip için işleme mantığı ekleyin

### Yeni Bildirim Kanalı Ekleme

1. Yeni kanal için servis sınıfı oluşturun
2. `NotificationService`'e yeni kanal entegrasyonu ekleyin
3. `NotificationPreference` modeline yeni kanal tercihi ekleyin

## Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın. 