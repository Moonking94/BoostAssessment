# BoostAssessment
Boost Assessment test

# 💰 Digital Wallet Microservice – Boost Assessment

This is a Digital Wallet Microservice built with **Spring Boot 3.3.9** and **Java 17**, supporting operations such as:
- User creation
- Wallet top-up (credit)
- Payment (debit)
- Fund transfer
- Transaction history retrieval

The service ensures:
- 📄 **Swagger UI** for easy API testing
- 🗃️ **PostgreSQL** as the persistent store

---

## 📌 Features

- ✅ RESTful endpoints with validation
- ✅ Thread-safe wallet operations
- ✅ Custom AOP for idempotency
- ✅ JPA with PostgreSQL + schema creation scripts
- ✅ Swagger-based API docs (`springdoc-openapi`)
- ✅ Clean architecture using DAO/Service layers

---

## 🗂 Project Structure
wallet-service/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com.boost.wallet_service/
│ │ │ ├── annotation/
│ │ │ ├── aspect/
│ │ │ ├── config/
│ │ │ ├── controller/
│ │ │ ├── dao/
│ │ │ ├── dto/
│ │ │ ├── enums/
│ │ │ ├── interceptor/
│ │ │ ├── model/
│ │ │ └── service/
│ │ └── resources/
│ │ ├── application.properties
│ │ └── sql/
│ │ ├── 01_main.sql
│ │ ├── 02_schemas.sql
│ │ ├── 03_wallet.sql
│ │ ├── user.sql
│ │ ├── transactions.sql
│ │ └── idempotency_records.sql
└── build.gradle

---

## 🧰 Setup Instructions

### ⚙️ Prerequisites

- Java 17
- PostgreSQL 16+
- Gradle (`./gradlew` is included)
- Postman or any HTTP client for testing

---

## 🗃️ Database Setup

### 1. Create a PostgreSQL database

```bash
CREATE DATABASE boost_wallet;
```

### 2. Run the SQL scripts

Navigate to the `sql/` folder in your terminal:

```bash
cd sql/
psql -U <your_user> -d <your_database> -f wallet.sql
```

This script will:
- Create the `wallet` schema
- Create the tables:
    - `wallet.users`
    - `wallet.transactions`
    - `wallet.idempotency_records`

---

## ⚙️ Configuration

Update your `application.properties` file:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/boost_wallet
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password
spring.jpa.hibernate.ddl-auto=none
spring.jpa.properties.hibernate.default_schema=wallet
server.port=8080
```

---

## 🚀 Run the Application

```bash
./gradlew bootRun
```

The app will start on `http://localhost:8085`

---

## 🧪 Swagger UI

Once running, visit:

```
http://localhost:8085/swagger-ui.html
```

From there, you can test all APIs interactively, with example payloads and responses.

---

## 🔑 Idempotency

All write endpoints (`POST /user`, `/wallet/credit`, etc.) require an `Idempotency-Key` header to prevent duplicate requests.

```http
Idempotency-Key: <any-unique-string>
```

---

## 📤 API Examples

### ➕ Create User

```http
POST /api/userController/create
Header: Idempotency-Key: create-001

request
{
    "name": "nazmi",
    "email": "farid.nazmi@gmail.com",
    "balance": 15000
}

response
{
    "name": "nazmi",
    "email": "farid.nazmi@gmail.com",
    "balance": 15000,
    "errorMsg": null
}
```

### ➕ Update User

```http
POST /api/userController/update
Header: Idempotency-Key: update-001

request
{
    "name": "imran abdulhadi",
    "email": "imran.abdulhadi@gmail.com"
}

response
{
    "name": "imran abdulhadi",
    "email": "imran.abdulhadi@gmail.com",
    "balance": null,
    "errorMsg": null
}
```

### ➕ Credit Wallet

```http
POST /api/walletController/credit
Header: Idempotency-Key: credit-001

request
{
    "email": "imran.abdulhadi@gmail.com",
    "amount": 999
}

response
{
    "status": "Success",
    "balance": 10999.00,
    "errorMsg": null
}
```

### ➕ Debit Wallet

```http
POST /api/walletController/debit
Header: Idempotency-Key: debit-001

request
{
    "email": "imran.abdulhadi@gmail.com",
    "amount": 499
}

response
{
    "status": "Success",
    "balance": 10500.00,
    "errorMsg": null
}
```

### ➕ Transfer Wallet

```http
POST /api/walletController/transfer
Header: Idempotency-Key: transfer-001

request
{
    "email": "farid.nazmi@gmail.com",
    "destinationEmail": "imran.abdulhadi@gmail.com",
    "amount": 5000
}

response
{
    "status": "Success",
    "balance": 10500.00,
    "errorMsg": null
}
```

### ➕ Get Wallet Transaction History

```http
POST /api/walletController/getTransactionHistory
Header: Idempotency-Key: transactionhistory-001

request
{
    "email": "imran.abdulhadi@gmail.com"
}

response
{
    "transactions": [
        {
            "transactionId": "db5a1c07-b4e9-444c-95a3-3e6c1f91b26b",
            "transactionType": "TRANSFER",
            "amount": 5000.00,
            "fromEmail": "farid.nazmi@gmail.com",
            "toEmail": "imran.abdulhadi@gmail.com",
            "timestamp": "2025-11-04T23:41:08.335865"
        },
        {
            "transactionId": "f102279a-49f4-4121-b7c0-339981a70fb9",
            "transactionType": "TRANSFER",
            "amount": 500.00,
            "fromEmail": "imran.abdulhadi@gmail.com",
            "toEmail": "farid.nazmi@gmail.com",
            "timestamp": "2025-11-04T22:55:42.432086"
        },
        {
            "transactionId": "a55e1e5a-00c7-4f26-8f80-7d7ba1264703",
            "transactionType": "DEBIT",
            "amount": 499.00,
            "fromEmail": "imran.abdulhadi@gmail.com",
            "toEmail": null,
            "timestamp": "2025-11-04T22:40:37.319916"
        },
        {
            "transactionId": "749898ac-81e7-4c79-b579-b343dfe19584",
            "transactionType": "CREDIT",
            "amount": 999.00,
            "fromEmail": "imran.abdulhadi@gmail.com",
            "toEmail": null,
            "timestamp": "2025-11-04T22:37:28.932025"
        }
    ],
    "total": 4,
    "errorMsg": null
}
```

---

## 🧪 Testing Tips

| Action         | Steps                                  |
|----------------|-----------------------------------------|
| Test duplicate POST | Re-send same request with same `Idempotency-Key` |
| Test concurrency | Simulate concurrent credits/debits with `@Version` |
| Inspect data   | Use `SELECT * FROM wallet.*` queries   |

---

## 🧳 Future Enhancements

- Add JWT-based authentication
- Dockerize the application
- Pagination for transaction history
- Retry logic for failed transfers

---
