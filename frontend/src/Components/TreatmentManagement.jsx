import React, { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import { Container, Row, Col, Card, Form, Button, Alert, Spinner } from "react-bootstrap";
import imageUrl from "./images/db1.jpg";

export default function TreatmentManagement() {
  const location = useLocation();
  const navigate = useNavigate();
  const { patient, user, role } = location.state || {};
  
  const [formData, setFormData] = useState({
    observations: "",
    treatments: ""
  });
  const [loading, setLoading] = useState(false);
  const [fetchLoading, setFetchLoading] = useState(true);
  const [message, setMessage] = useState(null);
  const [isNew, setIsNew] = useState(true);

  useEffect(() => {
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover";
    document.body.style.backgroundRepeat = "no-repeat";
    document.body.style.backgroundPosition = "center center";
    document.body.style.backgroundAttachment = "fixed";

    if (patient && user && role === "Doctor") {
      // Check if treatment exists
      axios.get("/api/v1/treatment/all")
        .then(response => {
          const existing = response.data.data.find(t => t.patientId === patient.id && t.doctorId === user.id);
          if (existing) {
            setFormData({
              observations: existing.observations,
              treatments: existing.treatments
            });
            setIsNew(false);
          }
          setFetchLoading(false);
        })
        .catch(err => {
          console.error(err);
          setFetchLoading(false);
        });
    } else {
      setFetchLoading(false);
    }

    return () => {
      document.body.style.backgroundImage = "";
      document.body.style.backgroundSize = "";
      document.body.style.backgroundRepeat = "";
      document.body.style.backgroundPosition = "";
    };
  }, [patient, user, role]);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    const payload = {
      patientId: patient.id,
      doctorId: user.id,
      ...formData
    };

    const apiCall = isNew 
      ? axios.post("/api/v1/treatment/save", payload)
      : axios.put("/api/v1/treatment/update", payload);

    apiCall
      .then(response => {
        setMessage({ type: "success", text: response.data.message });
        setIsNew(false);
        setLoading(false);
      })
      .catch(err => {
        setMessage({ type: "danger", text: "Error saving treatment." });
        setLoading(false);
      });
  };

  if (!patient || !user) return <Alert variant="danger">Invalid session.</Alert>;

  return (
    <Container className="mt-5">
      <Button variant="outline-light" onClick={() => navigate(-1)} className="mb-3">← Back to Patient List</Button>
      <Row>
        <Col md={6}>
          <Card className="mb-4 bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Header className="border-bottom border-secondary">Patient Details</Card.Header>
            <Card.Body>
              <h4 className="text-primary">{patient.firstName} {patient.lastName}</h4>
              <p><strong>Address:</strong> {patient.address}</p>
              <p><strong>Phone:</strong> {patient.phone}</p>
            </Card.Body>
          </Card>
        </Col>

        <Col md={6}>
          <Card className="bg-dark text-light border-0 shadow" style={{ opacity: 0.9 }}>
            <Card.Header className="border-bottom border-secondary">{isNew ? "Add Treatment" : "Update Treatment"}</Card.Header>
            <Card.Body>
              {fetchLoading ? <Spinner animation="border" variant="primary" /> : (
                <Form onSubmit={handleSubmit}>
                  {message && <Alert variant={message.type}>{message.text}</Alert>}
                  <Form.Group className="mb-3">
                    <Form.Label>Observations</Form.Label>
                    <Form.Control 
                      as="textarea" 
                      rows={3} 
                      name="observations" 
                      value={formData.observations}
                      onChange={handleChange}
                      disabled={role !== "Doctor"}
                      className="bg-secondary text-white border-0"
                    />
                  </Form.Group>
                  <Form.Group className="mb-3">
                    <Form.Label>Treatments</Form.Label>
                    <Form.Control 
                      as="textarea" 
                      rows={3} 
                      name="treatments" 
                      value={formData.treatments}
                      onChange={handleChange}
                      disabled={role !== "Doctor"}
                      className="bg-secondary text-white border-0"
                    />
                  </Form.Group>
                  {role === "Doctor" && (
                    <Button variant="primary" type="submit" disabled={loading}>
                      {loading ? "Saving..." : "Save Treatment"}
                    </Button>
                  )}
                </Form>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

