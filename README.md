# 🚀 Easy Booking – Doctor Appointment Management System
Easy Booking is a full-stack backend application built using Spring Boot that streamlines
doctor appointment scheduling, patient management, payments, and email notifications.
The system is designed with clean architecture, RESTful APIs, and scalable service layers, making it production-ready.

# 📌 Problem Statement
Managing doctor appointments manually leads to:

 ** Scheduling conflicts

 ** Poor patient experience

 ** No centralized payment or notification system

Easy Booking solves this by providing a secure, automated, and scalable appointment booking platform.

# Key Features

## 👨‍⚕️ Doctor Management
Add, update, and view doctor profiles

Fetch doctors by specialization

## 🧑‍🤝‍🧑 Patient Management
Patient registration & profile management

Secure user authentication

## 📅 Appointment Booking
Book appointments with available doctors

Prevent double bookings

View appointment history

## 💳 Payment Module
Appointment payment tracking

Payment status management

## 📧 Email Notification System
Email confirmation after booking

Modular email service design

## 🔐 Security
Spring Security integration

Role-based access control

Secure password handling

## ❗ Global Exception Handling
Centralized exception management

Clean and consistent API error responses

# 🏗️ Project Architecture
Controller  →  Service  →  Repository  →  Database

# Package Structure
com.pms

┣ controller     → REST API endpoints

┣ service        → Business logic

┣ repo           → JPA repositories

┣ model          → Entity classes

┣ dto            → Request & response DTOs

┣ exception      → Global exception handling

┗ security       → Authentication & authorization

# 🛠️ Tech Stack
Layer	            Technology

Language	          Java

Backend Framework	Spring Boot

ORM	            Spring Data JPA (Hibernate)

Security	      Spring Security

Database	         MySQL

Build Tool	         Maven

API Style	         REST

Utilities	        Lombok

Email Service	  Java Mail Sender

# 🔑 Core Modules Explained
## 🔹 Appointment Service
Validates doctor availability

Prevents overlapping bookings

Links patient, doctor, and payment

## 🔹 Payment Service
Tracks payment details

Updates appointment payment status

## 🔹 Email Service
Sends appointment confirmation emails

Easily extendable for reminders & alerts

## 🔹 User Authentication
Custom UserDetailsService implementation

Secure login & role handling

### 📡 Sample REST APIs
Method	 Endpoint	                    Description
POST	/api/patients/register	   Register a new patient

POST	/api/appointments/book	   Book an appointment

GET	    /api/doctors	           Fetch all doctors

GET	    /api/appointments/{id}	   Get appointment details

POST	/api/payments	           Process payment


# ⚙️ How to Run Locally
## Prerequisites
Java 17+

Maven

MySQL


## Steps
### Clone repository
git clone https://github.com/your-username/easy-booking.git

### Move into project directory
cd easy-booking

### Build project
mvn clean install

### Run application
mvn spring-boot:run

### Configure Database
Update application.properties:

spring.datasource.url=user's database url

spring.datasource.username=user's username for db connection

spring.datasource.password=user's password for db connection

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true










