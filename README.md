
# TinyUrl

TinyUrl is a URL shortening service built with Java and Spring Boot. It allows users to create short URLs, track click analytics, and redirect to original URLs.  
The application supports JWT-based authentication and uses a Bloom filter to optimize username checks during registration.

---

## Features

- URL Shortening: Convert long URLs into short, shareable links.
- Redirection: Redirect short URLs to their original destinations.
- User Authentication: Register and login with JWT-based authentication.
- Analytics: Track click events for short URLs with timestamps.
- Role-Based Access: Secured endpoints for authenticated users.
- Bloom Filter: Optimizes username uniqueness checks during registration.

---

## Tech Stack

- **Java**: JDK 17 (core programming language)
- **Spring Boot**: Framework for building the REST API and application structure
- **Spring Data JPA**: For database operations with Hibernate
- **Spring Security**: For JWT-based authentication and role-based access control
- **PostgreSQL**: Relational database for storing users, URL mappings, and click events
- **Guava**: For Bloom filter implementation to optimize username checks
- **Lombok**: For reducing boilerplate code (e.g., getters, setters)
- **JJWT**: For generating and validating JSON Web Tokens
- **Maven**: Build and dependency management

---

## Prerequisites

- Java: JDK 17 or later
- Maven: For dependency management and build
- PostgreSQL: Version 12 or later
- Git: To clone the repository

---

## Installation

### 1. Clone the Repository
git clone https://github.com/HARSHA-Z1/TinyUrl.git
cd TinyUrl

2. Install Dependencies

mvn install

3. Set Up PostgreSQL

Create a database for the project:

psql -U postgres
CREATE DATABASE tinyurl;


Configure src/main/resources/application.properties:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/tinyurl
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```
Replace your_password with your actual PostgreSQL password.

4. Run the Application

mvn spring-boot:run

The application will start at http://localhost:8080.

⸻

API Endpoints Overview

Authentication
```
Method	Endpoint	Description
POST	/api/auth/public/register	Register a new user
POST	/api/auth/public/login	Login and obtain JWT token
```
URL Management
```
Method	Endpoint	Description
POST	/api/url/shorten	Create a short URL
GET	/api/url/myurls	View user’s URLs
GET	/api/url/events/{shortUrl}	View analytics for a short URL
GET	/{shortUrl}	Redirect to the original URL
```

⸻

Using the API via Postman

You can test all endpoints using Postman.

1. Register a New User

Method: POST
URL: http://localhost:8080/api/auth/public/register
Body (JSON):
```
{
  "username": "testuser",
  "password": "testpass"
}
```
Response:

User registered successfully


⸻

2. Login and Get JWT Token

Method: POST
URL: http://localhost:8080/api/auth/public/login
Body (JSON):
```
{
  "username": "testuser",
  "password": "testpass"
}
```
Response:
```
{
  "jwt": "eyJhbGciOiJIUzI1NiJ9..."
}
```
Copy the token and use it in all protected routes.

⸻

3. Create a Short URL

Method: POST
URL: http://localhost:8080/api/url/shorten
Headers:

Authorization: Bearer <your_jwt_token>
Content-Type: application/json

Body (JSON):

{
  "originalUrl": "https://example.com/very/long/url"
}

Response:
```
{
  "shortUrl": "abc123"
}

```
⸻

4. Redirect to Original URL

Method: GET
URL: http://localhost:8080/abc123
Redirects to the original URL (HTTP 302).
If not found, returns HTTP 404.

⸻

5. View All URLs Created by User

Method: GET
URL: http://localhost:8080/api/url/myurls
Headers:

Authorization: Bearer <your_jwt_token>

Response Example:
```
[
  {
    "shortUrl": "abc123",
    "originalUrl": "https://example.com",
    "clickCount": 5
  }
]

```
⸻

6. View Analytics for a Short URL

Method: GET
URL: http://localhost:8080/api/url/events/abc123
Headers:

Authorization: Bearer <your_jwt_token>

Response Example:
```
[
  {
    "timestamp": "2025-10-27T12:00:00"
  }
]
```

⸻

Notes
	•	Backend must be running on http://localhost:8080.
	•	Always include Authorization: Bearer <jwt_token> in authenticated routes.
	•	You can save the JWT as a Postman environment variable for easier reuse.

⸻

Project Structure
```
src/main/java/com/shorturl/short_url/
│
├── controller/
│   ├── AuthController.java
│   ├── UrlAndAnalyticsController.java
│   └── UrlRedirectController.java
│
├── service/
│   ├── UrlService.java
│   ├── UserService.java
│   └── UrlEncoder.java
│
├── models/
│   ├── User.java
│   ├── UrlMapping.java
│   └── RedirectEvent.java
│
├── repository/
│   ├── UserRepository.java
│   ├── UrlMappingRepository.java
│   └── RedirectEventRepository.java
│
├── dto/
│   ├── UrlResponse.java
│   └── UserUrlDto.java
│
├── security/
│   ├── JwtUtils.java
│   ├── JwtAuthenticationFilter.java
│   └── WebSecurityConfig.java
│
├── config/
│   └── BloomFilterConfig.java
│
├── component/
│   └── BloomFilterInitializer.java
│
└── ShortUrlApplication.java

```
⸻

Database Setup

1. Install PostgreSQL
	•	On Ubuntu: sudo apt install postgresql postgresql-contrib
	•	On macOS: brew install postgresql
	•	On Windows: Download from https://www.postgresql.org/download/windows/

2. Create Database

CREATE DATABASE tinyurl;


3. Configure Spring Boot

Ensure application.properties includes:

spring.datasource.url=jdbc:postgresql://localhost:5432/tinyurl
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect


⸻

Dependencies

Key dependencies (from pom.xml):
```
	•	Spring Boot Starter Web
	•	Spring Boot Starter Data JPA
	•	Spring Boot Starter Security
	•	PostgreSQL JDBC Driver
	•	Guava (for Bloom Filter)
	•	Lombok
	•	JJWT (for JWT Authentication)
```
