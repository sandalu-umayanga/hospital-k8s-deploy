import React, { useEffect, useState } from "react";
import { Container, Card, ListGroup, Badge, Spinner, Alert } from "react-bootstrap";
import { useLocation } from "react-router-dom";
import axios from "axios";
import imageUrl from "./images/db1.jpg";

export default function Appointments() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const location = useLocation();
  const user = location.state?.user || JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    if (user && user.id) {
      // Determine if patient or doctor based on some property or just try both or use role
      // For now let's assume we can tell. I'll add a 'role' to user in LoginForm.
      const role = localStorage.getItem("role");
      let url = "";
      if (role === "PATIENT") url = `/api/v1/appointment/patient/${user.id}`;
      else if (role === "DOCTOR") url = `/api/v1/appointment/doctor/${user.id}`;
      else {
          setLoading(false);
          return;
      }

      axios.get(url)
        .then(res => {
          if (res.data.code === "05") setAppointments(res.data.data);
          setLoading(false);
        })
        .catch(err => {
          setError("Failed to fetch appointments.");
          setLoading(false);
        });
    } else {
      setLoading(false);
    }

    return () => {
      document.body.style.backgroundImage = "";
    };
  }, [user]);

  if (!user) return <Container className="mt-5"><Alert variant="danger">Please login to view appointments.</Alert></Container>;

  return (
    <Container className="mt-5">
      <h2 className="mb-4 text-light">Appointments Overview</h2>
      {loading ? <Spinner animation="border" variant="light" /> : (
        <Card className="bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
          <Card.Header className="border-bottom border-secondary">Your Appointments</Card.Header>
          <ListGroup variant="flush">
            {appointments.length > 0 ? appointments.map((app) => (
              <ListGroup.Item key={app.id} className="d-flex justify-content-between align-items-start bg-dark text-light border-secondary">
                <div className="ms-2 me-auto">
                  <div className="fw-bold">{localStorage.getItem("role") === "PATIENT" ? `With Dr. ${app.doctorName}` : `With Patient ${app.patientName}`}</div>
                  {app.reason}
                </div>
                <div className="text-end">
                    <Badge bg="primary" pill className="mb-1 d-block">
                      {new Date(app.dateTime).toLocaleString()}
                    </Badge>
                    <Badge bg={app.status === "CONFIRMED" ? "success" : "warning"} pill>
                      {app.status}
                    </Badge>
                </div>
              </ListGroup.Item>
            )) : <ListGroup.Item className="bg-dark text-light border-secondary">No appointments found.</ListGroup.Item>}
          </ListGroup>
        </Card>
      )}
    </Container>
  );
}

