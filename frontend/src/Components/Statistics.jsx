import React, { useEffect, useState } from "react";
import { Container, Row, Col, Card, ProgressBar, Spinner, Alert } from "react-bootstrap";
import axios from "axios";
import imageUrl from "./images/db1.jpg";

export default function Statistics() {
  const [counts, setCounts] = useState({
    patients: 0,
    doctors: 0,
    nurses: 0
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    const fetchData = async () => {
      try {
        const [patientsRes, doctorsRes, nursesRes] = await Promise.all([
          axios.get("/api/v1/patient/getAllPatients"),
          axios.get("/api/v1/doctor/getAllDoctors"),
          axios.get("/api/v1/nurse/getAllNurses")
        ]);

        setCounts({
          patients: patientsRes.data.data ? patientsRes.data.data.length : 0,
          doctors: doctorsRes.data.data ? doctorsRes.data.data.length : 0,
          nurses: nursesRes.data.data ? nursesRes.data.data.length : 0
        });
        setLoading(false);
      } catch (err) {
        console.error(err);
        setError("Failed to load statistics from backend.");
        setLoading(false);
      }
    };

    fetchData();

    return () => {
      document.body.style.backgroundImage = "";
      document.body.style.backgroundSize = "";
      document.body.style.backgroundRepeat = "";
      document.body.style.backgroundPosition = "";
    };
  }, []);

  if (loading) {
    return (
      <Container className="mt-5 text-center">
        <Spinner animation="border" variant="primary" />
      </Container>
    );
  }

  return (
    <Container className="mt-5">
      <h2 className="mb-4 text-light">Statistics & Reports</h2>
      {error && <Alert variant="warning">{error}</Alert>}
      
      <Row>
        <Col md={6} className="mb-4">
          <Card className="bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Header className="border-bottom border-secondary">System Health</Card.Header>
            <Card.Body>
              <h3>98%</h3>
              <ProgressBar now={98} variant="success" />
              <Card.Text className="mt-2 text-info">
                Server uptime is optimal.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={6} className="mb-4">
          <Card className="bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Header className="border-bottom border-secondary">Bed Occupancy (Mock)</Card.Header>
            <Card.Body>
              <h3>60%</h3>
              <ProgressBar now={60} variant="warning" />
              <Card.Text className="mt-2 text-info">
                30 beds available.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
      <Row>
        <Col md={4} className="mb-4">
          <Card className="text-center h-100 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Body className="d-flex flex-column justify-content-center">
              <h1 className="text-primary display-3">{counts.patients}</h1>
              <Card.Title>Total Patients</Card.Title>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4} className="mb-4">
          <Card className="text-center h-100 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Body className="d-flex flex-column justify-content-center">
              <h1 className="text-success display-3">{counts.doctors}</h1>
              <Card.Title>Doctors Registered</Card.Title>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4} className="mb-4">
          <Card className="text-center h-100 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Body className="d-flex flex-column justify-content-center">
              <h1 className="text-info display-3">{counts.nurses}</h1>
              <Card.Title>Nurses Registered</Card.Title>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}
