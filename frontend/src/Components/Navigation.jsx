import React from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import NavDropdown from "react-bootstrap/NavDropdown";
import { Link, useNavigate } from "react-router-dom";
import "./ComponentCss/Navigation.css";

export default function Navigation({ isAuthenticated, user, onLogout }) {
  const navigate = useNavigate();

  const handleLogout = () => {
    if (onLogout) onLogout();
    navigate("/");
  };

  return (
    <div className="mb-3">
      <Navbar collapseOnSelect expand="lg" className="bg-body-tertiary">
        <Container className="nav">
          <Navbar.Brand as={Link} to="/">CathLab</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link as={Link} to="/">Home</Nav.Link>
              <Nav.Link as={Link} to="/about">About</Nav.Link>
              <Nav.Link as={Link} to="/contact">Contact</Nav.Link>
              <Nav.Link as={Link} to="/statistics">Statistics</Nav.Link>
              
              <NavDropdown title="Services" id="collapsible-nav-dropdown">
                <NavDropdown.Item href="#cardiology">Cardiology</NavDropdown.Item>
                <NavDropdown.Item href="#neurology">Neurology</NavDropdown.Item>
                <NavDropdown.Item href="#orthopedics">Orthopedics</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="#pediatrics">Pediatrics</NavDropdown.Item>
              </NavDropdown>
            </Nav>
            <Nav>
              {isAuthenticated ? (
                <>
                  <Nav.Link as={Link} to="/notifications">Notifications</Nav.Link>
                  <Nav.Link as={Link} to="/appointments">Appointments</Nav.Link>
                  
                  <NavDropdown title={user?.firstName || "Account"} id="user-nav-dropdown">
                    {localStorage.getItem("role") === "ADMIN" && (
                        <NavDropdown.Item as={Link} to="/adminDashboard">Admin Dashboard</NavDropdown.Item>
                    )}
                    {["DOCTOR", "NURSE", "ATTENDANT"].includes(localStorage.getItem("role")) && (
                        <NavDropdown.Item as={Link} to="/patientRegister">Patient Register</NavDropdown.Item>
                    )}
                    {localStorage.getItem("role") === "PATIENT" && (
                        <NavDropdown.Item as={Link} to="/patientHome">My Dashboard</NavDropdown.Item>
                    )}
                    <NavDropdown.Divider />
                    <NavDropdown.Item onClick={handleLogout}>Logout</NavDropdown.Item>
                  </NavDropdown>
                </>
              ) : (
                <>
                  <NavDropdown title="Login" id="login-nav-dropdown">
                    <NavDropdown.Item as={Link} to="/adminLogin">Admin</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/doctorLogin">Doctor</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/nurseLogin">Nurse</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/attendantLogin">Attendant</NavDropdown.Item>
                    <NavDropdown.Divider />
                    <NavDropdown.Item as={Link} to="/patientLogin">Patient</NavDropdown.Item>
                  </NavDropdown>
                  <NavDropdown title="Register" id="register-nav-dropdown">
                    <NavDropdown.Item as={Link} to="/doctorRegister">Doctor</NavDropdown.Item>
                    <NavDropdown.Item as={Link} to="/nurseRegister">Nurse</NavDropdown.Item>
                    <NavDropdown.Divider />
                    <NavDropdown.Item as={Link} to="/attendantRegister">Attendant</NavDropdown.Item>
                  </NavDropdown>
                </>
              )}
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </div>
  );
}
