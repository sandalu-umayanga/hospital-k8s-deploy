# Hospital Management System (HMS) - Full Documentation

## 1. Introduction
The Hospital Management System (HMS) is a full-stack web application designed to automate clinical and administrative workflows. It provides a secure, role-based platform for patients, medical staff, and administrators to coordinate healthcare services efficiently.

## 2. System Roles & Access Control
The system implements Role-Based Access Control (RBAC) with the following roles:

| Role | Responsibilities | Key Features |
| :--- | :--- | :--- |
| **Admin** | System configuration and staff oversight. | Register staff, manage staff list, view system statistics. |
| **Doctor** | Clinical care and patient oversight. | View assigned patients, manage medical treatments/observations, view appointments. |
| **Nurse** | Daily clinical support and monitoring. | View assigned patients, update basic patient info, view notifications. |
| **Attendant**| Patient assistance and logistics. | View assigned patients, manage basic schedules. |
| **Patient** | Recipient of care. | View personal medical history, see assigned doctors, track appointments. |

---

## 3. Technology Stack

### Backend
- **Java 21**: Core programming language.
- **Spring Boot 3.3.0**: Framework for RESTful APIs and dependency injection.
- **Spring Security + JWT**: Stateless authentication and authorization.
- **Spring Data JPA**: ORM for database interactions.
- **MySQL / MariaDB**: Relational database for persistent storage.
- **ModelMapper**: For DTO-Entity mapping.
- **Lombok**: For boilerplate code reduction.

### Frontend
- **React 18**: UI Library.
- **Vite**: Modern build tool and development server.
- **React Bootstrap**: UI component framework.
- **Axios**: HTTP client with interceptors for JWT injection.
- **React Router DOM**: Navigation and routing.
- **Formik & Yup**: Form handling and validation.

---

## 4. Technical Architecture

### Database Schema (Key Tables)
- **patient**: Stores patient demographics, assigned nurse/attendant, and encrypted password.
- **doctor**: Stores doctor details and profile pictures (LongBlob).
- **nurse**: Stores nurse details and credentials.
- **attendant**: Stores attendant details.
- **treatment**: Many-to-Many join table with extra fields (Observations, Treatments) linking doctors and patients.
- **appointment**: Stores scheduled meetings between patients and doctors.
- **notification**: Stores system alerts for specific users.

### Security Model
The system uses **stateless JWT authentication**:
1.  User logs in via `/api/v1/[role]/login`.
2.  Backend validates credentials and returns a Signed JWT.
3.  Frontend stores the token in `localStorage`.
4.  The Axios interceptor attaches `Authorization: Bearer <token>` to all subsequent requests.
5.  `JwtFilter` on the backend validates the token and sets the Security Context.

---

## 5. Module Overviews

### Patient Management
- **Registration**: Patients can be registered by staff members. Assigning a primary Doctor, Nurse, and Attendant is handled at this stage.
- **Records**: Doctors can log detailed observations and prescriptions for patients via the Treatment Management module.

### Appointments & Notifications
- **Scheduling**: Real-time tracking of upcoming appointments (Backend supported, Frontend currently shows a dashboard view).
- **Alerts**: Automated and manual notifications for system maintenance or patient updates.

### Admin Tools
- **Statistics**: A dashboard showing total patient/staff counts and system health metrics.
- **Staff Control**: A centralized hub to view the entire workforce and prune inactive accounts.

---

## 6. Setup and Installation

### Prerequisites
- JDK 21
- Node.js & npm
- MariaDB/MySQL Server

### Database Configuration
Run the following in your SQL console:
```sql
CREATE DATABASE HospitalDatabase;
CREATE USER 'hospital_admin'@'localhost' IDENTIFIED BY 'H#LL@123hospital';
GRANT ALL PRIVILEGES ON HospitalDatabase.* TO 'hospital_admin'@'localhost';
FLUSH PRIVILEGES;
```

### Backend Setup
1. Navigate to `backend/`.
2. Create a `.env` file based on `.env.example` and fill in your credentials.
3. Run `./mvnw spring-boot:run`.
4. The server runs on `http://localhost:8080`.

### Frontend Setup
1. Navigate to `frontend/`.
2. Run `npm install`.
3. Run `npm start`.
4. The UI is available at `http://localhost:3000`.

---

## 7. API Documentation (Grouped)

### Patient Endpoints (`/api/v1/patient`)
- `POST /savePatient`: Create patient.
- `GET /getAllPatients`: List all.
- `POST /loginPatient`: Authenticate.
- `GET /findPatientByName/{name}`: Search.

### Staff Endpoints (`/api/v1/doctor`, `/nurse`, `/attendant`)
- `GET /getAll...`: List all staff.
- `POST /login...`: Authenticate staff.
- `GET /getPatientList/{id}`: View patients assigned to that staff member.

### Treatment Endpoints (`/api/v1/treatment`)
- `POST /save`: Create record.
- `PUT /update`: Modify record.
- `GET /patient/{id}`: Get medical history for a patient.

### Appointment & Notification
- `POST /api/v1/appointment/save`: Schedule appointment.
- `GET /api/v1/notification/{userId}/{role}`: Fetch alerts.

---

## 8. Troubleshooting
- **CORS Issues**: The `WebConfig` is configured for `http://localhost:3000`. If running on a different port, update the `allowedOrigins`.
- **Database Case Sensitivity**: Native queries use lowercase table names (`patient`, `doctor`) to remain compatible with Linux environments.
- **Port Conflict**: Use `fuser -k 8080/tcp` to clear the backend port if it's stuck.
