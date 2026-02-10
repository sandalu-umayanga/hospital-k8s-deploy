import React, { useEffect, useState } from "react";
import imageUrl from "./images/db1.jpg";
import { Container, Form, Button, Col, Row, Card } from "react-bootstrap";
import "./ComponentCss/Profile.css";
import { useLocation } from "react-router-dom";
import imageUrlProfile from "./images/defaultProfile.png";
import axios from "axios";

export default function Profile(props) {
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

  const location = useLocation();
  const { self } = location.state || {};

  const [user, setUser] = useState({
    id: self.id,
    firstName: self.firstName,
    lastName: self.lastName,
    email: self.email,
    address: self.address,
    phone: self.phone,
    status: self.status,
    gender: self.gender,
    simpleOverview: self.simpleOverview,
    password: "",
  });

  const [profilePicture, setProfilePicture] = useState(imageUrlProfile);
  const [fileInputKey, setFileInputKey] = useState(Date.now()); // Key to reset the file input

  useEffect(() => {
    const fetchProfilePicture = async () => {
      try {
        const response = await axios.get(
          props.getPictureLink + self.id,
          {
            responseType: "arraybuffer",
          }
        );
        const blob = new Blob([response.data], { type: "image/jpeg" });
        const url = URL.createObjectURL(blob);
        setProfilePicture(url);
      } catch (error) {
        console.error("Error fetching profile picture:", error);
      }
    };

    fetchProfilePicture();
  }, [self.id]); // Dependency to refetch if self.id changes

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser((prevUser) => ({
      ...prevUser,
      [name]: value,
    }));
  };

  const handleProfileUpdate = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.put(
        props.userUpdateLink,
        user,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      alert("code:" + response.data.code + " = " + response.data.message);
    } catch (error) {
      alert("An error occurred: " + error.message);
      console.log(error);
    }
  };

  const handleProfilePictureUpdate = async () => {
    if (!profilePicture) {
      alert("Please select a profile picture to upload.");
      return;
    }

    const formData = new FormData();
    formData.append("profilePicture", profilePicture);

    try {
      const response = await axios.put(
        props.updatePictureLink + user.id,
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
      alert("code:" + response.data.code + " = " + response.data.message);

      // Reload the updated profile picture
      setFileInputKey(Date.now()); // Reset file input
      const newProfilePictureUrl = URL.createObjectURL(profilePicture);
      setProfilePicture(newProfilePictureUrl);
    } catch (error) {
      alert("An error occurred: " + error.message);
      console.log(error);
    }
  };

  return (
    <div>
      <Container className="mt-4">
        <Row>
          <Col md={4}>
            <Card className="mb-4">
              <Card.Body className="text-center">
                <Card.Title>
                  {props.pronoun} {self.firstName} {self.lastName}
                </Card.Title>
                <img
                  src={profilePicture}
                  className="img-thumbnail mb-3"
                  alt="Profile"
                />
                <Form.Group controlId="formFile" className="mb-3">
                  <Form.Label>Change Profile Picture</Form.Label>
                  <Form.Control
                    type="file"
                    key={fileInputKey} // Use key to reset the file input
                    onChange={(e) => setProfilePicture(e.target.files[0])}
                  />
                  <Button
                    className="mt-2"
                    variant="primary"
                    onClick={handleProfilePictureUpdate}
                  >
                    Update Profile Picture
                  </Button>
                </Form.Group>
              </Card.Body>
            </Card>
          </Col>

          <Col md={8}>
            <Card>
              <Card.Body>
                <Form onSubmit={handleProfileUpdate}>
                  {/* Form fields remain unchanged */}
                  <Form.Group
                    as={Row}
                    className="mb-3"
                    controlId="formFirstName"
                  >
                    <Form.Label column sm={3}>
                      First Name
                    </Form.Label>
                    <Col sm={9}>
                      <Form.Control
                        type="text"
                        name="firstName"
                        value={user.firstName}
                        onChange={handleChange}
                        placeholder="Enter your name"
                      />
                    </Col>
                  </Form.Group>

                  <Form.Group
                    as={Row}
                    className="mb-3"
                    controlId="formLastName"
                  >
                    <Form.Label column sm={3}>
                      Last Name
                    </Form.Label>
                    <Col sm={9}>
                      <Form.Control
                        type="text"
                        name="lastName"
                        value={user.lastName}
                        onChange={handleChange}
                        placeholder="Enter your name"
                      />
                    </Col>
                  </Form.Group>

                  <Form.Group as={Row} className="mb-3" controlId="formEmail">
                    <Form.Label column sm={3}>
                      Email
                    </Form.Label>
                    <Col sm={9}>
                      <Form.Control
                        type="email"
                        name="email"
                        value={user.email}
                        onChange={handleChange}
                        placeholder="Enter your email"
                        readOnly
                      />
                    </Col>
                  </Form.Group>

                  <Form.Group as={Row} className="mb-3" controlId="formAddress">
                    <Form.Label column sm={3}>
                      Address
                    </Form.Label>
                    <Col sm={9}>
                      <Form.Control
                        type="text"
                        name="address"
                        value={user.address}
                        onChange={handleChange}
                        placeholder="Enter your address"
                      />
                    </Col>
                  </Form.Group>

                  <Form.Group as={Row} className="mb-3" controlId="formPhone">
                    <Form.Label column sm={3}>
                      Phone Number
                    </Form.Label>
                    <Col sm={9}>
                      <Form.Control
                        type="text"
                        name="phone"
                        value={user.phone}
                        onChange={handleChange}
                        placeholder="Enter your phone number"
                      />
                    </Col>
                  </Form.Group>

                  <Form.Group as={Row} className="mb-3" controlId="formStatus">
                    <Form.Label column sm={3}>
                      Status
                    </Form.Label>
                    <Col sm={9}>
                      <Form.Control
                        as="select"
                        name="status"
                        value={user.status}
                        onChange={handleChange}
                      >
                        <option value={self.status}>
                          {self.status}
                        </option>
                        <option value="Married">Married</option>
                        <option value="Single">Single</option>
                        <option value="Divorced">Divorced</option>
                        <option value="Other">Other</option>
                      </Form.Control>
                    </Col>
                  </Form.Group>

                  <Form.Group as={Row} className="mb-3" controlId="formGender">
                    <Form.Label column sm={3}>
                      Gender
                    </Form.Label>
                    <Col sm={9}>
                      <Form.Control
                        as="select"
                        name="gender"
                        value={user.gender}
                        onChange={handleChange}
                      >
                        <option value={self.gender}>
                          {self.gender}
                        </option>
                        <option value="Male">Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                      </Form.Control>
                    </Col>
                  </Form.Group>

                  <Form.Group
                    as={Row}
                    className="mb-3"
                    controlId="formSimpleOverview"
                  >
                    <Form.Label column sm={3}>
                      Simple Overview
                    </Form.Label>
                    <Col sm={9}>
                      <Form.Control
                        as="textarea"
                        name="simpleOverview"
                        value={user.simpleOverview}
                        onChange={handleChange}
                        placeholder="Enter your simple overview"
                      />
                    </Col>
                  </Form.Group>

                  <Form.Group
                    as={Row}
                    className="mb-3"
                    controlId="formPassword"
                  >
                    <Form.Label column sm={3}>
                      Change Password
                    </Form.Label>
                    <Col sm={9}>
                      <Form.Control
                        type="password"
                        name="password"
                        value={user.password}
                        onChange={handleChange}
                        placeholder="Enter new password"
                      />
                    </Col>
                  </Form.Group>

                  <div className="text-center">
                    <Button variant="primary" type="submit">
                      Save Changes
                    </Button>
                  </div>
                </Form>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
}

