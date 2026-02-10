import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation } from "react-router-dom";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Card from "react-bootstrap/Card";
import Badge from "react-bootstrap/Badge";
import Spinner from "react-bootstrap/Spinner";
import Alert from "react-bootstrap/Alert";
import Button from "react-bootstrap/Button";
import ListGroup from "react-bootstrap/ListGroup";
import imageUrl from "./images/db1.jpg";

export default function PatientHome() {
  const location = useLocation();
  const user = location.state?.user;
  const [doctors, setDoctors] = useState([]);
  const [treatments, setTreatments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    if (user && user.id) {
      const fetchDoctors = axios.get(`/api/v1/patient/getDoctorList/${user.id}`);
      const fetchTreatments = axios.get(`/api/v1/treatment/patient/${user.id}`);

      Promise.all([fetchDoctors, fetchTreatments])
        .then(([docRes, treatRes]) => {
          if (docRes.data.code === "08") {
            setDoctors(docRes.data.data);
          }
          if (treatRes.data.code === "09") {
            setTreatments(treatRes.data.data);
          }
          setLoading(false);
        })
        .catch((err) => {
          console.error(err);
          setError("Error fetching data.");
          setLoading(false);
        });
    } else {
      setLoading(false);
      setError("User not found. Please log in again.");
    }

    return () => {
      document.body.style.backgroundImage = "";
      document.body.style.backgroundSize = "";
      document.body.style.backgroundRepeat = "";
      document.body.style.backgroundPosition = "";
    };
  }, [user]);

  if (!user) {
    return (
      <Container className="mt-5">
        <Alert variant="danger">
          User session not found. Please <a href="/patientLogin">login</a>.
        </Alert>
      </Container>
    );
  }

  return (
    <Container className="mt-5">
      <Row className="mb-4">
        <Col>
          <h2 className="text-light">Welcome, {user.firstName} {user.lastName}</h2>
          <Badge bg="info" className="me-2">Patient Dashboard</Badge>
          <Button variant="outline-danger" size="sm" onClick={() => {
            if(window.confirm("Are you sure you want to logout?")) {
              window.location.href = "/";
            }
          }}>Logout</Button>
        </Col>
      </Row>

      <Row>
        <Col md={4}>
          <Card className="mb-4 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Header className="border-bottom border-secondary">My Profile</Card.Header>
            <Card.Body>
              <Card.Text><strong>Email:</strong> {user.email}</Card.Text>
              <Card.Text><strong>Phone:</strong> {user.phone}</Card.Text>
              <Card.Text><strong>Address:</strong> {user.address}</Card.Text>
              <Card.Text><strong>DOB:</strong> {user.dob}</Card.Text>
            </Card.Body>
          </Card>

          <Card className="bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Header className="border-bottom border-secondary">My Doctors</Card.Header>
            <Card.Body>
              {loading ? (
                <Spinner animation="border" size="sm" />
              ) : doctors.length > 0 ? (
                <ListGroup variant="flush">
                  {doctors.map((doctor) => (
                    <ListGroup.Item key={doctor.id} className="bg-dark text-light border-secondary px-0">
                      Dr. {doctor.firstName} {doctor.lastName}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              ) : (
                <p className="small">No doctors assigned yet.</p>
              )}
            </Card.Body>
          </Card>
        </Col>

        <Col md={8}>
          <Card className="bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Header className="border-bottom border-secondary">My Medical Records (Treatments)</Card.Header>
            <Card.Body>
              {loading ? (
                <div className="text-center">
                  <Spinner animation="border" />
                </div>
              ) : error ? (
                <Alert variant="warning">{error}</Alert>
              ) : treatments.length > 0 ? (
                <Row xs={1} className="g-4">
                  {treatments.map((treatment, idx) => (
                    <Col key={idx}>
                      <Card className="bg-secondary text-white border-0">
                        <Card.Body>
                          <Card.Title className="text-info">Medical Record #{idx + 1}</Card.Title>
                          <hr className="bg-light" />
                          <Card.Text>
                            <strong>Observations:</strong><br/>
                            {treatment.observations}
                          </Card.Text>
                          <Card.Text>
                            <strong>Prescribed Treatments:</strong><br/>
                            {treatment.treatments}
                          </Card.Text>
                        </Card.Body>
                      </Card>
                    </Col>
                  ))}
                </Row>
              ) : (
                <p>No medical records found.</p>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

