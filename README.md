# 🏥 Easy Booking – Doctor Appointment & Payment System

A **secure, scalable, and real-world healthcare appointment booking platform** built using **Spring Boot**, enabling patients to book doctor appointments, make online payments, and receive confirmations — while providing admins and staff with powerful management capabilities.

> Designed with **JWT Security**, **Razorpay Payments**, **Email Notifications**, and **QR-based appointment verification**.

---

## 🎯 Why Recruiters Care

- ✅ End-to-end **real-world business workflow**
- ✅ **JWT-based authentication & role-based access**
- ✅ **Payment gateway integration (Razorpay)**
- ✅ Clean **layered architecture** (Controller → Service → Repository)
- ✅ Production-ready **REST APIs**
- ✅ Demonstrates strong **Spring Boot & backend fundamentals**

---

## 👥 User Roles

- **Patient (User)**
- **Staff**
- **Admin**

Each role is secured using **JWT + Spring Security filters**.

---

## ✨ Features

### 👤 Patient Module
- Secure registration & login
- Browse doctors
- Book appointments
- Online payment via **Razorpay**
- Email confirmation
- QR code generation for appointment verification
- Appointment history tracking

---

### 🧑‍⚕️ Staff Module
- View assigned appointments
- Update appointment status
- Access patient details securely
- Appointment summary view

---

### 🛠 Admin Module
- Manage doctors (Add / Update / Delete)
- View all appointments & payments
- System analytics & summaries
- User & staff management
- Secure admin-only APIs

---

## 🔐 Security Features

- JWT Authentication
- Custom JWT Filter
- Role-Based Access Control (RBAC)
- Password encryption
- Secured REST endpoints
- Global exception handling

---

## 💳 Payment System

- Razorpay order creation
- Secure payment verification
- Payment status tracking
- Payment summary reports

---

## 📧 Utilities & Integrations

- Email notifications (Java Mail Sender)
- QR code generation
- Pagination support
- Centralized exception handling

---

## 🛠 Tech Stack

### Backend
- **Java 17**
- **Spring Boot**
- **Spring Security**
- **JWT**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Razorpay API**
- **Java Mail Sender**
- **Lombok**
- **Maven**

---

## 📁 Project Structure

```
Easy-booking/
├── src/main/java/com/pms/easy_book
│   ├── controller/
│   │   ├── public_controller/
│   │   ├── authenticated_controller/
│   │   ├── staff_controller/
│   │   └── admin_controller/
│   ├── service/
│   ├── repo/
│   ├── entity/
│   ├── dto/
│   ├── utils/
│   ├── config/
│   ├── filter/
│   └── exception/
├── src/main/resources/
│   ├── application.properties
│   ├── templates/
│   └── static/
├── pom.xml
└── README.md
```


---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven
- MySQL
- Razorpay Account

---

### Installation

#### Clone the repository
```
git clone https://github.com/R2004I/OPD-Management-System.git
cd Easy-booking
```

#### Configure Environment Variables
```
spring.datasource.url=jdbc:database url
spring.datasource.username=database username
spring.datasource.password=your_password

jwt.secret=your_jwt_secret

razorpay.key=your_key
razorpay.secret=your_secret

spring.mail.username=your_email
spring.mail.password=your_email_password
```

#### Run the application
```
mvn spring-boot:run
```
#### Application will start at:
```
http://localhost:8080
```

---

### API Overview

#### Authentication
```
POST /auth/signup-user
POST /public/signup-admin
POST /public/login
```
#### Patient APIs
```
GET  authenticated/details
POST authenticated/create-new-appointment
GET  authenticated/appointments/my
POST authenticated/payment/create-order
```
#### Hospital Staff APIs
```
POST staff/verify
GET staff/today/appointment
GET staff/ofDate/getAll
```
#### Admin APIs
```
POST admin/register-new-doctor
GET admin/api/get/live/data
GET admin/appointment
DELETE admin/appointment/delete
```

---

## 📊 Business Logic Covered

- **Appointment lifecycle management**
- **Payment → confirmation → verification flow**
- **Enum-based status transitions**
- **Pagination for large datasets**
- **Secure DTO-based data exposure**

---

## 🚀 Future Enhancements

- **Real-time notifications (WebSocket)**
- **Slot-based appointment booking**
- **Frontend (React / Angular)**
- **Analytics dashboard**

---

## 👨‍💻 Author

Ritam Sahoo
Java Backend Developer | Spring Boot | REST APIs | System Design
Built with ❤️ using Spring Boot and real-world backend engineering practices



