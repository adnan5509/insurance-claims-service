
# Insurance Claims Service

## Overview

The **Insurance Claims Service** is a Spring Boot application designed to manage insurance claims. This service allows customers to submit claims, check their statuses, and manage various insurance-related operations. The project uses JDK 21, Spring Boot, and H2 database for the development of a RESTful API.

## Features

- **Claim Creation**: Customers can create claims by specifying claim type, cost, and date.
- **Claim Status Update**: Allows updating the claim status through a dedicated API endpoint.
- **Customer Registration**: Enables the registration of customers with multiple insurance types.
- **API Documentation**: The project integrates **OpenAPI** documentation via **Springdoc OpenAPI** to explore API endpoints.

## Technologies Used

- **Java**: Version 21
- **Spring Boot**: Version 3.4.2
- **Spring Data JPA**: For database interaction
- **H2 Database**: In-memory database for local development
- **Springdoc OpenAPI**: For API documentation
- **JUnit**: For testing purposes
- **Mockito**: For mock testing
- **Maven**: Project management and build tool

## Setup and Installation

### Prerequisites

- **Java 21**: Ensure JDK 21 is installed on your machine.
- **Maven 3.6.3 or higher**: Ensure Maven is installed to build and run the project.

### Steps to Run Locally

1. **Clone the repository**:

   ```bash
   git clone https://github.com/your-username/insurance-claims-service.git
   cd insurance-claims-service
   ```

2. **Install dependencies**:

   If Maven is installed, run:

   ```bash
   mvn clean install
   ```

3. **Run the application**:

   Start the application with:

   ```bash
   mvn spring-boot:run
   ```

   The application will be available at `http://localhost:8080`.

### API Endpoints And API Documentation

For detailed API documentation, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

## Database Schema
When the application runs with Spring Data JPA, Hibernate automatically generates the following schema based on the entity mappings:

# Database Schema Documentation

This document outlines the structure of the database tables used in the application.

---

## 1. **Customer Table (`customer`)**

Stores customer information.

| Column Name     | Data Type       | Nullable | Key           | Description                          |
|-----------------|-----------------|----------|---------------|--------------------------------------|
| `id`            | `BIGINT`        | No       | Primary Key   | Auto-generated unique identifier.    |
| `full_name`     | `VARCHAR(255)`  | No       |               | The full name of the customer.       |
| `date_of_birth` | `DATE`          | No       |               | The customer's date of birth.        |

---

## 2. **Customer Insurance Types Table (`customer_insurance_types`)**

Maps insurance types to customers (supports multiple types per customer).

| Column Name        | Data Type       | Nullable | Key                         | Description                                      |
|--------------------|-----------------|----------|-----------------------------|--------------------------------------------------|
| `customer_id`      | `BIGINT`        | No       | Primary Key, Foreign Key    | References the customer's `id` in `customer`.    |
| `insurance_type`   | `VARCHAR(255)`  | No       | Primary Key                 | Type of insurance (e.g., "CAR", "HEALTH").       |

**Notes**:
- Composite primary key (`customer_id`, `insurance_type`).
- `insurance_type` values are derived from an enum (e.g., `InsuranceType`).

---

## 3. **Insurance Claim Table (`insurance_claim`)**

Stores details about insurance claims made by customers.

| Column Name     | Data Type       | Nullable | Key           | Description                                      |
|-----------------|-----------------|----------|---------------|--------------------------------------------------|
| `id`            | `BIGINT`        | No       | Primary Key   | Auto-generated unique identifier.                |
| `claim_type`    | `VARCHAR(255)`  | No       |               | Type of claim (from `InsuranceType` enum).       |
| `date`          | `DATE`          | No       |               | Date the claim was filed.                        |
| `status`        | `VARCHAR(255)`  | No       |               | Status of the claim (e.g., "OPEN", "CLOSED").    |
| `cost`          | `DOUBLE`        | No       |               | Cost associated with the claim.                  |
| `customer_id`   | `BIGINT`        | Yes      | Foreign Key    | References the customer's `id` in `customer`.    |

**Notes**:
- `claim_type` and `status` values are derived from enums (`InsuranceType` and `ClaimStatus`, respectively).
- `customer_id` can be `NULL` if a claim is not linked to a customer.

---

## Entity Relationships

- **Customer** ↔ **Insurance Claim**:  
  A customer can have **many** insurance claims (`1:N` relationship).
- **Customer** ↔ **Insurance Types**:  
  A customer can have **multiple** insurance types (`1:N` relationship via `customer_insurance_types`).



## Contributors

- **Adnan Afzal Bajwa** -

## Contact

For any queries, please reach out to [adnanafzalbajwa@gmail.com](mailto:adnanafzalbajwa@gmail.com).
