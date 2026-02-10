import React, { useEffect } from "react";
import { Container, Row, Col, Form, Button, Card } from "react-bootstrap";
import imageUrl from "./images/db1.jpg";

export default function Contact() {
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

  const handleSubmit = (e) => {
    e.preventDefault();
    alert("Thank you for your message. We will get back to you shortly!");
  };

  return (
    <Container className="mt-5">
      <Row className="mb-5">
        <Col>
          <h1 className="display-4 text-primary">Contact Us</h1>
          <p className="lead text-light">
            Have questions or need support? We're here to help. Reach out to us through the form below or via our contact details.
          </p>
        </Col>
      </Row>

      <Row>
        <Col md={7}>
          <Card className="p-4 shadow-sm border-0 bg-dark text-light" style={{ opacity: 0.9 }}>
            <h3>Send us a Message</h3>
            <Form onSubmit={handleSubmit}>
              <Form.Group className="mb-3" controlId="contactForm.Name">
                <Form.Label>Full Name</Form.Label>
                <Form.Control type="text" placeholder="Enter your name" required />
              </Form.Group>
              <Form.Group className="mb-3" controlId="contactForm.Email">
                <Form.Label>Email Address</Form.Label>
                <Form.Control type="email" placeholder="name@example.com" required />
              </Form.Group>
              <Form.Group className="mb-3" controlId="contactForm.Subject">
                <Form.Label>Subject</Form.Label>
                <Form.Control type="text" placeholder="What is this regarding?" required />
              </Form.Group>
              <Form.Group className="mb-3" controlId="contactForm.Message">
                <Form.Label>Message</Form.Label>
                <Form.Control as="textarea" rows={5} placeholder="Your message..." required />
              </Form.Group>
              <Button variant="primary" type="submit">
                Submit Message
              </Button>
            </Form>
          </Card>
        </Col>

        <Col md={5}>
          <div className="ps-md-4 mt-4 mt-md-0 text-light">
            <h3>Our Office</h3>
            <p className="mb-4">
              <strong>Address:</strong><br />
              123 Hospital Road,<br />
              Colombo, Sri Lanka
            </p>

            <h3>Support Hours</h3>
            <p className="mb-4">
              <strong>Monday - Friday:</strong> 8:00 AM - 6:00 PM<br />
              <strong>Saturday:</strong> 9:00 AM - 2:00 PM<br />
              <strong>Sunday:</strong> Closed
            </p>

            <h3>Direct Contact</h3>
            <p>
              <strong>Phone:</strong> +94 76 223 6029<br />
              <strong>Email:</strong> cathlab@hotmail.com
            </p>
          </div>
        </Col>
      </Row>
    </Container>
  );
}

