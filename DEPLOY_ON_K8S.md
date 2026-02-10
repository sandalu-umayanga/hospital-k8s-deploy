# Deploying Hospital Management System on Kubernetes

This deployment is specifically designed for local Kubernetes environments such as **Docker Desktop** (with Kubernetes enabled) or **Minikube**.

## Prerequisites

To run this project on Kubernetes, you **must** have one of the following installed:

1.  **Docker Desktop** (Windows/Mac) with Kubernetes enabled in settings.
2.  **Minikube** (Linux/Windows/Mac) along with a hypervisor or Docker driver.

Additionally, you need the `kubectl` CLI tool installed and configured to point to your cluster.

---

## 1. Build Docker Images

You need to build the images locally so your Kubernetes cluster can find them.

### Build Backend Image
Run this from the project root:
```bash
docker build -t hospital-backend:latest ./backend
```

### Build Frontend Image
Run this from the project root:
```bash
docker build -t hospital-frontend:latest ./frontend
```

> **Note for Minikube Users:** You may need to run `minikube image load hospital-backend:latest` and `minikube image load hospital-frontend:latest` if your cluster cannot find the locally built images.

## 2. Deploy to Kubernetes

Apply the configuration files in the `k8s` directory. It is recommended to apply them in this order:

1. **Database** (MySQL)
   ```bash
   kubectl apply -f k8s/mysql-deployment.yaml
   ```
   *Wait a moment for the database to initialize.*

2. **Backend API**
   ```bash
   kubectl apply -f k8s/backend-deployment.yaml
   ```

3. **Frontend UI**
   ```bash
   kubectl apply -f k8s/frontend-deployment.yaml
   ```

## 3. Access the Application

Check the status of your services:
```bash
kubectl get services
```

- **If using Docker Desktop (Windows/Mac):**
  The `frontend` service is type `LoadBalancer`. It should show an `EXTERNAL-IP` as `localhost`.
  Open your browser and go to: **http://localhost**

- **If using Minikube:**
  Run the following command to open the application:
  ```bash
  minikube service frontend
  ```

## 4. Configuration Details

- **Database Persistence**: The MySQL data is stored in a Persistent Volume Claim (`mysql-pvc`). Data will persist if the Pod restarts.   
- **Secrets**: The database password is stored in a Kubernetes Secret (`mysql-secret`) defined in `k8s/mysql-deployment.yaml`.
- **API Proxy**: The frontend Nginx server is configured to forward any request starting with `/api/` to the backend service. This eliminates CORS issues and simplifies the connection.

## Troubleshooting

If the backend fails to start, it might be waiting for the database to become ready. Kubernetes will restart the pod automatically until it connects successfully.

View backend logs:
```bash
kubectl logs -l app=backend
```
