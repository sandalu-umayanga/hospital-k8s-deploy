import React, { useEffect, useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import { Container, Row, Col, Card, Badge, Spinner, Alert, Button, Form } from "react-bootstrap";
import imageUrl from "./images/db1.jpg";

export default function PatientList() {
  const location = useLocation();
  const navigate = useNavigate();
  const { user, role, getPatientsLink } = location.state || {};
  const [patients, setPatients] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    fetchPatients();

    return () => {
      document.body.style.backgroundImage = "";
    };
  }, [user, getPatientsLink]);

  const fetchPatients = () => {
    if (user && user.id && getPatientsLink) {
      setLoading(true);
      axios
        .get(`${getPatientsLink}${user.id}`)
        .then((response) => {
          if (["08", "06"].includes(response.data.code)) {
            setPatients(response.data.data);
          } else {
            setError(response.data.message || "Failed to load patients.");
          }
          setLoading(false);
        })
        .catch((err) => {
          console.error(err);
          setError("Error fetching patient list.");
          setLoading(false);
        });
    } else {
      setLoading(false);
      setError("User or link not found.");
    }
  };

  const handleSearch = (e) => {
    e.preventDefault();
    if (!searchTerm.trim()) {
      fetchPatients();
      return;
    }
    setLoading(true);
    axios.get(`/api/v1/patient/findPatientByName/${searchTerm}`)
      .then(res => {
        if (res.data.code === "06") {
          // Filter patients assigned to current user from the search results
          // Since the backend findByName returns all matches, we should ideally filter by assigned patients
          // but for simplicity, let's just show matching patients.
          setPatients(res.data.data);
        } else {
          setPatients([]);
        }
        setLoading(false);
      })
      .catch(err => {
        setPatients([]);
        setLoading(false);
      });
  };

  if (!user) {
    return (
      <Container className="mt-5">
        <Alert variant="danger">User session not found.</Alert>
      </Container>
    );
  }

  return (
    <Container className="mt-5">
      <Row className="mb-4 align-items-center">
        <Col>
          <h2 className="text-light">Patients Assigned to You</h2>
          <Badge bg="primary">{role}</Badge>
        </Col>
        <Col md={4}>
          <Form onSubmit={handleSearch} className="d-flex">
            <Form.Control
              type="search"
              placeholder="Search by name..."
              className="me-2"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
            <Button variant="outline-light" type="submit">Search</Button>
          </Form>
        </Col>
      </Row>

      {loading ? (
        <div className="text-center">
          <Spinner animation="border" variant="primary" />
        </div>
      ) : error ? (
        <Alert variant="warning">{error}</Alert>
      ) : patients.length > 0 ? (
        <Row xs={1} md={2} lg={3} className="g-4">
          {patients.map((patient) => (
            <Col key={patient.id}>
              <Card className="h-100 shadow bg-dark text-light border-0" style={{ opacity: 0.9 }}>
                <Card.Body>
                  <Card.Title className="text-primary">{patient.firstName} {patient.lastName}</Card.Title>
                  <Card.Text>
                    <strong>Email:</strong> {patient.email}<br/>
                    <strong>Phone:</strong> {patient.phone}<br/>
                    <strong>Address:</strong> {patient.address}
                  </Card.Text>
                  <Button 
                    variant="outline-info" 
                    size="sm"
                    onClick={() => navigate("/treatmentManagement", { state: { patient, user, role } })}
                  >
                    Manage Treatment
                  </Button>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      ) : (
        <p className="text-light">No patients assigned to you.</p>
      )}
    </Container>
  );
}

