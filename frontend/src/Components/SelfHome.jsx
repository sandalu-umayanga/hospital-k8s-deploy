import React, { useEffect } from "react";
import imageUrl from "./images/db1.jpg";
import { Container, Row, Col, Card, Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import "./ComponentCss/SelfHome.css"; // Import your custom CSS file
import imageUrlCard from "./images/noteIcon.png";

export default function SelfHome(props) {
  useEffect(() => {
    // Set the background image of the body
    document.body.style.backgroundImage = `url(${imageUrl})`;
    document.body.style.backgroundSize = "cover"; // Ensures the image covers the viewport
    document.body.style.backgroundRepeat = "no-repeat"; // Ensures the image does not repeat
    document.body.style.backgroundPosition = "center center"; // Centers the image
    document.body.style.backgroundAttachment = "fixed"; // Prevents the background image from moving when the component unmounts

    // Cleanup function to reset background image when the component unmounts
    return () => {
      document.body.style.backgroundImage = "";
      document.body.style.backgroundSize = "";
      document.body.style.backgroundRepeat = "";
      document.body.style.backgroundPosition = "";
    };
  }, []);

  const navigate = useNavigate();
  const location = useLocation();
  const { user } = location.state || {}; // Extracting the 'user' data

  return (
    <div>
      <Container className="mt-4">
        <Row>
        <Col>
    <Card className="mb-3 card">
      <Card.Body className="d-flex align-items-start">
        <div className="me-3"> {/* Ensures spacing between image and text */}
          <Card.Img src={imageUrlCard} className="noteIcon" />
        </div>
        <div>
          <Card.Title>Welcome, <b>{props.pronoun} {user.firstName}</b></Card.Title>
          <Card.Text>{user.simpleOverview}</Card.Text>
        </div>
      </Card.Body>
    </Card>
  </Col>
        </Row>

        <Row>
        <Col md={6}>
            <Card className="mb-3">
              <Card.Body>
                <Card.Title>Profile Summary</Card.Title>
                <Card.Text>
                  View and edit your personal details, update your
                  specialization, and more.
                </Card.Text>
                <Button onClick={() => navigate(props.profileLink, { state: { self: user } })}>
                  Go to Profile
                </Button>
              </Card.Body>
            </Card>
          </Col>

          <Col md={6}>
            <Card className="mb-3">
              <Card.Body>
                <Card.Title>Appointments Overview</Card.Title>
                <Card.Text>
                  Check your upcoming appointments and patient schedules.
                </Card.Text>
                <Button onClick={() => navigate("/appointments")}>
                  View Appointments
                </Button>
              </Card.Body>
            </Card>
          </Col>
        </Row>

        <Row>
          <Col md={6}>
            <Card className="mb-3">
              <Card.Body>
                <Card.Title>Patient Management</Card.Title>
                <Card.Text>
                  Manage your patients, view medical histories, and add new
                  patient records.
                </Card.Text>
                <Button onClick={() => navigate("/patients", { state: { user, role: props.role, getPatientsLink: props.getPatientsLink } })}>
                  Manage Patients
                </Button>
              </Card.Body>
            </Card>
          </Col>

          <Col md={6}>
            <Card className="mb-3">
              <Card.Body>
                <Card.Title>Notifications</Card.Title>
                <Card.Text>
                  Check your latest notifications and messages.
                </Card.Text>
                <Button onClick={() => navigate("/notifications")}>
                  View Notifications
                </Button>
              </Card.Body>
            </Card>
          </Col>
        </Row>

        <Row>
          <Col md={6}>
            <Card className="mb-3">
              <Card.Body>
                <Card.Title>Statistics and Reports</Card.Title>
                <Card.Text>
                  Analyze your performance and generate reports.
                </Card.Text>
                <Button onClick={() => navigate("/statistics")}>
                  View Statistics
                </Button>
              </Card.Body>
            </Card>
          </Col>

          <Col md={6}>
            <Card className="mb-3">
              <Card.Body>
                <Card.Title>Logout</Card.Title>
                <Card.Text>Logout from your account securely.</Card.Text>
                <Button variant="danger" onClick={() => {
                  props.onLogout?.();
                  navigate("/");
                }}>
                  Logout
                </Button>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

