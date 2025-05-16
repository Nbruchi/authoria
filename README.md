# 🔐 Authoria

![License](https://img.shields.io/badge/license-MIT-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-17-orange.svg)

## 📋 Overview

Authoria is a robust authentication and user management service built with Spring Boot. It provides secure user registration, authentication, and profile management with features like JWT-based authentication, email verification, and role-based access control.

## ✨ Features

- 🔒 **Secure Authentication** - JWT-based authentication system
- 👤 **User Management** - Complete CRUD operations for user profiles
- 📧 **Email Verification** - Two-step verification process for email addresses
- 🖼️ **Image Management** - Upload, update, and delete profile images (with Cloudinary integration)
- 🛡️ **Role-Based Access Control** - Different permission levels for regular users and administrators
- 🌐 **CORS Support** - Configured for frontend applications
- 🔄 **Token Blacklisting** - Secure logout mechanism

## 🛠️ Technologies

- **Spring Boot** - Application framework
- **Spring Security** - Authentication and authorization
- **JWT (JSON Web Tokens)** - Stateless authentication
- **JPA/Hibernate** - ORM for database operations
- **BCrypt** - Password hashing
- **Cloudinary** - Cloud-based image management
- **Jakarta Mail** - Email services

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- MySQL (or your preferred database)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/authoria.git
   cd authoria
   ```

2. **Configure application properties**
   
   Update `src/main/resources/application.properties` with your database and email settings.

3. **Build the application**
   ```bash
   ./mvnw clean install
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

## 🔌 API Endpoints

### Authentication

- **POST** `/clients/register` - Register a new user
- **POST** `/clients/login` - Authenticate and receive JWT token
- **POST** `/clients/logout` - Invalidate JWT token

### User Management

- **GET** `/clients` - Get all users (admin only)
- **GET** `/clients/{id}` - Get user by ID
- **PUT** `/clients/{id}` - Update user
- **DELETE** `/clients/{id}` - Delete user

### Email Verification

- **POST** `/clients/emails/{clientId}/verify-code` - Send verification code
- **POST** `/clients/emails/confirm-code` - Verify email with code
- **POST** `/clients/emails/{clientId}/verify-email` - Alternative verification method
- **POST** `/clients/emails/confirm-email` - Confirm email verification

### Image Management

- **POST** `/clients/{clientId}/images` - Upload profile image
- **GET** `/clients/{clientId}/images` - Get profile image
- **PUT** `/clients/{clientId}/images` - Update profile image
- **DELETE** `/clients/{clientId}/images` - Delete profile image

## 🔐 Security

- All endpoints except registration and login require authentication
- JWT tokens are used for stateless authentication
- Passwords are encrypted using BCrypt
- Role-based access control for administrative functions
- Users can only access and modify their own data (unless they have admin privileges)

## 🌍 Internationalization

The application supports multiple languages:
- English (default)
- French
- Kinyarwanda

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 📞 Contact

If you have any questions, please open an issue or contact the repository owner.

---

⭐️ From [Bruce NKUNDABAGENZI](https://github.com/yourusername)
