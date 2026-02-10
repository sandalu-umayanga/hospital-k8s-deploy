import React, { useEffect } from "react";
import { Container, Row, Col, Card, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import imageUrl from "./images/db1.jpg";

export default function AdminDashboard(props) {
  const navigate = useNavigate();

  useEffect(() => {
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    return () => {
      document.body.style.backgroundImage = "";
      document.body.style.backgroundSize = "";
      document.body.style.backgroundRepeat = "";
      document.body.style.backgroundPosition = "";
    };
  }, []);

  return (
    <Container className="mt-5">
      <h2 className="mb-4 text-light">Admin Dashboard</h2>
      <Row className="g-4">
        <Col md={4}>
          <Card className="text-center h-100 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Body>
              <Card.Title>Doctor Management</Card.Title>
              <Card.Text>Register new doctors or update existing profiles.</Card.Text>
              <Button variant="primary" onClick={() => navigate("/doctorRegister")}>Register Doctor</Button>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4}>
          <Card className="text-center h-100 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Body>
              <Card.Title>Nurse Management</Card.Title>
              <Card.Text>Register new nurses or update existing profiles.</Card.Text>
              <Button variant="primary" onClick={() => navigate("/nurseRegister")}>Register Nurse</Button>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4}>
          <Card className="text-center h-100 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Body>
              <Card.Title>Attendant Management</Card.Title>
              <Card.Text>Register new attendants or update existing profiles.</Card.Text>
              <Button variant="primary" onClick={() => navigate("/attendantRegister")}>Register Attendant</Button>
            </Card.Body>
          </Card>
        </Col>
        <Col md={6}>
          <Card className="text-center h-100 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Body>
              <Card.Title>System Statistics</Card.Title>
              <Card.Text>View hospital operational statistics.</Card.Text>
              <Button variant="info" onClick={() => navigate("/statistics")}>View Stats</Button>
            </Card.Body>
          </Card>
        </Col>
        <Col md={6}>
          <Card className="text-center h-100 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Body>
              <Card.Title>Staff Management</Card.Title>
              <Card.Text>View, edit, or delete existing staff members.</Card.Text>
              <Button variant="warning" onClick={() => navigate("/staffManagement")}>Manage Staff</Button>
            </Card.Body>
          </Card>
        </Col>
        <Col md={6}>
          <Card className="text-center h-100 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Body>
              <Card.Title>Logout</Card.Title>
              <Card.Text>Securely logout from the admin panel.</Card.Text>
              <Button variant="danger" onClick={() => {
                  if (props.onLogout) props.onLogout();
                  navigate("/");
              }}>Logout</Button>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

