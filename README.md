🚀 Smart City Backend

Urban Issue Management System

📌 Overview

Smart City Backend is a backend system designed to collect, process, and manage urban issue reports submitted by citizens.

The system focuses on:

- Efficient report handling
- AI-assisted classification
- Scalable processing using an event-driven approach

This project is implemented using a modular monolithic architecture, ensuring clear separation of concerns while maintaining deployment simplicity.

🧩 System Architecture

The system is designed around a modular backend with asynchronous processing.

[Client] → [Backend API] → [Kafka] → [AI Service]
                              ↓
                         [PostgreSQL]
                              ↓
                         [Redis / Cloudinary]

- Backend – Handles core business logic and API
- Kafka – Enables asynchronous communication between services
- AI Service – Processes reports in the background
- Cloudinary – Stores uploaded media (images/evidence)

🎯 Key Features

👤 Citizen
- Submit reports with description, images, and location
- Track report status
- View report history

👨‍💼 Staff
- Manage tasks via dashboard (Kanban-style)
- Update task status
- Upload completion evidence

🛡️ Admin
- Monitor system activity
- Manage users, categories, departments, and offices
- Review reports flagged by the system

🧠 AI-assisted Processing

The system integrates AI to support report classification:

- Automatically suggest category
- Calculate confidence score

Decision Logic

| Condition                        | Action                       |
| -------------------------------- | ---------------------------- |
| confidence ≥ 0.8                 | Auto processed               |
| confidence < 0.8                 | Marked as LOW_CONFIDENCE     |
| Category mismatch with reality   | Marked as NEED_REVIEW        |

Flagged reports are handled manually by administrators.

🔄 Processing Flow (Simplified)

Citizen submits report → Backend validates & stores → Publish event to Kafka → AI service processes asynchronously → Backend receives result → Decision: Auto assign task or Wait for admin review

⚙️ Tech Stack

| Component      | Technology         |
| -------------- | ------------------ |
| Framework      | Spring Boot        |
| Database       | PostgreSQL         |
| Cache / Queue  | Redis              |
| Message Broker | Apache Kafka       |
| Storage        | Cloudinary         |
| Container      | Docker             |

🗄️ Database Migration

The project uses database migration to manage schema changes:

- Migrations are automatically executed on application startup
- Ensures consistency across environments
- Versioned and maintainable schema evolution

⚙️ Environment Setup

Environment variables are managed via .env file.

1. Copy from template:

cp .env.exp .env

2. Example configuration:

# Spring
SPRING_PROFILES_ACTIVE=dev
APP_NAME=smart-city-backend
SERVER_PORT=8080

# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=smart_city
DB_USERNAME=postgres
DB_PASSWORD=123456

# Kafka
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# JWT
JWT_SECRET=your_secret

# Cloudinary
CLOUDINARY_CLOUD_NAME=your_cloud
CLOUDINARY_API_KEY=your_key
CLOUDINARY_API_SECRET=your_secret

🐳 Running the Project (Development)

The project uses a dedicated Docker Compose file for development.

docker compose -f docker-compose.dev.yml up --build

This will start the following services:

- Backend service (Spring Boot)
- PostgreSQL
- Kafka
- Redis

📡 API Overview

| Method | Endpoint                       | Description                  |
| ------ | ------------------------------ | ---------------------------- |
| POST   | /api/reports                   | Create new report            |
| GET    | /api/reports                   | List all reports             |
| GET    | /api/reports/{id}              | Get report details           |
| POST   | /api/tasks/{id}/complete       | Complete a task              |

📊 Report Status

| Status             | Description                            |
| ------------------ | -------------------------------------- |
| PENDING            | Waiting for processing                 |
| IN_PROGRESS        | Currently being processed              |
| COMPLETED          | Successfully completed                 |
| LOW_CONFIDENCE     | Low AI confidence (needs verification) |
| NEED_REVIEW        | Requires admin review                  |
| REJECTED           | Report rejected                        |

🔐 Roles

| Role      | Permissions                                        |
| --------- | -------------------------------------------------- |
| Citizen   | Submit reports, track status                       |
| Staff     | Process tasks, update status, upload evidence     |
| Admin     | Full system management, manage users & categories |

👨‍💻 Author

Nguyen Manh Cuong
Graduation Thesis Project

📄 License

MIT License – feel free to use and modify for learning or practical purposes.
