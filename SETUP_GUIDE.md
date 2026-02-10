# Hospital Management System - Setup Guide

This document provides detailed, step-by-step instructions for setting up the development environment for the Hospital Management System.

## Prerequisites

Before you begin, ensure you have the following software installed on your machine:

1.  **Java Development Kit (JDK) 21**:
    *   Verify installation: `java -version`
2.  **Node.js (v16 or higher) & npm**:
    *   Verify installation: `node -v` and `npm -v`
3.  **MySQL or MariaDB Server**:
    *   Verify installation: `mysql --version`

---

## Part 1: Database Setup

The application requires a MySQL/MariaDB database to store data.

1.  **Log in to your Database Server**:
    Open your terminal and run:
    ```bash
    sudo mysql -u root -p
    ```
    *(Enter your root password when prompted)*

2.  **Create Database and User**:
    Run the following SQL commands to create the database (`HospitalDatabase`) and a dedicated user (`hospital_admin`) with the correct permissions.

    ```sql
    -- Create the database
    CREATE DATABASE IF NOT EXISTS HospitalDatabase;

    -- Create the user (if not already created)
    CREATE USER IF NOT EXISTS 'hospital_admin'@'localhost' IDENTIFIED BY 'H#LL@123hospital';

    -- Grant permissions
    GRANT ALL PRIVILEGES ON HospitalDatabase.* TO 'hospital_admin'@'localhost';

    -- Apply changes
    FLUSH PRIVILEGES;

    -- Exit
    EXIT;
    ```

---

## Part 2: Backend Setup (Spring Boot)

The backend is built with Java and Spring Boot. It runs on port `8080`.

1.  **Navigate to the Backend Directory**:
    ```bash
    cd backend
    ```

2.  **Configuration (Optional)**:
    The database connection settings are located in `src/main/resources/application.properties`. They are pre-configured to match the database setup above:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/HospitalDatabase?createDatabaseIfNotExist=true
    spring.datasource.username=hospital_admin
    spring.datasource.password=H#LL@123hospital
    ```
    *If you changed the database password or user, update this file accordingly.*

3.  **Run the Application**:
    Use the Maven Wrapper (`mvnw`) included in the project to run the app. This ensures you use the correct Maven version without installing it globally.

    *   **Linux/macOS**:
        ```bash
        ./mvnw spring-boot:run
        ```
        *(If you get a "permission denied" error, run `chmod +x mvnw` first)*

    *   **Windows (Command Prompt)**:
        ```cmd
        mvnw spring-boot:run
        ```

4.  **Verify**:
    Wait for the logs to show `Started BackendApplication in ... seconds`.
    The backend is now accessible at `http://localhost:8080`.

---

## Part 3: Frontend Setup (React + Vite)

The frontend is built with React and uses Vite for a fast development experience. It runs on port `3000`.

1.  **Navigate to the Frontend Directory**:
    Open a new terminal window (keep the backend running in the first one) and go to the frontend folder:
    ```bash
    cd frontend
    ```

2.  **Install Dependencies**:
    Download the required Node.js packages:
    ```bash
    npm install
    ```

3.  **Start the Development Server**:
    ```bash
    npm start
    ```
    *(Note: We have configured the `start` script to run `vite`)*

4.  **Access the Application**:
    Your browser should automatically open `http://localhost:3000`. If not, navigate there manually.

---

## Part 4: Testing the Application

### 1. Manual Testing via Browser
*   Go to `http://localhost:3000`.
*   Navigate to the **Patient Login** or **Register** pages to interact with the system.

### 2. API Testing (Postman/cURL)
You can test the backend APIs directly.
*   **Example: Register a Patient**
    *   **URL**: `http://localhost:8080/api/v1/patient/savePatient`
    *   **Method**: `POST`
    *   **Body (JSON)**:
        ```json
        {
          "email": "test@example.com",
          "firstName": "John",
          "lastName": "Doe",
          "dob": "1990-01-01",
          "address": "123 Main St",
          "phone": "555-0000",
          "gender": "Male",
          "password": "password123"
        }
        ```

### 3. Running Automated Backend Tests
To run the unit tests included in the backend:
```bash
cd backend
./mvnw clean test
```
*These tests use an in-memory database and do not affect your main data.*

---

## Troubleshooting

*   **Port Conflicts**:
    *   If port `8080` is busy, the backend won't start. Kill the process using that port or change `server.port` in `application.properties`.
    *   If port `3000` is busy, Vite will usually try the next available port (e.g., `3001`).

*   **Database Connection Errors**:
    *   Ensure the MySQL service is running (`sudo systemctl status mysql` or `sudo service mysql status`).
    *   Double-check the username and password in `application.properties` matches what you set in MySQL.
