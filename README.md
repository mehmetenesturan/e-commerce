# E-Commerce Platform (Microservices Architecture)

This is a modern e-commerce platform built using microservices architecture with Spring Boot, Spring Cloud, and other cutting-edge technologies.

## 🏗️ Architecture Components

### Core Services
- **Eureka Server**: Service discovery and registration
- **Config Server**: Centralized configuration management
- **API Gateway**: Single entry point for all client requests
- **Auth Service**: Authentication and authorization (Keycloak integration)
- **Product Service**: Product catalog management
- **Order Service**: Order processing and management
- **Payment Service**: Payment processing
- **Notification Service**: Email and notification handling

### Technology Stack
- **Backend**: Spring Boot 3.2.3, Spring Cloud 2023.0.0
- **Database**: PostgreSQL
- **Message Broker**: Apache Kafka
- **Authentication**: Keycloak
- **Containerization**: Docker
- **Orchestration**: Kubernetes
- **CI/CD**: GitHub Actions
- **Monitoring**: Prometheus & Grafana

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.8+
- Docker and Docker Compose
- PostgreSQL
- Apache Kafka
- Keycloak

### Local Development Setup

1. Clone the repository:
```bash
git clone https://github.com/yourusername/e-commerce-platform.git
cd e-commerce-platform
```

2. Start required services using Docker Compose:
```bash
docker-compose up -d
```

3. Build the project:
```bash
mvn clean install
```

4. Start the services in the following order:
   - Config Server
   - Eureka Server
   - API Gateway
   - Other services

### Service Ports
- Eureka Server: 8761
- Config Server: 8888
- API Gateway: 8080
- Auth Service: 8081
- Product Service: 8082
- Order Service: 8083
- Payment Service: 8084
- Notification Service: 8085

## 📦 Project Structure
```
e-commerce-platform/
├── eureka-server/
├── config-server/
├── api-gateway/
├── auth-service/
├── product-service/
├── order-service/
├── payment-service/
└── notification-service/
```

## 🔒 Security
- OAuth2 and OpenID Connect for authentication
- JWT tokens for authorization
- Role-based access control
- Secure communication between services

## 📊 Monitoring
- Prometheus for metrics collection
- Grafana for visualization
- Spring Boot Actuator for health checks

## 🧪 Testing
- Unit tests with JUnit 5
- Integration tests with TestContainers
- API tests with Postman

## 📝 API Documentation
- Swagger/OpenAPI documentation available at `/swagger-ui.html` for each service

## 🤝 Contributing
1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details

## Product Service
- Keycloak ile merkezi kimlik doğrulama ve yetkilendirme entegre edilmiştir.
- `SecurityConfig.java` ile güvenlik ayarları yapılmıştır.
- `application.yml` dosyasına Keycloak ayarları eklenmiştir.
- Diğer mikroservisler (Order, Payment, Notification) de benzer yapı ve güvenlik ile kurulacaktır. "# e-commerce" 
