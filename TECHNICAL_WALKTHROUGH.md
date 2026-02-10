# Hospital Management System (HMS) - Technical Walkthrough

This document provides an in-depth look at the architectural decisions, design patterns, and programming techniques used to build the Hospital Management System.

---

## 1. Architectural Overview: The Layered Pattern
The backend follows the **Layered Architecture** (or N-Tier Architecture), which ensures a strict separation of concerns:

1.  **Presentation Layer (Controller)**: Handles HTTP requests, validates basic input, and returns `ResponseEntity`. 
    *   *Example*: `PatientController.java`
2.  **Business Logic Layer (Service)**: Contains the "meat" of the application. It coordinates data flow between repositories and applies business rules (e.g., checking if a doctor exists before assigning them).
    *   *Example*: `PatientService.java`
3.  **Data Access Layer (Repository)**: Abstracts the database interactions using Spring Data JPA and Native SQL queries.
    *   *Example*: `PatientRepo.java`
4.  **Data Models**:
    *   **Entities**: Represent the database schema (JPA).
    *   **DTOs (Data Transfer Objects)**: Encapsulate data sent over the network to prevent exposing sensitive entity fields (like internal IDs or relationships).

---

## 2. Object-Oriented Programming (OOP) Techniques

### A. Encapsulation
Used throughout the project to protect data integrity.
*   **How**: All fields in Entities and DTOs are `private`. 
*   **Implementation**: We use **Lombok** (`@Data`, `@AllArgsConstructor`) to generate getters and setters at compile time, keeping the code clean while maintaining strict access control.

### B. Abstraction
The system uses abstraction to hide implementation details and reduce complexity.
*   **Repository Level**: `PatientRepo` extends `JpaRepository`. We don't write SQL for basic CRUD; we interact with an abstraction that Spring implements at runtime.
*   **Service Level**: Controllers don't know *how* a patient is saved to MySQL; they only call `patientService.savePatient()`.

### C. Composition
Instead of complex inheritance trees, the system uses composition to define relationships.
*   **Implementation**: The `Treatment` entity is composed of a `Patient` and a `Doctor`. A `Patient` is composed of a `Nurse` and an `Attendant`. This mirrors real-world healthcare relationships.

### D. Polymorphism
Used primarily in the Frontend.
*   **Implementation**: The `SelfHome.jsx` component is polymorphic. It accepts a `role` and `user` prop. Based on the "shape" of the user data passed, it renders different management tools (e.g., Doctors see "Manage Treatment," while Nurses see "View Patients").

---

## 3. Design Patterns

### 1. Repository Pattern
*   **Where**: All files in `com.backend.repo`.
*   **Purpose**: It decouples the business logic from the data access technology. If we switched from MySQL to MongoDB, we would only change the Repository implementation, leaving the Service layer untouched.

### 2. Singleton Pattern
*   **Where**: Spring Framework Core.
*   **Purpose**: Every `@Service` and `@Component` (like `JwtUtils`) is a Singleton. Spring creates exactly one instance of these classes, saving memory and ensuring consistent state across the application.

### 3. Filter / Interceptor Pattern
*   **Backend**: `JwtFilter.java` implements the **Interceptor** pattern. Every request is intercepted to check for a valid token before it ever reaches a Controller.
*   **Frontend**: An **Axios Interceptor** in `App.jsx` intercepts every outgoing request to automatically attach the JWT token from `localStorage`.

### 4. Data Transfer Object (DTO) Pattern
*   **Where**: `com.backend.dto`.
*   **Purpose**: Used to separate the internal database structure from the external API. For instance, `PatientDto` includes a list of `doctorsIDs` (integers) instead of the full `Doctor` entity objects, reducing payload size.

### 5. Strategy Pattern (Routing)
*   **Where**: `App.jsx` (React Router).
*   **Purpose**: The application selects which component to "inject" into the view based on the URL path strategy.

---

## 4. Security Implementation

### A. Stateless Authentication (JWT)
Unlike traditional sessions, HMS is **Stateless**. 
1.  **Token Generation**: Upon login, `JwtUtils` signs a token containing the user's email and role.
2.  **Signature**: We use `HMAC SHA-256` with a secret key stored in `.env` to ensure the token cannot be tampered with.
3.  **Validation**: The `JwtFilter` parses the token on every request to identify the user.

### B. Password Hashing
*   **Technique**: `BCryptPasswordEncoder`.
*   **Security**: Passwords are never stored in plain text. Even if the database is compromised, the actual passwords remain secure due to BCrypt's "salt" and work factor.

### C. Secret Management
*   **Tool**: `.env` files and `Dotenv-java`.
*   **Implementation**: Sensitive data like `DB_PASSWORD` and `JWT_SECRET` are stored in environment variables. This prevents hardcoding secrets in the source code, which is critical for pushing to public GitHub repos.

### D. Role-Based Access Control (RBAC)
The `Navigation.jsx` component and backend controllers use the user's role (ADMIN, DOCTOR, etc.) to determine permissions. For example, the `AdminController` checks for the "ADMIN" claim in the JWT before allowing access to staff management.

---

## 5. Summary of Frontend Techniques
*   **Hooks**: `useState` for UI state, `useEffect` for API calls and background management, `useLocation` for cross-page data passing.
*   **Conditional Rendering**: The Navigation bar uses the `isAuthenticated` state to switch between "Login/Register" and "User Profile/Logout".
*   **Asynchronous Coordination**: Using `Promise.all` in `Statistics.jsx` to fetch multiple staff counts simultaneously, improving page load performance.
