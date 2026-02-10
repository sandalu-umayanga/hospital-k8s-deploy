import React from "react";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Image from "react-bootstrap/Image";
import Row from "react-bootstrap/Row";
import imageUrlicon from "./images/icon1.png";
import imageUrlcontact from "./images/contact.png";
import "./ComponentCss/MainInfo.css";

export default function MainInfo() {
  return (
    <Container>
      <Row className="row justify-content-between">
        <Col xs={12} sm={4}>
          <div className="d-flex flex-row mb-3">
            <div className="me-2">
              <Image src={imageUrlicon} roundedCircle className="imgIcon" />
            </div>
            <div>
              <p><b>CathLab<br/>Management System</b></p>
            </div>
          </div>
        </Col>
        <Col xs={12} sm={4}>
        <div className="d-flex flex-row mb-3 justify-content-end">
            <div className="mt-2">
                <p><b>+94762236029<br/>cathlab@hotmail.com</b></p>
            </div>
            <div>
                <Image src={imageUrlcontact} roundedCircle className="imgIconContact" />
            </div>
          </div>
        </Col>
      </Row>
    </Container>
  );
}

