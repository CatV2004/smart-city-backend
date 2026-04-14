<div id="top">

<!-- HEADER STYLE: CLASSIC -->
<div align="center">

<img src="https://github.com/CatV2004/smart-city-frontend/blob/main/urban-management/public/logo_company.png" width="30%" style="position: relative; top: 0; right: 0;" alt="Project Logo"/>

# URBAN MANAGEMENT BE

<em>Smart Citizen Report Management System</em>

<!-- BADGES -->
<!-- local repository, no metadata badges. -->

<em>Built with the tools and technologies:</em>

<img src="https://img.shields.io/badge/Spring-000000.svg?style=default&logo=Spring&logoColor=white" alt="Spring">
<img src="https://img.shields.io/badge/Docker-2496ED.svg?style=default&logo=Docker&logoColor=white" alt="Docker">
<img src="https://img.shields.io/badge/Apache%20Kafka-231F20.svg?style=default&logo=ApacheKafka&logoColor=white" alt="Kafka">
<img src="https://img.shields.io/badge/Redis-DC382D.svg?style=default&logo=Redis&logoColor=white" alt="Redis">
<img src="https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=default&logo=PostgreSQL&logoColor=white" alt="PostgreSQL">
<img src="https://img.shields.io/badge/PostGIS-79BD42.svg?style=default&logo=PostgreSQL&logoColor=white" alt="PostGIS">
<img src="https://img.shields.io/badge/Flyway-CC0200.svg?style=default&logo=Flyway&logoColor=white" alt="Flyway">
<img src="https://img.shields.io/badge/WebSocket-010101.svg?style=default&logo=Socket.io&logoColor=white" alt="WebSocket">

</div>
<br>

---

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
  - [Project Index](#project-index)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Usage](#usage)
  - [Testing](#testing)
- [Roadmap](#roadmap)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgments](#acknowledgments)

---

## Overview

**Urban Management BE** is the backend system for smart city citizen report management. Residents can submit reports about urban issues (trash, infrastructure, public safety, etc.). The system automatically classifies each report by category, validates its authenticity, and intelligently assigns it to the nearest office within the relevant department using **PostGIS** geospatial queries. Staff can then process assigned tasks, upload completion evidence, and close reports. The system features real-time notifications and MapBox integration for geospatial visualization, with Kafka and Redis powering high-performance event-driven processing and caching.

---

## Features

| Feature                     | Description                                                                           |
| --------------------------- | ------------------------------------------------------------------------------------- |
| **Report Management**       | Residents submit reports with images and location; system auto-classifies by category |
| **Smart Auto-Assignment**   | Automatically assigns reports to the nearest office using PostGIS spatial queries     |
| **Validation & Review**     | Automated verification with optional admin review for edge cases                      |
| **Task Workflow**           | Staff create tasks, update progress, upload completion evidence                       |
| **Real-time Notifications** | Push notifications via WebSocket/Kafka for residents and staff                        |
| **MapBox Integration**      | Visualize reports, offices, and tasks on an interactive map                           |
| **High Performance**        | Kafka for event-driven architecture, Redis for caching and real-time data             |

---

## Project Structure

```sh
└── repo/
    ├── Dockerfile                 # Production Docker image
    ├── Dockerfile.dev             # Development Docker image with hot-reload
    ├── docker-compose.yml         # Orchestration for all services (PostgreSQL+PostGIS, Redis, Kafka)
    ├── docker-compose.dev.yml     # Dev environment setup
    ├── docker-compose.prod.yml    # Production environment setup
    ├── pom.xml                    # Maven dependencies
    ├── mvnw / mvnw.cmd            # Maven wrapper scripts
    └── src/
        ├── main/
        │   ├── java/              # Source code (Controllers, Services, Repositories)
        │   └── resources/
        │       ├── application*.yml  # Multi-environment configs
        │       └── db/migration/     # Flyway migration scripts (including PostGIS extensions)
        └── test/                  # Unit & integration tests
```

### Project Index

<details open>
    <summary><b><code>REPO/</code></b></summary>
    <!-- __root__ Submodule -->
    <details>
        <summary><b>__root__</b></summary>
        <blockquote>
            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                <code><b>⦿ __root__</b></code>
            <table style='width: 100%; border-collapse: collapse;'>
            <thead>
                <tr style='background-color: #f8f9fa;'>
                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                    <th style='text-align: left; padding: 8px;'>Summary</th>
                </tr>
            </thead>
                <tr style='border-bottom: 1px solid #eee;'>
                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/mvnw.cmd'>mvnw.cmd</a></b></td>
                    <td style='padding: 8px;'>Maven wrapper script for Windows - automatically downloads Maven if not present</code></td>
                </tr>
                <tr style='border-bottom: 1px solid #eee;'>
                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/docker-compose.yml'>docker-compose.yml</a></b></td>
                    <td style='padding: 8px;'>Main Docker Compose configuration orchestrating all services (Spring Boot app, PostgreSQL with PostGIS, Redis, Kafka, Zookeeper)</code></td>
                </tr>
                <tr style='border-bottom: 1px solid #eee;'>
                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/mvnw'>mvnw</a></b></td>
                    <td style='padding: 8px;'>Maven wrapper script for Unix/Linux/macOS - ensures consistent Maven version across environments</code></td>
                </tr>
                <tr style='border-bottom: 1px solid #eee;'>
                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/pom.xml'>pom.xml</a></b></td>
                    <td style='padding: 8px;'>Maven Project Object Model - defines project dependencies including Spring Boot, Spring Data JPA, PostgreSQL driver, Hibernate Spatial (for PostGIS), Kafka, Redis, Flyway, and WebSocket</code></td>
                </tr>
                <tr style='border-bottom: 1px solid #eee;'>
                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/docker-compose.dev.yml'>docker-compose.dev.yml</a></b></td>
                    <td style='padding: 8px;'>Development Docker Compose with hot-reload support, debug port mapping, and volume mounts for live code changes</code></td>
                </tr>
                <tr style='border-bottom: 1px solid #eee;'>
                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/docker-compose.prod.yml'>docker-compose.prod.yml</a></b></td>
                    <td style='padding: 8px;'>Production Docker Compose with optimized settings, resource limits, and replica configurations for scalability</code></td>
                </tr>
                <tr style='border-bottom: 1px solid #eee;'>
                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/Dockerfile'>Dockerfile</a></b></td>
                    <td style='padding: 8px;'>Multi-stage Docker build for production - compiles Java code, creates JAR, and builds lightweight runtime image</code></td>
                </tr>
                <tr style='border-bottom: 1px solid #eee;'>
                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/Dockerfile.dev'>Dockerfile.dev</a></b></td>
                    <td style='padding: 8px;'>Development Docker image with Maven, JDK, and Spring Boot DevTools for hot-reload and debugging</code></td>
                </tr>
            </table>
        </blockquote>
    </details>
    <!-- target Submodule -->
    <details>
        <summary><b>target</b></summary>
        <blockquote>
            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                <code><b>⦿ target</b></code>
            <!-- classes Submodule -->
            <details>
                <summary><b>classes</b></summary>
                <blockquote>
                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                        <code><b>⦿ target.classes</b></code>
                    <table style='width: 100%; border-collapse: collapse;'>
                    <thead>
                        <tr style='background-color: #f8f9fa;'>
                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                            <th style='text-align: left; padding: 8px;'>Summary</th>
                        </tr>
                    </thead>
                        <tr style='border-bottom: 1px solid #eee;'>
                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/application-redis-prod.yml'>application-redis-prod.yml</a></b></td>
                            <td style='padding: 8px;'>Production Redis configuration - connection pool settings, timeout, and cluster endpoints</code></td>
                        </tr>
                        <tr style='border-bottom: 1px solid #eee;'>
                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/application-local.yml'>application-local.yml</a></b></td>
                            <td style='padding: 8px;'>Local development configuration - uses embedded databases, debug logging, and dev-specific properties</code></td>
                        </tr>
                        <tr style='border-bottom: 1px solid #eee;'>
                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/application.yaml'>application.yaml</a></b></td>
                            <td style='padding: 8px;'>Main application configuration - server port, datasource, JPA settings, and common properties</code></td>
                        </tr>
                        <tr style='border-bottom: 1px solid #eee;'>
                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/application-redis.yml'>application-redis.yml</a></b></td>
                            <td style='padding: 8px;'>Base Redis configuration - host, port, password, and cache TTL settings for real-time data</code></td>
                        </tr>
                        <tr style='border-bottom: 1px solid #eee;'>
                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/application-dev.yml'>application-dev.yml</a></b></td>
                            <td style='padding: 8px;'>Development environment configuration - dev database, detailed logging, and hot-reload enabled</code></td>
                        </tr>
                        <tr style='border-bottom: 1px solid #eee;'>
                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/application-kafka-prod.yml'>application-kafka-prod.yml</a></b></td>
                            <td style='padding: 8px;'>Production Kafka configuration - broker addresses, replication factor, and consumer group settings</code></td>
                        </tr>
                        <tr style='border-bottom: 1px solid #eee;'>
                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/application-prod.yml'>application-prod.yml</a></b></td>
                            <td style='padding: 8px;'>Production environment configuration - production database, connection pooling, and performance tuning</code></td>
                        </tr>
                        <tr style='border-bottom: 1px solid #eee;'>
                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/application-kafka.yml'>application-kafka.yml</a></b></td>
                            <td style='padding: 8px;'>Base Kafka configuration - topic names, serializer/deserializer settings, and event-driven setup</code></td>
                        </tr>
                    </table>
                    <!-- db Submodule -->
                    <details>
                        <summary><b>db</b></summary>
                        <blockquote>
                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                <code><b>⦿ target.classes.db</b></code>
                            <!-- migration Submodule -->
                            <details>
                                <summary><b>migration</b></summary>
                                <blockquote>
                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                        <code><b>⦿ target.classes.db.migration</b></code>
                                    <table style='width: 100%; border-collapse: collapse;'>
                                    <thead>
                                        <tr style='background-color: #f8f9fa;'>
                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                        </tr>
                                    </thead>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration/V4__add_columns_and_constraints.sql'>V4__add_columns_and_constraints.sql</a></b></td>
                                            <td style='padding: 8px;'>Adds additional columns (status, priority, resolved_at) and foreign key constraints to reports table</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration/V2__create_base_tables.sql'>V2__create_base_tables.sql</a></b></td>
                                            <td style='padding: 8px;'>Creates core tables: users, reports, categories, departments with PostGIS geometry columns for location data</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration/V5__seed_data.sql'>V5__seed_data.sql</a></b></td>
                                            <td style='padding: 8px;'>Populates initial reference data: default admin users, department categories, and report types</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration/V3__create_additional_tables.sql'>V3__create_additional_tables.sql</a></b></td>
                                            <td style='padding: 8px;'>Creates additional tables: tasks, task_evidences, notifications, report_assignments for workflow management</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration/V1__init_extensions.sql'>V1__init_extensions.sql</a></b></td>
                                            <td style='padding: 8px;'>Initializes PostgreSQL extensions: PostGIS (spatial data), UUID, and pgcrypto for enhanced functionality</code></td>
                                        </tr>
                                    </table>
                                </blockquote>
                            </details>
                            <!-- migration-backup Submodule -->
                            <details>
                                <summary><b>migration-backup</b></summary>
                                <blockquote>
                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                        <code><b>⦿ target.classes.db.migration-backup</b></code>
                                    <table style='width: 100%; border-collapse: collapse;'>
                                    <thead>
                                        <tr style='background-color: #f8f9fa;'>
                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                        </tr>
                                    </thead>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration-backup/V7__migrate_task_to_department_office.sql'>V7__migrate_task_to_department_office.sql</a></b></td>
                                            <td style='padding: 8px;'>Migration script: restructures task relationships to link with department_offices instead of direct department assignment</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration-backup/V3__seed_departments_and_categories.sql'>V3__seed_departments_and_categories.sql</a></b></td>
                                            <td style='padding: 8px;'>Legacy seed script: initial department and category data (preserved for rollback compatibility)</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration-backup/V8__create_task_evidences.sql'>V8__create_task_evidences.sql</a></b></td>
                                            <td style='padding: 8px;'>Creates task_evidences table for storing completion proof documents and images uploaded by staff</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration-backup/V2__create_base_tables.sql'>V2__create_base_tables.sql</a></b></td>
                                            <td style='padding: 8px;'>Legacy base tables script (backup version before schema optimizations)</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration-backup/V5__update_task_relationship_to_many_to_one.sql'>V5__update_task_relationship_to_many_to_one.sql</a></b></td>
                                            <td style='padding: 8px;'>Schema update: converts task-report relationship from one-to-many to many-to-one for better flexibility</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration-backup/V1__init_extensions.sql'>V1__init_extensions.sql</a></b></td>
                                            <td style='padding: 8px;'>Legacy extension initialization script (backup version with original PostGIS setup)</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration-backup/V6__add_department_offices_and_user_office.sql'>V6__add_department_offices_and_user_office.sql</a></b></td>
                                            <td style='padding: 8px;'>Adds department_offices table for office location management and links users to their assigned offices</code></td>
                                        </tr>
                                        <tr style='border-bottom: 1px solid #eee;'>
                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/target/classes/db/migration-backup/V4__create_report_status_history.sql'>V4__create_report_status_history.sql</a></b></td>
                                            <td style='padding: 8px;'>Creates report_status_history table for audit trail and tracking status changes over time</code></td>
                                        </tr>
                                    </table>
                                </blockquote>
                            </details>
                        </blockquote>
                    </details>
                </blockquote>
            </details>
        </blockquote>
    </details>
    <!-- src Submodule -->
    <details>
        <summary><b>src</b></summary>
        <blockquote>
            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                <code><b>⦿ src</b></code>
            <!-- main Submodule -->
            <details>
                <summary><b>main</b></summary>
                <blockquote>
                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                        <code><b>⦿ src.main</b></code>
                    <!-- java Submodule -->
                    <details>
                        <summary><b>java</b></summary>
                        <blockquote>
                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                <code><b>⦿ src.main.java</b></code>
                            <!-- com Submodule -->
                            <details>
                                <summary><b>com</b></summary>
                                <blockquote>
                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                        <code><b>⦿ src.main.java.com</b></code>
                                    <!-- smartcity Submodule -->
                                    <details>
                                        <summary><b>smartcity</b></summary>
                                        <blockquote>
                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                <code><b>⦿ src.main.java.com.smartcity</b></code>
                                            <!-- urban_management Submodule -->
                                            <details>
                                                <summary><b>urban_management</b></summary>
                                                <blockquote>
                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management</b></code>
                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                    <thead>
                                                        <tr style='background-color: #f8f9fa;'>
                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                        </tr>
                                                    </thead>
                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/UrbanManagementApplication.java'>UrbanManagementApplication.java</a></b></td>
                                                            <td style='padding: 8px;'>Main Spring Boot application entry point with @SpringBootApplication annotation, contains main() method that bootstraps the entire Urban Management system, initializes all configurations, and starts the embedded Tomcat server</code></td>
                                                        </tr>
                                                    </table>
                                                    <!-- infrastructure Submodule -->
                                                    <details>
                                                        <summary><b>infrastructure</b></summary>
                                                        <blockquote>
                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.infrastructure</b></code>
                                                            <!-- storage Submodule -->
                                                            <details>
                                                                <summary><b>storage</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.infrastructure.storage</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/storage/FileUploadResult.java'>FileUploadResult.java</a></b></td>
                                                                            <td style='padding: 8px;'>DTO class encapsulating file upload response - contains file URL, public ID, format, size, and upload status</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/storage/StorageFolders.java'>StorageFolders.java</a></b></td>
                                                                            <td style='padding: 8px;'>Enum defining storage directory structure: reports, tasks, avatars, evidences for organized file management</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/storage/FileStorageService.java'>FileStorageService.java</a></b></td>
                                                                            <td style='padding: 8px;'>Core file storage interface with methods for upload, download, delete, and file URL generation</code></td>
                                                                        </tr>
                                                                    </table>
                                                                    <!-- cloudinary Submodule -->
                                                                    <details>
                                                                        <summary><b>cloudinary</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.infrastructure.storage.cloudinary</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                 </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/storage/cloudinary/CloudinaryService.java'>CloudinaryService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Cloudinary implementation of FileStorageService - handles image uploads, transformations, and secure URL generation for report evidences and task proofs</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/storage/cloudinary/CloudinaryConfig.java'>CloudinaryConfig.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Configuration class initializing Cloudinary client with API credentials from application properties (cloud name, API key, API secret)</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                            <!-- config Submodule -->
                                                            <details>
                                                                <summary><b>config</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.infrastructure.config</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                         </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/config/RestTemplateConfig.java'>RestTemplateConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configures RestTemplate bean for making HTTP requests to external services (MapBox API, AI classification service)</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- redis Submodule -->
                                                            <details>
                                                                <summary><b>redis</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.infrastructure.redis</b></code>
                                                                    <!-- config Submodule -->
                                                                    <details>
                                                                        <summary><b>config</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.infrastructure.redis.config</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                 </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/config/RedisTemplateConfig.java'>RedisTemplateConfig.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Configures RedisTemplate with JSON serialization for storing/fetching Java objects as cached data (reports, users, tasks)</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- key Submodule -->
                                                                    <details>
                                                                        <summary><b>key</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.infrastructure.redis.key</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                 </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/key/DepartmentCacheKeys.java'>DepartmentCacheKeys.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Defines Redis key patterns for department-related caching: dept:{id}, dept:all, dept:by_category:{categoryId}</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/key/OfficeCacheKeys.java'>OfficeCacheKeys.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Defines Redis key patterns for office-related caching: office:{id}, office:dept:{deptId}, office:nearby:{lat}:{lng}</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/key/TaskCacheKeys.java'>TaskCacheKeys.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Defines Redis key patterns for task-related caching: task:{id}, task:office:{officeId}, task:status:{status}</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/key/CachePrefix.java'>CachePrefix.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum defining base cache prefixes: REPORT, USER, TASK, OFFICE, DEPARTMENT, CATEGORY, MAP for consistent key naming</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/key/ReportCacheKeys.java'>ReportCacheKeys.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Defines Redis key patterns for report-related caching: report:{id}, report:user:{userId}, report:nearby:{lat}:{lng}:{radius}</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/key/CacheKeyBuilder.java'>CacheKeyBuilder.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Utility class for building standardized Redis keys with prefix, suffix, and dynamic parameters</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/key/CategoryCacheKeys.java'>CategoryCacheKeys.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Defines Redis key patterns for category-related caching: cat:{id}, cat:all, cat:dept:{deptId}</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/key/UserCacheKeys.java'>UserCacheKeys.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Defines Redis key patterns for user-related caching: user:{id}, user:email:{email}, user:role:{role}</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/key/MapCacheKeys.java'>MapCacheKeys.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Defines Redis key patterns for geospatial caching: map:clusters, map:bounds:{sw_lat}:{sw_lng}:{ne_lat}:{ne_lng}</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- cache Submodule -->
                                                                    <details>
                                                                        <summary><b>cache</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.infrastructure.redis.cache</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                 </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/cache/UserCacheService.java'>UserCacheService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Service for caching User entities - supports get, put, evict operations with TTL management</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/cache/TaskCacheService.java'>TaskCacheService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Service for caching Task entities and task lists by office, status, and assignee</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/cache/CategoryCacheService.java'>CategoryCacheService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Service for caching Category entities and department-category mappings</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/cache/MapCacheService.java'>MapCacheService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Service for caching geospatial data - report clusters, office locations, and map tile data for MapBox visualization</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/cache/ReportCacheService.java'>ReportCacheService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Service for caching Report entities with spatial query results and nearby report lists</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/cache/RedisCacheService.java'>RedisCacheService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Generic base cache service providing CRUD operations, expiration management, and batch operations for Redis</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/cache/DepartmentCacheService.java'>DepartmentCacheService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Service for caching Department entities and department hierarchies</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/infrastructure/redis/cache/OfficeCacheService.java'>OfficeCacheService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Service for caching Office entities, nearest office queries, and office assignment data for auto-assignment logic</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                        </blockquote>
                                                    </details>
                                                    <!-- security Submodule -->
                                                    <details>
                                                        <summary><b>security</b></summary>
                                                        <blockquote>
                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.security</b></code>
                                                            <!-- jwt Submodule -->
                                                            <details>
                                                                <summary><b>jwt</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.security.jwt</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/jwt/JwtAuthenticationFilter.java'>JwtAuthenticationFilter.java</a></b></td>
                                                                            <td style='padding: 8px;'>OncePerRequestFilter that intercepts HTTP requests, extracts JWT from Authorization header, validates token, and sets authentication context in SecurityContextHolder</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/jwt/JwtTokenProvider.java'>JwtTokenProvider.java</a></b></td>
                                                                            <td style='padding: 8px;'>Core JWT utility class for generating tokens (access/refresh), validating signatures, extracting user info, and checking token expiration</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/jwt/JwtProperties.java'>JwtProperties.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configuration properties class mapping JWT settings from application.yml (secret key, expiration times, issuer, refresh token duration)</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- handler Submodule -->
                                                            <details>
                                                                <summary><b>handler</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.security.handler</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/handler/AccessDeniedHandlerImpl.java'>AccessDeniedHandlerImpl.java</a></b></td>
                                                                            <td style='padding: 8px;'>Handles 403 Forbidden errors when authenticated user lacks required permissions - returns custom error response with available roles</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/handler/AuthEntryPoint.java'>AuthEntryPoint.java</a></b></td>
                                                                            <td style='padding: 8px;'>Handles 401 Unauthorized errors when unauthenticated user accesses protected endpoints - triggers authentication process and returns error response</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- websocket Submodule -->
                                                            <details>
                                                                <summary><b>websocket</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.security.websocket</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/websocket/HttpHandshakeInterceptor.java'>HttpHandshakeInterceptor.java</a></b></td>
                                                                            <td style='padding: 8px;'>Intercepts WebSocket handshake requests, extracts JWT from query params or headers, validates token, and authenticates user before establishing WebSocket connection</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/websocket/JwtChannelInterceptor.java'>JwtChannelInterceptor.java</a></b></td>
                                                                            <td style='padding: 8px;'>ChannelInterceptor for STOMP messages - validates JWT on each inbound message and ensures user is authorized for destination (private/user queue)</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- user Submodule -->
                                                            <details>
                                                                <summary><b>user</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.security.user</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/user/CustomUserDetails.java'>CustomUserDetails.java</a></b></td>
                                                                            <td style='padding: 8px;'>Custom UserDetails implementation wrapping User entity - provides authorities, account status, and user metadata for Spring Security context</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/user/CustomUserDetailsService.java'>CustomUserDetailsService.java</a></b></td>
                                                                            <td style='padding: 8px;'>Implements UserDetailsService - loads user by username/email from database and returns CustomUserDetails for authentication</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- config Submodule -->
                                                            <details>
                                                                <summary><b>config</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.security.config</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/config/MethodSecurityConfig.java'>MethodSecurityConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Enables method-level security with @PreAuthorize and @PostAuthorize annotations - configures permission evaluator for role-based access control (RBAC)</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/security/config/SecurityConfig.java'>SecurityConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Main Spring Security configuration - defines public endpoints, protected routes, CORS settings, session management, and registers JWT authentication filter</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                        </blockquote>
                                                    </details>
                                                    <!-- config Submodule -->
                                                    <details>
                                                        <summary><b>config</b></summary>
                                                        <blockquote>
                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.config</b></code>
                                                            <!-- database Submodule -->
                                                            <details>
                                                                <summary><b>database</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.config.database</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/database/JpaConfig.java'>JpaConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configures JPA and Hibernate settings - enables auditing, sets naming strategy, configures batch processing, and defines Open Session In View behavior</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- websocket Submodule -->
                                                            <details>
                                                                <summary><b>websocket</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.config.websocket</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/websocket/WebSocketConfig.java'>WebSocketConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Enables WebSocket and STOMP messaging - configures message broker, application destination prefixes, and registers authentication interceptors for real-time notifications</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- properties Submodule -->
                                                            <details>
                                                                <summary><b>properties</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.config.properties</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/properties/CloudinaryProperties.java'>CloudinaryProperties.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configuration properties for Cloudinary - binds cloud_name, api_key, api_secret from application.yml for image upload service</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/properties/ReportProperties.java'>ReportProperties.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configuration properties for report processing - defines expiration time, auto-assignment rules, max retry attempts, and proximity radius for nearest office search</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/properties/UploadProperties.java'>UploadProperties.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configuration properties for file upload - max file size, allowed MIME types, upload directory, and image compression settings</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- async Submodule -->
                                                            <details>
                                                                <summary><b>async</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.config.async</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/async/AsyncConfig.java'>AsyncConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Enables asynchronous processing with @EnableAsync - configures thread pool size, queue capacity, and exception handler for async tasks like report processing and notifications</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- jackson Submodule -->
                                                            <details>
                                                                <summary><b>jackson</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.config.jackson</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/jackson/JacksonConfig.java'>JacksonConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configures Jackson ObjectMapper - sets date format, handles null values, registers JavaTimeModule, and configures property naming strategy for JSON serialization</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- openapi Submodule -->
                                                            <details>
                                                                <summary><b>openapi</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.config.openapi</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/openapi/OpenApiConfig.java'>OpenApiConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configures OpenAPI 3.0 (Swagger) documentation - sets API info, security scheme (JWT Bearer), server URLs, and contact details for API documentation at /swagger-ui</code></td>
                                                                        </tr>
                                                                    </tr>
                                                                </blockquote>
                                                            </details>
                                                            <!-- kafka Submodule -->
                                                            <details>
                                                                <summary><b>kafka</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.config.kafka</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/kafka/KafkaConsumerConfig.java'>KafkaConsumerConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configures Kafka consumers - sets bootstrap servers, group ID, deserializers, and concurrency settings for processing events like report_created, task_assigned, notification_sent</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/kafka/KafkaTopicConfig.java'>KafkaTopicConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Defines Kafka topic names and configurations - creates topics (reports, tasks, notifications) with partitions and replication factor for event-driven workflows</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- web Submodule -->
                                                            <details>
                                                                <summary><b>web</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.config.web</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/web/WebMvcConfig.java'>WebMvcConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configures Spring MVC - adds CORS mappings, registers interceptors, sets static resource handling, and configures path matching for REST APIs</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- cache Submodule -->
                                                            <details>
                                                                <summary><b>cache</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.config.cache</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/config/cache/RedisCacheConfig.java'>RedisCacheConfig.java</a></b></td>
                                                                            <td style='padding: 8px;'>Configures Redis as Spring Cache provider - sets cache manager, default TTL, cache names (reports, users, tasks), and serialization for cached objects</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                        </blockquote>
                                                    </details>
                                                    <!-- shared Submodule -->
                                                    <details>
                                                        <summary><b>shared</b></summary>
                                                        <blockquote>
                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.shared</b></code>
                                                            <!-- pagination Submodule -->
                                                            <details>
                                                                <summary><b>pagination</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.shared.pagination</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/pagination/PageMapper.java'>PageMapper.java</a></b></td>
                                                                            <td style='padding: 8px;'>Utility for mapping Spring Data Page objects to custom PageResponse DTOs with content transformation</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/pagination/PageRequestDto.java'>PageRequestDto.java</a></b></td>
                                                                            <td style='padding: 8px;'>DTO for pagination request parameters - contains page number, page size, sort field, and sort direction</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/pagination/PageResponse.java'>PageResponse.java</a></b></td>
                                                                            <td style='padding: 8px;'>Generic pagination response wrapper - contains content list, total elements, total pages, current page, and page size</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/pagination/PageableFactory.java'>PageableFactory.java</a></b></td>
                                                                            <td style='padding: 8px;'>Factory class for creating Pageable objects from PageRequestDto with validation and default values</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/pagination/SortResolver.java'>SortResolver.java</a></b></td>
                                                                            <td style='padding: 8px;'>Resolves sort parameters from request - maps field names to entity properties and validates allowed sort fields</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- exception Submodule -->
                                                            <details>
                                                                <summary><b>exception</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.shared.exception</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/exception/GlobalExceptionHandler.java'>GlobalExceptionHandler.java</a></b></td>
                                                                            <td style='padding: 8px;'>@ControllerAdvice handling all exceptions globally - returns standardized ErrorResponse with HTTP status codes for validation, business, and system errors</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/exception/ErrorResponse.java'>ErrorResponse.java</a></b></td>
                                                                            <td style='padding: 8px;'>Standardized error response DTO - contains timestamp, error code, message, path, and validation details</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/exception/AppException.java'>AppException.java</a></b></td>
                                                                            <td style='padding: 8px;'>Custom runtime exception for business logic errors - wraps ErrorCode and allows parameterized error messages</code></td>
                                                                        </tr>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/exception/ErrorCode.java'>ErrorCode.java</a></b></td>
                                                                            <td style='padding: 8px;'>Enum defining all error codes with HTTP status mapping - categories include USER, REPORT, TASK, OFFICE, AUTH, FILE, VALIDATION</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- util Submodule -->
                                                            <details>
                                                                <summary><b>util</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.shared.util</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/util/UpdateUtils.java'>UpdateUtils.java</a></b></td>
                                                                            <td style='padding: 8px;'>Utility for partial entity updates - copies non-null properties from source to target using reflection, useful for PATCH operations</code></td>
                                                                        </tr>
                                                                    </table>
                                                                </blockquote>
                                                            </details>
                                                            <!-- security Submodule -->
                                                            <details>
                                                                <summary><b>security</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.shared.security</b></code>
                                                                    <!-- authorization Submodule -->
                                                                    <details>
                                                                        <summary><b>authorization</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.shared.security.authorization</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                 </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/security/authorization/ReportAuthorizationService.java'>ReportAuthorizationService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Authorization service for report resources - checks if current user can view, edit, assign, or delete reports based on roles (RESIDENT, STAFF, ADMIN) and ownership</code></td>
                                                                                 </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                            <!-- messaging Submodule -->
                                                            <details>
                                                                <summary><b>messaging</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.shared.messaging</b></code>
                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                    <thead>
                                                                        <tr style='background-color: #f8f9fa;'>
                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                        </tr>
                                                                    </thead>
                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/messaging/KafkaTopics.java'>KafkaTopics.java</a></b></td>
                                                                            <td style='padding: 8px;'>Constants class defining Kafka topic names for event-driven workflows: REPORT_CREATED, TASK_ASSIGNED, NOTIFICATION_SENT, REPORT_STATUS_CHANGED</code></td>
                                                                        </tr>
                                                                    </table>
                                                                    <!-- envelope Submodule -->
                                                                    <details>
                                                                        <summary><b>envelope</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.shared.messaging.envelope</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                 </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/messaging/envelope/EventEnvelope.java'>EventEnvelope.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Generic event wrapper for Kafka messages - contains event type, timestamp, event ID, source, and payload for distributed tracing</code></td>
                                                                                 </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- event Submodule -->
                                                                    <details>
                                                                        <summary><b>event</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.shared.messaging.event</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                 </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/messaging/event/ReportAIAnalyzedEvent.java'>ReportAIAnalyzedEvent.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Event published after AI classification completes - contains report ID, detected category, confidence score, and extracted keywords</code></td>
                                                                                 </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/messaging/event/ReportStatusChangedEvent.java'>ReportStatusChangedEvent.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Event published when report status changes (PENDING -> VERIFIED -> ASSIGNED -> RESOLVED) - contains old status, new status, and changed by user</code></td>
                                                                                 </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/messaging/event/TaskAssignedEvent.java'>TaskAssignedEvent.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Event published when a task is assigned to staff/office - contains task ID, report ID, assigned office ID, and assignee details</code></td>
                                                                                 </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/messaging/event/ReportAttachmentsAddedEvent.java'>ReportAttachmentsAddedEvent.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Event published when attachments are added to a report - contains report ID, list of file URLs, and uploader info</code></td>
                                                                                 </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/messaging/event/ReportCreatedEvent.java'>ReportCreatedEvent.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Event published when a new report is created - contains report ID, resident ID, location, description, and attachments metadata</code></td>
                                                                                 </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/messaging/event/TaskStartedEvent.java'>TaskStartedEvent.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Event published when staff starts working on a task - contains task ID, started by user, start time, and estimated completion</code></td>
                                                                                 </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/shared/messaging/event/TaskCompletedEvent.java'>TaskCompletedEvent.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Event published when a task is completed - contains task ID, completion evidence URLs, resolution notes, and completed by user</code></td>
                                                                                 </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                        </blockquote>
                                                    </details>
                                                    <!-- modules Submodule -->
                                                    <details>
                                                        <summary><b>modules</b></summary>
                                                        <blockquote>
                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules</b></code>
                                                            <!-- report Submodule -->
                                                            <details>
                                                                <summary><b>report</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report</b></code>
                                                                    <!-- pagination Submodule -->
                                                                    <details>
                                                                        <summary><b>pagination</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.pagination</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/pagination/ReportSortField.java'>ReportSortField.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum defining sortable fields for report queries: createdAt, updatedAt, status, priority, distance from location</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- repository Submodule -->
                                                                    <details>
                                                                        <summary><b>repository</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.repository</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/repository/AttachmentRepository.java'>AttachmentRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for Attachment entity - provides methods to find attachments by report ID and delete by report</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/repository/ReportStatusHistoryRepository.java'>ReportStatusHistoryRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for ReportStatusHistory - retrieves status change timeline for a report and latest status entry</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/repository/ReportRepository.java'>ReportRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Core JPA repository for Report entity - includes complex queries with PostGIS spatial functions for nearby reports, filtering by status/category/office, and dashboard statistics</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- infrastructure Submodule -->
                                                                    <details>
                                                                        <summary><b>infrastructure</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.infrastructure</b></code>
                                                                            <!-- kafka Submodule -->
                                                                            <details>
                                                                                <summary><b>kafka</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.infrastructure.kafka</b></code>
                                                                                    <!-- consumer Submodule -->
                                                                                    <details>
                                                                                        <summary><b>consumer</b></summary>
                                                                                        <blockquote>
                                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.infrastructure.kafka.consumer</b></code>
                                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                                            <thead>
                                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                                </tr>
                                                                                            </thead>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/infrastructure/kafka/consumer/ReportAIConsumer.java'>ReportAIConsumer.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Kafka consumer listening for AI analysis results - updates report with predicted category and confidence score</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/infrastructure/kafka/consumer/ReportStatusConsumer.java'>ReportStatusConsumer.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Kafka consumer for status change events - processes notifications and triggers side effects like WebSocket pushes</code></td>
                                                                                                </tr>
                                                                                            </table>
                                                                                        </blockquote>
                                                                                    </details>
                                                                                    <!-- producer Submodule -->
                                                                                    <details>
                                                                                        <summary><b>producer</b></summary>
                                                                                        <blockquote>
                                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.infrastructure.kafka.producer</b></code>
                                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                                            <thead>
                                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                                </tr>
                                                                                            </thead>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/infrastructure/kafka/producer/ReportStatusProducer.java'>ReportStatusProducer.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Kafka producer for status change events - sends ReportStatusChangedMessage to notification service</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/infrastructure/kafka/producer/ReportStatusEventHandler.java'>ReportStatusEventHandler.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Application event handler - listens to internal status changes and delegates to Kafka producer for async processing</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/infrastructure/kafka/producer/ReportKafkaEventHandler.java'>ReportKafkaEventHandler.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Event handler for all report-related events - orchestrates sending events to appropriate Kafka topics</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/infrastructure/kafka/producer/ReportEventProducer.java'>ReportEventProducer.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Core Kafka producer for report events - sends ReportCreatedEvent and ReportAIAnalyzedEvent to Kafka</code></td>
                                                                                                </tr>
                                                                                            </table>
                                                                                        </blockquote>
                                                                                    </details>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- service Submodule -->
                                                                    <details>
                                                                        <summary><b>service</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.service</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                <tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/service/AttachmentService.java'>AttachmentService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for attachment management - upload, download, delete attachments linked to reports</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/service/ReportService.java'>ReportService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Main report service interface - create, update, assign, filter reports with role-based access control</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/service/ReportStatusHistoryService.java'>ReportStatusHistoryService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Service for tracking report status history - records changes and provides audit trail</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/service/ReportAIService.java'>ReportAIService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>AI classification service - calls external ML service to predict report category from description and images</code></td>
                                                                                </tr>
                                                                            </table>
                                                                            <!-- impl Submodule -->
                                                                            <details>
                                                                                <summary><b>impl</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.service.impl</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/service/impl/ReportCachedService.java'>ReportCachedService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Decorator service adding Redis caching to ReportService - caches report details, list queries, and auto-assign results</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/service/impl/ReportStatusHistoryServiceImpl.java'>ReportStatusHistoryServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of ReportStatusHistoryService - records each status transition with timestamp and user</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/service/impl/ReportServiceImpl.java'>ReportServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Core implementation of ReportService - business logic for CRUD, status management, auto-assignment using PostGIS nearest office query</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/service/impl/AttachmentServiceImpl.java'>AttachmentServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of AttachmentService - integrates with Cloudinary for cloud storage and manages attachment metadata</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- controller Submodule -->
                                                                    <details>
                                                                        <summary><b>controller</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.controller</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/controller/AttachmentController.java'>AttachmentController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for file upload/download - multipart file upload, delete attachment, get attachment URLs</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/controller/StaffReportController.java'>StaffReportController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Staff-only endpoints - view assigned reports, update status, add internal notes, reassign to other offices</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/controller/AdminReportController.java'>AdminReportController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Admin-only endpoints - review pending reports, manually assign/override categories, manage report visibility, delete reports</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/controller/CitizenReportController.java'>CitizenReportController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Citizen-facing endpoints - create report, view own reports, track status, cancel pending reports</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- entity Submodule -->
                                                                    <details>
                                                                        <summary><b>entity</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.entity</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/entity/Attachment.java'>Attachment.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA entity for report attachments - stores file URL, public ID, file type, size, and relation to Report</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/entity/ReportStatus.java'>ReportStatus.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum for report lifecycle: PENDING, VERIFIED, ASSIGNED, IN_PROGRESS, RESOLVED, REJECTED, CLOSED</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/entity/Priority.java'>Priority.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum for report priority: LOW, MEDIUM, HIGH, URGENT - determines SLA and assignment urgency</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/entity/ReportStatusHistory.java'>ReportStatusHistory.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Audit entity tracking status changes - stores previous status, new status, changed by user, timestamp, and notes</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/entity/Report.java'>Report.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Core JPA entity for citizen reports - includes PostGIS Point for location, status, priority, description, images, and relations to User, Category, Office, Task</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- messaging Submodule -->
                                                                    <details>
                                                                        <summary><b>messaging</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.messaging</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                <tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/messaging/Prediction.java'>Prediction.java</a></b></td>
                                                                                    <td style='padding: 8px;'>DTO for AI prediction result - contains category ID, category name, confidence score (0-1), and alternative suggestions</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/messaging/ReportAIAnalyzedMessage.java'>ReportAIAnalyzedMessage.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Kafka message payload for AI analysis result - contains report ID, predictions list, analysis timestamp</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/messaging/ReportStatusChangedMessage.java'>ReportStatusChangedMessage.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Kafka message payload for status change - report ID, old status, new status, changed by user, reason</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/messaging/ReportAttachmentsAddedMessage.java'>ReportAttachmentsAddedMessage.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Kafka message for attachment additions - report ID, list of attachment URLs, uploader info</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/messaging/ReportCreatedMessage.java'>ReportCreatedMessage.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Kafka message for new report creation - full report payload including location, description, images for async processing</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- dto Submodule -->
                                                                    <details>
                                                                        <summary><b>dto</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.dto</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/FinalCategoryType.java'>FinalCategoryType.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum for final category decision - AUTO_ASSIGNED, ADMIN_OVERRIDE, MANUAL_SELECTION</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/UpdateReportStatusRequest.java'>UpdateReportStatusRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for status update - contains new status, optional reason/note for the change</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/UpdateFinalCategoryRequest.java'>UpdateFinalCategoryRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for admin category override - new category ID and override reason</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/ReportResultDto.java'>ReportResultDto.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Projection DTO for report list queries - minimal fields for performance (id, title, status, location, created_at)</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/ReportDisplayStatus.java'>ReportDisplayStatus.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum mapping internal status to user-friendly display status with i18n keys and color codes</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/ReportCreateRequest.java'>ReportCreateRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for creating report - contains title, description, location coordinates, category hint, attachments</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/AttachmentProjection.java'>AttachmentProjection.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface projection for attachment data - id, fileUrl, fileName, fileType, createdAt</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/ReportStatusHistoryResponse.java'>ReportStatusHistoryResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Response DTO for status history - includes timeline entries with status, timestamp, user, notes</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/RecentReportData.java'>RecentReportData.java</a></b></td>
                                                                                    <td style='padding: 8px;'>DTO for dashboard recent reports - simplified view with status, location preview, days ago</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/CreateReportResponse.java'>CreateReportResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Response DTO after report creation - returns report ID, tracking URL, and estimated response time</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/ReportFilterRequest.java'>ReportFilterRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for filtering reports - status, category, date range, location bounding box, office ID</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/AttachmentCreateRequest.java'>AttachmentCreateRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for adding attachments - contains file data, description, and isPublic flag</code></td>
                                                                                </tr>
                                                                            </table>
                                                                            <!-- summary Submodule -->
                                                                            <details>
                                                                                <summary><b>summary</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.dto.summary</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/summary/ReportSummaryBaseResponse.java'>ReportSummaryBaseResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Base summary DTO with common fields: id, title, status, createdAt, location preview</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/summary/ReportStaffSummaryResponse.java'>ReportStaffSummaryResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Staff-specific summary - adds assigned office, priority, and days since assignment</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/summary/ReportCitizenSummaryResponse.java'>ReportCitizenSummaryResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Citizen-specific summary - adds tracking status and last update message</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/summary/ReportSummaryProjection.java'>ReportSummaryProjection.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Interface projection for efficient summary queries from database</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/summary/ReportAdminSummaryResponse.java'>ReportAdminSummaryResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Admin-specific summary - includes assigned office, AI confidence score, and admin notes</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/summary/AttachmentSummaryResponse.java'>AttachmentSummaryResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Summary DTO for attachments - thumbnail URL, file type, size, upload date</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                            <!-- detail Submodule -->
                                                                            <details>
                                                                                <summary><b>detail</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.dto.detail</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/detail/ReportAdminDetailResponse.java'>ReportAdminDetailResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Full detail DTO for admin - includes all fields, AI analysis, assignment history, internal notes</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/detail/ReportDetailBaseResponse.java'>ReportDetailBaseResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Base detail DTO with common fields for all user roles</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/detail/ReportStaffDetailResponse.java'>ReportStaffDetailResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Staff detail DTO - includes task assignments, evidence uploads, and office communication</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/detail/ReportCitizenDetailResponse.java'>ReportCitizenDetailResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Citizen detail DTO - includes tracking updates, response timeline, and contact info for assigned office</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/dto/detail/ReportDetailProjection.java'>ReportDetailProjection.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Interface projection for efficient full detail queries with joined entities</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- mapper Submodule -->
                                                                    <details>
                                                                        <summary><b>mapper</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.report.mapper</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/mapper/ReportDetailMapper.java'>ReportDetailMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper converting Report entity to role-specific detail DTOs (Citizen, Staff, Admin)</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/mapper/AttachmentMapper.java'>AttachmentMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for Attachment entity to AttachmentSummaryResponse and AttachmentProjection</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/mapper/ReportSummaryMapper.java'>ReportSummaryMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper converting Report to summary DTOs with role-based field filtering</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/report/mapper/ReportStatusHistoryMapper.java'>ReportStatusHistoryMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for ReportStatusHistory entity to ReportStatusHistoryResponse DTO</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                            <!-- category Submodule -->
                                                            <details>
                                                                <summary><b>category</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.category</b></code>
                                                                    <!-- pagination Submodule -->
                                                                    <details>
                                                                        <summary><b>pagination</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.category.pagination</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/pagination/CategorySortField.java'>CategorySortField.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum defining sortable fields for category queries: name, createdAt, updatedAt, reportCount</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- repository Submodule -->
                                                                    <details>
                                                                        <summary><b>repository</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.category.repository</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/repository/CategoryRepository.java'>CategoryRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for Category entity - provides methods to find by department, active status, and count reports per category</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- service Submodule -->
                                                                    <details>
                                                                        <summary><b>service</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.category.service</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/service/CategoryService.java'>CategoryService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for category management - CRUD operations, find by department, get active categories, check category usage</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                            <!-- impl Submodule -->
                                                                            <details>
                                                                                <summary><b>impl</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.category.service.impl</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/service/impl/CategoryCachedService.java'>CategoryCachedService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Decorator service adding Redis caching to CategoryService - caches category lists, details, and department mappings</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/service/impl/CategoryServiceImpl.java'>CategoryServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of CategoryService - business logic for category CRUD, validation (prevent delete if linked to reports), and department assignment</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- controller Submodule -->
                                                                    <details>
                                                                        <summary><b>controller</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.category.controller</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/controller/CategoryController.java'>CategoryController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for category management (admin only) - list categories, create, update, delete, get category details, and get categories by department</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- entity Submodule -->
                                                                    <details>
                                                                        <summary><b>entity</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.category.entity</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/entity/Category.java'>Category.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA entity for report categories - stores name, description, icon, color code, and relation to Department (many-to-one) and Reports (one-to-many)</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- dto Submodule -->
                                                                    <details>
                                                                        <summary><b>dto</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.category.dto</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/dto/CategoryUpdateRequest.java'>CategoryUpdateRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for updating category - fields: name, description, icon, colorCode, departmentId, isActive</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/dto/ActiveCategoryResponse.java'>ActiveCategoryResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Response DTO for active categories - simplified view with id, name, icon, color for frontend display</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/dto/CategoryDetailResponse.java'>CategoryDetailResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Full detail DTO for category - includes department info, report count, createdAt, updatedAt</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/dto/CategoryFilterRequest.java'>CategoryFilterRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for filtering categories - by department, active status, search keyword</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/dto/CategorySummaryResponse.java'>CategorySummaryResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Summary DTO for category lists - id, name, icon, color, reportCount, isActive</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/dto/CategoryCreateRequest.java'>CategoryCreateRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for creating new category - required fields: name, departmentId, optional: description, icon, colorCode</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/dto/CategoryProjection.java'>CategoryProjection.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface projection for efficient category queries - id, name, icon, color, department name</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- mapper Submodule -->
                                                                    <details>
                                                                        <summary><b>mapper</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.category.mapper</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/category/mapper/CategoryMapper.java'>CategoryMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for Category entity - converts to CategorySummaryResponse, CategoryDetailResponse, ActiveCategoryResponse</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                            <!-- dashboard Submodule -->
                                                            <details>
                                                                <summary><b>dashboard</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard</b></code>
                                                                    <!-- admin Submodule -->
                                                                    <details>
                                                                        <summary><b>admin</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.admin</b></code>
                                                                            <!-- service Submodule -->
                                                                            <details>
                                                                                <summary><b>service</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.admin.service</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/service/DashboardService.java'>DashboardService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Interface for admin dashboard - aggregates statistics, recent reports, and performance metrics</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/service/ResolvedReportService.java'>ResolvedReportService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Service for resolved reports analytics - tracks resolution time, trends, and staff performance</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/service/DashboardStatisticsService.java'>DashboardStatisticsService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Service for core dashboard statistics - total reports, by status, by category, by department, by priority</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/service/PriorityReportService.java'>PriorityReportService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Service for priority report tracking - lists urgent/high priority reports needing immediate attention</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                            <!-- controller Submodule -->
                                                                            <details>
                                                                                <summary><b>controller</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.admin.controller</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/controller/AdminDashboardController.java'>AdminDashboardController.java</a></b></td>
                                                                                            <td style='padding: 8px;'>REST endpoints for admin dashboard - get statistics overview, recent reports, priority reports, and resolved reports analytics</code></td>
                                                                                        </tr>
                                                                                    <tr>
                                                                                </blockquote>
                                                                            </details>
                                                                            <!-- dto Submodule -->
                                                                            <details>
                                                                                <summary><b>dto</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.admin.dto</b></code>
                                                                                    <!-- response Submodule -->
                                                                                    <details>
                                                                                        <summary><b>response</b></summary>
                                                                                        <blockquote>
                                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.admin.dto.response</b></code>
                                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                                            <thead>
                                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                                </tr>
                                                                                            </thead>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/dto/response/PriorityReportResponse.java'>PriorityReportResponse.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Response DTO for priority reports - includes report ID, title, priority level, days pending, assigned office</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/dto/response/ResolvedReportResponse.java'>ResolvedReportResponse.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Response DTO for resolved reports - includes resolution time, category, assigned staff, completion date</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/dto/response/ResolvedReportProjection.java'>ResolvedReportProjection.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Interface projection for efficient resolved reports queries - id, title, resolvedAt, category, resolutionTimeHours</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/dto/response/DashboardStatisticsResponse.java'>DashboardStatisticsResponse.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Complete dashboard statistics DTO - totals by status, category distribution, monthly trends, average resolution time</code></td>
                                                                                                </tr>
                                                                                            </table>
                                                                                        </blockquote>
                                                                                    </details>
                                                                                </blockquote>
                                                                            </details>
                                                                            <!-- mapper Submodule -->
                                                                            <details>
                                                                                <summary><b>mapper</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.admin.mapper</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </td>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/admin/mapper/ReportMapper.java'>ReportMapper.java</a></b></td>
                                                                                            <td style='padding: 8px;'>MapStruct mapper for dashboard - converts Report entity to PriorityReportResponse and ResolvedReportResponse</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- citizen Submodule -->
                                                                    <details>
                                                                        <summary><b>citizen</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.citizen</b></code>
                                                                            <!-- repository Submodule -->
                                                                            <details>
                                                                                <summary><b>repository</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.citizen.repository</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/repository/DashboardRepository.java'>DashboardRepository.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Repository for citizen dashboard queries - get nearby reports, category statistics for area, recent public reports</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                            <!-- service Submodule -->
                                                                            <details>
                                                                                <summary><b>service</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.citizen.service</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/service/CitizenDashboardService.java'>CitizenDashboardService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Interface for citizen dashboard - get nearby reports, category heatmap data, personal report statistics</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/service/DashboardQueryService.java'>DashboardQueryService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Query service for complex dashboard data - uses PostGIS for nearby queries and aggregates for statistics</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                    <!-- impl Submodule -->
                                                                                    <details>
                                                                                        <summary><b>impl</b></summary>
                                                                                        <blockquote>
                                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.citizen.service.impl</b></code>
                                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                                            <thead>
                                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                                </tr>
                                                                                            </thead>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/service/impl/CitizenDashboardServiceImpl.java'>CitizenDashboardServiceImpl.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Implementation of CitizenDashboardService - provides dashboard data for logged-in citizens</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/service/impl/DashboardQueryServiceImpl.java'>DashboardQueryServiceImpl.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Implementation of DashboardQueryService - executes complex JPQL/PostGIS queries for map and statistics data</code></td>
                                                                                                </tr>
                                                                                            </table>
                                                                                        </blockquote>
                                                                                    </details>
                                                                                </blockquote>
                                                                            </details>
                                                                            <!-- controller Submodule -->
                                                                            <details>
                                                                                <summary><b>controller</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.citizen.controller</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/controller/CitizenDashboardController.java'>CitizenDashboardController.java</a></b></td>
                                                                                            <td style='padding: 8px;'>REST endpoints for citizen dashboard - get nearby reports (with radius), category statistics, recent public reports, and personal report summary</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                            <!-- dto Submodule -->
                                                                            <details>
                                                                                <summary><b>dto</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.citizen.dto</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/dto/CategoryCountDto.java'>CategoryCountDto.java</a></b></td>
                                                                                            <td style='padding: 8px;'>DTO for category statistics - category name, report count, percentage of total</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/dto/CitizenDashboardResponse.java'>CitizenDashboardResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Complete citizen dashboard response - nearby reports, statistics, recent activity, personal summary</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/dto/RecentReportDto.java'>RecentReportDto.java</a></b></td>
                                                                                            <td style='padding: 8px;'>DTO for recent public reports - title, status, distance from user, days ago</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/dto/ReportSummaryDto.java'>ReportSummaryDto.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Personal report summary for citizen - total reports, pending count, resolved count, in-progress count</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                            <!-- mapper Submodule -->
                                                                            <details>
                                                                                <summary><b>mapper</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.dashboard.citizen.mapper</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/dashboard/citizen/mapper/DashboardMapper.java'>DashboardMapper.java</a></b></td>
                                                                                            <td style='padding: 8px;'>MapStruct mapper for citizen dashboard - converts Report entities to RecentReportDto and aggregates to CategoryCountDto</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                            <!-- department Submodule -->
                                                            <details>
                                                                <summary><b>department</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department</b></code>
                                                                    <!-- pagination Submodule -->
                                                                    <details>
                                                                        <summary><b>pagination</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.pagination</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/pagination/DepartmentOfficeSortField.java'>DepartmentOfficeSortField.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum defining sortable fields for office queries: name, address, createdAt, staffCount</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/pagination/DepartmentSortField.java'>DepartmentSortField.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum defining sortable fields for department queries: name, createdAt, reportCount, staffCount</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- repository Submodule -->
                                                                    <details>
                                                                        <summary><b>repository</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.repository</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/repository/DepartmentRepository.java'>DepartmentRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for Department entity - provides methods to find by name, get departments with categories, and count reports per department</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/repository/DepartmentOfficeRepository.java'>DepartmentOfficeRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for DepartmentOffice - includes PostGIS spatial query to find nearest office to a location within a department</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- service Submodule -->
                                                                    <details>
                                                                        <summary><b>service</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.service</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/service/DepartmentOfficeService.java'>DepartmentOfficeService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for office management - CRUD operations, find nearest office (PostGIS), assign users to offices</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/service/DepartmentStatsService.java'>DepartmentStatsService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Service for department performance analytics - report volume, resolution rate, average handling time per department</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/service/DepartmentService.java'>DepartmentService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for department management - CRUD operations, find by category, get department hierarchy</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                            <!-- impl Submodule -->
                                                                            <details>
                                                                                <summary><b>impl</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.service.impl</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/service/impl/DepartmentOfficeServiceImpl.java'>DepartmentOfficeServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of DepartmentOfficeService - office CRUD, nearest office calculation using PostGIS ST_Distance</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/service/impl/DepartmentCachedService.java'>DepartmentCachedService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Decorator service adding Redis caching to DepartmentService - caches department lists, hierarchy, and office assignments</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/service/impl/DepartmentServiceImpl.java'>DepartmentServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of DepartmentService - business logic for department CRUD, validation (prevent delete if has categories/offices)</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/service/impl/OfficeCacheServiceImpl.java'>OfficeCacheServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of OfficeCacheService - caches office data including nearest office query results</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- controller Submodule -->
                                                                    <details>
                                                                        <summary><b>controller</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.controller</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/controller/DepartmentController.java'>DepartmentController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for department management (admin only) - create, update, delete, list departments, get department details</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/controller/DepartmentOfficeController.java'>DepartmentOfficeController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for office management - create/update/delete offices, find nearest office by coordinates, assign users to offices</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/controller/DepartmentStatsController.java'>DepartmentStatsController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for department statistics (admin only) - get report counts, resolution rates, performance metrics by department</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- entity Submodule -->
                                                                    <details>
                                                                        <summary><b>entity</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.entity</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/entity/Department.java'>Department.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA entity for government departments - stores name, description, contact info, and relations to Category (one-to-many) and DepartmentOffice (one-to-many)</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/entity/DepartmentOffice.java'>DepartmentOffice.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA entity for physical offices - stores address, PostGIS Point location, phone, email, and relation to Department (many-to-one) and User (one-to-many)</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- dto Submodule -->
                                                                    <details>
                                                                        <summary><b>dto</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.dto</b></code>
                                                                            <!-- department Submodule -->
                                                                            <details>
                                                                                <summary><b>department</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.dto.department</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/department/DepartmentDetailResponse.java'>DepartmentDetailResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Full detail DTO for department - includes categories list, offices list, report count, and contact info</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/department/CreateDepartmentResponse.java'>CreateDepartmentResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Response DTO after department creation - returns created department ID and summary info</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/department/UpdateDepartmentRequest.java'>UpdateDepartmentRequest.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Request DTO for updating department - fields: name, description, contactEmail, contactPhone, isActive</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/department/DepartmentResponse.java'>DepartmentResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Base response DTO for department - id, name, description, isActive</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/department/UpdateDepartmentResponse.java'>UpdateDepartmentResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Response DTO after department update - returns updated department details</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/department/DepartmentFilterRequest.java'>DepartmentFilterRequest.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Request DTO for filtering departments - by name, active status, hasCategories, date range</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/department/CreateDepartmentRequest.java'>CreateDepartmentRequest.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Request DTO for creating department - required: name, optional: description, contactEmail, contactPhone</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/department/DepartmentSummaryResponse.java'>DepartmentSummaryResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Summary DTO for department lists - id, name, reportCount, officeCount, isActive</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                            <!-- office Submodule -->
                                                                            <details>
                                                                                <summary><b>office</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.dto.office</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/office/DepartmentOfficeResponse.java'>DepartmentOfficeResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Response DTO for office - id, name, address, location coordinates, phone, department info, staff count</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/office/CreateOfficeResponse.java'>CreateOfficeResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Response DTO after office creation - returns created office ID and summary info</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/office/OfficeUserCount.java'>OfficeUserCount.java</a></b></td>
                                                                                            <td style='padding: 8px;'>DTO for office user statistics - office ID, office name, staff count, admin count</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/office/AddUserToOfficeRequest.java'>AddUserToOfficeRequest.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Request DTO for assigning user to office - userId, officeId, role within office (STAFF/MANAGER)</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/office/DepartmentStatsResponse.java'>DepartmentStatsResponse.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Response DTO for department statistics - total reports, resolved count, average resolution time, reports by priority</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/dto/office/DepartmentOfficeRequest.java'>DepartmentOfficeRequest.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Request DTO for creating/updating office - name, address, latitude, longitude, departmentId, phone, email</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- mapper Submodule -->
                                                                    <details>
                                                                        <summary><b>mapper</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.department.mapper</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/mapper/DepartmentMapper.java'>DepartmentMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for Department entity - converts to DepartmentSummaryResponse, DepartmentDetailResponse, DepartmentResponse</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/department/mapper/DepartmentOfficeMapper.java'>DepartmentOfficeMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for DepartmentOffice entity - converts to DepartmentOfficeResponse and handles location coordinate mapping</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                            <!-- user Submodule -->
                                                            <details>
                                                                <summary><b>user</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.user</b></code>
                                                                    <!-- pagination Submodule -->
                                                                    <details>
                                                                        <summary><b>pagination</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.user.pagination</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/pagination/UserSortField.java'>UserSortField.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum defining sortable fields for user queries: fullName, email, createdAt, lastLogin, role</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- repository Submodule -->
                                                                    <details>
                                                                        <summary><b>repository</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.user.repository</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/repository/RoleRepository.java'>RoleRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for Role entity - provides method to find by role name (ROLE_CITIZEN, ROLE_STAFF, ROLE_ADMIN)</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/repository/UserRepository.java'>UserRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for User entity - provides methods to find by email, find by role, find by office, and exists by email</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- service Submodule -->
                                                                    <details>
                                                                        <summary><b>service</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.user.service</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/service/RoleService.java'>RoleService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for role management - get all roles, find by name, assign role to user</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/service/AuthService.java'>AuthService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for authentication - login, register, refresh token, logout, change password</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/service/UserService.java'>UserService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for user management - CRUD operations, find by role, find by office, update profile, change user status</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                            <!-- impl Submodule -->
                                                                            <details>
                                                                                <summary><b>impl</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.user.service.impl</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/service/impl/AuthServiceImpl.java'>AuthServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of AuthService - handles authentication logic, password encoding, JWT generation and validation</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/service/impl/RoleServiceImpl.java'>RoleServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of RoleService - role CRUD, assign/remove roles from users</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/service/impl/UserServiceImpl.java'>UserServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of UserService - user CRUD, validation (unique email), password management, role assignment</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/service/impl/UserCachedService.java'>UserCachedService.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Decorator service adding Redis caching to UserService - caches user profiles, role assignments, and user lists</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- controller Submodule -->
                                                                    <details>
                                                                        <summary><b>controller</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.user.controller</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/controller/AuthController.java'>AuthController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for authentication - login, register citizen, refresh token, logout, forgot/reset password</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/controller/RoleController.java'>RoleController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for role management (admin only) - list roles, create role, update role, assign role to user</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/controller/UserController.java'>UserController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for user management (admin only) - list users, create staff/admin, update user, delete user, get user details</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- entity Submodule -->
                                                                    <details>
                                                                        <summary><b>entity</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.user.entity</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                <tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/entity/User.java'>User.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Core JPA entity for system users - stores email, password, full name, phone, address, avatar URL, status, and relations to Role (many-to-many) and DepartmentOffice (many-to-one)</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/entity/Role.java'>Role.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA entity for user roles - stores name (ROLE_CITIZEN, ROLE_STAFF, ROLE_ADMIN) and description</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/entity/RoleName.java'>RoleName.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum defining available role names: ROLE_CITIZEN, ROLE_STAFF, ROLE_ADMIN, ROLE_MANAGER</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- dto Submodule -->
                                                                    <details>
                                                                        <summary><b>dto</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.user.dto</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/dto/LoginResponse.java'>LoginResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Response DTO for login - contains accessToken, refreshToken, tokenType, user summary, and expiration</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/dto/CreateUserResponse.java'>CreateUserResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Response DTO after user creation - returns user ID, email, roles, and temporary password (if auto-generated)</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/dto/CreateUserRequest.java'>CreateUserRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for creating user (admin only) - email, password, fullName, phone, roles, officeId</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/dto/LoginRequest.java'>LoginRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for login - email/username and password</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/dto/UserDetailResponse.java'>UserDetailResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Full detail DTO for user - includes all fields, roles, office assignment, created/updated timestamps</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/dto/RegisterRequest.java'>RegisterRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for citizen self-registration - email, password, fullName, phone, address</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/dto/UserSummaryResponse.java'>UserSummaryResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Summary DTO for user lists - id, fullName, email, role, office name, status, lastLogin</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/dto/RoleResponse.java'>RoleResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Response DTO for role - id, name, description, userCount</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/dto/UserFilterRequest.java'>UserFilterRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for filtering users - by role, office, status, date range, search keyword</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- mapper Submodule -->
                                                                    <details>
                                                                        <summary><b>mapper</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.user.mapper</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/user/mapper/UserMapper.java'>UserMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for User entity - converts to UserSummaryResponse, UserDetailResponse, and handles password encoding during creation</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                            <!-- notification Submodule -->
                                                            <details>
                                                                <summary><b>notification</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification</b></code>
                                                                    <!-- pagination Submodule -->
                                                                    <details>
                                                                        <summary><b>pagination</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.pagination</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/pagination/NotificationSortField.java'>NotificationSortField.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum defining sortable fields for notification queries: createdAt, readAt, type, priority</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- repository Submodule -->
                                                                    <details>
                                                                        <summary><b>repository</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.repository</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/repository/NotificationRepository.java'>NotificationRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for Notification entity - provides methods to find unread by user, mark as read, delete old notifications</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- infrastructure Submodule -->
                                                                    <details>
                                                                        <summary><b>infrastructure</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.infrastructure</b></code>
                                                                            <!-- kafka Submodule -->
                                                                            <details>
                                                                                <summary><b>kafka</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.infrastructure.kafka</b></code>
                                                                                    <!-- consumer Submodule -->
                                                                                    <details>
                                                                                        <summary><b>consumer</b></summary>
                                                                                        <blockquote>
                                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.infrastructure.kafka.consumer</b></code>
                                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                                            <thead>
                                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                                </tr>
                                                                                            </thead>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/infrastructure/kafka/consumer/TaskStaffNotificationConsumer.java'>TaskStaffNotificationConsumer.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Kafka consumer for task assignment events - sends notifications to staff when new tasks are assigned</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/infrastructure/kafka/consumer/ReportCreatedConsumer.java'>ReportCreatedConsumer.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Kafka consumer for report creation events - triggers admin/staff notifications based on category and department</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/infrastructure/kafka/consumer/ReportCitizenNotificationConsumer.java'>ReportCitizenNotificationConsumer.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Kafka consumer for status change events - sends updates to citizens about their report progress</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/infrastructure/kafka/consumer/ReportAdminNotificationConsumer.java'>ReportAdminNotificationConsumer.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Kafka consumer for critical report events - notifies admins about urgent reports needing review</code></td>
                                                                                                </tr>
                                                                                            </table>
                                                                                        </blockquote>
                                                                                    </details>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- service Submodule -->
                                                                    <details>
                                                                        <summary><b>service</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.service</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                <tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/service/NotificationService.java'>NotificationService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for notification management - send notifications, mark as read/unread, get user notifications, delete notifications</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                            <!-- impl Submodule -->
                                                                            <details>
                                                                                <summary><b>impl</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.service.impl</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/service/impl/NotificationServiceImpl.java'>NotificationServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of NotificationService - creates in-app notifications, stores in database, integrates with WebSocket for real-time delivery</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- builder Submodule -->
                                                                    <details>
                                                                        <summary><b>builder</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.builder</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/builder/NotificationContentBuilder.java'>NotificationContentBuilder.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Builder utility for creating notification content templates - constructs messages based on event type (report_status, task_assigned, comment_added)</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- controller Submodule -->
                                                                    <details>
                                                                        <summary><b>controller</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.controller</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/controller/NotificationController.java'>NotificationController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for notifications - get user notifications, mark as read, mark all as read, delete notification, get unread count</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- realtime Submodule -->
                                                                    <details>
                                                                        <summary><b>realtime</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.realtime</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/realtime/NotificationRealtimePublisher.java'>NotificationRealtimePublisher.java</a></b></td>
                                                                                    <td style='padding: 8px;'>WebSocket publisher for real-time notifications - pushes notifications to specific user sessions via STOMP</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- entity Submodule -->
                                                                    <details>
                                                                        <summary><b>entity</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.entity</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/entity/NotificationType.java'>NotificationType.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum for notification types: REPORT_STATUS, TASK_ASSIGNED, TASK_UPDATED, SYSTEM_ALERT, COMMENT_ADDED</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/entity/Notification.java'>Notification.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA entity for user notifications - stores title, content, type, isRead flag, target URL, and relation to User</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- dto Submodule -->
                                                                    <details>
                                                                        <summary><b>dto</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.notification.dto</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/notification/dto/NotificationResponse.java'>NotificationResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Response DTO for notification - id, title, content, type, isRead, createdAt, targetUrl</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                            <!-- location Submodule -->
                                                            <details>
                                                                <summary><b>location</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.location</b></code>
                                                                    <!-- service Submodule -->
                                                                    <details>
                                                                        <summary><b>service</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.location.service</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/service/MapService.java'>MapService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for map-related services - get reports/offices/tasks for map bounds, geocode addresses, calculate distances</code></td>
                                                                                </tr>
                                                                            </table>
                                                                            <!-- impl Submodule -->
                                                                            <details>
                                                                                <summary><b>impl</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.location.service.impl</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/service/impl/MapCacheServiceImpl.java'>MapCacheServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Decorator service adding Redis caching to MapService - caches map tile data, geocoding results, and feature collections</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/service/impl/MapServiceImpl.java'>MapServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of MapService - uses PostGIS spatial queries to fetch map features within bounds, integrates with Mapbox for geocoding and routing</code></td>
                                                                                        </tr>
                                                                                    </tr>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- client Submodule -->
                                                                    <details>
                                                                        <summary><b>client</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.location.client</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/client/MapboxClient.java'>MapboxClient.java</a></b></td>
                                                                                    <td style='padding: 8px;'>HTTP client for Mapbox API - geocoding (forward/reverse), static maps, directions, and matrix calculations</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/client/RoutingClient.java'>RoutingClient.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Mapbox routing client - calculates optimal routes between locations, distance matrix for auto-assignment optimization</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- controller Submodule -->
                                                                    <details>
                                                                        <summary><b>controller</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.location.controller</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/controller/MapController.java'>MapController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for map visualization - get map features within bounds (reports, offices, tasks), geocode address, get route between locations</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- dto Submodule -->
                                                                    <details>
                                                                        <summary><b>dto</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.location.dto</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                <tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/dto/MapStaffFilterRequest.java'>MapStaffFilterRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Filter request DTO for staff map - bounds, status filters, office filters, category filters</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/dto/MapboxMatrixResponse.java'>MapboxMatrixResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Response DTO from Mapbox Matrix API - contains distance/duration matrices for multiple locations</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/dto/MapFilterRequest.java'>MapFilterRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Base filter request DTO for map queries - bounds (SW/NE coordinates), zoom level, layer selection</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/dto/MapDataResponse.java'>MapDataResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Response DTO containing GeoJSON feature collections for reports, offices, and tasks on map</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/dto/MapFeatureResponse.java'>MapFeatureResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>GeoJSON feature response DTO - includes geometry (Point) and properties (id, type, status, title)</code></td>
                                                                                </tr>
                                                                            </table>
                                                                            <!-- projection Submodule -->
                                                                            <details>
                                                                                <summary><b>projection</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.location.dto.projection</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/dto/projection/OfficeMapProjection.java'>OfficeMapProjection.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Interface projection for office map data - id, name, address, location coordinates, department name</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/dto/projection/ReportMapProjection.java'>ReportMapProjection.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Interface projection for report map data - id, title, status, priority, location coordinates, category color</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/location/dto/projection/TaskMapProjection.java'>TaskMapProjection.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Interface projection for task map data - id, title, status, assigned office, due date, location coordinates</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                            <!-- task Submodule -->
                                                            <details>
                                                                <summary><b>task</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task</b></code>
                                                                    <!-- pagination Submodule -->
                                                                    <details>
                                                                        <summary><b>pagination</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.pagination</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/pagination/TaskSortField.java'>TaskSortField.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum defining sortable fields for task queries: createdAt, dueDate, status, priority, assignedTo</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- repository Submodule -->
                                                                    <details>
                                                                        <summary><b>repository</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.repository</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/repository/TaskRepository.java'>TaskRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for Task entity - provides methods to find by report, by assigned office, by assigned user, by status, and tasks due soon</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/repository/TaskEvidenceRepository.java'>TaskEvidenceRepository.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA repository for TaskEvidence - provides methods to find evidence by task ID, delete by task</code></td>
                                                                                </td>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- infrastructure Submodule -->
                                                                    <details>
                                                                        <summary><b>infrastructure</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.infrastructure</b></code>
                                                                            <!-- kafka Submodule -->
                                                                            <details>
                                                                                <summary><b>kafka</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.infrastructure.kafka</b></code>
                                                                                    <!-- producer Submodule -->
                                                                                    <details>
                                                                                        <summary><b>producer</b></summary>
                                                                                        <blockquote>
                                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.infrastructure.kafka.producer</b></code>
                                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                                            <thead>
                                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                                </tr>
                                                                                            </thead>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/infrastructure/kafka/producer/TaskKafkaEventHandler.java'>TaskKafkaEventHandler.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Event handler for task events - listens to internal application events and delegates to Kafka producer for async processing</code></td>
                                                                                                </tr>
                                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/infrastructure/kafka/producer/TaskEventProducer.java'>TaskEventProducer.java</a></b></td>
                                                                                                    <td style='padding: 8px;'>Kafka producer for task events - sends TaskAssignedMessage, TaskStartedMessage, TaskCompletedMessage to Kafka</code></td>
                                                                                                </tr>
                                                                                            <tr>
                                                                                        </blockquote>
                                                                                    </details>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- service Submodule -->
                                                                    <details>
                                                                        <summary><b>service</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.service</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/service/TaskService.java'>TaskService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for task management - create task from report, assign to staff/office, update status, start/completed task, get task by report</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/service/TaskEvidenceService.java'>TaskEvidenceService.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface for task evidence management - upload evidence (images/documents), get evidence by task, delete evidence</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                            <!-- impl Submodule -->
                                                                            <details>
                                                                                <summary><b>impl</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.service.impl</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/service/impl/TaskCacheServiceImpl.java'>TaskCacheServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Decorator service adding Redis caching to TaskService - caches task details, task lists by office, and task counts</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/service/impl/TaskEvidenceServiceImpl.java'>TaskEvidenceServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of TaskEvidenceService - integrates with Cloudinary for evidence upload, stores metadata</code></td>
                                                                                        </tr>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/service/impl/TaskServiceImpl.java'>TaskServiceImpl.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Implementation of TaskService - business logic for task CRUD, status transitions (PENDING → IN_PROGRESS → COMPLETED), validation, and event publishing</code></td>
                                                                                        </tr>
                                                                                    </tr>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- controller Submodule -->
                                                                    <details>
                                                                        <summary><b>controller</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.controller</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/controller/TaskController.java'>TaskController.java</a></b></td>
                                                                                    <td style='padding: 8px;'>REST endpoints for task management - list tasks (by office/status), get task details, start task, complete task with evidence, reassign task</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- validator Submodule -->
                                                                    <details>
                                                                        <summary><b>validator</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.validator</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/validator/TaskValidator.java'>TaskValidator.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Validator for task operations - checks if user is authorized to modify task, validates status transitions, ensures evidence is provided on completion</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- entity Submodule -->
                                                                    <details>
                                                                        <summary><b>entity</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.entity</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/entity/TaskEvidence.java'>TaskEvidence.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA entity for task completion evidence - stores file URL, public ID, file type, uploaded by user, upload timestamp</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/entity/TaskStatus.java'>TaskStatus.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Enum for task lifecycle: PENDING, IN_PROGRESS, COMPLETED, CANCELLED, REOPENED</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/entity/Task.java'>Task.java</a></b></td>
                                                                                    <td style='padding: 8px;'>JPA entity for tasks - stores title, description, status, priority, due date, assigned to user/office, and relations to Report (many-to-one)</code></td>
                                                                                </tr>
                                                                            </table>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- messaging Submodule -->
                                                                    <details>
                                                                        <summary><b>messaging</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.messaging</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/messaging/TaskStartedMessage.java'>TaskStartedMessage.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Kafka message payload for task started - contains task ID, report ID, started by user, start time</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/messaging/TaskAssignedMessage.java'>TaskAssignedMessage.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Kafka message payload for task assignment - contains task ID, report ID, assigned office ID, assigned user ID, assigned timestamp</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/messaging/TaskCompletedMessage.java'>TaskCompletedMessage.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Kafka message payload for task completion - contains task ID, evidence URLs, resolution notes, completed by user, completion timestamp</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- dto Submodule -->
                                                                    <details>
                                                                        <summary><b>dto</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.dto</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/dto/CompleteTaskRequest.java'>CompleteTaskRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for completing task - contains resolution notes, evidence files list, completion timestamp</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/dto/TaskDetailResponse.java'>TaskDetailResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Full detail DTO for task - includes report info, assigned office/staff, evidence list, status history, timestamps</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/dto/TaskSummaryResponse.java'>TaskSummaryResponse.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Summary DTO for task lists - id, title, status, priority, due date, assigned office, report ID</code></td>
                                                                                </td>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/dto/TaskProjection.java'>TaskProjection.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface projection for efficient task queries - id, title, status, createdAt, dueDate, assignedTo</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/dto/TaskFilterRequest.java'>TaskFilterRequest.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Request DTO for filtering tasks - by status, priority, office, assigned user, date range</code></td>
                                                                                </td>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/dto/EvidenceDto.java'>EvidenceDto.java</a></b></td>
                                                                                    <td style='padding: 8px;'>DTO for task evidence - file URL, file type, uploaded by, upload timestamp, description</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/dto/TaskDetailProjection.java'>TaskDetailProjection.java</a></b></td>
                                                                                    <td style='padding: 8px;'>Interface projection for efficient task detail queries with joined report and office data</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                    <!-- mapper Submodule -->
                                                                    <details>
                                                                        <summary><b>mapper</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.main.java.com.smartcity.urban_management.modules.task.mapper</b></code>
                                                                            <table style='width: 100%; border-collapse: collapse;'>
                                                                            <thead>
                                                                                <tr style='background-color: #f8f9fa;'>
                                                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                </tr>
                                                                            </thead>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/mapper/TaskDetailMapper.java'>TaskDetailMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for Task entity to TaskDetailResponse - includes nested mapping for report and office</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/mapper/TaskSummaryMapper.java'>TaskSummaryMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for Task entity to TaskSummaryResponse - optimized for list views</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/mapper/EvidenceMapper.java'>EvidenceMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for TaskEvidence entity to EvidenceDto</code></td>
                                                                                </tr>
                                                                                <tr style='border-bottom: 1px solid #eee;'>
                                                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/java/com/smartcity/urban_management/modules/task/mapper/TaskResultMapper.java'>TaskResultMapper.java</a></b></td>
                                                                                    <td style='padding: 8px;'>MapStruct mapper for task query results to various DTOs based on role (staff, admin, citizen)</code></td>
                                                                                </tr>
                                                                            </tr>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                        </blockquote>
                                                    </details>
                                                </blockquote>
                                            </details>
                                        </blockquote>
                                    </details>
                                </blockquote>
                            </details>
                        </blockquote>
                    </details>
                    <!-- resources Submodule -->
                    <details>
                        <summary><b>resources</b></summary>
                        <blockquote>
                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                <code><b>⦿ src.main.resources</b></code>
                            <table style='width: 100%; border-collapse: collapse;'>
                            <thead>
                                <tr style='background-color: #f8f9fa;'>
                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                </tr>
                            </thead>
                                <tr style='border-bottom: 1px solid #eee;'>
                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/application-redis-prod.yml'>application-redis-prod.yml</a></b></td>
                                    <td style='padding: 8px;'>Production Redis configuration - cluster endpoints, SSL enabled, connection pool size (max 50), and TTL settings for cache</code></td>
                                </tr>
                                <tr style='border-bottom: 1px solid #eee;'>
                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/application-local.yml'>application-local.yml</a></b></td>
                                    <td style='padding: 8px;'>Local development configuration - H2 database for testing, debug logging level, hot-reload enabled, and mock external services</code></td>
                                </tr>
                                <tr style='border-bottom: 1px solid #eee;'>
                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/application.yaml'>application.yaml</a></b></td>
                                    <td style='padding: 8px;'>Main application configuration - server port (8080), Spring profiles, JPA/Hibernate settings, multipart file limits, and common properties</code></td>
                                </tr>
                                <tr style='border-bottom: 1px solid #eee;'>
                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/application-redis.yml'>application-redis.yml</a></b></td>
                                    <td style='padding: 8px;'>Base Redis configuration - host (localhost), port (6379), password placeholder, default TTL (3600s), and cache names definitions</code></td>
                                </tr>
                                <tr style='border-bottom: 1px solid #eee;'>
                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/application-dev.yml'>application-dev.yml</a></b></td>
                                    <td style='padding: 8px;'>Development environment configuration - PostgreSQL dev database, SQL logging enabled, dev tools active, and CORS allowed origins for localhost:3000</code></td>
                                </tr>
                                <tr style='border-bottom: 1px solid #eee;'>
                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/application-kafka-prod.yml'>application-kafka-prod.yml</a></b></td>
                                    <td style='padding: 8px;'>Production Kafka configuration - broker addresses (cluster), SSL/SASL authentication, compression type (snappy), and replication factor (3)</code></td>
                                </tr>
                                <tr style='border-bottom: 1px solid #eee;'>
                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/application-prod.yml'>application-prod.yml</a></b></td>
                                    <td style='padding: 8px;'>Production environment configuration - PostgreSQL prod database (RDS), connection pooling (HikariCP), log level WARN, and performance tuning</code></td>
                                </tr>
                                <tr style='border-bottom: 1px solid #eee;'>
                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/application-kafka.yml'>application-kafka.yml</a></b></td>
                                    <td style='padding: 8px;'>Base Kafka configuration - bootstrap servers, consumer/producer properties, serializers (JSON), and topic names (reports, tasks, notifications)</code></td>
                                </tr>
                            </table>
                            <!-- db Submodule -->
                            <details>
                                <summary><b>db</b></summary>
                                <blockquote>
                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                        <code><b>⦿ src.main.resources.db</b></code>
                                    <!-- migration Submodule -->
                                    <details>
                                        <summary><b>migration</b></summary>
                                        <blockquote>
                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                <code><b>⦿ src.main.resources.db.migration</b></code>
                                            <table style='width: 100%; border-collapse: collapse;'>
                                            <thead>
                                                <tr style='background-color: #f8f9fa;'>
                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                </tr>
                                            </thead>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration/V4__add_columns_and_constraints.sql'>V4__add_columns_and_constraints.sql</a></b></td>
                                                    <td style='padding: 8px;'>Adds additional columns (resolution_note, resolved_at, assigned_office_id) and foreign key constraints to reports table</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration/V2__create_base_tables.sql'>V2__create_base_tables.sql</a></b></td>
                                                    <td style='padding: 8px;'>Creates core tables: users, roles, departments, categories, reports with PostGIS geometry column (location) and indexes</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration/V5__seed_data.sql'>V5__seed_data.sql</a></b></td>
                                                    <td style='padding: 8px;'>Populates initial reference data: default admin user, department categories (Transportation, Sanitation, Public Safety, Infrastructure), and report types</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration/V3__create_additional_tables.sql'>V3__create_additional_tables.sql</a></b></td>
                                                    <td style='padding: 8px;'>Creates additional tables: tasks, task_evidences, notifications, report_status_history, department_offices for full workflow management</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration/V1__init_extensions.sql'>V1__init_extensions.sql</a></b></td>
                                                    <td style='padding: 8px;'>Initializes PostgreSQL extensions: PostGIS (geometry/geography types, spatial indexes), UUID generation, and pgcrypto for password hashing</code></td>
                                                </tr>
                                            </table>
                                        </blockquote>
                                    </details>
                                    <!-- migration-backup Submodule -->
                                    <details>
                                        <summary><b>migration-backup</b></summary>
                                        <blockquote>
                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                <code><b>⦿ src.main.resources.db.migration-backup</b></code>
                                            <table style='width: 100%; border-collapse: collapse;'>
                                            <thead>
                                                <tr style='background-color: #f8f9fa;'>
                                                    <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                    <th style='text-align: left; padding: 8px;'>Summary</th>
                                                </tr>
                                            </thead>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration-backup/V7__migrate_task_to_department_office.sql'>V7__migrate_task_to_department_office.sql</a></b></td>
                                                    <td style='padding: 8px;'>Migration script: restructures task relationships to link with department_offices instead of direct department assignment (backup version)</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration-backup/V3__seed_departments_and_categories.sql'>V3__seed_departments_and_categories.sql</a></b></td>
                                                    <td style='padding: 8px;'>Legacy seed script: initial department and category data (preserved for rollback compatibility)</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration-backup/V8__create_task_evidences.sql'>V8__create_task_evidences.sql</a></b></td>
                                                    <td style='padding: 8px;'>Creates task_evidences table for storing completion proof documents and images uploaded by staff (backup version)</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration-backup/V2__create_base_tables.sql'>V2__create_base_tables.sql</a></b></td>
                                                    <td style='padding: 8px;'>Legacy base tables script with original schema before optimizations (backup version)</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration-backup/V5__update_task_relationship_to_many_to_one.sql'>V5__update_task_relationship_to_many_to_one.sql</a></b></td>
                                                    <td style='padding: 8px;'>Schema update: converts task-report relationship from one-to-many to many-to-one for better flexibility (backup version)</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration-backup/V1__init_extensions.sql'>V1__init_extensions.sql</a></b></td>
                                                    <td style='padding: 8px;'>Legacy extension initialization script with original PostGIS setup (backup version)</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration-backup/V6__add_department_offices_and_user_office.sql'>V6__add_department_offices_and_user_office.sql</a></b></td>
                                                    <td style='padding: 8px;'>Adds department_offices table for office location management and links users to their assigned offices (backup version)</code></td>
                                                </tr>
                                                <tr style='border-bottom: 1px solid #eee;'>
                                                    <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/main/resources/db/migration-backup/V4__create_report_status_history.sql'>V4__create_report_status_history.sql</a></b></td>
                                                    <td style='padding: 8px;'>Creates report_status_history table for audit trail and tracking status changes over time (backup version)</code></td>
                                                </tr>
                                            </table>
                                        </blockquote>
                                    </details>
                                </blockquote>
                            </details>
                        </blockquote>
                    </details>
                </blockquote>
            </details>
            <!-- test Submodule -->
            <details>
                <summary><b>test</b></summary>
                <blockquote>
                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                        <code><b>⦿ src.test</b></code>
                    <!-- java Submodule -->
                    <details>
                        <summary><b>java</b></summary>
                        <blockquote>
                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                <code><b>⦿ src.test.java</b></code>
                            <!-- com Submodule -->
                            <details>
                                <summary><b>com</b></summary>
                                <blockquote>
                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                        <code><b>⦿ src.test.java.com</b></code>
                                    <!-- smartcity Submodule -->
                                    <details>
                                        <summary><b>smartcity</b></summary>
                                        <blockquote>
                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                <code><b>⦿ src.test.java.com.smartcity</b></code>
                                            <!-- urban_management Submodule -->
                                            <details>
                                                <summary><b>urban_management</b></summary>
                                                <blockquote>
                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                        <code><b>⦿ src.test.java.com.smartcity.urban_management</b></code>
                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                    <thead>
                                                        <tr style='background-color: #f8f9fa;'>
                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                        </tr>
                                                    </thead>
                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/test/java/com/smartcity/urban_management/UrbanManagementApplicationTests.java'>UrbanManagementApplicationTests.java</a></b></td>
                                                            <td style='padding: 8px;'>Main Spring Boot test class - verifies application context loads successfully, contains base setup for integration tests with @SpringBootTest</code></td>
                                                        </tr>
                                                    </table>
                                                    <!-- modules Submodule -->
                                                    <details>
                                                        <summary><b>modules</b></summary>
                                                        <blockquote>
                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                <code><b>⦿ src.test.java.com.smartcity.urban_management.modules</b></code>
                                                            <!-- task Submodule -->
                                                            <details>
                                                                <summary><b>task</b></summary>
                                                                <blockquote>
                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                        <code><b>⦿ src.test.java.com.smartcity.urban_management.modules.task</b></code>
                                                                    <!-- service Submodule -->
                                                                    <details>
                                                                        <summary><b>service</b></summary>
                                                                        <blockquote>
                                                                            <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                <code><b>⦿ src.test.java.com.smartcity.urban_management.modules.task.service</b></code>
                                                                            <!-- impl Submodule -->
                                                                            <details>
                                                                                <summary><b>impl</b></summary>
                                                                                <blockquote>
                                                                                    <div class='directory-path' style='padding: 8px 0; color: #666;'>
                                                                                        <code><b>⦿ src.test.java.com.smartcity.urban_management.modules.task.service.impl</b></code>
                                                                                    <table style='width: 100%; border-collapse: collapse;'>
                                                                                    <thead>
                                                                                        <tr style='background-color: #f8f9fa;'>
                                                                                            <th style='width: 30%; text-align: left; padding: 8px;'>File Name</th>
                                                                                            <th style='text-align: left; padding: 8px;'>Summary</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                        <tr style='border-bottom: 1px solid #eee;'>
                                                                                            <td style='padding: 8px;'><b><a href='/app/repo/blob/master/src/test/java/com/smartcity/urban_management/modules/task/service/impl/TaskServiceImplTest.java'>TaskServiceImplTest.java</a></b></td>
                                                                                            <td style='padding: 8px;'>Unit tests for TaskServiceImpl - covers task creation, assignment, status transitions, completion with evidence, and edge cases (invalid status change, unauthorized access)</code></td>
                                                                                        </tr>
                                                                                    </table>
                                                                                </blockquote>
                                                                            </details>
                                                                        </blockquote>
                                                                    </details>
                                                                </blockquote>
                                                            </details>
                                                        </blockquote>
                                                    </details>
                                                </blockquote>
                                            </details>
                                        </blockquote>
                                    </details>
                                </blockquote>
                            </details>
                        </blockquote>
                    </details>
                </blockquote>
            </details>
        </blockquote>
    </details>
</details>
</details>
</details>

---

## Getting Started

### Prerequisites

This project requires the following dependencies:

- **Programming Language:** Java 17 or higher
- **Build Tool:** Maven 3.8+
- **Container Runtime:** Docker & Docker Compose
- **Database:** PostgreSQL with PostGIS extension
- **Message Broker:** Apache Kafka
- **Cache:** Redis

### Installation

Build repo from the source and intsall dependencies:

1. **Clone the repository:**

   ```sh
   ❯  git clone https://github.com/CatV2004/smart-city-backend.git
   ```

2. **Navigate to the project directory:**

   ```sh
   ❯ cd smart-city-backend
   ```

3. **Configure environment variables:**

   ```sh
   ❯ cp .env.exp .env
   ```

4. **Install the dependencies:**

<!-- SHIELDS BADGE CURRENTLY DISABLED -->

     <!-- [![docker][docker-shield]][docker-link] -->
     <!-- REFERENCE LINKS -->
     <!-- [docker-shield]: https://img.shields.io/badge/Docker-2CA5E0.svg?style={badge_style}&logo=docker&logoColor=white -->
     <!-- [docker-link]: https://www.docker.com/ -->

     **Using [docker](https://www.docker.com/):**

     ```sh
     # Build the Docker image
     docker build -t smart-city-backend:latest .

     # Or use Docker Compose for full stack (recommended)
     docker-compose up -d
     ```

 <!-- SHIELDS BADGE CURRENTLY DISABLED -->

     <!-- [![maven][maven-shield]][maven-link] -->
     <!-- REFERENCE LINKS -->
     <!-- [maven-shield]: https://img.shields.io/badge/Maven-C71A36.svg?style={badge_style}&logo=apache-maven&logoColor=white -->
     <!-- [maven-link]: https://maven.apache.org/ -->

     **Using [maven](https://maven.apache.org/):**

     ```sh
     # Clean and install dependencies
     mvn clean install

     # Skip tests if needed
     mvn clean install -DskipTests
     ```

### Usage

Run the project with:

**Using [docker](https://www.docker.com/):**

```sh
# Run with Docker Compose (all services together)
docker-compose up -d

# Run only the app with existing dependencies
docker run -p 8080:8080 --name smart-city-backend smart-city-backend:latest

# For development with hot-reload
docker-compose -f docker-compose.dev.yml up
```

**Using [maven](https://maven.apache.org/):**

```sh
# Run with default profile
mvn spring-boot:run

# Run with specific profile (dev, prod, local)# mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Run with environment variables
export DB_PASSWORD=your_password && mvn spring-boot:run
```

**Access the application:**

- API Base URL: `http://localhost:8080`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI Docs: `http://localhost:8080/v3/api-docs`
- Actuator Health: `http://localhost:8080/actuator/health`

### Testing

Repo uses **JUnit 5**, **Mockito**, and **Spring Boot Test** test frameworks. Run the test suite with:

**Using [maven](https://maven.apache.org/):**

```sh
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=TaskServiceImplTest

# Run tests with coverage report
mvn test jacoco:report

# Run integration tests only
mvn test -Dtest=*IntegrationTest

# Skip tests during build
mvn install -DskipTests
```

---

## Roadmap

- [x] **Database Schema Design**: Completed core tables with PostGIS support
- [x] **JWT Authentication**: Implemented secure login and role-based access
- [x] **Report Management**: CRUD operations for citizen reports
- [x] **AI Auto-Classification**: Automatic category detection for reports
- [x] **PostGIS Integration**: Spatial queries for nearby reports and nearest office
- [x] **Auto-Assignment Logic**: Smart assignment to nearest department office
- [x] **Task Workflow**: Task creation, assignment, start, complete with evidence
- [x] **Real-time Notifications**: WebSocket + Kafka for push notifications
- [x] **MapBox Integration**: Geospatial visualization for reports and offices
- [x] **Cloudinary Upload**: Image storage for report attachments and evidence
- [x] **Kafka Event Streaming**: Async processing for reports and notifications
- [x] **Redis Caching**: Performance optimization for frequent queries
- [ ] **Admin Dashboard Analytics**: Advanced statistics and reporting
- [ ] **Mobile App Optimization**: API fine-tuning for mobile clients
- [ ] **Multi-language Support**: i18n for notifications and email templates
- [ ] **SLA Monitoring**: Automated alerts for overdue tasks
- [ ] **Export Reports**: PDF/Excel export for admin reporting
- [ ] **API Rate Limiting**: Prevent abuse and ensure fair usage

---

## Contributing

- **💬 [Join the Discussions](https://github.com/CatV2004/smart-city-backend/discussions)**: Share your insights, provide feedback, or ask questions.
- **🐛 [Report Issues](https://github.com/CatV2004/smart-city-backend/issues)**: Submit bugs found or log feature requests.
- **💡 [Submit Pull Requests](https://github.com/CatV2004/smart-city-backend/pulls)**: Review open PRs, and submit your own PRs.

 <details closed>
 <summary>Contributing Guidelines</summary>

1.  **Fork the Repository**: Start by forking the project repository to your GitHub account.
2.  **Clone Locally**: Clone the forked repository to your local machine.
    ```sh
    git clone https://github.com/your-username/smart-city-backend.git
    ```
3.  **Create a New Branch**: Always work on a new branch, giving it a descriptive name.
    ```sh
    git checkout -b feature/your-feature-name
    ```
4.  **Make Your Changes**: Develop and test your changes locally.
    ```sh
    mvn test
    ```
5.  **Commit Your Changes**: Commit with a clear message following conventional commits.
    ```sh
    git commit -m 'feat: add new feature description'
    git commit -m 'fix: resolve bug in report assignment'
    ```
6.  **Push to GitHub**: Push the changes to your forked repository.
    ```sh
    git push origin feature/your-feature-name
    ```
7.  **Submit a Pull Request**: Create a PR against the original project repository. Clearly describe the changes and their motivations.
8.  **Review**: Once your PR is reviewed and approved, it will be merged into the main branch. Congratulations on your contribution!
</details>

 <details closed>
 <summary>Contributor Graph</summary>
 <br>
 <p align="left">
    <a href="https://github.com/CatV2004/smart-city-backend/graphs/contributors">
       <img src="https://contrib.rocks/image?repo=CatV2004/smart-city-backend">
    </a>
 </p>
 </details>

---

## License

This project is protected under the **MIT License**. For more details, refer to the [LICENSE](LICENSE) file.

---

## Acknowledgments

- **Spring Boot Team** - Excellent framework for Java backend development
- **PostgreSQL & PostGIS** - Powerful database with geospatial capabilities
- **Apache Kafka** - Event-driven architecture for async processing
- **Redis** - High-performance caching and real-time data
- **Mapbox** - Geocoding, maps, and location services
- **Cloudinary** - Cloud-based image upload and transformation
- **Flyway** - Reliable database migration management
- **JWT** - Secure authentication and authorization
- **All Contributors** - Thanks to everyone who has contributed to this project
- **Inspiration** - Smart city initiatives and citizen engagement platforms worldwide

 <div align="right">

[![][back-to-top]](#top)

 </div>

[back-to-top]: https://img.shields.io/badge/-BACK_TO_TOP-151515?style=flat-square
