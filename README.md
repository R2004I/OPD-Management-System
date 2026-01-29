🏥 Easy Booking – Doctor Appointment & Payment System
A secure, scalable, and real-world healthcare appointment booking platform built using Spring Boot, enabling patients to book doctor appointments, make online payments, and receive confirmations — while providing admins and staff with powerful management capabilities.

Designed with clean architecture, JWT security, Razorpay payments, email notifications, and QR-based appointment verification.

🎯 Key Highlights (Why Recruiters Care)
✅ End-to-end real-world business workflow
✅ Secure JWT-based authentication & role-based access
✅ Online payment integration (Razorpay)
✅ Clean layered architecture (Controller → Service → Repository)
✅ Exception-safe, scalable, production-ready backend
✅ Demonstrates Spring Security, REST APIs, JPA, and integrations

👥 User Roles
Patient (User)

Staff

Admin

Each role has strictly separated access using JWT & Spring Security filters.

✨ Features
👤 Patient Module
✅ User registration & login (JWT authentication)

✅ Browse available doctors

✅ Book appointments with doctors

✅ Online payment using Razorpay

✅ Receive email confirmation

✅ QR code generation for appointment verification

✅ View appointment history & summary

🧑‍⚕️ Staff Module
✅ View assigned appointments

✅ Update appointment status

✅ Access patient details securely

✅ Appointment summary dashboard

🛠 Admin Module
✅ Manage doctors (Add / Update / Delete)

✅ View all appointments & payments

✅ System-wide summaries & analytics

✅ User & staff management

✅ Secure administrative APIs

🔐 Security Features
✅ JWT Authentication

✅ Custom JWT Filter

✅ Role-Based Access Control (RBAC)

✅ Password encryption

✅ Secure REST endpoints

✅ Centralized exception handling

💳 Payment System
✅ Razorpay order creation & verification

✅ Secure payment callback handling

✅ Payment status tracking

✅ Payment summary reports

📧 Communication & Utilities
📩 Email notifications on booking & payment

📦 QR Code generation for appointments

📄 Pagination utility for large datasets

⚠️ Global exception handling

🛠 Tech Stack
Backend
Java 17

Spring Boot

Spring Security

JWT Authentication

Spring Data JPA (Hibernate)

MySQL

Razorpay Payment Gateway

Java Mail Sender

Lombok

Tools & Concepts
RESTful APIs

DTO Pattern

Pagination

Exception Handling

Clean Architecture

Maven

📁 Project Structure
css
Copy code
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
🚀 Getting Started
Prerequisites
Java 17+

Maven

MySQL

Razorpay Account

Installation
Clone the repository
bash
Copy code
git clone https://github.com/your-username/easy-booking.git
cd Easy-booking
Configure Database & Secrets
Update application.properties:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/easy_booking
spring.datasource.username=root
spring.datasource.password=your_password

jwt.secret=your_jwt_secret
razorpay.key=your_key
razorpay.secret=your_secret

spring.mail.username=your_email
spring.mail.password=your_email_password
Run the Application
bash
Copy code
mvn spring-boot:run
Application runs at:

arduino
Copy code
http://localhost:8080
🧪 API Highlights
Authentication
POST /auth/register

POST /auth/login

Patient
GET /doctors

POST /appointments/book

GET /appointments/my

POST /payment/create-order

Admin
POST /admin/doctor

GET /admin/appointments

GET /admin/payments/summary

📊 Business Logic Covered
Appointment lifecycle management

Payment → confirmation → verification flow

Status transitions using enums

Pagination for large datasets

Secure data exposure using DTOs

🚢 Future Enhancements
🔔 Real-time notifications (WebSocket)

📅 Slot-based booking

📱 Frontend (React / Angular)

📊 Admin analytics dashboard

☁️ Cloud deployment (AWS)

📄 License
This project is open-source and intended for educational & portfolio purposes.

🤝 Contributing
Contributions are welcome!
Fork the repo, create a branch, and submit a PR 🚀

👨‍💻 Author
Ritam Sahoo
Java Backend Developer | Spring Boot | REST APIs | System Design

Built with ❤️ using Spring Boot & real-world engineering practices
