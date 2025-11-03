# Check-in Service Design

## Overview
The Check-in Service is a Spring Boot-based REST API for factory attendance management. 
Employees can check-in and check-out using a single endpoint that accepts their employee ID. 
All working times are stored in a database (H2 in development) and asynchronous messages 
are sent to external systems such as email notifications and a legacy attendance recording API.

## Components

### REST API
Single endpoint handles check-in and check-out. 
The service decides whether an employee is checking in or out based on their existing records in the database.

### Database
H2 is used to store all attendance events. Each event records:
- Employee ID
- Check-in time
- Check-out time
- Hours worked

### Asynchronous Queue
RabbitMQ is used to decouple the main API from slow or unreliable external systems. 
Messages containing attendance events are enqueued on check-out.

### Consumers
- **EmailConsumer** consumes messages to send emails (currently logs for testing).  
- A separate consumer can call the legacy API to record hours, implementing retries and error handling.

### Error Handling and Reliability
- All check-in/check-out events are first stored in the database before sending to external systems.  
- RabbitMQ ensures messages are not lost and can be retried in case of consumer failures.  
- JSON serialization is used for messages to ensure cross-language compatibility.

### Scalability and Monitoring
- Multiple consumers can scale independently to handle email and legacy API calls.  
- Each message can be traced using unique employee IDs and timestamps to monitor processing.  
- Failed messages can be routed to a dead-letter queue for investigation.

### Benefits of Design
- Decouples user interactions from slow external dependencies.  
- Prevents data loss using a persistent database and queue.  
- Allows retries and handles outages gracefully.  
- Extensible to new consumers or changes in message format.
