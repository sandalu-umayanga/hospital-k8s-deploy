# Load Testing Guide for Hospital Management System

This guide explains how to perform load testing on the Hospital Management System running in Kubernetes using **k6**. The testing infrastructure is deployed directly into the cluster as a Kubernetes Job.

## 1. Prerequisites

*   A running Kubernetes cluster (Minikube or Docker Desktop).
*   The application (Backend, Frontend, MySQL) must be deployed and running.
*   `kubectl` installed and configured.

## 2. Configuration (`k8s/load-test.yaml`)

The load test is defined in `k8s/load-test.yaml`. It consists of two parts:
1.  **ConfigMap**: Contains the actual JavaScript test script (`test.js`).
2.  **Job**: Runs the `grafana/k6` container to execute the script.

### Adjusting Parameters
To change the load, edit the `options` object in the ConfigMap section of `k8s/load-test.yaml`:

```javascript
export const options = {
  vus: 10,       // Number of Virtual Users (Concurrent connections)
  duration: "30s", // Duration of the test
};
```

**Note:** For local environments (Minikube), keep `vus` between **10-50**. Setting it higher (e.g., 500) may cause the backend pods to crash due to resource exhaustion.

### Test Logic
The script sends HTTP GET requests to the internal backend service:
```javascript
export default function () {
  // Accessing the backend via its internal ClusterIP service name
  const res = http.get("http://backend:8080/api/v1/doctor/getAllDoctors");
  
  // Validation Check
  check(res, {
    "status is 200 or 400": (r) => r.status === 200 || r.status === 400,
  });
  sleep(1); // Wait 1 second between requests
}
```
*   **Success Criteria**: We accept `200 OK` (Doctors found) or `400 Bad Request` (Empty Database/No Doctors found) as successful responses to prove the server handled the request.

## 3. Running the Test

Since the test runs as a Job, you must delete any previous test runs before starting a new one.

```bash
# 1. Clean up previous runs
kubectl delete job load-test-job
kubectl delete configmap k6-script

# 2. Apply the configuration
kubectl apply -f k8s/load-test.yaml
```

## 4. Monitoring Progress

Watch the pod creation and execution status:

```bash
kubectl get pods -l job-name=load-test-job --watch
```

*   **Pending/ContainerCreating**: The k6 image is being pulled.
*   **Running**: The test is executing.
*   **Completed**: The test finished successfully.
*   **Error**: The test script failed (check logs).

## 5. Analyzing Results

Once the pod status is `Completed`, retrieve the logs to see the report:

```bash
kubectl logs job/load-test-job
```

### Key Metrics to Read

1.  **Checks**:
    ```
    ? status is 200 or 400 (Empty DB)
    checks_succeeded...: 100.00%
    ```
    This indicates **Availability**. If this is less than 100%, requests are failing (crashing or timing out).

2.  **HTTP Request Duration** (`http_req_duration`):
    ```
    http_req_duration....: avg=23.02ms  p(95)=57.78ms
    ```
    *   **avg**: The average time the server took to respond. Lower is better.
    *   **p(95)**: 95% of requests were faster than this time. This is a critical metric for "worst-case" user experience.

3.  **Throughput**:
    ```
    http_reqs............: 300     9.721123/s
    ```
    This shows how many requests per second (RPS) your system handled.

### Troubleshooting Failures

*   **`connect: connection refused`**: The backend pod crashed or is restarting. Check backend status with `kubectl get pods`.
*   **`status is 200` failures**: If you see "checks failed" but `http_req_duration` is valid, the server is responding but with an error code (e.g., 401 Unauthorized, 403 Forbidden, 500 Error). Check the script's validation logic.

