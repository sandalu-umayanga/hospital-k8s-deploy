import React, { useEffect } from "react";
import { Container, Row, Col, Card } from "react-bootstrap";
import imageUrl from "./images/db1.jpg";

export default function About() {
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
      <Row className="mb-5">
        <Col>
          <h1 className="display-4 text-primary">About CathLab Management System</h1>
          <p className="lead text-light">
            Welcome to the CathLab Hospital Management System, a state-of-the-art solution designed to streamline healthcare operations and enhance patient care.
          </p>
        </Col>
      </Row>

      <Row className="mb-4">
        <Col md={6}>
          <h3 className="text-light">Our Mission</h3>
          <p className="text-light">
            Our mission is to provide healthcare professionals with intuitive, efficient, and reliable tools to manage patient data, coordinate treatments, and optimize hospital workflows. We believe that by empowering staff with better technology, we can ultimately improve health outcomes for patients.
          </p>
        </Col>
        <Col md={6}>
          <h3 className="text-light">Our Vision</h3>
          <p className="text-light">
            To be the leading provider of hospital management software, recognized for innovation, security, and a deep commitment to the healthcare community. We envision a future where every hospital is equipped with seamless digital systems that allow doctors and nurses to focus entirely on what they do best: saving lives.
          </p>
        </Col>
      </Row>

      <Row className="mt-5">
        <Col>
          <h2 className="text-center mb-4 text-light">Core Features</h2>
        </Col>
      </Row>

      <Row>
        <Col md={4} className="mb-4">
          <Card className="h-100 shadow-sm border-0">
            <Card.Body>
              <Card.Title className="text-primary">Patient Portal</Card.Title>
              <Card.Text>
                Secure access for patients to view their medical history, assigned doctors, and treatment progress.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4} className="mb-4">
          <Card className="h-100 shadow-sm border-0">
            <Card.Body>
              <Card.Title className="text-primary">Clinical Management</Card.Title>
              <Card.Text>
                Comprehensive tools for doctors and nurses to manage appointments, observations, and treatment plans.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
        <Col md={4} className="mb-4">
          <Card className="h-100 shadow-sm border-0">
            <Card.Body>
              <Card.Title className="text-primary">Admin Control</Card.Title>
              <Card.Text>
                Robust administration panel for managing hospital staff, departments, and viewing system-wide statistics.
              </Card.Text>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

