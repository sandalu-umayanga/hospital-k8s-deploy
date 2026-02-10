# Kubernetes Orchestration: How it Works

This document explains the technical architecture and request flow of the Hospital Management System within a Kubernetes cluster.

## 1. Architectural Layers

The application is decomposed into three distinct layers, each managed by specific Kubernetes controllers.

### A. Data Layer (MySQL)
**File:** `k8s/mysql-deployment.yaml`
*   **Persistent Storage**: Uses a `PersistentVolumeClaim` (PVC) to ensure data persistence. The database files are stored in a volume that exists independently of the container life cycle.
*   **Secret Management**: Sensitive information like the `MYSQL_ROOT_PASSWORD` is stored in a Kubernetes `Secret` and injected into the container as an environment variable.
*   **Service Discovery**: Exposed via a Service named `mysql`. This creates an internal DNS entry that the backend uses to connect.

### B. Logic Layer (Spring Boot API)
**File:** `k8s/backend-deployment.yaml`
*   **Containerization**: Runs a Java 21 OpenJDK environment serving the Spring Boot JAR.
*   **Service Connectivity**: Connects to the database using the internal hostname `mysql`. It is exposed internally within the cluster, meaning it is not directly accessible from outside the cluster (enhancing security).
*   **Scalability**: The `replicas` field can be increased to handle higher traffic, and Kubernetes will load-balance requests across multiple backend pods.

### C. Presentation Layer (React + Nginx)
**File:** `k8s/frontend-deployment.yaml`
*   **Serving Strategy**: The React application is built into static files and served using an **Nginx** web server.
*   **Reverse Proxy**: Nginx is configured (via `frontend/nginx.conf`) to proxy all requests starting with `/api/` to the `backend` service. This eliminates Cross-Origin Resource Sharing (CORS) issues.
*   **External Access**: Exposed via a `LoadBalancer` service, which provides a single entry point (Port 80) for users to access the application from their browsers.

---

## 2. The Network & Traffic Flow

Kubernetes manages the networking between these components seamlessly:

1.  **Ingress**: The user enters the URL in their browser. Traffic hits the `frontend` Service.
2.  **Routing**: The `frontend` Service directs the traffic to one of the Nginx pods.
3.  **Proxying**:
    *   If the request is for a UI page (e.g., `/dashboard`), Nginx serves the React file.
    *   If the request is for data (e.g., `/api/v1/patients`), Nginx forwards it to `http://backend:8080`.
4.  **Processing**: The `backend` service receives the request and directs it to a Java pod.
5.  **Database Access**: The Java pod queries the database using the hostname `mysql:3306`.
6.  **Response**: Data flows back through the same path to the user browser.

---

## 3. Key Kubernetes Concepts Used

| Concept | Purpose in this Project |
| :--- | :--- |
| **Pod** | The smallest deployable unit; contains our Backend, Frontend, or DB container. |
| **Deployment** | Manages the Pods, ensuring the desired number of replicas are running and handling updates. |
| **Service** | Provides a stable IP address and DNS name (`mysql`, `backend`) for Pods to talk to each other. |
| **PVC** | (Persistent Volume Claim) Ensures our MySQL data isn't deleted when the container restarts. |
| **Secret** | Safely stores the database password. |
| **ConfigMap / Inline Nginx Config** | Configures Nginx to act as a reverse proxy for the API. |

---

## 4. Why Use Kubernetes for this Project?

*   **Self-Healing**: If the backend crashes, Kubernetes automatically starts a new instance.
*   **Zero Downtime**: We can update the frontend or backend without taking the whole system offline.
*   **Environment Parity**: The system runs exactly the same way on a laptop (Minikube) as it would on a professional cloud provider (AWS/GCP/Azure).
