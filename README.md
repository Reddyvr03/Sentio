# Sentio — Secure Personal Journal & Sentiment Analysis Platform

Sentio is a secure backend application for managing personal journal entries while providing AI-powered sentiment analysis.

The application is built using **Java and Spring Boot** and demonstrates backend engineering concepts including **REST APIs, JWT-based authentication, role-based access control, MongoDB persistence, Redis caching, Apache Kafka asynchronous processing, scheduled background jobs, and LLM integration**.

---

## 🚀 Features

* 🔐 **JWT-based Authentication**

  * Stateless authentication using JSON Web Tokens
  * Secure API access using Spring Security

* 👤 **Role-Based Access Control**

  * Authorization based on user roles
  * Protected endpoints for authenticated users

* 📔 **Journal Management**

  * Create, read, update, and delete journal entries
  * User-specific journal access

* 🤖 **AI-Powered Sentiment Analysis**

  * Journal content can be processed using an LLM
  * Generates sentiment information from journal entries

* ⚡ **Asynchronous Processing**

  * Apache Kafka is used for asynchronous event processing
  * Decouples journal operations from background processing

* 🚀 **Redis Caching**

  * Caches frequently accessed data
  * Reduces unnecessary calls to external services

* ⏰ **Scheduled Background Processing**

  * Supports scheduled jobs for background operations

* 📧 **Email Integration**

  * Spring Mail is used for email-related functionality

* 🗄️ **MongoDB**

  * NoSQL persistence for users and journal data

* 🛡️ **Security**

  * Spring Security
  * JWT authentication
  * Password protection
  * Role-based authorization

---

## 🏗️ Architecture

The application follows a layered backend architecture:

```text
                    ┌──────────────────────┐
                    │       Client         │
                    │   Postman / Frontend │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │     REST APIs        │
                    │    Controllers       │
                    └──────────┬───────────┘
                               │
                               ▼
                    ┌──────────────────────┐
                    │      Service Layer   │
                    │   Business Logic     │
                    └──────┬─────────┬─────┘
                           │         │
                ┌──────────┘         └──────────┐
                ▼                               ▼
       ┌─────────────────┐             ┌─────────────────┐
       │   MongoDB       │             │     Redis       │
       │   Persistence   │             │     Cache       │
       └─────────────────┘             └─────────────────┘
                           │
                           ▼
                    ┌─────────────────┐
                    │      Kafka      │
                    │ Async Processing│
                    └────────┬────────┘
                             │
                             ▼
                    ┌─────────────────┐
                    │  AI / LLM       │
                    │ Sentiment       │
                    │ Analysis        │
                    └─────────────────┘
```

---

## 🛠️ Tech Stack

| Category       | Technology             |
| -------------- | ---------------------- |
| Language       | Java 8                 |
| Framework      | Spring Boot 2.7.16     |
| Web            | Spring MVC / REST APIs |
| Security       | Spring Security        |
| Authentication | JWT                    |
| Database       | MongoDB                |
| Caching        | Redis                  |
| Messaging      | Apache Kafka           |
| Email          | Spring Mail            |
| AI             | LLM Integration        |
| Build Tool     | Maven                  |
| Code Quality   | SonarQube              |
| Testing        | Spring Boot Test       |

The repository's Maven configuration confirms Spring Boot 2.7.16, Java 8, MongoDB, Spring Security, Redis, Kafka, Spring Mail, JWT, and the Sonar Maven plugin.

---

## 🔐 Authentication & Authorization

Sentio uses **JWT-based stateless authentication**.

### Authentication Flow

```text
User
 │
 │ Login
 ▼
Authentication API
 │
 ▼
Validate Credentials
 │
 ▼
Generate JWT
 │
 ▼
Return Token
 │
 ▼
Client
 │
 │ Authorization: Bearer <JWT>
 ▼
Protected API
 │
 ▼
JWT Validation
 │
 ▼
Authorization
 │
 ▼
Business Logic
```

The JWT contains the information required to identify and authorize the authenticated user.

Protected APIs validate the token before allowing access to journal-related operations.

---

## 📔 Journal Management

Users can manage their personal journal entries through REST APIs.

Typical operations include:

```text
POST    /journal
GET     /journal
GET     /journal/{id}
PUT     /journal/{id}
DELETE  /journal/{id}
```

Each journal entry is associated with its respective user, ensuring that users can access only their own journal data.

---

## 🤖 AI Sentiment Analysis

One of the core capabilities of Sentio is AI-assisted sentiment analysis.

When a journal entry is processed:

```text
Journal Entry
      │
      ▼
Application
      │
      ▼
Kafka Event
      │
      ▼
Async Consumer
      │
      ▼
LLM / AI Service
      │
      ▼
Sentiment Analysis
      │
      ▼
Store / Update Result
```

Using asynchronous processing allows the journal API to remain independent from potentially slower AI processing.

This approach also demonstrates an **event-driven architecture** where background processing can be separated from the main request-response flow.

---

## ⚡ Kafka-Based Asynchronous Processing

Apache Kafka is used to decouple journal operations from background processing.

### Producer

The application publishes an event when a journal-related operation requires asynchronous processing.

```text
Application
     │
     ▼
Kafka Producer
     │
     ▼
Kafka Topic
```

### Consumer

A Kafka consumer processes the event asynchronously.

```text
Kafka Topic
     │
     ▼
Kafka Consumer
     │
     ▼
Processing Logic
     │
     ▼
AI / Background Operation
```

Benefits:

* Reduced API response dependency on background processing
* Loose coupling between components
* Better scalability
* Independent processing of events
* Ability to introduce additional consumers later

---

## 🚀 Redis Caching

Redis is used as a caching layer to reduce repeated access to external or frequently requested data.

```text
             Request
                │
                ▼
           Service Layer
                │
         ┌──────┴──────┐
         │             │
      Cache Hit     Cache Miss
         │             │
         ▼             ▼
       Redis        External API
         │             │
         │             ▼
         │          Response
         │             │
         └──────┬──────┘
                ▼
             Client
```

This reduces unnecessary external calls and improves response time for cached data.

---

## 🧩 Layered Architecture

The application follows a separation-of-concerns approach:

```text
Controller
    │
    ▼
Service
    │
    ▼
Repository
    │
    ▼
MongoDB
```

### Controller Layer

Responsible for:

* Handling HTTP requests
* Request validation
* Returning HTTP responses
* Delegating business operations

### Service Layer

Responsible for:

* Business logic
* Authentication logic
* Journal processing
* AI integration
* Cache interaction
* Event publishing

### Repository Layer

Responsible for:

* Database interaction
* CRUD operations
* MongoDB queries

---

## 📂 Project Structure

```text
src
└── main
    └── java
        └── ...
            ├── controller
            ├── service
            ├── repository
            ├── entity
            ├── dto
            ├── config
            ├── security
            └── exception
```

> The exact package structure may evolve as the project grows.

---

## 🔄 End-to-End Journal Flow

```text
1. User authenticates
          │
          ▼
2. Server validates credentials
          │
          ▼
3. JWT token generated
          │
          ▼
4. User creates journal entry
          │
          ▼
5. JWT validated
          │
          ▼
6. Journal stored in MongoDB
          │
          ▼
7. Event published to Kafka
          │
          ▼
8. Kafka consumer processes event
          │
          ▼
9. AI performs sentiment analysis
          │
          ▼
10. Sentiment result persisted
```

---

## ⚙️ Getting Started

### Prerequisites

Make sure the following are installed:

* Java 8+
* Maven 3.6+
* MongoDB
* Redis
* Apache Kafka
* Git

---

### Clone the Repository

```bash
git clone https://github.com/Reddyvr03/Sentio.git

cd Sentio
```

---

### Configure MongoDB

Start MongoDB locally and configure the MongoDB connection in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/sentio
```

---

### Configure Redis

Make sure Redis is running locally.

Example:

```properties
spring.redis.host=localhost
spring.redis.port=6379
```

---

### Configure Kafka

Start Kafka and configure the broker:

```properties
spring.kafka.bootstrap-servers=localhost:9092
```

Create/configure the required Kafka topics used by the application.

---

### Configure Application Secrets

Do not commit secrets directly into the repository.

Use environment variables or an external configuration mechanism for:

```text
JWT_SECRET
DATABASE_URL
REDIS_HOST
KAFKA_BOOTSTRAP_SERVERS
MAIL_USERNAME
MAIL_PASSWORD
LLM_API_KEY
```

---

## ▶️ Run the Application

Using Maven:

```bash
mvn clean install
```

Then:

```bash
mvn spring-boot:run
```

Or run the main Spring Boot application class from IntelliJ IDEA.

The application will start on the configured Spring Boot port.

---

## 🧪 Testing

Run the test suite using:

```bash
mvn test
```

The project uses Spring Boot's testing infrastructure for automated testing.

---

## 🔒 Security Considerations

The application is designed around several backend security principles:

* JWT-based stateless authentication
* Role-based authorization
* Protected REST endpoints
* Password security
* Separation of authentication and business logic
* Externalized application secrets

**Never commit:**

```text
JWT secrets
Database passwords
API keys
Email credentials
LLM API keys
```

---

## 📈 Future Improvements

Potential improvements for future versions:

* [ ] Dockerize the application
* [ ] Docker Compose for MongoDB, Redis and Kafka
* [ ] API documentation using Swagger / OpenAPI
* [ ] Centralized exception handling
* [ ] Request validation
* [ ] Unit and integration test coverage
* [ ] Kafka Dead Letter Topic
* [ ] Retry mechanism for failed events
* [ ] Kafka consumer monitoring
* [ ] Prometheus metrics
* [ ] Grafana dashboards
* [ ] ELK-based centralized logging
* [ ] CI/CD using GitHub Actions
* [ ] Cloud deployment
* [ ] Improved AI prompt management
* [ ] Sentiment history and analytics dashboard

---

## 💡 What This Project Demonstrates

Sentio demonstrates practical backend engineering concepts including:

```text
Java
 │
 ├── Spring Boot
 │     ├── REST APIs
 │     ├── Dependency Injection
 │     └── Layered Architecture
 │
 ├── Spring Security
 │     └── JWT Authentication
 │
 ├── MongoDB
 │     └── NoSQL Persistence
 │
 ├── Redis
 │     └── Caching
 │
 ├── Apache Kafka
 │     └── Asynchronous Processing
 │
 ├── LLM
 │     └── AI-powered Sentiment Analysis
 │
 └── Maven
       └── Build & Dependency Management
```

---

## 👨‍💻 Author

**Karthik V R**

Software Developer | Java Backend Engineer

### Technologies

`Java` `Spring Boot` `Spring Security` `JWT` `MongoDB` `Redis` `Kafka` `REST APIs` `LLM` `Maven`

---

## ⭐ Project

If you find this project useful, consider giving it a ⭐ on GitHub.

**Repository:**
https://github.com/Reddyvr03/Sentio

