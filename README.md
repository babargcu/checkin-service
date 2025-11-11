# Check-in Service

A **Spring Boot** microservice for managing employee **check-ins and check-outs**.  
On check-out, the service records total hours worked, stores them in an **H2 in-memory database**, and sends an asynchronous message to **RabbitMQ** for email or legacy API integration.

---

## Tech Stack
- **Java 25**
- **Spring Boot 3.2.5**
- **Spring Data JPA** (H2 Database)
- **Spring AMQP (RabbitMQ)**
- **Spring Boot Mail (simulated)**
- **Maven**
- **Docker (for RabbitMQ)**

---

## Repository Contents

| File / Folder | Description |
|----------------|-------------|
| `src/main/java` | Source code for Spring Boot service |
| `src/main/resources/application.properties` | H2 DB, RabbitMQ, and app configuration |
| `EXPLANATION.md` | Short written explanation of system design |
| `README.md` | This file |

---

## How to Run the Project

### Clone the Repository
```bash
git clone https://github.com/babargcu/checkin-service.git
cd checkin-service


Start RabbitMQ in Docker

Make sure Docker Desktop is running, then execute:

docker run -d --hostname my-rabbit --name some-rabbit \
-p 5672:5672 -p 15672:15672 rabbitmq:3-management

Build the Application
mvn clean install

Run the Service
mvn spring-boot:run


The Check-in Service will start at:

http://localhost:8080

Access H2 Database Console

To view all attendance data:
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:checkin_db
Username: sa
Password:

Check-in
POST http://localhost:8080/api/attendance
Content-Type: application/json

{
  "employeeId": "emp123"
}


Response:
Check-in recorded

Check-out:
POST http://localhost:8080/api/attendance
Content-Type: application/json

{
  "employeeId": "emp123"
}


Response:

Check-out recorded. Hours worked: 8.00
