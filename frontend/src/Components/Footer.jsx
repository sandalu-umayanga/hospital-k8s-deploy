import React from 'react';
import { Container, Row, Col } from 'react-bootstrap';
import "./ComponentCss/Footer.css";

export default function Footer() {
  return (
    <footer className="footerMain mt-5 py-4">
      <Container>
        <Row>
          <Col md={4} className="mb-3">
            <h5>CathLab Management</h5>
            <p className="small">
              Providing world-class healthcare management solutions for modern hospitals. 
              Our system streamlines patient care and clinical operations.
            </p>
          </Col>
          <Col md={2} className="mb-3">
            <h5>Quick Links</h5>
            <ul className="list-unstyled small">
              <li><a href="/" className="footer-link">Home</a></li>
              <li><a href="/about" className="footer-link">About Us</a></li>
              <li><a href="/contact" className="footer-link">Contact</a></li>
              <li><a href="/patientLogin" className="footer-link">Patient Portal</a></li>
            </ul>
          </Col>
          <Col md={3} className="mb-3">
            <h5>Departments</h5>
            <ul className="list-unstyled small">
              <li><a href="#cardiology" className="footer-link">Cardiology</a></li>
              <li><a href="#neurology" className="footer-link">Neurology</a></li>
              <li><a href="#orthopedics" className="footer-link">Orthopedics</a></li>
              <li><a href="#pediatrics" className="footer-link">Pediatrics</a></li>
            </ul>
          </Col>
          <Col md={3} className="mb-3">
            <h5>Contact Us</h5>
            <ul className="list-unstyled small">
              <li><i className="bi bi-geo-alt-fill me-2"></i>123 Hospital Road, Colombo</li>
              <li><i className="bi bi-telephone-fill me-2"></i>+94 76 223 6029</li>
              <li><i className="bi bi-envelope-fill me-2"></i>cathlab@hotmail.com</li>
            </ul>
          </Col>
        </Row>
        <hr className="bg-light" />
        <Row>
          <Col className="text-center">
            <p className="small mb-0">&copy; {new Date().getFullYear()} CathLab Hospital Management System. All Rights Reserved.</p>
          </Col>
        </Row>
      </Container>
    </footer>
  );
}
