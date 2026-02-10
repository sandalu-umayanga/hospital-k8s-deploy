# Hospital Management System (Kubernetes Deployment)

A professional full-stack platform for coordinating healthcare services, now containerized and ready for Kubernetes deployment.

## Quick Links
- [🚀 Kubernetes Deployment Guide](DEPLOY_ON_K8S.md) (Recommended)
- [🛠️ Local Development Guide](SETUP_GUIDE.md)
- [📖 Full Technical Documentation](FULL_DOCUMENTATION.md)

## Architecture
This repository contains a containerized version of the Hospital Management System:
- **Frontend**: React (Vite) served via Nginx
- **Backend**: Spring Boot (Java 21)
- **Database**: MySQL 8.0 with Persistent Storage

## Features
- **Multi-Role Dashboards**: Specific views for Admins, Doctors, Nurses, Attendants, and Patients.
- **Secure Access**: JWT-based authentication and role-based navigation.
- **Patient Records**: Real-time management of medical observations and treatments.
- **Staff Management**: Centralized admin hub for hospital workforce oversight.
- **System Analytics**: Dynamic statistics based on live data.

## Deployment
This project is designed to be deployed on a Kubernetes cluster (Minikube, Docker Desktop, or Cloud).

**[➡️ Click here for the Deployment Instructions](DEPLOY_ON_K8S.md)**

### Quick Start (Docker Desktop / Minikube)
```bash
# 1. Build Images
docker build -t hospital-backend:latest ./backend
docker build -t hospital-frontend:latest ./frontend

# 2. Apply Manifests
kubectl apply -f k8s/mysql-deployment.yaml
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/frontend-deployment.yaml

# 3. Access
# Visit http://localhost (Docker Desktop)
# Or run `minikube service frontend`
```

---
*Created for CathLab Hospital Management.*
