
                 EMPLOYEE CHECK-IN SYSTEM ARCHITECTURE                 
                                                                               
           ┌──────────────┐              ┌──────────────┐                      
           │   CLIENT     │              │ APPLICATION  │                      
           │              │   REST API   │              │                      
           │ Card         │──────────────▶ Check-in     │                      
           │ Device       │              │ Service      │                      
           │              │◄─────────────│ (Spring Boot)│                      
           └──────────────┘              └──────┬───────┘                      
                                                │                              
                        ┌───────────────────────┼───────────────────────┐      
                        │                       │                       │      
            ┌───────────┴──────────┐ ┌──────────┴──────────┐ ┌──────────┴──────────┐
            │      DATA STORE      │ │   MESSAGE QUEUE     │ │   INTEGRATION       │
            │                      │ │                     │ │                     │
            │ H2 Database          │ │ RabbitMQ            │ │ Legacy System       │
            │ • Attendance Records │ │ • attendanceQueue   │ │ • External API      │
            │ • Event Storage       │ │ • Async Messaging  │ │ • Synchronization   │
            └──────────────────────┘ └──────────┬──────────┘ └────────────────────┘
                                                 │                                  
                                        ┌────────┴──────────┐                      
                                        │   CONSUMER        │                      
                                        │                   │                      
                                        │ Email Service     │                      
                                        │ • Notifications   │                      
                                        │ • Event Processing│                      
                                        └───────────────────┘                      