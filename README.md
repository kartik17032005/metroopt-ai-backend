# 🚇 MetroOpt AI - Backend

Backend service for **MetroOpt AI**, an intelligent metro fleet allocation and decision support platform inspired by the Kochi Metro induction optimization process.

The application automates nightly train allocation by evaluating operational constraints and generating optimized deployment plans using Google's OR-Tools.

---

## ✨ Features

- 🔐 JWT Authentication & Authorization
- 🚆 Trainset Management
- 📋 Job Card Management
- ✅ Fitness Certificate Tracking
- 🧹 Cleaning Schedule Management
- 🎯 Optimization Engine using Google OR-Tools
- 📊 Analytics Dashboard APIs
- 📈 Mileage Tracking
- 🗄️ MySQL Database Integration
- ⚡ Redis Caching
- 🌐 RESTful APIs
- 📑 Swagger/OpenAPI Documentation

---

## 🛠 Tech Stack

| Category | Technology |
|-----------|------------|
| Language | Java 21 |
| Framework | Spring Boot |
| Database | MySQL |
| Cache | Redis |
| Security | Spring Security + JWT |
| Optimization | Google OR-Tools |
| Build Tool | Maven |
| API Testing | Postman |
| Documentation | Swagger/OpenAPI |

---

## 📂 Project Structure

```
src
├── config
├── controller
├── dto
├── entity
├── exception
├── repository
├── security
├── service
│   └── impl
├── optimization
├── analytics
└── util
```

---

## 🚀 Getting Started

### Prerequisites

- Java 21+
- Maven
- MySQL
- Redis
- Google OR-Tools

### Clone Repository

```bash
git clone <repository-url>
cd metroopt-ai-backend
```

### Configure Environment

Update `application.properties` with your configuration.

```properties
spring.datasource.url=YOUR_DATABASE_URL
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

jwt.secret=YOUR_SECRET_KEY

spring.data.redis.host=YOUR_REDIS_HOST
spring.data.redis.port=6379
```

### Run

```bash
mvn clean install

mvn spring-boot:run
```

Application starts at

```
http://localhost:8080
```

---

## 🔑 Authentication

The API uses JWT authentication.

Typical flow:

1. Register
2. Login
3. Receive JWT Token
4. Use the token in the Authorization header

```
Authorization: Bearer <JWT_TOKEN>
```

---

## 📚 Main Modules

- Authentication
- Dashboard
- Trainset Management
- Job Cards
- Fitness Certificates
- Cleaning Slots
- Branding Contracts
- Mileage Records
- Optimization Engine
- Analytics

---

## 🧠 Optimization Engine

MetroOpt AI uses **Google OR-Tools** to determine the optimal allocation of metro trainsets while considering:

- Fitness certificate validity
- Job card status
- Mileage balancing
- Cleaning schedules
- Branding priorities
- Operational constraints
- Inspection requirements

The generated allocation improves operational efficiency while maintaining maintenance compliance.

---

## 📖 API Documentation

Swagger UI

```
http://localhost:8080/swagger-ui/index.html
```


## 📸 Frontend Repository
https://github.com/kartik17032005/metroopt_ai_frontend
