# Deploying Hospital Management System on Kubernetes

This guide assumes you have **Docker** and **Kubernetes** (e.g., Docker Desktop with Kubernetes enabled, Minikube, or a cloud cluster) installed and configured.

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
  The `frontend` service is type `LoadBalancer`. It should show an `EXTERNAL-IP` (usually `localhost`).
  Open your browser and go to: **http://localhost**

- **If using Minikube:**
  Run the following command to get the URL:
  ```bash
  minikube service frontend
  ```

## 4. Configuration Details

- **Database Persistence**: The MySQL data is stored in a Persistent Volume Claim (`mysql-pvc`). Data will persist if the Pod restarts.
- **Secrets**: The database password is stored in a Kubernetes Secret (`mysql-secret`) defined in `k8s/mysql-deployment.yaml`. **For production, change the password in this file before applying.**
- **API Proxy**: The frontend Nginx server is configured to forward any request starting with `/api/` to the backend service. This eliminates CORS issues and simplifies the connection.

## Troubleshooting

If the backend fails to start, it might be waiting for the database to become ready. Kubernetes will restart the pod automatically until it connects successfully.

View backend logs:
```bash
kubectl logs -l app=backend
```
